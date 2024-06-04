package com.codeagles.middleware.dynamic.thread.pool.sdk.trigger.job;

import com.alibaba.fastjson.JSON;
import com.codeagles.middleware.dynamic.thread.pool.sdk.domain.IDynamicThreadPoolService;
import com.codeagles.middleware.dynamic.thread.pool.sdk.model.entity.ThreadPoolConfigEntity;
import com.codeagles.middleware.dynamic.thread.pool.sdk.register.IRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @author codeagles
 * @date 2024/6/4
 * @description 线程池数据上报任务
 */
public class ThreadPoolDataReportJob {

    private final Logger logger = LoggerFactory.getLogger(ThreadPoolDataReportJob.class);

    private final IDynamicThreadPoolService dynamicThreadPoolService;

    private final IRegistry registry;

    public ThreadPoolDataReportJob(IDynamicThreadPoolService dynamicThreadPoolService, IRegistry registry) {
        this.dynamicThreadPoolService = dynamicThreadPoolService;
        this.registry = registry;
    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void execReportThreadPoolList() {
        List<ThreadPoolConfigEntity> threadPoolConfigEntityList = dynamicThreadPoolService.queryThreadPoolList();
        registry.reportThreadPool(threadPoolConfigEntityList);
        logger.info("动态线程池，上报线程池信息:{}", JSON.toJSONString(threadPoolConfigEntityList));

        for (ThreadPoolConfigEntity threadPoolConfigEntity : threadPoolConfigEntityList) {
            registry.reportThreadPoolConfigParameter(threadPoolConfigEntity);
            logger.info("动态线程池，上报线程池信息:{}", JSON.toJSONString(threadPoolConfigEntity));

        }


    }
}
