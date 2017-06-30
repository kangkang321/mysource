package com.neusoft.core.webx.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;

import com.neusoft.core.webx.WebxComponent;
import com.neusoft.core.webx.WebxConfiguration;
import com.neusoft.core.webx.WebxController;

public abstract class AbstractWebxController implements WebxController {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    private WebxComponent  component;

    public WebxComponent getComponent() {
        return component;
    }

    public WebxConfiguration getWebxConfiguration() {
        return getComponent().getWebxConfiguration();
    }

    /** 此方法在创建controller时被调用。 */
    public void init(WebxComponent component) {
        this.component = component;
    }

    /** 此方法在创建或刷新WebApplicationContext时被调用。 */
    public void onRefreshContext() throws BeansException {
        initWebxConfiguration();
    }

    private void initWebxConfiguration() {
        WebxConfiguration webxConfiguration = getWebxConfiguration();

        log.debug("Initializing WebxComponent \"{}\" in {} mode, according to <webx-configuration>",
                  getComponent().getName(), webxConfiguration.isProductionMode() ? "production" : "development");
    }

    public void onFinishedProcessContext() {
    }
}
