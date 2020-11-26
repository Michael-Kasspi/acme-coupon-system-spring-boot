package com.acme.couponsystem.service.ex;

public class AccountWithoutUserException extends RuntimeException {
    public AccountWithoutUserException(String message) {
        super(message);
    }
}
