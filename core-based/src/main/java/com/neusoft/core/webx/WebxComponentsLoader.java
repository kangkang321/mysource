package com.neusoft.core.webx;

import static com.neusoft.core.webx.util.SpringExtUtil.assertNotNull;
import static com.neusoft.core.webx.util.SpringExtUtil.assertTrue;
import static com.neusoft.core.webx.util.SpringExtUtil.autowireAndInitialize;
import static com.neusoft.core.webx.util.SpringExtUtil.unsupportedOperation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SourceFilteringListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ServletContextResourcePatternResolver;
import org.springframework.web.servlet.FrameworkServlet;

import com.alibaba.dubbo.common.compiler.support.ClassUtils;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.container.Container;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.webx.WebxConfiguration.ComponentConfig;
import com.neusoft.core.webx.WebxConfiguration.ComponentsConfig;
import com.neusoft.core.webx.WebxConfigurationImpl.ComponentsConfigImpl;
import com.neusoft.core.webx.impl.WebxComponentContext;
import com.neusoft.core.webx.impl.WebxComponentsContext;
import com.neusoft.core.webx.impl.WebxServletContext;
import com.neusoft.core.webx.util.FileUtil;
import com.neusoft.core.webx.util.PathNameWildcardCompiler;
import com.neusoft.core.webx.util.SpringExtUtil;

/**
 * 用来装载webx components的装载器。
 *
 * @author Michael Zhou
 */
@Activate
public class WebxComponentsLoader extends ContextLoader implements Container {

    public static final String    EMPTY_STRING             = "";
    /**
     * 用于在servlet context中保存component context的attribute key前缀，兼容FrameworkServlet。
     */
    public final static String    COMPONENT_CONTEXT_PREFIX = FrameworkServlet.SERVLET_CONTEXT_PREFIX;

    private final static Logger   log                      = LoggerFactory.getLogger(WebxComponentsLoader.class);
    private String                webxConfigurationName;
    private ServletContext        servletContext;
    private WebApplicationContext componentsContext;
    private WebxComponentsImpl    components;

    /** 取得context中<code>WebxConfiguration</code>的名称。 */
    public String getWebxConfigurationName() {
        return webxConfigurationName == null ? "webxConfiguration" : webxConfigurationName;
    }

    /** 设置context中<code>WebxConfiguration</code>的名称。 */
    public void setWebxConfigurationName(String webxConfigurationName) {
        this.webxConfigurationName = StringUtils.trimToNull(webxConfigurationName);
    }

    /** 取得在servlet context中保存component context的key。 */
    @Deprecated
    public String getComponentContextAttributeName(String componentName) {
        return COMPONENT_CONTEXT_PREFIX + componentName;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    /** 取得components。 */
    public WebxComponents getWebxComponents() {
        return components;
    }

    /*
     * 入口函数
     */
    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) throws IllegalStateException,
                                                                                          BeansException {
        this.servletContext = servletContext;
        init();

        return super.initWebApplicationContext(servletContext);
    }

    protected void init() {
        setWebxConfigurationName(servletContext.getInitParameter("webxConfigurationName"));
    }

    @Override
    protected final Class<?> determineContextClass(ServletContext servletContext) throws ApplicationContextException {
        String contextClassName = servletContext.getInitParameter(CONTEXT_CLASS_PARAM);

        if (contextClassName != null) {
            return ClassUtils.forName(contextClassName);
        } else {
            return getDefaultContextClass();
        }
    }

    /**
     * 取得默认的components <code>WebApplicationContext</code>实现类。
     * <p>
     * 子类可以覆盖并修改此方法。
     * </p>
     */
    protected Class<? extends WebxComponentsContext> getDefaultContextClass() {
        return WebxComponentsContext.class;
    }

    /** 在componentsContext.refresh()之前被调用。 */
    @Override
    protected void customizeContext(ServletContext servletContext,
                                    ConfigurableWebApplicationContext componentsContext) {
        this.componentsContext = componentsContext;

        if (componentsContext instanceof WebxComponentsContext) {
            ((WebxComponentsContext) componentsContext).setLoader(this);
        }
    }

