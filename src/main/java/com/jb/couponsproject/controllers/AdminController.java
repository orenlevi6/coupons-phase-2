package com.jb.couponsproject.controllers;

import com.jb.couponsproject.beans.Company;
import com.jb.couponsproject.beans.Customer;
import com.jb.couponsproject.exceptions.LoginException;
import com.jb.couponsproject.exceptions.NotExistException;
import com.jb.couponsproject.exceptions.TokenException;
import com.jb.couponsproject.login.ClientDetails;
import com.jb.couponsproject.login.ClientType;
import com.jb.couponsproject.login.LoginManager;
import com.jb.couponsproject.services.serviceImpl.AdminServiceImpl;
import com.jb.couponsproject.utils.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final JWT jwt;
    private final LoginManager loginManager;
    private AdminServiceImpl adminService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ClientDetails clientDetails) throws LoginException {
        if (!clientDetails.getClientType().toString().equals("ADMIN")) {
            throw new LoginException("Invalid user type");
        }
        adminService = (AdminServiceImpl) loginManager.login(clientDetails);
        return new ResponseEntity<>(jwt.generateToken(clientDetails), HttpStatus.ACCEPTED);
    }

    @PostMapping("/company/add")
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws TokenException, LoginException {
        jwt.checkUser(token, ClientType.ADMIN);

        adminService.addCompany(company);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body("Company added successfully");
    }

    @PutMapping("/company/update")
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws TokenException, LoginException, NotExistException {
        jwt.checkUser(token, ClientType.ADMIN);

        adminService.updateCompany(company);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body("Company #" + company.getId() + " updated successfully");
    }

    @DeleteMapping("/company/delete/{companyID}")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int companyID) throws NotExistException, TokenException, LoginException {
        jwt.checkUser(token, ClientType.ADMIN);

        adminService.deleteCompany(companyID);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body("Company #" + companyID + " deleted successfully");
    }

    @GetMapping("/company/get/all")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) throws TokenException, LoginException, NotExistException {
        jwt.checkUser(token, ClientType.ADMIN);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body(adminService.getAllCompanies());
    }

    @GetMapping("/company/get/{companyID}")
    public ResponseEntity<?> getCompanyByID(@RequestHeader(name = "Authorization") String token, @PathVariable int companyID) throws NotExistException, TokenException, LoginException {
        jwt.checkUser(token, ClientType.ADMIN);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body(adminService.getCompanyByID(companyID));
    }

    @PostMapping("/customer/add")
    public ResponseEntity<?> addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer)
            throws TokenException, LoginException {
        jwt.checkUser(token, ClientType.ADMIN);

        adminService.addCustomer(customer);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body("Customer added successfully");
    }

    @PutMapping("/customer/update")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws NotExistException, TokenException, LoginException {
        jwt.checkUser(token, ClientType.ADMIN);

        adminService.updateCustomer(customer);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body("Customer #" + customer.getId() + " updated successfully");
    }

    @DeleteMapping("/customer/delete/{customerID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int customerID) throws NotExistException, TokenException, LoginException {

        jwt.checkUser(token, ClientType.ADMIN);

        adminService.deleteCustomer(customerID);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body("Customer #" + customerID + " updated successfully");
    }

    @GetMapping("/customer/get/all")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String token) throws NotExistException, TokenException, LoginException {
        jwt.checkUser(token, ClientType.ADMIN);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token.replace("Bearer ", "")))
                .body(adminService.getAllCustomers());
    }

    @GetMapping("/customer/get/{customerID}")
    public ResponseEntity<?> getCustomerByID(@RequestHeader(name = "Authorization") String token, @PathVariable int customerID) throws NotExistException, TokenException, LoginException {
        jwt.checkUser(token, ClientType.ADMIN);

        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(token))
                .body(adminService.getCustomerByID(customerID));
    }

}
