package com.acme.couponsystem.service;

import com.acme.couponsystem.db.entity.Account;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {

    Account getAccount();

    void setAccount(Account account);

    Account findAccount();

    boolean checkDuplicateEmail(String email);

    Account uploadProfilePicture(MultipartFile file);

    Account deleteProfilePicture();

    /**
     * The following method is used to update tha account credentials.
     * A current password is required to protect from token thieves stealing the account.
     *
     * The password should be checked against the password in the database.
     *
     * For update of less sensitive fields its advised to use updatePersonalInfo method,
     * Since it doesn't require the account's password.
     *
     * @param account The account with the credentials to update.
     * @param currentPassword The current password of the account to validate the request.
     * @return The updated account.
     */
    Account updateCredentials(Account account, String currentPassword);

    /**
     * The following method is used to update tha account's less sensitive fields.
     * Only fields that don't alter the account access should be updated.
     * Credentials fields should be ignored.
     *
     * @param account The account with the fields to update.
     * @return The updated account.
     */
    Account updatePersonalInfo(Account account);
}
