package com.neusoft.core.util;

import javax.servlet.ServletRequest;

/**
 * 类SpringMVCUtil.java的实现描述：配合SpringMVCFillter取得入參
 * 
 * @author Administrator 2017年6月14日 下午4:55:10
 */
public class SpringMVCUtil {

    private static ThreadLocal<ServletRequest> request = new ThreadLocal<>();

    public static void setRequest(ServletRequest r) {
        request.set(r);
    }

    public static ServletRequest getRequest() {
        return request.get();
    }

    public static void release() {
        request.set(null);
        request.remove();
    }
}
