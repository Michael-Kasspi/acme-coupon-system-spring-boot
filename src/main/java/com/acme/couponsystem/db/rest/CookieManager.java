package com.acme.couponsystem.db.rest;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Stream;

@Service
public class CookieManager {

    private static final String EXPIRY = "expiry";
    private static final String TOKEN = "token";
    private static final String USER_TYPE = "userType";
    private static final long SECOND_MS = 1000;
    private static final int NO_AGE = 0;
    private static int SESSION_DURATION_SEC;

    public CookieManager(RestConfiguration configuration) {
        SESSION_DURATION_SEC = (int) (configuration.getSessionTimeoutMs() / SECOND_MS);
    }

    public void setTokenCookie(
            HttpServletResponse response,
            String token,
            long expiry,
            String userType
    ) {
        Cookie tokenCookie = new Cookie(TOKEN, token);
        Cookie expiryCookie = new Cookie(EXPIRY, Long.toString(expiry));
        Cookie userTypeCookie = new Cookie(USER_TYPE, userType);
        configureCookie(SESSION_DURATION_SEC, tokenCookie, expiryCookie, userTypeCookie);
        addCookies(response, tokenCookie, expiryCookie, userTypeCookie);
    }

    public void clearSessionCookies(HttpServletResponse response) {
        Cookie tokenCookie = new Cookie(TOKEN, null);
        Cookie expiryCookie = new Cookie(EXPIRY, null);
        Cookie userTypeCookie = new Cookie(USER_TYPE, null);
        configureCookie(NO_AGE, tokenCookie, expiryCookie, userTypeCookie);
        addCookies(response, tokenCookie, expiryCookie, userTypeCookie);
    }

    private void configureCookie(int age, Cookie... cookies) {
        Stream.of(cookies)
                .peek(cookie -> cookie.setPath("/")) // global cookies
                .forEach(cookie -> cookie.setMaxAge(age));
    }

    private void addCookies(HttpServletResponse response, Cookie... cookies) {
        Stream.of(cookies).forEach(response::addCookie);
    }
}
