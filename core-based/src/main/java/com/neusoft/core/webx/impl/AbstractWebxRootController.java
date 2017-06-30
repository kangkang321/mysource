package com.neusoft.core.webx.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;

import com.neusoft.core.webx.WebxComponents;
import com.neusoft.core.webx.WebxConfiguration;
import com.neusoft.core.webx.WebxRootController;

public abstract class AbstractWebxRootController implements WebxRootController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private WebxComponents components;

    public WebxComponents getComponents() {
        return components;
    }

    public WebxConfiguration getWebxConfiguration() {
        return getComponents().getParentWebxConfiguration();
    }

    /** 此方法在创建controller时被调用。 */
    public void init(WebxComponents components) {
        this.components = components;
    }

    /** 此方法在创建或刷新WebApplicationContext时被调用。 */
    public void onRefreshContext() throws BeansException {
        initWebxConfiguration();
    }

    private void initWebxConfiguration() {
        WebxConfiguration webxConfiguration = getWebxConfiguration();

        log.debug("Initializing Webx root context in {} mode, according to <webx-configuration>",
                  webxConfiguration.isProductionMode() ? "production" : "development");
    }

    public void onFinishedProcessContext() {
    }

}
