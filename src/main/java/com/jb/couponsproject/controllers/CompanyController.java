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
import com.jb.couponsproject.services.serviceImpl.CompanyServiceImpl;
import com.jb.couponsproject.utils.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final JWT jwt;
    private final LoginManager loginManager;
    private CompanyServiceImpl companyService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ClientDetails clientDetails) throws LoginException {
        if (!clientDetails.getClientType().toString().equals("COMPANY")) {
            throw new LoginException("Invalid user type");
        }
        companyService = (CompanyServiceImpl) loginManager.login(clientDetails);
        return new ResponseEntity<>(jwt.generateToken(clientDetails), HttpStatus.ACCEPTED);
    }

    @PostMapping("/coupon/add")
    public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws TokenException, LoginException {
        jwt.checkUser(token, ClientType.COMPANY);

        companyService.addCoupon(coupon);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body("Coupon added Successfully");
    }

    @PutMapping("/coupon/update")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon)
            throws TokenException, LoginException, NotExistException {
        jwt.checkUser(token, ClientType.COMPANY);

        companyService.updateCoupon(coupon);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body("Coupon #" + coupon.getId() + " updated Successfully");
    }

    @DeleteMapping("/coupon/delete/{couponID}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable int couponID)
            throws CouponException, NotExistException, TokenException, LoginException {
        jwt.checkUser(token, ClientType.COMPANY);

        companyService.deleteCoupon(couponID);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body("Coupon #" + couponID + " deleted Successfully");
    }

    @GetMapping("/coupon/get/all")
    public ResponseEntity<?> getAllCompanyCoupons(@RequestHeader(name = "Authorization") String token) throws NotExistException, TokenException, LoginException {
        jwt.checkUser(token, ClientType.COMPANY);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body(companyService.getAllCompanyCoupons());
    }

    @GetMapping("/coupon/get/category")
    public ResponseEntity<?> getAllCompanyCouponsByCategory(@RequestHeader(name = "Authorization") String token, @RequestHeader Categories category)
            throws NotExistException, TokenException, LoginException {
        jwt.checkUser(token, ClientType.COMPANY);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body(companyService.getAllCompanyCouponsByCategory(category));
    }

    @GetMapping("/coupon/get/maxPrice")
    public ResponseEntity<?> getAllCompanyCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @RequestHeader double maxPrice)
            throws NotExistException, TokenException, LoginException {
        jwt.checkUser(token, ClientType.COMPANY);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body(companyService.getAllCompanyCouponsByMaxPrice(maxPrice));
    }

    @GetMapping("/details")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token)
            throws TokenException, LoginException {
        jwt.checkUser(token, ClientType.COMPANY);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body(companyService.getCompanyDetails());
    }

}
