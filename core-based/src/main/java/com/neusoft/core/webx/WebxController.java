package com.neusoft.core.webx;

import org.springframework.beans.BeansException;

/**
 * 用来处理对特定<code>WebxComponent</code>的请求的控制器。
 *
 * @author Michael Zhou
 */
public interface WebxController {

    void init(WebxComponent component);

    void onRefreshContext() throws BeansException;

    void onFinishedProcessContext();

}
