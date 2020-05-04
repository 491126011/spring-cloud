package com.platform.task;

import com.platform.service.UserCouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 优惠券定时任务
 * @author wht
 */
@Component("couponTask")
public class CouponTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserCouponService userCouponService;

    public void checkCoupon() {
        logger.info("扫描优惠券过期定时任务开始");
        userCouponService.checkCoupon();

    }

}
