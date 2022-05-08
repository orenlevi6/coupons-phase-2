package com.jb.couponsproject.repositories;

import com.jb.couponsproject.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

}
