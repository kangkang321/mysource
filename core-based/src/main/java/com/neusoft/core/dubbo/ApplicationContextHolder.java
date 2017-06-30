package com.neusoft.core.dubbo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 类ApplicationContextHolder.java的实现描述：設置 LazyAutowiredProxy的上下文
 * 
 * @author Administrator 2017年6月14日 下午4:22:19
 */
public class ApplicationContextHolder implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LazyAutowiredProxy.setApplicationContext(applicationContext);
    }

}
