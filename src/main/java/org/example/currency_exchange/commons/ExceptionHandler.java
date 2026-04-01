package org.example.currency_exchange.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.currency_exchange.JsonConverter;
import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.currency.CurrencyTypeException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExceptionHandler {
    private final JsonConverter jsonConverter = new JsonConverter();
    private final Map<String, ResponseEntity> exceptions = new HashMap<>();

    public ExceptionHandler(TypeException[] typeExceptions) {
        try {
            loadErrorMessages(typeExceptions);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("The CurrencyExceptionHandler class failed to load\n");
        }
    }

    public Map<String, ResponseEntity> getExceptions() {
        return exceptions;
    }

    protected void loadErrorMessages(TypeException[] typeExceptions) throws JsonProcessingException {
        for (TypeException typeException: typeExceptions) {
            ResponseEntity responseEntity = typeException.getResponseEntity();
            String messageJson = jsonConverter.convertToJSON(responseEntity);
            responseEntity.setMessage(messageJson);
            exceptions.put(typeException.toString(), responseEntity);
        }
    }

    public ResponseEntity catchException(IOException exception) {
        String exceptionClassName = getClassName(exception);
        Map<String, ResponseEntity> exceptions = getExceptions();
        if (exceptions.containsKey(exceptionClassName)) {
            return exceptions.get(exceptionClassName);
        }
        return exceptions.get(CurrencyTypeException.UnknownException.toString());
    }

    private String getClassName(IOException exception) {
        return exception.getClass().getSimpleName();
    }

}
