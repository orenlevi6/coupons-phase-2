package com.jb.couponsproject.services.serviceDAO;

import com.jb.couponsproject.beans.Company;
import com.jb.couponsproject.beans.Customer;
import com.jb.couponsproject.exceptions.NotExistException;

import java.util.List;

public interface AdminService {

    void addCompany(Company company);

    void updateCompany(Company company) throws NotExistException;

    void deleteCompany(int companyID) throws NotExistException;

    List<Company> getAllCompanies() throws NotExistException;

    Company getCompanyByID(int companyID) throws NotExistException;

    void addCustomer(Customer customer);

    void updateCustomer(Customer customer) throws NotExistException;

    void deleteCustomer(int customerID) throws NotExistException;

    List<Customer> getAllCustomers() throws NotExistException;

    Customer getCustomerByID(int customerID) throws NotExistException;

}
