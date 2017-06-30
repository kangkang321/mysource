package com.neusoft.hp.runtime.factory.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neusoft.core.exception.DisconfException;
import com.neusoft.hp.runtime.factory.HandlerFactory;
import com.neusoft.hp.runtime.handler.BaseHandler;

public class SimpleHandlerFactoryImpl implements HandlerFactory {

    private static final Logger     LOGGER       = LoggerFactory.getLogger(SimpleHandlerFactoryImpl.class);

    public Map<String, BaseHandler> cacheHandler = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public BaseHandler getHandler(String clazz) {
        if (!cacheHandler.containsKey(clazz)) {
            try {
                Class<? extends BaseHandler> c = (Class<? extends BaseHandler>) Class.forName(clazz);
                cacheHandler.put(clazz, c.newInstance());
            } catch (ClassNotFoundException e) {
                LOGGER.error("加载Handler错误，未找到%s", clazz);
                throw new DisconfException(e);
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.error("加载Handler错误，不能实例化%s", clazz);
                throw new DisconfException(e);
            }

        }
        return cacheHandler.get(clazz);
    }

}
