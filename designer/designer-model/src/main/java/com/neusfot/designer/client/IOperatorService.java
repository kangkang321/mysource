package com.neusfot.designer.client;

import java.util.List;

import com.neusfot.designer.domain.vo.LayoutVO;
import com.neusfot.designer.domain.vo.ObjectEntityAttrVO;
import com.neusfot.designer.domain.vo.ObjectEntityVO;
import com.neusfot.designer.domain.vo.ObjectVO;
import com.neusfot.designer.domain.vo.QueryTmpVO;
import com.neusfot.designer.domain.vo.StyleTmpVO;
import com.neusfot.designer.domain.vo.TemplateVO;
import com.neusoft.core.domain.dubbo.BizResult;

/**
 * 类IOperatorService.java的实现描述：对象设计器有关的增、删、改、查 FIXME 错误码暂未定义
 * 
 * @author Administrator 2017年3月16日 上午9:45:50
 */

public interface IOperatorService {

    /**
     * 表单模板、图卡模板、查询模式 新增
     * 
     * @param layout
     * @return
     */

    @Deprecated
    public BizResult create(LayoutVO layout);

    /**
     * 表单模板、图卡模板、查询模式修改名称
     * 
     * @param layout
     * @return
     */

    @Deprecated
    public BizResult modify(LayoutVO layout);

    /**
     * 表单模板、图卡模板、查询模式删除（支持批量删除）
     * 
     * @param id
     * @return
     */

    @Deprecated
    public BizResult delete(List<String> id);

    /**
     * 表单模板、图卡模板、查询模式设置
     * 
     * @param layout
     * @return
     */

    @Deprecated
    public BizResult save(LayoutVO layout);

    /**
     * 修改对象
     * 
     * @author Mike
     * @param obj
     * @return
     */
    public BizResult modifyObj(ObjectVO obj);

    /**
     * 修改实体
     * 
     * @author YI
     * @param obj
     * @return
     */
    public BizResult modifyEntity(ObjectEntityVO obj);

    /**
     * 修改属性
     * 
     * @author YI
     * @param obj
     * @return
     */
    public BizResult modifyAttr(ObjectEntityAttrVO obj);

    /**
     * 批量删除对象
     * 
     * @author Mike
     * @param ids
     * @return
     */
    public BizResult deleteObj(List<String> ids);

    /**
     * 修改样式模版（样式模版表，样式模版页签表，样式模版字段表）
     * 
     * @author Mike
     * @param styleTmpVO
     * @return
     */

    @Deprecated
    public BizResult modifyCssTmp(StyleTmpVO styleTmpVO);

    /**
     * 修改查询模版以及相关信息（查询模版表，查询模版字段表）
     * 
     * @author Mike
     * @param queryTmpVO
     * @return
     */

    @Deprecated
    public BizResult modifyQueryTmp(QueryTmpVO queryTmpVO);

    /**
     * 删除查询模版以及相关信息（查询模版表，查询模版字段表）
     * 
     * @author Mike
     * @param ids
     * @return
     */

    @Deprecated
    public BizResult deleteQueryTmp(List<String> ids);

    /**
     * 删除样式模版（样式模版表，样式模版页签表，样式模版字段表）
     * 
     * @author Mike
     * @param ids
     * @return
     */

    @Deprecated
    public BizResult deleteCssTmp(List<String> ids);

    /**
     * 创建查询模版（查询模版表）
     * 
     * @author Mike
     * @param queryTmpVO
     * @return
     */

    @Deprecated
    public BizResult createQueryTmp(QueryTmpVO queryTmpVO);

    /**
     * 创建样式模版（样式模版表）
     * 
     * @author Mike
     * @param styleTmpVO
     * @return
     */

    @Deprecated
    public BizResult createStyleTmp(StyleTmpVO styleTmpVO);

    /**
     * 修改模版
     * 
     * @author yjl
     * @date 2017/05/05
     * @param styleTmpVO
     * @return
     */

    @Deprecated
    public BizResult modifyTemplate(TemplateVO tmpVO);

    /**
     * 创建模版
     * 
     * @author yjl
     * @date:2017/05/05
     * @param styleTmpVO
     * @return
     */

    @Deprecated
    public BizResult createTemplate(TemplateVO tmpVO);

    /**
     * 删除模版
     * 
     * @author yjl
     * @param ids
     * @return
     */

    @Deprecated
    public BizResult deleteTemplate(List<String> ids);

    /**
     * 修改模版(hp_od_template)
     * 
     * @author chengwei
     * @date 2017/05/23
     * @param tmpVO
     * @return
     */

    @Deprecated
    public BizResult modifyTemp(TemplateVO tmpVO);

    /**
     * 创建模版(hp_od_template)
     * 
     * @author chengwei
     * @date:2017/05/23
     * @param tmpVO
     * @return
     */

    @Deprecated
    public BizResult createTemp(TemplateVO tmpVO);

    /**
     * 删除模版(hp_od_template)
     * 
     * @author chengwei
     * @param ids
     * @return
     */

    @Deprecated
    public BizResult deleteTemp(List<String> ids);

}
