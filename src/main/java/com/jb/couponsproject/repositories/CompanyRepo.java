package com.jb.couponsproject.repositories;

import com.jb.couponsproject.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company, Integer> {

    boolean existsByIdAndName(int id, String name);

    Optional<Company> findByEmailAndPassword(String email, String password);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_vs_coupons WHERE coupons_id = any " +
            "(SELECT id FROM coupons WHERE company_id= ?1)", nativeQuery = true)
    void deletePurchasedCouponsByCompanyID(int companyID);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM coupons WHERE company_id = ?1", nativeQuery = true)
    void deleteCompanyCoupons(int companyID);
}
