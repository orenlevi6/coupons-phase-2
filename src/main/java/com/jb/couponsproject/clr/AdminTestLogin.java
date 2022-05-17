package com.jb.couponsproject.clr;

import com.jb.couponsproject.beans.Categories;
import com.jb.couponsproject.beans.Category;
import com.jb.couponsproject.beans.Company;
import com.jb.couponsproject.beans.Customer;
import com.jb.couponsproject.login.ClientDetails;
import com.jb.couponsproject.login.ClientType;
import com.jb.couponsproject.login.LoginManager;
import com.jb.couponsproject.repositories.CategoryRepo;
import com.jb.couponsproject.services.serviceImpl.AdminServiceImpl;
import com.jb.couponsproject.utils.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(3)
public class AdminTestLogin implements CommandLineRunner {
    private final CategoryRepo categoryRepo;
    private final LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {
        addCategories();

        System.out.println("Admin Test \n");
        AdminServiceImpl admin = (AdminServiceImpl) loginManager.login(
                new ClientDetails("admin@admin.com", "admin", ClientType.ADMIN));

        companiesTest(admin);
        customersTest(admin);
    }

    private void addCategories() {
        categoryRepo.save(Category.builder().title(Categories.FOOD).build());
        categoryRepo.save(Category.builder().title(Categories.ELECTRICITY).build());
        categoryRepo.save(Category.builder().title(Categories.RESTAURANT).build());
        categoryRepo.save(Category.builder().title(Categories.VACATION).build());
    }

    private void companiesTest(AdminServiceImpl admin) throws Exception {
        admin.addCompany(Company.builder()
                .name("Oren INC")
                .email("no-reply@oren.inc")
                .password("orenInc")
                .build());

        admin.addCompany(Company.builder()
                .name("Lea INC")
                .email("no-reply@lea.inc")
                .password("leasInc")
                .build());

        admin.addCompany(Company.builder()
                .name("Tal INC")
                .email("no-reply@tal.inc")
                .password("talbInc")
                .build());

        System.out.println("Adding companies");
        TablePrinter.print(admin.getAllCompanies());

        System.out.println("Deleting company");
        admin.deleteCompany(3);
        TablePrinter.print(admin.getAllCompanies());

        System.out.println("Updating company");
        Company company = admin.getCompanyByID(2);
        company.setEmail("noreply@lea.inc");
        admin.updateCompany(company);
        TablePrinter.print(admin.getAllCompanies());
    }

    private void customersTest(AdminServiceImpl admin) throws Exception {
        admin.addCustomer(Customer.builder()
                .firstName("Oren")
                .lastName("Levi")
                .email("orenlevi6@gmail.com")
                .password("orenOren")
                .build());

        admin.addCustomer(Customer.builder()
                .firstName("Lea")
                .lastName("Sad")
                .email("leasad@gmail.com")
                .password("leaLea")
                .build());

        admin.addCustomer(Customer.builder()
                .firstName("Tal")
                .lastName("Bechor")
                .email("talb@gmail.com")
                .password("talTal")
                .build());

        System.out.println("Adding customers");
        TablePrinter.print(admin.getAllCustomers());

        System.out.println("Deleting customer");
        admin.deleteCustomer(3);
        TablePrinter.print(admin.getAllCustomers());

        System.out.println("Updating customer");
        Customer customer = admin.getCustomerByID(2);
        customer.setEmail("leas@gmail.com");
        admin.updateCustomer(customer);
        TablePrinter.print(admin.getAllCustomers());
    }


}
