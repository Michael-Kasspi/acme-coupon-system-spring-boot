package com.acme.couponsystem.service.ex;

public class DuplicateCouponPurchaseException extends RuntimeException {

    public DuplicateCouponPurchaseException(String message) {
        super(message);
    }
}
