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
    public void purchaseCoupon(Coupon coupon) throws CouponException {
        if (couponRepo.countCouponPurchase(this.customerID, coupon.getId()) > 0) {
            throw new CouponException("Customer already has this coupon");
        }
        if (coupon.getAmount() <= 0) {
            throw new CouponException("Coupon has ran out of stock");
        }
        if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new CouponException("Coupon has expired");
        }
        couponRepo.addCouponPurchase(this.customerID, coupon.getId());
        couponRepo.decreaseCouponAmount(coupon.getId());
    }

    @Override
    public List<Coupon> getAllCustomerCoupons() throws NotExistException {
        List<Coupon> customerCoupons = couponRepo.findAllCustomerCoupons(this.customerID);
        if (customerCoupons.isEmpty()) {
            throw new NotExistException("No coupons found");
        }
        return customerCoupons;
    }

    @Override
    public List<Coupon> getAllCustomerCouponsByCategory(Categories category) throws NotExistException {
        List<Coupon> customerCouponsByCategory = couponRepo.findAllCustomerCouponsByCategory(this.customerID, category.getValue());
        if (customerCouponsByCategory.isEmpty()) {
            throw new NotExistException("No coupons found");
        }
        return customerCouponsByCategory;
    }

    @Override
    public List<Coupon> getAllCustomerCouponsByMaxPrice(double maxPrice) throws NotExistException {
        List<Coupon> customerCouponsByMaxPrice = couponRepo.findAllCustomerCouponsByMaxPrice(this.customerID, maxPrice);
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
