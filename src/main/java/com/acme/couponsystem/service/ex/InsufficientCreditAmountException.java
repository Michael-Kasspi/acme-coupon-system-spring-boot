package com.acme.couponsystem.service.ex;

public class InsufficientCreditAmountException extends RuntimeException {
    public InsufficientCreditAmountException(String message) {
        super(message);
    }
}
