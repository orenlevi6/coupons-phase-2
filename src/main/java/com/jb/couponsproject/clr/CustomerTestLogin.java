package com.jb.couponsproject.clr;

import com.jb.couponsproject.beans.Categories;
import com.jb.couponsproject.login.ClientDetails;
import com.jb.couponsproject.login.ClientType;
import com.jb.couponsproject.login.LoginManager;
import com.jb.couponsproject.repositories.CouponRepo;
import com.jb.couponsproject.services.serviceImpl.CustomerServiceImpl;
import com.jb.couponsproject.utils.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(5)
public class CustomerTestLogin implements CommandLineRunner {
    private final LoginManager loginManager;

    private final CouponRepo couponRepo;

    @Override
    public void run(String... args) throws Exception {
        CustomerServiceImpl customerService = (CustomerServiceImpl) loginManager.login(
                new ClientDetails("orenlevi6@gmail.com", "orenOren", ClientType.CUSTOMER));

        System.out.println("Purchase coupon");
        customerService.purchaseCoupon(couponRepo.findById(1).get());
        TablePrinter.print(customerService.getAllCustomerCoupons());

        System.out.println("Get coupons by category - FOOD");
        TablePrinter.print(customerService.getAllCustomerCouponsByCategory(Categories.FOOD));

        System.out.println("Get coupons by max price - 50");
        TablePrinter.print(customerService.getAllCustomerCouponsByMaxPrice(50));

        System.out.println("Get customer #1 details");
//        TablePrinter.print(customerService.getCustomerDetails()); doesn't work - failed to lazily initialize
        System.out.println(customerService.getCustomerDetails());
    }

}
