package com.jb.couponsproject.clr;

import com.jb.couponsproject.beans.Categories;
import com.jb.couponsproject.beans.Category;
import com.jb.couponsproject.beans.Company;
import com.jb.couponsproject.beans.Customer;
import com.jb.couponsproject.repositories.CategoryRepo;
import com.jb.couponsproject.services.serviceImpl.AdminServiceImpl;
import com.jb.couponsproject.utils.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(2)
public class AdminTestNoLogin implements CommandLineRunner {
    private final CategoryRepo categoryRepo;
    private final AdminServiceImpl adminService;

    @Override
    public void run(String... args) throws Exception {
        addCategories();
        addCompanies();
        addCustomers();

        TablePrinter.print(categoryRepo.findAll());
        TablePrinter.print(adminService.getAllCompanies());
//        TablePrinter.print(adminService.getAllCustomers()); doesn't work - failed to lazily initialize
        adminService.getAllCustomers().forEach(System.out::println);
    }

    private void addCategories() {
        categoryRepo.save(Category.builder().title(Categories.FOOD).build());
        categoryRepo.save(Category.builder().title(Categories.ELECTRICITY).build());
        categoryRepo.save(Category.builder().title(Categories.RESTAURANT).build());
        categoryRepo.save(Category.builder().title(Categories.VACATION).build());
    }

    private void addCompanies() {
        adminService.addCompany(Company.builder()
                .name("Oren INC")
                .email("no-reply@oren.inc")
                .password("orenInc")
                .build());

        adminService.addCompany(Company.builder()
                .name("Lea INC")
                .email("no-reply@lea.inc")
                .password("leasInc")
                .build());

        adminService.addCompany(Company.builder()
                .name("Tal INC")
                .email("no-reply@tal.inc")
                .password("talbInc")
                .build());
    }

    private void addCustomers() {
        adminService.addCustomer(Customer.builder()
                .firstName("Oren")
                .lastName("Levi")
                .email("orenlevi@gmail.com")
                .password("orenOren")
                .build());

        adminService.addCustomer(Customer.builder()
                .firstName("Lea")
                .lastName("Sadikov")
                .email("leasad@gmail.com")
                .password("leaLea")
                .build());

        adminService.addCustomer(Customer.builder()
                .firstName("Tal")
                .lastName("Bechor")
                .email("talbe@gmail.com")
                .password("talTal")
                .build());
    }

}
