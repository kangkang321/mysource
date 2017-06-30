package com.neusoft.designer.service.domain.convert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.JSONObject;
import com.neusfot.designer.domain.dto.LayoutDTO;
import com.neusfot.designer.domain.vo.LayoutVO;
import com.neusoft.designer.service.domain.HpObjLayout;

/**
 * 类LayoutConver.java的实现描述：TODO 类实现描述
 * 
 * @author Administrator 2017年3月16日 上午11:04:37
 */
public class LayoutConvert {

    /**
     * @param vo
     * @return
     * @throws IOException
     */
    public static HpObjLayout convertFromVO(LayoutVO vo) throws IOException {
        HpObjLayout po = new HpObjLayout();
        po.setGid(vo.getId());
        if (null != vo.getDf()) {
            po.setDf(vo.getDf());
        }
        if (null != vo.getType() && vo.getType() != 0) {
            po.setType(vo.getType().shortValue());
        }
        if (StringUtils.isNotEmpty(vo.getName())) {
            po.setName(vo.getName());
        }
        if (StringUtils.isNotEmpty(vo.getObjId())) {
            po.setHpObjObjectGid(vo.getObjId());
        }
        if (null != vo.getColumns()) {
            po.setJsondata(JSON.json(vo.getColumns()));
            List<String> columns = getFields(vo.getColumns());
            columns.add("gid");
            if (!CollectionUtils.isEmpty(columns)) {
                po.setFields(StringUtils.join(columns, ","));
            }
        }
        return po;
    }

    @SuppressWarnings("rawtypes")
    private static List<String> getFields(Object obj) {
        List<String> columns = new ArrayList<>();
        if (obj instanceof JSONArray) {
            JSONArray array = (JSONArray) obj;
            for (int i = 0; i < array.length(); i++) {
                columns.add(getField(array.get(i)));
            }
        } else if (obj instanceof JSONObject) {
            JSONObject map = (JSONObject) obj;
            columns.addAll(getFields(map.get("form")));
        } else if (obj instanceof List) {
            List list = (List) obj;
            for (Object l : list) {
                columns.add(getField(l));
            }
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            columns.addAll(getFields(map.get("form")));
        }
        return columns;
    }

    @SuppressWarnings("rawtypes")
    private static String getField(Object obj) {
        String field = null;
        if (obj instanceof JSONObject) {
            JSONObject j = (JSONObject) obj;
            field = j.getString("field");
        } else if (obj instanceof Map) {
            Map m = (Map) obj;
            field = (String) m.get("field");
        }
        return field;
    }

    /**
     * @param po
     * @return
     */
    public static LayoutDTO convertToDTO(HpObjLayout po) {
        LayoutDTO dto = new LayoutDTO();
        dto.setDf(po.getDf());
        dto.setId(po.getGid());
        dto.setName(po.getName());
        dto.setObjId(po.getHpObjObjectGid());
        dto.setType(po.getType());
        return dto;
    }

}
