package com.acme.couponsystem.db.repository;

import com.acme.couponsystem.db.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
