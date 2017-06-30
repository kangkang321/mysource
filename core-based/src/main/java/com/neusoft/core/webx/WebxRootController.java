package com.neusoft.core.webx;

import org.springframework.beans.BeansException;

/**
 * 用来处理请求的控制器。
 *
 * @author Michael Zhou
 */
public interface WebxRootController {

    void init(WebxComponents components);

    void onRefreshContext() throws BeansException;

    void onFinishedProcessContext();

}
