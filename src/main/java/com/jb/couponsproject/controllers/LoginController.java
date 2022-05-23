package com.jb.couponsproject.controllers;

import com.jb.couponsproject.beans.ClientDetails;
import com.jb.couponsproject.exceptions.LoginException;
import com.jb.couponsproject.services.LoginService;
import com.jb.couponsproject.services.serviceImpl.AdminServiceImpl;
import com.jb.couponsproject.services.serviceImpl.CompanyServiceImpl;
import com.jb.couponsproject.services.serviceImpl.CustomerServiceImpl;
import com.jb.couponsproject.utils.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final JWT jwt;
    private final LoginService loginService;
    private final AdminServiceImpl adminService;
    private final CompanyServiceImpl companyService;
    private final CustomerServiceImpl customerService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody ClientDetails clientDetails) throws LoginException {
        if (loginService.login(clientDetails) == null) {
            throw new LoginException(clientDetails.getClientType());
        }
        return new ResponseEntity<>(jwt.generateToken(clientDetails), HttpStatus.OK);
    }

}
