package com.neusoft.core.util;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Stack;
import java.util.function.Supplier;

import org.codehaus.plexus.classworlds.launcher.ConfigurationException;
import org.codehaus.plexus.classworlds.launcher.Configurator;
import org.codehaus.plexus.classworlds.launcher.Launcher;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.codehaus.plexus.classworlds.realm.DuplicateRealmException;
import org.codehaus.plexus.classworlds.realm.NoSuchRealmException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.neusoft.core.exception.BizException;

/**
 * 类M2Util.java的实现描述：目前所有項目里都有 ext/lib ext/class；這裡引用這些路徑下面的class
 * 
 * @author Administrator 2017年6月14日 下午4:28:10
 */
public class M2Util {

    private static Stack<SoftReference<ClassRealm>> classRealms = new Stack<>();

    @SuppressWarnings("rawtypes")
    public static SoftReference<Class> getClass(String clazz) {
        try {
            return new SoftReference<Class>(Class.forName(clazz, true, get()));
        } catch (ClassNotFoundException e) {
            throw new BizException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public static SoftReference<Class> getClass(Class clazz) {
        return getClass(clazz.getCanonicalName());
    }

    public synchronized static void refresh() {
        if (classRealms.isEmpty()) {

        } else {
            SoftReference<ClassRealm> realm = classRealms.pop();
            if (!realm.isEnqueued()) {
                realm.enqueue();
            }
        }
    }

    public synchronized static ClassRealm get() {
        if (classRealms.isEmpty() || classRealms.peek().get() == null) {
            if (!classRealms.isEmpty()) {
                classRealms.pop();
            }
            SoftReference<ClassRealm> realm = new SoftReference<ClassRealm>(createRealm.get());
            classRealms.push(realm);
        }
        return classRealms.peek().get();
    }

    private static Supplier<ClassRealm> createRealm = new Supplier<ClassRealm>() {

        public ClassRealm get() {
            String conf = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            File file = new File(conf);
            System.setProperty("basedir", file.getParent());
            Resource resource = null;
            Enumeration<URL> urls;
            try {
                urls = Thread.currentThread().getContextClassLoader().getResources("m2.conf");
            } catch (IOException e) {
                throw new BizException(e);
            }
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                resource = new UrlResource(url);
            }
            Configurator configurator;
            try {
                configurator = Configurator.class.getConstructor(Launcher.class).newInstance(new Object[] { null });
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                throw new BizException(e);
            }
            try {
                configurator.configure(resource.getInputStream());
            } catch (DuplicateRealmException | NoSuchRealmException | IOException | ConfigurationException e) {
                throw new BizException(e);
            }
            ClassRealm classRealm = (ClassRealm) ReflectionUtils.getFieldValue(configurator, "curRealm");
            classRealm.getParentRealm().setParentClassLoader(Thread.currentThread().getContextClassLoader());
            return classRealm;
        };
    };
}
