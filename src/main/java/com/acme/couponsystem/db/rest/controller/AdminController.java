package com.acme.couponsystem.db.rest.controller;

import com.acme.couponsystem.db.entity.*;
import com.acme.couponsystem.db.rest.SessionService;
import com.acme.couponsystem.service.AdminService;
import com.acme.couponsystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("api/admin/")
public class AdminController {

    private SessionService sessionService;

    @Autowired
    public AdminController(SessionService SessionService) {
        this.sessionService = SessionService;
    }

    @PostMapping("accounts")
    public ResponseEntity<Account> addAccount(
            @RequestBody Account account,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.add(account));
    }

    @PostMapping("/")
    public ResponseEntity<Admin> addAdmin(
            @RequestBody Admin admin,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.add(admin));
    }

    @PostMapping("companies")
    public ResponseEntity<Company> addCompany(
            @RequestBody Company company,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.add(company));
    }

    @PostMapping("customers")
    public ResponseEntity<Customer> addCustomer(
            @RequestBody Customer customer,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.add(customer));
    }

    @PostMapping("coupons")
    public ResponseEntity<Coupon> addCoupon(
            @RequestBody Coupon coupon,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.add(coupon));
    }

    @PostMapping("categories")
    public ResponseEntity<Category> addCategory(
            @RequestBody Category category,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);

        return ResponseEntity.ok(service.add(category));
    }

    @PostMapping("categories/list")
    public ResponseEntity<List<Category>> addCategories(
            @RequestBody List<Category> categories,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);

        return ResponseEntity.ok(service.add(categories));
    }

    @PostMapping("customers/{customerId}/coupons/{couponId}")
    public ResponseEntity<List<Coupon>> addCouponToCustomer(
            @PathVariable long customerId,
            @PathVariable long couponId,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.addCouponToCustomer(couponId, customerId));
    }

    @DeleteMapping("customers/{customerId}/coupons/{couponId}")
    public ResponseEntity<List<Coupon>> removeCouponFromCustomer(
            @PathVariable long couponId,
            @PathVariable long customerId,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.removeCouponFromCustomer(couponId, customerId));
    }

    @PatchMapping("customers/coupons/remove/all/{customerId}")
    public ResponseEntity<List<Coupon>> removeAllCouponsFromCustomer(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response,
            @PathVariable long customerId) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.removeAllCouponsFromCustomer(customerId));
    }

    @PutMapping("update")
    public ResponseEntity<Admin> updateAdmin(
            @RequestBody Admin admin,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.update(admin));
    }

    @PutMapping("accounts")
    public ResponseEntity<Account> updateAccount(
            @RequestBody Account account,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.update(account));
    }

    @PutMapping("companies")
    public ResponseEntity<Company> updateCompany(
            @RequestBody Company company,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.update(company));
    }

    @PutMapping("coupons")
    public ResponseEntity<Coupon> updateCoupon(
            @RequestBody Coupon coupon,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.update(coupon));
    }

    @PutMapping("categories")
    public ResponseEntity<Category> updateCategory(
            @RequestBody Category category,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.update(category));
    }

    @PutMapping("coupons/list")
    public ResponseEntity<List<Coupon>> updateCoupons(
            @RequestParam long companyId,
            @RequestBody List<Coupon> coupons,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {

        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.updateCompanyCoupons(companyId, coupons));
    }

    @PutMapping("categories/list")
    public ResponseEntity<List<Category>> updateCategories(
            @RequestBody List<Category> categories,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {

        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.update(categories));
    }

    @GetMapping("accounts")
    public ResponseEntity<List<Account>> findAllAccounts(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.findAllAccounts());
    }

    @GetMapping("companies")
    public ResponseEntity<List<Company>> findAllCompanies(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.findAllCompanies());
    }

    @GetMapping("customers")
    public ResponseEntity<List<Customer>> findAllCustomers(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.findAllCustomers());
    }

    @GetMapping("coupons")
    public ResponseEntity<List<Coupon>> findAllCoupons(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.findAllCoupons());
    }

    @GetMapping("categories")
    public ResponseEntity<List<Category>> findAllCategories(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.findAllCategories());
    }

    @GetMapping("accounts/{accountId}")
    public ResponseEntity<Account> findAccount(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response,
            @PathVariable long accountId) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.findAccount(accountId));
    }

    @GetMapping("companies/{companyId}")
    public ResponseEntity<Company> findCompany(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response,
            @PathVariable long companyId) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.findCompany(companyId));
    }

    @GetMapping("customers/{customerId}")
    public ResponseEntity<Customer> findCustomer(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response,
            @PathVariable long customerId) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.findCustomer(customerId));
    }

    @GetMapping("coupons/{couponId}")
    public ResponseEntity<Coupon> findCoupon(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response,
            @PathVariable long couponId) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.findCoupon(couponId));
    }

    @GetMapping("categories/{categoryId}")
    public ResponseEntity<Category> findCategory(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response,
            @PathVariable long categoryId) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.findCategory(categoryId));
    }

    @GetMapping("companies/{companyId}/coupons/")
    public ResponseEntity<List<Coupon>> findCompanyCoupons(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response,
            @PathVariable long companyId) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.findCompanyCoupons(companyId));
    }

    @GetMapping("customers/{customerId}/coupons")
    public ResponseEntity<List<Coupon>> findCustomerCoupons(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response,
            @PathVariable long customerId) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.findCustomerCoupons(customerId));
    }

    @DeleteMapping("accounts/{accountId}")
    public void deleteAccount(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response,
            @PathVariable long accountId) {
        AdminService service = getService(token, response);
        service.deleteAccount(accountId);
    }

    @DeleteMapping("companies/{companyId}")
    public void deleteCompany(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response,
            @PathVariable long companyId) {
        AdminService service = getService(token, response);
        service.deleteCompany(companyId);
    }

    @DeleteMapping("customers/{customerId}")
    public void deleteCustomer(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response,
            @PathVariable long customerId) {
        AdminService service = getService(token, response);
        service.deleteCustomer(customerId);
    }

    @DeleteMapping("coupons/{couponId}")
    public void deleteCoupon(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response,
            @PathVariable long couponId) {
        AdminService service = getService(token, response);
        service.deleteCoupon(couponId);
    }

    @DeleteMapping("categories/{categoryId}")
    public void deleteCategory(
            @CookieValue(defaultValue = "") String token, HttpServletResponse response,
            @PathVariable long categoryId) {
        AdminService service = getService(token, response);
        service.deleteCategory(categoryId);
    }

    @PatchMapping("companies/{companyId}/credit/add/")
    public ResponseEntity<Account> addCompanyCredit(
            @PathVariable long companyId,
            @RequestParam double amount,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.addCompanyCredit(companyId, amount));
    }

    @PatchMapping("customers/{customerId}/credit/add/")
    public ResponseEntity<Account> addCustomerCredit(
            @PathVariable long customerId,
            @RequestParam double amount,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.addCustomerCredit(customerId, amount));
    }

    @PatchMapping("companies/{companyId}/credit/reduce")
    public ResponseEntity<Account> reduceCompanyCredit(
            @PathVariable long companyId,
            @RequestParam double amount,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.reduceCompanyCredit(companyId, amount));
    }

    @PatchMapping("customers/{customerId}/credit/reduce")
    public ResponseEntity<Account> reduceCustomerCredit(
            @PathVariable long customerId,
            @RequestParam double amount,
            @CookieValue(defaultValue = "") String token, HttpServletResponse response) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.reduceCustomerCredit(customerId, amount));
    }

    @PostMapping(path = "coupons/{couponId}/images")
    public ResponseEntity<Coupon> uploadCouponImage(
            @RequestParam MultipartFile image,
            @PathVariable long couponId,
            @CookieValue(defaultValue = "") String token,
            HttpServletResponse response
    ) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.uploadCouponImage(image, couponId));
    }

    @DeleteMapping(path = "coupons/{couponId}/images")
    public ResponseEntity<Coupon> deleteCouponImage(
            @PathVariable long couponId,
            @CookieValue(defaultValue = "") String token,
            HttpServletResponse response
    ) {
        AdminService service = getService(token, response);
        return ResponseEntity.ok(service.deleteCouponImage(couponId));
    }

    private AdminService getService(String token, HttpServletResponse response) {
        return sessionService.getUserService(token, response, AdminService.class);
    }
}
