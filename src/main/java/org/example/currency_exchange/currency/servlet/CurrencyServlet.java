package org.example.currency_exchange.currency.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.JsonConverter;
import org.example.currency_exchange.commons.BaseServlet;
import org.example.currency_exchange.commons.ObjectDtoMapper;
import org.example.currency_exchange.currency.ErrorHandler;
import org.example.currency_exchange.currency.dto.CodeDTO;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.mapper.CodeMapper;
import org.example.currency_exchange.currency.service.CurrencyService;
import org.example.currency_exchange.exception_and_error.InvalidCurrencyCodeInPathException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "CurrencyServlet", value = "/currency/*")
public class CurrencyServlet extends HttpServlet implements BaseServlet {
    private static final String PATH_TO_ERROR_MESSAGES = "apiErrorCodes/currencyError.property";
    private final ObjectDtoMapper<String, CodeDTO> codeMapper = new CodeMapper();
    private final CurrencyService currencyService = new CurrencyService();
    private final JsonConverter jsonConverter = new JsonConverter();
    private final ErrorHandler errorHandler = new ErrorHandler();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String code = getCurrencyCodeFromPath(request.getPathInfo());
            CodeDTO codeDTO = codeMapper.objectToDto(code);
            CurrencyDTO currencyDTO = currencyService.getCurrency(codeDTO);
            //
            sendResponse(500, "", response);
        } catch (IOException e) {
            System.out.println(51);
        }
    }

    public String getCurrencyCodeFromPath(String path) throws InvalidCurrencyCodeInPathException {
        String pathSeparator = "/";
        List<String> pathComponents = Arrays.asList(path.split(pathSeparator));
        return filterPathComponents(pathComponents);
    }

    public String filterPathComponents(List<String> pathComponents) throws InvalidCurrencyCodeInPathException {
        pathComponents = pathComponents.stream()
                .filter(component -> !component.isEmpty())
                .toList();

        int allowedAmountPathComponents = 1;
        if (pathComponents.size() != allowedAmountPathComponents) {
            throw new InvalidCurrencyCodeInPathException("Найден не только 1 code");
        }
        return pathComponents.getFirst();
    }
}
