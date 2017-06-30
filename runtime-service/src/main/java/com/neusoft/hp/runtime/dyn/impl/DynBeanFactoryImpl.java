package com.neusoft.hp.runtime.dyn.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.core.dao.GenDO;
import com.neusoft.core.dao.GenJoinDO;
import com.neusoft.core.service.annotation.GenColumn;
import com.neusoft.core.service.annotation.SubTable;
import com.neusoft.core.util.M2Util;
import com.neusoft.hp.runtime.dyn.AttributeBean;
import com.neusoft.hp.runtime.dyn.DynBeanFactory;
import com.neusoft.hp.runtime.dyn.bean.DynBean;
import com.neusoft.hp.runtime.dyn.bean.DynQueryBean;
import com.neusoft.hp.runtime.dyn.bean.DynStyleBean;
import com.neusoft.hp.runtime.dyn.visitor.DynFieldVisitor;
import com.neusoft.hp.runtime.dyn.visitor.EntityDynFieldVisitor;
import com.neusoft.hp.runtime.dyn.visitor.ListDynFieldVisitor;
import com.neusoft.hp.runtime.dyn.visitor.QueryDynFieldVisitor;
import com.neusoft.hp.runtime.dyn.visitor.element.EnumDynField;
import com.neusoft.hp.runtime.dyn.visitor.element.OriginalDynField;
import com.neusoft.hp.runtime.dyn.visitor.element.RefDynField;
import com.neusoft.hp.runtime.dyn.visitor.element.SubTableDynField;

public class DynBeanFactoryImpl implements DynBeanFactory {

    @Override
    public DynStyleBean create(List<AttributeBean> selectedAttributes, String type, Class<? extends GenDO> domain) {
        DynStyleBean bean = new DynStyleBean();
        DynFieldVisitor visitor = getDynFieldVisitor(type);
        create(selectedAttributes, domain, visitor, bean);
        return bean;
    }

    @Override
    public DynQueryBean

            create(List<AttributeBean> selectedAttributes, Class<? extends GenDO> domain) {
        DynQueryBean bean = new DynQueryBean();
        DynFieldVisitor visitor = new QueryDynFieldVisitor();
        create(selectedAttributes, domain, visitor, bean);
        return bean;
    }

    private DynFieldVisitor getDynFieldVisitor(String type) {
        DynFieldVisitor visitor = null;
        if (StringUtils.equals(type, "1")) {
            // 卡片
            visitor = new EntityDynFieldVisitor();
        } else if (StringUtils.equals(type, "2")) {
            // 列表
            visitor = new ListDynFieldVisitor();
        }
        return visitor;
    }

    public static void create(List<AttributeBean> selectedAttributes, Class<? extends GenDO> domain,
                              DynFieldVisitor visitor, DynBean bean) {
        Field[] fields = M2Util.getClass(domain).get().getDeclaredFields();

        for (Field f : fields) {
            GenColumn column = f.getAnnotation(GenColumn.class);
            SubTable subTable = f.getAnnotation(SubTable.class);
            if (null == column && subTable == null) {
                if (StringUtils.equals(f.getName(), "serialVersionUID")) {

                } else if (GenJoinDO.class.isAssignableFrom(domain)) {
                    // FIXME 列表查询时传入的domain也应该是GenDO
                    SubTableDynField field = new SubTableDynField(selectedAttributes, bean, f);
                    field.accept(visitor);
                } else {
                    OriginalDynField field = new OriginalDynField(selectedAttributes, bean, f);
                    field.accept(visitor);
                }
            } else if (column != null) {
                Consumer<AttributeBean> consumer = new Consumer<AttributeBean>() {

                    @Override
                    public void accept(AttributeBean t) {
                        String dataType = t.getField().getAnnotation(GenColumn.class).type();
                        if (StringUtils.equals(dataType, "1")) {
                            EnumDynField field = new EnumDynField(selectedAttributes, bean, f);
                            field.setCurrAttribute(t);
                            field.accept(visitor);
                        } else if (StringUtils.equals(dataType, "2")) {
                            RefDynField field = new RefDynField(selectedAttributes, bean, f);
                            field.setCurrAttribute(t);
                            field.accept(visitor);
                        } else {
                            OriginalDynField field = new OriginalDynField(selectedAttributes, bean, f);
                            field.setCurrAttribute(t);
                            field.accept(visitor);
                        }
                    }
                };
                // selectedAttributes中是否存在此attribute
                selectedAttributes.stream().filter(m -> m.getField() != null).filter(m -> m.getField().equals(f)).forEach(consumer);
            } else if (subTable != null) {
                SubTableDynField field = new SubTableDynField(selectedAttributes, bean, f);
                field.accept(visitor);
            }
        }
    }

}
