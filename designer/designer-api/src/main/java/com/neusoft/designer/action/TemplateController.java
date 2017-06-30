package com.neusoft.designer.action;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.neusfot.designer.client.IOperatorService;
import com.neusfot.designer.client.IQueryService;
import com.neusfot.designer.domain.vo.QueryTmpVO;
import com.neusfot.designer.domain.vo.StyleTmpVO;
import com.neusfot.designer.domain.vo.TemplateVO;
import com.neusoft.core.domain.page.Pager;
import com.neusoft.core.domain.restful.JsonResult;
import com.neusoft.core.restful.AbstractDubboController;
import com.neusoft.core.service.annotation.LazyAutowired;

@RestController
@RequestMapping("/designer/layout")
public class TemplateController extends AbstractDubboController {

    @LazyAutowired("queryService")
    private IQueryService    queryService;

    @LazyAutowired("operatorService")
    private IOperatorService operatorService;

    @RequestMapping(method = RequestMethod.POST, value = "findCssTmpById")
    @ResponseBody
    public JsonResult findCssTmpById(@RequestBody(required = false) Pager page, @RequestParam String objId,
                                     @RequestParam(required = false) Boolean df,
                                     @RequestParam(required = false) Integer type) {
        return invoke(() -> queryService.findStyleTemplateByObjId(page == null ? new Pager() : page, objId, df, type),
                      new AbstractDubboController.PageBiConsumer());
    }

    @RequestMapping(method = RequestMethod.POST, value = "findQueryTmpById")
    public JsonResult findQueryTmpById(@RequestBody(required = false) Pager page, @RequestParam String objId,
                                       @RequestParam(required = false) Boolean df) {
        return invoke(() -> queryService.findQueryTemplateByObjId(page == null ? new Pager() : page, objId, df),
                      new AbstractDubboController.PageBiConsumer());
    }

    @RequestMapping(method = RequestMethod.POST, value = "findCssTmpAttrById")
    public JsonResult findCssTmpAttrById(@RequestParam String tmpId) {
        return invoke(() -> queryService.findCssTmpAttrByTmpId(tmpId), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "findQueryTmpAttrById")
    public JsonResult findQueryTmpAttrById(@RequestParam String tmpId) {
        return invoke(() -> queryService.findQueryTmpAttrByTmpId(tmpId), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "findEnumValue")
    public JsonResult findEnumValue(@RequestParam String typeId, @RequestParam String key) {
        return invoke(() -> queryService.findEnumValue(typeId, key), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "modifyCssTmp")
    public JsonResult modifyCssTmp(@RequestBody StyleTmpVO styleTmpVO) {
        return invoke(() -> operatorService.modifyCssTmp(styleTmpVO), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "modifyQueryTmp")
    public JsonResult modifyQueryTmp(@RequestBody QueryTmpVO queryTmpVO) {
        return invoke(() -> operatorService.modifyQueryTmp(queryTmpVO), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "createCssTmp")
    public JsonResult createCssTmp(@RequestBody StyleTmpVO styleTmpVO) {
        return invoke(() -> operatorService.createStyleTmp(styleTmpVO), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "createQueryTmp")
    public JsonResult createQueryTmp(@RequestBody QueryTmpVO queryTmpVO) {
        return invoke(() -> operatorService.createQueryTmp(queryTmpVO), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "deleteCssTmp")
    public JsonResult deleteCssTmp(@RequestBody List<String> ids) {

        return invoke(() -> operatorService.deleteCssTmp(ids), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "deleteQueryTmp")
    public JsonResult deleteQueryTmp(@RequestBody List<String> ids) {

        return invoke(() -> operatorService.deleteQueryTmp(ids), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "findTmpById")
    @ResponseBody
    public JsonResult findTmpById(@RequestBody(required = false) Pager page,
                                  @RequestParam(required = false) String objId,
                                  @RequestParam(required = false) String tempId,
                                  @RequestParam(required = false) Boolean df,
                                  @RequestParam(required = false) Integer type) {
        return invoke(() -> queryService.findTemplateByObjId(page == null ? new Pager() : page, objId, tempId, df,
                                                             type),
                      new AbstractDubboController.PageBiConsumer());
    }

    @RequestMapping(method = RequestMethod.POST, value = "findTmpAttrById")
    public JsonResult findTmpAttrById(@RequestParam String tmpInstanceId) {
        return invoke(() -> queryService.findTmpAttrByTmpId(tmpInstanceId), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "modifyTemplate")
    public JsonResult modifyTemplate(@RequestBody TemplateVO tmpVO) {
        return invoke(() -> operatorService.modifyTemplate(tmpVO), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTemplate")
    public JsonResult createTemplate(@RequestBody TemplateVO tmpVO) {
        return invoke(() -> operatorService.createTemplate(tmpVO), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "deleteTemplate")
    public JsonResult deleteTemplate(@RequestBody List<String> ids) {

        return invoke(() -> operatorService.deleteTemplate(ids), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "queryTemp")
    @ResponseBody
    public JsonResult queryTemp(@RequestBody(required = false) Pager page,
                                @RequestParam(required = false) String tempId,
                                @RequestParam(required = false) String code,
                                @RequestParam(required = false) String name) {
        return invoke(() -> queryService.queryTemp(page == null ? new Pager() : page, tempId, code, name),
                      new AbstractDubboController.PageBiConsumer());
    }

    @RequestMapping(method = RequestMethod.POST, value = "modifyTemp")
    public JsonResult modifyTemp(@RequestBody TemplateVO tmpVO) {
        return invoke(() -> operatorService.modifyTemp(tmpVO), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTemp")
    public JsonResult createTemp(@RequestBody TemplateVO tmpVO) {
        return invoke(() -> operatorService.createTemp(tmpVO), null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "deleteTemp")
    public JsonResult deleteTemp(@RequestBody List<String> ids) {
        return invoke(() -> operatorService.deleteTemp(ids), null);
    }
}
