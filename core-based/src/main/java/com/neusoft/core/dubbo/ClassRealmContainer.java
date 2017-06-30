package com.neusoft.core.dubbo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;

import org.codehaus.plexus.classworlds.launcher.ConfigurationException;
import org.codehaus.plexus.classworlds.launcher.Configurator;
import org.codehaus.plexus.classworlds.launcher.Launcher;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.codehaus.plexus.classworlds.realm.DuplicateRealmException;
import org.codehaus.plexus.classworlds.realm.NoSuchRealmException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.container.Container;
import com.alibaba.dubbo.container.spring.SpringContainer;
import com.neusoft.core.util.ReflectionUtils;

@Deprecated
public class ClassRealmContainer implements Container {

    private static final Logger           logger                = LoggerFactory.getLogger(SpringContainer.class);

    public static final String            SPRING_CONFIG         = "dubbo.spring.config";

    public static final String            DEFAULT_SPRING_CONFIG = "classpath*:META-INF/spring/*.xml";

    static ClassPathXmlApplicationContext context;

    public static ClassPathXmlApplicationContext getContext() {
        return context;
    }

    public void start() {
        String configPath = ConfigUtils.getProperty(SPRING_CONFIG);
        if (configPath == null || configPath.length() == 0) {
            configPath = DEFAULT_SPRING_CONFIG;
        }
        // 设置下classLoader；
        try {
            String conf = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            File file = new File(conf);
            System.setProperty("basedir", file.getParent());
            Resource resource = null;
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("m2.conf");
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                resource = new UrlResource(url);
            }
            Configurator configurator = Configurator.class.getConstructor(Launcher.class).newInstance(new Object[] { null });
            configurator.configure(resource.getInputStream());
            ClassRealm classRealm = (ClassRealm) ReflectionUtils.getFieldValue(configurator, "curRealm");
            classRealm.getParentRealm().setParentClassLoader(Thread.currentThread().getContextClassLoader());
            Thread.currentThread().setContextClassLoader(classRealm);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | DuplicateRealmException | NoSuchRealmException
                | IOException | ConfigurationException e) {
            e.printStackTrace();
        }
        context = new ClassPathXmlApplicationContext(configPath.split("[,\\s]+"));
        context.start();
    }

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

}
