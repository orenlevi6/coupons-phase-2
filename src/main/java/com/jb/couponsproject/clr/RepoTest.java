package com.jb.couponsproject.clr;

import com.jb.couponsproject.beans.*;
import com.jb.couponsproject.repositories.CategoryRepo;
import com.jb.couponsproject.repositories.CompanyRepo;
import com.jb.couponsproject.repositories.CouponRepo;
import com.jb.couponsproject.repositories.CustomerRepo;
import com.jb.couponsproject.utils.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Order(1)
public class RepoTest implements CommandLineRunner {
    private final CategoryRepo categoryRepo;
    private final CompanyRepo companyRepo;
    private final CustomerRepo customerRepo;
    private final CouponRepo couponRepo;

    @Override
    public void run(String... args) throws Exception {
        addCategories();
        addCompanies();
        addCustomers();
        addCoupons();

        TablePrinter.print(categoryRepo.findAll());
        TablePrinter.print(companyRepo.findAll());
//        TablePrinter.print(customerRepo.findAll()); //Does Not Work - 'failed to lazily initialize'
        TablePrinter.print(couponRepo.findAll());
    }

    private void addCategories() {
        categoryRepo.save(Category.builder().title(Categories.FOOD).build());
        categoryRepo.save(Category.builder().title(Categories.ELECTRICITY).build());
        categoryRepo.save(Category.builder().title(Categories.RESTAURANT).build());
        categoryRepo.save(Category.builder().title(Categories.VACATION).build());
    }

    private void addCompanies() {
        companyRepo.save(Company.builder()
                .name("Oren INC")
                .email("no-reply@oren.inc")
                .password("orenInc")
                .build());

        companyRepo.save(Company.builder()
                .name("Lea INC")
                .email("no-reply@lea.inc")
                .password("leasInc")
                .build());

        companyRepo.save(Company.builder()
                .name("Tal INC")
                .email("no-reply@tal.inc")
                .password("talbInc")
                .build());
    }

    private void addCustomers() {
        customerRepo.save(Customer.builder()
                .firstName("Oren")
                .lastName("Levi")
                .email("orenlevi@gmail.com")
                .password("orenOren")
                .build());

        customerRepo.save(Customer.builder()
                .firstName("Lea")
                .lastName("Sadikov")
                .email("leasad@gmail.com")
                .password("leaLea")
                .build());

        customerRepo.save(Customer.builder()
                .firstName("Tal")
                .lastName("Bechor")
                .email("talbe@gmail.com")
                .password("talTal")
                .build());
    }

    private void addCoupons() {
        couponRepo.save(Coupon.builder()
                .company(companyRepo.getById(1))
                .category(categoryRepo.getById(1))
                .title("Mcdonald's")
                .description("bla bla")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(7)))
                .amount(10)
                .price(19.90)
                .image("M")
                .build());

        couponRepo.save(Coupon.builder()
                .company(companyRepo.getById(1))
                .category(categoryRepo.getById(2))
                .title("Oven Discount")
                .description("bla bla")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(7)))
                .amount(10)
                .price(29.90)
                .image("M")
                .build());

        couponRepo.save(Coupon.builder()
                .company(companyRepo.getById(2))
                .category(categoryRepo.getById(1))
                .title("Burger King")
                .description("bla bla")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(7)))
                .amount(10)
                .price(39.90)
                .image("M")
                .build());
    }

}
