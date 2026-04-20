package org.example.currency_exchange.servlet.currency;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.util.ServletUtil;
import org.example.currency_exchange.dto.currency.CurrencyDTO;
import org.example.currency_exchange.dto.exchange.service.currency.CurrencyService;

import java.io.IOException;


@WebServlet(name = "CurrencyServlet", value = "/currency/*")
public class CurrencyServlet extends HttpServlet {
    private CurrencyService currencyService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.currencyService = (CurrencyService) getServletContext().getAttribute("currencyService");

        if (currencyService == null) {
            throw new IllegalStateException("CurrencyService не найден");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currencyCode = ServletUtil.getParameterFromPath(request.getPathInfo());
        CurrencyDTO currencyDTO = currencyService.getCurrency(currencyCode);
        ServletUtil.sendResponse(HttpServletResponse.SC_OK, currencyDTO, response);
    }
}
