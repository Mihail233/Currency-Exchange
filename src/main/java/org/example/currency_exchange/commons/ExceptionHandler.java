package org.example.currency_exchange.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.currency_exchange.JsonConverter;
import org.example.currency_exchange.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

abstract public class ExceptionHandler {
    private final JsonConverter jsonConverter = new JsonConverter();
    private final Map<String, ResponseEntity> exceptions = new HashMap<>();

    abstract public ResponseEntity catchException(IOException exception);

    public void loadErrorMessages(TypeException[] typeExceptions) throws JsonProcessingException {
        for (TypeException typeException: typeExceptions) {
            ResponseEntity responseEntity = typeException.getResponseEntity();
            String messageJson = jsonConverter.convertToJSON(responseEntity);
            responseEntity.setMessage(messageJson);
            exceptions.put(typeException.toString(), responseEntity);
        }
    }

    public Map<String, ResponseEntity> getExceptions() {
        return exceptions;
    }
}
