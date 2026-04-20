package org.example.currency_exchange.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.entity.Currency;
import org.example.currency_exchange.exception.InvalidParameterInPathException;
import org.example.currency_exchange.exception.RequiredQueryParametersMissException;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ServletUtil {
    private static final JsonConverter JSON_CONVERTER = new JsonConverter();

    private ServletUtil() {}

    public static void sendResponse(int code, Object object, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        String json = JSON_CONVERTER.convertToJSON(object);
        response.setStatus(code);
        printWriter.println(json);
    }

    public static JsonConverter getJsonConverter() {
        return JSON_CONVERTER;
    }

    public static Map<String, String> getParametersFromBody(HttpServletRequest request) throws IOException {
        String body = getBodyFromRequest(request);
        String encodeBody = decodeBody(body);
        return convertBodyToMap(encodeBody);
    }

    public static Map<String, String> getParametersFromQueryParameters(String queryParameters) throws RequiredQueryParametersMissException {
        if (queryParameters == null) {
            throw new RequiredQueryParametersMissException("The required query parameter is missing");
        }

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
        if (path == null) {
            throw new InvalidParameterInPathException("Currency code is missing from the address");
        }

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
            throw new InvalidParameterInPathException("Currency code is missing from the address");
        }
        return pathParameters.getFirst();
    }

    public static List<String> splitCurrencyPairIntoCodes(String currencyPair) {
        int size = Currency.CODE_SIZE;
        List<String> codes = new ArrayList<>((currencyPair.length() + size - 1) / size);

        for (int start = 0; start < currencyPair.length(); start += size) {
            codes.add(currencyPair.substring(start, Math.min(currencyPair.length(), start + size)));
        }
        return codes;
    }
}
