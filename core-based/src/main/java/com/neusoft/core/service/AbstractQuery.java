package com.neusoft.core.service;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.neusoft.core.domain.dubbo.BizResult;
import com.neusoft.core.dubbo.ExceptionHandler;

/**
 * 类AbstractQuery.java的实现描述：dubbo查询接口基类 FIXME 后续考虑加入底层jar包
 * 
 * @author Administrator 2017年3月16日 上午11:08:52
 */
public abstract class AbstractQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQuery.class);

    @SuppressWarnings("rawtypes")
    protected BizResult doQuery(Supplier fn, Consumer<BizResult> callback) {
        BizResult result = new BizResult();
        try {
            Object t = fn.get();// 实际业务逻辑，由开发实现
            result.setValue(t);
            result.setSuccess(true);
            if (null != callback) {
                callback.accept(result);
            }
        } catch (Exception e) {
            exceptionHandler(result, e);
            LOGGER.error("执行查询时出现异常");
            e.printStackTrace();
        }
        return result;
    }

    private void exceptionHandler(BizResult r, Exception e) {
        try {
            Set<String> sets = ExtensionLoader.getExtensionLoader(ExceptionHandler.class).getSupportedExtensions();
            if (!CollectionUtils.isEmpty(sets)) {
                sets.stream().map(m -> ExtensionLoader.getExtensionLoader(ExceptionHandler.class).getExtension(m)).forEach(m -> m.accept(r,
                                                                                                                                         e));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

}
