/*
 * 类名称:StatisticsController.java
 * 包名称:com.platform.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-04-29 11:06:12        lipengjun     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.annotation.SysLog;
import com.platform.entity.StatisticsEntity;
import com.platform.service.OrderService;
import com.platform.service.StatisticsService;
import com.platform.service.UserService;
import com.platform.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


/**
 * Controller
 *
 * @author lipengjun
 * @date 2019-04-29 11:06:12
 */
@RestController
@RequestMapping("statistics")
public class StatisticsController extends AbstractController {
    @Autowired
    private StatisticsService statisticsService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return R
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("statistics:list")
    public R queryAll(@RequestParam Map<String, Object> params) {
        List<StatisticsEntity> list = statisticsService.queryAll(params);

        return R.ok().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return R
     */
    @GetMapping("/list")
    @RequiresPermissions("statistics:list")
    public R list(@RequestParam Map<String, Object> params) {
        Page page = statisticsService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return R
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("statistics:info")
    public R info(@PathVariable("id") Integer id) {
        StatisticsEntity statistics = statisticsService.getById(id);

        return R.ok().put("statistics", statistics);
    }

    /**
     * 新增
     *
     * @param statistics statistics
     * @return R
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("statistics:save")
    public R save(@RequestBody StatisticsEntity statistics) {

        statisticsService.add(statistics);

        return R.ok();
    }

    /**
     * 修改
     *
     * @param statistics statistics
     * @return R
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("statistics:update")
    public R update(@RequestBody StatisticsEntity statistics) {

        statisticsService.update(statistics);

        return R.ok();
    }

    /**
     * 根据主键删除
     *
     * @param ids ids
     * @return R
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("statistics:delete")
    public R delete(@RequestBody Integer[] ids) {
        statisticsService.deleteBatch(ids);

        return R.ok();
    }

    /**
     1.用户统计
     今日注册用户:24   select IFNULL(count(1),0) from nideshop_user where date_format(register_time,'%Y-%m-%d')=date_format (now(),'%Y-%m-%d');
     今日微信用户:14   select IFNULL(count(1),0) from nideshop_user where date_format(register_time,'%Y-%m-%d')=date_format (now(),'%Y-%m-%d') and weixin_openid is not null;
     今日下单新用户:4  select IFNULL(count(1),0) from nideshop_user where date_format(register_time,'%Y-%m-%d')=date_format (now(),'%Y-%m-%d') and id in (select user_id from nideshop_order);
     系统总用户数:8927 select IFNULL(count(1),0) from nideshop_user;
     当前在线用户:8
     2.订单统计
     今日支付订单数:    select IFNULL(count(1),0) from nideshop_order where pay_status=2 and date_format(add_time,'%Y-%m-%d')=date_format (now(),'%Y-%m-%d');
     今日未支付订单数:  select IFNULL(count(1),0) from nideshop_order where pay_status=1 and date_format(add_time,'%Y-%m-%d')=date_format (now(),'%Y-%m-%d');
     今日下单用户数:    select IFNULL(count(distinct(user_id)),0) from nideshop_order where pay_status=2 and date_format(add_time,'%Y-%m-%d')=date_format (now(),'%Y-%m-%d');
     系统累计成交订单:  select IFNULL(count(1),0) from nideshop_order where pay_status=2;
     3.资金统计
     今日交易金额      select IFNULL(sum(order_price),0) from nideshop_order where pay_status=2 and date_format(add_time,'%Y-%m-%d')=date_format (now(),'%Y-%m-%d');
     累计交易金额      select IFNULL(sum(order_price),0) from nideshop_order where pay_status=2;
     */

    @SysLog("查询当日")
    @RequestMapping("/currentday")
    public R queryCurrentDay(){
        StatisticsEntity statistics = statisticsService.queryCurrent();
        return R.ok().put("statistics", statistics);
    }
}
