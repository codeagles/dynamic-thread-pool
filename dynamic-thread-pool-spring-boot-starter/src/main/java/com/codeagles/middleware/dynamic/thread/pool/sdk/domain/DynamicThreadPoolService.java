package com.codeagles.middleware.dynamic.thread.pool.sdk.domain;

import com.alibaba.fastjson2.JSON;
import com.codeagles.middleware.dynamic.thread.pool.sdk.model.entity.ThreadPoolConfigEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author codeagles
 * @date 2024/5/29
 * @description
 */
public class DynamicThreadPoolService implements IDynamicThreadPoolService{

    private final Logger logger = LoggerFactory.getLogger(DynamicThreadPoolService.class);

    private final Map<String, ThreadPoolExecutor> threadPoolExecutorMap;

    private final String applicationName;

    public DynamicThreadPoolService(String applicationName, Map<String, ThreadPoolExecutor> threadPoolExecutorMap) {
        this.threadPoolExecutorMap = threadPoolExecutorMap;
        this.applicationName = applicationName;
    }

    @Override
    public List<ThreadPoolConfigEntity> queryThreadPoolList() {
        Set<String> threadPoolBeanNames = threadPoolExecutorMap.keySet();
        List<ThreadPoolConfigEntity> threadPoolConfigEntities = new ArrayList<ThreadPoolConfigEntity>(threadPoolBeanNames.size());
        for (String threadPoolBeanName : threadPoolBeanNames) {
            ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorMap.get(threadPoolBeanName);
            ThreadPoolConfigEntity threadPoolConfigEntity = new ThreadPoolConfigEntity();
            threadPoolConfigEntity.setCorePoolSize(threadPoolExecutor.getCorePoolSize());
            threadPoolConfigEntity.setMaximumPoolSize(threadPoolExecutor.getMaximumPoolSize());
            threadPoolConfigEntity.setActiveCount(threadPoolExecutor.getActiveCount());
            threadPoolConfigEntity.setPoolSize(threadPoolExecutor.getPoolSize());
            threadPoolConfigEntity.setQueueType(threadPoolExecutor.getQueue().getClass().getSimpleName());
            threadPoolConfigEntity.setQueueSize(threadPoolExecutor.getQueue().size());
            threadPoolConfigEntity.setRemainingCapacity(threadPoolExecutor.getQueue().remainingCapacity());
            threadPoolConfigEntities.add(threadPoolConfigEntity);
        }
        return threadPoolConfigEntities;
    }

    @Override
    public ThreadPoolConfigEntity queryThreadPoolConfigByName(String threadPoolName) {
        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorMap.get(threadPoolName);
        if (null == threadPoolExecutor) {
            return new ThreadPoolConfigEntity(applicationName, threadPoolName);
        }
        //线程池配置
        ThreadPoolConfigEntity threadPoolConfigEntity = new ThreadPoolConfigEntity();
        threadPoolConfigEntity.setCorePoolSize(threadPoolExecutor.getCorePoolSize());
        threadPoolConfigEntity.setMaximumPoolSize(threadPoolExecutor.getMaximumPoolSize());
        threadPoolConfigEntity.setActiveCount(threadPoolExecutor.getActiveCount());
        threadPoolConfigEntity.setPoolSize(threadPoolExecutor.getPoolSize());
        threadPoolConfigEntity.setQueueType(threadPoolExecutor.getQueue().getClass().getSimpleName());
        threadPoolConfigEntity.setQueueSize(threadPoolExecutor.getQueue().size());
        threadPoolConfigEntity.setRemainingCapacity(threadPoolExecutor.getQueue().remainingCapacity());
        if (logger.isDebugEnabled()) {
            logger.info("动态线程池，配置查询:{}, 线程名:{}, 池化配置:{}", applicationName, threadPoolName, JSON.toJSONString(threadPoolExecutor));
        }
        return threadPoolConfigEntity;
    }

    @Override
    public void updateThreadPoolConfig(ThreadPoolConfigEntity threadPoolConfigEntity) {
        if (null == threadPoolConfigEntity || !applicationName.equals(threadPoolConfigEntity.getAppName())) return;
        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorMap.get(threadPoolConfigEntity.getThreadPoolName());
        if (null == threadPoolExecutor) return;

        // 设置参数 「调整核心线程数和最大线程数」
        threadPoolExecutor.setCorePoolSize(threadPoolConfigEntity.getCorePoolSize());
        threadPoolExecutor.setMaximumPoolSize(threadPoolConfigEntity.getMaximumPoolSize());
    }

}
