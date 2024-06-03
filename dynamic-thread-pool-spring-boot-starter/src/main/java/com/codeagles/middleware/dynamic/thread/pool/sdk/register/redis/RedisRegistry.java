package com.codeagles.middleware.dynamic.thread.pool.sdk.register.redis;

import com.codeagles.middleware.dynamic.thread.pool.sdk.domain.DynamicThreadPoolService;
import com.codeagles.middleware.dynamic.thread.pool.sdk.model.entity.ThreadPoolConfigEntity;
import com.codeagles.middleware.dynamic.thread.pool.sdk.model.valobj.RegistryEnumVO;
import com.codeagles.middleware.dynamic.thread.pool.sdk.register.IRegistry;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

/**
 * @author codeagles
 * @date 2024/6/3
 * @description redis注册中心
 */
@Configuration
public class RedisRegistry implements IRegistry {

    private final Logger logger = LoggerFactory.getLogger(RedisRegistry.class);

    private final RedissonClient redissonClient;

    public RedisRegistry(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolConfigEntityList) {
        RList<Object> list = redissonClient.getList(RegistryEnumVO.THREAD_POOL_CONFIG_LIST_KEY.getKey());
        list.addAll(list);

    }

    @Override
    public void reportThreadPoolConfigParameter(ThreadPoolConfigEntity threadPoolConfigEntity) {
        String cacheKey = RegistryEnumVO.THREAD_POOL_CONFIG_PARAMETER_LIST_KEY.getKey() + "_" + threadPoolConfigEntity.getAppName() + "_" + threadPoolConfigEntity.getThreadPoolName();
        RBucket<ThreadPoolConfigEntity> bucket = redissonClient.getBucket(cacheKey);
        bucket.set(threadPoolConfigEntity, Duration.ofDays(30));    }
}
