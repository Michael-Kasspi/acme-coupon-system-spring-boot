package com.acme.couponsystem.service.ex;

public abstract class UnableToUpdateResourceException extends RuntimeException{
    public UnableToUpdateResourceException(String message) {
        super(message);
    }
}
