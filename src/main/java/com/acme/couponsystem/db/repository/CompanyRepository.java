package com.acme.couponsystem.db.repository;

import com.acme.couponsystem.db.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByNameIgnoreCase(String name);
}
