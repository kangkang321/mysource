package com.neusoft.core.restful;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.core.domain.dubbo.BizResult;
import com.neusoft.core.domain.restful.JsonResult;
import com.neusoft.core.util.SpringMVCUtil;
import com.neusoft.core.util.TraceUtils;

/**
 * 类AbstractDubboController.java的实现描述： controller调用dubbo通用逻辑处理
 * 
 * @author Administrator 2017年3月27日 下午4:20:01
 */
public class AbstractDubboController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDubboController.class);

    protected JsonResult invoke(Supplier<BizResult> fn, BiConsumer<JsonResult, BizResult> callBack) {
        JsonResult result = new JsonResult();
        try {
            TraceUtils.beginTrace();
            BizResult r = fn.get();
            if (r.isSuccess()) {
                result.setData(r.getValue());
                result.setResultCode(JsonResult.SUCCESS);
            } else {
                result.setResultCode(JsonResult.ERROR);
            }
            if (null != callBack) {
                callBack.accept(result, r);
            }
        } catch (Exception e) {
            ServletRequest request = SpringMVCUtil.getRequest();
            if (request != null && request instanceof HttpServletRequest) {
                HttpServletRequest r = (HttpServletRequest) request;
                LOGGER.error("请求地址%s时出现错误", r.getRequestURI());
            }
            result.setResultCode(JsonResult.ERROR);
            e.printStackTrace();
        } finally {
            SpringMVCUtil.release();
            TraceUtils.endTrace();
        }
        return result;
    }

    /**
     * 类AbstractDubboController.java的实现描述：通用分页
     * 
     * @author Administrator 2017年3月27日 下午4:29:12
     */
    public static class PageBiConsumer implements BiConsumer<JsonResult, BizResult> {

        public void accept(JsonResult t, BizResult u) {
            if (t.getResultCode() == JsonResult.SUCCESS) {
                t.setPage(u.getPage());
            }
        }
    }

    /**
     * 前端數據保存的時候會有全路徑，把全路徑去掉
     * 
     * @param json
     * @return
     */
    protected JSONObject convertJSON(JSONObject json) {
        JSONObject obj = new JSONObject();
        for (String key : json.keySet()) {
            if (key.indexOf(',') < 0) {
                String newKey = key;
                if (key.indexOf("$") > -1) {
                    newKey = StringUtils.substringAfter(key, "$");
                }
                if (json.get(key) instanceof JSONArray) {
                    JSONArray arr = new JSONArray();
                    JSONArray clon = (JSONArray) json.get(key);
                    for (int i = 0; i < clon.size(); i++) {
                        if (clon.get(i) instanceof JSONObject) {
                            arr.add(convertJSON(clon.getJSONObject(i)));
                        } else {
                            arr.add(clon.get(i));
                        }
                    }
                    obj.put(newKey, arr);
                } else if (json.get(key) instanceof JSONObject) {
                    obj.put(newKey, convertJSON(json.getJSONObject(key)));
                } else {
                    obj.put(newKey, json.get(key));
                }
            }
        }
        return obj;
    }

}
