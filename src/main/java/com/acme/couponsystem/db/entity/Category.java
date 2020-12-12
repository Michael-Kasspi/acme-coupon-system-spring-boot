package com.acme.couponsystem.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Indexed(index = "Category")
public class Category implements Updateable<Category> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Field
    @Column(nullable = false)
    private String name;
    @NotNull
    @Field
    @Column(nullable = false)
    private String description;
    @JsonIgnore
    @ContainedIn
    @OneToMany(mappedBy = "category")
    private List<Coupon> couponsOfCategory;

    public Category() {
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

    public List<Coupon> getCouponsOfCategory() {
        return couponsOfCategory;
    }

    public void setCouponsOfCategory(List<Coupon> couponsOfCategory) {
        this.couponsOfCategory = couponsOfCategory;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public void update (Updateable<? extends Category> updateable){
        Category category = (Category) updateable;
        if (null != category.name) name = category.name;
        if (null != category.description) description = category.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
