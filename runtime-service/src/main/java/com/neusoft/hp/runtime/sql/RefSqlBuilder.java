package com.neusoft.hp.runtime.sql;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.neusoft.core.dao.GenDO;
import com.neusoft.core.service.annotation.GenColumn;
import com.neusoft.core.service.annotation.GenTable;
import com.neusoft.hp.runtime.client.QuerySqlByOwner;
import com.neusoft.hp.runtime.handler.HandlerContext;

public class RefSqlBuilder extends AbstractSqlBuilder {

    private HandlerContext context;

    public RefSqlBuilder(HandlerContext context){
        this.context = context;
    }

    @Override
    public String getColumn(String path) {
        Field field = getField(StringUtils.substringBefore(path, ","));
        GenTable table = field.getDeclaringClass().getAnnotation(GenTable.class);
        GenColumn column = field.getAnnotation(GenColumn.class);
        return table.tableName() + "." + column.columnName();
    }

    @Override
    public String getValue(String path, TypeEnum type, String value) {
        String[] fields = StringUtils.split(path, ",");
        QuerySqlByOwner query = context.getApplicationContext().getBean(QuerySqlByOwner.class);
        for (int i = fields.length - 1; i > 0; i--) {
            Field f = getField(fields[i]);
            List<String> rs = null;
            if (i == fields.length - 1) {
                rs = query.querySqlList(getSql(f, fields[i], type, value),
                                        f.getDeclaringClass().getAnnotation(GenTable.class).owner());
            } else {
                rs = query.querySqlList(getSql(f, value), f.getDeclaringClass().getAnnotation(GenTable.class).owner());
            }
            if (CollectionUtils.isEmpty(rs)) {
                break;
            }
            value = rs.stream().collect(StringBuffer::new, (m, n) -> m.append(",").append(n),
                                        StringBuffer::append).substring(1);
        }
        return value;
    }

    private String getSql(Field f, String path, TypeEnum type, String value) {
        GenTable table = f.getDeclaringClass().getAnnotation(GenTable.class);
        if (table.parentClass().equals(GenDO.class)) {
            return "SELECT GID FROM " + table.tableName() + " WHERE " + toSql(path, type, value);
        } else {
            return "SELECT " + table.refKey() + " FROM " + table.tableName() + " WHERE " + toSql(path, type, value);
        }
    }

    private String getSql(Field f, String value) {
        GenTable table = f.getDeclaringClass().getAnnotation(GenTable.class);
        GenColumn column = f.getAnnotation(GenColumn.class);
        if (table.parentClass().equals(GenDO.class)) {
            return "SELECT GID FROM " + table.tableName() + " WHERE " + column.columnName() + " IN " + forIn(value);
        } else {
            return "SELECT " + table.refKey() + " FROM " + table.tableName() + " WHERE " + column.columnName() + " IN "
                   + forIn(value);
        }
    }

}
