package com.acme.couponsystem.db.rest.controller;

import com.acme.couponsystem.db.entity.Account;
import com.acme.couponsystem.db.entity.Coupon;
import com.acme.couponsystem.db.entity.Customer;
import com.acme.couponsystem.db.rest.SessionService;
import com.acme.couponsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("api/customer/")
public class CustomerController {

    private SessionService sessionService;

    @Autowired
    public CustomerController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("coupons")
    public ResponseEntity<List<Coupon>> findAllCoupons(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);

        return ResponseEntity.ok(service.findAllCoupons());
    }

    @GetMapping("cart")
    public ResponseEntity<List<Coupon>> findAllCartCoupons(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);

        return ResponseEntity.ok(service.findCartCoupons());
    }

    @GetMapping("wishlist")
    public ResponseEntity<List<Coupon>> findAllWishlistCoupons(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);

        return ResponseEntity.ok(service.findWishlistCoupons());
    }

    @GetMapping("/")
    public ResponseEntity<Customer> getCustomer(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);
        return ResponseEntity.ok(service.getCustomer());
    }

    @PostMapping("coupons/{couponId}")
    public ResponseEntity<Account> purchaseCoupon(
            @PathVariable long couponId,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);

        return ResponseEntity.ok(service.purchaseCoupon(couponId));
    }

    @PostMapping("coupons")
    public ResponseEntity<Account> purchaseCoupons(
            @RequestBody List<Coupon> coupons,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);

        return ResponseEntity.ok(service.purchaseCoupons(coupons));
    }

    @PostMapping("cart/{couponId}")
    public ResponseEntity<Coupon> addCouponToCart(
            @PathVariable long couponId,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);

        return ResponseEntity.ok(service.addCouponToCart(couponId));
    }

    @PostMapping("wishlist/{couponId}")
    public ResponseEntity<Coupon> addCouponToWishlist(
            @PathVariable long couponId,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);

        return ResponseEntity.ok(service.addCouponToWishlist(couponId));
    }

    @PostMapping("cart")
    public ResponseEntity<List<Coupon>> addCouponsToCart(
            @RequestBody List<Coupon> coupons,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);

        return ResponseEntity.ok(service.addCouponsToCart(coupons));
    }

    @PutMapping("cart")
    public ResponseEntity<List<Coupon>> updateCouponsToCart(
            @RequestBody List<Coupon> coupons,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);

        return ResponseEntity.ok(service.updateCouponsToCart(coupons));
    }

    @PutMapping("wishlist")
    public ResponseEntity<List<Coupon>> updateCouponsToWishlist(
            @RequestBody List<Coupon> coupons,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);

        return ResponseEntity.ok(service.updateCouponsWishlist(coupons));
    }

    @PostMapping("credits")
    public ResponseEntity<Account> purchaseCredits(
            @RequestParam double amount,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);

        return ResponseEntity.ok(service.purchaseCredits(amount));
    }

    @DeleteMapping("coupons/{couponId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeCoupon(
            @PathVariable long couponId,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);
        service.removeCoupon(couponId);
    }

    @DeleteMapping("cart/{couponId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeCouponFromCart(
            @PathVariable long couponId,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);
        service.removeCouponFromCart(couponId);
    }

    @DeleteMapping("wishlist/{couponId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeCouponFromWishlist(
            @PathVariable long couponId,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);
        service.removeCouponFromWishlist(couponId);
    }

    @DeleteMapping("cart")
    @ResponseStatus(HttpStatus.OK)
    public void removeAllCouponsFromCart(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);

        service.removeAllCouponsFromCart();
    }

    @DeleteMapping("wishlist")
    @ResponseStatus(HttpStatus.OK)
    public void removeAllCouponsFromWishlist(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CustomerService service = getService(token, response);

        service.removeAllCouponsFromWishlist();
    }

    private CustomerService getService(String token, HttpServletResponse response) {
        return sessionService.getUserService(token, response, CustomerService.class);
    }
}
