package com.neusoft.hp.dubbo.container;

import static com.neusoft.core.webx.util.SpringExtUtil.assertNotNull;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.codehaus.plexus.classworlds.launcher.ConfigurationException;
import org.codehaus.plexus.classworlds.launcher.Configurator;
import org.codehaus.plexus.classworlds.launcher.Launcher;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.codehaus.plexus.classworlds.realm.DuplicateRealmException;
import org.codehaus.plexus.classworlds.realm.NoSuchRealmException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.container.Container;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.util.ReflectionUtils;
import com.neusoft.hp.dubbo.container.context.WebxComponent;
import com.neusoft.hp.dubbo.container.context.WebxComponents;
import com.neusoft.hp.dubbo.container.context.WebxConfiguration;
import com.neusoft.hp.dubbo.container.context.WebxConfiguration.ComponentConfig;
import com.neusoft.hp.dubbo.container.context.impl.WebxComponentImpl;
import com.neusoft.hp.dubbo.container.context.impl.WebxComponentsImpl;
import com.neusoft.hp.dubbo.container.context.impl.WebxConfigurationImpl;
import com.neusoft.hp.dubbo.container.context.impl.WebxContext;
import com.neusoft.hp.dubbo.container.context.impl.WebxRootContext;
import com.neusoft.md.dictionary.client.IDictEnumValueService;

public class WebxContainer implements Container {

    private static final Logger           logger                = LoggerFactory.getLogger(WebxContainer.class);

    public static final String            SPRING_CONFIG         = "dubbo.spring.config";

    public static final String            DEFAULT_SPRING_CONFIG = "classpath*:META-INF/spring/*.xml";

    private Map<String, ClassRealm>       configuredRealms;

    static ClassPathXmlApplicationContext context;

    static WebxComponents                 components;

    static ClassRealm                     classRealm;

    public static ClassPathXmlApplicationContext getContext() {
        return context;
    }

    public static ClassRealm getClassRealm() {
        return classRealm;
    }

    public static WebxComponents getWebxComponents() {
        return components;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start() {
        Resource webx = new ClassPathResource("config/webx.conf");
        Configurator configurator;
        try {
            System.setProperty("basedir", webx.getFile().getParentFile().getParentFile().getParent());
            configurator = Configurator.class.getConstructor(Launcher.class).newInstance(new Object[] { null });
            configurator.configure(webx.getInputStream());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | DuplicateRealmException | NoSuchRealmException
                | IOException | ConfigurationException e) {
            logger.error("加载webx文件失败");
            throw new BizException(e);
        }
        classRealm = (ClassRealm) ReflectionUtils.getFieldValue(configurator, "curRealm");
        configuredRealms = (Map<String, ClassRealm>) ReflectionUtils.getFieldValue(configurator, "configuredRealms");
        context = new WebxRootContext(this);
        context.setConfigLocation("classpath*:META-INF/webx/*.xml");
        context.refresh();// FIXME
        context.start();
    }

    @Override
    public void stop() {
        try {
            if (context != null) {
                context.stop();
                context.close();
                context = null;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void finishRefresh() {
        IDictEnumValueService service = null;
        for (WebxComponent component : components) {
            Resources.setDefaultClassLoader(component.getClassRealm());
            System.setProperty("owner", component.getPath());
            ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) component.getApplicationContext();
            context.refresh();
            // if (context.containsBean("dictEnumValueService")) {
            // service = (IDictEnumValueService) context.getBean("dictEnumValueService");
            // }
            // if (!getContext().getBeanFactory().containsBean("dictEnumValueService")) {
            // getContext().getBeanFactory().registerSingleton("dictEnumValueService", service);
            // }
        }
    }

    private WebxConfiguration getParentConfiguration() {
        return new WebxConfigurationImpl(configuredRealms.keySet(),
                                         null == ConfigUtils.getProperty(SPRING_CONFIG) ? DEFAULT_SPRING_CONFIG : ConfigUtils.getProperty(SPRING_CONFIG));
    }

    private WebxComponents createComponents(WebxConfiguration webxConfiguration,
                                            ConfigurableListableBeanFactory beanFactory) {
        WebxComponentsImpl components = new WebxComponentsImpl(webxConfiguration, beanFactory,
                                                               classRealm.getParentRealm());
        beanFactory.registerResolvableDependency(WebxComponents.class, components);// 使得所有类都可以依赖注入WebxComponents
        Map<String, ComponentConfig> componentConfigs = webxConfiguration.getComponentsConfig().getComponents();
        for (String key : componentConfigs.keySet()) {
            ComponentConfig componentConfig = componentConfigs.get(key);
            WebxComponentImpl component = new WebxComponentImpl(components, componentConfig.getName(),
                                                                componentConfig.getPath());
            components.addComponent(component);
            prepareComponent(component, webxConfiguration.getComponentConfigurationLocation());
        }
        return components;
    }

    private void prepareComponent(WebxComponentImpl component, String componentLocation) {
        String name = component.getName();
        ClassRealm classRealm = configuredRealms.get(name);
        component.setClassRealm(classRealm);
        try {
            @SuppressWarnings("unchecked")
            Constructor<WebxContext> constructor = (Constructor<WebxContext>) ClassUtils.getConstructorIfAvailable(ClassUtils.forName("com.neusoft.hp.dubbo.container.context.impl.WebxContext",
                                                                                                                                      classRealm),
                                                                                                                   WebxComponent.class);
            WebxContext context = constructor.newInstance(component);
            context.setParent(getContext());
            context.setConfigLocations(component.getRoot().getConfigPath().split("[,\\s]+"));
            component.setApplicationContext(context);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | LinkageError
                | IllegalArgumentException | InvocationTargetException e) {
            logger.error("can not create component");
            throw new BizException(e);
        }
    }

    public static class WebxComponentsCreator implements BeanFactoryPostProcessor, Ordered {

        private final WebxContainer container;

        public WebxComponentsCreator(WebxContainer container){
            this.container = assertNotNull(container, "WebxContainer");
        }

        @SuppressWarnings("static-access")
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            if (container.components == null) {
                WebxComponents components = container.createComponents(container.getParentConfiguration(), beanFactory);
                // AbstractApplicationContext wcc = (AbstractApplicationContext) components.getApplicationContext();
                // wcc.addApplicationListener(new SourceFilteringListener(wcc, components));
                container.components = components;
            }
        }

        public int getOrder() {
            return Ordered.LOWEST_PRECEDENCE;
        }
    }

}
