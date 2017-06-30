package com.neusoft.hp.runtime.client;

import java.util.List;

/**
 * 类QuerySqlByOwner.java的实现描述：平台方使用，通过owner调用不同业务方的{@link QuerySql}
 * 
 * @author Administrator 2017年6月14日 下午5:16:48
 */
public interface QuerySqlByOwner {

    public String querySqlValue(String sql, String owner);

    public List<String> querySqlList(String sql, String owner);
}
