package com.neusoft.core.dubbo;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.neusoft.core.util.TraceUtils;

/**
 * 类DubboLogFilter.java的实现描述：配合dubbo做的日志切点，dubbo不允许service跟spring的aop融合即AnnotationBean有问题
 * 
 * @author Administrator 2017年5月8日 上午10:12:40
 */
public class DubboLogFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboLogFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result r = null;
        try {
            TraceUtils.beginTrace();
            MDC.put("url", invoker.getUrl());
            MDC.put("clazz", invoker.getInterface());
            MDC.put("method", invocation.getMethodName());
            LOGGER.debug("执行方法开始");
            r = invoker.invoke(invocation);
            LOGGER.debug("执行方法结束");
        } finally {
            TraceUtils.endTrace();
        }
        return r;
    }

}
