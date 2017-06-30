package com.neusoft.hp.runtime.factory;

import com.neusoft.core.dao.GenDao;

public interface MapperFactory {

    public Class<? extends GenDao> getMapper(String clazz);
}
