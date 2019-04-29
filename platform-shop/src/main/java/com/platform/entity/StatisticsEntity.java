/*
 * 类名称:StatisticsEntity.java
 * 包名称:com.platform.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-04-29 11:06:12        lipengjun     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体
 *
 * @author lipengjun
 * @date 2019-04-29 11:06:12
 */
@Data
@TableName("nideshop_statistics")
public class StatisticsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private Integer id;
    /**
     * 
     */
    private Date countDate;
    /**
     * 今日注册用户
     */
    private Integer todayRegUser;
    /**
     * 今日微信用户
     */
    private Integer todayWxRegUser;
    /**
     * 今日下单新用户
     */
    private Integer todayOrderUser;
    /**
     * 系统总用户数
     */
    private Integer countUserNum;
    /**
     * 今日支付订单数
     */
    private Integer todayPayOrder;
    /**
     * 今日未支付订单数
     */
    private Integer todayUnpayOrder;
    /**
     * 今日下单用户数
     */
    private Integer todayUserOrder;
    /**
     * 系统累计成交订单
     */
    private Integer countOrderNum;
    /**
     * 今日交易金额
     */
    private BigDecimal todayTradeMoney;
    /**
     * 累计交易金额
     */
    private BigDecimal countTradeMoney;
    /**
     * 
     */
    private Date addTime;
}
