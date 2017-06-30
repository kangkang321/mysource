package com.neusoft.hp.dubbo.container.context.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.config.spring.ServiceBean;
import com.neusoft.core.dubbo.GenericServiceImpl;
import com.neusoft.core.webx.util.SpringExtUtil;
import com.neusoft.hp.dubbo.container.context.WebxComponent;

public class WebxContext extends ClassPathXmlApplicationContext {

    private WebxComponent component;

    public WebxContext(WebxComponent component){
        this.component = component;
    }

    @Override
    public ClassLoader getClassLoader() {
        return component.getClassRealm();
    }

    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(GroupBeanPostProcessor.class);
        BeanDefinition componentsCreator = builder.getBeanDefinition();
        componentsCreator.setAutowireCandidate(false);

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        String name = SpringExtUtil.generateBeanName(GroupBeanPostProcessor.class.getName(), registry);

        registry.registerBeanDefinition(name, componentsCreator);
    }

    public static class GroupBeanPostProcessor implements MergedBeanDefinitionPostProcessor {

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }

        @Override
        public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType,
                                                    String beanName) {
            if (ServiceBean.class.equals(beanType)) {
                PropertyValue value = beanDefinition.getPropertyValues().getPropertyValue("group");
                if (GenericServiceImpl.GROUP.equals(value.getValue())) {
                    beanDefinition.getPropertyValues().removePropertyValue("group");
                    beanDefinition.getPropertyValues().add("group", System.getProperty("owner") + ","
                                                                    + GenericServiceImpl.GROUP);
                }
            }
        }

    }
}
