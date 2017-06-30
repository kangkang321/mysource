package com.neusoft.core.service;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.neusoft.core.domain.dubbo.BizResult;
import com.neusoft.core.dubbo.ExceptionHandler;
import com.neusoft.core.util.ReflectWrapper;

/**
 * 类ProcessorDispatcher.java的实现描述：dubbo操作类接口入口 FIXME 后续考虑加入底层jar包
 * 
 * @author Administrator 2017年3月16日 上午11:13:54
 */
public class ProcessorDispatcher {

    private static final Logger        LOGGER     = LoggerFactory.getLogger(ProcessorDispatcher.class);
    /**
     * 单例模式
     */
    private static ProcessorDispatcher instance   = new ProcessorDispatcher();

    /**
     * 与业务操作挂钩
     */
    private Map<String, Method>        processors = new ConcurrentHashMap<>();

    /**
     * spring上下文
     */
    private ApplicationContext         applicationContext;

    private ProcessorDispatcher(){
    }

    public static ProcessorDispatcher getInstance() {
        return instance;
    }

    public void registerProcessor(String business, Method method) {
        processors.put(business, method);
    }

    public Method getProcessor(String business) {
        return processors.get(business);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * FIXME 这里只实现了同步调用，后续考虑异步调用、准同步调用、任务恢复执行
     * 
     * @param business
     * @param args
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> BizResult invoke(String business, Object... args) {
        BizResult result = new BizResult();
        try {
            // FIXME 参数验证
            // FIXME 日志
            MDC.put("business", business);
            // FIXME 幂等与落地
            Method method = processors.get(business);
            Object bean = applicationContext.getBean(method.getDeclaringClass());
            T r = (T) ReflectWrapper.invoke(method, bean, args);
            result.setValue(r);
            result.setSuccess(true);
        } catch (Exception e) {
            exceptionHandler(result, e);
            LOGGER.error("执行操作时出现异常");
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    private void exceptionHandler(BizResult r, Exception e) {
        try {
            Set<String> sets = ExtensionLoader.getExtensionLoader(ExceptionHandler.class).getSupportedExtensions();
            if (!CollectionUtils.isEmpty(sets)) {
                sets.stream().map(m -> ExtensionLoader.getExtensionLoader(ExceptionHandler.class).getExtension(m)).forEach(m -> m.accept(r,
                                                                                                                                         e));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
