package org.example.currency_exchange.commons;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.JsonConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public JsonConverter getJsonConverter() {
        return jsonConverter;
    }

    public String getBodyFromRequest(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String lineSeparator = System.lineSeparator();
        return reader.lines()
                .collect(Collectors.joining(lineSeparator));
    }

    public Map<String, String> convertBodyToMap(String encodeBody) {
        String[] parameters = encodeBody.split("&");
        return Arrays.stream(parameters)
                .map(parameter -> parameter.split("="))
                .map(Arrays::asList)
                .collect(Collectors.toMap(
                        List::getFirst,
                        List::getLast
                ));
    }

}
