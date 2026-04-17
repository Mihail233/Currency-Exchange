package org.example.currency_exchange;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.exception.*;
import org.example.currency_exchange.util.ServletUtil;

import java.io.IOException;

@WebFilter("/*")
public class ExceptionHandlingFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        try {
            filterChain.doFilter(request, resp);
        } catch (DataBaseUnavailableException e) {
            sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage(), resp);

        } catch (RequiredFormFieldMissException | InvalidParameterInPathException e) {
            sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), resp);

        } catch (CurrencyNotFoundException e) {
            sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage(), resp);

        } catch (CurrencyWithThisCodeExistsException e) {
            sendError(HttpServletResponse.SC_CONFLICT, e.getMessage(), resp);

        } catch (Exception e) {
            sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unknown error", resp);
        }
    }

    private void sendError(int code, String message, HttpServletResponse response) throws IOException {
        ErrorEntity errorEntity = new ErrorEntity(message);
        ServletUtil.sendResponse(code, errorEntity, response);
    }
}
