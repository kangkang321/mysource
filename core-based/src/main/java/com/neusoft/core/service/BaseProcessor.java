package com.neusoft.core.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.neusoft.core.service.annotation.Processor;

/**
 * 类BaseProcessor.java的实现描述：业务操作实现基类 FIXME 后续考虑加入底层jar包
 * 
 * @author Administrator 2017年3月16日 上午11:12:12
 */
public class BaseProcessor implements InitializingBean, ApplicationContextAware {

    protected ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (Processor.class == annotationType) {
                    Processor p = (Processor) annotation;
                    ProcessorDispatcher.getInstance().registerProcessor(p.bussinessEnum(), method);
                }
            }
        }
        // 子类可以再扩展
        customerAfterPropertiesSet();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        ProcessorDispatcher.getInstance().setApplicationContext(applicationContext);
    }

    protected void customerAfterPropertiesSet() {
    }
}
