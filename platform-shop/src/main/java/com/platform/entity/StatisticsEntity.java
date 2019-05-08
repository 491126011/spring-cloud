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
import com.fasterxml.jackson.annotation.JsonFormat;
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
//@Data
//@TableName("nideshop_statistics")
public class StatisticsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    public Date getCountDate() {
        return countDate;
    }

    public void setCountDate(Date countDate) {
        this.countDate = countDate;
    }

    public Integer getTodayRegUser() {
        return todayRegUser;
    }

    public void setTodayRegUser(Integer todayRegUser) {
        this.todayRegUser = todayRegUser;
    }

    public Integer getTodayWxRegUser() {
        return todayWxRegUser;
    }

    public void setTodayWxRegUser(Integer todayWxRegUser) {
        this.todayWxRegUser = todayWxRegUser;
    }

    public Integer getTodayOrderUser() {
        return todayOrderUser;
    }

    public void setTodayOrderUser(Integer todayOrderUser) {
        this.todayOrderUser = todayOrderUser;
    }

    public Integer getCountUserNum() {
        return countUserNum;
    }

    public void setCountUserNum(Integer countUserNum) {
        this.countUserNum = countUserNum;
    }

    public Integer getTodayPayOrder() {
        return todayPayOrder;
    }

    public void setTodayPayOrder(Integer todayPayOrder) {
        this.todayPayOrder = todayPayOrder;
    }

    public Integer getTodayUnpayOrder() {
        return todayUnpayOrder;
    }

    public void setTodayUnpayOrder(Integer todayUnpayOrder) {
        this.todayUnpayOrder = todayUnpayOrder;
    }

    public Integer getTodayUserOrder() {
        return todayUserOrder;
    }

    public void setTodayUserOrder(Integer todayUserOrder) {
        this.todayUserOrder = todayUserOrder;
    }

    public Integer getCountOrderNum() {
        return countOrderNum;
    }

    public void setCountOrderNum(Integer countOrderNum) {
        this.countOrderNum = countOrderNum;
    }

    public BigDecimal getTodayTradeMoney() {
        return todayTradeMoney;
    }

    public void setTodayTradeMoney(BigDecimal todayTradeMoney) {
        this.todayTradeMoney = todayTradeMoney;
    }

    public BigDecimal getCountTradeMoney() {
        return countTradeMoney;
    }

    public void setCountTradeMoney(BigDecimal countTradeMoney) {
        this.countTradeMoney = countTradeMoney;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
