package com.neusoft.designer.service.domain.convert;

import com.neusfot.designer.domain.dto.ColumnDTO;
import com.neusoft.designer.service.domain.HpObjAttribute;

/**
 * 类ColumnConver.java的实现描述：TODO 类实现描述
 * 
 * @author Administrator 2017年3月16日 上午11:04:27
 */
public class ColumnConvert {

    /**
     * @param po
     * @return
     */
    public static ColumnDTO convertToDTO(HpObjAttribute po) {
        ColumnDTO dto = new ColumnDTO();
        // FIXME 这里要做个转换
        dto.setColumn(null);
        dto.setField(po.getName());
        dto.setId(po.getGid());
        dto.setName(po.getLabel());
        return dto;
    }
}
