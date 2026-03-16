package org.example.currency_exchange;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record ErrorEntity(int statusCode, String message) {

    @JsonIgnore
    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
