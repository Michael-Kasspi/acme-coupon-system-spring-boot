package com.acme.couponsystem.db.search.factory;

import com.acme.couponsystem.db.search.factory.ex.SortParsingException;
import org.apache.logging.log4j.util.Strings;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortFieldContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This Factory class is Singleton and used to convert list of Strings to Sort object.
 */
public class SortFactoryImpl implements SortFactory {

    private static final String SEMICOLON = ";";
    private static final String DELIMITER = SEMICOLON;
    private static final String DESC = "desc";
    private static final int FIELD = 0;
    private static final int DIRECTION = 1;
    private static final int FIRST = 0;

    private static volatile SortFactoryImpl instance;
    private static final Object mutex = new Object();

    private SortFactoryImpl() {
        /*empty*/
    }

    public static SortFactoryImpl getInstance(){
        SortFactoryImpl result = instance;

        if (result == null){
            synchronized (mutex){
                result = instance;
                if (result == null){
                    result = instance = new SortFactoryImpl();
                }
            }
        }

        return result;
    }

    /**
     * @return Sort object form String sortList
     */
    @Override
    public Sort getSort(QueryBuilder builder, List<String> sortList) {
        return new Sort(getSortFields(builder, sortList));
    }

    /**
     * @return SortField array converted from SortOptions
     */
    private SortField[] getSortFields(QueryBuilder builder, List<String> sortList) {

        return getSortOptions(sortList)
                .stream()
                .map(sortOption -> getSortField(sortOption, builder))
                .toArray(SortField[]::new);
    }

    /**
     * Converts SortOption object into SortField object to be passed to the Sort object.
     *
     * @param sortOption The given SortOption object to be converted.
     * @return SortField to later be passed to the Sort object.
     */
    private SortField getSortField(SortOption sortOption, QueryBuilder builder) {

        //assignment
        String field = sortOption.getField();
        boolean reverse = sortOption.isReverse();

        Sort sort;

        sort = builder.sort().byField(field).createSort();

        //set direction
        if (reverse) {
            sort = builder.sort().byField(field).desc().createSort();
        }

        //get the SortField object
        return sort.getSort()[FIRST];
    }

    /**
     * @return SortOptions List from parsing sortList String List
     */
    private List<SortOption> getSortOptions(List<String> sortList) {

        return sortList
                .stream()
                .filter(Strings::isNotBlank)
                .map(this::parse)
                .collect(Collectors.toList());
    }

    /**
     * Parses a string into SortOption object
     * <p>
     * The String is split by a delimiter:
     * The first entry in the array represents the field String.
     * The second entry in the array represents the sorting direction which is optional.
     *
     * @param sortString the given string to be parsed to SortOption
     * @return SortOption object
     */
    private SortOption parse(String sortString) {

        if (sortString.isEmpty()) {
            throw new SortParsingException("Empty sort String");
        }

        //splits the string
        String[] split = sortString.split(DELIMITER);

        //set the first string in the array as the field
        String field = split[FIELD];

        //check whether the direction argument is present
        if (split.length > 1) {

            //match the direction string with the descending string
            boolean direction = DESC.equalsIgnoreCase(split[DIRECTION]);

            //create new SortField instance

            return new SortOption(field, direction);
        }

        //create new SortField instance without specifying the direction
        return new SortOption(field);
    }

    /**
     * Representing a sort option which will converted into a SortField
     */
    private static class SortOption {

        private static final boolean DEFAULT_SORT_DIRECTION = false;
        final String field;
        final boolean reverse;

        public SortOption(String field, boolean reverse) {
            this.field = field;
            this.reverse = reverse;
        }

        public SortOption(String field) {
            this(field, DEFAULT_SORT_DIRECTION);
        }

        public String getField() {
            return field;
        }

        public boolean isReverse() {
            return reverse;
        }

        @Override
        public String toString() {
            return "SortOption{" +
                    "field='" + field + '\'' +
                    ", reverse=" + reverse +
                    '}';
        }
    }
}
