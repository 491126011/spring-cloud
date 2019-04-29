/*
 * 类名称:StatisticsService.java
 * 包名称:com.platform.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-04-29 11:06:12        lipengjun     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.entity.StatisticsEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author lipengjun
 * @date 2019-04-29 11:06:12
 */
public interface StatisticsService extends IService<StatisticsEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<StatisticsEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增
     *
     * @param statistics 
     * @return 新增结果
     */
    boolean add(StatisticsEntity statistics);

    /**
     * 根据主键更新
     *
     * @param statistics 
     * @return 更新结果
     */
    boolean update(StatisticsEntity statistics);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(Integer id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(Integer[] ids);

    /**
     *  查询当前相关统计数据
     * @return
     */
    StatisticsEntity queryCurrent();

}
