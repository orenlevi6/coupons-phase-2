package com.jb.couponsproject.repositories;

import com.jb.couponsproject.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {

}
