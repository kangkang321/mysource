package com.neusoft.hp.runtime.client.action;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusfot.designer.client.IQueryService;
import com.neusfot.designer.domain.dto.ObjectDTO;
import com.neusoft.core.domain.dubbo.BizResult;
import com.neusoft.core.domain.page.Pager;
import com.neusoft.core.domain.restful.JsonResult;
import com.neusoft.core.dubbo.GenericServiceImpl;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.restful.AbstractDubboController;
import com.neusoft.core.service.annotation.LazyAutowired;
import com.neusoft.hp.runtime.client.IRuntimeEntityOperatorService;
import com.neusoft.hp.runtime.client.IRuntimeEntityQueryService;

@RestController
@RequestMapping("/runtime/entity")
public class DefaultRuntimeEntityController extends AbstractDubboController {

    @LazyAutowired("queryService")
    private IQueryService       queryService;

    @Autowired
    private GenericServiceImpl  genericServiceImpl;

    private Map<String, String> obj2owner = new ConcurrentHashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "create")
    public JsonResult create(@RequestBody JSONObject fields, @RequestParam(required = false) String objId,
                             @RequestParam(required = false) String domainClazz) {
        return invoke(() -> {
            GenericService service = getIRuntimeEntityOperatorService(getOwner(objId));
            return convertObject(service.$invoke("add",
                                                 new String[] { JSONObject.class.getName(), String.class.getName(),
                                                                String.class.getName() },
                                                 new Object[] { convertJSON(fields), objId, domainClazz }));
        }, null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "modify")
    public JsonResult modify(@RequestBody JSONObject fields, @RequestParam(required = false) String objId,
                             @RequestParam(required = false) String domainClazz) {
        return invoke(() -> {
            GenericService service = getIRuntimeEntityOperatorService(getOwner(objId));
            return convertObject(service.$invoke("save",
                                                 new String[] { JSONObject.class.getName(), String.class.getName(),
                                                                String.class.getName() },
                                                 new Object[] { convertJSON(fields), objId, domainClazz }));

        }, null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public JsonResult delete(@RequestBody List<String> ids, @RequestParam(required = false) String objId,
                             @RequestParam(required = false) String domainClazz) {
        return invoke(() -> {
            GenericService service = getIRuntimeEntityOperatorService(getOwner(objId));
            return convertObject(service.$invoke("delete",
                                                 new String[] { List.class.getName(), String.class.getName(),
                                                                String.class.getName() },
                                                 new Object[] { ids, objId, domainClazz }));

        }, null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "queryAll")
    public JsonResult queryAll(@RequestBody QueryAllVO vo, @RequestParam String objId) {
        return invoke(() -> {
            GenericService service = getIRuntimeEntityQueryService(getOwner(objId));
            return convertObject(service.$invoke("queryAll",
                                                 new String[] { JSONArray.class.getName(), Pager.class.getName(),
                                                                JSONArray.class.getName(), String.class.getName() },
                                                 new Object[] { vo.getCondition(),
                                                                new Pager(vo.getPageSize(), vo.getPageNum()),
                                                                vo.getAttributes(), objId }));

        }, (j, r) -> {
            if (j.getResultCode() == JsonResult.SUCCESS) {
                j.setPage(r.getPage());
            }
        });
    }

    @RequestMapping(method = RequestMethod.POST, value = "findById")
    public JsonResult findById(@RequestParam String id, @RequestParam String objId, @RequestBody JSONArray layout) {
        return invoke(() -> {
            GenericService service = getIRuntimeEntityQueryService(getOwner(objId));
            return convertObject(service.$invoke("selectById",
                                                 new String[] { String.class.getName(), JSONArray.class.getName(),
                                                                String.class.getName() },
                                                 new Object[] { id, layout, objId }));
        }, null);
    }

    private void refresh() {
        Map<String, String> maps = new ConcurrentHashMap();
        BizResult result = queryService.queryAllObj(new Pager());
        if (result.isSuccess()) {
            List<ObjectDTO> objs = (List<ObjectDTO>) result.getValue();
            obj2owner.clear();
            // FIXME ME
            obj2owner.putAll(objs.stream().collect(Collectors.toMap(ObjectDTO::getId, ObjectDTO::getOwner)));
        } else {
            // FIXME 报错；
        }
    }

    private String getOwner(String objId) {
        if (obj2owner.containsKey(objId)) {
            return obj2owner.get(objId);
        }
        refresh();
        if (obj2owner.containsKey(objId)) {
            return obj2owner.get(objId);
        } else {
            // FIXME 报错；
            throw new BizException("未找到对应的owner");
        }
    }

    protected GenericService getIRuntimeEntityOperatorService(String group) {
        return genericServiceImpl.getGenericService(IRuntimeEntityOperatorService.class, group,
                                                    new Consumer<ReferenceConfig<GenericService>>() {

                                                        @Override
                                                        public void accept(ReferenceConfig<GenericService> t) {
                                                            t.setTimeout(Integer.valueOf(System.getProperty("timeout",
                                                                                                            "5000")));
                                                        }

                                                    });
    }

    protected GenericService getIRuntimeEntityQueryService(String group) {
        return genericServiceImpl.getGenericService(IRuntimeEntityQueryService.class, group,
                                                    new Consumer<ReferenceConfig<GenericService>>() {

                                                        @Override
                                                        public void accept(ReferenceConfig<GenericService> t) {
                                                            t.setTimeout(Integer.valueOf(System.getProperty("timeout",
                                                                                                            "5000")));
                                                        }

                                                    });
    }

    private String getProfile() {
        // FIXME 从context里得到值
        return System.getProperty("profile", "rd");
    }

    // @RequestMapping(method = RequestMethod.POST, value = "queryTreeNode")
    // @Deprecated
    // public JsonResult queryTreeNode(@RequestParam String clazz, @RequestParam String field,
    // @RequestParam(required = false) String pgid) {
    // return getIRuntimeEntityQueryService().queryTreeNode(clazz, field, pgid);
    // }

    private BizResult convertObject(Object obj) {
        return JSON.toJavaObject((JSONObject) JSON.toJSON(obj), BizResult.class);
    }

}
