package com.neusoft.hp.runtime.dyn.visitor.element;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.hp.runtime.dyn.AttributeBean;
import com.neusoft.hp.runtime.dyn.annotation.ColumnInfo;
import com.neusoft.hp.runtime.dyn.annotation.DynWhere;
import com.neusoft.hp.runtime.dyn.annotation.OriginalValue;
import com.neusoft.hp.runtime.dyn.annotation.RefValue;
import com.neusoft.hp.runtime.dyn.bean.DynBean;
import com.neusoft.hp.runtime.dyn.bean.DynQueryBean;
import com.neusoft.hp.runtime.dyn.bean.DynStyleBean;
import com.neusoft.hp.runtime.dyn.visitor.DynFieldVisitor;
import com.neusoft.hp.runtime.util.JavassistUtil;
import com.neusoft.hp.runtime.util.SQLUtil;

import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.AnnotationMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

/**
 * 类RefDynField.java的实现描述：引用字段
 * 
 * @author Administrator 2017年4月21日 下午2:51:48
 */
public class RefDynField extends DynField {

    public RefDynField(){
        super();
    }

    public RefDynField(List<AttributeBean> selectedAttributes, DynBean bean, Field field){
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
            a.addMemberValue("path",
                             new StringMemberValue(getCurrAttribute() == null
                                                   || getCurrAttribute().getPath() == null ? "" : getCurrAttribute().getPath(),
                                                   JavassistUtil.getConstPool(a)));
        });
        if (getCurrAttribute() != null) {

            // if (CollectionUtils.isEmpty(getCurrAttribute().getValues())) {
            // bean.getQueryStrAnnotations().add(value);
            // } else {
            // bean.getEnums().put(value, getCurrAttribute().getValues());
            // }
        }
    }

    @Override
    public void accept(DynFieldVisitor visitor) {
        visitor.visitor(this);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void makeForEntity() {
        DynStyleBean bean = (DynStyleBean) getBean();
        RefValue value = (RefValue) JavassistUtil.createAnnotation(RefValue.class, a -> {
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
            // FIXME 后期转换成sql在序列化的时候搞
            SQLUtil sql = new SQLUtil(getCurrAttribute().getPath(), false);
            // a.addMemberValue("sql",
            // new StringMemberValue(sql.getSQL() + " AND " + getTableName() + "." + getColumnName() + "="
            // + SysConstants.REF, JavassistUtil.getConstPool(a)));
            a.addMemberValue("path",
                             new StringMemberValue(getCurrAttribute() == null
                                                   || getCurrAttribute().getPath() == null ? "" : getCurrAttribute().getPath(),
                                                   JavassistUtil.getConstPool(a)));

        });
        OriginalValue value1 = (OriginalValue) JavassistUtil.createAnnotation(OriginalValue.class, a -> {
            String displayName = "";
            if (null != getCurrAttribute()) {
                displayName = getCurrAttribute().getDisplayName();
            }
            if (StringUtils.isBlank(displayName)) {
                displayName = getField().getName();
            }
            displayName = StringUtils.substringBefore(displayName, ",");
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
        anns.add(value1);

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void makeForList() {
        DynStyleBean bean = (DynStyleBean) getBean();
        RefValue value = (RefValue) JavassistUtil.createAnnotation(RefValue.class, a -> {
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
            // FIXME 后期转换成sql在序列化的时候搞
            SQLUtil sql = new SQLUtil(getCurrAttribute().getPath(), false);
            // a.addMemberValue("sql",
            // new StringMemberValue(sql.getSQL() + " AND " + getTableName() + "." + getColumnName() + "="
            // + SysConstants.REF, JavassistUtil.getConstPool(a)));
            a.addMemberValue("path",
                             new StringMemberValue(getCurrAttribute() == null
                                                   || getCurrAttribute().getPath() == null ? "" : getCurrAttribute().getPath(),
                                                   JavassistUtil.getConstPool(a)));

        });
        OriginalValue value1 = (OriginalValue) JavassistUtil.createAnnotation(OriginalValue.class, a -> {
            String displayName = "";
            if (null != getCurrAttribute()) {
                displayName = getCurrAttribute().getDisplayName();
            }
            if (StringUtils.isBlank(displayName)) {
                displayName = getField().getName();
            }
            displayName = StringUtils.substringBefore(displayName, ",");
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
        anns.add(value1);
    }

}
