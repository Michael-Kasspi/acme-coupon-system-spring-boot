package com.acme.couponsystem.service.ex;

public class NoSuchCompanyException extends NoSuchResourceException {
    public NoSuchCompanyException(String message) {
        super(message);
    }
}
