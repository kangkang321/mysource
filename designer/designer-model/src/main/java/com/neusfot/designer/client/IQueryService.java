package com.neusfot.designer.client;

import com.neusoft.core.domain.dubbo.BizResult;
import com.neusoft.core.domain.page.Pager;

/**
 * 类IQueryService.java的实现描述：对象设计器有关的查询操作 FIXME 错误码暂未定义
 * 
 * @author Administrator 2017年3月16日 上午9:49:25
 */
public interface IQueryService {

    /**
     * 查找所有实体
     * 
     * @param page
     * @return
     */
    @Deprecated
    public BizResult queryAllEntity(Pager page);

    /**
     * 根据实体id查找表头
     * 
     * @param id
     * @return
     */
    @Deprecated
    public BizResult findColumnsById(String id);

    /**
     * 根据实体id，类别查找模式
     * 
     * @param type 0查询所有，1列表模式，2图卡模式，3查询条件
     * @return
     */

    @Deprecated
    public BizResult queryAllLayout(String type, String objId, Pager page);

    /**
     * @param 模式id
     * @return "type" //1列表模式、2图卡模式、3查询条件 "columns" //json字符串
     * [[{"id"//表头id}，……]，……]type等于1或3时，外围数组是只有一个元素；type等于2时，外围数组里有几个元素，图卡模式就有几行
     */

    @Deprecated
    public BizResult findLayoutById(String id);

    /**
     * 查询所有对象（对象表）
     * 
     * @author Mike
     * @param page
     * @return
     */
    public BizResult queryAllObj(Pager page);

    /**
     * 根据对象id查询该对象所有样式模版（样式模版表）
     * 
     * @author Mike
     * @param page
     * @param objId
     * @return
     */

    @Deprecated
    public BizResult findStyleTemplateByObjId(Pager page, String objId, Boolean df, Integer type);

    /**
     * 根据对象id查询该对象所有查询模版（查询模版表）
     * 
     * @author Mike
     * @param page
     * @param objId
     * @return
     */

    @Deprecated
    public BizResult findQueryTemplateByObjId(Pager page, String objId, Boolean df);

    /**
     * 根据对象id查找对象属性、实体以及实体属性（对象表，实体表，属性表）
     * 
     * @author Mike
     * @param objId
     * @return
     */
    public BizResult findObjectEntityAttr(String objId);

    /**
     * 根据样式模版id查询表单模版字段信息（样式模版表，样式页签表，样式模版字段表）
     * 
     * @author Mike
     * @param tmpId
     * @return
     */

    @Deprecated
    public BizResult findCssTmpAttrByTmpId(String tmpId);

    /**
     * 根据查询模版id查询“查询模版”字段信息(查询模版表，查询模版字段表)
     * 
     * @author Mike
     * @param tmpId
     * @return
     */

    @Deprecated
    public BizResult findQueryTmpAttrByTmpId(String tmpId);

    /**
     * @param typeId
     * @return
     */

    @Deprecated
    public BizResult findEnumValue(String typeId, String key);

    /**
     * 根据对象id查询该对象所有模版
     * 
     * @author yjl
     * @date 2017/05/05
     * @param page
     * @param objId
     * @return
     */

    @Deprecated
    public BizResult findTemplateByObjId(Pager page, String objId, String tempId, Boolean df, Integer type);

    /**
     * 根据样式模版id查询模版字段信息
     * 
     * @author yjl
     * @date 2017/05/05
     * @param tmpId
     * @return
     */

    @Deprecated
    public BizResult findTmpAttrByTmpId(String tmpId);

    /**
     * 根据id查询模版
     * 
     * @author chengwei
     * @date 2017/05/23
     * @param page
     * @param tempId
     * @param code
     * @param name
     * @return
     */

    @Deprecated
    public BizResult queryTemp(Pager page, String tempId, String code, String name);

    /**
     * 根据objId或classGid查询所有HpOdAttribute，如果classGid不为空则优先用classGid查询
     * 
     * @param objId
     * @param classGid
     * @return
     */
    public BizResult queryHpOdAttributes(String objId, String classGid, Pager page);

}
