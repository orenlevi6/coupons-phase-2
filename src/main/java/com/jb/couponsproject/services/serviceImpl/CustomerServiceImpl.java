package com.jb.couponsproject.services.serviceImpl;

import com.jb.couponsproject.beans.Categories;
import com.jb.couponsproject.beans.Coupon;
import com.jb.couponsproject.beans.Customer;
import com.jb.couponsproject.exceptions.AlreadyExistsException;
import com.jb.couponsproject.exceptions.ExceptionType;
import com.jb.couponsproject.exceptions.NotExistException;
import com.jb.couponsproject.services.ClientService;
import com.jb.couponsproject.services.serviceDAO.CustomerService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl extends ClientService implements CustomerService {
    private int customerID;

    /**
     * Performs a login check for a customer
     *
     * @param email    Login email
     * @param password Login password
     * @return boolean that determines if the login was successful, while updating the local variable customerID
     */
    @Override
    public boolean login(String email, String password) {
        Optional<Customer> optionalCustomer = customerRepo.findByEmailAndPassword(email, password);
        if (optionalCustomer.isPresent()) {
            this.customerID = optionalCustomer.get().getId();
            return true;
        }
        return false;
    }

    /**
     * Checks if a coupon is valid for purchase and purchases it for the customer
     *
     * @param couponID ID of a coupon to purchase
     * @throws NotExistException      If the coupon does not exist in the database, has an amount of 0 or is expired
     * @throws AlreadyExistsException If the coupon was already purchased by the customer
     */
    @Override
    public void purchaseCoupon(int couponID) throws NotExistException, AlreadyExistsException {
        Optional<Coupon> optionalCoupon = couponRepo.findById(couponID);

        if (optionalCoupon.isEmpty()) {
            throw new NotExistException(ExceptionType.COUPON);
        }
        if (couponRepo.countCouponPurchase(this.customerID, couponID) > 0) {
            throw new AlreadyExistsException(ExceptionType.COUPON);
        }
        if (optionalCoupon.get().getAmount() == 0) {
            throw new NotExistException(ExceptionType.COUPON);
        }
        if (optionalCoupon.get().getEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new NotExistException(ExceptionType.COUPON);
        }
        couponRepo.addCouponPurchase(this.customerID, couponID);
        couponRepo.decreaseCouponAmount(couponID);
    }

    /**
     * Gets all coupons of a customer from the database by customer's ID
     *
     * @return A list of coupons that belongs to a customer by ID
     * @throws NotExistException If no coupons were found
     */
    @Override
    public List<Coupon> getAllCustomerCoupons() throws NotExistException {
        List<Coupon> customerCoupons = couponRepo.findAllCustomerCoupons(this.customerID);
        if (customerCoupons.isEmpty()) {
            throw new NotExistException(ExceptionType.COUPON);
        }
        return customerCoupons;
    }

    /**
     * Gets all coupons of a customer from the database by customer's ID and a category
     *
     * @param category The category of the coupons which will be retrieved from the database
     * @return A list of all the coupons of this customer with the given category that exists in the database
     * @throws NotExistException If no coupons were found
     */
    @Override
    public List<Coupon> getAllCustomerCouponsByCategory(Categories category) throws NotExistException {
        List<Coupon> customerCouponsByCategory = couponRepo.findAllCustomerCouponsByCategory(this.customerID, category.getValue());
        if (customerCouponsByCategory.isEmpty()) {
            throw new NotExistException(ExceptionType.COUPON);
        }
        return customerCouponsByCategory;
    }

    /**
     * Gets all coupons of a customer from the database by customer's ID and a price limit
     *
     * @param maxPrice The price limit of the coupons which will be retrieved from the database
     * @return A list of all the coupons of this customer with the given price limit that exists in the database
     * @throws NotExistException If no coupons were found
     */
    @Override
    public List<Coupon> getAllCustomerCouponsByMaxPrice(double maxPrice) throws NotExistException {
        List<Coupon> customerCouponsByMaxPrice = couponRepo.findAllCustomerCouponsByMaxPrice(this.customerID, maxPrice);
        if (customerCouponsByMaxPrice.isEmpty()) {
            throw new NotExistException(ExceptionType.COUPON);
        }
        return customerCouponsByMaxPrice;
    }

    /**
     * Gets the customer's details from the database
     *
     * @return The details of this customer, that will be retrieved from the database
     */
    @Override
    public Customer getCustomerDetails() {
        return customerRepo.findById(this.customerID).get();
    }

}
