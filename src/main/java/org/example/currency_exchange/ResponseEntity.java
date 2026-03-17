package org.example.currency_exchange;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResponseEntity {
    private final int statusCode;
    private String message;

    public ResponseEntity(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    @JsonIgnore
    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
