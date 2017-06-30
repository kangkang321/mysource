package com.neusoft.designer.service.domain.convert;

import java.util.HashMap;
import java.util.Map;

import com.neusfot.designer.domain.dto.SelectOptionDTO;
import com.neusfot.designer.domain.vo.SelectOptionVO;
import com.neusoft.designer.service.domain.HpOdEnumValue;

/**
 * 类SelectOptionConvert.java的实现描述：TODO 类实现描述
 * 
 * @author YI 2017年4月14日 下午5:53:05
 */

public class SelectOptionConvert {

    /**
     * po-->dto
     * 
     * @param po
     * @return
     */
    public static SelectOptionDTO convertToDTO(HpOdEnumValue po) {
        SelectOptionDTO dto = new SelectOptionDTO();

        dto.setKey(po.getGid());
        dto.setValue(po.getVal());

        return dto;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map convertMap(HpOdEnumValue po) {
        Map result = new HashMap();
        if (po != null) {
            result.put("key", po.getGid());
            result.put("value", po.getVal());
        }
        return result;
    }

    /**
     * vo-->po
     * 
     * @param vo
     * @return po
     */
    public static HpOdEnumValue convertToPO(SelectOptionVO vo) {
        HpOdEnumValue po = new HpOdEnumValue();

        po.setGid(vo.getKey());
        po.setVal(vo.getValue());

        return po;
    }

}
