package com.acme.couponsystem.service.search;

import com.acme.couponsystem.db.entity.Coupon;
import com.acme.couponsystem.db.search.SearchRequest;
import org.springframework.data.domain.Page;

public interface CouponSearchService {
    Page<Coupon> search(SearchRequest request);
}
