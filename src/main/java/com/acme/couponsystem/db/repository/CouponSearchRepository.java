package com.acme.couponsystem.db.repository;

import com.acme.couponsystem.db.entity.Coupon;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextQuery;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CouponSearchRepository {
     List<Coupon> search(FullTextQuery fullTextQuery);
     long getResultSize(FullTextQuery fullTextQuery);
}
