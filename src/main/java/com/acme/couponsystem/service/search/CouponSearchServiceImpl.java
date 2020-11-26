package com.acme.couponsystem.service.search;

import com.acme.couponsystem.db.entity.Coupon;
import com.acme.couponsystem.db.repository.CouponRepository;
import com.acme.couponsystem.db.search.SearchRequest;
import com.acme.couponsystem.db.search.factory.FilterFactory;
import com.acme.couponsystem.db.search.factory.MergeFactory;
import com.acme.couponsystem.db.search.factory.RangeFactory;
import com.acme.couponsystem.db.search.factory.SortFactory;
import com.acme.couponsystem.db.search.meta.CouponMeta;
import org.apache.logging.log4j.util.Strings;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CouponSearchServiceImpl implements CouponSearchService {

    private static final int NONE = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;
    @PersistenceContext
    private EntityManager entityManager;
    private CouponRepository repository;
    private QueryBuilder qBuilder;
    private FilterFactory filterFactory;
    private RangeFactory rangeFactory;
    private SortFactory sortFactory;
    private MergeFactory mergeFactory;

    @Autowired
    public CouponSearchServiceImpl(
            CouponRepository repository,
            @Qualifier("couponQueryBuilder") QueryBuilder qBuilder,
            FilterFactory filterFactory,
            RangeFactory rangeFactory,
            SortFactory sortFactory,
            MergeFactory mergeFactory) {
        this.repository = repository;
        this.qBuilder = qBuilder;
        this.filterFactory = filterFactory;
        this.rangeFactory = rangeFactory;
        this.sortFactory = sortFactory;
        this.mergeFactory = mergeFactory;
    }

    @Transactional
    @Override
    public Page<Coupon> search(SearchRequest request) {

        /*validation*/
        validateSize(request);

        /*list to combine the queries to one boolean Query*/
        List<Query> queries = new ArrayList<>();

        /*the main query with the search text*/
        queries.add(getQuery(request));

        /*get all the range queries*/
        queries.addAll(getRange(request));

        /*merge the queries*/
        Query mergedQueries = mergeFactory.merge(qBuilder, queries);

        /*convert to FullTextQuery for further processing*/
        FullTextQuery fullTextQuery = getFullTextQuery(mergedQueries);

        /*pagination*/
        Pageable pageRequest = request.getPageRequest();
        setPagination(pageRequest, fullTextQuery);

        /*sorting*/
        setSort(request, fullTextQuery);

        /*filtering*/
        setFilter(request, fullTextQuery);

        /*get results and results size*/
        return getPage(fullTextQuery, pageRequest);
    }

    private List<Query> getRange(SearchRequest request) {
        List<String> rangeList = request.getRange();

        if (Objects.nonNull(rangeList) && !rangeList.isEmpty()) {
            return rangeFactory.getRangeQueries(qBuilder, rangeList, Coupon.class);
        }
        return new ArrayList<>();
    }

    private Page<Coupon> getPage(FullTextQuery fullTextQuery, Pageable pageRequest) {
        List<Coupon> resultList = repository.search(fullTextQuery);
        long resultSize = repository.getResultSize(fullTextQuery);

        return new PageImpl<>(resultList, pageRequest, resultSize);
    }

    private void setFilter(SearchRequest request, FullTextQuery fullTextQuery) {
        List<String> filterList = request.getFilter();

        if (Objects.nonNull(filterList) && !filterList.isEmpty()) {
            filterFactory.applyFilters(fullTextQuery, filterList);
        }
    }

    private void setSort(SearchRequest request, FullTextQuery fullTextQuery) {
        List<String> sortList = request.getSort();

        if (Objects.nonNull(sortList) && !sortList.isEmpty()) {
            Sort sort = sortFactory.getSort(qBuilder, sortList);
            fullTextQuery.setSort(sort);
        }
    }

    private void validateSize(SearchRequest request) {
        int size = request.getSize();

        if (size == NONE) {
            request.setSize(DEFAULT_PAGE_SIZE);
        } else if (size > MAX_PAGE_SIZE) {
            request.setSize(MAX_PAGE_SIZE);
        }
    }

    private void setPagination(Pageable pageRequest, FullTextQuery fullTextQuery) {
        fullTextQuery.setFirstResult((int) pageRequest.getOffset());
        fullTextQuery.setMaxResults(pageRequest.getPageSize());
    }

    private FullTextQuery getFullTextQuery(Query mergedQueries) {
        return getFullTextEntityManager()
                .createFullTextQuery(mergedQueries, Coupon.class);
    }

    private Query getQuery(SearchRequest request) {

        if (Strings.isBlank(request.getQuery())) {
            return qBuilder
                    .all()
                    .createQuery();
        }

        if (request.isAdvanced()) {
            return qBuilder
                    .simpleQueryString()
                    .onFields(CouponMeta.TITLE, CouponMeta.DESC)
                    .matching(request.getQuery())
                    .createQuery();
        }
        return qBuilder
                .keyword()
                .fuzzy().withEditDistanceUpTo(2)
                .onFields(CouponMeta.TITLE, CouponMeta.DESC)
                .matching(request.getQuery())
                .createQuery();
    }

    @Transactional
    private FullTextEntityManager getFullTextEntityManager() {
        return Search.getFullTextEntityManager(entityManager);
    }

    public QueryBuilder getqBuilder() {
        return qBuilder;
    }

    public void setqBuilder(QueryBuilder qBuilder) {
        this.qBuilder = qBuilder;
    }

    public CouponRepository getRepository() {
        return repository;
    }

    public void setRepository(CouponRepository repository) {
        this.repository = repository;
    }

    public MergeFactory getMergeFactory() {
        return mergeFactory;
    }

    public void setMergeFactory(MergeFactory mergeFactory) {
        this.mergeFactory = mergeFactory;
    }
}
