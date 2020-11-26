package com.acme.couponsystem.db.search.config;

import com.acme.couponsystem.db.entity.Coupon;
import com.acme.couponsystem.db.entity.User;
import com.acme.couponsystem.db.search.factory.*;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Configuration
public class SearchConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    public SearchConfiguration() {
        /*empty*/
    }
    @Transactional
    public FullTextEntityManager getFullTextEntityManager() {
        return Search.getFullTextEntityManager(entityManager);
    }

    @Transactional
    @Bean("couponQueryBuilder")
    public QueryBuilder getCouponQueryBuilder() {
        return getFullTextEntityManager().getSearchFactory()
                .buildQueryBuilder().forEntity(Coupon.class).get();
    }

    @Transactional
    @Bean("userQueryBuilder")
    public QueryBuilder getUserQueryBuilder(){
        return getFullTextEntityManager().getSearchFactory()
                .buildQueryBuilder().forEntity(User.class).get();
    }

    @Bean("filterFactory")
    public FilterFactory getFilterFactory(){
        return FilterFactoryImpl.getInstance();
    }

    @Bean("rangeFactory")
    public RangeFactory getRangeFactory(){
        return RangeFactoryImpl.getInstance();
    }

    @Bean("sortFactory")
    public SortFactory getSortFactory(){
        return SortFactoryImpl.getInstance();
    }

    @Bean("mergeFactory")
    public MergeFactory getMergeFactory(){
        return MergeFactoryImpl.getInstance();
    }

}
