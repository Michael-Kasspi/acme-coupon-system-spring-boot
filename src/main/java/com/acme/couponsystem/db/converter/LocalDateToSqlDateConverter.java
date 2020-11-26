package com.acme.couponsystem.db.converter;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.time.LocalDate;

public class LocalDateToSqlDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return null == localDate ? null : Date.valueOf(localDate);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return null == date ? null : date.toLocalDate();
    }
}
