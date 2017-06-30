package com.neusoft.hp.dubbo.container.context.impl;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.hp.dubbo.container.context.WebxConfiguration;

public class WebxConfigurationImpl implements WebxConfiguration {

    private Set<String> names;

    private String      location;

    public WebxConfigurationImpl(Set<String> names, String location){
        this.names = names;
        this.location = location;
    }

    @Override
    public String getProfile() {
        return System.getProperty("profile", "rd");
    }

    @Override
    public ComponentsConfig getComponentsConfig() {
        return new ComponentsConfig() {

            @Override
            public Map<String, ComponentConfig> getComponents() {
                // TODO Auto-generated method stub
                return names.stream().filter(m -> !StringUtils.equals(m, "webx")
                                                  && !StringUtils.equals(m,
                                                                         "webx.all")).collect(Collectors.toMap(m -> m,
                                                                                                               m -> convert(m)));
            }
        };
    }

    public ComponentConfig convert(final String name) {
        return new ComponentConfig() {

            @Override
            public String getPath() {
                return StringUtils.replace(name, "webx.", "");
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }

    @Override
    public String getComponentConfigurationLocation() {
        return location;
    }

}
