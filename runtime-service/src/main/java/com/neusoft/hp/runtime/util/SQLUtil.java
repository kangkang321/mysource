package com.neusoft.hp.runtime.util;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.core.dao.GenDO;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.service.annotation.GenColumn;
import com.neusoft.core.service.annotation.GenTable;
import com.neusoft.core.service.annotation.SubTable;

/**
 * 类SQLUtil.java的实现描述：操作生成sql语句
 * 
 * @author Administrator 2017年5月8日 上午11:21:19
 */
@Deprecated
public class SQLUtil {

    private static final String    SQL = "SELECT ${COLUMNS} FROM ${TABLES} WHERE ${WHERE}";

    /**
     * like A$a,B$b,C$c用逗号分割对象
     */
    private String                 path;

    /**
     * 区分是查值(false)还是组合where语句(true)
     */
    private boolean                flag;

    private Class<? extends GenDO> subClass;                                               // 引用对象是主表时，其中的子表class

    public SQLUtil(String path, boolean flag){
        this.path = path;
        this.flag = flag;
    }

    public StringBuffer getSQL() {
        StringBuffer sb = new StringBuffer(SQL);
        Stream.of(StringUtils.split(path, ",")).map(s -> {
            try {
                return Class.forName(StringUtils.substringBefore(s,
                                                                 "$")).getDeclaredField(StringUtils.substringAfter(s,
                                                                                                                   "$"));
            } catch (ClassNotFoundException | NoSuchFieldException | SecurityException e) {
                throw new BizException(e);
            }
        }).forEach(field -> {
            if (!field.getDeclaringClass().getAnnotation(GenTable.class).isSingle() && sb.length() != SQL.length()) {
                // 引用对象是多表的时候
                // moreTable(sb, field);
                moreTableImproved(sb, field);
            } else {
                // 引用对象是单表、原始值
                // simpleTable(sb, field);
                simpleTableImproved(sb, field);
            }
        });
        return sb;
    }

    /**
     * @param sb
     * @param tag
     * @param rep
     * @param flag
     */
    private void replace(StringBuffer sb, TagEnum tag, String rep, boolean flag) {
        int start = sb.indexOf(tag.value);
        if (!flag) {
            sb.insert(start, rep);
        } else {
            sb.replace(start, start + tag.value.length(), rep);
        }
    }

    /**
     * 引用表是单表、原始值
     * 
     * @param sb
     * @param field
     */
    private void simpleTable(StringBuffer sb, Field field) {
        GenTable table = field.getDeclaringClass().getAnnotation(GenTable.class);
        GenColumn column = field.getAnnotation(GenColumn.class);
        if (StringUtils.isBlank(column.refObjectId())) {
            // 最后一个元素
            if (!flag) {
                // 查值
                replace(sb, TagEnum.COLUMN, table.tableName() + "." + column.columnName(), true);
            }
            replace(sb, TagEnum.TABLE, table.tableName(), true);
            replace(sb, TagEnum.WHERE, table.tableName() + ".GID", true);
            if (flag) {
                sb.append(" AND ").append(table.tableName() + "." + column.columnName());
            }
        } else {
            // 还要接着引用
            if (sb.length() == SQL.length()) {
                // 原始值，第一次
                replace(sb, TagEnum.WHERE, table.tableName() + "." + column.columnName() + "=", false);
            } else {
                replace(sb, TagEnum.WHERE,
                        table.tableName() + ".GID AND " + table.tableName() + "." + column.columnName() + "=", false);
            }
            if (flag && sb.indexOf(TagEnum.COLUMN.value) > 0) {
                // 组合where语句、未拼接显示值
                replace(sb, TagEnum.COLUMN, table.tableName() + "." + column.columnName(), true);
            }
            replace(sb, TagEnum.TABLE, table.tableName() + ",", false);

        }
    }

    /**
     * 如果是引用主表中的从表列时，查值时直接用主表的gid连接从表
     * 
     * @param sb
     * @param field
     */
    private void simpleTableImproved(StringBuffer sb, Field field) {
        GenTable table = field.getDeclaringClass().getAnnotation(GenTable.class);
        GenColumn column = field.getAnnotation(GenColumn.class);
        if (StringUtils.isBlank(column.refObjectId())) {
            // 最后一个元素
            if (!flag) {
                // 查值
                replace(sb, TagEnum.COLUMN, table.tableName() + "." + column.columnName(), true);

                /**
                 * 在引用对象是多表时，并在查值时，直接用子表的外键（也就是主表的主键gid来作为接连条件）
                 */
                if (field.getDeclaringClass() == subClass) {
                    replace(sb, TagEnum.TABLE, table.tableName(), true);
                    replace(sb, TagEnum.WHERE, table.tableName() + "." + table.refKey(), true);
                    return;
                }
            }
            replace(sb, TagEnum.TABLE, table.tableName(), true);
            replace(sb, TagEnum.WHERE, table.tableName() + ".GID", true);
            if (flag) {
                sb.append(" AND ").append(table.tableName() + "." + column.columnName());
            }
        } else {
            // 还要接着引用
            if (sb.length() == SQL.length()) {
                // 原始值，第一次
                replace(sb, TagEnum.WHERE, table.tableName() + "." + column.columnName() + "=", false);
            } else {
                replace(sb, TagEnum.WHERE,
                        table.tableName() + ".GID AND " + table.tableName() + "." + column.columnName() + "=", false);
            }
            if (flag && sb.indexOf(TagEnum.COLUMN.value) > 0) {
                // 组合where语句、未拼接显示值
                replace(sb, TagEnum.COLUMN, table.tableName() + "." + column.columnName(), true);
            }
            replace(sb, TagEnum.TABLE, table.tableName() + ",", false);

        }
    }

