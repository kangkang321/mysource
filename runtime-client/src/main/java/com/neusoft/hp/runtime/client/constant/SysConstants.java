package com.neusoft.hp.runtime.client.constant;

public interface SysConstants {

    public final static String ADD_SIMPLEENTITY                = "ADD_SIMPLEENTITY";

    public final static String SAVE_SIMPLEENTITY               = "SAVE_SIMPLEENTITY";

    public final static String DEL_SIMPLEENTITY                = "DEL_SIMPLEENTITY";

    public final static String INSERT_METHOD_NAME              = "insert";

    public final static String UPDATE_METHOD_NAME              = "updateByPrimaryKeySelective";

    public final static String UPDATE_SUB_METHOD_NAME          = "updateByExampleSelective";

    public final static String DELETE_METHOD_NAME              = "deleteByPrimaryKey";

    public final static String DELETE_SUB_METHOD_NAME          = "deleteByExample";

    public final static String QUERY_METHOD_NAME               = "selectByExample";

    public final static String QUERY_BY_PRIMARYKEY_METHOD_NAME = "selectByPrimaryKey";

    // /**
    // * FIXME 这个后期要该
    // */
    // public final static String SELECT_METHOD_NAME = "selectByPrimaryKey";

    public final static String FORM_CSS_TEMPLATE               = "1";

    public final static String LIST_CSS_TEMPLATE               = "2";

    /**
     * sql中的占位符
     */
    public final static String REF                             = "#{ref}";
}
