package com.neusoft.hp.runtime.dyn.visitor.element;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.core.service.annotation.GenColumn;
import com.neusoft.hp.runtime.dyn.AttributeBean;
import com.neusoft.hp.runtime.dyn.annotation.ColumnInfo;
import com.neusoft.hp.runtime.dyn.annotation.DynWhere;
import com.neusoft.hp.runtime.dyn.annotation.EnumValue;
import com.neusoft.hp.runtime.dyn.annotation.OriginalValue;
import com.neusoft.hp.runtime.dyn.bean.DynBean;
import com.neusoft.hp.runtime.dyn.bean.DynQueryBean;
import com.neusoft.hp.runtime.dyn.bean.DynStyleBean;
import com.neusoft.hp.runtime.dyn.visitor.DynFieldVisitor;
import com.neusoft.hp.runtime.util.JavassistUtil;

import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.AnnotationMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

/**
 * 类EnumDynField.java的实现描述：枚举字段
 * 
 * @author Administrator 2017年4月21日 下午2:50:04
 */
public class EnumDynField extends DynField {

    public EnumDynField(){
        super();
    }

    public EnumDynField(List<AttributeBean> selectedAttributes, DynBean bean, Field field){
        super(selectedAttributes, bean, field);
    }

    @Override
    public void makeForQuery() {
        DynQueryBean bean = (DynQueryBean) getBean();
        DynWhere value = (DynWhere) JavassistUtil.createAnnotation(DynWhere.class, a -> {
            String displayName = "";
            if (null != getCurrAttribute()) {
                displayName = getCurrAttribute().getDisplayName();
            }
            if (StringUtils.isBlank(displayName)) {
                displayName = getField().getName();
            }

            Annotation columnInfo = new Annotation(ColumnInfo.class.getName(), JavassistUtil.getConstPool(a));
            columnInfo.addMemberValue("tableName",
                                      new StringMemberValue(getTableName(), JavassistUtil.getConstPool(a)));
            columnInfo.addMemberValue("columnName",
                                      new StringMemberValue(getColumnName(), JavassistUtil.getConstPool(a)));
            a.addMemberValue("columnInfo", new AnnotationMemberValue(columnInfo, JavassistUtil.getConstPool(a)));
            // 被引用对象的目标属性是Enum类型 path:p_car$tc_order_entity_gid,tc_order_entity$order_statues
            if (getCurrAttribute().getPath().split(",").length > 1) {
                a.addMemberValue("path",
                                 new StringMemberValue(getCurrAttribute() == null
                                                       || getCurrAttribute().getPath() == null ? "" : getCurrAttribute().getPath(),
                                                       JavassistUtil.getConstPool(a)));
            }
            ;
            // a.addMemberValue("path",
            // new StringMemberValue(getCurrAttribute() == null || getCurrAttribute().getPath() == null ? "" :
            // getCurrAttribute().getPath(),
            // JavassistUtil.getConstPool(a)));
        });
        // if (getCurrAttribute() != null) {
        //
        // if (CollectionUtils.isEmpty(getCurrAttribute().getValues())) {
        // bean.getQueryStrAnnotations().add(value);
        // } else {
        // bean.getEnums().put(value, getCurrAttribute().getValues());
        // }
        // }

    }

    @Override
    public void accept(DynFieldVisitor visitor) {
        visitor.visitor(this);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void makeForEntity() {
        DynStyleBean bean = (DynStyleBean) getBean();
        // EnumValue value = (EnumValue) JavassistUtil.createAnnotation(EnumValue.class, a -> {
        // String displayName = "";
        // if (null != getCurrAttribute()) {
        // displayName = getCurrAttribute().getDisplayName();
        // }
        // if (StringUtils.isBlank(displayName)) {
        // displayName = getField().getName();
        // }
        // a.addMemberValue("displayName", new StringMemberValue(displayName, JavassistUtil.getConstPool(a)));
        //
        // Annotation columnInfo = new Annotation(ColumnInfo.class.getName(), JavassistUtil.getConstPool(a));
        // columnInfo.addMemberValue("tableName",
        // new StringMemberValue(getTableName(), JavassistUtil.getConstPool(a)));
        // columnInfo.addMemberValue("columnName",
        // new StringMemberValue(getColumnName(), JavassistUtil.getConstPool(a)));
        // a.addMemberValue("columnInfo", new AnnotationMemberValue(columnInfo, JavassistUtil.getConstPool(a)));
        // GenColumn column = getField().getAnnotation(GenColumn.class);
        // a.addMemberValue("key", new StringMemberValue(column.enumId(), JavassistUtil.getConstPool(a)));
        //
        // });
        OriginalValue value = (OriginalValue) JavassistUtil.createAnnotation(OriginalValue.class, a -> {
            String displayName = "";
            if (null != getCurrAttribute()) {
                displayName = getCurrAttribute().getDisplayName();
            }
            if (StringUtils.isBlank(displayName)) {
                displayName = getField().getName();
            }
            a.addMemberValue("displayName", new StringMemberValue(displayName, JavassistUtil.getConstPool(a)));

            Annotation columnInfo = new Annotation(ColumnInfo.class.getName(), JavassistUtil.getConstPool(a));
            columnInfo.addMemberValue("tableName",
                                      new StringMemberValue(getTableName(), JavassistUtil.getConstPool(a)));
            columnInfo.addMemberValue("columnName",
                                      new StringMemberValue(getColumnName(), JavassistUtil.getConstPool(a)));
            a.addMemberValue("columnInfo", new AnnotationMemberValue(columnInfo, JavassistUtil.getConstPool(a)));
        });
        List anns = bean.getAnnotations().get(getField());
        if (null == anns) {
            anns = new ArrayList<>();
            bean.getAnnotations().put(getField(), anns);
        }
        anns.add(value);

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void makeForList() {
        DynStyleBean bean = (DynStyleBean) getBean();
        EnumValue value = (EnumValue) JavassistUtil.createAnnotation(EnumValue.class, a -> {
            String displayName = "";
            if (null != getCurrAttribute()) {
                displayName = getCurrAttribute().getDisplayName();
            }
            if (StringUtils.isBlank(displayName)) {
                displayName = getField().getName();
            }
            a.addMemberValue("displayName", new StringMemberValue(displayName, JavassistUtil.getConstPool(a)));

            Annotation columnInfo = new Annotation(ColumnInfo.class.getName(), JavassistUtil.getConstPool(a));
            columnInfo.addMemberValue("tableName",
                                      new StringMemberValue(getTableName(), JavassistUtil.getConstPool(a)));
            columnInfo.addMemberValue("columnName",
                                      new StringMemberValue(getColumnName(), JavassistUtil.getConstPool(a)));
            a.addMemberValue("columnInfo", new AnnotationMemberValue(columnInfo, JavassistUtil.getConstPool(a)));
            GenColumn column = getField().getAnnotation(GenColumn.class);
            a.addMemberValue("key", new StringMemberValue(column.enumId(), JavassistUtil.getConstPool(a)));

        });
        List anns = bean.getAnnotations().get(getField());
        if (null == anns) {
            anns = new ArrayList<>();
            bean.getAnnotations().put(getField(), anns);
        }
        anns.add(value);

    }

}
