package com.neusoft.designer.service.domain.convert;

import java.io.IOException;

import com.alibaba.dubbo.common.json.JSON;
import com.neusfot.designer.domain.dto.QueryTmpDTO;
import com.neusfot.designer.domain.vo.QueryTmpVO;
import com.neusoft.core.exception.BizException;
import com.neusoft.designer.service.domain.HpOdQueryTemplate;

/**
 * 类QueryTmpConvert.java的实现描述：TODO 类实现描述
 * 
 * @author YI 2017年4月14日 下午4:56:09
 */
public class QueryTmpConvert {

    /**
     * po-->dto
     * 
     * @param po
     * @return
     */
    public static QueryTmpDTO convertToDTO(HpOdQueryTemplate po) {
        QueryTmpDTO dto = new QueryTmpDTO();

        dto.setId(po.getGid());
        dto.setObjId(po.getHpOdObjectGid());
        dto.setCode(po.getCode());
        dto.setName(po.getName());
        dto.setDescription(po.getDescription());
        dto.setDf(po.getIsDefault());
        return dto;
    }

    /**
     * vo-->po
     * 
     * @param vo
     * @return po
     */
    public static HpOdQueryTemplate convertToPO(QueryTmpVO vo) {
        HpOdQueryTemplate po = new HpOdQueryTemplate();

        po.setGid(vo.getId());
        po.setHpOdObjectGid(vo.getObjId());
        po.setCode(vo.getCode());
        po.setName(vo.getName());
        po.setDescription(vo.getDescription());
        po.setIsDefault(vo.getDf());
        if (vo.getColumns() != null) {
            try {
                po.setTemplateInfo(JSON.json(vo.getColumns()));
            } catch (IOException e) {
                throw new BizException("样式模版JSON字符串格式错误");
            }
        }
        return po;
    }

}
