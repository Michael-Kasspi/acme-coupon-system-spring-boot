package com.acme.couponsystem.db.search.factory;

import org.hibernate.search.jpa.FullTextQuery;

import java.util.List;

public interface FilterFactory {
    void applyFilters(FullTextQuery query, List<String> filterList);
}
