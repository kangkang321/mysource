package com.neusoft.core.webx;

import org.springframework.context.ApplicationContext;

public interface WebxComponent {

    /** 取得当前component所属的components集合。 */
    WebxComponents getWebxComponents();

    /** 取得所有component的名称。 */
    String getName();

    /** 取得指定component的component path。如果是默认component，则返回空字符串。 */
    String getComponentPath();

    /** 取得webx configuration设置。 */
    WebxConfiguration getWebxConfiguration();

    /** 取得用来处理当前component请求的controller。 */
    WebxController getWebxController();

    /** 取得当前component对应的application context容器。 */
    ApplicationContext getApplicationContext();
}
