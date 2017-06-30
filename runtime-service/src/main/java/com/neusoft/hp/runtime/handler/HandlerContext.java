package com.neusoft.hp.runtime.handler;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.core.dao.GenDO;
import com.neusoft.hp.runtime.dyn.AttributeBean;
import com.neusoft.hp.runtime.dyn.bean.DynQueryBean;
import com.neusoft.hp.runtime.dyn.bean.DynStyleBean;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 类ListenerContext.java的实现描述：埋点上下文，一些与当前模式有关的数据
 * 
 * @author Administrator 2017年3月16日 上午11:01:42
 */
@RequiredArgsConstructor
@Data
public class HandlerContext {

    @NonNull
    private String                 objId;

    @NonNull
    private Class<? extends GenDO> domainClass;

    @NonNull
    private ApplicationContext     applicationContext;

    private JSONArray              list;

    private JSONObject             entity;

    private DynStyleBean           style;

    private DynQueryBean           query;

    private List<AttributeBean>    layoutAttributeBean;

    private List<AttributeBean>    queryAttributeBean;
}
