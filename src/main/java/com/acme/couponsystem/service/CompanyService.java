package com.acme.couponsystem.service;

import com.acme.couponsystem.db.entity.Category;
import com.acme.couponsystem.db.entity.Company;
import com.acme.couponsystem.db.entity.Coupon;
import com.sun.istack.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompanyService extends UserService {

    Coupon add(Coupon coupon);

    void deleteCoupon(long id);

    Company findCompany();

    Coupon findCoupon(long id);

    List<Coupon> findAllCoupons();

    List<Coupon> deleteAllCoupons();

    Company update(Company company);

    Coupon update(Coupon coupon);

    List<Coupon> update (List<Coupon> coupons);

    Company uploadLogo(MultipartFile image);

    Company deleteLogo();

    Coupon uploadCouponImage(MultipartFile image, long couponId);

    Coupon deleteCouponImage(long couponId);

    List<Category> findAllCategories();
}
