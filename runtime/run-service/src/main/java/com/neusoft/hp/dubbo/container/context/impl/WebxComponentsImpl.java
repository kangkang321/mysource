package com.neusoft.hp.dubbo.container.context.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.ClassUtils;

import com.neusoft.hp.dubbo.container.context.WebxComponent;
import com.neusoft.hp.dubbo.container.context.WebxComponents;
import com.neusoft.hp.dubbo.container.context.WebxConfiguration;

public class WebxComponentsImpl implements WebxComponents {

    private Set<WebxComponent> components = new HashSet<>();

    private BeanFactory        applicationContext;

    private ClassRealm         classRealm;

    private WebxConfiguration  webxConfiguration;

    public WebxComponentsImpl(WebxConfiguration webxConfiguration, ConfigurableListableBeanFactory beanFactory,
                              ClassRealm classRealm){
        this.webxConfiguration = webxConfiguration;
        this.applicationContext = beanFactory;
        this.classRealm = classRealm;
        this.classRealm.setParentClassLoader(ClassUtils.getDefaultClassLoader());

    }

    public void addComponent(WebxComponent component) {
        components.add(component);
    }

    @Override
    public Iterator<WebxComponent> iterator() {
        return components.iterator();
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // FIXME
    }

    @Override
    public WebxComponent getComponent(String name) {
        Optional<WebxComponent> ss = components.stream().filter(m -> StringUtils.equals(m.getName(), name)).findFirst();
        if (ss.isPresent()) {
            return ss.get();
        }
        return null;
    }

    @Override
    public WebxComponent findComponent(String path) {
        Optional<WebxComponent> ss = components.stream().filter(m -> StringUtils.equals(m.getPath(), path)).findFirst();
        if (ss.isPresent()) {
            return ss.get();
        }
        return null;
    }

    @Override
    public WebxConfiguration getWebxConfiguration() {
        return webxConfiguration;
    }

    @Override
    public BeanFactory getApplicationContext() {
        return applicationContext;
    }

    @Override
    public ClassRealm getClassRealm() {
        return classRealm;
    }

    @Override
    public String getConfigPath() {
        return webxConfiguration.getComponentConfigurationLocation();
    }

}
