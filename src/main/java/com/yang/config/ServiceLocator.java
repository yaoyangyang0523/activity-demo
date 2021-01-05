package com.yang.config;

import com.yang.workOrder.service.IRootService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * explain：获取应用上下文并获取相应的接口实现类
 *
 * @author yang
 * @date 2021/1/5
 */
@Component
public class ServiceLocator implements ApplicationContextAware {

    /**
     * 用于保存接口实现类名及对应的类
     */
    private Map<String, IRootService> map;

    /**
     * 获取应用上下文并获取相应的接口实现类
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //根据接口类型返回相应的所有bean
        map = applicationContext.getBeansOfType(IRootService.class);
    }

    /**
     * 获取所有实现集合
     * @return
     */
    public Map<String, IRootService> getMap() {
        return map;
    }

    /**
     * 获取对应服务
     * @param key
     * @return
     */
    public IRootService getService(String key) {
        return map.get(key);
    }
}
