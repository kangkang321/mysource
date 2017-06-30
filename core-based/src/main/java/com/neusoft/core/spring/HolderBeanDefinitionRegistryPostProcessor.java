package com.neusoft.core.spring;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 类HolderBeanDefinitionRegistryPostProcessor.java的实现描述：只有实现PriorityOrdered才能初始化，方法不能放到static方法里，因为线程不同
 * 
 * @author Administrator 2017年5月8日 上午9:40:09
 */
public class HolderBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {

    private static final Logger LOGGER = LoggerFactory.getLogger(HolderBeanDefinitionRegistryPostProcessor.class);

    private String              sysFile;

    public HolderBeanDefinitionRegistryPostProcessor(String sysFile){
        this.sysFile = sysFile;
        // FIXME 后期是不是考虑放到init方法里
        try {
            Properties pro = PropertiesLoaderUtils.loadProperties(new ClassPathResource(sysFile));
            for (String key : pro.stringPropertyNames()) {
                if (StringUtils.isBlank(System.getProperty(key))) {
                    System.setProperty(key, pro.getProperty(key));
                }
            }
        } catch (IOException e) {
            LOGGER.error("not found sysFile");
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    public String getSysFile() {
        return sysFile;
    }

    public void setSysFile(String sysFile) {
        this.sysFile = sysFile;
    }

}
