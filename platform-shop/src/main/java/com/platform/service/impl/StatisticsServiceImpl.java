/*
 * 类名称:StatisticsServiceImpl.java
 * 包名称:com.platform.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-04-29 11:06:12        lipengjun     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.utils.QueryPlus;
import com.platform.dao.StatisticsDao;
import com.platform.entity.StatisticsEntity;
import com.platform.service.StatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author lipengjun
 * @date 2019-04-29 11:06:12
 */
@Service("statisticsService")
public class StatisticsServiceImpl extends ServiceImpl<StatisticsDao, StatisticsEntity> implements StatisticsService {

    @Override
    public List<StatisticsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<StatisticsEntity> page = new QueryPlus<StatisticsEntity>(params).getPage();
        return page.setRecords(baseMapper.selectStatisticsPage(page, params));
    }

    @Override
    public boolean add(StatisticsEntity statistics) {
        return this.save(statistics);
    }

    @Override
    public boolean update(StatisticsEntity statistics) {
        return this.updateById(statistics);
    }

    @Override
    public boolean delete(Integer id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(Integer[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public StatisticsEntity queryCurrent(){
        return this.queryCurrent();
    }


}
