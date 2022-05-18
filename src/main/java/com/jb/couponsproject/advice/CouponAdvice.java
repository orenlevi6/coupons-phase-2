package com.jb.couponsproject.advice;

import com.jb.couponsproject.exceptions.CouponException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class CouponAdvice {
    @ExceptionHandler(value = {CouponException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleCouponException(Exception err) {
        return new ErrorDetails("Coupon error", err.getMessage());
    }

}
