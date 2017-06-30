package com.neusoft.core.webx.impl;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.neusoft.core.webx.WebxComponent;
import com.neusoft.core.webx.util.SpringExtUtil;

/**
 * 和<code>WebxComponent</code>对应的context类。
 *
 * @author Michael Zhou
 */
public class WebxComponentContext extends XmlWebApplicationContext {

    private final WebxComponent component;

    public WebxComponentContext(WebxComponent component){
        this.component = SpringExtUtil.assertNotNull(component, "component");

        ApplicationContext parentContext = component.getWebxComponents().getParentApplicationContext();

        setParent(parentContext);

        if (parentContext instanceof WebxComponentsContext) {
            ((WebxComponentsContext) parentContext).setupComponentContext(this);
        }
    }

    public WebxComponent getWebxComponent() {
        return component;
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        super.postProcessBeanFactory(beanFactory);
        beanFactory.registerResolvableDependency(WebxComponent.class, component);
    }
}
