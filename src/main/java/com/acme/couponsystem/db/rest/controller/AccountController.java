package com.acme.couponsystem.db.rest.controller;

import com.acme.couponsystem.db.entity.Account;
import com.acme.couponsystem.db.rest.ClientSession;
import com.acme.couponsystem.db.rest.SessionService;
import com.acme.couponsystem.service.AccountService;
import com.acme.couponsystem.service.CustomerService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/accounts/")
public class AccountController {

    private SessionService sessionService;

    @Autowired
    public AccountController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PutMapping("/")
    public ResponseEntity<Account> update(
            @RequestHeader String password,
            @RequestBody Account account,
            @CookieValue(defaultValue = "") String token,
            HttpServletResponse response
    ) {
        AccountService service = getService(token, response);

        Account updatedAccount;

        if (Strings.isNotBlank(password)) {
            updatedAccount = service.updateCredentials(account, password);
        } else {
            updatedAccount = service.updatePersonalInfo(account);
        }

        return ResponseEntity.ok(updatedAccount);
    }

    @GetMapping("/")
    public ResponseEntity<Account> getAccount(
            @CookieValue(defaultValue = "") String token,
            HttpServletResponse response
    ) {
        AccountService service = getService(token, response);
        return ResponseEntity.ok(service.findAccount());
    }

    @GetMapping(path = "/emails")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public boolean isDuplicateEmail(
            @RequestHeader String email,
            @CookieValue(defaultValue = "") String token,
            HttpServletResponse response
    ) {
        AccountService service = getService(token, response);
        return service.checkDuplicateEmail(email);
    }

    @PostMapping(path = "/profile")
    public ResponseEntity<Account> uploadProfilePicture(
            @RequestParam MultipartFile image,
            @CookieValue(defaultValue = "") String token,
            HttpServletResponse response
    ) {
        AccountService service = getService(token, response);
        return ResponseEntity.ok(service.uploadProfilePicture(image));
    }

    @DeleteMapping(path = "/profile")
    public ResponseEntity<Account> deleteProfilePicture(
            @CookieValue(defaultValue = "") String token,
            HttpServletResponse response
    ) {
        AccountService service = getService(token, response);
        return ResponseEntity.ok(service.deleteProfilePicture());
    }

    private AccountService getService(String token, HttpServletResponse response) {
        return sessionService.getAccountService(token, response);
    }
}
