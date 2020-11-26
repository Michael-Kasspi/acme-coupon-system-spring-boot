package com.acme.couponsystem.db.search.filter.coupon;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.hibernate.search.annotations.Factory;

public class CouponCategoryFilter {

    private static final String ID = "category.id";
    private static final String NAME = "category.name";

    private String param;
    private String arg;

    public CouponCategoryFilter() {
        /*empty*/
    }

    @Factory
    public Query getFilter() {
        return new TermQuery(new Term(param, arg));
    }

    public void setId(String id) {
        this.param = ID;
        this.arg = id;
    }

    public void setName(String name) {
        this.param = NAME;
        this.arg = name;
    }
}
