package com.neusoft.designer.service.domain.convert;

import com.neusfot.designer.domain.dto.EntityDTO;
import com.neusoft.designer.service.domain.HpObjObject;

/**
 * 类EntityConver.java的实现描述：TODO 类实现描述
 * 
 * @author Administrator 2017年3月16日 上午11:04:33
 */
public class EntityConvert {

    /**
     * @param po
     * @return
     */
    public static EntityDTO convertToDTO(HpObjObject po) {
        EntityDTO dto = new EntityDTO();
        dto.setId(po.getGid());
        dto.setModule(po.getModule());
        dto.setName(po.getName());
        dto.setType(po.getCategory());
        return dto;
    }

}
