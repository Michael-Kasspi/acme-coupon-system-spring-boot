package com.acme.couponsystem.service;

import com.acme.couponsystem.db.entity.Account;
import com.acme.couponsystem.db.entity.Category;
import com.acme.couponsystem.db.entity.Company;
import com.acme.couponsystem.db.entity.Coupon;
import com.acme.couponsystem.db.repository.AccountRepository;
import com.acme.couponsystem.db.repository.CategoryRepository;
import com.acme.couponsystem.db.repository.CompanyRepository;
import com.acme.couponsystem.db.repository.CouponRepository;
import com.acme.couponsystem.service.ex.NoSuchAccountException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PublicServiceImpl implements PublicService {

    private CouponRepository couponRepository;
    private CompanyRepository companyRepository;
    private CategoryRepository categoryRepository;
    private AccountRepository accountRepository;

    @Autowired
    public PublicServiceImpl(CouponRepository couponRepository, CompanyRepository companyRepository, CategoryRepository categoryRepository, AccountRepository accountRepository) {
        this.couponRepository = couponRepository;
        this.companyRepository = companyRepository;
        this.categoryRepository = categoryRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public List<Coupon> findAllCoupons() {
        return couponRepository.findAll();
    }

    @Transactional
    @Override
    public Coupon findCoupon(long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new NoSuchAccountException("Unable to find the coupon"));
    }

    @Transactional
    @Override
    public List<Coupon> findCouponsByCategory(long id) {
        return this.couponRepository.findByCategoryId(id);
    }

    @Transactional
    @Override
    public List<Coupon> findCouponsByCompany(long id) {
        return this.couponRepository.findByCompanyId(id);
    }

    @Transactional
    @Override
    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    @Transactional
    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    @Override
    public boolean doesEmailExist(String email) {
        if (Strings.isBlank(email)) {
            return false;
        }
        return accountRepository
                .findByEmail(email)
                .map(account -> email.equalsIgnoreCase(account.getEmail()))
                .orElse(false);
    }
}
