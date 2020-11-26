package com.acme.couponsystem.thread;

import com.acme.couponsystem.service.task.CouponCleanerService;
import com.acme.couponsystem.service.task.TokenCleanerService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ThreadInitialization implements InitializingBean {

    private static final long ONE_MINUTE_MS = 60000;
    private TaskExecutor taskExecutor;
    private CouponCleanerService couponCleanerService;
    private TokenCleanerService tokenCleanerService;

    @Autowired
    public ThreadInitialization(
            @Qualifier("taskExecutor") TaskExecutor taskExecutor,
            CouponCleanerService couponCleanerService,
            TokenCleanerService tokenCleanerService) {
        this.taskExecutor = taskExecutor;
        this.couponCleanerService = couponCleanerService;
        this.tokenCleanerService = tokenCleanerService;
    }

    @Override
    public void afterPropertiesSet() {
        cleanExpiredCoupons();
    }

    //will run at midnight everyday
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanExpiredCoupons() {
        taskExecutor.execute(couponCleanerService);
    }

    @Scheduled(fixedRate = ONE_MINUTE_MS)
    public void cleanExpiredSessions() {taskExecutor.execute(tokenCleanerService);}

}
