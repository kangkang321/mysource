package com.neusoft.designer.service.domain.convert;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.common.json.JSON;
import com.neusfot.designer.domain.dto.TemplateDTO;
import com.neusfot.designer.domain.vo.TemplateVO;
import com.neusoft.core.exception.BizException;
import com.neusoft.designer.service.domain.HpOdTemplate;
import com.neusoft.designer.service.domain.HpOdTemplateInstance;

/**
 * 类QueryTmpConvert.java的实现描述：TODO 类实现描述
 * 
 * @author YI 2017年4月14日 下午4:56:09
 */
public class TemplateConvert {

    /**
     * po-->dto
     * 
     * @param po
     * @return
     */
    public static TemplateDTO convertToDTO(HpOdTemplateInstance po) {
        TemplateDTO dto = new TemplateDTO();

        dto.setId(po.getGid());
        dto.setTempId(po.getHpOdTemplateGid());
        dto.setObjId(po.getHpOdObjectGid());
        dto.setCode(po.getCode());
        dto.setName(po.getName());
        dto.setType(po.getTemplateType().intValue());
        dto.setDescription(po.getDescription());
        dto.setDf(po.getIsDefault());
        return dto;
    }

    /**
     * po-->dto
     * 
     * @param po
     * @return
     */
    public static TemplateDTO convertToDTO(HpOdTemplate po) {
        TemplateDTO dto = new TemplateDTO();
        dto.setId(po.getGid());
        dto.setObjId(po.getHpOdObjectGid());
        dto.setCode(po.getCode());
        dto.setName(po.getName());
        dto.setDescription(po.getDescription());
        return dto;
    }

    /**
     * vo-->po
     * 
     * @param vo
     * @return po
     */
    public static HpOdTemplateInstance convertToPO(TemplateVO vo) {
        HpOdTemplateInstance po = new HpOdTemplateInstance();

        po.setGid(vo.getId());
        // po.setObjId(vo.getObjId());
        po.setHpOdObjectGid(vo.getObjId());
        po.setCode(vo.getCode());
        if (StringUtils.isNotEmpty(vo.getTempId())) {
            po.setHpOdTemplateGid(vo.getTempId());
        }
        if (StringUtils.isNotEmpty(vo.getName())) {
            po.setName(vo.getName());
        }
        if (vo.getType() != null) {
            po.setTemplateType(vo.getType().shortValue());
        }
        if (vo.getDf() != null) {
            po.setIsDefault(vo.getDf());
        }
        po.setDescription(vo.getDescription());
        if (vo.getColumns() != null) {
            try {
                po.setTemplateInfo(JSON.json(vo.getColumns()));
            } catch (IOException e) {
                throw new BizException("样式模版JSON字符串格式错误");
            }
        }
        return po;
    }

    /**
     * vo-->po
     * 
     * @param vo
     * @return po
     */
    public static HpOdTemplate convertToHpOdTemplate(TemplateVO vo) {
        HpOdTemplate po = new HpOdTemplate();

        po.setGid(vo.getId());
        po.setHpOdObjectGid(vo.getObjId());

        if (StringUtils.isNotEmpty(vo.getCode())) {
            po.setCode(vo.getCode());
        }
        if (StringUtils.isNotEmpty(vo.getName())) {
            po.setName(vo.getName());
        }
        if (StringUtils.isNotEmpty(vo.getDescription())) {
            po.setDescription(vo.getDescription());
        }

        return po;
    }

}
