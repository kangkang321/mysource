package com.neusoft.core.domain.restful;

import java.io.Serializable;

import com.neusoft.core.domain.page.Pager;

/**
 * 类JsonResult.java的实现描述：前后端分离，后台返回的数据对象
 * 
 * @author Administrator 2017年3月27日 下午4:07:32
 */
public class JsonResult implements Serializable {

    public static final Integer SUCCESS          = 1000;

    public static final Integer ERROR            = 10001;

    /**
     * 
     */
    private static final long   serialVersionUID = 3489542072695484293L;

    /**
     * 返回值
     */
    private Object              data;

    /**
     * 分页信息
     */
    private Pager               page;

    /**
     * 返回码
     */
    private Integer             resultCode;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Pager getPage() {
        return page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

}
