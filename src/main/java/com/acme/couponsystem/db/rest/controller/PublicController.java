package com.acme.couponsystem.db.rest.controller;

import com.acme.couponsystem.db.entity.Category;
import com.acme.couponsystem.db.entity.Company;
import com.acme.couponsystem.db.entity.Coupon;
import com.acme.couponsystem.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("api/public/")
public class PublicController {

    private PublicService publicService;

    @Autowired
    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    @GetMapping("coupons")
    public ResponseEntity<List<Coupon>> findAllCoupons() {
        return ResponseEntity.ok(publicService.findAllCoupons());
    }

    @GetMapping("coupons/{id}")
    public ResponseEntity<Coupon> findCoupon(@PathVariable long id) {
        return ResponseEntity.ok(publicService.findCoupon(id));
    }

    @GetMapping("coupons/companies/{id}")
    public ResponseEntity<List<Coupon>> findCouponByCompany(@PathVariable long id) {
        return ResponseEntity.ok(publicService.findCouponsByCompany(id));
    }

    @GetMapping("coupons/categories/{id}")
    public ResponseEntity<List<Coupon>> findCouponByCategory(@PathVariable long id) {
        return ResponseEntity.ok(publicService.findCouponsByCategory(id));
    }

    @GetMapping("companies")
    public ResponseEntity<List<Company>> findAllCompanies() {
        return ResponseEntity.ok(this.publicService.findAllCompanies());
    }

    @GetMapping("categories")
    public ResponseEntity<List<Category>> findAllCategories() {
        return ResponseEntity.ok(this.publicService.findAllCategories());
    }

    @GetMapping("accounts/emails")
    public ResponseEntity<Boolean> emailExist(@RequestHeader String email) {
        return ResponseEntity.ok(this.publicService.doesEmailExist(email));
    }
}
