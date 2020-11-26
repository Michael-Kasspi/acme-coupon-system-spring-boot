package com.acme.couponsystem.db.search.factory;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.QueryBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class MergeFactoryImpl implements MergeFactory {

    private static volatile MergeFactoryImpl instance;
    private static final Object mutex = new Object();

    private MergeFactoryImpl() {
        /*empty*/
    }

    public static MergeFactoryImpl getInstance() {
        MergeFactoryImpl result = instance;

        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    result = instance = new MergeFactoryImpl();
                }
            }
        }

        return result;
    }

    @Override
    public Query merge(QueryBuilder builder, List<Query> queries) {
        List<BooleanClause> clauses = getClauses(queries);
        return mergeQueries(builder,clauses);
}

    private List<BooleanClause> getClauses(List<Query> queries) {
        return queries.
                stream()
                .map(query -> new BooleanClause(query, BooleanClause.Occur.MUST))
                .collect(Collectors.toList());
    }

    private Query mergeQueries(QueryBuilder builder, List<BooleanClause> clauses) {
        BooleanQuery.Builder bb = new BooleanQuery.Builder();
        clauses.forEach(bb::add);
        return bb.build();
    }
}
