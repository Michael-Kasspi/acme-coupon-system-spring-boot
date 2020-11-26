package com.acme.couponsystem.service;

import com.acme.couponsystem.db.entity.*;
import com.acme.couponsystem.db.repository.*;
import com.acme.couponsystem.service.ex.*;
import com.acme.couponsystem.service.storage.StorageService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AdminServiceImpl implements AdminService {

    private static final String COUPON_IMAGE_LOCATION = "coupons/images";
    private static final long NO_ID = 0;
    private static final double NO_CREDIT = 0;

    private AccountRepository accountRepository;
    private AdminRepository adminRepository;
    private CompanyRepository companyRepository;
    private CustomerRepository customerRepository;
    private CouponRepository couponRepository;
    private CategoryRepository categoryRepository;
    private StorageService storageService;
    private Account account;
    private Admin admin;

    @Autowired
    public AdminServiceImpl(
            AccountRepository accountRepository, AdminRepository adminRepository,
            CompanyRepository companyRepository,
            CustomerRepository customerRepository,
            CouponRepository couponRepository, CategoryRepository categoryRepository, StorageService storageService) {
        this.accountRepository = accountRepository;
        this.adminRepository = adminRepository;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
        this.categoryRepository = categoryRepository;
        this.storageService = storageService;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
        this.admin = (Admin) account.getUser();
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public String getUserType() {
        return UserType.ADMIN;
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Coupon> findAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public List<Coupon> findCompanyCoupons(long companyId) {
        return getCompany(companyId).getCoupons();
    }

    @Override
    public List<Coupon> findCustomerCoupons(long customerId) {
        return getCustomer(customerId).getCoupons();
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    @Override
    public Account add(Account account) {

        User user = account.getUser();

        if (null == user) throw new MalformedAccountException("Account must have a user");

        /*check if the user is admin*/
        adminValidation(user);

        /*look for account with the email*/
        Optional<Account> duplicate = accountRepository.findByEmail(account.getEmail());

        /*catch duplicate email*/
        if (duplicate.isPresent()) throw new DuplicateEmailException("This email address is already taken");

        /*make sure new entity is created*/
        account.setId(NO_ID);
        user.setId(NO_ID);

        assertCompanyUniqueName(user);

        /*set credit to create credit entity in db*/
        account.setCredit(NO_CREDIT);

        return accountRepository.save(account);
    }

    @Override
    public Admin add(Admin admin) {

        if (!admin.isMain()) {
            throw new ForbiddenActionException("Admins must be main in order to add new admins");
        }

        admin.setId(NO_ID);
        return adminRepository.save(admin);
    }

    @Transactional
    @Override
    public Company add(Company company) {
        company.setId(NO_ID);
        return companyRepository.save(company);
    }

    @Transactional
    @Override
    public Customer add(Customer customer) {
        customer.setId(NO_ID);
        return customerRepository.save(customer);
    }

    @Transactional
    @Override
    public Coupon add(Coupon coupon) {
        if (null == coupon) {
            throw new MalformedCouponException("Unable to add coupon without coupon");
        }
        /*check if the company exists*/
        if (!companyRepository.existsById(coupon.getCompany().getId())) {
            throw new NoSuchCompanyException("The company of the coupon does't exist in the database");
        }

        //clear id to prevent update on existing coupon
        coupon.setId(NO_ID);

        //validate the dates of the coupon
        validateCoupon(coupon);

        //save and return
        return couponRepository.save(coupon);
    }

    @Override
    public Category add(Category category) {
        category.setId(NO_ID);

        validateCategory(category);

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> add(List<Category> categories) {

        categories = categories.stream()
                .peek(category -> category.setId(NO_ID))
                .peek(this::validateCategory)
                .collect(Collectors.toList());

        return categoryRepository.saveAll(categories);
    }

    @Transactional
    @Override
    public Account update(Account account) {

        long accountId = account.getId();
        //make sure only existing entities are updated
        if (accountId <= NO_ID) {
            throw new UnableToUpdateAccountException(
                    "Unable to update account without id");
        }
        if (accountId == this.account.getId()) {
            throw new ForbiddenActionException(
                    "Update of self account should be done through account service");
        }
        //fetch account
        Account accountFromDB = getAccount(accountId);

        //prevent admin from updating other admin's account if they are not main
        validateAdminUpdate(accountFromDB);
        //update fields
        accountFromDB.update(account);
        /*Update user if present*/
        updateUser(account, accountFromDB);
        //save and return
        return accountRepository.save(accountFromDB);
    }

    private void updateUser(Account account, Account accountFromDB) {
        User user = account.getUser();
        if (null != user) {
            User userFromDb = accountFromDB.getUser();
            /*Override user if not present in db or new user type*/
            if (null == userFromDb) {
                accountFromDB.setUser(user);
            } else { /*Update the user*/
                if (!userFromDb.getClass().equals(user.getClass())) {
                    throw new UnableToUpdateAccountException(
                            "Changing an existing account user type is unsupported");
                }
                if (user instanceof Company) {
                    checkCompanyNameDuplicate((Company) userFromDb, ((Company) user).getName());
                }
                userFromDb.update(user);
            }
        }
    }

    @Transactional
    @Override
    public Admin update(Admin admin) {

        //make sure admin only updates himself
        if (!admin.isMain() && admin.getId() != getAdmin().getId()) {
            throw new ForbiddenActionException(
                    "Admins are prohibited to update other admins unless the admin is main");
        }
        return adminRepository.save(admin);
    }

    @Transactional
    @Override
    public Company update(Company company) {

        //make sure only existing entities are updated
        if (company.getId() <= NO_ID) {
            throw new UnableToUpdateCompanyException(
                    "Unable to update company without id");
        }

        //fetch company
        Company companyFromDB = getCompany(company.getId());
        checkCompanyNameDuplicate(companyFromDB, company.getName());

        //update fields
        companyFromDB.update(company);

        //save and return
        return companyRepository.save(companyFromDB);
    }

    @Transactional
    @Override
    public Coupon update(Coupon coupon) {

        //make sure only existing entities are updated
        if (coupon.getId() <= NO_ID) {
            throw new UnableToUpdateCouponException(
                    "Unable to update coupon without id");
        }

        //fetch coupon
        Coupon couponFromDB = getCoupon(coupon.getId());

        //update fields
        couponFromDB.update(coupon);

        //save and return
        return couponRepository.save(couponFromDB);
    }

    @Override
    public Category update(Category category) {

        long categoryId = category.getId();

        if (categoryId == NO_ID) {
            throw new UnableToUpdateCategoryException("Unable to update category without id");
        }

        validateCategory(category);

        Category categoryFromDb = getCategory(categoryId);
        categoryFromDb.update(category);
        return categoryRepository.save(categoryFromDb);
    }

    @Override
    public List<Category> update(List<Category> categories) {

        categories.forEach(this::validateCategory);

        List<Long> categoriesIds = mapCategoriesToId(categories);

        List<Category> categoriesFromDb = categoryRepository.findAllById(categoriesIds);

        boolean notAllCategoriesExist = categories.retainAll(categoriesFromDb);

        if (notAllCategoriesExist) {
            throw new UnableToUpdateCategoryException("Some of the categories don't exist");
        }

        update(categories, categoriesFromDb);

        return categoryRepository.saveAll(categoriesFromDb);
    }

    private List<Long> mapCategoriesToId(List<Category> categories) {
        return categories
                .stream()
                .map(Category::getId)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Coupon> updateCompanyCoupons(long companyId, List<Coupon> coupons) {

        if (!companyRepository.existsById(companyId)) {
            throw new NoSuchCompanyException(String.format("The company with the id of %s doesn't exist", companyId));
        }

        /*get the ids of the coupons to be fetched from db for update*/
        List<Long> couponIdsToFind = mapCouponsToIds(coupons);

        List<Coupon> couponsFromDb = couponRepository.findAllById(couponIdsToFind);

        /*filter out coupons that don't belong to the company*/
        couponsFromDb = filterByCompany(companyId, couponsFromDb);

        /*remove coupons from the list to update which don't belong to this company*/
        coupons.retainAll(couponsFromDb);

        /*update the coupons*/
        update(coupons, couponsFromDb);

        return couponRepository.saveAll(couponsFromDb);
    }

    @Override
    public Account findAccount(long id) {
        return getAccount(id);
    }

    @Override
    public Company findCompany(long id) {
        return getCompany(id);
    }

    @Override
    public Customer findCustomer(long id) {
        return getCustomer(id);
    }

    @Override
    public Coupon findCoupon(long id) {
        return getCoupon(id);
    }

    @Override
    public Category findCategory(long id) {
        return getCategory(id);
    }

    @Transactional
    @Override
    public void deleteAccount(long id) {

        //fetch account
        Account accountFromDB = getAccount(id);

        //prevent admin from deleting other admin's account
        if (accountFromDB.getUser() instanceof Admin && !admin.isMain()) {
            throw new ForbiddenActionException(
                    "Admins are prohibited from deleting Admin accounts");
        }

        accountRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteCompany(long id) {
        //check if company exists
        Company company = getCompany(id);

        try {
            //fetch account by company id in order to delete the account
            account = getAccountByUser(id, UserType.COMPANY);

            //delete the account with the company (cascade)
            accountRepository.delete(account);
        } catch (NoSuchAccountException ex) {
            //the company has no account
            companyRepository.delete(company);
        }
    }

    @Transactional
    @Override
    public void deleteCustomer(long id) {
        //check if customer exists
        Customer customer = getCustomer(id);
        try {
            //fetch account by customer id in order to delete the account
            Account account = getAccountByUser(id, UserType.CUSTOMER);

            //delete the account with the customer (cascade)
            accountRepository.delete(account);

        } catch (NoSuchAccountException ex) {
            //delete the customer entity in case the company has no account
            customerRepository.delete(customer);
        }
    }

    @Transactional
    @Override
    public void deleteCoupon(long id) {

        //check if the coupon exists
        Coupon coupon = getCoupon(id);

        couponRepository.delete(coupon);
    }

    @Override
    public void deleteCoupons(List<Coupon> coupons) {
        couponRepository.deleteAll(coupons);
    }

    @Override
    public void deleteCategory(long id) {
        Category category = getCategory(id);
        categoryRepository.delete(category);
    }

    @Transactional
    @Override
    public List<Coupon> removeCouponFromCustomer(long couponId, long customerId) {

        //fetch customer
        Customer customer = getCustomer(customerId);

        //fetch coupon to check if the coupon exist and to remove it from the list.
        Coupon coupon = getCoupon(couponId);

        //remove the coupon from the list
        List<Coupon> customerCoupons = customer.getCoupons();

        //check if the coupon is owned by the customer
        if (!customerCoupons.contains(coupon)) {
            throw new UnableToRemoveCouponException("This coupon is not owned by the customer");
        }

        //remove the coupon
        customerCoupons.remove(coupon);

        //update the customer with the updated list of coupons
        customerRepository.save(customer);

        //return the updated list of coupons
        return customer.getCoupons();
    }

    @Transactional
    @Override
    public List<Coupon> removeAllCouponsFromCustomer(long customerId) {
        Customer customer = getCustomer(customerId);

        //removes all the coupons from the list
        List<Coupon> customerCoupons = customer.getCoupons();

        if (customerCoupons.isEmpty()) {
            throw new UnableToRemoveCouponException("This customer has no coupons");
        }

        //clear the list of coupons
        customerCoupons.clear();

        //update the customer with empty list of coupons
        customerRepository.save(customer);

        //return empty list
        return customer.getCoupons();
    }

    @Transactional
    @Override
    public List<Coupon> addCouponToCustomer(long couponId, long customerId) {

        //fetch customer
        Customer customer = getCustomer(customerId);

        //fetch coupon
        Coupon coupon = getCoupon(couponId);

        //check if the coupon is in stock
        if (!coupon.isInStock()) {
            throw new CouponNotInStockException("The coupon is no longer in stock");
        }

        //assignment
        List<Coupon> customerCoupons = customer.getCoupons();

        //check if the customer already owns the coupon to prevent duplicate purchase
        if (customerCoupons.contains(coupon)) {
            throw new DuplicateCouponPurchaseException("This customer already has this coupon");
        }

        //reduce the amount of the coupon
        coupon.decrementAmount();

        //add the coupon to the list of the coupons of the customer
        customerCoupons.add(coupon);

        //update customer with updated list of coupons
        customerRepository.save(customer);

        //update the coupon with updated amount
        couponRepository.save(coupon);

        //return the list of coupons of the customer
        return customer.getCoupons();
    }

    @Transactional
    @Override
    public Account addCompanyCredit(long id, double amount) {
        //make sure the value is positive
        amount = Math.abs(amount);
        return modifyAccountCredit(id, amount, UserType.COMPANY);
    }

    @Transactional
    @Override
    public Account reduceCompanyCredit(long id, double amount) {
        //make sure the value is positive and then convert it to negative
        amount = Math.abs(amount) * (-1);
        return modifyAccountCredit(id, amount, UserType.COMPANY);
    }

    @Transactional
    @Override
    public Account addCustomerCredit(long id, double amount) {
        //make sure the value is positive
        amount = Math.abs(amount);
        return modifyAccountCredit(id, amount, UserType.CUSTOMER);
    }

    @Transactional
    @Override
    public Account reduceCustomerCredit(long id, double amount) {
        //make sure the value is positive and then convert it to negative
        amount = Math.abs(amount) * (-1);
        return modifyAccountCredit(id, amount, UserType.CUSTOMER);
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

    /*private methods below*/

    private <T extends Updateable<T>> void update(
            List<? extends Updateable<T>> sources,
            List<? extends Updateable<T>> targets
    ) {
        for (int i = 0; i < targets.size(); i++) {
            Updateable<T> target = targets.get(i);
            Updateable<T> source = sources.get(i);
            target.update(source);
        }
    }

    private List<Coupon> filterByCompany(long companyId, List<Coupon> couponsFromDb) {
        return couponsFromDb
                .stream()
                .filter(c -> c.getCompany().getId() == companyId)
                .collect(Collectors.toList());
    }

    private List<Long> mapCouponsToIds(List<Coupon> coupons) {
        return coupons
                .stream()
                .map(Coupon::getId)
                .collect(Collectors.toList());
    }

    private void validateAdminUpdate(Account account) {

        if (!(account.getUser() instanceof Admin)) {
            return;
        }

        if (!this.admin.isMain()) {
            throw new ForbiddenActionException(
                    "Not main Admins are prohibited from updating other admin accounts");
        }
    }

    private void adminValidation(User user) {
        if (user instanceof Admin && !admin.isMain()) {
            throw new ForbiddenActionException(
                    "Admins are not allowed to add other admins " +
                            "to the system unless the admin is main");
        }
    }

    private Account modifyAccountCredit(long id, double amount, String userType) {
        Account account = getAccountByUser(id, userType);
        account.setCredit(account.getCredit() + amount);
        return accountRepository.save(account);
    }

    private Admin getAdmin() {

        Admin adminFromDb = adminRepository.findById(admin.getId())
                .orElseThrow(() -> new NoSuchAdminException(
                        "Your admin user is terminated"));

        account.setUser(adminFromDb);
        return adminFromDb;
    }

    private Customer getCustomer(long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchCustomerException(
                        "This customer doesn't exist"));
    }

    private Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new NoSuchCouponException(
                        "This coupon doesn't exist"));
    }

    private Company getCompany(long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new NoSuchCompanyException(
                        "This company doesn't exist"));
    }

    private Account getAccount(long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchAccountException(
                        "This account doesn't exist"));
    }

    private Account getAccountByUser(long userId, String userRole) {
        return accountRepository.findByUser(userId, userRole)
                .orElseThrow(() -> new NoSuchAccountException(
                        "This user has no account"));
    }

    private Category getCategory(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchCategoryException("No such category"));
    }

    private void validateCoupon(Coupon coupon) {

        //assignment
        LocalDate startDate = coupon.getStartDate();
        LocalDate endDate = coupon.getEndDate();
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

    private void validateCategory(Category category) {

        if (category.getId() <= NO_ID) {
            throw new UnableToUpdateCategoryException("Unable to update category without id");
        }

        String categoryName = category.getName();
        if (null == categoryName || categoryName.isEmpty()) {
            throw new UnableToUpdateCategoryException("Unable to update category without name");
        }
    }

    private void checkCompanyNameDuplicate(Company companyFromDB, String name) {
        List<Company> duplicates = companyRepository.findByNameIgnoreCase(name);
        duplicates.forEach(c -> {
            boolean otherCompany = c.getId() != companyFromDB.getId();
            boolean duplicateName = c.getName().equalsIgnoreCase(name);
            if (otherCompany && duplicateName) {
                throwDuplicateCompanyNameException(String.format("The company name %s is already taken", name));
            }
        });
    }

    private void throwDuplicateCompanyNameException(String name) {
        throw new UnableToUpdateCompanyException(name);
    }

    private void assertCompanyUniqueName(User user) {
        if (user instanceof Company) {
            String name = ((Company) user).getName();
            List<Company> duplicates = companyRepository.findByNameIgnoreCase(name);
            if (!duplicates.isEmpty()) {
                throw new MalformedAccountException(String.format("The company name %s is already taken", name));
            }
        }
    }
}
