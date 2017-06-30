package com.neusoft.core.webx.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.context.ContextLoader;

import com.alibaba.dubbo.container.spring.SpringContainer;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.webx.util.FileUtil;

public class WebxServletContext implements ServletContext {

    private final static Logger             log            = LoggerFactory.getLogger(WebxServletContext.class);
    private final static WebxServletContext servletContext = new WebxServletContext();

    private ResourceLoader                  loader         = new FileSystemResourceLoader();

    private Map<String, Object>             attributes     = new ConcurrentHashMap<>();

    private Map<String, String>             inits          = new ConcurrentHashMap<>();

    private String                          contextPath    = "";

    private WebxServletContext(){
        inits.put(ContextLoader.CONFIG_LOCATION_PARAM, SpringContainer.DEFAULT_SPRING_CONFIG);
        contextPath = initContextPath();
    }

    private static String initContextPath() {
        String conf = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        return StringUtils.substring(conf, 0, conf.length() - "conf".length()) + "WEB-INF/lib/";
    }

    public static ServletContext getSingleInstance() {
        return servletContext;
    }

    @Override
    public String getContextPath() {
        return contextPath;
    }

    @Override
    public ServletContext getContext(String uripath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMajorVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMinorVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getMimeType(String file) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set getResourcePaths(String path) {
        // FIXME
        return Collections.EMPTY_SET;
    }

    @Override
    public URL getResource(String path) throws MalformedURLException {
        try {
            return loader.getResource(FileUtil.getAbsolutePathBasedOn(contextPath, path)).getURL();
        } catch (IOException e) {
            throw new MalformedURLException("invalid path");
        }
    }

    @Override
    public InputStream getResourceAsStream(String path) {
        try {
            return loader.getResource(FileUtil.getAbsolutePathBasedOn(contextPath, path)).getInputStream();
        } catch (IOException e) {
            throw new BizException("invalid path");
        }
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Servlet getServlet(String name) throws ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Enumeration getServlets() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Enumeration getServletNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void log(String msg) {
        log.debug(msg);
    }

    @Override
    public void log(Exception exception, String msg) {
        log.error(msg, exception);
    }

    @Override
    public void log(String message, Throwable throwable) {
        log.error(message, throwable);
    }

    @Override
    public String getRealPath(String path) {
        try {
            return loader.getResource(FileUtil.getAbsolutePathBasedOn(contextPath, path)).getURL().getPath();
        } catch (IOException e) {
            throw new BizException("invalid path");
        }
    }

    @Override
    public String getServerInfo() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInitParameter(String name) {
        return inits.get(name);
    }

    @Override
    public Enumeration getInitParameterNames() {
        return Collections.enumeration(inits.keySet());
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public Enumeration getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    @Override
    public void setAttribute(String name, Object object) {
        attributes.put(name, object);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public String getServletContextName() {
        throw new UnsupportedOperationException();
    }

}
