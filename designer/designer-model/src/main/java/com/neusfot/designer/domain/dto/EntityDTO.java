package com.neusfot.designer.domain.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 类EntityDTO.java的实现描述：实体对象 FIXME 后续考虑放入redis缓存
 * 
 * @author Administrator 2017年3月16日 上午10:18:16
 */
@Data
public class EntityDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6655001369498911033L;

    private String            id;

    private String            name;

    private String            type;

    private String            module;

    private List<String>      dfLayouts;

}
