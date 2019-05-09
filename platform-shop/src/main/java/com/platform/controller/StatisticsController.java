package com.platform.controller;

import java.util.List;
import java.util.Map;

import com.platform.annotation.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.platform.entity.StatisticsEntity;
import com.platform.service.StatisticsService;
import com.platform.utils.PageUtils;
import com.platform.utils.Query;
import com.platform.utils.R;

/**
 * Controller
 *
 * @author hsq
 * @email 939961241@qq.com
 * @date 2019-05-09 09:51:03
 */
@RestController
@RequestMapping("statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("statistics:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<StatisticsEntity> statisticsList = statisticsService.queryList(query);
        int total = statisticsService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(statisticsList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("statistics:info")
    public R info(@PathVariable("id") Integer id) {
        StatisticsEntity statistics = statisticsService.queryObject(id);

        return R.ok().put("statistics", statistics);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("statistics:save")
    public R save(@RequestBody StatisticsEntity statistics) {
        statisticsService.save(statistics);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("statistics:update")
    public R update(@RequestBody StatisticsEntity statistics) {
        statisticsService.update(statistics);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("statistics:delete")
    public R delete(@RequestBody Integer[] ids) {
        statisticsService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<StatisticsEntity> list = statisticsService.queryList(params);

        return R.ok().put("list", list);
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
    //http://localhost:8086/statistics/currentday
    public R queryCurrentDay(){
        StatisticsEntity statistics = statisticsService.queryCurrent();
//        StatisticsEntity existEntity = statisticsService.queryObject(statistics);
//        if(existEntity!=null){
//            statistics.setId(existEntity.getId());
//            statistics.setCountDate(existEntity.getCountDate());
//            statisticsService.update(statistics);
//        }else{
//            statisticsService.save(statistics);
//        }

        return R.ok().put("statistics", statistics);
    }
}
