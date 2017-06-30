package com.neusoft.core.domain.dubbo;

import java.io.Serializable;

import com.neusoft.core.domain.page.Pager;

/**
 * 类BizResult.java的实现描述：dubbo服务返回值
 * 
 * @author Administrator 2017年3月27日 下午4:05:50
 */
public class BizResult implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2635922813339214239L;

    /**
     * 分页信息
     */
    private Pager             page;

    /**
     * dubbo不建议用错误码，但是不能直接抛出异常，因为可能会报CNF异常
     */
    private Integer           errCode;

    private Object            value;

    private boolean           success;

    public Pager getPage() {
        return page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
