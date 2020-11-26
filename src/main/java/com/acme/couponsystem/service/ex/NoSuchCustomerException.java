package com.acme.couponsystem.service.ex;

public class NoSuchCustomerException extends NoSuchResourceException {
    public NoSuchCustomerException(String message) {
        super(message);
    }
}
