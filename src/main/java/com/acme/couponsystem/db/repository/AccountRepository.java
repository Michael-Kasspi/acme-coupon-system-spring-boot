package com.acme.couponsystem.db.repository;

import com.acme.couponsystem.db.entity.Account;
import com.acme.couponsystem.db.entity.Company;
import com.acme.couponsystem.db.entity.User;
import com.acme.couponsystem.db.meta.DbMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmailIgnoreCaseAndPassword(String email, String password);

    Optional<Account> findByEmail(String email);

    @Query(
            nativeQuery = true,
            value = "SELECT * from "
                    + DbMeta.TABLE_ACCOUNT
                    + " where " + DbMeta.COLUMN_USER_ID + " = ?1"
                    + " and " + DbMeta.COLUMN_USER_ROLE + " = ?2"
    )
    Optional<Account> findByUser(long userId, String userRole);

    @Query(
            nativeQuery = true,
            value = "SELECT * from "
                    + DbMeta.TABLE_ACCOUNT
                    + " where " + DbMeta.COLUMN_USER_ID + " = ?1"
                    + " and " + DbMeta.COLUMN_USER_ROLE + " = ?2"
    )
    List<Account> findAllByUser(long userId, String userRole);
}
