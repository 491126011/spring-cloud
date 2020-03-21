/*
 * 类名称:SellerEntity.java
 * 包名称:com.platform.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-21 15:32:44        lipengjun     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 卖家信息表实体
 *
 * @author lipengjun
 * @date 2020-03-21 15:32:44
 */
@Data
@TableName("nideshop_seller")
public class SellerEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 系统用户id
     */
    private Long sysId;
    /**
     * 卖家名称
     */
    private String sellerName;
    /**
     * 卖家openid
     */
    private String openId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 状态0禁用1正常
     */
    private String stutus;
}
