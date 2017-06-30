package com.neusoft.hp.runtime.client;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.core.domain.dubbo.BizResult;

/**
 * 类ISimpleEntityOperatorService.java的实现描述：单实体操作接口，FIXME 后续单独jar包或作为底层jar包，暂未定义错误码 这块的序列化采用fastjson
 * 
 * @author Administrator 2017年3月16日 上午10:03:53
 */
public interface IRuntimeEntityOperatorService {

    /**
     * 新增实体
     * 
     * @param entity 前端传值
     * @param layoutId 模式id
     * @param domainClazz 子表的domain类名
     * @return
     */
    public BizResult add(JSONObject entity, String objectId, String domainClazz);

    /**
     * 修改实体
     * 
     * @param entity 前端传值
     * @param layoutId 模式id
     * @param domainClazz 子表的domain类名
     * @return
     */
    public BizResult save(JSONObject entity, String objectId, String domainClazz);

    /**
     * 删除实体
     * 
     * @param ids 可以批量删除
     * @param layoutId 模式id
     * @param domainClazz 子表的domain类名
     * @return
     */
    public BizResult delete(List<String> ids, String objectId, String domainClazz);

}
