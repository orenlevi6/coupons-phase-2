package com.jb.couponsproject.repositories;

import com.jb.couponsproject.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {

    boolean existsByCompany_idAndTitle(int companyID, String title);

    Optional<Coupon> findByIdAndCompany_id(int id, int companyID);

    List<Coupon> findAllByCompany_id(int companyID);

    List<Coupon> findAllByCompany_idAndCategory_id(int companyID, int categoryID);

    List<Coupon> findAllByCompany_idAndPriceLessThanEqual(int companyID, double maxPrice);

    @Query(value = "SELECT count(*) FROM customers_vs_coupons WHERE customer_id = ?1 AND coupons_id = ?2", nativeQuery = true)
    int countCouponPurchase(int customerID, int couponID);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO customers_vs_coupons (customer_id, coupons_id) values (?1, ?2)", nativeQuery = true)
    void addCouponPurchase(int customerID, int couponID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE coupons SET amount=amount-1 WHERE id=?1", nativeQuery = true)
    void decreaseCouponAmount(int couponID);

    @Query(value = " SELECT * FROM coupons " +
            " INNER JOIN customers_vs_coupons " +
            " ON coupons.id = customers_vs_coupons.coupons_id " +
            " WHERE customers_vs_coupons.customer_id= ?1 ", nativeQuery = true)
    List<Coupon> getAllCustomerCoupons(int customerID);

    @Query(value = " SELECT * FROM coupons " +
            " INNER JOIN customers_vs_coupons " +
            " ON coupons.id = customers_vs_coupons.coupons_id " +
            " WHERE customers_vs_coupons.customer_id= ?1 AND coupons.category_id = ?2", nativeQuery = true)
    List<Coupon> getAllCustomerCouponsByCategory(int customerID, int categoryID);

    @Query(value = " SELECT * FROM coupons " +
            " INNER JOIN customers_vs_coupons " +
            " ON coupons.id = customers_vs_coupons.coupons_id " +
            " WHERE customers_vs_coupons.customer_id= ?1 AND coupons.price <= ?2", nativeQuery = true)
    List<Coupon> getAllCustomerCouponsByMaxPrice(int customerID, double maxPrice);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_vs_coupons WHERE customer_id = ?1", nativeQuery = true)
    void deleteCouponPurchaseByCustomerID(int customerID);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_vs_coupons WHERE coupons_id = ?1", nativeQuery = true)
    void deleteCouponPurchase(int couponID);

}