    /**
     * 引用表是多表
     * 
     * @param sb
     * @param field
     */
    private void moreTable(StringBuffer sb, Field field) {
        GenTable table = field.getDeclaringClass().getAnnotation(GenTable.class);
        GenColumn column = field.getAnnotation(GenColumn.class);
        if (StringUtils.isBlank(column.refObjectId())) {
            // 最后一个元素
            if (!flag) {
                // 查值
                replace(sb, TagEnum.COLUMN, table.tableName() + "." + column.columnName(), true);
            }
            // 递归
            GenTable prev = recursion(sb, table);
            replace(sb, TagEnum.TABLE, table.tableName(), true);
            replace(sb, TagEnum.WHERE, prev.tableName() + ".GID", true);
            if (flag) {
                sb.append(" AND ").append(table.tableName() + "." + column.columnName());
            }
        } else {
            // 还要接着引用
            if (flag && sb.indexOf(TagEnum.COLUMN.value) > 0) {
                // 组合where语句，第一次
                replace(sb, TagEnum.COLUMN, table.tableName() + "." + column.columnName(), true);
            }
            // 递归
            GenTable prev = recursion(sb, table);
            replace(sb, TagEnum.TABLE, table.tableName() + ",", false);
            replace(sb, TagEnum.WHERE, prev.tableName() + ".GID", true);// 之前的where类似于 A.a = ${WHERE} AND
            sb.append(TagEnum.WHERE.value);
            replace(sb, TagEnum.WHERE, " AND " + table.tableName() + "." + column.columnName() + "=", false);

        }
    }

    /**
     * 引用对象是多表时生成SQL语句 关系
     * 
     * @version 2.0
     * @param sb
     * @param field
     */
    private void moreTableImproved(StringBuffer sb, Field field) {
        GenTable table = field.getDeclaringClass().getAnnotation(GenTable.class);
        GenColumn column = field.getAnnotation(GenColumn.class);
        if (null == column && null != field.getAnnotation(SubTable.class)) {
            SubTable subTable = field.getAnnotation(SubTable.class);
            subClass = subTable.subClass();
        } else {
            if (StringUtils.isBlank(column.refObjectId())) {
                // 最后一个元素
                if (!flag) {
                    // 查值
                    replace(sb, TagEnum.COLUMN, table.tableName() + "." + column.columnName(), true);
                }
                // 递归
                GenTable prev = recursion(sb, table);
                replace(sb, TagEnum.TABLE, table.tableName(), true);
                replace(sb, TagEnum.WHERE, prev.tableName() + ".GID", true);
                if (flag) {
                    sb.append(" AND ").append(table.tableName() + "." + column.columnName());
                }
            } else {
                // 还要接着引用
                if (flag && sb.indexOf(TagEnum.COLUMN.value) > 0) {
                    // 组合where语句，第一次
                    replace(sb, TagEnum.COLUMN, table.tableName() + "." + column.columnName(), true);
                }
                // 递归
                GenTable prev = recursion(sb, table);
                replace(sb, TagEnum.TABLE, table.tableName() + ",", false);
                replace(sb, TagEnum.WHERE, prev.tableName() + ".GID", true);// 之前的where类似于 A.a = ${WHERE} AND
                sb.append(TagEnum.WHERE.value);
                replace(sb, TagEnum.WHERE, " AND " + table.tableName() + "." + column.columnName() + "=", false);

            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private GenTable recursion(StringBuffer sb, GenTable table) {
        Class par = table.parentClass();
        GenTable prev = table;
        while (!GenDO.class.equals(par)) {
            GenTable t = (GenTable) par.getAnnotation(GenTable.class);
            replace(sb, TagEnum.TABLE, t.tableName() + ",", false);
            replace(sb, TagEnum.WHERE,
                    TagEnum.WHERE.value + " AND " + t.tableName() + "." + t.refKey() + "=" + prev.tableName() + ".GID",
                    true);
            par = t.parentClass();
            prev = t;
        }
        return prev;
    }

    public static enum TagEnum {
                                COLUMN("${COLUMNS}"), TABLE("${TABLES}"), WHERE("${WHERE}");

        private TagEnum(String value){
            this.value = value;
        }

        public String value;

    }

}
