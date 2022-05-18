package com.jb.couponsproject.controllers;

import com.jb.couponsproject.beans.Categories;
import com.jb.couponsproject.beans.Coupon;
import com.jb.couponsproject.exceptions.CouponException;
import com.jb.couponsproject.exceptions.LoginException;
import com.jb.couponsproject.exceptions.NotExistException;
import com.jb.couponsproject.exceptions.TokenException;
import com.jb.couponsproject.login.ClientDetails;
import com.jb.couponsproject.login.ClientType;
import com.jb.couponsproject.login.LoginManager;
import com.jb.couponsproject.services.serviceImpl.CustomerServiceImpl;
import com.jb.couponsproject.utils.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final JWT jwt;
    private final LoginManager loginManager;
    private CustomerServiceImpl customerService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ClientDetails clientDetails) throws LoginException {
        if (!clientDetails.getClientType().toString().equals("CUSTOMER")) {
            throw new LoginException("Invalid user type");
        }
        customerService = (CustomerServiceImpl) loginManager.login(clientDetails);
        return new ResponseEntity<>(jwt.generateToken(clientDetails), HttpStatus.ACCEPTED);
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon)
            throws TokenException, LoginException, CouponException, NotExistException {
        jwt.checkUser(token, ClientType.CUSTOMER);

        customerService.purchaseCoupon(coupon);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body("Coupon #" + coupon.getCompany().getId() + " purchased successfully");
    }

    @GetMapping("/coupon/get/all")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token)
            throws NotExistException, TokenException, LoginException {
        jwt.checkUser(token, ClientType.CUSTOMER);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body(customerService.getAllCustomerCoupons());
    }

    @GetMapping("/coupon/get/{category}")
    public ResponseEntity<?> getCustomerCouponsByCategory(@RequestHeader(name = "Authorization") String token, @RequestHeader Categories category)
            throws NotExistException, TokenException, LoginException {
        jwt.checkUser(token, ClientType.CUSTOMER);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body(customerService.getAllCustomerCouponsByCategory(category));
    }

    @GetMapping("/coupon/get/{maxPrice}")
    public ResponseEntity<?> getCustomerCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @RequestHeader double maxPrice)
            throws NotExistException, TokenException, LoginException {
        jwt.checkUser(token, ClientType.CUSTOMER);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body(customerService.getAllCustomerCouponsByMaxPrice(maxPrice));
    }

}
