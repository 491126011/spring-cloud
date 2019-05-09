package com.platform.service;

import com.platform.entity.StatisticsEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author hsq
 * @email 939961241@qq.com
 * @date 2019-05-09 09:51:03
 */
public interface StatisticsService {

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
    StatisticsEntity queryObject(Integer id);

    /**
     * 根据主键对象实体
     *
     * @param statistics
     * @return 实体
     */
    StatisticsEntity queryObject(StatisticsEntity statistics);


    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<StatisticsEntity> queryList(Map<String, Object> map);

    /**
     * 分页统计总数
     *
     * @param map 参数
     * @return 总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存实体
     *
     * @param statistics 实体
     * @return 保存条数
     */
    int save(StatisticsEntity statistics);

    /**
     * 根据主键更新实体
     *
     * @param statistics 实体
     * @return 更新条数
     */
    int update(StatisticsEntity statistics);

    /**
     * 根据主键删除
     *
     * @param id
     * @return 删除条数
     */
    int delete(Integer id);

    /**
     * 根据主键批量删除
     *
     * @param ids
     * @return 删除条数
     */
    int deleteBatch(Integer[] ids);

    /**
     *  查询当前相关统计数据
     * @return
     */
    StatisticsEntity queryCurrent();

}
