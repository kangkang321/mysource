package com.neusoft.core.dao;

import java.util.Date;
import java.util.Map;

import com.neusoft.core.service.annotation.ExtTable;

/**
 * 类BasePO.java的实现描述：数据库默认值
 * 
 * @author Administrator 2017年3月27日 下午5:08:25
 */
public class GenDO {

    private String              createBy;

    private String              updateBy;

    private Date                createTime;

    private Date                updateTime;

    private Boolean             active;

    private Boolean             delete;

    private String              gid;

    /**
     * 业务系统增表扩展
     */
    @ExtTable
    private Map<String, Object> exts;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Map<String, Object> getExts() {
        return exts;
    }

    public void setExts(Map<String, Object> exts) {
        this.exts = exts;
    }

}
