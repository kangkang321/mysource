package com.neusoft.core.domain.page;

import java.io.Serializable;

/**
 * 类Pager.java的实现描述：分页对象
 * 
 * @author Administrator 2017年3月27日 下午3:46:09
 */
public class Pager implements Serializable {

    /**
    * 
    */
    private static final long serialVersionUID = 4070375232802472204L;

    /**
     * 每页多少行 默认10
     */
    private Integer           pageSize;

    /**
     * 页码 默认1
     */
    private Integer           pageNum;

    /**
     * 总行数
     */
    private Integer           totalCount;

    public Pager(){
    }

    public Pager(Integer pageSize, Integer pageNum){
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public Pager(Pager page, Integer totalCount){
        this.pageSize = page.getPageSize();
        this.pageNum = page.getPageNum();
        this.totalCount = totalCount;
    }

    public Pager(Integer pageSize, Integer pageNum, Integer totalCount){
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        if (null == pageSize || pageSize <= 0) {
            return Integer.MAX_VALUE;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        if (null == pageNum || pageNum <= 0) {
            return 1;
        }
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        int mod = getTotalCount() % getPageSize();
        int size = getTotalCount() / getPageSize();
        return mod == 0 ? size : size + 1;
    }

    public Integer getLimit() {
        return (getPageNum() - 1) * getPageSize();
    }

    public Integer getOffset() {
        return getPageNum() * getPageSize();
    }

}
