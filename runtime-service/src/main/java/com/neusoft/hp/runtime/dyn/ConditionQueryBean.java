package com.neusoft.hp.runtime.dyn;

import lombok.Data;

@Data
public class ConditionQueryBean {

    private String leftSuffix;

    private String joinFlag;

    private String rightSuffix;

    private String path;
    /**
     * 表名
     */
    private String table;

    /**
     * 字段名
     */
    private String column;

    /**
     * 查询条件 gt > lt < eq = ne <> ge >= le <= in in like like
     */
    private String type;

    /**
     * 字段值
     */
    private String value;

}
