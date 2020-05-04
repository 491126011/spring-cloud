package com.platform.task;

import com.platform.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商户到期时间定时任务
 * @author wht
 */
@Component("shopExpireTask")
public class ShopExpireTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;

    public void checkShopExpire() {
        logger.info("每日扫描商户到期时间定时任务开始");
        sysUserService.checkShopExpire();

    }

}
