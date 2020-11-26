package com.acme.couponsystem.service;

import com.acme.couponsystem.db.entity.Account;

public interface UserService {
    void setAccount(Account account);
    Account getAccount();
    String getUserType();
}
