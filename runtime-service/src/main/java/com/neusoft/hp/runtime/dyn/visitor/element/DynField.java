package com.neusoft.hp.runtime.dyn.visitor.element;

import java.lang.reflect.Field;
import java.util.List;

import com.neusoft.core.service.annotation.GenColumn;
import com.neusoft.core.service.annotation.GenTable;
import com.neusoft.hp.runtime.dyn.AttributeBean;
import com.neusoft.hp.runtime.dyn.bean.DynBean;
import com.neusoft.hp.runtime.dyn.visitor.DynFieldVisitor;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类DynField.java的实现描述：字段
 * 
 * @author Administrator 2017年4月21日 下午2:49:40
 */
@Data
@NoArgsConstructor
public abstract class DynField {

    private List<AttributeBean> selectedAttributes;

    private DynBean             bean;

    private Field               field;

    private AttributeBean       currAttribute;

    /**
     * 查询模板
     */
    public abstract void makeForQuery();

    /**
     * 表单模板
     */
    public abstract void makeForEntity();

    /**
     * 列表模板
     */
    public abstract void makeForList();

    public abstract void accept(DynFieldVisitor visitor);

    public String getTableName() {
        GenTable table = getField().getDeclaringClass().getAnnotation(GenTable.class);
        return table.tableName();
    }

    public String getColumnName() {
        GenColumn column = getField().getAnnotation(GenColumn.class);
        if (null != column) {
            return column.columnName();
        } else {
            return camelToUnderline(getField().getName());
        }
    }

    protected String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(c);
            } else {
                sb.append(Character.toUpperCase(c));
            }
        }
        return sb.toString();
    }

    public DynField(List<AttributeBean> selectedAttributes, DynBean bean, Field field){
        super();
        this.selectedAttributes = selectedAttributes;
        this.bean = bean;
        this.field = field;
    }

}
