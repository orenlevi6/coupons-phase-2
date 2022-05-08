package com.jb.couponsproject.services;

import com.jb.couponsproject.beans.Company;
import com.jb.couponsproject.beans.Customer;
import com.jb.couponsproject.exceptions.NotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService extends ClientService {

    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    public void addCompany(Company company) {
        companyRepo.save(company);
    }

    public void updateCompany(Company company) throws NotExistException {
        if (!companyRepo.existsByIdAndName(company.getId(), company.getName())) {
            throw new NotExistException("Company not found");
        }
        companyRepo.save(company);

    }

    public void deleteCompany(int companyID) throws NotExistException {
        if (!companyRepo.existsById(companyID)) {
            throw new NotExistException("Company ID not found");
        }
        companyRepo.deleteById(companyID);
    }

    public List<Company> getAllCompanies() throws NotExistException {
        List<Company> companies = companyRepo.findAll();
        if (companies.isEmpty()) {
            throw new NotExistException("No companies found");
        }
        return companies;
    }

    public Company getCompanyByID(int companyID) throws NotExistException {
        Optional<Company> optionalCompany = companyRepo.findById(companyID);
        if (optionalCompany.isEmpty()) {
            throw new NotExistException("Company ID not found");
        }
        return optionalCompany.get();
    }

    public void addCustomer(Customer customer) {
        customerRepo.save(customer);
    }

    public void updateCustomer(Customer customer) throws NotExistException {
        if (!customerRepo.existsById(customer.getId())) {
            throw new NotExistException("Customer ID not found");
        }
        customerRepo.save(customer);
    }

    public void deleteCustomer(int customerID) throws NotExistException {
        if (!customerRepo.existsById(customerID)) {
            throw new NotExistException("Customer ID not found");
        }
//        couponRepo.deleteCouponPurchaseByCustomerID(customerID);
        customerRepo.deleteById(customerID);
    }

    public List<Customer> getAllCustomers() throws NotExistException {
        List<Customer> customers = customerRepo.findAll();
        if (customers.isEmpty()) {
            throw new NotExistException("No customers found");
        }
        return customers;
    }

    public Customer getCustomerByID(int customerID) throws NotExistException {
        Optional<Customer> optionalCustomer = customerRepo.findById(customerID);
        if (optionalCustomer.isEmpty()) {
            throw new NotExistException("Customer ID not found");
        }
        return optionalCustomer.get();
    }

}
