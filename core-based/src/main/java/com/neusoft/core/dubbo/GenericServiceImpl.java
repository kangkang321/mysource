package com.neusoft.core.dubbo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ModuleConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;

public class GenericServiceImpl implements ApplicationContextAware {

    public static final String VERSION = "generic";

    public static final String GROUP   = "core-runtime";

    private ApplicationContext applicationContext;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public GenericService getGenericService(Class clazz, String group,
                                            Consumer<ReferenceConfig<GenericService>>... consumers) {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        wrapper(reference);
        reference.setGroup(group + "," + GROUP);
        reference.setVersion(VERSION);
        reference.setInterface(clazz);
        reference.setGeneric(true);
        if (consumers.length > 0) {
            Stream.of(consumers).forEach(c -> c.accept(reference));
        }
        GenericService instance = ReferenceConfigCache.getCache().get(reference);
        if (null == instance) {
            ReferenceConfigCache.getCache().destroy(reference);
        }
        return instance;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void wrapper(ReferenceConfig<GenericService> reference) {
        Map<String, ConsumerConfig> consumerConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
                                                                                                                                           ConsumerConfig.class,
                                                                                                                                           false,
                                                                                                                                           false);
        if (consumerConfigMap != null && consumerConfigMap.size() > 0) {
            ConsumerConfig consumerConfig = null;
            for (ConsumerConfig config : consumerConfigMap.values()) {
                if (config.isDefault() == null || config.isDefault().booleanValue()) {
                    if (consumerConfig != null) {
                        throw new IllegalStateException("Duplicate consumer configs: " + consumerConfig + " and "
                                                        + config);
                    }
                    consumerConfig = config;
                }
            }
            if (consumerConfig != null) {
                reference.setConsumer(consumerConfig);
            }
        }
        Map<String, ApplicationConfig> applicationConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
                                                                                                                                                 ApplicationConfig.class,
                                                                                                                                                 false,
                                                                                                                                                 false);
        if (applicationConfigMap != null && applicationConfigMap.size() > 0) {
            ApplicationConfig applicationConfig = null;
            for (ApplicationConfig config : applicationConfigMap.values()) {
                if (config.isDefault() == null || config.isDefault().booleanValue()) {
                    if (applicationConfig != null) {
                        throw new IllegalStateException("Duplicate application configs: " + applicationConfig + " and "
                                                        + config);
                    }
                    applicationConfig = config;
                }
            }
            if (applicationConfig != null) {
                reference.setApplication(applicationConfig);
            }
        }
        Map<String, ModuleConfig> moduleConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
                                                                                                                                       ModuleConfig.class,
                                                                                                                                       false,
                                                                                                                                       false);
        if (moduleConfigMap != null && moduleConfigMap.size() > 0) {
            ModuleConfig moduleConfig = null;
            for (ModuleConfig config : moduleConfigMap.values()) {
                if (config.isDefault() == null || config.isDefault().booleanValue()) {
                    if (moduleConfig != null) {
                        throw new IllegalStateException("Duplicate module configs: " + moduleConfig + " and " + config);
                    }
                    moduleConfig = config;
                }
            }
            if (moduleConfig != null) {
                reference.setModule(moduleConfig);
            }
        }
        Map<String, RegistryConfig> registryConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
                                                                                                                                           RegistryConfig.class,
                                                                                                                                           false,
                                                                                                                                           false);
        if (registryConfigMap != null && registryConfigMap.size() > 0) {
            List<RegistryConfig> registryConfigs = new ArrayList<RegistryConfig>();
            for (RegistryConfig config : registryConfigMap.values()) {
                if (config.isDefault() == null || config.isDefault().booleanValue()) {
                    registryConfigs.add(config);
                }
            }
            if (registryConfigs != null && registryConfigs.size() > 0) {
                reference.setRegistries(registryConfigs);
            }
        }
        Map<String, MonitorConfig> monitorConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
                                                                                                                                         MonitorConfig.class,
                                                                                                                                         false,
                                                                                                                                         false);
        if (monitorConfigMap != null && monitorConfigMap.size() > 0) {
            MonitorConfig monitorConfig = null;
            for (MonitorConfig config : monitorConfigMap.values()) {
                if (config.isDefault() == null || config.isDefault().booleanValue()) {
                    if (monitorConfig != null) {
                        throw new IllegalStateException("Duplicate monitor configs: " + monitorConfig + " and "
                                                        + config);
                    }
                    monitorConfig = config;
                }
            }
            if (monitorConfig != null) {
                reference.setMonitor(monitorConfig);
            }
        }
    }
}
