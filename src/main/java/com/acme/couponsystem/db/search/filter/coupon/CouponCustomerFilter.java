package com.acme.couponsystem.db.search.filter.coupon;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.hibernate.search.annotations.Factory;

public class CouponCustomerFilter {

    private static final String FIELD = "customers.id";

    private String id;

    public CouponCustomerFilter() {
        /*empty*/
    }

    @Factory
    public Query getFilter() {
        return new TermQuery(new Term(FIELD, id));
    }

    public void setId(String id) {
        this.id = id;
    }
}
