package org.example.currency_exchange.commons;

import jakarta.servlet.http.HttpServletRequest;
import org.example.currency_exchange.exception_and_error.InvalidParameterInPathException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServletUtil {
    public static Map<String, String> getParametersFromRequest(HttpServletRequest request) throws IOException {
        String body = getBodyFromRequest(request);
        String encodeBody = decodeBody(body);
        return convertBodyToMap(encodeBody);
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
        return Arrays.stream(parameters)
                .map(parameter -> parameter.split("="))
                .map(Arrays::asList)
                .collect(Collectors.toMap(
                        List::getFirst,
                        List::getLast
                ));
    }

    public static String getParameterFromPath(String path) throws InvalidParameterInPathException {
        //если /currency, не /currency/ -> кидает path == null
        String pathSeparator = "/";
        List<String> pathComponents = Arrays.asList(path.split(pathSeparator));
        return filterPathComponents(pathComponents);
    }

    private static String filterPathComponents(List<String> pathComponents) throws InvalidParameterInPathException {
        pathComponents = pathComponents.stream()
                .filter(component -> !component.isEmpty())
                .toList();

        int allowedAmountPathComponents = 1;
        if (pathComponents.size() != allowedAmountPathComponents) {
            throw new InvalidParameterInPathException("Не было передано параметра пути");
        }
        return pathComponents.getFirst();
    }
}
