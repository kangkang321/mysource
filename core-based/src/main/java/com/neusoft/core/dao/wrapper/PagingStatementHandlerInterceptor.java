package com.neusoft.core.dao.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.druid.util.JdbcConstants;
import com.neusoft.core.dao.GenExample;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.util.ReflectionUtils;

/**
 * 类PagingStatementHandlerInterceptor.java的实现描述：分页插件
 * 
 * @author Administrator 2017年3月16日 上午11:26:34
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }),
              @Signature(type = StatementHandler.class, method = "query", args = { Statement.class,
                                                                                   ResultHandler.class }) })
public class PagingStatementHandlerInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PagingStatementHandlerInterceptor.class);

    private static Object getFieldValue(Object target, String name) {
        return ReflectionUtils.getFieldValue(target, name);
    }

    private static void setFieldValue(Object target, String name, String value) {
        ReflectionUtils.setFieldValue(target, name, value);
    }

    /**
     * 分页拦截器。
     * <p>
     * 功能主要是用来取总记录数和对SQL分页拦截。
     * 
     * @param invocation
     * @return
     * @throws Throwable
     * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler routingStatementHandler = (RoutingStatementHandler) invocation.getTarget();
        BaseStatementHandler delegate = (BaseStatementHandler) getFieldValue(routingStatementHandler, "delegate");

        MappedStatement mappedStatement = (MappedStatement) getFieldValue(delegate, "mappedStatement");
        BoundSql boundSql = delegate.getBoundSql();
        Object parameterObject = boundSql.getParameterObject();
        if (parameterObject == null) {
            LOGGER.error("参数对象尚未实例化！");
        }
        if (parameterObject != null && parameterObject instanceof GenExample
            && ((GenExample) parameterObject).isUsedPage()) {
            GenExample pageBean = (GenExample) parameterObject;
            Connection connection = null;
            if (StringUtils.equals(invocation.getMethod().getName(), "prepare")) {
                connection = (Connection) invocation.getArgs()[0];
            } else if (StringUtils.equals(invocation.getMethod().getName(), "query")) {
                connection = ((Statement) invocation.getArgs()[0]).getConnection();
            }
            String sql = boundSql.getSql();
            String newCountSql = PagerUtils.count(sql, JdbcConstants.ORACLE);
            BoundSql newBoundSql = mappedStatement.getBoundSql(parameterObject);
            setFieldValue(newBoundSql, "sql", newCountSql);
            DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject,
                                                                                   newBoundSql);

            PreparedStatement ps = null;
            int count = 0;
            try {
                ps = connection.prepareStatement(newCountSql);
                parameterHandler.setParameters(ps);
                ResultSet rs = ps.executeQuery();
                count = (rs.next()) ? rs.getInt(1) : 0;
                rs.close();
            } catch (SQLException e) {
                LOGGER.error("执行记录总数SQL时发生异常");
                throw new BizException("执行记录总数SQL时发生异常", e);
            } finally {
                try {
                    if (ps != null) ps.close();
                } catch (SQLException e) {
                    LOGGER.error("关闭状态时发生异常");
                    throw new BizException("关闭状态时发生异常", e);
                }
            }
            pageBean.getPage().setTotalCount(count);
            pageBean.setUsedPage(false);
            String newSql = PagerUtils.limit(sql, JdbcConstants.ORACLE,
                                             (pageBean.getPage().getPageNum() - 1) * pageBean.getPage().getPageSize(),
                                             pageBean.getPage().getPageSize());
            setFieldValue(boundSql, "sql", newSql);
        }

        return invocation.proceed();
    }

    /**
     * 拦截本插件。
     * 
     * @param target
     * @return
     * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
