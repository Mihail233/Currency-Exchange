package org.example.currency_exchange.currency;

import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.ErrorEntity;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.exception_and_error.FileApiErrorCodesReadingException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ErrorHandler {
    private final Properties errors = new Properties();

    public ErrorHandler(String pathToErrorMessages) {
        loadErrorMessages(pathToErrorMessages);
    }

    public void loadErrorMessages(String pathToErrorMessages) {
        try (InputStream errorsInputStream = this.getClass().getClassLoader().getResourceAsStream(pathToErrorMessages)){
            errors.load(errorsInputStream);
        } catch (IOException e) {
            throw new FileApiErrorCodesReadingException(String.format("Ошибка с файлом %s ", pathToErrorMessages));
        }
    }

    public ErrorEntity catchError(IOException exception) {
        int statusCode;
        switch (exception) {
            case DataBaseUnavailableException dataBaseUnavailableException -> {
                statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                String messageError = getMessageError(statusCode);
                return new ErrorEntity(statusCode, messageError);
            }
            default -> throw new IllegalStateException("Unexpected value: " + exception);
        }
    }

    public String getMessageError(int statusCode) {
        return errors.getProperty(String.valueOf(statusCode));
    }
}
