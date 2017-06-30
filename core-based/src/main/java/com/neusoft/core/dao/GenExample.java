package com.neusoft.core.dao;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.core.domain.page.Pager;

/**
 * 类BaseExample.java的实现描述：分页
 * 
 * @author Administrator 2017年3月27日 下午5:08:03
 */
public class GenExample {

    private boolean usedPage;

    private Pager   page;

    /**
     * 用来替换select items
     */
    private String  selectItems;

    /**
     * 用来追加用户自定义查询
     */
    private String  appendWhere;

    public boolean isUsedPage() {
        return usedPage;
    }

    public void setUsedPage(boolean usedPage) {
        this.usedPage = usedPage;
    }

    public Pager getPage() {
        return page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public String getSelectItems() {
        return selectItems;
    }

    public void setSelectItems(String selectItems) {
        this.selectItems = selectItems;
    }

    public String getAppendWhere() {
        return appendWhere;
    }

    public void setAppendWhere(String appendWhere) {
        if (StringUtils.isNotBlank(this.appendWhere)) {
            this.appendWhere = this.appendWhere + " and (" + appendWhere + ")";
        } else {
            this.appendWhere = appendWhere;
        }
    }

}
