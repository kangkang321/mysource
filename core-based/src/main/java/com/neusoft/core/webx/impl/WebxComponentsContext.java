package com.neusoft.core.webx.impl;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.neusoft.core.webx.WebxComponents;
import com.neusoft.core.webx.WebxComponentsLoader;
import com.neusoft.core.webx.util.SpringExtUtil;

public class WebxComponentsContext extends XmlWebApplicationContext {

    private WebxComponentsLoader componentsLoader;

    public WebxComponentsLoader getLoader() {
        return SpringExtUtil.assertNotNull(componentsLoader, "no WebxComponentsLoader set");
    }

    public void setLoader(WebxComponentsLoader loader) {
        this.componentsLoader = loader;
    }

    /** 取得所有的components。 */
    public WebxComponents getWebxComponents() {
        return getLoader().getWebxComponents();
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        super.postProcessBeanFactory(beanFactory);
        getLoader().postProcessBeanFactory(beanFactory);
    }

    @Override
    protected void finishRefresh() {
        super.finishRefresh();
        getLoader().finishRefresh();
    }

    /** 在创建子容器时，给parent一个设置子context的机会。 */
    protected void setupComponentContext(WebxComponentContext componentContext) {
    }
}
