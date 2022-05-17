package com.jb.couponsproject.services.serviceDAO;

import com.jb.couponsproject.beans.Categories;
import com.jb.couponsproject.beans.Coupon;
import com.jb.couponsproject.beans.Customer;
import com.jb.couponsproject.exceptions.CouponException;
import com.jb.couponsproject.exceptions.NotExistException;

import java.util.List;

public interface CustomerService {

    void purchaseCoupon(int couponID) throws NotExistException, CouponException;

    List<Coupon> getAllCustomerCoupons() throws NotExistException;

    List<Coupon> getAllCustomerCoupons(Categories category) throws NotExistException;

    List<Coupon> getAllCustomerCoupons(double maxPrice) throws NotExistException;

    Customer getCustomerDetails();
}
