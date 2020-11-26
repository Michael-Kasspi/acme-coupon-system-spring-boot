package com.acme.couponsystem.db.search;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class SearchRequestImpl implements SearchRequest {

    private String query;
    private boolean advanced;
    private int page;
    private int size;
    private List<String> sort;
    private List<String> filter;
    private List<String> range;

    public SearchRequestImpl() {
        query = "";
        sort = new ArrayList<>();
        filter = new ArrayList<>();
        range = new ArrayList<>();
    }

    @Override
    public String getQuery() {
        return query;
    }

    public void setQuery(String q) {
        this.query = q;
    }

    @Override
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    @Override
    public List<String> getFilter() {
        return filter;
    }

    public void setFilter(List<String> filter) {
        this.filter = filter;
    }

    @Override
    public List<String> getRange() {
        return range;
    }


    public void setRange(List<String> range) {
        this.range = range;
    }

    @Override
    public Pageable getPageRequest() {
        return PageRequest.of(page, size);
    }

    @Override
    public boolean isAdvanced() {
        return advanced;
    }

    public void setAdvanced(boolean advanced) {
        this.advanced = advanced;
    }
}
