package com.neusoft.hp.runtime.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.hp.runtime.client.QuerySql;
import com.neusoft.hp.runtime.dyn.mapper.DynSqlMapper;

/**
 * 类QuerySqlImpl.java的实现描述：sql查询的默认实现，业务方可以有自己的实现；要发布成dubbo服务
 * 
 * @author Administrator 2017年6月14日 下午5:32:16
 */
public class QuerySqlImpl implements QuerySql {

    @Autowired
    private DynSqlMapper dynSqlMapper;

    @Override
    public String querySqlValue(String sql) {
        return dynSqlMapper.selectValue(sql);

    }

    @Override
    public List<String> querySqlList(String sql) {
        return dynSqlMapper.queryValues(sql);
    }

}
