package com.neusoft.hp.runtime.client.action;

import com.alibaba.fastjson.JSONArray;

import lombok.Data;

/**
 * 类QueryAllVO.java的实现描述：查询条件对应的VO
 * 
 * @author Administrator 2017年5月8日 上午10:28:17
 */
@Data
public class QueryAllVO {

    /**
     * 查询条件，包括模糊查询及枚举查询
     */
    private JSONArray condition;

    private Integer   pageNum;

    private Integer   pageSize;

    /**
     * 显示呢些字段
     */
    private JSONArray attributes;
}
