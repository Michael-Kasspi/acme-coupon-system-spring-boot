package com.acme.couponsystem.db.repository;

import com.acme.couponsystem.db.entity.Coupon;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class CouponSearchRepositoryImpl implements CouponSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public CouponSearchRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public List<Coupon> search(FullTextQuery fullTextQuery) {
        return fullTextQuery.getResultList();
    }

    @Transactional
    @Override
    public long getResultSize(FullTextQuery fullTextQuery) {
        return fullTextQuery.getResultSize();
    }

    private FullTextEntityManager getFullTextEntityManager() {
        return Search.getFullTextEntityManager(entityManager);
    }
}
