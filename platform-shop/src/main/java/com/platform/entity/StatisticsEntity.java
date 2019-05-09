package com.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体
 * 表名 nideshop_statistics
 *
 * @author hsq
 * @email 939961241@qq.com
 * @date 2019-05-09 09:51:03
 */
public class StatisticsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;
    //
    private Date countDate;
    //今日注册用户
    private Integer todayRegUser;
    //今日微信用户
    private Integer todayWxRegUser;
    //今日下单新用户
    private Integer todayOrderUser;
    //系统总用户数
    private Integer countUserNum;
    //今日支付订单数
    private Integer todayPayOrder;
    //今日未支付订单数
    private Integer todayUnpayOrder;
    //今日下单用户数
    private Integer todayUserOrder;
    //系统累计成交订单
    private Integer countOrderNum;
    //今日交易金额
    private BigDecimal todayTradeMoney;
    //累计交易金额
    private BigDecimal countTradeMoney;
    //
    private Date addTime;

    /**
     * 设置：
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置：
     */
    public void setCountDate(Date countDate) {
        this.countDate = countDate;
    }

    /**
     * 获取：
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getCountDate() {
        return countDate;
    }
    /**
     * 设置：今日注册用户
     */
    public void setTodayRegUser(Integer todayRegUser) {
        this.todayRegUser = todayRegUser;
    }

    /**
     * 获取：今日注册用户
     */
    public Integer getTodayRegUser() {
        return todayRegUser;
    }
    /**
     * 设置：今日微信用户
     */
    public void setTodayWxRegUser(Integer todayWxRegUser) {
        this.todayWxRegUser = todayWxRegUser;
    }

    /**
     * 获取：今日微信用户
     */
    public Integer getTodayWxRegUser() {
        return todayWxRegUser;
    }
    /**
     * 设置：今日下单新用户
     */
    public void setTodayOrderUser(Integer todayOrderUser) {
        this.todayOrderUser = todayOrderUser;
    }

    /**
     * 获取：今日下单新用户
     */
    public Integer getTodayOrderUser() {
        return todayOrderUser;
    }
    /**
     * 设置：系统总用户数
     */
    public void setCountUserNum(Integer countUserNum) {
        this.countUserNum = countUserNum;
    }

    /**
     * 获取：系统总用户数
     */
    public Integer getCountUserNum() {
        return countUserNum;
    }
    /**
     * 设置：今日支付订单数
     */
    public void setTodayPayOrder(Integer todayPayOrder) {
        this.todayPayOrder = todayPayOrder;
    }

    /**
     * 获取：今日支付订单数
     */
    public Integer getTodayPayOrder() {
        return todayPayOrder;
    }
    /**
     * 设置：今日未支付订单数
     */
    public void setTodayUnpayOrder(Integer todayUnpayOrder) {
        this.todayUnpayOrder = todayUnpayOrder;
    }

    /**
     * 获取：今日未支付订单数
     */
    public Integer getTodayUnpayOrder() {
        return todayUnpayOrder;
    }
    /**
     * 设置：今日下单用户数
     */
    public void setTodayUserOrder(Integer todayUserOrder) {
        this.todayUserOrder = todayUserOrder;
    }

    /**
     * 获取：今日下单用户数
     */
    public Integer getTodayUserOrder() {
        return todayUserOrder;
    }
    /**
     * 设置：系统累计成交订单
     */
    public void setCountOrderNum(Integer countOrderNum) {
        this.countOrderNum = countOrderNum;
    }

    /**
     * 获取：系统累计成交订单
     */
    public Integer getCountOrderNum() {
        return countOrderNum;
    }
    /**
     * 设置：今日交易金额
     */
    public void setTodayTradeMoney(BigDecimal todayTradeMoney) {
        this.todayTradeMoney = todayTradeMoney;
    }

    /**
     * 获取：今日交易金额
     */
    public BigDecimal getTodayTradeMoney() {
        return todayTradeMoney;
    }
    /**
     * 设置：累计交易金额
     */
    public void setCountTradeMoney(BigDecimal countTradeMoney) {
        this.countTradeMoney = countTradeMoney;
    }

    /**
     * 获取：累计交易金额
     */
    public BigDecimal getCountTradeMoney() {
        return countTradeMoney;
    }
    /**
     * 设置：
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取：
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getAddTime() {
        return addTime;
    }

    @Override
    public String toString() {
        return "StatisticsEntity{" +
                "id=" + id +
                ", countDate=" + countDate +
                ", todayRegUser=" + todayRegUser +
                ", todayWxRegUser=" + todayWxRegUser +
                ", todayOrderUser=" + todayOrderUser +
                ", countUserNum=" + countUserNum +
                ", todayPayOrder=" + todayPayOrder +
                ", todayUnpayOrder=" + todayUnpayOrder +
                ", todayUserOrder=" + todayUserOrder +
                ", countOrderNum=" + countOrderNum +
                ", todayTradeMoney=" + todayTradeMoney +
                ", countTradeMoney=" + countTradeMoney +
                ", addTime=" + addTime +
                '}';
    }
}
