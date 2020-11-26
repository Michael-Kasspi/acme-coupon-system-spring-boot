package com.acme.couponsystem.service;

import com.acme.couponsystem.db.entity.*;
import com.acme.couponsystem.db.repository.AccountRepository;
import com.acme.couponsystem.db.repository.CompanyRepository;
import com.acme.couponsystem.db.repository.CouponRepository;
import com.acme.couponsystem.db.repository.CustomerRepository;
import com.acme.couponsystem.service.ex.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerServiceImpl implements CustomerService {

    private static final int FIRST = 0;
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private CouponRepository couponRepository;
    private Account account;

    @Autowired
    public CustomerServiceImpl(
            AccountRepository accountRepository,
            CustomerRepository customerRepository,
            CouponRepository couponRepository, CompanyRepository companyRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String getUserType() {
        return UserType.CUSTOMER;
    }

    @Transactional
    @Override
    public List<Coupon> findAllCoupons() {
        List<Coupon> coupons = getCustomer().getCoupons();
        Hibernate.initialize(coupons);
        return coupons;
    }

    @Transactional
    @Override
    public Account purchaseCoupon(long id) {

        //fetch customer
        Account customerAccount = getAccount();
        Customer customer = (Customer) customerAccount.getUser();
        //fetch the coupon
        Coupon coupon = getCoupon(id);

        //check if the coupon is already purchased
        if (customer.getCoupons().contains(coupon)) {
            String message = String.format("The '%s' coupon is already purchased", coupon.getTitle());
            throw new DuplicateCouponPurchaseException(message);
        }

        checkCouponInStock(coupon);

        //assignment
        double customerCredit = customerAccount.getCredit();
        double price = coupon.getPrice();

        checkCreditSufficiency(customerCredit, Collections.singletonList(coupon));
        //reduce credits by the price amount
        customerAccount.setCredit(customerCredit - price);

        long companyId = coupon.getCompany().getId();

        /*add credits to the companies account*/
        addCouponValueToCompany(coupon);
        //decrement the coupon amount
        coupon.decrementAmount();
        /*increment the coupon popularity*/
        coupon.incrementPopularity();
        //add the coupon to the list of customer's coupons
        customer.getCoupons().add(coupon);
        // remove the coupon from cart
        customer.getCart().remove(coupon);
        customer.getWishlist().remove(coupon);

        //set the customer in the customerAccount
        this.account.setUser(customer);
        couponRepository.save(coupon);
        return accountRepository.save(customerAccount);
    }

    @Transactional
    @Override
    public Account purchaseCoupons(List<Coupon> coupons) {
        //fetching
        Account account = getAccount();
        Customer customer = (Customer) account.getUser();
        List<Coupon> customerCoupons = customer.getCoupons();

        //remove duplicates
        List<Coupon> distinctCoupons = removeDuplicates(coupons);

        // fetch the coupons from db to make sure only existing coupons are purchased
        List<Long> couponsIds = mapCouponsToId(distinctCoupons);
        List<Coupon> couponsToPurchase = couponRepository.findAllById(couponsIds);

        validateAllCouponExist(distinctCoupons, couponsToPurchase);

        List<Coupon> couponsNotInStock = filterCouponsNotInStock(couponsToPurchase);
        if (couponsNotInStock.size() > 0) {
            throw new CouponNotInStockException("Some of the coupons not in stock");
        }

        // avoid duplicate purchase
        checkDuplicateCoupons(customerCoupons, couponsToPurchase);

        // check if there is enough credits to purchase the coupons
        double accountCredit = account.getCredit();
        double totalAmount = checkCreditSufficiency(accountCredit, couponsToPurchase);

        // reduce the total amount form the customers credit
        account.setCredit(accountCredit - totalAmount);

        // add the coupons value to the company
        couponsToPurchase.forEach(this::addCouponValueToCompany);

        // reduce the coupon stock amount and increase popularity
        couponsToPurchase.forEach(c -> {
            c.decrementAmount();
            c.incrementPopularity();
        });

        // add the coupons to the customers coupon list
        customerCoupons.addAll(couponsToPurchase);

        // remove the coupons from cart and wishlist
        customer.getCart().removeAll(couponsToPurchase);
        customer.getWishlist().removeAll(couponsToPurchase);

        couponRepository.saveAll(couponsToPurchase);
        return accountRepository.save(account);
    }

    private void validateAllCouponExist(List<Coupon> coupons, List<Coupon> couponsFromDb) {
        if  (coupons.size() != couponsFromDb.size()) {
            throw new NoSuchCouponException("Unable to complete the purchase, some coupons don't exist");
        }
    }

    private List<Coupon> filterCouponsNotInStock(List<Coupon> couponsToPurchase) {
        return couponsToPurchase.stream().filter(coupon -> !coupon.isInStock()).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void removeCoupon(long id) {
        //fetching
        Customer customer = getCustomer();
        Coupon coupon = getCoupon(id);
        //assignment
        List<Coupon> coupons = customer.getCoupons();
        //check if the customer owns the coupon
        if (!coupons.contains(coupon)) {
            throw new UnableToRemoveCouponException("This coupon is no longer owned");
        }
        //remove the coupon from the customer
        coupons.remove(coupon);
        //update the customer with the list without the coupon
        customerRepository.save(customer);
    }

    @Transactional
    @Override
    public Account purchaseCredits(double amount) {
        Account account = getAccount();
        account.setCredit(account.getCredit() + Math.abs(amount));
        return accountRepository.save(account);
    }

    @Transactional
    @Override
    public Coupon addCouponToCart(long id) {

        //fetching
        Customer customer = getCustomer();
        Coupon coupon = getCoupon(id);

        checkCouponInStock(coupon);
        checkDuplicateCoupon(customer, coupon);

        List<Coupon> cart = customer.getCart();

        if (!cart.contains(coupon)) {
            cart.add(coupon);
            customerRepository.save(customer);
        }

        return coupon;
    }

    @Transactional
    @Override
    public List<Coupon> addCouponsToCart(List<Coupon> coupons) {
        //fetching
        Customer customer = getCustomer();

        List<Coupon> couponsToCart = couponCartProcedure(coupons, customer);

        // add items to cart
        List<Coupon> cart = customer.getCart();
        cart.addAll(couponsToCart);

        customerRepository.save(customer);
        return cart;
    }

    @Override
    public List<Coupon> updateCouponsToCart(List<Coupon> coupons) {
        // fetching
        Customer customer = getCustomer();
        List<Coupon> couponsToCart = couponCartProcedure(coupons, customer);
        // override cart items
        customer.setCart(couponsToCart);

        customerRepository.save(customer);
        return couponsToCart;
    }

    private List<Coupon> couponCartProcedure(List<Coupon> coupons, Customer customer) {
        // remove duplicates
        List<Coupon> distinctCoupons = removeDuplicates(coupons);
        // fetch the coupons form db to make sure all coupons exits
        List<Coupon> couponsFromDb = couponRepository.findAllById(mapCouponsToId(distinctCoupons));
        validateAllCouponExist(distinctCoupons, couponsFromDb);
        // remove coupons already purchased
        couponsFromDb.removeAll(customer.getCoupons());
        return couponsFromDb;
    }

    @Transactional
    @Override
    public void removeCouponFromCart(long id) {
        //fetching
        Customer customer = getCustomer();
        Coupon coupon = getCoupon(id);

        List<Coupon> cart = customer.getCart();
        cart.remove(coupon);

        customerRepository.save(customer);
    }

    @Transactional
    @Override
    public void removeAllCouponsFromCart() {
        Customer customer = getCustomer();
        List<Coupon> cart = customer.getCart();
        cart.clear();
        customerRepository.save(customer);
    }

    @Transactional
    @Override
    public List<Coupon> findCartCoupons() {
        List<Coupon> cart = getCustomer().getCart();
        Hibernate.initialize(cart);
        return cart;
    }

    @Transactional
    @Override
    public Coupon addCouponToWishlist(long id) {
        //fetching
        Customer customer = getCustomer();
        Coupon coupon = getCoupon(id);

        checkDuplicateCoupon(customer, coupon);

        List<Coupon> wishlist = customer.getWishlist();
        wishlist.add(coupon);

        if (!wishlist.contains(coupon)) {
            wishlist.add(coupon);
            customerRepository.save(customer);
        }

        return coupon;
    }

    @Transactional
    @Override
    public List<Coupon> updateCouponsWishlist(List<Coupon> coupons) {
        //fetching
        Customer customer = getCustomer();

        checkDuplicateCoupons(customer.getCoupons(), coupons);

        List<Coupon> wishlist = customer.getWishlist();
        wishlist.addAll(coupons);
        customerRepository.save(customer);

        return wishlist;
    }

    @Transactional
    @Override
    public void removeCouponFromWishlist(long id) {
        //fetching
        Customer customer = getCustomer();
        Coupon coupon = getCoupon(id);

        List<Coupon> wishlist = customer.getWishlist();
        wishlist.remove(coupon);

        customerRepository.save(customer);
    }

    @Transactional
    @Override
    public void removeAllCouponsFromWishlist() {
        Customer customer = getCustomer();
        customer.setWishlist(null);
        customerRepository.save(customer);
    }

    @Transactional
    @Override
    public List<Coupon> findWishlistCoupons() {
        List<Coupon> wishlist = getCustomer().getWishlist();
        Hibernate.initialize(wishlist);
        return wishlist;
    }

    private List<Account> getCompanyAccount(long id) {
        return accountRepository.findAllByUser(id, UserType.COMPANY);
    }

    @Override
    public Account getAccount() {
        return accountRepository.findById(account.getId())
                .orElseThrow(() -> new NoSuchAccountException(
                        "This account doesn't exist"));
    }

    public Customer getCustomer() {
        long customerId = account.getUser().getId();
        Customer customer = customerRepository.findById(customerId).
                orElseThrow(() -> new NoSuchCustomerException("This customer doesn't exist"));
        account.setUser(customer);
        return customer;
    }

    private Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new NoSuchCouponException("This coupon doesn't exist"));
    }

    private void addCouponValueToCompany(Coupon coupon) {

        long companyId = coupon.getCompany().getId();
        double price = coupon.getPrice();

        List<Account> companyAccounts = getCompanyAccount(companyId);
        /*the company might not have an account*/
        if (!companyAccounts.isEmpty()) {
            Account companyAccount = companyAccounts.get(FIRST);
            // add credit to the company's customerAccount worth the coupons price
            companyAccount.setCredit(companyAccount.getCredit() + price);
            accountRepository.save(companyAccount);
        }
    }

    private double checkCreditSufficiency(double customerCredit, List<Coupon> coupons) {
        double totalPrice = sumCouponsPrice(coupons);

        if (customerCredit < totalPrice) {
            throw new InsufficientCreditAmountException(
                    "Not enough credits to complete the purchase"
            );
        }
        return totalPrice;
    }

    private List<Coupon> removeDuplicates(List<Coupon> coupons) {
        return coupons
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    private double sumCouponsPrice(List<Coupon> couponsToPurchase) {
        return couponsToPurchase
                .stream()
                .mapToDouble(Coupon::getPrice)
                .sum();
    }

    private List<Long> mapCouponsToId(List<Coupon> coupons) {
        return coupons
                .stream()
                .map(Coupon::getId)
                .collect(Collectors.toList());
    }

    private void checkDuplicateCoupon(Customer customer, Coupon coupon) {
        List<Coupon> customerCoupons = customer.getCoupons();
        if (customerCoupons.contains(coupon)) {
            throw new DuplicateCouponPurchaseException("This coupon is already owned");
        }
    }

    private void checkCouponInStock(Coupon coupon) {
        if (!coupon.isInStock()) {
            throw new CouponNotInStockException("This coupon is no longer in stock");
        }
    }

    private void checkDuplicateCoupons(List<Coupon> customerCoupons, List<Coupon> couponsToPurchase) {
        boolean duplicatePurchase = couponsToPurchase.removeAll(customerCoupons);

        if (duplicatePurchase) {
            throw new DuplicateCouponPurchaseException("Some of the coupons are already purchased");
        }
    }
}
