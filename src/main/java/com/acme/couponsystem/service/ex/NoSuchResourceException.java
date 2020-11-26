package com.acme.couponsystem.service.ex;

public abstract class NoSuchResourceException extends RuntimeException {
    public NoSuchResourceException(String message) {
        super(message);
    }
}
