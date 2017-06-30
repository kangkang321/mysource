package com.neusoft.designer.action;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neusfot.designer.client.IOperatorService;
import com.neusfot.designer.client.IQueryService;
import com.neusfot.designer.domain.vo.LayoutVO;
import com.neusoft.core.domain.page.Pager;
import com.neusoft.core.domain.restful.JsonResult;
import com.neusoft.core.restful.AbstractDubboController;
import com.neusoft.core.service.annotation.LazyAutowired;

/**
 * 类LayoutController.java的实现描述：表单模式的controller
 * 
 * @author Administrator 2017年3月16日 上午10:43:19
 */
@RestController
@RequestMapping("/designer/layoutOld")
public class LayoutController extends AbstractDubboController {

    @LazyAutowired("queryService")
    private IQueryService    queryService;

    @LazyAutowired("operatorService")
    private IOperatorService operatorService;

    @RequestMapping(method = RequestMethod.POST, value = "queryAll")
    public JsonResult queryAll(@RequestParam String type, @RequestParam String objId,
                               @RequestBody(required = false) Pager page) {
        return invoke(() -> queryService.queryAllLayout(type, objId, page == null ? new Pager() : page),
                      new AbstractDubboController.PageBiConsumer());
    }

    @RequestMapping(method = RequestMethod.POST, value = "findById")
    public JsonResult findById(@RequestParam String id) {
        return invoke(() -> queryService.findLayoutById(id), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "create")
    public JsonResult create(@RequestBody LayoutVO layout) {
        return invoke(() -> operatorService.create(layout), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "modify")
    public JsonResult modify(@RequestBody LayoutVO layout) {
        layout.setDf(null);// FIXME 目前通过df是否为null来判断是否是修改默认值
        return invoke(() -> operatorService.modify(layout), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "modifyDf")
    public JsonResult modifyDf(@RequestBody LayoutVO layout) {
        return invoke(() -> operatorService.modify(layout), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public JsonResult delete(@RequestBody List<String> ids) {
        return invoke(() -> operatorService.delete(ids), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "save")
    public JsonResult save(@RequestBody LayoutVO layout) {
        return invoke(() -> operatorService.save(layout), null);
    }

}
