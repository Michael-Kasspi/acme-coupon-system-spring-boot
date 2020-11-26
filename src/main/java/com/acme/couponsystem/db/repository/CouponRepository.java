package com.acme.couponsystem.db.repository;

import com.acme.couponsystem.db.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponSearchRepository {

    List<Coupon> deleteByEndDateBefore(LocalDate date);

    List<Coupon> findByCompanyId(long id);

    List<Coupon> findByCategoryId(long id);

}
