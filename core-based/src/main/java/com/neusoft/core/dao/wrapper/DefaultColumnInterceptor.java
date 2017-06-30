package com.neusoft.core.dao.wrapper;

import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neusoft.core.dao.GenDO;

/**
 * 类DefaultColumnInterceptor.java的实现描述：当用户调用自动生成的mapper文件操作数据库是会操作默认字段
 * 
 * @author Administrator 2017年3月16日 上午11:25:28
 */
public class DefaultColumnInterceptor implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultColumnInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object r = null;
        GenDO b = null;
        try {
            String methodName = invocation.getMethod().getName();
            switch (methodName) {
                case "deleteByExample":
                case "deleteByPrimaryKey":
                    LOGGER.error("不允许物理删除");
                    break;
                case "insert":
                case "insertSelective":
                    b = (GenDO) invocation.getArguments()[0];
                    b.setCreateBy("createBy");// FIXME
                    b.setCreateTime(new Date());
                    b.setActive(true);
                    b.setDelete(false);
                    break;
                case "updateByExampleSelective":
                case "updateByExample":
                case "updateByPrimaryKeySelective":
                case "updateByPrimaryKey":
                    b = (GenDO) invocation.getArguments()[0];
                    b.setUpdateBy("updateBy");// FIXME
                    b.setUpdateTime(new Date());
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            LOGGER.warn("设置默认字段错误");
            e.printStackTrace();
        } finally {
            r = invocation.proceed();
        }
        return r;
    }

}
