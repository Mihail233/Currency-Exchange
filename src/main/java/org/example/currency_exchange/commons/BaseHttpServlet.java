package org.example.currency_exchange.commons;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.JsonConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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

    protected JsonConverter getJsonConverter() {
        return jsonConverter;
    }

    protected Map<String, String> getParametersFromRequest(HttpServletRequest request) throws IOException {
        String body = getBodyFromRequest(request);
        String encodeBody = decodeBody(body);
        return convertBodyToMap(encodeBody);
    }

    private String decodeBody(String body) {
        return URLDecoder.decode(body, StandardCharsets.UTF_8);
    }

    private String getBodyFromRequest(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String lineSeparator = System.lineSeparator();
        return reader.lines()
                .collect(Collectors.joining(lineSeparator));
    }

    private Map<String, String> convertBodyToMap(String encodeBody) {
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
