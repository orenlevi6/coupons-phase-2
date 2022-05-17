package com.jb.couponsproject.clr;

import com.jb.couponsproject.login.ClientDetails;
import com.jb.couponsproject.login.ClientType;
import com.jb.couponsproject.login.LoginManager;
import com.jb.couponsproject.services.serviceImpl.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(6)
public class DeleteTest implements CommandLineRunner {
    private final LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {
        AdminServiceImpl admin = (AdminServiceImpl) loginManager.login(
                new ClientDetails("admin@admin.com", "admin", ClientType.ADMIN));

        System.out.println("Deleting customer #1");
        admin.deleteCustomer(1);

        System.out.println("Deleting company #1");
        admin.deleteCompany(1);

//        CompanyServiceImpl company = (CompanyServiceImpl) loginManager.login(
//                new ClientDetails("no-reply@oren.inc", "orenInc", ClientType.COMPANY));
//
//        System.out.println("Deleting coupon #1 for company #1");
//        company.deleteCoupon(1);
    }

}
