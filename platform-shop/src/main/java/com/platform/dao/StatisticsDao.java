/*
 * 类名称:StatisticsDao.java
 * 包名称:com.platform.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-04-29 11:06:12        lipengjun     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.entity.StatisticsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author lipengjun
 * @date 2019-04-29 11:06:12
 */
@Mapper
public interface StatisticsDao extends BaseDao<StatisticsEntity>  {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<StatisticsEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<StatisticsEntity> selectStatisticsPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 查询当前相关统计数据
     * @return
     */
    StatisticsEntity queryCurrent();

}
