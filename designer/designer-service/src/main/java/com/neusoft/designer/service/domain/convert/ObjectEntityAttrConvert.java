package com.neusoft.designer.service.domain.convert;

import com.neusfot.designer.domain.dto.ObjectEntityAttrDTO;
import com.neusfot.designer.domain.vo.ObjectEntityAttrVO;
import com.neusoft.designer.service.domain.HpOdAttribute;

/**
 * 类ObjectEntityAttrConvert.java的实现描述：TODO 类实现描述
 * 
 * @author YI 2017年4月14日 下午3:34:17
 */
public class ObjectEntityAttrConvert {

    /**
     * po-->dto
     * 
     * @param po
     * @return
     */
    public static ObjectEntityAttrDTO convertToDTO(HpOdAttribute po, String clazz) {
        ObjectEntityAttrDTO dto = new ObjectEntityAttrDTO();

        dto.setId(po.getGid());
        dto.setCode(po.getCode());
        dto.setName(po.getName());
        dto.setFieldName(po.getFieldName());
        dto.setDisplayName(po.getDisplayName());
        if (po.getDataType() != null) {
            dto.setDataType(po.getDataType().toString());
        }
        dto.setIsFixedlength(po.getIsFixedlength());
        dto.setTypeValue(po.getTypeValue());
        dto.setIsRequired(po.getIsRequired());
        dto.setIsReadonly(po.getIsReadonly());
        dto.setIsDatafilter(po.getIsDatafilter());
        dto.setIsUnique(po.getIsUnique());
        dto.setDescription(po.getDescription());
        dto.setClazz(clazz);
        dto.setField(po.getName());

        return dto;
    }

    /**
     * vo-->po
     * 
     * @param vo
     * @return po
     */
    public static HpOdAttribute convertToPO(ObjectEntityAttrVO vo) {
        HpOdAttribute po = new HpOdAttribute();
        po.setGid(vo.getId());
        po.setCode(vo.getCode());
        po.setName(vo.getName());
        po.setFieldName(vo.getFieldName());
        po.setDisplayName(vo.getDisplayName());
        po.setIsFixedlength(vo.getIsFixedlength());
        po.setTypeValue(vo.getTypeValue());
        po.setDataType(Short.valueOf(vo.getDataType()));
        po.setIsRequired(vo.getIsRequired());
        po.setIsReadonly(vo.getIsReadonly());
        po.setIsDatafilter(vo.getIsDatafilter());
        po.setIsUnique(vo.getIsUnique());
        po.setDescription(vo.getDescription());

        return po;
    }

}
