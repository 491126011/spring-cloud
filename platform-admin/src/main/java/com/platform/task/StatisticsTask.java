package com.platform.task;

import com.platform.entity.StatisticsEntity;
import com.platform.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 统计定时任务
 * <p>
 * statisticsTask为spring bean的名称
 *
 * @author hsq
 * @email 896259559@qq.com
 * @date 2019年05月09日
 */
@Component("statisticsTask")
public class StatisticsTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 统计当天数据
     */
    public void statisticsCurrentDay() {

        StatisticsEntity entity = statisticsService.queryCurrent();

//        StatisticsEntity existEntity = statisticsService.queryObject(entity);
//        if(existEntity!=null){
//            entity.setId(existEntity.getId());
//            entity.setCountDate(existEntity.getCountDate());
//            statisticsService.update(entity);
//        }else{
            statisticsService.save(entity);
//        }


        logger.info("统计当天数据："  + entity.toString());
    }
}
