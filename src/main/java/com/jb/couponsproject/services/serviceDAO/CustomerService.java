package com.jb.couponsproject.services.serviceDAO;

import com.jb.couponsproject.beans.Categories;
import com.jb.couponsproject.beans.Coupon;
import com.jb.couponsproject.beans.Customer;
import com.jb.couponsproject.exceptions.CouponException;
import com.jb.couponsproject.exceptions.NotExistException;

import java.util.List;

public interface CustomerService {

    void purchaseCoupon(Coupon coupon) throws CouponException;

    List<Coupon> getAllCustomerCoupons() throws NotExistException;

    List<Coupon> getAllCustomerCouponsByCategory(Categories category) throws NotExistException;

    List<Coupon> getAllCustomerCouponsByMaxPrice(double maxPrice) throws NotExistException;

    Customer getCustomerDetails();
}
