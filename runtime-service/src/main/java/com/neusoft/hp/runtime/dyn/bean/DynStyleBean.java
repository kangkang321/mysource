package com.neusoft.hp.runtime.dyn.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.hp.runtime.dyn.annotation.EnumValue;
import com.neusoft.hp.runtime.dyn.annotation.OriginalValue;
import com.neusoft.hp.runtime.dyn.annotation.RefValue;

/**
 * 类DynStyleBean.java的实现描述：模板
 * 
 * @author Administrator 2017年4月18日 下午3:22:33
 */
public class DynStyleBean extends DynBean {

    private Map<Field, List<Annotation>> annotations = new HashMap<Field, List<Annotation>>();

    /**
     * 得到select后面的东西
     * 
     * @param tableName
     * @return
     */
    public String selectItem(String tableName) {
        return annotations.values().stream().flatMap(Collection::stream).filter(a -> {
            if (a instanceof OriginalValue) {
                return StringUtils.equals(((OriginalValue) a).columnInfo().tableName(), tableName);
            } else if (a instanceof EnumValue) {
                return StringUtils.equals(((EnumValue) a).columnInfo().tableName(), tableName);
            } else if (a instanceof RefValue) {
                return StringUtils.equals(((RefValue) a).columnInfo().tableName(), tableName);
            }
            return false;
        }).map(a -> {
            if (a instanceof OriginalValue) {
                return tableName + "." + ((OriginalValue) a).columnInfo().columnName();
            } else if (a instanceof EnumValue) {
                return tableName + "." + ((EnumValue) a).columnInfo().columnName();
            } else if (a instanceof RefValue) {
                return tableName + "." + ((RefValue) a).columnInfo().columnName();
            } else {
                return null;
            }
        }).reduce(new StringBuilder(), (ss, s) -> ss.append(s).append(","),
                  StringBuilder::append).append(tableName
                                                + ".GID,").append(tableName
                                                                  + ".CREATE_BY,").append(tableName
                                                                                          + ".UPDATE_BY,").append(tableName
                                                                                                                  + ".CREATE_TIME,").append(tableName
                                                                                                                                            + ".UPDATE_TIME,").append(tableName
                                                                                                                                                                      + ".IS_DELETE,").append(tableName
                                                                                                                                                                                              + ".IS_ACTIVE").toString();
    }

    /**
     * FIXME 多表查询，暂时没有这块处理
     * 
     * @param tableName
     * @param placeHolder
     * @return
     */
    public String selectItems(String tableName, boolean placeHolder) {
        Collection<List<Annotation>> anns = annotations.values();
        StringBuffer sbf = new StringBuffer();
        for (List<Annotation> list : anns) {
            for (Annotation a : list) {
                String alias = "", column = "";
                if (a instanceof OriginalValue) {
                    if (StringUtils.equals(((OriginalValue) a).columnInfo().tableName(), tableName)) {
                        column = tableName + "." + ((OriginalValue) a).columnInfo().columnName();
                        alias = tableName + "$" + ((OriginalValue) a).columnInfo().columnName();
                    }
                } else if (a instanceof EnumValue) {
                    if (StringUtils.equals(((EnumValue) a).columnInfo().tableName(), tableName)) {
                        column = tableName + "." + ((EnumValue) a).columnInfo().columnName();
                        alias = tableName + "$" + ((EnumValue) a).columnInfo().columnName();
                    }
                } else if (a instanceof RefValue) {
                    if (StringUtils.equals(((RefValue) a).columnInfo().tableName(), tableName)) {
                        column = tableName + "." + ((RefValue) a).columnInfo().columnName();
                        alias = tableName + "$" + ((RefValue) a).columnInfo().columnName();
                    }
                }
                if (!StringUtils.isEmpty(column) && !StringUtils.isEmpty(alias)) {
                    if (placeHolder) {
                        sbf.append("NULL" + " as " + alias + ",");
                    } else {
                        sbf.append(column + ",");
                    }
                }
            }
        }
        if (sbf.toString().length() > 0) {
            sbf.append(tableName + ".GID,");
            sbf.append(tableName + ".CREATE_BY,");
            sbf.append(tableName + ".UPDATE_BY,");
            sbf.append(tableName + ".CREATE_TIME,");
            sbf.append(tableName + ".UPDATE_TIME,");
            sbf.append(tableName + ".IS_DELETE,");
            sbf.append(tableName + ".IS_ACTIVE");
        }
        return sbf.toString();

    }

    public Map<Field, List<Annotation>> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Map<Field, List<Annotation>> annotations) {
        this.annotations = annotations;
    }

}
