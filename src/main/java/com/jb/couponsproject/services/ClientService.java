package com.jb.couponsproject.services;

import com.jb.couponsproject.repositories.CategoryRepo;
import com.jb.couponsproject.repositories.CompanyRepo;
import com.jb.couponsproject.repositories.CouponRepo;
import com.jb.couponsproject.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClientService {
    @Autowired
    protected CategoryRepo categoryRepo;

    @Autowired
    protected CompanyRepo companyRepo;

    @Autowired
    protected CustomerRepo customerRepo;

    @Autowired
    protected CouponRepo couponRepo;

    public abstract boolean login(String email, String password);
}
