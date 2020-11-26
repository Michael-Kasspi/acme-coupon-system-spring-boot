package com.acme.couponsystem.db.rest.controller;

import com.acme.couponsystem.db.entity.Category;
import com.acme.couponsystem.db.entity.Company;
import com.acme.couponsystem.db.entity.Coupon;
import com.acme.couponsystem.db.rest.SessionService;
import com.acme.couponsystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("api/company/")
public class CompanyController {

    private SessionService SessionService;

    @Autowired
    public CompanyController(SessionService SessionService) {
        this.SessionService = SessionService;
    }

    @GetMapping("/")
    public ResponseEntity<Company> findCompany(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CompanyService service = getService(token, response);
        return ResponseEntity.ok(service.findCompany());
    }

    @PostMapping("coupons")
    public ResponseEntity<Coupon> add(
            @RequestBody Coupon coupon,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CompanyService service = getService(token, response);
        return ResponseEntity.ok(service.add(coupon));
    }

    @PutMapping("coupons")
    public ResponseEntity<Coupon> update(
            @RequestBody Coupon coupon,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CompanyService service = getService(token, response);
        return ResponseEntity.ok(service.update(coupon));
    }

    @PutMapping("coupons/list")
    public ResponseEntity<List<Coupon>> update(
            @RequestBody List<Coupon> coupons,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CompanyService service = getService(token, response);
        return ResponseEntity.ok(service.update(coupons));
    }

    @PutMapping("/")
    public ResponseEntity<Company> update(
            @RequestBody Company company,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CompanyService service = getService(token, response);
        return ResponseEntity.ok(service.update(company));
    }

    @DeleteMapping("coupons/{CouponId}")
    public void delete(
            @PathVariable long CouponId,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CompanyService service = getService(token, response);
        service.deleteCoupon(CouponId);
    }

    @DeleteMapping("coupons")
    public ResponseEntity<List<Coupon>> deleteAll(
            @CookieValue(defaultValue = "") String token,
            HttpServletResponse response
    ) {
        CompanyService service = getService(token, response);
        return ResponseEntity.ok(service.deleteAllCoupons());
    }

    @GetMapping("coupons/{CouponId}")
    public ResponseEntity<Coupon> findCoupon(
            @PathVariable long CouponId,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        CompanyService service = getService(token, response);
        return ResponseEntity.ok(service.findCoupon(CouponId));
    }

    @GetMapping("coupons")
    public ResponseEntity<List<Coupon>> findAllCoupons(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response
    ) {
        CompanyService service = getService(token, response);
        return ResponseEntity.ok(service.findAllCoupons());
    }

    @PostMapping(path = "logo")
    public ResponseEntity<Company> uploadLogoImage(
            @RequestParam MultipartFile image,
            @CookieValue(defaultValue = "") String token,
            HttpServletResponse response
    ) {
        CompanyService service = getService(token, response);
        return ResponseEntity.ok(service.uploadLogo(image));
    }

    @DeleteMapping(path = "logo")
    public ResponseEntity<Company> deleteLogoImage(
            @CookieValue(defaultValue = "") String token,
            HttpServletResponse response
    ) {
        CompanyService service = getService(token, response);
        return ResponseEntity.ok(service.deleteLogo());
    }

    @PostMapping(path = "coupons/{couponId}/images")
    public ResponseEntity<Coupon> uploadCouponImage(
            @RequestParam MultipartFile image,
            @PathVariable long couponId,
            @CookieValue(defaultValue = "") String token,
            HttpServletResponse response
    ) {
        CompanyService service = getService(token, response);
        return ResponseEntity.ok(service.uploadCouponImage(image, couponId));
    }

    @DeleteMapping(path = "coupons/{couponId}/images")
    public ResponseEntity<Coupon> deleteCouponImage(
            @PathVariable long couponId,
            @CookieValue(defaultValue = "") String token,
            HttpServletResponse response
    ) {
        CompanyService service = getService(token, response);
        return ResponseEntity.ok(service.deleteCouponImage(couponId));
    }

    @GetMapping("coupons/categories")
    public ResponseEntity<List<Category>> findAllCategories(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response
    ) {
        CompanyService service = getService(token, response);
        return ResponseEntity.ok(service.findAllCategories());
    }

    private CompanyService getService(String token, HttpServletResponse response) {
        return SessionService.getUserService(token, response, CompanyService.class);
    }
}
