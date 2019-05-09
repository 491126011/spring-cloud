package com.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.platform.dao.StatisticsDao;
import com.platform.entity.StatisticsEntity;
import com.platform.service.StatisticsService;

/**
 * Service实现类
 *
 * @author hsq
 * @email 939961241@qq.com
 * @date 2019-05-09 09:51:03
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsDao statisticsDao;

    @Override
    public StatisticsEntity queryObject(Integer id) {
        return statisticsDao.queryObject(id);
    }

    @Override
    public StatisticsEntity queryObject(StatisticsEntity statistics) {
        return statisticsDao.queryObject(statistics);
    }

    @Override
    public List<StatisticsEntity> queryList(Map<String, Object> map) {
        return statisticsDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return statisticsDao.queryTotal(map);
    }

    @Override
    public int save(StatisticsEntity statistics) {
        return statisticsDao.save(statistics);
    }

    @Override
    public int update(StatisticsEntity statistics) {
        return statisticsDao.update(statistics);
    }

    @Override
    public int delete(Integer id) {
        return statisticsDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[] ids) {
        return statisticsDao.deleteBatch(ids);
    }

    @Override
    public StatisticsEntity queryCurrent(){
        return statisticsDao.queryCurrent();
    }


}
