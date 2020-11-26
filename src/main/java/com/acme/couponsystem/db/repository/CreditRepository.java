package com.acme.couponsystem.db.repository;

import com.acme.couponsystem.db.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit,Long> {
}
