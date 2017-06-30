package com.neusoft.hp.runtime.dyn.visitor.element;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.neusoft.core.dao.GenDO;
import com.neusoft.core.service.annotation.SubTable;
import com.neusoft.hp.runtime.dyn.AttributeBean;
import com.neusoft.hp.runtime.dyn.bean.DynBean;
import com.neusoft.hp.runtime.dyn.impl.DynBeanFactoryImpl;
import com.neusoft.hp.runtime.dyn.visitor.DynFieldVisitor;
import com.neusoft.hp.runtime.dyn.visitor.ListDynFieldVisitor;
import com.neusoft.hp.runtime.dyn.visitor.QueryDynFieldVisitor;

/**
 * 类SubTableDynField.java的实现描述：子表字段
 * 
 * @author Administrator 2017年4月21日 下午2:53:32
 */
public class SubTableDynField extends DynField {

    public SubTableDynField(){
        super();
    }

    public SubTableDynField(List<AttributeBean> selectedAttributes, DynBean bean, Field field){
        super(selectedAttributes, bean, field);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void makeForQuery() {
        DynFieldVisitor visitor = new QueryDynFieldVisitor();
        Type type = getField().getGenericType();
        Class<? extends GenDO> domain = null;
        if (type instanceof ParameterizedType) {
            ParameterizedType param = (ParameterizedType) type;
            domain = (Class) param.getActualTypeArguments()[0];
        } else {
            domain = (Class<? extends GenDO>) getField().getType();
        }
        DynBeanFactoryImpl.create(getSelectedAttributes(), domain, visitor, getBean());
    }

    @Override
    public void accept(DynFieldVisitor visitor) {
        visitor.visitor(this);
    }

    @Override
    public void makeForEntity() {
        DynFieldVisitor visitor = new ListDynFieldVisitor();
        SubTable sub = getField().getAnnotation(SubTable.class);
        DynBeanFactoryImpl.create(getSelectedAttributes(), sub.subClass(), visitor, getBean());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void makeForList() {
        DynFieldVisitor visitor = new ListDynFieldVisitor();
        Type type = getField().getGenericType();
        Class<? extends GenDO> domain = null;
        if (type instanceof ParameterizedType) {
            ParameterizedType param = (ParameterizedType) type;
            domain = (Class) param.getActualTypeArguments()[0];
        } else {
            domain = (Class<? extends GenDO>) getField().getType();
        }
        DynBeanFactoryImpl.create(getSelectedAttributes(), domain, visitor, getBean());

    }

}
