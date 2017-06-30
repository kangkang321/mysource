package com.neusoft.hp.runtime.sql;

public enum TypeEnum {
                      EQ("=", ""), NE("<>", ""), GT(">", ""), LT("<", ""), GE(">=", ""), LE("<=", ""), IN("IN", ""),
                      NOIN("NOT IN", ""), LIKE("LIKE", ""), NULL("IS NULL", ""), NONULL("IS NOT NULL", "");

    private String expression;

    private String desc;

    private TypeEnum(String expression, String desc){
        this.expression = expression;
        this.desc = desc;
    }

    public String getExpression() {
        return expression;
    }

    public String getDesc() {
        return desc;
    }

}
