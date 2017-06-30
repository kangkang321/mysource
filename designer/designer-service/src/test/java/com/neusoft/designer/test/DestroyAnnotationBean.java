package com.neusoft.designer.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import com.alibaba.dubbo.config.spring.AnnotationBean;

public class DestroyAnnotationBean extends AbstractTestExecutionListener {

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        Map<String, AnnotationBean> maps = testContext.getApplicationContext().getBeansOfType(AnnotationBean.class);
        maps.values().forEach(a -> {
            try {
                a.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("config/");
        try {
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
            method.setAccessible(true);
            method.invoke(SpringJUnit4ClassRunner.class.getClassLoader(), url);
        } catch (SecurityException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
