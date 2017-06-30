package com.neusoft.designer.action;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neusfot.designer.client.IQueryService;
import com.neusoft.core.domain.page.Pager;
import com.neusoft.core.domain.restful.JsonResult;
import com.neusoft.core.restful.AbstractDubboController;
import com.neusoft.core.service.annotation.LazyAutowired;

/**
 * 类EntityController.java的实现描述：实体对象的controller
 * 
 * @author Administrator 2017年3月16日 上午10:22:18
 */
@RestController
@RequestMapping("/designer/entity")
public class EntityController extends AbstractDubboController {

    @LazyAutowired("queryService")
    private IQueryService queryService;

    @RequestMapping(method = RequestMethod.POST, value = "queryAll")
    public JsonResult queryAll(@RequestBody(required = false) Pager page) {
        return invoke(() -> queryService.queryAllEntity(page == null ? new Pager() : page),
                      new AbstractDubboController.PageBiConsumer());
    }

    @RequestMapping(method = RequestMethod.POST, value = "findById")
    public JsonResult findById(@RequestParam String id) {
        return invoke(() -> queryService.findColumnsById(id), null);
    }

}
