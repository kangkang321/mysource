package com.neusoft.designer.action;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neusfot.designer.client.IOperatorService;
import com.neusfot.designer.client.IQueryService;
import com.neusfot.designer.domain.vo.ObjectEntityAttrVO;
import com.neusfot.designer.domain.vo.ObjectEntityVO;
import com.neusfot.designer.domain.vo.ObjectVO;
import com.neusoft.core.domain.page.Pager;
import com.neusoft.core.domain.restful.JsonResult;
import com.neusoft.core.restful.AbstractDubboController;
import com.neusoft.core.service.annotation.LazyAutowired;

@RestController
@RequestMapping("/designer/obj")
public class ObjectController extends AbstractDubboController {

    @LazyAutowired("queryService")
    private IQueryService    queryService;

    @LazyAutowired("operatorService")
    private IOperatorService operatorService;

    @RequestMapping(method = RequestMethod.POST, value = "queryAll")
    public JsonResult queryAll(@RequestBody(required = false) Pager page) {
        return invoke(() -> queryService.queryAllObj(page == null ? new Pager() : page),
                      new AbstractDubboController.PageBiConsumer());
    }

    @RequestMapping(method = RequestMethod.POST, value = "findById")
    public JsonResult findById(@RequestParam String objId) {
        return invoke(() -> queryService.findObjectEntityAttr(objId), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "modifyObject")
    public JsonResult modifyObject(@RequestBody ObjectVO obj) {
        return invoke(() -> operatorService.modifyObj(obj), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "modifyEntity")
    public JsonResult modifyEntity(@RequestBody ObjectEntityVO obj) {
        return invoke(() -> operatorService.modifyEntity(obj), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "modifyAttr")
    public JsonResult modifyAttr(@RequestBody ObjectEntityAttrVO obj) {
        return invoke(() -> operatorService.modifyAttr(obj), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "deleteObj")
    public JsonResult deleteObj(@RequestBody List<String> ids) {
        return invoke(() -> operatorService.deleteObj(ids), null);
    }

}
