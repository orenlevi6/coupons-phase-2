package com.jb.couponsproject.repositories;

import com.jb.couponsproject.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {

    boolean existsByIdAndCompanyId(int id, int companyID);

    List<Coupon> findAllByCompanyId(int companyID);

    List<Coupon> findAllByCompanyIdAndCategoryId(int companyID, int categoryID);

    List<Coupon> findAllByCompanyIdAndPriceLessThanEqual(int companyID, double maxPrice);

    @Query(value = "SELECT count(*) FROM customers_vs_coupons WHERE customer_id = ?1 AND coupons_id = ?2", nativeQuery = true)
    int countCouponPurchase(int customerID, int couponID);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_vs_coupons WHERE customer_id <>0 and coupons_id = any " +
            "(SELECT id FROM coupons WHERE company_id = ?1)", nativeQuery = true)
    void deleteCompanyCoupons(int companyID);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_vs_coupons WHERE customer_id = ?1", nativeQuery = true)
    void deleteCouponPurchaseByCustomerID(int customerID);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_vs_coupons WHERE coupons_id = ?1", nativeQuery = true)
    void deleteCouponPurchaseByCouponID(int couponID);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO customers_vs_coupons (customer_id,coupons_id) VALUES (?1,?2)", nativeQuery = true)
    void addCouponPurchase(int customerID, int couponID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE coupons SET amount = amount-1 WHERE id = ?1", nativeQuery = true)
    void decreaseCouponAmount(int couponID);

    @Query(value = "SELECT * FROM coupons " +
            "INNER JOIN customers_vs_coupons " +
            "ON coupons.id = customers_vs_coupons.coupons_id " +
            "WHERE customers_vs_coupons.customer_id = ?1", nativeQuery = true)
    List<Coupon> findAllCustomerCoupons(int customerID);

    @Query(value = "SELECT * FROM coupons " +
            "INNER JOIN customers_vs_coupons " +
            "ON coupons.id = customers_vs_coupons.coupons_id " +
            "WHERE customers_vs_coupons.customer_id = ?1 AND category_id = ?2", nativeQuery = true)
    List<Coupon> findAllCustomerCouponsByCategory(int customerID, int categoryID);

    @Query(value = "SELECT * FROM coupons " +
            "INNER JOIN customers_vs_coupons " +
            "ON coupons.id = customers_vs_coupons.coupons_id " +
            "WHERE customers_vs_coupons.customer_id = ?1 AND price <= ?2", nativeQuery = true)
    List<Coupon> findAllCustomerCouponsByMaxPrice(int customerID, double maxPrice);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM coupons WHERE id>0 AND end_date < curdate()", nativeQuery = true)
    void deleteExpiredCoupons();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_vs_coupons WHERE coupons_id = any" +
            " (SELECT id FROM coupons WHERE end_date < curdate())", nativeQuery = true)
    void deletePurchasedExpiredCoupons();
}
