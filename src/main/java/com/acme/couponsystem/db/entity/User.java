package com.acme.couponsystem.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.MappedSuperclass;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class, name = UserType.ADMIN),
        @JsonSubTypes.Type(value = Company.class, name = UserType.COMPANY),
        @JsonSubTypes.Type(value = Customer.class, name = UserType.CUSTOMER)
})
@MappedSuperclass
@JsonIgnoreProperties("hibernateLazyInitializer")
public abstract class User implements Updateable<User> {

    public User() {
        /*empty*/
    }

    public abstract long getId();

    public abstract void setId(long id);
}
