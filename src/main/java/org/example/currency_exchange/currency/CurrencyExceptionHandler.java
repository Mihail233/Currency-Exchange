package org.example.currency_exchange.currency;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.commons.ExceptionHandler;
import org.example.currency_exchange.commons.TypeException;

import java.io.IOException;
import java.util.Map;

public class CurrencyExceptionHandler extends ExceptionHandler {

    public CurrencyExceptionHandler() {
        try {
            TypeException[] typeExceptions = CurrencyTypeException.values();
            loadErrorMessages(typeExceptions);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("The CurrencyExceptionHandler class failed to load\n");
        }
    }

    @Override
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