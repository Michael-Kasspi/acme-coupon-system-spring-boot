package com.acme.couponsystem.service;

import com.acme.couponsystem.db.entity.*;
import com.acme.couponsystem.db.repository.AccountRepository;
import com.acme.couponsystem.db.repository.CategoryRepository;
import com.acme.couponsystem.db.repository.CompanyRepository;
import com.acme.couponsystem.db.repository.CouponRepository;
import com.acme.couponsystem.service.ex.*;
import com.acme.couponsystem.service.storage.StorageService;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CompanyServiceImpl implements CompanyService {

    private static final long NO_ID = 0;
    private static final String COUPON_IMAGE_LOCATION = "coupons/images";

    private AccountRepository accountRepository;
    private CompanyRepository companyRepository;
    private CouponRepository couponRepository;
    private StorageService storageService;
    private CategoryRepository categoryRepository;
    private Account account;
    private Company company;

    @Autowired
    public CompanyServiceImpl(
            AccountRepository accountRepository,
            CompanyRepository companyRepository,
            CouponRepository couponRepository,
            StorageService storageService,
            CategoryRepository categoryRepository) {
        this.accountRepository = accountRepository;
        this.companyRepository = companyRepository;
        this.couponRepository = couponRepository;
        this.storageService = storageService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
        company = (Company) account.getUser();
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public String getUserType() {
        return UserType.COMPANY;
    }

    @Transactional
    @Override
    public Coupon add(Coupon coupon) {
        //constrain company
        coupon.setCompany(fetchCompany());
        //assert that the save operation will be performed
        coupon.setId(NO_ID);
        validateCoupon(coupon);

        return couponRepository.save(coupon);
    }

    @Transactional
    @Override
    public Coupon update(Coupon coupon) {
        if (coupon.getId() == NO_ID) {
            throw new UnableToUpdateCouponException("Unable to update coupon without id");
        }
        //fetch the coupon to check if it exists and perform update method
        Coupon couponFromDB = getCoupon(coupon.getId());
        //check if the coupon belongs to the company
        validateCompany(couponFromDB);
        //update fields
        couponFromDB.update(coupon);
        return couponRepository.save(couponFromDB);
    }

    @Transactional
    @Override
    public List<Coupon> update(List<Coupon> coupons) {

        List<Coupon> couponsFromDb = couponRepository.findAllById(mapCouponsToId(coupons));

        couponsFromDb.forEach(this::validateCompany);
        /*filter out coupons that don't belong to the company*/
        couponsFromDb = filterForeignCoupons(couponsFromDb);
        /*remove coupons from the list to update which don't belong to this company*/
        coupons.retainAll(couponsFromDb);
        /*update the coupons*/
        updateCoupons(coupons, couponsFromDb);

        return couponRepository.saveAll(couponsFromDb);
    }

    @Transactional
    @Override
    public void deleteCoupon(long id) {

        //fetch coupon
        Coupon couponFromDB = getCoupon(id);

        //check if the coupon belongs to the company
        validateCompany(couponFromDB);

        //delete the coupon
        couponRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Company findCompany() {
        return fetchCompany();
    }

    @Transactional
    @Override
    public Coupon findCoupon(long id) {

        Coupon coupon = getCoupon(id);

        if (coupon.getCompany().getId() != account.getUser().getId()) {
            throw new ForbiddenActionException(
                    "This coupon doesn't belong to your company");
        }
        return coupon;
    }

    @Transactional
    @Override
    public List<Coupon> findAllCoupons() {
        List<Coupon> coupons = fetchCompany().getCoupons();
        Hibernate.initialize(coupons);
        return coupons;
    }

    @Transactional
    @Override
    public List<Coupon> deleteAllCoupons() {

        //fetch company
        Company company = fetchCompany();

        //remove all coupons
        company.getCoupons().clear();

        //update company in user
        account.setUser(company);

        //update company with empty list
        companyRepository.save(company);

        //return an empty list
        return company.getCoupons();

    }

    @Transactional
    @Override
    public Company update(Company company) {

        Company companyFromDB = fetchCompany();
        checkNameDuplicate(companyFromDB, company.getName());
        companyFromDB.update(company);
        account.setUser(company); //refresh the company
        return companyRepository.save(companyFromDB);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Company uploadLogo(MultipartFile image) {
        Company company = fetchCompany();
        String imageUrl = company.getImageUrl();

        if (Objects.nonNull(imageUrl)) {
            storageService.deleteFile(imageUrl);
        }

        String fileLocation = storageService.storeImage(image, "companies/logo");
        company.setImageUrl(fileLocation);

        return companyRepository.save(company);
    }

    @Override
    public Company deleteLogo() {
        Company company = fetchCompany();
        String imageUrl = company.getImageUrl();

        if (Objects.nonNull(imageUrl)) {
            storageService.deleteFile(imageUrl);
        }

        company.setImageUrl(null);
        return companyRepository.save(company);
    }

    @Override
    public Coupon uploadCouponImage(MultipartFile image, long couponId) {

        if (couponId <= NO_ID) {
            throw new UnableToUpdateCouponException("Unable to add image to coupon without id");
        }

        Coupon coupon = getCoupon(couponId);
        String imageUrl = coupon.getImageUrl();

        if (Strings.isNotBlank(imageUrl)) {
            storageService.deleteFile(imageUrl);
        }

        String fileLocation = storageService.storeImage(image, COUPON_IMAGE_LOCATION);
        coupon.setImageUrl(fileLocation);

        return couponRepository.save(coupon);
    }

    @Override
    public Coupon deleteCouponImage(long couponId) {

        if (couponId <= NO_ID) {
            throw new UnableToUpdateCouponException("Unable to delete image to coupon without id");
        }

        Coupon coupon = getCoupon(couponId);
        String imageUrl = coupon.getImageUrl();

        if (Objects.nonNull(imageUrl)) {
            storageService.deleteFile(imageUrl);
        }

        coupon.setImageUrl(null);

        return couponRepository.save(coupon);
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    private Company fetchCompany() {
        Company company = companyRepository.findById(this.company.getId())
                .orElseThrow(() -> new NoSuchCompanyException(
                        "This company doesn't exist"));
        account.setUser(company);
        return company;
    }

    private Account fetchAccount() {
        return accountRepository.findById(account.getId())
                .orElseThrow(() -> new NoSuchAccountException(
                        "This account doesn't exist"));
    }

    private Coupon getCoupon(long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new NoSuchCouponException(
                        "This coupon doesn't exist"));
    }

    private void validateCompany(Coupon coupon) {

        long couponCompanyId = coupon.getCompany().getId();
        long companyId = account.getUser().getId();

        if (couponCompanyId != companyId) {
            throw new ForbiddenActionException(
                    "Manipulation of other company's coupons is prohibited");
        }
    }

    private void constrainCompany(List<Coupon> coupons) {
        Company company = (Company) account.getUser();
        coupons.forEach(c -> c.setCompany(company));
    }

    private void validateCoupon(Coupon coupon) {

        //assignment
        LocalDate startDate = coupon.getStartDate();
        LocalDate endDate = coupon.getEndDate();

        if (null == startDate || null == endDate) {
            throw new MalformedCouponException("The coupon must have start and end dates");
        }

        LocalDate today = LocalDate.now();

        //the coupon start date must be in present time
        if (today.isAfter(startDate)) {

            String message = String.format(
                    "The start date (%s) of the coupon is in the past",
                    startDate.toString());

            throw new MalformedCouponException(message);
        }

        //the start date must not be before end date
        if (endDate.isBefore(startDate)) {

            String message = String.format(
                    "The end date (%s) of the coupon (%s) is before the end date (%s)",
                    startDate.toString(), coupon.getTitle(), endDate.toString());

            throw new MalformedCouponException(message);
        }
    }

    private void updateCoupons(List<Coupon> coupons, List<Coupon> couponsFromDb) {
        for (int i = 0; i < couponsFromDb.size(); i++) {
            Coupon couponFromDb = couponsFromDb.get(i);
            Coupon couponToUpdate = coupons.get(i);
            couponFromDb.update(couponToUpdate);
        }
    }

    private List<Coupon> filterForeignCoupons(List<Coupon> couponsFromDb) {
        couponsFromDb = couponsFromDb
                .stream()
                .filter(c -> c.getCompany().getId() == account.getUser().getId())
                .collect(Collectors.toList());
        return couponsFromDb;
    }

    private List<Long> mapCouponsToId(List<Coupon> coupons) {
        return coupons
                .stream()
                .map(Coupon::getId)
                .collect(Collectors.toList());
    }

    private void checkNameDuplicate(Company companyFromDB, String companyName) {
        List<Company> duplicates = companyRepository.findByNameIgnoreCase(companyName);
        duplicates.forEach(c -> {
            boolean otherCompany = c.getId() != companyFromDB.getId();
            boolean sameName = c.getName().equalsIgnoreCase(companyName);
            if (otherCompany && sameName) {
                throw new UnableToUpdateCompanyException(
                        String.format("The company name '%s' is already taken", companyName)
                );
            }
        });
    }
}
