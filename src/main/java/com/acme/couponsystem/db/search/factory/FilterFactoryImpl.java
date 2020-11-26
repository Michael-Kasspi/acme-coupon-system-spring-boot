package com.acme.couponsystem.db.search.factory;

import com.acme.couponsystem.db.search.factory.ex.FilterParsingException;
import org.hibernate.search.jpa.FullTextQuery;

import java.util.List;

/**
 * This Factory class is immutable and used to parse String List of filters,
 * And apply them on a FullTextQuery.
 */
public class FilterFactoryImpl implements FilterFactory {

    private static final String SEMICOLON = ";";
    private static final String DELIMITER = SEMICOLON;
    private static final int REQ_ARGS_AMOUNT = 3;
    private static final int NAME = 0;
    private static final int PARAM = 1;
    private static final int ARG = 2;

    private static volatile FilterFactoryImpl instance;
    private static final Object mutex = new Object();

    private FilterFactoryImpl() {
        /*empty*/
    }

    public static FilterFactoryImpl getInstance(){
        FilterFactoryImpl result = instance;

        if (result == null){
            synchronized (mutex){
                result = instance;
                if (result == null){
                    result = instance = new FilterFactoryImpl();
                }
            }
        }

        return result;
    }

    /**
     * Enables FullTextFilters by parsing the filterList and applying the filters to the query.
     */
    @Override
    public void applyFilters(FullTextQuery query, List<String> filterList) {

        filterList
                .stream()
                .map(this::parse)
                .forEachOrdered(fo -> applyFilter(fo, query));
    }

    /**
     * Enable FullTextFilter using the FilterOption fields
     *
     * @param filterOption contains the fields for the filter creation arguments
     */
    private void applyFilter(FilterOption filterOption, FullTextQuery query) {
        //assignment
        String name = filterOption.getName();
        String param = filterOption.getParam();
        String arg = filterOption.getArg();

        //enable filter
        query.enableFullTextFilter(name).setParameter(param, arg);
    }

    /**
     * Parses a string into FilterOption object.
     * <p>
     * The String is split by a delimiter:
     * The first entry in the array represents the filter name,
     * The second entry in the array represents the filter parameter,
     * The third entry in the array represents the filter argument.
     *
     * @param filterString The String to be parsed.
     * @return FilterOption to be later used in the filter enabling.
     */
    private FilterOption parse(String filterString) {

        String[] split = filterString.split(DELIMITER);

        if (split.length < REQ_ARGS_AMOUNT) {
            throw new FilterParsingException(
                    "Insufficient amount of filter arguments" + split.length);
        }

        String name = split[NAME];
        String param = split[PARAM];
        String arg = split[ARG];

        return new FilterOption(name, param, arg);
    }

    /**
     * Holds the filter arguments to be later used in enabling filters
     */
    private static class FilterOption {

        String name;
        String param;
        String arg;

        public FilterOption(String name, String param, String arg) {
            this.name = name;
            this.param = param;
            this.arg = arg;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getArg() {
            return arg;
        }

        public void setArg(String arg) {
            this.arg = arg;
        }

        @Override
        public String toString() {
            return "FilterOption{" +
                    "name='" + name + '\'' +
                    ", param='" + param + '\'' +
                    ", arg='" + arg + '\'' +
                    '}';
        }
    }
}
