package com.neusoft.designer.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.neusfot.designer.client.IOperatorService;
import com.neusfot.designer.domain.vo.LayoutVO;
import com.neusfot.designer.domain.vo.ObjectEntityAttrVO;
import com.neusfot.designer.domain.vo.ObjectEntityVO;
import com.neusfot.designer.domain.vo.ObjectVO;
import com.neusfot.designer.domain.vo.QueryTmpVO;
import com.neusfot.designer.domain.vo.StyleTmpVO;
import com.neusfot.designer.domain.vo.TemplateVO;
import com.neusoft.core.domain.dubbo.BizResult;
import com.neusoft.core.service.ProcessorDispatcher;
import com.neusoft.designer.service.domain.constant.BusinessConstant;

/**
 * 类OperatorServiceImpl.java的实现描述：TODO 类实现描述
 * 
 * @author Administrator 2017年3月16日 上午11:54:38
 */
@Component("operatorService")
public class OperatorServiceImpl implements IOperatorService {

    @Deprecated
    @Override
    public BizResult create(LayoutVO layout) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.ADD_LAYOUT, layout);
    }

    @Deprecated
    @Override
    public BizResult modify(LayoutVO layout) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.MOD_LAYOUT, layout);
    }

    @Deprecated
    @Override
    public BizResult delete(List<String> ids) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.DEL_LAYOUT, ids);
    }

    @Deprecated
    @Override
    public BizResult save(LayoutVO layout) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.SAVE_LAYOUT, layout);
    }

    @Override
    public BizResult modifyObj(ObjectVO obj) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.MOD_OBJ, obj);
    }

    @Override
    public BizResult modifyEntity(ObjectEntityVO obj) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.MOD_ENTITY, obj);
    }

    @Override
    public BizResult modifyAttr(ObjectEntityAttrVO obj) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.MOD_ATTR, obj);
    }

    @Override
    public BizResult deleteObj(List<String> ids) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.DEL_OBJ, ids);
    }

    @Deprecated
    @Override
    public BizResult modifyCssTmp(StyleTmpVO styleTmpVO) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.MOD_STYLE_TEMPLATE, styleTmpVO);
    }

    @Deprecated
    @Override
    public BizResult modifyQueryTmp(QueryTmpVO queryTmpVO) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.MOD_QUERY_TEMPLATE, queryTmpVO);
    }

    @Deprecated
    @Override
    public BizResult deleteQueryTmp(List<String> ids) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.DEL_QUERY_TEMPLATE, ids);
    }

    @Deprecated
    @Override
    public BizResult deleteCssTmp(List<String> ids) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.DEL_STYLE_TEMPLATE, ids);
    }

    @Deprecated
    @Override
    public BizResult createQueryTmp(QueryTmpVO queryTmpVO) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.ADD_QUERY_TEMPLATE, queryTmpVO);
    }

    @Deprecated
    @Override
    public BizResult createStyleTmp(StyleTmpVO styleTmpVO) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.ADD_STYLE_TEMPLATE, styleTmpVO);
    }

    @Deprecated
    @Override
    public BizResult modifyTemplate(TemplateVO tmpVO) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.MOD_TEMPLATE, tmpVO);
    }

    @Deprecated
    @Override
    public BizResult createTemplate(TemplateVO tmpVO) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.ADD_TEMPLATE, tmpVO);
    }

    @Deprecated
    @Override
    public BizResult deleteTemplate(List<String> ids) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.DEL_TEMPLATE, ids);
    }

    @Deprecated
    @Override
    public BizResult modifyTemp(TemplateVO tmpVO) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.MOD_TEMP, tmpVO);
    }

    @Deprecated
    @Override
    public BizResult createTemp(TemplateVO tmpVO) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.ADD_TEMP, tmpVO);
    }

    @Deprecated
    @Override
    public BizResult deleteTemp(List<String> ids) {
        return ProcessorDispatcher.getInstance().invoke(BusinessConstant.DEL_TEMP, ids);
    }

}
