package com.acme.couponsystem.service.ex;

public class NoSuchCategoryException extends NoSuchResourceException {
    public NoSuchCategoryException(String message) {
        super(message);
    }
}
