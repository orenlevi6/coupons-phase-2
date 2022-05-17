package com.jb.couponsproject.services.serviceDAO;

import com.jb.couponsproject.beans.Categories;
import com.jb.couponsproject.beans.Company;
import com.jb.couponsproject.beans.Coupon;
import com.jb.couponsproject.exceptions.CouponException;
import com.jb.couponsproject.exceptions.NotExistException;

import java.util.List;

public interface CompanyService {

    void addCoupon(Coupon coupon);

    void updateCoupon(Coupon coupon) throws NotExistException;

    void deleteCoupon(int couponID) throws NotExistException, CouponException;

    List<Coupon> getAllCompanyCoupons() throws NotExistException;

    List<Coupon> getAllCompanyCoupons(Categories category) throws NotExistException;

    List<Coupon> getAllCompanyCoupons(double maxPrice) throws NotExistException;

    Company getCompanyDetails();

}
