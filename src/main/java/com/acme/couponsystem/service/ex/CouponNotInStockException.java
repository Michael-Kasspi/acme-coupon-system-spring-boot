package com.acme.couponsystem.service.ex;

public class CouponNotInStockException extends RuntimeException {
    public CouponNotInStockException(String message) {
        super(message);
    }
}
