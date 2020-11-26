package com.acme.couponsystem.db.search.factory;

import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.QueryBuilder;

import java.util.List;

public interface MergeFactory {
    Query merge(QueryBuilder builder, List<Query> queries);
}
