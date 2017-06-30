package com.neusoft.hp.dubbo.container.context;

import java.util.Map;

public interface WebxConfiguration {

    public String getProfile();

    public ComponentsConfig getComponentsConfig();

    public String getComponentConfigurationLocation();

    public interface ComponentsConfig {

        public Map<String, ComponentConfig> getComponents();
    }

    public interface ComponentConfig {

        public String getPath();

        public String getName();

    }

}
