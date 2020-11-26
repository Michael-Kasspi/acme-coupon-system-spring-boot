package com.acme.couponsystem.service.task;

import com.acme.couponsystem.db.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
public class CouponCleanerService implements Runnable {

    private CouponRepository couponRepository;

    @Autowired
    public CouponCleanerService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional
    @Override
    public void run() {
        LocalDate currentDate = LocalDate.now();
        couponRepository.deleteByEndDateBefore(currentDate);
    }
}
