package com.neusoft.designer.service.domain.convert;

import com.alibaba.druid.util.StringUtils;
import com.neusfot.designer.domain.dto.ObjectEntityDTO;
import com.neusfot.designer.domain.vo.ObjectEntityVO;
import com.neusoft.designer.service.domain.HpOdClass;

/**
 * 类ObjectConvert.java的实现描述：实体转换
 * 
 * @author YI 2017年4月14日 下午2:31:38
 */
public class ObjectEntityConvert {

    /**
     * po-->dto
     * 
     * @param po
     * @return
     */
    public static ObjectEntityDTO convertToDTO(HpOdClass po, String parentName) {
        ObjectEntityDTO dto = new ObjectEntityDTO();
        dto.setId(po.getGid());
        dto.setCode(po.getCode());
        dto.setName(po.getName());
        dto.setDisplayName(po.getDisplayName());
        dto.setParentName(parentName);
        dto.setTableName(po.getTableName());
        dto.setClassName(po.getClassName());
        dto.setDescription(po.getDescription());
        dto.setIsPrimary(po.getIsPrimary());
        dto.setParentGid(po.getHpOdClassGid());
        if(!po.getIsPrimary() && !StringUtils.isEmpty(po.getHpOdClassGid())){
            String[] s = po.getClassName().split("\\.");
            dto.setBelongObj(s[s.length - 1].substring(0, 1).toLowerCase() + s[s.length - 1].substring(1) + "s");
        }

        return dto;
    }

    /**
     * vo-->po
     * 
     * @param vo
     * @return po
     */
    public static HpOdClass convertToPO(ObjectEntityVO vo) {
        HpOdClass po = new HpOdClass();
        po.setGid(vo.getId());
        po.setCode(vo.getCode());
        po.setName(vo.getName());
        po.setDisplayName(vo.getDisplayName());
        po.setTableName(vo.getTableName());
        po.setClassName(vo.getClassName());
        po.setDescription(vo.getDescription());
        po.setIsPrimary(vo.getIsPrimary());
        po.setHpOdClassGid(vo.getParentGid());
        return po;
    }

}
