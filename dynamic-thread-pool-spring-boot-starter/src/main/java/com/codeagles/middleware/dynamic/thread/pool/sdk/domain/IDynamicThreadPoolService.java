package com.codeagles.middleware.dynamic.thread.pool.sdk.domain;

import com.codeagles.middleware.dynamic.thread.pool.sdk.model.entity.ThreadPoolConfigEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author codeagles
 * @date 2024/5/29
 * @description 动态线程池服务
 */
public interface IDynamicThreadPoolService {

    List<ThreadPoolConfigEntity> queryThreadPoolList();

    ThreadPoolConfigEntity queryThreadPoolConfigByName(String threadPoolName);

    void updateThreadPoolConfig(ThreadPoolConfigEntity threadPoolConfig);
}
