package com.neusoft.hp.runtime.sql;

import java.lang.reflect.Field;

import com.neusoft.core.service.annotation.GenColumn;
import com.neusoft.core.service.annotation.GenTable;

public class OriginalSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String getColumn(String path) {
        Field field = getField(path);
        GenTable table = field.getDeclaringClass().getAnnotation(GenTable.class);
        GenColumn column = field.getAnnotation(GenColumn.class);
        return table.tableName() + "." + column.columnName();
    }

    @Override
    public String getValue(String path, TypeEnum type, String value) {
        return value;
    }

}
