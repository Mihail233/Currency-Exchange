package org.example.currency_exchange.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.dto.ErrorResponseDTO;
import org.example.currency_exchange.exception.*;
import org.example.currency_exchange.util.ServletUtil;

import java.io.IOException;

@WebFilter("/*")
public class ExceptionHandlingFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)  throws IOException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        try {
            filterChain.doFilter(request, resp);
        } catch (DataBaseUnavailableException e) {
            sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage(), resp);

        } catch (RequiredFormFieldMissException | InvalidParameterInPathException | RequiredQueryParametersMissException e) {
            sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), resp);

        } catch (CurrencyNotFoundException | ExchangeRateNotFoundException e) {
            sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage(), resp);

        } catch (CurrencyWithThisCodeExistsException | CurrencyPairWithThisCodeAlreadyExists e) {
            sendError(HttpServletResponse.SC_CONFLICT, e.getMessage(), resp);

        } catch (Exception e) {
            sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unknown error", resp);
        }
    }

    private void sendError(int code, String message, HttpServletResponse response) throws IOException {
        ErrorResponseDTO errorEntity = new ErrorResponseDTO(message);
        ServletUtil.sendResponse(code, errorEntity, response);
    }
}
