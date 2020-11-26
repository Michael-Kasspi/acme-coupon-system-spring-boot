package com.acme.couponsystem.db.rest;

import com.acme.couponsystem.service.AccountService;
import com.acme.couponsystem.service.UserService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ClientSession {

    private UserService service;
    private AccountService accountService;
    private long lastAccessed;

    public ClientSession() {
        /*empty*/
    }

    public UserService getService() {
        return service;
    }

    public void setService(UserService service) {
        this.service = service;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public String getUserType(){
        return service.getUserType();
    }

    public long getLastAccessed() {
        return lastAccessed;
    }

    public ClientSession accessed() {
        lastAccessed = System.currentTimeMillis();
        return this;
    }

    public boolean isExpired(long timeoutMillis) {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis > lastAccessed + timeoutMillis;
    }

    @Override
    public String toString() {
        return "ClientSession{" +
                "service=" + service +
                ", lastAccessed=" + lastAccessed +
                '}';
    }
}
