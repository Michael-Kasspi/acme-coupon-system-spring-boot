package com.acme.couponsystem.service.ex;

public class TerminatedAccountException extends RuntimeException {
    public TerminatedAccountException(String message) {
        super(message);
    }
}
