package com.neusoft.hp.dubbo.container.context;

import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.springframework.context.ApplicationContext;

public interface WebxComponent {

    public WebxComponents getRoot();

    public String getName();

    public String getPath();

    public ApplicationContext getApplicationContext();

    public ClassRealm getClassRealm();
}
