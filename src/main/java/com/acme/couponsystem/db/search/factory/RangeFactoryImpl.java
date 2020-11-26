package com.acme.couponsystem.db.search.factory;

import com.acme.couponsystem.db.search.factory.ex.RangeParsingException;
import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.RangeTerminationExcludable;
import org.hibernate.search.query.dsl.impl.RangeQueryContext;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton factory class which is used to convert List of String range queries to a list of range Query.
 */
public class RangeFactoryImpl implements RangeFactory {

    private static final String SEMICOLON = ";";
    private static final String DELIMITER = SEMICOLON;
    private static final String EXC = "exc";
    private static final String BETWEEN = "between";
    private static final String ABOVE = "above";
    private static final String BELOW = "below";
    private static final String EMPTY_STRING = "";

    private static volatile RangeFactoryImpl instance;
    private static final Object mutex = new Object();

    private RangeFactoryImpl() {
        /*empty*/
    }

    public static RangeFactoryImpl getInstance() {
        RangeFactoryImpl result = instance;

        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    result = instance = new RangeFactoryImpl();
                }
            }
        }

        return result;
    }

    /**
     * Holds the parameters information to be used during parsing by calling the getArg method.
     */
    private enum Param {
        TYPE(0, "type", false),
        FIELD(1, "field", false),
        VALUE(2, "value", false),
        FROM(2, "from", false),
        EXC_VALUE(3, "exclude limit", true),
        EXC_FROM(3, "exclude limit of 'from' value", false),
        TO(4, "to", false),
        EXC_TO(5, "exclude limit of 'to' value", true);

        final int position;
        final String name;
        final boolean optional;

        Param(int position, String name, boolean optional) {
            this.position = position;
            this.name = name;
            this.optional = optional;
        }

        public int getPosition() {
            return position;
        }

        public String getName() {
            return name;
        }

        public boolean isOptional() {
            return optional;
        }
    }

    /**
     * Converts a List of String range queries to a list of Query.
     * <p>
     * The List of String range queries is converted into RangeOption object.
     * The RangeOption is then converted into a range Query.
     *
     * @return the expected List of parsed String into Query object.
     */
    @Override
    public List<Query> getRangeQueries(QueryBuilder builder, List<String> rangeList, Class<?> clazz) {
        return rangeList
                .stream()
                .map(this::parse)
                .map(ro -> getRangeQuery(ro, builder, clazz))
                .collect(Collectors.toList());
    }

    /**
     * Parses a String into RangeOption object.
     * <p>
     * The String is parsed into 3 different range query types as follows:
     * Between, Above and Below types.
     * <p>
     * Each mode also supports exclusion of the range limits.
     * <p>
     * The following syntax is expected:
     * <p>
     * Between - "fieldName,between,from,excludeLimit,to,excludeLimit".
     * <p>
     * fieldName - the given field name.
     * between - the following type name 'between'.
     * from - from value.
     * to - to value.
     * excludeLimit - 'exc' (exclude), 'inc' (include).
     * <p>
     * Above - "fieldName,above,value,excludeLimit".
     * <p>
     * fieldName - the given field name.
     * above - the following type name 'above'.
     * value - above value.
     * excludeLimit - 'exc' (exclude), 'inc' (include) - (optional, is included by default).
     * <p>
     * Below - "fieldName,below,value,excludeLimit".
     * <p>
     * fieldName - the given field name.
     * below - the following type name 'below'.
     * value - below value.
     * excludeLimit - exc (exclude), inc (include) - (optional, is included by default).
     *
     * @param rangeString the given string to be parsed.
     * @return RangeOption which is later converted into Query object.
     */
    private RangeOption parse(String rangeString) {

        //split the String by a delimiter
        String[] split = rangeString.split(DELIMITER);

        //assignment
        String type = getArg(split, Param.TYPE);
        String field = getArg(split, Param.FIELD);

        RangeOption.Builder roBuilder = new RangeOption.Builder(field);

        //'between' parsing
        if (BETWEEN.equalsIgnoreCase(type)) {

            //assignment
            String from = getArg(split, Param.FROM);
            String excFrom = getArg(split, Param.EXC_FROM);
            String to = getArg(split, Param.TO);
            String excTo = getArg(split, Param.EXC_TO);

            //check if to exclude limit
            boolean isExcFrom = EXC.equalsIgnoreCase(excFrom);
            boolean isExcTo = EXC.equalsIgnoreCase(excTo);

            return roBuilder
                    .above(from, isExcFrom)
                    .below(to, isExcTo)
                    .build();
        }

        //assignment
        String value = getArg(split, Param.VALUE);
        String excLimit = getArg(split, Param.EXC_VALUE);


        //check if to exclude limit
        boolean isExcLimit = EXC.equalsIgnoreCase(excLimit);

        //'above' or 'below' parsing
        if (ABOVE.equalsIgnoreCase(type)) {
            return roBuilder.above(value, isExcLimit).build();
        } else if (BELOW.equalsIgnoreCase(type)) {
            return roBuilder.below(value, isExcLimit).build();
        }

        //if no type matches the supported types
        throw new RangeParsingException(
                String.format("Unsupported range query type %s", type));
    }

    /**
     * Returns a value from an array previously split by the Param enum.
     *
     * @param split the array of Strings.
     * @param param the Param enum holding the relevant values.
     * @return String at the given position.
     * @throws RangeParsingException if the array is too short or if the required argument is empty.
     */
    private String getArg(String[] split, Param param) {

        //assignment
        int position = param.getPosition();
        boolean optional = param.isOptional();
        String name = param.getName();

        //check if the argument is present in the expected position
        if (split.length < position + 1) {

            if (optional) {
                return EMPTY_STRING;
            }
            throw new RangeParsingException(
                    String.format(
                            "The required parameter '%s' is missing at the position of '%d'", name, position));
        }

        //get the string by its position
        String arg = split[position];

        //check if the argument is not empty and required
        if (arg.isEmpty() && !optional) {
            throw new RangeParsingException(
                    String.format(
                            "The required parameter '%s' is empty at the position of '%d'", name, position));
        }

        return arg;
    }

    /**
     * Converts RangeOption object into range Query.
     *
     * @param rangeOption the given RangeOption object.
     * @param builder
     * @return Query - the expected range Query.
     */
    private Query getRangeQuery(RangeOption rangeOption, QueryBuilder builder, Class<?> clazz) {

        //assignment
        String fieldName = rangeOption.getFieldName();
        String from = rangeOption.getFrom();
        String to = rangeOption.getTo();

        /*get RangeTerminationExcludable to inject the RangeQueryContext object
        to create the range query*/
        RangeTerminationExcludable termination =
                builder
                        .range()
                        .onField(fieldName)
                        .from(from)
                        .to(to);

        /*set the RangeQueryContext*/
        setRangeQueryContext(termination, convert(rangeOption, clazz));

        return termination.createQuery();
    }

    private void setRangeQueryContext(
            RangeTerminationExcludable termination,
            RangeQueryContext rangeQueryContext) {

        /*declaration*/
        Field rangeContextField;

        /*get Field object*/
        try { rangeContextField = termination.getClass().getDeclaredField("rangeContext"); }

        /*wrap checked Exception into RuntimeException*/
        catch (NoSuchFieldException e) { throw new RuntimeException(e.getMessage(), e.getCause()); }

        /*change access*/
        rangeContextField.setAccessible(true);

        /*set the desired RangeQueryContext object*/
        try { rangeContextField.set(termination, rangeQueryContext); }

        /*wrap checked Exception into RuntimeException*/
        catch (IllegalAccessException e) { throw new RuntimeException(e.getMessage(), e.getCause()); }
    }

    private RangeQueryContext convert(RangeOption rangeOption, Class<?> clazz) {

        /*declaration*/
        String fieldName = rangeOption.getFieldName();
        String fromString = rangeOption.getFrom();
        String toString = rangeOption.getTo();

        boolean hasFrom = rangeOption.isHasFrom();
        boolean hasTo = rangeOption.isHasTo();
        boolean excFrom = rangeOption.isExcFrom();
        boolean excTo = rangeOption.isExcTo();

        Class<?> fieldType;

        /*get field type*/
        try { fieldType = clazz.getDeclaredField(fieldName).getType(); }
        catch (NoSuchFieldException e) {
            throw new RangeParsingException(e.getMessage());
        }

        RangeQueryContext rangeQueryContext = new RangeQueryContext();

        if (hasFrom) {
            Object from = parseStringToType(fromString, fieldType);
            rangeQueryContext.setFrom(from);
            rangeQueryContext.setExcludeFrom(excFrom);
        }

        if (hasTo) {
            Object to = parseStringToType(toString, fieldType);
            rangeQueryContext.setTo(to);
            rangeQueryContext.setExcludeTo(excTo);
        }

        return rangeQueryContext;
    }

    private static Object parseStringToType(String value, Class<?> type) {

        if (String.class == type) return value;

        if (Double.class == type) return Double.valueOf(value);

        if (Byte.class == type) return Byte.valueOf(value);

        if (Short.class == type) return Short.valueOf(value);

        if (Long.class == type) return Long.valueOf(value);

        if (Integer.class == type) return Integer.valueOf(value);

        if (Float.class == type) return Float.valueOf(value);

        if (java.time.LocalDate.class == type) return LocalDate.parse(value);

        String message = String.format("Unable to convert the value from String to the type of '%s", type.toString());
        throw new RangeParsingException(message);
    }

    private static class RangeOption {

        String fieldName;
        String from;
        boolean hasFrom;
        String to;
        boolean hasTo;
        boolean excFrom;
        boolean excTo;

        public RangeOption(
                String fieldName,
                String from,
                boolean hasFrom,
                String to,
                boolean hasTo,
                boolean excFrom,
                boolean excTo) {
            this.fieldName = fieldName;
            this.from = from;
            this.hasFrom = hasFrom;
            this.to = to;
            this.hasTo = hasTo;
            this.excFrom = excFrom;
            this.excTo = excTo;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public boolean isHasFrom() {
            return hasFrom;
        }

        public void setHasFrom(boolean hasFrom) {
            this.hasFrom = hasFrom;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public boolean isHasTo() {
            return hasTo;
        }

        public void setHasTo(boolean hasTo) {
            this.hasTo = hasTo;
        }

        public boolean isExcFrom() {
            return excFrom;
        }

        public void setExcFrom(boolean excFrom) {
            this.excFrom = excFrom;
        }

        public boolean isExcTo() {
            return excTo;
        }

        public void setExcTo(boolean excTo) {
            this.excTo = excTo;
        }

        @Override
        public String toString() {
            return "RangeOption{" +
                    "fieldName='" + fieldName + '\'' +
                    ", from='" + from + '\'' +
                    ", hasFrom=" + hasFrom +
                    ", to='" + to + '\'' +
                    ", hasTo=" + hasTo +
                    ", excFrom=" + excFrom +
                    ", excTo=" + excTo +
                    '}';
        }

        static class Builder {

            String fieldName;
            String from;
            boolean hasFrom;
            String to;
            boolean hasTo;
            boolean excFrom;
            boolean excTo;

            public Builder() {
                /*empty*/
            }

            public Builder(String fieldName) {
                this.fieldName = fieldName;
            }

            Builder onField(String fieldName) {
                this.fieldName = fieldName;
                return this;
            }

            Builder below(String to, boolean excTo) {
                this.to = to;
                this.excTo = excTo;
                hasTo = true;
                return this;
            }

            Builder above(String from, boolean excFrom) {
                this.from = from;
                this.excFrom = excFrom;
                hasFrom = true;
                return this;
            }

            RangeOption build() {
                return new RangeOption(fieldName, from, hasFrom, to, hasTo, excFrom, excTo);
            }
        }


    }
}
