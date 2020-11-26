package com.acme.couponsystem.db.search.factory;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.QueryBuilder;

import java.util.List;

public interface SortFactory {
    Sort getSort(QueryBuilder builder, List<String> sortList);
}
