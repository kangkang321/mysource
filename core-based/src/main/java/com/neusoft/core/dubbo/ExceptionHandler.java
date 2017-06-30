package com.neusoft.core.dubbo;

import java.util.function.BiConsumer;

import com.alibaba.dubbo.common.extension.SPI;
import com.neusoft.core.domain.dubbo.BizResult;

@SPI("default")
public interface ExceptionHandler extends BiConsumer<BizResult, Exception> {

}
