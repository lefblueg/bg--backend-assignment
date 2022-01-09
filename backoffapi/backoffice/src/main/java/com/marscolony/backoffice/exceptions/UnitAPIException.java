package com.marscolony.backoffice.exceptions;

import org.springframework.http.HttpStatus;

public class UnitAPIException  extends RuntimeException {
    private HttpStatus status;
    private String message;

    public UnitAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public UnitAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
