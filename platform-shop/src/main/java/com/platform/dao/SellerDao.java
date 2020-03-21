/*
 * 类名称:SellerDao.java
 * 包名称:com.platform.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-21 15:32:44        lipengjun     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.entity.SellerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 卖家信息表Dao
 *
 * @author lipengjun
 * @date 2020-03-21 15:32:44
 */
@Mapper
public interface SellerDao extends BaseMapper<SellerEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SellerEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<SellerEntity> selectSellerPage(IPage page, @Param("params") Map<String, Object> params);
}
