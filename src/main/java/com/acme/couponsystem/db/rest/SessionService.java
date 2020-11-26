package com.acme.couponsystem.db.rest;

import com.acme.couponsystem.db.repository.AccountRepository;
import com.acme.couponsystem.db.entity.*;
import com.acme.couponsystem.db.rest.controller.ex.InvalidTokenException;
import com.acme.couponsystem.db.rest.controller.ex.SessionExpiredException;
import com.acme.couponsystem.service.*;
import com.acme.couponsystem.service.ex.AccountWithoutUserException;
import com.acme.couponsystem.service.ex.InvalidLoginCredentialsException;
import com.acme.couponsystem.service.ex.UnsupportedUserTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

@Service
public class SessionService {

    private static final int LENGTH_TOKEN = 15;
    private static long SESSION_TIME_OUT_MS;

    private ApplicationContext context;
    private AccountRepository accountRepository;
    private CookieManager cookieService;
    private Map<String, ClientSession> tokenMap;

    @Autowired
    public SessionService(
            ApplicationContext context,
            AccountRepository accountRepository,
            @Qualifier("tokens") Map<String, ClientSession> tokenMap,
            CookieManager cookieManager,
            RestConfiguration configuration) {
        this.context = context;
        this.accountRepository = accountRepository;
        this.tokenMap = tokenMap;
        this.cookieService = cookieManager;
        SESSION_TIME_OUT_MS = configuration.getSessionTimeoutMs();
    }

    public ClientSession login(String email, String password) {

        Account account = getAccount(email, password); // login
        User user = account.getUser();

        if (null == user) {
            throw new AccountWithoutUserException("The following account has no user");
        }
        UserService service = getServiceByUser(user.getClass());
        service.setAccount(account);

        AccountService accountService = createAccountService(account);

        return createClientSession(service, accountService);
    }

    private AccountService createAccountService(Account account) {
        AccountService accountService = context.getBean(AccountService.class);
        accountService.setAccount(account);
        return accountService;
    }

    public String store(ClientSession clientSession) {
        String token = generateToken();
        tokenMap.put(token, clientSession);
        return token;
    }

    public void logout(String token) {
        ClientSession session = tokenMap.remove(token);
        validate(session);
    }

    public ClientSession getSession(String token) {
        ClientSession session = tokenMap.get(token);
        return validate(session);
    }

    public ClientSession refresh(String token) {
        ClientSession session = tokenMap.remove(token);
        return validate(session);
    }

    public ClientSession validate(ClientSession session) {

        if (session == null) {
            throw new InvalidTokenException("Session expired or invalid token");
        }
        if (session.isExpired(SESSION_TIME_OUT_MS)) {
            throw new SessionExpiredException("Session expired");
        }
        // refresh session duration
        session.accessed();

        return session;
    }

    public <T> T getUserService(
            String token,
            HttpServletResponse response,
            Class<? extends UserService> serviceClass
    ) {
        ClientSession session = getSession(token);
        T userService;

        try { //noinspection unchecked
            userService = (T) serviceClass.cast(session.getService());
        } catch (ClassCastException e) {
            throw new InvalidTokenException("Invalid session token for this resource");
        }

        setSessionCookies(response, token, session);

        return userService;
    }

    public AccountService getAccountService(
            String token,
            HttpServletResponse response
    ) {
        ClientSession session = getSession(token);
        setSessionCookies(response, token, session);
        return session.getAccountService();
    }

    public void setSessionCookies(HttpServletResponse response, String token, ClientSession session) {
        long expiry = session.getLastAccessed() + SESSION_TIME_OUT_MS;
        String userType = session.getUserType();
        cookieService.setTokenCookie(response, token, expiry, userType);
    }

    public void clearSessionCookies(HttpServletResponse response) {
        cookieService.clearSessionCookies(response);
    }

    private UserService getServiceByUser(Class<? extends User> userClass) {
        UserService service;
        if (userClass == Admin.class) {
            service = context.getBean(AdminServiceImpl.class);
        } else if (userClass == Company.class) {
            service = context.getBean(CompanyServiceImpl.class);
        } else if (userClass == Customer.class) {
            service = context.getBean(CustomerServiceImpl.class);
        } else {
            throw new UnsupportedUserTypeException("Login for this account type is not implemented");
        }
        return service;
    }

    private Account getAccount(String email, String password) {

        Account account = accountRepository.findByEmailIgnoreCaseAndPassword(email, password)
                .orElseThrow(() -> new InvalidLoginCredentialsException("Invalid login credentials"));

        String accountPassword = account.getPassword();

        /*some database ignore case*/
        if (!accountPassword.equals(password)) {
            throw new InvalidLoginCredentialsException("Invalid login credentials");
        }

        return account;
    }

    private ClientSession createClientSession(UserService service, AccountService accountService) {
        //get ClientSession prototype
        ClientSession clientSession = context.getBean(ClientSession.class);
        //set the the corresponding services
        clientSession.setService(service);
        clientSession.setAccountService(accountService);
        //set accessed time
        clientSession.accessed();
        return clientSession;
    }

    private static String generateToken() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .substring(0, LENGTH_TOKEN);
    }
}
