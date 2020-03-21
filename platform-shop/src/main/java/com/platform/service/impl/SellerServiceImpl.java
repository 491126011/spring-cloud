/*
 * 类名称:SellerServiceImpl.java
 * 包名称:com.platform.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-21 15:32:44        lipengjun     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.utils.QueryPlus;
import com.platform.dao.SellerDao;
import com.platform.entity.SellerEntity;
import com.platform.service.SellerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 卖家信息表Service实现类
 *
 * @author lipengjun
 * @date 2020-03-21 15:32:44
 */
@Service("sellerService")
public class SellerServiceImpl extends ServiceImpl<SellerDao, SellerEntity> implements SellerService {

    @Override
    public List<SellerEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<SellerEntity> page = new QueryPlus<SellerEntity>(params).getPage();
        return page.setRecords(baseMapper.selectSellerPage(page, params));
    }

    @Override
    public boolean add(SellerEntity seller) {
        return this.save(seller);
    }

    @Override
    public boolean update(SellerEntity seller) {
        return this.updateById(seller);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(Long[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
