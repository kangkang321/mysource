package com.neusoft.hp.runtime.client.action;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.core.domain.page.Pager;
import com.neusoft.core.domain.restful.JsonResult;
import com.neusoft.core.restful.AbstractDubboController;
import com.neusoft.hp.runtime.client.IRuntimeEntityOperatorService;
import com.neusoft.hp.runtime.client.IRuntimeEntityQueryService;

public abstract class AbstractRuntimeEntityController extends AbstractDubboController {

    @RequestMapping(method = RequestMethod.POST, value = "create")
    public JsonResult create(@RequestBody JSONObject fields, @RequestParam(required = false) String objId,
                             @RequestParam(required = false) String domainClazz) {
        return invoke(() -> getIRuntimeEntityOperatorService().add(convertJSON(fields), objId, domainClazz), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "modify")
    public JsonResult modify(@RequestBody JSONObject fields, @RequestParam(required = false) String objId,
                             @RequestParam(required = false) String domainClazz) {
        return invoke(() -> getIRuntimeEntityOperatorService().save(convertJSON(fields), objId, domainClazz), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public JsonResult delete(@RequestBody List<String> ids, @RequestParam(required = false) String objId,
                             @RequestParam(required = false) String domainClazz) {
        return invoke(() -> getIRuntimeEntityOperatorService().delete(ids, objId, domainClazz), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "queryAll")
    public JsonResult queryAll(@RequestBody QueryAllVO vo, @RequestParam String objId) {
        return invoke(() -> getIRuntimeEntityQueryService().queryAll(vo.getCondition(),
                                                                     new Pager(vo.getPageSize(), vo.getPageNum()),
                                                                     vo.getAttributes(), objId),
                      (j, r) -> {
                          if (j.getResultCode() == JsonResult.SUCCESS) {
                              j.setPage(r.getPage());
                          }
                      });
    }

    @RequestMapping(method = RequestMethod.POST, value = "findById")
    public JsonResult findById(@RequestParam String id, @RequestParam String objId, @RequestBody JSONArray layout) {
        return invoke(() -> getIRuntimeEntityQueryService().selectById(id, layout, objId), null);
    }

    protected abstract IRuntimeEntityOperatorService getIRuntimeEntityOperatorService();

    protected abstract IRuntimeEntityQueryService getIRuntimeEntityQueryService();

    @RequestMapping(method = RequestMethod.POST, value = "queryTreeNode")
    @Deprecated
    public JsonResult queryTreeNode(@RequestParam String clazz, @RequestParam String field,
                                    @RequestParam(required = false) String pgid) {
        return getIRuntimeEntityQueryService().queryTreeNode(clazz, field, pgid);
    }
}
