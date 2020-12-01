package com.acme.couponsystem.service.task;

import com.acme.couponsystem.db.entity.Account;
import com.acme.couponsystem.db.entity.Admin;
import com.acme.couponsystem.db.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DefaultAdminCreator implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAdminCreator.class);

    private final AccountRepository accountRepository;
    private final DefaultAdminConfig defaultAdminConfig;

    @Autowired
    public DefaultAdminCreator(
            AccountRepository accountRepository,
            DefaultAdminConfig defaultAdminConfig
    )
    {
        this.accountRepository = accountRepository;
        this.defaultAdminConfig = defaultAdminConfig;
    }

    @Override
    @Transactional
    public void run()
    {
        if (!accountRepository.findAll().isEmpty()) return;
        LOGGER.warn("No accounts found, going to create new admin account");

        final String email = defaultAdminConfig.getEmail();
        final String password = defaultAdminConfig.getPassword();

        final Admin admin = new Admin();
        admin.setMain(true);

        final Account account = new Account();
        account.setUser(admin);
        account.setFirstName(defaultAdminConfig.getFirstName());
        account.setLastName(defaultAdminConfig.getLastName());
        account.setEmail(email);
        account.setPassword(password);
        account.setCredit(0);

        accountRepository.save(account);
        LOGGER.warn("New admin account created with email: {}, password: {}", email, password);
    }
}
