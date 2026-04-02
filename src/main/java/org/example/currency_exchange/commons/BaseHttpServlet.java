package org.example.currency_exchange.commons;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.JsonConverter;

import java.io.IOException;
import java.io.PrintWriter;

public abstract class BaseHttpServlet extends HttpServlet {
    private final JsonConverter jsonConverter = new JsonConverter();

    protected void sendResponse(int code, String message, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        response.setStatus(code);
        printWriter.println(message);
    }

    protected void sendSuccessfulResponse(Object DTO, HttpServletResponse response) throws IOException {
        String json = jsonConverter.convertToJSON(DTO);
        int code = HttpServletResponse.SC_OK;
        sendResponse(code, json, response);
    }
}
