package com.jb.couponsproject.services.serviceImpl;

import com.jb.couponsproject.beans.Categories;
import com.jb.couponsproject.beans.Coupon;
import com.jb.couponsproject.beans.Customer;
import com.jb.couponsproject.exceptions.CouponException;
import com.jb.couponsproject.exceptions.NotExistException;
import com.jb.couponsproject.services.ClientService;
import com.jb.couponsproject.services.serviceDAO.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends ClientService implements CustomerService {
    private int customerID;

    @Override
    public boolean login(String email, String password) {
        Optional<Customer> optionalCustomer = customerRepo.findByEmailAndPassword(email, password);
        if (optionalCustomer.isPresent()) {
            this.customerID = optionalCustomer.get().getId();
            return true;
        }
        return false;
    }

    @Override
    public void purchaseCoupon(int couponID) throws NotExistException, CouponException {
        Optional<Coupon> optionalCoupon = couponRepo.findById(couponID);
        if (optionalCoupon.isEmpty()) {
            throw new NotExistException("Coupon ID not found");
        }
        if (couponRepo.countCouponPurchase(this.customerID, couponID) > 0) {
            throw new CouponException("Customer already has this coupon");
        }
        if (optionalCoupon.get().getAmount() <= 0) {
            throw new CouponException("Coupon has ran out of stock");
        }
        if (optionalCoupon.get().getEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new CouponException("This coupon is expired");
        }
        couponRepo.addCouponPurchase(this.customerID, couponID);
        couponRepo.decreaseCouponAmount(couponID);
    }

    @Override
    public List<Coupon> getAllCustomerCoupons() throws NotExistException {
        List<Coupon> customerCoupons = couponRepo.getAllCustomerCoupons(this.customerID);
        if (customerCoupons.isEmpty()) {
            throw new NotExistException("No coupons found");
        }
        return customerCoupons;
    }

    @Override
    public List<Coupon> getAllCustomerCoupons(Categories category) throws NotExistException {
        List<Coupon> customerCouponsByCategory = couponRepo.getAllCustomerCouponsByCategory(this.customerID, category.getValue());
        if (customerCouponsByCategory.isEmpty()) {
            throw new NotExistException("No coupons found");
        }
        return customerCouponsByCategory;
    }

    @Override
    public List<Coupon> getAllCustomerCoupons(double maxPrice) throws NotExistException {
        List<Coupon> customerCouponsByMaxPrice = couponRepo.getAllCustomerCouponsByMaxPrice(this.customerID, maxPrice);
        if (customerCouponsByMaxPrice.isEmpty()) {
            throw new NotExistException("No coupons found");
        }
        return customerCouponsByMaxPrice;
    }

    @Override
    public Customer getCustomerDetails() {
        return customerRepo.findById(this.customerID).get();
    }

}
