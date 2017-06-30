package com.neusoft.hp.dubbo.container.context;

import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationListener;

public interface WebxComponents extends Iterable<WebxComponent>, ApplicationListener {

    public WebxComponent getComponent(String name);

    public WebxComponent findComponent(String path);

    public WebxConfiguration getWebxConfiguration();

    public BeanFactory getApplicationContext();

    public ClassRealm getClassRealm();

    public String getConfigPath();
}
