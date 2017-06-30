package com.neusoft.hp.runtime.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.core.domain.dubbo.BizResult;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.service.ProcessorDispatcher;
import com.neusoft.hp.runtime.client.IRuntimeEntityOperatorService;
import com.neusoft.hp.runtime.client.constant.SysConstants;

/**
 * 类SimpleEntityOperatorServiceImpl.java的实现描述：TODO 类实现描述
 * 
 * @author Administrator 2017年3月16日 上午11:55:02
 */
public class RuntimeEntityOperatorServiceImpl implements IRuntimeEntityOperatorService {

    @SuppressWarnings("rawtypes")
    @Override
    public BizResult add(JSONObject entity, String objId, String domainClazz) {
        Class clazz = null;
        if (StringUtils.isNotBlank(domainClazz)) {
            try {
                clazz = Class.forName(domainClazz);
            } catch (ClassNotFoundException e) {
                throw new BizException(e);
            }
        }
        return ProcessorDispatcher.getInstance().invoke(SysConstants.ADD_SIMPLEENTITY, entity, objId, clazz);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public BizResult save(JSONObject entity, String objId, String domainClazz) {
        Class clazz = null;
        if (StringUtils.isNotBlank(domainClazz)) {
            try {
                clazz = Class.forName(domainClazz);
            } catch (ClassNotFoundException e) {
                throw new BizException(e);
            }
        }
        return ProcessorDispatcher.getInstance().invoke(SysConstants.SAVE_SIMPLEENTITY, entity, objId, clazz);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public BizResult delete(List<String> ids, String objId, String domainClazz) {
        Class clazz = null;
        if (StringUtils.isNotBlank(domainClazz)) {
            try {
                clazz = Class.forName(domainClazz);
            } catch (ClassNotFoundException e) {
                throw new BizException(e);
            }
        }
        return ProcessorDispatcher.getInstance().invoke(SysConstants.DEL_SIMPLEENTITY, ids, objId, clazz);
    }

}
