package com.acme.couponsystem.service.ex;

public class NoSuchAccountException extends NoSuchResourceException {
    public NoSuchAccountException(String message) {
        super(message);
    }
}
