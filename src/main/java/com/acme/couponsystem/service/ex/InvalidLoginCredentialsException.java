package com.acme.couponsystem.service.ex;

public class InvalidLoginCredentialsException extends RuntimeException {

    public InvalidLoginCredentialsException(String message) {
        super(message);
    }

}
