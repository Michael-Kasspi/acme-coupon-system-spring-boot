package com.acme.couponsystem.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.List;

@Indexed(index = "User")
@Entity
public class Customer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ContainedIn
    @IndexedEmbedded(depth = 1, includeEmbeddedObjectId = true)
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "customer_coupon",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private List<Coupon> coupons;

    @ContainedIn
    @IndexedEmbedded(depth = 1, includeEmbeddedObjectId = true)
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "customer_cart",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private List<Coupon> cart;

    @ContainedIn
    @IndexedEmbedded(depth = 1, includeEmbeddedObjectId = true)
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "customer_wishlist",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private List<Coupon> wishlist;

    public Customer() {
        /*empty*/
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public List<Coupon> getCart() {
        return cart;
    }

    public void setCart(List<Coupon> cart) {
        this.cart = cart;
    }

    public List<Coupon> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<Coupon> wishlist) {
        this.wishlist = wishlist;
    }

    @Override
    public void update(Updateable<? extends User> updateable) {
        Customer customer = (Customer) updateable;
        if (null != customer.coupons) this.coupons = customer.coupons;
        if (null != customer.cart) this.cart = customer.cart;
        if (null != customer.wishlist) this.wishlist = customer.wishlist;
    }
}
