package com.neusoft.designer.service.domain.convert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.neusfot.designer.domain.dto.ObjectDTO;
import com.neusfot.designer.domain.vo.ObjectVO;
import com.neusoft.designer.service.domain.HpOdObject;

/**
 * 类ObjectConvert.java的实现描述：对象转换
 * 
 * @author YI 2017年4月14日 下午2:31:38
 */
public class ObjectConvert {

    /**
     * po-->dto
     * 
     * @param po
     * @return
     */
    public static ObjectDTO convertToDTO(HpOdObject po) {
        ObjectDTO dto = new ObjectDTO();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        dto.setId(po.getGid());
        dto.setCode(po.getCode());
        dto.setName(po.getName());
        dto.setDisplayName(po.getDisplayName());
        dto.setIsResource(po.getIsResource());
        dto.setIsReferencable(po.getIsReferencable());
        dto.setIndustry(po.getIndustry());
        dto.setDescription(po.getDescription());
        dto.setStatus(po.getActive());
        dto.setOwner(po.getOwner() == null ? "" : po.getOwner());
        dto.setJars(po.getJars());
        if (po.getCreateTime() != null) {
            dto.setCreateTime(format.format(po.getCreateTime()));
        }
        dto.setCreateBy(po.getCreateBy());

        return dto;
    }

    /**
     * vo-->po
     * 
     * @param vo
     * @return po
     */
    public static HpOdObject convertToPO(ObjectVO vo) {
        HpOdObject po = new HpOdObject();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        po.setGid(vo.getId());
        po.setCode(vo.getCode());
        po.setName(vo.getName());
        po.setDisplayName(vo.getDisplayName());
        po.setIsResource(vo.getIsResource());
        po.setIsReferencable(vo.getIsReferencable());
        po.setActive(vo.getStatus());
        po.setIndustry(vo.getIndustry());
        po.setOwner(vo.getOwner() == null ? "" : vo.getOwner());
        po.setJars(vo.getJars());
        if (vo.getCreateTime() != null) {
            try {
                po.setCreateTime(format.parse(vo.getCreateTime()));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        po.setCreateBy(vo.getCreateBy());

        return po;
    }

}
