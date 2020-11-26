package com.acme.couponsystem.db.entity;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Indexed(index = "User")
@Entity
public class Admin extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean main;

    public Admin() {
        /*empty*/
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", main=" + main +
                '}';
    }

    @Override
    public void update(Updateable<? extends User> updateable) {
        Admin admin = (Admin) updateable;
        main = admin.main;
    }
}
