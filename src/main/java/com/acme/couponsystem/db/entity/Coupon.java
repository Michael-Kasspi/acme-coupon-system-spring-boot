package com.acme.couponsystem.db.entity;

import com.acme.couponsystem.db.converter.LocalDateToSqlDateConverter;
import com.acme.couponsystem.db.search.filter.coupon.CouponCategoryFilter;
import com.acme.couponsystem.db.search.filter.coupon.CouponCompanyFilter;
import com.acme.couponsystem.db.search.filter.coupon.CouponCustomerFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Indexed(index = "Coupon")
@FullTextFilterDef(name = "category", impl = CouponCategoryFilter.class)
@FullTextFilterDef(name = "company", impl = CouponCompanyFilter.class)
@FullTextFilterDef(name = "customer", impl = CouponCustomerFilter.class)
public class Coupon implements Updateable<Coupon> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @ManyToOne
    @IndexedEmbedded(depth = 1, includeEmbeddedObjectId = true)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @NotNull
    @ContainedIn
    @IndexedEmbedded(depth = 1, includeEmbeddedObjectId = true)
    @ManyToOne
    @NotNull
    @JoinColumn(name = "category", nullable = false)
    private Category category;
    @NotNull
    @Field
    @Column(nullable = false)
    private String title;
    @Field
    private String description;
    @NotNull
    @DateBridge(resolution = Resolution.DAY)
    @SortableField
    @Field(analyze = Analyze.NO)
    @Column(nullable = false)
    @Convert(converter = LocalDateToSqlDateConverter.class)
    private LocalDate startDate;
    @NotNull
    @SortableField
    @Field(analyze = Analyze.NO)
    @DateBridge(resolution = Resolution.DAY)
    @Column(nullable = false)
    @Convert(converter = LocalDateToSqlDateConverter.class)
    private LocalDate endDate;
    @SortableField
    @NumericField
    @Field(analyze = Analyze.NO)
    private Integer amount = 0;
    @NumericField
    @SortableField
    @Field(analyze = Analyze.NO)
    private Double price;
    @NumericField
    @SortableField
    @Field(analyze = Analyze.NO)
    private Long popularity = 0L;
    private String imageUrl;

    @ContainedIn
    @IndexedEmbedded(depth = 1, includeEmbeddedObjectId = true)
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "customer_coupon",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> customers;

    @ContainedIn
    @IndexedEmbedded(depth = 1, includeEmbeddedObjectId = true)
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "customer_cart",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> customersCart;

    @ContainedIn
    @IndexedEmbedded(depth = 1, includeEmbeddedObjectId = true)
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "customer_wishlist",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> customersWishList;

    public Coupon()
    {
        /*empty*/
    }

    public Coupon(long id)
    {
        this.id = id;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Company getCompany()
    {
        return company;
    }

    public void setCompany(Company company)
    {
        this.company = company;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public List<Customer> getCustomers()
    {
        return customers;
    }

    public void setCustomers(List<Customer> customers)
    {
        this.customers = customers;
    }

    public boolean isInStock()
    {
        return amount > 0;
    }

    public void decrementAmount()
    {
        if (null == amount) {
            amount = -1;
        } else {
            amount = amount - 1;
        }
    }

    public void incrementPopularity()
    {
        if (null == popularity) {
            popularity = 1L;
        } else {
            popularity = popularity + 1;
        }
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<Customer> getCustomersCart()
    {
        return customersCart;
    }

    public void setCustomersCart(List<Customer> customersCart)
    {
        this.customersCart = customersCart;
    }

    public List<Customer> getCustomersWishList()
    {
        return customersWishList;
    }

    public void setCustomersWishList(List<Customer> customersWishList)
    {
        this.customersWishList = customersWishList;
    }

    public Long getPopularity()
    {
        return popularity;
    }

    public void setPopularity(Long popularity)
    {
        this.popularity = popularity;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return id == coupon.id;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    /**
     * Will only assign fields that should be modified via update.
     * null fields are ignored.
     *
     * @param updateable the coupon to be updated
     */
    @Override
    public void update(Updateable<? extends Coupon> updateable)
    {
        Coupon coupon = (Coupon) updateable;
        if (null != coupon.category) category = coupon.category;
        if (null != coupon.title) title = coupon.title;
        if (null != coupon.description) description = coupon.description;
        if (null != coupon.startDate) startDate = coupon.startDate;
        if (null != coupon.endDate) endDate = coupon.endDate;
        if (null != coupon.amount) amount = coupon.amount;
        if (null != coupon.price) price = coupon.price;
        if (null != coupon.imageUrl) imageUrl = coupon.imageUrl;
    }

    @Override
    public String toString()
    {
        return "Coupon{" +
                "id=" + id +
                '}';
    }
}
