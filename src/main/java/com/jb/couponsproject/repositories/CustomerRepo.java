package com.jb.couponsproject.repositories;

import com.jb.couponsproject.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByEmailAndPassword(String email, String password);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_vs_coupons WHERE customer_id = ?1", nativeQuery = true)
    void deleteCouponPurchase(int customerID);
}
