package com.acme.couponsystem.db.rest.controller;

import com.acme.couponsystem.db.entity.Coupon;
import com.acme.couponsystem.db.search.SearchRequestImpl;
import com.acme.couponsystem.service.search.CouponSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/search/")
public class SearchController {

    private CouponSearchService service;

    @Autowired
    public SearchController(CouponSearchService service) {
        this.service = service;
    }

    @GetMapping("coupons")
    ResponseEntity<Page<Coupon>> search(SearchRequestImpl request){
        return ResponseEntity.ok(service.search(request));
    }

    public CouponSearchService getService() {
        return service;
    }

    public void setService(CouponSearchService service) {
        this.service = service;
    }
}
