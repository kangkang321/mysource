package com.neusoft.core.webx;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import com.neusoft.core.webx.impl.WebxComponentsContext;
import com.neusoft.core.webx.impl.WebxControllerImpl;
import com.neusoft.core.webx.impl.WebxRootControllerImpl;

/**
 * 实现<code>WebxConfiguration</code>。
 *
 * @author Michael Zhou
 */
public class WebxConfigurationImpl implements WebxConfiguration, InitializingBean, DisposableBean, BeanNameAware {

    private ComponentsConfig   componentsConfig;

    private ApplicationContext factory;

    public void setApplicationContext(ApplicationContext factory) throws BeansException {
        this.factory = factory;
    }

    public ApplicationContext getFactory() {
        return factory;
    }

    public WebxConfigurationImpl(){
        super();
    }

    /** 取得一组关于components的配置。 */
    public ComponentsConfig getComponentsConfig() {
        // FIXME
        // return getProperty("componentsConfig", null);
        return componentsConfig;
    }

    public void setComponentsConfig(ComponentsConfig componentsConfig) {
        // FIXME
        // setProperty("componentsConfig", componentsConfig);
        this.componentsConfig = componentsConfig;
    }

    public static class ComponentsConfigImpl implements ComponentsConfig {

        private Boolean                      autoDiscoverComponents;
        private String                       componentConfigurationLocationPattern;
        private Class<?>                     defaultControllerClass;
        private Class<?>                     rootControllerClass;
        private String                       defaultComponent;
        private Map<String, ComponentConfig> components;
        private WebxRootController           rootController;

        public Boolean isAutoDiscoverComponents() {
            return autoDiscoverComponents == null ? true : autoDiscoverComponents;
        }

        public void setAutoDiscoverComponents(boolean autoDiscoverComponents) {
            this.autoDiscoverComponents = autoDiscoverComponents;
        }

        public String getComponentConfigurationLocationPattern() {
            return componentConfigurationLocationPattern == null ? "/WEB-INF/webx-*.xml" : componentConfigurationLocationPattern;
        }

        public void setComponentConfigurationLocationPattern(String componentConfigurationLocationPattern) {
            this.componentConfigurationLocationPattern = StringUtils.trimToNull(componentConfigurationLocationPattern);
        }

        public Class<?> getDefaultControllerClass() {
            return defaultControllerClass == null ? WebxControllerImpl.class : defaultControllerClass;
        }

        public void setDefaultControllerClass(Class<?> defaultControllerClass) {
            this.defaultControllerClass = defaultControllerClass;
        }

        public Class<?> getRootControllerClass() {
            return rootControllerClass == null ? WebxRootControllerImpl.class : rootControllerClass;
        }

        public void setRootControllerClass(Class<?> rootControllerClass) {
            this.rootControllerClass = rootControllerClass;
        }

        public String getDefaultComponent() {
            return defaultComponent;
        }

        public void setDefaultComponent(String defaultComponent) {
            this.defaultComponent = StringUtils.trimToNull(defaultComponent);
        }

        public Map<String, ComponentConfig> getComponents() {
            return components == null ? Collections.<String, ComponentConfig> emptyMap() : components;
        }

        public void setComponents(Map<String, ComponentConfig> components) {
            this.components = components;
        }

        public WebxRootController getRootController() {
            return rootController;
        }

        public void setRootController(WebxRootController rootController) {
            this.rootController = rootController;
        }
    }

    public static class ComponentConfigImpl implements ComponentConfig {

        private String         name;
        private String         path;
        private WebxController controller;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = StringUtils.trimToNull(name);
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = StringUtils.trimToNull(path);
        }

        public WebxController getController() {
            return controller;
        }

        public void setController(WebxController controller) {
            this.controller = controller;
        }
    }

    @Override
    public boolean isProductionMode() {
        return false;
    }

    @Override
    public void setBeanName(String name) {
        if (factory instanceof WebxComponentsContext) {
            WebxComponentsContext context = (WebxComponentsContext) factory;
            context.getLoader().setWebxConfigurationName(name);
        }
    }

    @Override
    public void destroy() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub

    }
}
