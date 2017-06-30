package com.neusfot.designer.domain.vo;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 类LayoutVO.java的实现描述：模式VO
 * 
 * @author Administrator 2017年3月16日 上午10:20:11
 */
public class LayoutVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5464088423134312745L;

    private String            id;

    private String            name;

    private Boolean           df;

    private Integer           type;

    private JSON              columns;

    private JSONArray         array;

    private JSONObject        object;

    private String            objId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDf() {
        return df;
    }

    public void setDf(Boolean df) {
        this.df = df;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public JSON getColumns() {
        if (null != array) {
            return array;
        }
        if (null != object) {
            return object;
        }
        return columns;
    }

    public void setColumns(JSON columns) {
        if (columns != null) {
            if (columns instanceof JSONArray) {
                array = (JSONArray) columns;
            } else if (columns instanceof JSONObject) {
                object = (JSONObject) columns;
            }
        }
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

}
