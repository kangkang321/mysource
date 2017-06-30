package com.neusoft.hp.core;

import com.alibaba.dubbo.common.extension.Activate;
import com.neusoft.core.domain.dubbo.BizResult;
import com.neusoft.core.dubbo.ExceptionHandler;

@Activate
public class DefaultExceptionHandler implements ExceptionHandler {

    @Override
    public void accept(BizResult t, Exception u) {

    }

}
