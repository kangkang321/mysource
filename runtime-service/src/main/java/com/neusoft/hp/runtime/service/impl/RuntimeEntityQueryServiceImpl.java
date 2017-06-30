package com.neusoft.hp.runtime.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.neusoft.core.domain.dubbo.BizResult;
import com.neusoft.core.domain.page.Pager;
import com.neusoft.core.domain.restful.JsonResult;
import com.neusoft.core.service.AbstractQuery;
import com.neusoft.hp.runtime.client.IRuntimeEntityQueryService;
import com.neusoft.hp.runtime.service.processor.RuntimeEntityProcessor;

/**
 * 类SimpleEntityServiceImpl.java的实现描述：TODO 类实现描述
 * 
 * @author Administrator 2017年3月16日 上午11:55:09
 */
public class RuntimeEntityQueryServiceImpl extends AbstractQuery implements IRuntimeEntityQueryService {

    @Autowired
    private RuntimeEntityProcessor simpleEntityProcessor;

    @Override
    public BizResult queryAll(JSONArray query, Pager page, JSONArray layout, String objId) {
        return doQuery(simpleEntityProcessor.queryAll(query, page, layout, objId), (r) -> {
            r.setPage(page);
        });
    }

    @Override
    public BizResult selectById(String id, JSONArray layout, String objId) {
        return doQuery(simpleEntityProcessor.selectById(id, layout, objId), null);
    }

    @Override
    public BizResult showDropDowns(List<String> ids) {
        return doQuery(simpleEntityProcessor.showDropDowns(ids), null);
    }

    @Deprecated
    @Override
    public JsonResult queryTreeNode(String clazz, String field, String pgid) {
        return simpleEntityProcessor.queryTreeNode(clazz, field, pgid);
    }

}
