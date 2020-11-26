package com.acme.couponsystem.db.rest.controller;

import com.acme.couponsystem.db.entity.Account;
import com.acme.couponsystem.db.rest.ClientSession;
import com.acme.couponsystem.db.rest.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class SessionController {

    private SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(
            @RequestHeader String email,
            @RequestHeader String password,
            HttpServletResponse response
    ) {

        ClientSession session = sessionService.login(email, password);

        String token = sessionService.store(session);

        Account account = session.getService().getAccount();

        sessionService.setSessionCookies(response, token, session);

        return ResponseEntity.ok(new LoginResponse(token, account));
    }

    @PostMapping("logout")
    public ResponseEntity<LogoutResponse> logout(
            @CookieValue(defaultValue = "") String token,
            HttpServletResponse response
    ) {
        sessionService.logout(token);
        sessionService.clearSessionCookies(response);
        return ResponseEntity.ok(new LogoutResponse("Logout succeeded"));
    }

    @PostMapping("refresh")
    public ResponseEntity<LoginResponse> refresh(
            @CookieValue(defaultValue = "") String token,
            HttpServletResponse response
    ) {
        ClientSession session = sessionService.refresh(token);

        String refreshedToken = sessionService.store(session);

        Account account = session.getService().getAccount();

        sessionService.setSessionCookies(response, token, session);

        return ResponseEntity.ok(new LoginResponse(refreshedToken, account));
    }

    private static class LoginResponse {

        String token;
        Account account;

        public LoginResponse(String token, Account account) {
            this.token = token;
            this.account = account;
        }

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    private static class LogoutResponse {
        String message;

        public LogoutResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
