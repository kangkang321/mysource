package com.neusoft.hp.runtime.client.action;

import java.io.Serializable;

import lombok.Data;

/**
 * 类TreeNodeVO.java的实现描述：树节点的bean
 * 
 * @author chengwei 2017年5月27日 下午1:19:59
 */
@Data
@Deprecated
public class TreeNodeVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Integer           leve;                 // level

    private String            gid;                  // id

    private String            pgid;                 // 父id

    private String            name;                 // 名称

    private String            description;          // 描述

}
