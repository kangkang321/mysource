package com.neusoft.hp.runtime.handler.global;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import com.alibaba.dubbo.common.bytecode.NoSuchMethodException;
import com.neusoft.core.dao.GenDO;
import com.neusoft.core.dao.GenExample;
import com.neusoft.core.dao.GenJoinExample;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.service.annotation.AppendWhere;
import com.neusoft.core.service.annotation.GenTable;
import com.neusoft.core.service.annotation.SubTable;
import com.neusoft.core.util.ReflectWrapper;
import com.neusoft.hp.runtime.client.constant.SysConstants;
import com.neusoft.hp.runtime.handler.EntityOperatorHandlerAdapter;
import com.neusoft.hp.runtime.handler.HandlerContext;

/**
 * FIXME 先做一个主从结构，后续考虑有多级的情况 类JoinTableHandler.java的实现描述：TODO 类实现描述
 * 
 * @author Administrator 2017年4月20日 下午5:22:51
 */
public class JoinTableHandler extends EntityOperatorHandlerAdapter {

    @SuppressWarnings("deprecation")
    public JoinTableHandler(){
        this.setSorted(1);
        this.setUsed(true);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean afterInsert(GenDO po, HandlerContext context) {
        Class clazz = po.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Stream.of(fields).filter(f -> null != f.getAnnotation(SubTable.class)).forEach(f -> {
            Class sub = f.getAnnotation(SubTable.class).subClass();
            List items = (List) ReflectWrapper.getValue(po, f.getName());
            if (!CollectionUtils.isEmpty(items)) {
                GenTable table = (GenTable) sub.getAnnotation(GenTable.class);
                Method method = ReflectionUtils.findMethod(table.mapperClass(), SysConstants.INSERT_METHOD_NAME, null);
                Object bean = context.getApplicationContext().getBean(table.mapperClass());
                items.stream().forEach(i -> {
                    try {
                        ReflectWrapper.setValue(i, underlineToCamel(table.refKey(), false), po.getGid());
                        ReflectWrapper.invoke(method, bean, i);
                    } catch (NoSuchMethodException | InvocationTargetException e) {
                        throw new BizException(e);
                    }
                });
            }
        });
        return super.afterInsert(po, context);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean afterSave(GenDO po, HandlerContext context) {
        Class clazz = po.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Stream.of(fields).filter(f -> null != f.getAnnotation(SubTable.class)).forEach(f -> {
            Class sub = f.getAnnotation(SubTable.class).subClass();
            List<GenDO> items = (List) ReflectWrapper.getValue(po, f.getName());
            GenTable table = (GenTable) sub.getAnnotation(GenTable.class);
            List<String> gids = new ArrayList<>();
            Object bean = context.getApplicationContext().getBean(table.mapperClass());
            if (!CollectionUtils.isEmpty(items)) {
                Method method = ReflectionUtils.findMethod(table.mapperClass(), SysConstants.UPDATE_SUB_METHOD_NAME,
                                                           null);

                items.stream().forEach(i -> {
                    try {
                        if (StringUtils.isNotBlank(i.getGid())) {
                            GenExample example = getQueryByForeignKey(sub, table.refKey(), po.getGid(), i.getGid());
                            ReflectWrapper.invoke(method, bean, i, example);
                        } else {
                            // 新增的插入
                            Method mInsert = ReflectionUtils.findMethod(table.mapperClass(),
                                                                        SysConstants.INSERT_METHOD_NAME, null);
                            ReflectWrapper.setValue(i, underlineToCamel(table.refKey(), false), po.getGid());
                            ReflectWrapper.invoke(mInsert, bean, i);

                        }
                        gids.add(i.getGid());
                    } catch (NoSuchMethodException | InvocationTargetException e) {
                        throw new BizException(e);
                    }
                });
            }
            // 删除从表
            Method mDelete = ReflectionUtils.findMethod(table.mapperClass(), SysConstants.DELETE_SUB_METHOD_NAME, null);
            GenExample example = getQueryByForeignKey(sub, table.refKey(), po.getGid(), gids);
            try {
                ReflectWrapper.invoke(mDelete, bean, example);
            } catch (NoSuchMethodException | InvocationTargetException e) {
                throw new BizException(e);
            }
        });
        return super.afterInsert(po, context);
    }

    /*
     * @SuppressWarnings({ "unchecked", "rawtypes" })
     * @Override public boolean afterDelete(String id, HandlerContext context) { Class clazz = context.getDomainClass();
     * Field[] fields = clazz.getDeclaredFields(); Stream.of(fields).filter(f -> null !=
     * f.getAnnotation(SubTable.class)).forEach(f -> { Class sub = f.getAnnotation(SubTable.class).subClass(); GenTable
     * table = (GenTable) sub.getAnnotation(GenTable.class); String refKey = table.refKey();// 外键 Method method =
     * ReflectionUtils.findMethod(table.mapperClass(), SysConstants.DELETE_SUB_METHOD_NAME, null); Object bean =
     * context.getApplicationContext().getBean(table.mapperClass()); try { GenExample example =
     * getQueryByForeignKey(sub, underlineToCamel(refKey, true), id, ""); ReflectWrapper.invoke(method, bean, example);
     * } catch (NoSuchMethodException | InvocationTargetException e) { throw new BizException(e); } }); return
     * super.afterDelete(id, context); }
     */
    /**
     * 主表伪删除后,在afterDelete中,关联的子表中对应记录也进行伪删除
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean afterDelete(String id, HandlerContext context) {
        Class clazz = context.getDomainClass();
        Field[] fields = clazz.getDeclaredFields();
        Stream.of(fields).filter(f -> null != f.getAnnotation(SubTable.class)).forEach(f -> {
            Class sub = f.getAnnotation(SubTable.class).subClass();
            GenTable table = (GenTable) sub.getAnnotation(GenTable.class);
            String refKey = table.refKey();// 外键
            Method method = ReflectionUtils.findMethod(table.mapperClass(), SysConstants.UPDATE_SUB_METHOD_NAME, null);
            Object bean = context.getApplicationContext().getBean(table.mapperClass());
            try {
                GenExample example = getQueryByForeignKey(sub, refKey, id, "");
                GenDO DO = (GenDO) sub.newInstance();
                DO.setUpdateTime(new Date());
                DO.setDelete(true);
                DO.setActive(false);
                ReflectWrapper.invoke(method, bean, DO, example);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException
                    | IllegalAccessException e) {
                throw new BizException(e);
            }
        });
        return super.afterDelete(id, context);
    }

    @Override
    public boolean beforeSelect(GenExample example, HandlerContext context) {
        String tableName = context.getDomainClass().getAnnotation(GenTable.class).tableName();
        example.setSelectItems(context.getStyle().selectItem(tableName));
        return super.beforeSelect(example, context);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean afterSelect(GenDO po, HandlerContext context) {
        Class clazz = context.getDomainClass();
        Field[] fields = clazz.getDeclaredFields();
        Stream.of(fields).filter(f -> null != f.getAnnotation(SubTable.class)).forEach(f -> {
            Class sub = f.getAnnotation(SubTable.class).subClass();
            GenTable table = (GenTable) sub.getAnnotation(GenTable.class);
            String refKey = table.refKey();// 外键
            Method method = ReflectionUtils.findMethod(table.mapperClass(), SysConstants.QUERY_METHOD_NAME, null);
            Object bean = context.getApplicationContext().getBean(table.mapperClass());
            List<GenDO> pos = null;
            try {
                GenExample example = getQueryByForeignKey(sub, refKey, po.getGid(), "");
                example.setSelectItems(context.getStyle().selectItem(table.tableName()));
                pos = (List<GenDO>) ReflectWrapper.invoke(method, bean, example);
            } catch (NoSuchMethodException | InvocationTargetException e) {
                throw new BizException(e);
            }
            ReflectWrapper.setValue(po, f.getName(), pos);
        });
        return super.afterSelect(po, context);
    }

    @Override
    public boolean beforeQuery(GenExample example, HandlerContext context) {
        if (GenJoinExample.class.isAssignableFrom(example.getClass())) {
            // 多表联合查询
            Field[] fields = example.getClass().getDeclaredFields();
            for (Field field : fields) {
                AppendWhere where = field.getAnnotation(AppendWhere.class);
                if (null != where) {
                    ReflectWrapper.setValue(example, field.getName(),
                                            context.getQuery() == null ? null : context.getQuery().getWhere(where.tableName()));
                }
            }
        } else {
            String tableName = context.getDomainClass().getAnnotation(GenTable.class).tableName();
            // example.setSelectItems(context.getStyle().selectItems(tableName, false));
            example.setAppendWhere(context.getQuery() == null ? null : context.getQuery().getWhere(tableName));
        }
        return super.beforeQuery(example, context);
    }

}
