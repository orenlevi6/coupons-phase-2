package com.jb.couponsproject.repositories;

import com.jb.couponsproject.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {

    List<Coupon> findAllByCompanyId(int companyID);

    List<Coupon> findAllByCompanyIdAndCategoryId(int companyID, int categoryID);

    List<Coupon> findAllByCompanyIdAndPriceLessThanEqual(int companyID, double maxPrice);

}
