package com.neusoft.hp.runtime.factory.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neusoft.core.dao.GenDao;
import com.neusoft.core.exception.DisconfException;
import com.neusoft.hp.runtime.factory.MapperFactory;

public class SimpleMapperFactoryImpl implements MapperFactory {

    private static final Logger                  LOGGER      = LoggerFactory.getLogger(SimpleMapperFactoryImpl.class);

    public Map<String, Class<? extends GenDao>> cacheMapper = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends GenDao> getMapper(String clazz) {
        if (!cacheMapper.containsKey(clazz)) {
            try {
                Class<? extends GenDao> mapper = (Class<? extends GenDao>) Class.forName(clazz);
                cacheMapper.put(clazz, mapper);
                LOGGER.error("加载Mapper错误，未找到%s", clazz);
            } catch (ClassNotFoundException e) {
                throw new DisconfException(e);
            }

        }
        return cacheMapper.get(clazz);
    }

}
