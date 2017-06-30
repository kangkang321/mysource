package com.neusoft.core.dubbo;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.alibaba.dubbo.common.bytecode.ClassGenerator;
import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.neusoft.core.service.annotation.LazyAutowired;

/**
 * 类LazyAutowiredProxy.java的实现描述：代理類，把依賴注入推遲到方法調用時 {@link LazyAutowired}
 * 
 * @author Administrator 2017年6月14日 下午4:23:01
 */
public class LazyAutowiredProxy {

    public static ApplicationContext                           applicationContext;

    private static final AtomicLong                            PROXY_CLASS_COUNTER     = new AtomicLong(0);

    private static final Map<ClassLoader, Map<String, Object>> ProxyCacheMap           = new WeakHashMap<ClassLoader, Map<String, Object>>();

    private static final String                                PACKAGE_NAME            = LazyAutowiredProxy.class.getPackage().getName();

    private static final Object                                PendingGenerationMarker = new Object();

    protected static void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        Map<String, T> maps = applicationContext.getBeansOfType(requiredType);
        T ref = null;
        for (String key : maps.keySet()) {
            ref = maps.get(key);
            if (StringUtils.equals(name, key)) {
                break;
            }
        }
        if (null == ref) {
            ReferenceBean<T> refBean = (ReferenceBean<T>) applicationContext.getBean("&" + name);
            ref = refBean.get();
        }
        return ref;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(Class<T> clazzT, String beanName) {
        ClassLoader cl = LazyAutowiredProxy.class.getClassLoader();

        // use interface class name list as key.
        String key = beanName;

        // get cache by class loader.
        Map<String, Object> cache;
        synchronized (ProxyCacheMap) {
            cache = ProxyCacheMap.get(cl);
            if (cache == null) {
                cache = new HashMap<String, Object>();
                ProxyCacheMap.put(cl, cache);
            }
        }

        T proxy = null;
        synchronized (cache) {
            do {
                Object value = cache.get(key);
                if (value instanceof Reference<?>) {
                    proxy = (T) ((Reference<?>) value).get();
                    if (proxy != null) return proxy;
                }

                if (value == PendingGenerationMarker) {
                    try {
                        cache.wait();
                    } catch (InterruptedException e) {
                    }
                } else {
                    cache.put(key, PendingGenerationMarker);
                    break;
                }
            } while (true);
        }

        long id = PROXY_CLASS_COUNTER.getAndIncrement();
        String pkg = null;
        ClassGenerator ccp = null, ccm = null;
        try {
            ccp = ClassGenerator.newInstance(cl);

            Set<String> worked = new HashSet<String>();
            List<Method> methods = new ArrayList<Method>();

            ccp.addInterface(clazzT);

            for (Method method : clazzT.getMethods()) {
                String desc = ReflectUtils.getDesc(method);
                if (worked.contains(desc)) continue;
                worked.add(desc);

                // int ix = methods.size();
                Class<?> rt = method.getReturnType();
                Class<?>[] pts = method.getParameterTypes();

                StringBuilder code = (new StringBuilder("((")).append(clazzT.getName()).append(")").append(LazyAutowiredProxy.class.getName()).append(".getBean(\"").append(beanName).append("\",").append(clazzT.getName()).append(".class)).").append(method.getName()).append("($$);");
                if (Void.TYPE.equals(rt)) {

                } else {
                    code.insert(0, "return ");
                }
                methods.add(method);
                ccp.addMethod(method.getName(), method.getModifiers(), rt, pts, method.getExceptionTypes(),
                              code.toString());
            }

            if (pkg == null) pkg = PACKAGE_NAME;

            // create ProxyInstance class.
            String pcn = pkg + ".proxy" + id;
            ccp.setClassName(pcn);
            ccp.addDefaultConstructor();
            Class<?> clazz = ccp.toClass();
            proxy = (T) clazz.newInstance();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            // release ClassGenerator
            if (ccp != null) ccp.release();
            if (ccm != null) ccm.release();
            synchronized (cache) {
                if (proxy == null) cache.remove(key);
                else cache.put(key, new WeakReference<T>(proxy));
                cache.notifyAll();
            }
        }
        return proxy;
    }

}
