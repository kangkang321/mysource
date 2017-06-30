package com.neusoft.hp.dubbo.container.context.impl;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.neusoft.core.webx.util.SpringExtUtil;
import com.neusoft.hp.dubbo.container.WebxContainer;
import com.neusoft.hp.dubbo.container.WebxContainer.WebxComponentsCreator;

public class WebxRootContext extends ClassPathXmlApplicationContext {

    private WebxContainer container;

    public WebxRootContext(WebxContainer container){
        super();
        this.container = container;
    }

    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(WebxComponentsCreator.class);
        builder.addConstructorArgValue(container);
        BeanDefinition componentsCreator = builder.getBeanDefinition();
        componentsCreator.setAutowireCandidate(false);

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        String name = SpringExtUtil.generateBeanName(WebxComponentsCreator.class.getName(), registry);

        registry.registerBeanDefinition(name, componentsCreator);
    }

    @Override
    protected void finishRefresh() {
        super.finishRefresh();
        container.finishRefresh();
    }

}
