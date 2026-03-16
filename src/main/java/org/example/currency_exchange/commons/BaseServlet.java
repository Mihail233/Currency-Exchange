package org.example.currency_exchange.commons;

import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.ErrorEntity;

import java.io.IOException;
import java.io.PrintWriter;

public interface BaseServlet {
    public default void sendResponse(int code, String message, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        response.setStatus(code);
        printWriter.println(message);
    }
}
