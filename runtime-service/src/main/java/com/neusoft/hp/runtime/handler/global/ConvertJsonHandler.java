package com.neusoft.hp.runtime.handler.global;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.neusoft.core.dao.GenDO;
import com.neusoft.hp.runtime.handler.EntityOperatorHandlerAdapter;
import com.neusoft.hp.runtime.handler.HandlerContext;
import com.neusoft.hp.runtime.json.MyJSON;
import com.neusoft.hp.runtime.json.MySerializeConfigWrapper;

/**
 * FIXME 先做一个主从结构，后续考虑有多级的情况 类JoinTableHandler.java的实现描述：TODO 类实现描述
 * 
 * @author Administrator 2017年4月20日 下午5:22:51
 */
public class ConvertJsonHandler extends EntityOperatorHandlerAdapter {

    @SuppressWarnings("deprecation")
    public ConvertJsonHandler(){

        this.setSorted(2);
        this.setUsed(true);
    }

    @Override
    public boolean afterSelect(GenDO po, HandlerContext context) {
        SerializeConfig config = new MySerializeConfigWrapper(context);
        context.setEntity((JSONObject) MyJSON.toJSON(po, config));
        return super.afterSelect(po, context);
    }

    @Override
    public boolean afterQuery(List<GenDO> po, HandlerContext context) {
        SerializeConfig config = new MySerializeConfigWrapper(context);
        context.setList((JSONArray) MyJSON.toJSON(po, config));
        return super.afterQuery(po, context);
    }

}
