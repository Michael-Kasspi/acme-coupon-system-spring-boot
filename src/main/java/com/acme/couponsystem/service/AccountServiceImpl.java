package com.acme.couponsystem.service;

import com.acme.couponsystem.db.entity.Account;
import com.acme.couponsystem.db.repository.AccountRepository;
import com.acme.couponsystem.service.ex.DuplicateEmailException;
import com.acme.couponsystem.service.ex.MalformedAccountException;
import com.acme.couponsystem.service.ex.TerminatedAccountException;
import com.acme.couponsystem.service.ex.UnableToUpdateAccountCredentialsException;
import com.acme.couponsystem.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private StorageService storageService;
    private Account account;

    @Autowired
    public AccountServiceImpl(
            AccountRepository accountRepository,
            StorageService storageService
    ) {
        this.accountRepository = accountRepository;
        this.storageService = storageService;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Transactional
    @Override
    public Account updateCredentials(Account account, String currentPassword) {
        // fetch the account from db
        Account accountFromDb = findAccount();
        String email = account.getEmail();

        validate(email);

        // validate password
        if (!accountFromDb.getPassword().equals(currentPassword)) {
            throw new UnableToUpdateAccountCredentialsException(
                    "The current password is invalid");
        }

        if (isDuplicateEmail(email)) {
            throw new DuplicateEmailException("The email is already taken");
        }

        // update the fields
        accountFromDb.update(account);

        return accountRepository.save(accountFromDb);
    }

    @Transactional
    @Override
    public Account updatePersonalInfo(Account account) {
        // fetch the account from db
        Account accountFromDb = findAccount();

        String firstName = account.getFirstName();
        String lastName = account.getLastName();

        if (null != firstName) {
            validate(firstName);
            accountFromDb.setFirstName(firstName);
        }

        if (null != lastName) {
            validate(lastName);
            accountFromDb.setLastName(lastName);
        }

        return accountRepository.save(accountFromDb);
    }

    @Override
    public boolean checkDuplicateEmail(String email) {
        validate(email);
        return isDuplicateEmail(email);
    }

    @Override
    public Account findAccount() {
        return accountRepository.findById(account.getId())
                .orElseThrow(() -> new TerminatedAccountException("The following account has been terminated"));
    }

    @Override
    public Account uploadProfilePicture(MultipartFile file) {
        Account account = findAccount();
        String profilePictureUri = account.getProfilePictureUrl();

        if (Objects.nonNull(profilePictureUri)){
            storageService.deleteFile(profilePictureUri);
        }

        String fileLocation = storageService.storeImage(file, "accounts/profile");
        account.setProfilePictureUrl(fileLocation);

        return accountRepository.save(account);
    }

    @Override
    public Account deleteProfilePicture() {
        Account account = findAccount();
        String profilePictureUri = account.getProfilePictureUrl();

        if (Objects.nonNull(profilePictureUri)){
            storageService.deleteFile(profilePictureUri);
        }

        account.setProfilePictureUrl(null);

        return accountRepository.save(account);
    }

    private void validate(String string) {
        if (string == null || string.isEmpty()) {
            throw new MalformedAccountException("Account fields can't be empty");
        }
    }

    private boolean isDuplicateEmail(String email) {
        return accountRepository
                .findByEmail(email)
                .map(account -> !findAccount().getEmail().equals(account.getEmail()))
                .orElse(false);
    }
}
