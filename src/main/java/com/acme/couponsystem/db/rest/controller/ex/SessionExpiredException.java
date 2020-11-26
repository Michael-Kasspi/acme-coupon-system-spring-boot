package com.acme.couponsystem.db.rest.controller.ex;

public class SessionExpiredException extends RuntimeException {
    public SessionExpiredException(String message) {
        super(message);
    }

}
