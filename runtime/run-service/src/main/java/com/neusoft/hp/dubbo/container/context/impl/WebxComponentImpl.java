package com.neusoft.hp.dubbo.container.context.impl;

import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.springframework.context.ApplicationContext;

import com.neusoft.hp.dubbo.container.context.WebxComponent;
import com.neusoft.hp.dubbo.container.context.WebxComponents;

public class WebxComponentImpl implements WebxComponent {

    private WebxComponents     root;

    private String             name;

    private String             path;

    private ApplicationContext applicationContext;

    private ClassRealm         classRealm;

    public WebxComponentImpl(WebxComponents root, String name, String path){
        this.root = root;
        this.name = name;
        this.path = path;
    }

    @Override
    public WebxComponents getRoot() {
        return root;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public ClassRealm getClassRealm() {
        return classRealm;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setClassRealm(ClassRealm classRealm) {
        this.classRealm = classRealm;
    }

}
