package com.acme.couponsystem.service;

import com.acme.couponsystem.db.entity.Category;
import com.acme.couponsystem.db.entity.Company;
import com.acme.couponsystem.db.entity.Coupon;

import java.util.List;

public interface PublicService {

    List<Coupon> findAllCoupons();

    Coupon findCoupon(long id);

    List<Company> findAllCompanies();

    List<Category> findAllCategories();

    List<Coupon> findCouponsByCategory(long id);

    List<Coupon> findCouponsByCompany(long id);

    boolean doesEmailExist(String email);
}
