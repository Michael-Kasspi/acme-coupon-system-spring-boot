package com.acme.couponsystem.service.ex;

public class NoSuchCouponException extends NoSuchResourceException{
    public NoSuchCouponException(String message) {
        super(message);
    }
}
