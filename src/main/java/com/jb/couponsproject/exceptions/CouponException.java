package com.jb.couponsproject.exceptions;

public class CouponException extends Exception {
    public CouponException() {
        super("Coupon Exception");
    }

    public CouponException(String message) {
        super(message);
    }

}
