package com.neusoft.hp.runtime.client;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.neusoft.core.domain.dubbo.BizResult;
import com.neusoft.core.domain.page.Pager;
import com.neusoft.core.domain.restful.JsonResult;

/**
 * 类ISimpleEntityQueryService.java的实现描述：单实体操作接口，FIXME 后续单独jar包或作为底层jar包，暂未定义错误码
 * 
 * @author Administrator 2017年3月16日 上午10:06:48
 */
public interface IRuntimeEntityQueryService {

    /**
     * 单实体列表查询
     * 
     * @param query 前端传过来的查询条件
     * @param page
     * @param layoutId 列表模式id
     * @return
     */
    public BizResult queryAll(JSONArray query, Pager page, JSONArray layout, String objId);

    /**
     * 单实体图卡查询
     * 
     * @param id
     * @param layoutId 图卡模式id
     * @return
     */
    public BizResult selectById(String id, JSONArray layout, String objId);

    /**
     * 展示下拉
     * 
     * @param ids
     * @return
     */
    public BizResult showDropDowns(List<String> ids);

    /**
     * 查询树形节点
     * 
     * @param clazz 类名
     * @param field 待查询字段名
     * @param pgid 父节点gid字段名
     * @return
     */
    @Deprecated
    public JsonResult queryTreeNode(String clazz, String field, String pgid);

}
