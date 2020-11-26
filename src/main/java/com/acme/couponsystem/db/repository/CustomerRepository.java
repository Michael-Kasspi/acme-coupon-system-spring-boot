package com.acme.couponsystem.db.repository;

import com.acme.couponsystem.db.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import static javafx.scene.input.KeyCode.T;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
