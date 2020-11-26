package com.acme.couponsystem.db.entity;

import com.acme.couponsystem.db.meta.DbMeta;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Indexed(index = "Account")
@Entity
public class Account implements Updateable<Account>{

    private static final double NO_CREDIT = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Field
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Column(nullable = false)
    private String password;
    private String profilePictureUrl;
    @Any(metaDef = "AccountMetaDef", metaColumn = @Column(name = DbMeta.COLUMN_USER_ROLE))
    @AnyMetaDef(name = "AccountMetaDef", metaType = "string", idType = "long", metaValues = {
            @MetaValue(value = UserType.ADMIN, targetEntity = Admin.class),
            @MetaValue(value = UserType.COMPANY, targetEntity = Company.class),
            @MetaValue(value = UserType.CUSTOMER, targetEntity = Customer.class)
    })
    @NotNull(message = "Account must have a user")
    @IndexedEmbedded
    @JoinColumn(name = DbMeta.COLUMN_USER_ID)
    @Cascade(CascadeType.ALL)
    private User user;
    @Field
    @FieldBridge(impl = Credit.class)
    @IndexedEmbedded
    @ManyToOne(cascade = javax.persistence.CascadeType.PERSIST)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "credit_id")
    private Credit credit;
    @Field
    @Column(name = "first_name")
    private String firstName;
    @Field
    @Column(name = "last_name")
    private String lastName;

    public Account() {
        /*empty*/
    }

    public Account(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getCredit() {
        if (Objects.isNull(credit)) {
            this.credit = new Credit(NO_CREDIT);
        }
        return credit.getAmount();
    }

    public void setCredit(double credit) {

        if (Objects.isNull(this.credit)) {
            this.credit = new Credit(credit);
        }

        this.credit.setAmount(credit);
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", user=" + user +
                ", credit=" + credit +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    /**
     * Will only assign fields that should be modified via update.
     * null fields are ignored.
     *
     * @param updateable the source to update from
     */
    public void update(Updateable<? extends Account> updateable) {
        Account account = (Account) updateable;
        if (null != account.email) email = account.email;
        if (null != account.password) password = account.password;
        if (null != account.lastName) lastName = account.lastName;
        if (null != account.firstName) firstName = account.firstName;
    }
}
