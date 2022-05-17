package com.jb.couponsproject.services.serviceImpl;

import com.jb.couponsproject.beans.Company;
import com.jb.couponsproject.beans.Customer;
import com.jb.couponsproject.exceptions.NotExistException;
import com.jb.couponsproject.services.ClientService;
import com.jb.couponsproject.services.serviceDAO.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ClientService implements AdminService {

    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    @Override
    public void addCompany(Company company) {
        companyRepo.save(company);
    }

    @Override
    public void updateCompany(Company company) throws NotExistException {
        if (!companyRepo.existsByIdAndName(company.getId(), company.getName())) {
            throw new NotExistException("Company not found");
        }
        companyRepo.save(company);
    }

    @Override
    public void deleteCompany(int companyID) throws NotExistException {
        if (!companyRepo.existsById(companyID)) {
            throw new NotExistException("Company ID not found");
        }
        companyRepo.deleteById(companyID);
    }

    @Override
    public List<Company> getAllCompanies() throws NotExistException {
        List<Company> companies = companyRepo.findAll();
        if (companies.isEmpty()) {
            throw new NotExistException("No companies found");
        }
        return companies;
    }

    @Override
    public Company getCompanyByID(int companyID) throws NotExistException {
        Optional<Company> optionalCompany = companyRepo.findById(companyID);
        if (optionalCompany.isEmpty()) {
            throw new NotExistException("Company ID not found");
        }
        return optionalCompany.get();
    }

    @Override
    public void addCustomer(Customer customer) {
        customerRepo.save(customer);
    }

    @Override
    public void updateCustomer(Customer customer) throws NotExistException {
        if (!customerRepo.existsById(customer.getId())) {
            throw new NotExistException("Customer ID not found");
        }
        customerRepo.save(customer);
    }

    @Override
    public void deleteCustomer(int customerID) throws NotExistException {
        if (!customerRepo.existsById(customerID)) {
            throw new NotExistException("Customer ID not found");
        }
        customerRepo.deleteById(customerID);
    }

    @Override
    public List<Customer> getAllCustomers() throws NotExistException {
        List<Customer> customers = customerRepo.findAll();
        if (customers.isEmpty()) {
            throw new NotExistException("No customers found");
        }
        return customers;
    }

    @Override
    public Customer getCustomerByID(int customerID) throws NotExistException {
        Optional<Customer> optionalCustomer = customerRepo.findById(customerID);
        if (optionalCustomer.isEmpty()) {
            throw new NotExistException("Customer ID not found");
        }
        return optionalCustomer.get();
    }

}
