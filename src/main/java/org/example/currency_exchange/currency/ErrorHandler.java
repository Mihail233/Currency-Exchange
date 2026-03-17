package org.example.currency_exchange.currency;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.JsonConverter;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ErrorHandler {
    private final Map<String, ResponseEntity> errors = new HashMap<>();
    private final JsonConverter jsonConverter = new JsonConverter();

    public ErrorHandler() {
        try {
            loadErrorMessages();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("d");
        }
    }

    public void loadErrorMessages() throws JsonProcessingException {
        for (CurrencyTypeError currencyTypeError: CurrencyTypeError.values()) {
            ResponseEntity responseEntity = currencyTypeError.getResponseEntity();
            String messageJson = jsonConverter.convertToJSON(responseEntity);
            responseEntity.setMessage(messageJson);
            errors.put(currencyTypeError.toString(), responseEntity);
        }
    }

    public ResponseEntity catchError(IOException exception) {
        return errors.get(getObjectClassName(exception));
    }

    public String getObjectClassName(IOException ioException) {
        return ioException.getClass().getSimpleName();
    }
}