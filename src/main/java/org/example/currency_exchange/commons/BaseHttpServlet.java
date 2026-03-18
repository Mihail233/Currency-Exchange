package org.example.currency_exchange.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.JsonConverter;
import org.example.currency_exchange.ResponseEntity;

import java.io.IOException;
import java.io.PrintWriter;

public abstract class BaseHttpServlet extends HttpServlet {
    private final JsonConverter jsonConverter = new JsonConverter();

    public void sendResponse(int code, String message, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        response.setStatus(code);
        printWriter.println(message);
    }

    public void sendSuccessfulResponse(Object objectDto, HttpServletResponse response) throws IOException {
        String json = jsonConverter.convertToJSON(objectDto);
        int statusCode = HttpServletResponse.SC_OK;
        sendResponse(statusCode, json, response);
    }

    public JsonConverter getJsonConverter() {
        return jsonConverter;
    }
}
