package org.example.currency_exchange.util;

import jakarta.servlet.http.HttpServletRequest;
import org.example.currency_exchange.JsonConverter;
import org.example.currency_exchange.exception.InvalidParameterInPathException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServletUtil {
    private static final JsonConverter JSON_CONVERTER = new JsonConverter();

    public static JsonConverter getJsonConverter() {
        return JSON_CONVERTER;
    }

    public static Map<String, String> getParametersFromBody(HttpServletRequest request) throws IOException {
        String body = getBodyFromRequest(request);
        String encodeBody = decodeBody(body);
        return convertBodyToMap(encodeBody);
    }

    public static Map<String, String> getParametersFromQueryParameters(String queryParameters) {
        return convertBodyToMap(queryParameters);
    }

    private static String decodeBody(String body) {
        return URLDecoder.decode(body, StandardCharsets.UTF_8);
    }

    private static String getBodyFromRequest(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String lineSeparator = System.lineSeparator();
        return reader.lines()
                .collect(Collectors.joining(lineSeparator));
    }

    private static Map<String, String> convertBodyToMap(String encodeBody) {
        String[] parameters = encodeBody.split("&");
        int keyAndValue = 2;
        return Arrays.stream(parameters)
                .map(parameter -> parameter.split("="))
                .map(Arrays::asList)
                .filter(parameter -> parameter.size() == keyAndValue)
                .filter(parameter -> !parameter.getLast().trim().isEmpty())
                .collect(Collectors.toMap(
                        List::getFirst,
                        List::getLast
                ));
    }

    public static String getParameterFromPath(String path) throws InvalidParameterInPathException {
        //если /currency, не /currency/ -> кидает path == null
        String separator = "/";
        List<String> pathParameters = Arrays.asList(path.split(separator));
        return filterPathParameters(pathParameters);
    }

    private static String filterPathParameters(List<String> pathParameters) throws InvalidParameterInPathException {
        pathParameters = pathParameters.stream()
                .filter(component -> !component.isEmpty())
                .toList();

        int allowedAPathParameters = 1;
        if (pathParameters.size() != allowedAPathParameters) {
            throw new InvalidParameterInPathException("Не было передано параметра пути");
        }
        return pathParameters.getFirst();
    }
}
