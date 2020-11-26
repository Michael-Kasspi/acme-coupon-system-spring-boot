package com.acme.couponsystem.db.search;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchRequest {
    String getQuery();
    void setQuery(String query);
    boolean isAdvanced();
    void setAdvanced(boolean isAdvanced);
    int getPage();
    void setPage(int page);
    int getSize();
    void setSize(int size);
    List<String> getSort();
    void setSort(List<String> sort);
    List<String> getFilter();
    void setFilter(List<String> filter);
    List<String> getRange();
    void setRange(List<String> range);
    Pageable getPageRequest();
}
