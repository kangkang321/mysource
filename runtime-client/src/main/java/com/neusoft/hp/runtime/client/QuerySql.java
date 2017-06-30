package com.neusoft.hp.runtime.client;

import java.util.List;

/**
 * 分库查询接口，由業務方提供
 * 
 * @author Administrator
 */
public interface QuerySql {

    /**
     * 得到一個返回值，一般是根據gid查詢
     * 
     * @param sql
     * @return
     */
    public String querySqlValue(String sql);

    /**
     * 得到多個返回值
     * 
     * @param sql
     * @return
     */
    public List<String> querySqlList(String sql);

}
