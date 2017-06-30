package com.neusfot.designer.domain.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 类ColumnDTO.java的实现描述：表头对象 FIXME 后续考虑放入redis缓存
 * 
 * @author Administrator 2017年3月16日 上午10:16:18
 */
@Data
public class ColumnDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5655544763634999014L;

    private String            id;

    private String            name;

    /**
     * 数据库列名
     */
    private String            column;

    /**
     * java列名
     */
    private String            field;
}
