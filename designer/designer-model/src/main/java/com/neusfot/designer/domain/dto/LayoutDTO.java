package com.neusfot.designer.domain.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 类LayoutDTO.java的实现描述：模式对象 FIXME 后续考虑放入redis缓存
 * 
 * @author Administrator 2017年3月16日 上午10:19:03
 */
@Data
public class LayoutDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 834927158116599864L;

    private String            id;

    private String            name;

    private boolean           df;

    /**
     * 1列表模式，2图卡模式，3查询条件
     */
    private int               type;

    private String            objId;
}
