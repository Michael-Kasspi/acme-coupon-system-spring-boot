package com.acme.couponsystem.service;

import com.acme.couponsystem.db.entity.Account;
import com.acme.couponsystem.db.entity.Coupon;
import com.acme.couponsystem.db.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService extends UserService{

    List<Coupon> findAllCoupons();

    Customer getCustomer();

    Account purchaseCoupon(long id);

    Account purchaseCoupons(List<Coupon> coupons);

    void removeCoupon(long id);

    Account purchaseCredits(double amount);

    List<Coupon> findCartCoupons();

    Coupon addCouponToCart(long id);

    List<Coupon> addCouponsToCart(List<Coupon> coupons);

    List<Coupon> updateCouponsToCart(List<Coupon> coupons);

    void removeCouponFromCart(long id);

    void removeAllCouponsFromCart();

    Coupon addCouponToWishlist(long id);

    List<Coupon> updateCouponsWishlist(List<Coupon> coupons);

    void removeCouponFromWishlist(long id);

    void removeAllCouponsFromWishlist();

    List<Coupon> findWishlistCoupons();
}