    /**
     * 在创建beanFactory之初被调用。
     *
     * @param webxComponentsContext
     */
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        // 由于初始化components依赖于webxConfiguration，而webxConfiguration可能需要由PropertyPlaceholderConfigurer来处理，
        // 此外，其它有一些BeanFactoryPostProcessors会用到components，
        // 因此components必须在PropertyPlaceholderConfigurer之后初始化，并在其它BeanFactoryPostProcessors之前初始化。
        //
        // 下面创建的WebxComponentsCreator辅助类就是用来确保这个初始化顺序：
        // 1. PriorityOrdered - PropertyPlaceholderConfigurer
        // 2. Ordered - WebxComponentsCreator
        // 3. 普通 - 其它BeanFactoryPostProcessors
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(WebxComponentsCreator.class);
        builder.addConstructorArgValue(this);
        BeanDefinition componentsCreator = builder.getBeanDefinition();
        componentsCreator.setAutowireCandidate(false);

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        String name = SpringExtUtil.generateBeanName(WebxComponentsCreator.class.getName(), registry);

        registry.registerBeanDefinition(name, componentsCreator);
    }

    public static class WebxComponentsCreator implements BeanFactoryPostProcessor, Ordered {

        private final WebxComponentsLoader loader;

        public WebxComponentsCreator(WebxComponentsLoader loader){
            this.loader = assertNotNull(loader, "WebxComponentsLoader");
        }

        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            if (loader.components == null) {
                WebxComponentsImpl components = loader.createComponents(loader.getParentConfiguration(), beanFactory);
                AbstractApplicationContext wcc = (AbstractApplicationContext) components.getParentApplicationContext();
                wcc.addApplicationListener(new SourceFilteringListener(wcc, components));
                loader.components = components;
            }
        }

        public int getOrder() {
            return Ordered.LOWEST_PRECEDENCE;
        }
    }

    /** 初始化所有components。 */
    public void finishRefresh() {
        components.getWebxRootController().onFinishedProcessContext();

        for (WebxComponent component : components) {
            logInBothServletAndLoggingSystem("Initializing Spring sub WebApplicationContext: " + component.getName());

            WebxComponentContext wcc = (WebxComponentContext) component.getApplicationContext();
            WebxController controller = component.getWebxController();

            wcc.refresh();
            controller.onFinishedProcessContext();
        }

        logInBothServletAndLoggingSystem("WebxComponents: initialization completed");
    }

    private void logInBothServletAndLoggingSystem(String msg) {
        servletContext.log(msg);
        log.info(msg);
    }

    /** 初始化components。 */
    private WebxComponentsImpl createComponents(WebxConfiguration parentConfiguration,
                                                ConfigurableListableBeanFactory beanFactory) {
        ComponentsConfig componentsConfig = getComponentsConfig(parentConfiguration);

        // 假如isAutoDiscoverComponents==true，试图自动发现components
        Map<String, String> componentNamesAndLocations = findComponents(componentsConfig, getServletContext());

        // 取得特别指定的components
        Map<String, ComponentConfig> specifiedComponents = componentsConfig.getComponents();

        // 实际要初始化的comonents，为上述两种来源的并集
        Set<String> componentNames = createTreeSet();

        componentNames.addAll(componentNamesAndLocations.keySet());
        componentNames.addAll(specifiedComponents.keySet());

        // 创建root controller
        WebxRootController rootController = componentsConfig.getRootController();

        if (rootController == null) {
            rootController = (WebxRootController) BeanUtils.instantiateClass(componentsConfig.getRootControllerClass());
        }

        // 创建并将components对象置入resolvable dependencies，以便注入到需要的bean中
        WebxComponentsImpl components = new WebxComponentsImpl(componentsContext,
                                                               componentsConfig.getDefaultComponent(), rootController,
                                                               parentConfiguration);

        beanFactory.registerResolvableDependency(WebxComponents.class, components);

        // 初始化每个component
        // FIXME 这部分放到配置文件更新的时候做，另外还要初始化refresh
        for (String componentName : componentNames) {
            ComponentConfig componentConfig = specifiedComponents.get(componentName);

            String componentPath = null;
            WebxController controller = null;

            if (componentConfig != null) {
                componentPath = componentConfig.getPath();
                controller = componentConfig.getController();
            }

            if (controller == null) {
                controller = (WebxController) BeanUtils.instantiateClass(componentsConfig.getDefaultControllerClass());
            }

            WebxComponentImpl component = new WebxComponentImpl(components, componentName, componentPath,
                                                                componentName.equals(componentsConfig.getDefaultComponent()),
                                                                controller, getWebxConfigurationName());

            components.addComponent(component);

            prepareComponent(component, componentNamesAndLocations.get(componentName));
        }

        return components;
    }

    @SuppressWarnings("resource")
    private void prepareComponent(WebxComponentImpl component, String componentLocation) {
        String componentName = component.getName();
        WebxComponentContext wcc = new WebxComponentContext(component);

        wcc.setServletContext(getServletContext());
        URL jar;
        try {
            jar = getServletContext().getResource(component.getComponentPath());
        } catch (MalformedURLException e) {
            throw new BizException(e);
        }
        URLClassLoader loader = new URLClassLoader(new URL[] { jar }, wcc.getClassLoader());
        wcc.setClassLoader(loader);
        wcc.setNamespace(componentName);
        wcc.addApplicationListener(new SourceFilteringListener(wcc, component));

        if (componentLocation != null) {
            wcc.setConfigLocation(componentLocation);
        }

        component.setApplicationContext(wcc);

        // 将context保存在servletContext中
        String attrName = getComponentContextAttributeName(componentName);
        getServletContext().setAttribute(attrName, wcc);

        log.debug("Published WebApplicationContext of component {} as ServletContext attribute with name [{}]",
                  componentName, attrName);
    }

    /** 查找component名称。 */
    private Map<String, String> findComponents(ComponentsConfig componentsConfig, ServletContext servletContext) {
        String locationPattern = componentsConfig.getComponentConfigurationLocationPattern();
        String[] prefixAndPattern = checkComponentConfigurationLocationPattern(locationPattern);
        String prefix = prefixAndPattern[0];
        String pathPattern = prefixAndPattern[1];

        Map<String, String> componentNamesAndLocations = createTreeMap();

        if (componentsConfig.isAutoDiscoverComponents()) {
            try {
                ResourcePatternResolver resolver = new ServletContextResourcePatternResolver(servletContext);
                Resource[] componentConfigurations = resolver.getResources(locationPattern);
                Pattern pattern = PathNameWildcardCompiler.compilePathName(pathPattern);

                if (componentConfigurations != null) {
                    for (Resource resource : componentConfigurations) {
                        String path = resource.getURL().getPath();
                        Matcher matcher = pattern.matcher(path);

                        assertTrue(matcher.find(), "unknown component configuration file: %s", path);
                        String componentName = StringUtils.trimToNull(matcher.group(1));

                        if (componentName != null) {
                            componentNamesAndLocations.put(componentName,
                                                           prefix + pathPattern.replace("*", componentName));
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return componentNamesAndLocations;
    }

    /**
     * 检查componentConfigurationLocationPattern是否符合以下要求：
     * <ol>
     * <li>非空。</li>
     * <li>包含且只包含一个<code>*</code>。</li>
     * <li>支持<code>classpath*:</code>前缀。</li>
     * </ol>
     * <p>
     * 返回数组：[前缀, 不包含<code>classpath*:</code>的路径]。
     * </p>
     */
    private String[] checkComponentConfigurationLocationPattern(String componentConfigurationLocationPattern) {
        if (componentConfigurationLocationPattern != null) {
            // 允许并剔除classpath*:前缀。
            boolean classpath = componentConfigurationLocationPattern.startsWith("classpath*:");
            String pathPattern = componentConfigurationLocationPattern;

            if (classpath) {
                pathPattern = componentConfigurationLocationPattern.substring("classpath*:".length()).trim();
            }

            // 检查路径。
            int index = pathPattern.indexOf("*");

            if (index >= 0) {
                index = pathPattern.indexOf("*", index + 1);

                if (index < 0) {
                    if (pathPattern.startsWith("/")) {
                        pathPattern = pathPattern.substring(1);
                    }

                    return new String[] { classpath ? "classpath:" : EMPTY_STRING, pathPattern };
                }
            }
        }

        throw new IllegalArgumentException("Invalid componentConfigurationLocationPattern: "
                                           + componentConfigurationLocationPattern);
    }

    /** 从parent configuration中取得components配置。 */
    private ComponentsConfig getComponentsConfig(WebxConfiguration parentConfiguration) {
        ComponentsConfig componentsConfig = assertNotNull(parentConfiguration,
                                                          "parentConfiguration").getComponentsConfig();

        if (componentsConfig == null) {
            // create default components configuration
            componentsConfig = new ComponentsConfigImpl();
        }

        return componentsConfig;
    }

    /** 从parent context中取得<code>WebxConfiguration</code>。 */
    private WebxConfiguration getParentConfiguration() {
        try {
            return (WebxConfiguration) componentsContext.getBean(getWebxConfigurationName());
        } catch (BeansException e) {
            // create default configuration
            WebxConfigurationImpl parentConfiguration = new WebxConfigurationImpl();
            parentConfiguration.setApplicationContext(componentsContext);

            try {
                parentConfiguration.afterPropertiesSet();
            } catch (RuntimeException ee) {
                throw ee;
            } catch (Exception ee) {
                throw new RuntimeException(ee);
            }

            return parentConfiguration;
        }
    }

    private static class WebxComponentsImpl implements WebxComponents, ApplicationListener {

        private final WebxConfiguration          parentConfiguration;
        private final WebApplicationContext      parentContext;
        private final Map<String, WebxComponent> components;
        private final RootComponent              rootComponent;
        private final String                     defaultComponentName;
        private final WebxRootController         rootController;

        public WebxComponentsImpl(WebApplicationContext parentContext, String defaultComponentName,
                                  WebxRootController rootController, WebxConfiguration parentConfiguration){
            this.parentConfiguration = assertNotNull(parentConfiguration, "no parent webx-configuration");
            this.parentContext = parentContext;
            this.components = createHashMap();
            this.rootComponent = new RootComponent();
            this.defaultComponentName = defaultComponentName;
            this.rootController = assertNotNull(rootController, "no rootController");

            rootController.init(this);
        }

        public WebxConfiguration getParentWebxConfiguration() {
            return parentConfiguration;
        }

        private void addComponent(WebxComponent component) {
            components.put(component.getName(), component);
        }

        public WebxComponent getComponent(String componentName) {
            if (componentName == null) {
                return rootComponent;
            } else {
                return components.get(componentName);
            }
        }

        public String[] getComponentNames() {
            String[] names = components.keySet().toArray(new String[components.size()]);
            Arrays.sort(names);
            return names;
        }

        public WebxComponent getDefaultComponent() {
            return defaultComponentName == null ? null : components.get(defaultComponentName);
        }

        public Iterator<WebxComponent> iterator() {
            return components.values().iterator();
        }

        public WebxComponent findMatchedComponent(String path) {
            if (!path.startsWith("/")) {
                path = "/" + path;
            }

            WebxComponent defaultComponent = getDefaultComponent();
            WebxComponent matched = null;

            // 前缀匹配componentPath。
            for (WebxComponent component : this) {
                if (component == defaultComponent) {
                    continue;
                }

                String componentPath = component.getComponentPath();

                if (!path.startsWith(componentPath)) {
                    continue;
                }

                // path刚好等于componentPath，或者path以componentPath/为前缀
                if (path.length() == componentPath.length() || path.charAt(componentPath.length()) == '/') {
                    matched = component;
                    break;
                }
            }

            // fallback to default component
            if (matched == null) {
                matched = defaultComponent;
            }

            return matched;
        }

        public WebxRootController getWebxRootController() {
            return rootController;
        }

        public WebApplicationContext getParentApplicationContext() {
            return parentContext;
        }

        public void onApplicationEvent(ApplicationEvent event) {
            if (event instanceof ContextRefreshedEvent) {
                // autowire and init root controller
                autowireAndInitialize(rootController, getParentApplicationContext(),
                                      AbstractBeanDefinition.AUTOWIRE_AUTODETECT, "webxRootController");

                rootController.onRefreshContext();
            }
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("parentContext",
                                                    parentContext).append("defaultComponentName",
                                                                          defaultComponentName).append("components",
                                                                                                       components).append("rootController",
                                                                                                                          rootController).toString();
        }

        /** 这是一个特殊的component实现，对应于root context。 */
        private class RootComponent implements WebxComponent {

            public WebxComponents getWebxComponents() {
                return WebxComponentsImpl.this;
            }

            public String getName() {
                return null;
            }

            public String getComponentPath() {
                return EMPTY_STRING;
            }

            public WebxConfiguration getWebxConfiguration() {
                return getParentWebxConfiguration();
            }

            public WebxController getWebxController() {
                unsupportedOperation("RootComponent.getWebxController()");
                return null;
            }

            public WebApplicationContext getApplicationContext() {
                return getParentApplicationContext();
            }

            @Override
            public String toString() {
                return WebxComponentsImpl.this.toString();
            }
        }
    }

    private static class WebxComponentImpl implements WebxComponent, ApplicationListener {

        private final WebxComponents  components;
        private final String          name;
        private final String          componentPath;
        private final WebxController  controller;
        private final String          webxConfigurationName;
        private WebApplicationContext context;

        public WebxComponentImpl(WebxComponents components, String name, String path, boolean defaultComponent,
                                 WebxController controller, String webxConfigurationName){
            this.components = assertNotNull(components, "components");
            this.name = assertNotNull(name, "componentName");
            this.controller = assertNotNull(controller, "controller");
            this.webxConfigurationName = assertNotNull(webxConfigurationName, "webxConfigurationName");

            // 规格化path，去除尾部的/；空路径则设为null
            path = StringUtils.trimToNull(FileUtil.normalizeAbsolutePath(path));// FIXME

            if (defaultComponent) {
                assertTrue(path == null, "default component \"%s\" should not have component path \"%s\"", name, path);
                this.componentPath = EMPTY_STRING;
            } else if (path != null) {
                this.componentPath = path;
            } else {
                this.componentPath = "/" + name;
            }

            controller.init(this);
        }

        public WebxComponents getWebxComponents() {
            return components;
        }

        public String getName() {
            return name;
        }

        public String getComponentPath() {
            return componentPath;
        }

        public WebxController getWebxController() {
            return controller;
        }

        public WebxConfiguration getWebxConfiguration() {
            return (WebxConfiguration) context.getBean(webxConfigurationName);
        }

        public WebApplicationContext getApplicationContext() {
            return context;
        }

        private void setApplicationContext(WebApplicationContext context) {
            this.context = context;
        }

        public void onApplicationEvent(ApplicationEvent event) {
            if (event instanceof ContextRefreshedEvent) {
                // autowire and init controller
                autowireAndInitialize(controller, getApplicationContext(), AbstractBeanDefinition.AUTOWIRE_AUTODETECT,
                                      "webxController." + getName());

                controller.onRefreshContext();
            }
        }

        @Override
        public String toString() {

            return new ToStringBuilder(this).append("name",
                                                    name).append("path",
                                                                 componentPath).append("controller",
                                                                                       controller).append("context",
                                                                                                          context).toString();
        }
    }

    /** 创建一个<code>HashMap</code>。 */
    public static <K, V> HashMap<K, V> createHashMap() {
        return new HashMap<K, V>();
    }

    /** 创建一个<code>TreeMap</code>。 */
    public static <K, V> TreeMap<K, V> createTreeMap() {
        return new TreeMap<K, V>();
    }

    /** 创建一个<code>TreeSet</code>。 */
    public static <T> TreeSet<T> createTreeSet() {
        return new TreeSet<T>();
    }

    @Override
    public void start() {
        initWebApplicationContext(WebxServletContext.getSingleInstance());

    }

    @Override
    public void stop() {
        closeWebApplicationContext(WebxServletContext.getSingleInstance());

    }

}
