/*
 * 类名称:SellerService.java
 * 包名称:com.platform.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-21 15:32:44        lipengjun     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.entity.SellerEntity;

import java.util.List;
import java.util.Map;

/**
 * 卖家信息表Service接口
 *
 * @author lipengjun
 * @date 2020-03-21 15:32:44
 */
public interface SellerService extends IService<SellerEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SellerEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询卖家信息表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增卖家信息表
     *
     * @param seller 卖家信息表
     * @return 新增结果
     */
    boolean add(SellerEntity seller);

    /**
     * 根据主键更新卖家信息表
     *
     * @param seller 卖家信息表
     * @return 更新结果
     */
    boolean update(SellerEntity seller);

    /**
     * 根据主键删除卖家信息表
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(Long id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(Long[] ids);
}
