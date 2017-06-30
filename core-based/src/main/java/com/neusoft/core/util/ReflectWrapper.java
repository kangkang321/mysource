package com.neusoft.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.alibaba.dubbo.common.bytecode.NoSuchMethodException;

/**
 * 类ReflectWrapper.java的实现描述：反射，基於字節碼的
 * 
 * @author Administrator 2017年6月14日 下午4:54:57
 */
public class ReflectWrapper {

    public static Object invoke(Method method, Object bean, Object... args) throws NoSuchMethodException,
                                                                            InvocationTargetException {
        Wrapper wrapper = Wrapper.getWrapper(method.getDeclaringClass());
        return wrapper.invokeMethod(bean, method.getName(), method.getParameterTypes(), args);
    }

    /**
     * 这个貌似只能取得public
     * 
     * @param bean
     * @param fieldName
     * @return
     */
    public static Object getValue(Object bean, String fieldName) {
        Wrapper wrapper = Wrapper.getWrapper(bean.getClass());
        return wrapper.getPropertyValue(bean, fieldName);
    }

    public static void setValue(Object bean, String fieldName, Object value) {
        Wrapper wrapper = Wrapper.getWrapper(bean.getClass());
        wrapper.setPropertyValue(bean, fieldName, value);
    }

}
