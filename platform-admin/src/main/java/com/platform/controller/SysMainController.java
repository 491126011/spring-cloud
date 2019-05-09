package com.platform.controller;

import com.platform.entity.StatisticsEntity;
import com.platform.entity.SysSmsLogEntity;
import com.platform.service.StatisticsService;
import com.platform.service.SysLogService;
import com.platform.utils.PageUtilsPlus;
import com.platform.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 首页Controller
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-03-08 10:40:56
 */
@Controller
@RequestMapping("/sys/main")
public class SysMainController {
    @Autowired
    private StatisticsService statisticsService;

    /**
     * 
     *
     * @param params 请求参数
     * @return R
     */
    @ResponseBody
    @RequestMapping("/info")
    public R list(@RequestParam Map<String, Object> params) {
        //查询数据
        StatisticsEntity statistics = statisticsService.queryCurrent();

        return R.ok().put("statistics", statistics);
    }

}
