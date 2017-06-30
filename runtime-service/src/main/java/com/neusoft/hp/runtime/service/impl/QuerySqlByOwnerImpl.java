package com.neusoft.hp.runtime.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.rpc.service.GenericService;
import com.neusoft.core.dubbo.GenericServiceImpl;
import com.neusoft.hp.runtime.client.QuerySql;
import com.neusoft.hp.runtime.client.QuerySqlByOwner;

/**
 * 类QuerySqlByOwnerImpl.java的实现描述：引用实现的配合
 * 
 * @author Administrator 2017年6月14日 下午5:31:14
 */
public class QuerySqlByOwnerImpl implements QuerySqlByOwner {

    @Autowired
    private GenericServiceImpl genericServiceImpl;

    @Override
    public String querySqlValue(String sql, String owner) {
        GenericService genericService = genericServiceImpl.getGenericService(QuerySql.class, owner);
        return (String) genericService.$invoke("querySqlValue", new String[] { "java.lang.String" },
                                               new Object[] { sql });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> querySqlList(String sql, String owner) {
        GenericService genericService = genericServiceImpl.getGenericService(QuerySql.class, owner);
        return (List<String>) genericService.$invoke("querySqlList", new String[] { "java.lang.String" },
                                                     new Object[] { sql });
    }

}
