package com.jb.couponsproject.services.serviceImpl;

import com.jb.couponsproject.beans.Categories;
import com.jb.couponsproject.beans.Company;
import com.jb.couponsproject.beans.Coupon;
import com.jb.couponsproject.exceptions.AlreadyExistsException;
import com.jb.couponsproject.exceptions.CouponException;
import com.jb.couponsproject.exceptions.NotExistException;
import com.jb.couponsproject.services.ClientService;
import com.jb.couponsproject.services.serviceDAO.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl extends ClientService implements CompanyService {
    private int companyID;

    @Override
    public boolean login(String email, String password) {
        Optional<Company> optionalCompany = companyRepo.findByEmailAndPassword(email, password);
        if (optionalCompany.isPresent()) {
            this.companyID = optionalCompany.get().getId();
            return true;
        }
        return false;
    }

    @Override
    public void addCoupon(Coupon coupon) {
        coupon.setCompany(getCompanyDetails());
        couponRepo.save(coupon);
    }

    @Override
    public void updateCoupon(Coupon coupon) throws NotExistException {
        coupon.setCompany(getCompanyDetails());

        Optional<Coupon> optionalCoupon = couponRepo.findByIdAndCompany_id(coupon.getId(), this.companyID);
        if (optionalCoupon.isEmpty()) {
            throw new NotExistException("Coupon not found");
        }
        if (!optionalCoupon.get().getTitle().equals(coupon.getTitle())) {
            addCoupon(coupon);
        } else {
            couponRepo.save(coupon);
        }
    }

    @Override
    public void deleteCoupon(int couponID) throws NotExistException, CouponException {
        Optional<Coupon> optionalCoupon = couponRepo.findById(couponID);
        if (optionalCoupon.isEmpty()) {
            throw new NotExistException("Coupon ID not found");
        }
        if (optionalCoupon.get().getCompany().getId() != this.companyID) {
            throw new CouponException("Coupon does not match this company");
        }
        couponRepo.deleteCouponPurchase(couponID);
        couponRepo.deleteById(couponID);
    }

    @Override
    public List<Coupon> getAllCompanyCoupons() throws NotExistException {
        List<Coupon> companyCoupons = couponRepo.findAllByCompany_id(this.companyID);
        if (companyCoupons.isEmpty()) {
            throw new NotExistException("No coupons found");
        }
        return companyCoupons;
    }

    @Override
    public List<Coupon> getAllCompanyCoupons(Categories category) throws NotExistException {
        List<Coupon> companyCouponsByCategory = couponRepo.findAllByCompany_idAndCategory_id(this.companyID, category.getValue());
        if (companyCouponsByCategory.isEmpty()) {
            throw new NotExistException("No coupons found");
        }
        return companyCouponsByCategory;
    }

    @Override
    public List<Coupon> getAllCompanyCoupons(double maxPrice) throws NotExistException {
        List<Coupon> companyCouponsByMaxPrice = couponRepo.findAllByCompany_idAndPriceLessThanEqual(this.companyID, maxPrice);
        if (companyCouponsByMaxPrice.isEmpty()) {
            throw new NotExistException("No coupons found");
        }
        return companyCouponsByMaxPrice;
    }

    @Override
    public Company getCompanyDetails() {
        return companyRepo.findById(this.companyID).get();
    }

}
