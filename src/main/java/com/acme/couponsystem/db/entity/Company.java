package com.acme.couponsystem.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Indexed(index = "User")
@Entity
public class Company extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Field
    @Column(nullable = false)
    private String name;
    @Field
    private String description;
    @Field
    private String imageUrl;
    @JsonIgnore
    @ContainedIn
    @IndexedEmbedded(depth = 1, includeEmbeddedObjectId = true)
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Coupon> coupons;

    public Company() {
        /*empty*/
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Will only assign fields that should be modified via update.
     * null fields are ignored.
     *
     * @param updateable the company to update from
     */
    @Override
    public void update(Updateable<? extends User> updateable) {
        Company company = (Company) updateable;
        if (null != company.name) name = company.name;
        if (null != company.description) description = company.description;
    }
}
