package com.acme.couponsystem.db.entity;

/**
 * The following interface is used by deserialized Entities from the web domain,
 * Which are stored in a database and have sensitive fields which are not allowed to be overridden,
 * By null value caused by a missing value during deserialization or a malicious unauthorized change of the value.
 *
 * @param <T> the entity to be updated.
 */
@FunctionalInterface
public interface Updateable<T> {
    void update(Updateable<? extends T> updateable);
}
