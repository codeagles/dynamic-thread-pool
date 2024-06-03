package com.codeagles.middleware.dynamic.thread.pool.sdk.register;

import com.codeagles.middleware.dynamic.thread.pool.sdk.model.entity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * @author codeagles
 * @date 2024/6/3
 * @description 注册中心接口
 */
public interface IRegistry {

    void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolConfigEntityList);

    void reportThreadPoolConfigParameter(ThreadPoolConfigEntity threadPoolConfigEntity);
}
