package com.neusoft.core.webx;

import java.util.Map;

/**
 * 代表一组webx的配置信息。
 *
 * @author Michael Zhou
 */
public interface WebxConfiguration extends Configuration {

    /** 取得一组关于components的配置。 */
    ComponentsConfig getComponentsConfig();

    /** 代表一组关于components的配置。 */
    interface ComponentsConfig {

        Boolean isAutoDiscoverComponents();

        String getComponentConfigurationLocationPattern();

        Class<?> getDefaultControllerClass();

        Class<?> getRootControllerClass();

        String getDefaultComponent();

        Map<String, ComponentConfig> getComponents();

        WebxRootController getRootController();
    }

    interface ComponentConfig {

        String getName();

        String getPath();

        WebxController getController();
    }
}
