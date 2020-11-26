package com.acme.couponsystem.service;

import com.acme.couponsystem.db.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminService extends UserService {

    List<Account> findAllAccounts();

    List<Company> findAllCompanies();

    List<Customer> findAllCustomers();

    List<Coupon> findAllCoupons();

    List<Coupon> findCompanyCoupons(long companyId);

    List<Coupon> findCustomerCoupons(long customerId);

    Account add(Account account);

    Admin add(Admin admin);

    Company add(Company company);

    Customer add(Customer customer);

    Coupon add(Coupon coupon);

    Account update(Account account);

    Admin update(Admin admin);

    Company update(Company company);

    Coupon update(Coupon coupon);

    List<Coupon> updateCompanyCoupons(long companyId ,List<Coupon> coupons);

    Account findAccount(long id);

    Company findCompany(long id);

    Customer findCustomer(long id);

    Coupon findCoupon (long id);

    void deleteAccount(long id);

    void deleteCompany(long id);

    void deleteCustomer(long id);

    void deleteCoupon(long id);

    void deleteCoupons(List<Coupon> coupons);

    List<Coupon> removeCouponFromCustomer(long couponId, long customerId);

    List<Coupon> removeAllCouponsFromCustomer(long customerId);

    List<Coupon> addCouponToCustomer(long couponId, long customerId);

    Account addCompanyCredit(long id, double amount);

    Account addCustomerCredit(long id, double amount);

    Account reduceCompanyCredit(long id, double amount);

    Account reduceCustomerCredit(long id, double amount);

    Category add(Category category);

    List<Category> add(List<Category> categories);

    void deleteCategory(long id);

    Category update(Category category);

    List<Category> update(List<Category> categories);

    Category findCategory(long id);

    List<Category> findAllCategories();

    Coupon uploadCouponImage(MultipartFile image, long couponId);

    Coupon deleteCouponImage(long couponId);
}
