package com.neusoft.core.webx;

import org.springframework.context.ApplicationContext;

public interface WebxComponents extends Iterable<WebxComponent> {

    /** 取得所有components名称。 */
    String[] getComponentNames();

    /** 取得指定名称的component。 */
    WebxComponent getComponent(String componentName);

    /** 取得默认的component。如果未设置，则返回<code>null</code>。 */
    WebxComponent getDefaultComponent();

    /** 查找匹配的component。 */
    WebxComponent findMatchedComponent(String path);

    /** 取得用来处理请求的controller。 */
    WebxRootController getWebxRootController();

    /** 取得webx configuration设置。 */
    WebxConfiguration getParentWebxConfiguration();

    /** 取得所有component的父application context容器，如果没有，则返回<code>null</code>。 */
    ApplicationContext getParentApplicationContext();
}
