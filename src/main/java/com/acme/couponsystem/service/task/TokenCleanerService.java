package com.acme.couponsystem.service.task;

import com.acme.couponsystem.db.rest.ClientSession;
import com.acme.couponsystem.db.rest.RestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TokenCleanerService implements Runnable {

    private static final long DEFAULT_SESSION_TIME_OUT = 1_800_000;

    //will be overridden if set in the properties file
    private static long SESSION_TIME_OUT_MS = DEFAULT_SESSION_TIME_OUT;

    private Map<String, ClientSession> tokenMap;

    @Autowired
    public TokenCleanerService(
            @Qualifier("tokens") Map<String, ClientSession> tokenMap,
            RestConfiguration restConfiguration) {
        this.tokenMap = tokenMap;
        SESSION_TIME_OUT_MS = restConfiguration.getSessionTimeoutMs();
    }

    @Override
    public void run() {
        //current time
        long currentTimeMillis = System.currentTimeMillis();

        //iterate over all the token map entries and remove expired tokens
        for (Map.Entry<String, ClientSession> tokenMapEntry : tokenMap.entrySet()) {

            ClientSession clientSession = tokenMapEntry.getValue();

            //check if the token has expired
            if (clientSession.isExpired(SESSION_TIME_OUT_MS)) {
                tokenMap.remove(tokenMapEntry.getKey());
            }
        }
    }

}
