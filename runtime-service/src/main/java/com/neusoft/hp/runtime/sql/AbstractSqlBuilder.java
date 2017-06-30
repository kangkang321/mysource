package com.neusoft.hp.runtime.sql;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.core.exception.BizException;
import com.neusoft.core.util.M2Util;

public abstract class AbstractSqlBuilder {

    public abstract String getColumn(String path);

    public abstract String getValue(String path, TypeEnum type, String value);

    protected String forIn(String ins) {
        if (StringUtils.isBlank(ins)) {
            return "()";
        }
        String[] arrs = ins.split(",");
        return "("
               + Stream.of(arrs).collect(StringBuffer::new, (m, n) -> m.append(",").append("'").append(n).append("'"),
                                         StringBuffer::append).substring(1)
               + ")";
    }

    // protected String forIn(List<String> ins) {
    // if (CollectionUtils.isEmpty(ins)) {
    // return "()";
    // }
    // return "(" + ins.stream().collect(StringBuffer::new, (m, n) -> m.append(",").append("'").append(n).append("'"),
    // StringBuffer::append).substring(1)
    // + ")";
    // }

    protected Field getField(String filedStr) {
        Field column;
        try {
            column = M2Util.getClass(StringUtils.substringBefore(filedStr,
                                                                 "$")).get().getDeclaredField(StringUtils.substringAfter(filedStr,
                                                                                                                         "$"));
        } catch (NoSuchFieldException | SecurityException e) {
            throw new BizException(e);
        }
        return column;
    }

    public String toSql(String path, TypeEnum type, String value, String... args) {
        StringBuffer sb = new StringBuffer();
        Class clazz = null;
        if (args.length == 2) {
            sb.append(args[0]).append(".").append(args[1]).append(" ");
            if (StringUtils.equalsIgnoreCase(args[1], "CREATE_TIME")
                || StringUtils.equalsIgnoreCase(args[1], "UPDATE_TIME")) {
                clazz = Date.class;
            } else if (StringUtils.equalsIgnoreCase(args[1], "GID")
                       || StringUtils.equalsIgnoreCase(args[1], "CREATE_BY")
                       || StringUtils.equalsIgnoreCase(args[1], "UPDATE_BY")) {
                clazz = String.class;
            }
        } else {
            Field column = getField(StringUtils.substringBefore(path, ","));
            clazz = column.getType();
            sb.append(getColumn(path)).append(" ");
        }
        if (StringUtils.contains(path, ",")) {
            sb.append(" IN ").append(" ").append(forIn(getValue(path, type, value)));
            return sb.toString();
        }
        switch (type) {
            case NULL:
            case NONULL:
                sb.append(type.getExpression());
                break;
            case EQ:
            case NE:
            case GT:
            case LT:
            case GE:
            case LE:
                sb.append(type.getExpression());
                if (Date.class.equals(clazz) || Timestamp.class.equals(clazz)) {
                    sb.append(" ").append("to_date('").append(getValue(path, type,
                                                                       value)).append("','yyyy-mm-dd hh24:mi:ss')");
                } else if (String.class.equals(clazz)) {
                    sb.append(" '").append(getValue(path, type, value)).append("'");
                } else {
                    sb.append(" ").append(getValue(path, type, value));
                }
                break;
            case LIKE:
                sb.append(type.getExpression());
                if (String.class.equals(clazz)) {
                    sb.append(" '%").append(getValue(path, type, value)).append("%'");
                } else {
                    throw new BizException("只支持字符串模糊查询");
                }
                break;
            case IN:
            case NOIN:
                sb.append(type.getExpression());
                sb.append(type.getExpression()).append(" ").append(forIn(getValue(path, type, value)));
                break;
            default:
                break;
        }
        return sb.toString();
    }

}
