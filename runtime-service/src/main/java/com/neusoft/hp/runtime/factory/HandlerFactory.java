package com.neusoft.hp.runtime.factory;

import com.neusoft.hp.runtime.handler.BaseHandler;

public interface HandlerFactory {

    public BaseHandler getHandler(String clazz);
}
