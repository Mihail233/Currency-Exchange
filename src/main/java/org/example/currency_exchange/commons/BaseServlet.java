package org.example.currency_exchange.commons;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface BaseServlet {
    public void generateResponse(HttpServletResponse response) throws IOException;
    public void sendResponse(int code, String message, HttpServletResponse response) throws IOException;
}
