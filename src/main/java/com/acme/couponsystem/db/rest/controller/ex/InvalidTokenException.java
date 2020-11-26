package com.acme.couponsystem.db.rest.controller.ex;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }
}
