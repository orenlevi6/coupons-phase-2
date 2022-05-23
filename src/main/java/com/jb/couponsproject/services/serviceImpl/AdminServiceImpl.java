package com.jb.couponsproject.services.serviceImpl;

import com.jb.couponsproject.beans.Company;
import com.jb.couponsproject.beans.Customer;
import com.jb.couponsproject.exceptions.ExceptionType;
import com.jb.couponsproject.exceptions.NotExistException;
import com.jb.couponsproject.services.ClientService;
import com.jb.couponsproject.services.serviceDAO.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl extends ClientService implements AdminService {
    /**
     * Performs a login check for admin
     *
     * @param email    Login email
     * @param password Login password
     * @return boolean that determines if the login was successful
     */
    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    /**
     * Adds a company to the database
     *
     * @param company Full company instance that will be added to the database
     */
    @Override
    public void addCompany(Company company) {
        companyRepo.save(company);
    }

    /**
     * Updates a company in the database
     *
     * @param company Full company instance that updated be updated in the database
     * @throws NotExistException If the company's ID and name does not exist in the database
     */
    @Override
    public void updateCompany(Company company) throws NotExistException {
        if (!companyRepo.existsByIdAndName(company.getId(), company.getName())) {
            throw new NotExistException(ExceptionType.COMPANY);
        }
        companyRepo.save(company);
    }

    /**
     * Deletes a company from the database
     *
     * @param companyID The ID of the company that will be deleted
     * @throws NotExistException If the company's ID does not exist in the database
     */
    @Override
    public void deleteCompany(int companyID) throws NotExistException {
        if (!companyRepo.existsById(companyID)) {
            throw new NotExistException(ExceptionType.COMPANY);
        }
        couponRepo.deleteCompanyCoupons(companyID);
        companyRepo.deleteById(companyID);
    }

    /**
     * Gets all companies from the database
     *
     * @return A list of all the companies that exist in the database
     * @throws NotExistException If no companies exist in the database
     */
    @Override
    public List<Company> getAllCompanies() throws NotExistException {
        List<Company> companies = companyRepo.findAll();
        if (companies.isEmpty()) {
            throw new NotExistException(ExceptionType.COMPANY);
        }
        return companies;
    }

    /**
     * Gets a single company from the database by ID
     *
     * @param companyID The ID of the company that will be retrieved from the database
     * @return The details of the company that will be retrieved from the database
     * @throws NotExistException If the company's ID does not exist in the database
     */
    @Override
    public Company getCompanyByID(int companyID) throws NotExistException {
        return companyRepo.findById(companyID)
                .orElseThrow(() -> new NotExistException(ExceptionType.COMPANY));
    }

    /**
     * Adds a customer to the database
     *
     * @param customer Full customer instance that will be added to the database
     */
    @Override
    public void addCustomer(Customer customer) {
        customerRepo.save(customer);
    }

    /**
     * Updates a customer in the database
     *
     * @param customer Full customer instance that will be updated in the database
     * @throws NotExistException If the customer's ID does not exist in the database
     */
    @Override
    public void updateCustomer(Customer customer) throws NotExistException {
        customerRepo.findById(customer.getId()).orElseThrow(() -> new NotExistException(ExceptionType.CUSTOMER));
        customerRepo.save(customer);
    }

    /**
     * Deletes a customer from the database
     *
     * @param customerID The ID of the customer that will be deleted
     * @throws NotExistException If the customer's ID does not exist in the database
     */
    @Override
    public void deleteCustomer(int customerID) throws NotExistException {
        if (!customerRepo.existsById(customerID)) {
            throw new NotExistException(ExceptionType.CUSTOMER);
        }
        couponRepo.deleteCouponPurchaseByCustomerID(customerID);
        customerRepo.deleteById(customerID);
    }

    /**
     * Gets all customers from the database
     *
     * @return A list of all the customers that exist in the database
     * @throws NotExistException If no customers exist in the database
     */
    @Override
    public List<Customer> getAllCustomers() throws NotExistException {
        List<Customer> customers = customerRepo.findAll();
        if (customers.isEmpty()) {
            throw new NotExistException(ExceptionType.CUSTOMER);
        }
        return customers;
    }

    /**
     * Gets a single customer from the database by ID
     *
     * @param customerID The ID of the customer that will be retrieved from the database
     * @return The details of the customer that will be retrieved from the database
     * @throws NotExistException If the customer's ID does not exist in the database
     */
    @Override
    public Customer getCustomerByID(int customerID) throws NotExistException {
        return customerRepo.findById(customerID)
                .orElseThrow(() -> new NotExistException(ExceptionType.CUSTOMER));
    }

}
