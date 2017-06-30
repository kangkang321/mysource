package com.neusoft.hp.runtime.handler.global;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import com.alibaba.dubbo.common.bytecode.NoSuchMethodException;
import com.alibaba.fastjson.JSON;
import com.neusoft.core.dao.GenDO;
import com.neusoft.core.dao.GenDao;
import com.neusoft.core.dao.GenExample;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.service.annotation.GenTable;
import com.neusoft.core.util.ReflectWrapper;
import com.neusoft.hp.runtime.client.constant.SysConstants;
import com.neusoft.hp.runtime.dyn.DynBeanFactory;
import com.neusoft.hp.runtime.dyn.bean.DynStyleBean;
import com.neusoft.hp.runtime.handler.EntityOperatorHandlerAdapter;
import com.neusoft.hp.runtime.handler.HandlerContext;
import com.neusoft.hp.runtime.json.MyJSON;
import com.neusoft.hp.runtime.json.MySerializeConfigWrapper;

/**
 * 类DynTableHandler.java的实现描述：动态增加表时，业务方需要的实现，目前只支持单表操作
 * 
 * @author Administrator 2017年6月14日 下午5:27:38
 */
public abstract class DynTableHandler extends EntityOperatorHandlerAdapter {

    protected abstract Class<? extends GenDO> getClazz();

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public boolean afterInsert(GenDO po, HandlerContext context) {
        Map maps = po.getExts();
        if (!CollectionUtils.isEmpty(maps) && null != maps.get(getClazz().getName())) {
            GenDO domain = (GenDO) JSON.toJavaObject((JSON) maps.get(getClazz().getName()), getClazz());
            GenTable table = (GenTable) getClazz().getAnnotation(GenTable.class);
            ReflectWrapper.setValue(domain, underlineToCamel(table.refKey(), false), po.getGid());
            Method method = ReflectionUtils.findMethod(table.mapperClass(), SysConstants.INSERT_METHOD_NAME, null);
            GenDao dao = context.getApplicationContext().getBean(table.mapperClass());
            try {
                ReflectWrapper.invoke(method, dao, domain);
            } catch (NoSuchMethodException | InvocationTargetException e) {
                throw new BizException(e);
            }
        }
        return super.afterInsert(po, context);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public boolean afterSave(GenDO po, HandlerContext context) {
        Map maps = po.getExts();
        if (!CollectionUtils.isEmpty(maps) && null != maps.get(getClazz().getName())) {
            GenDO domain = (GenDO) JSON.toJavaObject((JSON) maps.get(getClazz().getName()), getClazz());
            GenTable table = (GenTable) getClazz().getAnnotation(GenTable.class);
            Method method = ReflectionUtils.findMethod(table.mapperClass(), SysConstants.UPDATE_SUB_METHOD_NAME, null);
            GenDao dao = context.getApplicationContext().getBean(table.mapperClass());
            try {
                GenExample example = getQueryByForeignKey(getClazz(), table.refKey(), po.getGid(), domain.getGid());
                ReflectWrapper.invoke(method, dao, domain, example);
            } catch (NoSuchMethodException | InvocationTargetException e) {
                throw new BizException(e);
            }
        }
        return super.afterSave(po, context);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean afterDelete(String id, HandlerContext context) {
        GenTable table = (GenTable) getClazz().getAnnotation(GenTable.class);
        Method method = ReflectionUtils.findMethod(table.mapperClass(), SysConstants.DELETE_SUB_METHOD_NAME, null);
        GenDao dao = context.getApplicationContext().getBean(table.mapperClass());
        try {
            GenExample example = getQueryByForeignKey(getClazz(), table.refKey(), id, "");
            ReflectWrapper.invoke(method, dao, example);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new BizException(e);
        }
        return super.afterDelete(id, context);
    }

    @Override
    public boolean beforeSelect(GenExample example, HandlerContext context) {
        // FIXME 只有等主表查出来之后再搞
        return super.beforeSelect(example, context);
    }

    @Override
    public boolean afterSelect(GenDO po, HandlerContext context) {
        handler(po, context);
        return super.afterSelect(po, context);
    }

    @Override
    public boolean beforeQuery(GenExample example, HandlerContext context) {
        // FIXME 只有等主表查出来之后再搞
        return super.beforeQuery(example, context);
    }

    @Override
    public boolean afterQuery(List<GenDO> po, HandlerContext context) {
        po.forEach(p -> handler(p, context));
        return super.afterQuery(po, context);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void handler(GenDO po, HandlerContext context) {
        DynStyleBean bean = context.getApplicationContext().getBean(DynBeanFactory.class).create(context.getLayoutAttributeBean(),
                                                                                                 SysConstants.FORM_CSS_TEMPLATE,
                                                                                                 getClazz());
        GenTable table = (GenTable) getClazz().getAnnotation(GenTable.class);
        GenExample example;
        try {
            example = table.exampleClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BizException(e);
        }
        example.setSelectItems(bean.selectItem(table.tableName()));
        example.setAppendWhere(table.refKey() + "='" + po.getGid() + "'");
        GenDao dao = context.getApplicationContext().getBean(table.mapperClass());
        Method method = ReflectionUtils.findMethod(table.mapperClass(), SysConstants.QUERY_METHOD_NAME, null);
        try {
            List<GenDO> domains = (List<GenDO>) ReflectWrapper.invoke(method, dao, example);
            if (!CollectionUtils.isEmpty(domains)) {
                GenDO domain = domains.get(0);
                DynStyleBean tmp = context.getStyle();
                context.setStyle(bean);
                MySerializeConfigWrapper config = new MySerializeConfigWrapper(context);
                Map maps = po.getExts();
                if (CollectionUtils.isEmpty(maps)) {
                    maps = new HashMap<>();
                    po.setExts(maps);
                }
                maps.put(getClazz().getName(), MyJSON.toJSON(domain, config));
                context.setStyle(tmp);
            }
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new BizException(e);
        }
    }
}
