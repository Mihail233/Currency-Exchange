package org.example.currency_exchange.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.currency_exchange.exception_and_error.FileApiErrorCodesReadingException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class JsonConverter<T> {
    private final Properties errors = new Properties();
    private static final ObjectMapper MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public abstract String convertToJSON(T object);

    public void loadErrorMessages(String pathToErrorMessages) {
        try (InputStream errorsInputStream = this.getClass().getClassLoader().getResourceAsStream(pathToErrorMessages)){
            errors.load(errorsInputStream);
        } catch (IOException e) {
            throw new FileApiErrorCodesReadingException(String.format("Ошибка с файлом %s ", pathToErrorMessages));
        }
    }

    public ObjectMapper getMapper() {
        return MAPPER;
    }

    public String getJsonError(int statusCode) {
        String message = errors.getProperty(String.valueOf(statusCode));
        Error error = new Error(message);
        String json;
        try {
            json = getMapper().writeValueAsString(error);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
