package com.neusoft.hp.runtime.service.processor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import com.alibaba.dubbo.common.bytecode.NoSuchMethodException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.core.dao.GenDO;
import com.neusoft.core.dao.GenDao;
import com.neusoft.core.dao.GenExample;
import com.neusoft.core.dao.GenJoinExample;
import com.neusoft.core.domain.page.Pager;
import com.neusoft.core.domain.restful.JsonResult;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.service.BaseProcessor;
import com.neusoft.core.service.annotation.GenColumn;
import com.neusoft.core.service.annotation.GenTable;
import com.neusoft.core.service.annotation.Processor;
import com.neusoft.core.spring.AnnotationBean;
import com.neusoft.core.util.M2Util;
import com.neusoft.core.util.ReflectWrapper;
import com.neusoft.hp.runtime.client.action.TreeNodeVO;
import com.neusoft.hp.runtime.client.constant.SysConstants;
import com.neusoft.hp.runtime.dyn.AttributeBean;
import com.neusoft.hp.runtime.dyn.ConditionQueryBean;
import com.neusoft.hp.runtime.dyn.DynBeanFactory;
import com.neusoft.hp.runtime.dyn.bean.DynQueryBean;
import com.neusoft.hp.runtime.dyn.bean.TreeDTO;
import com.neusoft.hp.runtime.dyn.mapper.DynSqlMapper;
import com.neusoft.hp.runtime.handler.EntityOperatorHandlerAdapter;
import com.neusoft.hp.runtime.handler.HandlerChain;
import com.neusoft.hp.runtime.handler.HandlerContext;
import com.neusoft.hp.runtime.util.TreeBuilderUtil;

/**
 * 类SimpleEntityProcessor.java的实现描述：实体类操作实现
 * 
 * @author Administrator 2017年3月16日 上午11:28:24
 */
@SuppressWarnings("deprecation")
public class RuntimeEntityProcessor extends BaseProcessor {

    private final static Logger LOGGER = LoggerFactory.getLogger(RuntimeEntityProcessor.class);

    @Autowired
    private AnnotationBean      annotationBean;

    @Autowired
    private HandlerChain        handlerChain;

    @Autowired
    private DynSqlMapper        dynSqlMapper;

    @Autowired
    private DynBeanFactory      dynBeanFactory;

    /**
     * 传入objId时单操作主表，传入domainClazz时单操作从表
     * 
     * @param entity 请求的json参数：主表或者从表的字段和值
     * @param objId 主表的objId
     * @param domainClazz 只传入仅仅某一个从表对应的domain类，并插入该从表中
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    @SuppressWarnings("unchecked")
    @Processor(bussinessEnum = SysConstants.ADD_SIMPLEENTITY)
    public String add(JSONObject entity, String objId,
                      Class<? extends GenDO> domainClazz) throws IOException, IllegalAccessException,
                                                          InvocationTargetException, InstantiationException {
        String objIdstr = null;
        Class<GenDao> dao = null;
        if (StringUtils.isNotBlank(objId) && null == domainClazz) {
            dao = (Class<GenDao>) getMapper(objId, false);
            domainClazz = (Class<GenDO>) getDO(objId, false);
            objIdstr = objId;
        } else {
            try {
                GenTable table = (GenTable) domainClazz.getAnnotation(GenTable.class);

                dao = (Class<GenDao>) table.mapperClass();
                objIdstr = table.objId();
            } catch (Exception e) {
                LOGGER.error(this.getClass().getName() + "domainClazz: 传入的参数有误！");
            }
        }
        GenDao mapper = applicationContext.getBean(dao);
        Method method = ReflectionUtils.findMethod(dao, SysConstants.INSERT_METHOD_NAME, null);
        GenDO po = JSON.toJavaObject(entity, domainClazz);
        HandlerContext context = handler("beforeInsert", po, null, objIdstr, null);
        ReflectWrapper.invoke(method, mapper, po);
        context = handler("afterInsert", po, null, objIdstr, context);
        return po.getGid();
    }

    /**
     * 传入objId时单操作主表，传入domainClazz时单操作从表
     */
    @SuppressWarnings("unchecked")
    @Processor(bussinessEnum = SysConstants.SAVE_SIMPLEENTITY)
    public Boolean save(JSONObject entity, String objId,
                        Class<? extends GenDO> domainClazz) throws IOException, IllegalAccessException,
                                                            InvocationTargetException, InstantiationException {
        String objIdstr = null;
        Class<GenDao> dao = null;
        if (StringUtils.isNotBlank(objId) && null == domainClazz) {
            dao = (Class<GenDao>) getMapper(objId, false);
            domainClazz = (Class<GenDO>) getDO(objId, false);
            objIdstr = objId;
        } else {
            GenTable table = (GenTable) domainClazz.getAnnotation(GenTable.class);
            dao = (Class<GenDao>) table.mapperClass();
            objIdstr = table.objId();
        }
        GenDao mapper = applicationContext.getBean(dao);
        Method method = ReflectionUtils.findMethod(dao, SysConstants.UPDATE_METHOD_NAME, null);
        GenDO po = JSON.toJavaObject(entity, domainClazz);
        HandlerContext context = handler("beforeSave", po, null, objIdstr, null);
        ReflectWrapper.invoke(method, mapper, po);
        context = handler("afterSave", po, null, objIdstr, context);
        return true;
    }

    /**
     * 传入objId时单操作主表，传入domainClazz时单操作从表
     * 
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    /*
     * @SuppressWarnings("unchecked")
     * @Processor(bussinessEnum = SysConstants.DEL_SIMPLEENTITY) public Boolean delete(List<String> ids, String objId,
     * Class<? extends GenDO> domainClazz) throws NoSuchMethodException, InvocationTargetException { String objIdstr =
     * null; Class<GenDao> dao = null; if (StringUtils.isNotBlank(objId) && null == domainClazz) { dao = (Class<GenDao>)
     * getMapper(objId, false); objIdstr = objId; } else { GenTable table = (GenTable)
     * domainClazz.getAnnotation(GenTable.class); dao = (Class<GenDao>) table.mapperClass(); objIdstr = table.objId(); }
     * GenDao mapper = applicationContext.getBean(dao); Method method = ReflectionUtils.findMethod(dao,
     * SysConstants.DELETE_METHOD_NAME, null); for (String id : ids) { HandlerContext context = handler("beforeDelete",
     * null, id, objIdstr, null); ReflectWrapper.invoke(method, mapper, id); context = handler("afterDelete", null, id,
     * objIdstr, context); } return true; }
     */
    @SuppressWarnings("unchecked")
    @Processor(bussinessEnum = SysConstants.DEL_SIMPLEENTITY)
    public Boolean delete(List<String> ids, String objId,
                          Class<? extends GenDO> domainClazz) throws NoSuchMethodException, InvocationTargetException,
                                                              InstantiationException, IllegalAccessException {
        String objIdstr = null;
        Class<GenDao> dao = null;

        if (StringUtils.isNotBlank(objId) && null == domainClazz) {
            Class<GenDO> doClazz = (Class<GenDO>) getDO(objId, false);
            dao = (Class<GenDao>) getMapper(objId, false);
            domainClazz = (Class<GenDO>) getDO(objId, false);
            objIdstr = objId;
        } else {
            GenTable table = (GenTable) domainClazz.getAnnotation(GenTable.class);
            dao = (Class<GenDao>) table.mapperClass();
            objIdstr = table.objId();
        }
        GenDao mapper = applicationContext.getBean(dao);
        Method method = ReflectionUtils.findMethod(dao, SysConstants.UPDATE_METHOD_NAME, null); // 伪删除,将is_delete和is_active,以及update_time重置
        for (String id : ids) {
            GenDO po = domainClazz.newInstance();
            po.setGid(id);
            po.setUpdateTime(new Date());
            po.setDelete(true);
            po.setActive(false);
            HandlerContext context = handler("beforeDelete", po, id, objIdstr, null);
            ReflectWrapper.invoke(method, mapper, po);
            context = handler("afterDelete", po, id, objIdstr, context);
        }
        return true;
    }

    public Supplier<JSONArray> queryAll(JSONArray query, Pager page, JSONArray layout, String objId) {

        return new Supplier<JSONArray>() {

            @SuppressWarnings("unchecked")
            @Override
            public JSONArray get() {

                Class<GenDao> dao = (Class<GenDao>) getMapper(objId, true);
                GenDao mapper = applicationContext.getBean(dao);
                Method method = ReflectionUtils.findMethod(dao, SysConstants.QUERY_METHOD_NAME, null);
                Class<GenJoinExample> clazz = (Class<GenJoinExample>) getExample(objId, true);
                GenExample example = null;
                try {
                    example = clazz.newInstance();
                    example.setUsedPage(true);
                    example.setPage(page);
                } catch (InstantiationException | IllegalAccessException e) {
                    LOGGER.error("对象设计器查询列表时不能实例化%s", clazz);
                    throw new BizException(e);
                }
                HandlerContext context = handler("beforeQuery", query, example, null, null, null, layout, objId, null);
                List<GenDO> pos = null;
                try {
                    pos = (List<GenDO>) ReflectWrapper.invoke(method, mapper, example);
                } catch (NoSuchMethodException | InvocationTargetException e) {
                    LOGGER.error("对象设计器查询列表时不能执行操作%s", method);
                    throw new BizException(e);
                }
                context = handler("afterQuery", null, null, pos, null, null, layout, objId, context);
                return context.getList();
            }

        };
    }

    public Supplier<JSONObject> selectById(String id, JSONArray layout, String objId) {
        return new Supplier<JSONObject>() {

            @SuppressWarnings("unchecked")
            @Override
            public JSONObject get() {
                Class<GenDao> dao = (Class<GenDao>) getMapper(objId, false);
                GenDao mapper = applicationContext.getBean(dao);
                // 这里有个layoutId到objId转换的
                Method method = ReflectionUtils.findMethod(dao, SysConstants.QUERY_METHOD_NAME, null);
                Class<GenJoinExample> clazz = (Class<GenJoinExample>) getExample(objId, false);
                GenExample example = null;
                try {
                    example = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    LOGGER.error("对象设计器查询单个实体时不能实例化%s", clazz);
                    throw new BizException(e);
                }
                // Method crt = ReflectionUtils.findMethod(example.getClass(), "createCriteria");
                // try {
                // Object c = ReflectWrapper.invoke(crt, example);
                // Method equal = ReflectionUtils.findMethod(c.getClass(), "andGidEqualTo", null);
                // ReflectWrapper.invoke(equal, c, id);
                //
                // } catch (NoSuchMethodException | InvocationTargetException e1) {
                // LOGGER.error("对象设计器查询单个实体时不能实例化%s", example.getClass());
                // throw new BizException(e1);
                // }
                example.setAppendWhere("gid = '" + id + "'");

                HandlerContext context = handler("beforeSelect", null, example, null, id, null, layout, objId, null);
                List<GenDO> pos = null;
                try {
                    pos = (List<GenDO>) ReflectWrapper.invoke(method, mapper, example);
                } catch (NoSuchMethodException | InvocationTargetException e) {
                    LOGGER.error("对象设计器查询时不能执行操作%s", method);
                    throw new BizException(e);
                }
                context = handler("afterSelect", null, null, null, null, pos == null ? null : pos.get(0), layout, objId,
                                  context);
                return context.getEntity();// FIXME 这里由bean转换成map
            }

        };
    }

    @SuppressWarnings("rawtypes")
    public Supplier<Map<String, List<Map>>> showDropDowns(List<String> ids) {
        return new Supplier<Map<String, List<Map>>>() {

            @SuppressWarnings("unchecked")
            @Override
            public Map<String, List<Map>> get() {
                return new HashMap<>();
            }
        };
    }

    /**
     * 实体操作用到
     * 
     * @param method EntityOperatorListenerAdapter方法名
     * @param po 要新增、修改的实体
     * @param id 要删除的实体
     * @param layoutId 布局模式
     * @param context 上下文
     * @return
     */
    private HandlerContext handler(String method, GenDO po, String id, String objId, HandlerContext context) {
        Set<EntityOperatorHandlerAdapter> listeners = handlerChain.getEntityOperatorListener(objId);
        switch (method) {
            case "beforeInsert":
                if (null == context) {
                    context = new HandlerContext(objId, getDO(objId, false), applicationContext);
                }
                for (EntityOperatorHandlerAdapter listener : listeners) {
                    if (!listener.beforeInsert(po, context)) {
                        break;
                    }
                }
                break;
            case "afterInsert":
                if (null == context) {
                    context = new HandlerContext(objId, getDO(objId, false), applicationContext);
                }
                for (EntityOperatorHandlerAdapter listener : listeners) {
                    if (!listener.afterInsert(po, context)) {
                        break;
                    }
                }
                break;
            case "beforeSave":
                if (null == context) {
                    context = new HandlerContext(objId, getDO(objId, false), applicationContext);
                }
                for (EntityOperatorHandlerAdapter listener : listeners) {
                    if (!listener.beforeSave(po, context)) {
                        break;
                    }
                }
                break;
            case "afterSave":
                if (null == context) {
                    context = new HandlerContext(objId, getDO(objId, false), applicationContext);
                }
                for (EntityOperatorHandlerAdapter listener : listeners) {
                    if (!listener.afterSave(po, context)) {
                        break;
                    }
                }
                break;
            case "beforeDelete":
                if (null == context) {
                    context = new HandlerContext(objId, getDO(objId, false), applicationContext);
                }
                for (EntityOperatorHandlerAdapter listener : listeners) {
                    if (!listener.beforeDelete(id, context)) {
                        break;
                    }
                }
                break;
            case "afterDelete":
                if (null == context) {
                    context = new HandlerContext(objId, getDO(objId, false), applicationContext);
                }
                for (EntityOperatorHandlerAdapter listener : listeners) {
                    if (!listener.afterDelete(id, context)) {
                        break;
                    }
                }
                break;

            default:
                break;
        }
        return context;
    }

    /**
     * 实体查询用到
     * 
     * @param method
     * @param query
     * @param example
     * @param pos
     * @param id
     * @param po
     * @param layout
     * @param layoutId
     * @param context
     * @return
     */
    private HandlerContext handler(String method, JSONArray query, GenExample example, List<GenDO> pos, String id,
                                   GenDO po, JSONArray layout, String objId, HandlerContext context) {
        Set<EntityOperatorHandlerAdapter> listeners = handlerChain.getEntityOperatorListener(objId);
        switch (method) {
            case "beforeSelect":
                if (null == context) {
                    context = new HandlerContext(objId, getDO(objId, false), applicationContext);
                    List<AttributeBean> selectedAttributes = convertFromJSONArray(layout);
                    context.setLayoutAttributeBean(selectedAttributes);
                    context.setStyle(dynBeanFactory.create(selectedAttributes, SysConstants.FORM_CSS_TEMPLATE,
                                                           getDO(objId, false)));
                    setOrderBye(selectedAttributes, example);
                }
                for (EntityOperatorHandlerAdapter listener : listeners) {
                    if (!listener.beforeSelect(example, context)) {
                        break;
                    }
                }
                break;
            case "afterSelect":
                if (null == context) {
                    context = new HandlerContext(objId, getDO(objId, false), applicationContext);
                }
                for (EntityOperatorHandlerAdapter listener : listeners) {
                    if (!listener.afterSelect(po, context)) {
                        break;
                    }
                }
                break;
            case "beforeQuery":
                if (null == context) {
                    context = new HandlerContext(objId, getDO(objId, true), applicationContext);
                    List<AttributeBean> selectedAttributes = convertFromJSONArray(layout);
                    context.setLayoutAttributeBean(selectedAttributes);
                    context.setStyle(dynBeanFactory.create(selectedAttributes, SysConstants.LIST_CSS_TEMPLATE,
                                                           getDO(objId, false)));
                    if (null != query) {
                        List<AttributeBean> queryAttributes = new ArrayList<>();
                        DynQueryBean bean = dynBeanFactory.create(queryAttributes, getDO(objId, false));
                        // if (null != query.get("likeStr")) {
                        // JSONObject likeStr = query.getJSONObject("likeStr");
                        // bean.setQueryStr(likeStr.getString("value"));
                        // }
                        // 条件查询
                        if (null != query) {
                            List<ConditionQueryBean> conditionQueryBeans = convertToConditionQueryBean(query);
                            if (!CollectionUtils.isEmpty(conditionQueryBeans)) {
                                bean.setConditionQueryBeans(conditionQueryBeans);
                            }
                        }

                        context.setQueryAttributeBean(queryAttributes);
                        context.setQuery(bean);
                        bean.setContext(context);
                    }
                    setOrderBye(selectedAttributes, example);
                }
                for (EntityOperatorHandlerAdapter listener : listeners) {
                    if (!listener.beforeQuery(example, context)) {
                        break;
                    }
                }
                break;
            case "afterQuery":
                if (null == context) {
                    context = new HandlerContext(objId, getDO(objId, true), applicationContext);
                }
                for (EntityOperatorHandlerAdapter listener : listeners) {
                    if (!listener.afterQuery(pos, context)) {
                        break;
                    }
                }
                break;

            default:
                break;
        }
        return context;
    }

    private List<AttributeBean> convertFromJSONArray(JSONArray array) {
        List<AttributeBean> selectedAttributes = new ArrayList<>();
        if (null != array && array.size() > 0) {
            array.forEach(obj -> {
                if (obj instanceof JSONObject) {
                    JSONObject json = (JSONObject) obj;
                    AttributeBean bean = new AttributeBean();
                    if (StringUtils.isNotBlank(json.getString("path"))) {
                        String fieldStr = StringUtils.substringBefore(json.getString("path"), ",");
                        String clazz = StringUtils.substringBefore(fieldStr, "$");
                        String fieldName = StringUtils.substringAfter(fieldStr, "$");
                        Field field = ReflectionUtils.findField(M2Util.getClass(clazz).get(), fieldName);
                        bean.setField(field);
                        bean.setPath(json.getString("path"));
                        bean.setDisplayName(json.getString("path"));// displayName显示path
                        bean.setTableName(field.getDeclaringClass().getAnnotation(GenTable.class).tableName());
                        bean.setColumnName(field.getAnnotation(GenColumn.class).columnName());
                    } else if (StringUtils.isNotBlank(json.getString("tableName"))
                               && StringUtils.isNotBlank(json.getString("columnName"))) {
                        bean.setTableName(json.getString("tableName"));
                        bean.setColumnName(json.getString("columnName"));
                    } else {
                        throw new BizException("参数列表错误");
                    }
                    // if (StringUtils.isNotBlank(json.getString("displayName"))) {
                    // }
                    // if (json.get("values") != null) {
                    // JSONArray values = json.getJSONArray("values");
                    // List<String> vs = new ArrayList<>();
                    // if (values.size() > 0) {
                    // values.forEach(s -> vs.add(String.valueOf(s)));
                    // }
                    // bean.setValues(vs);
                    // }
                    if (StringUtils.isNotBlank(json.getString("sortType"))) {
                        bean.setSorted(json.getString("sortType"));
                    }
                    selectedAttributes.add(bean);
                }
            });
        }
        return selectedAttributes;
    }

    private List<ConditionQueryBean> convertToConditionQueryBean(JSONArray array) {
        List<ConditionQueryBean> queryBeans = new ArrayList<>();
        if (null != array && array.size() > 0) {
            array.forEach(obj -> {
                if (obj instanceof JSONObject) {
                    JSONObject json = (JSONObject) obj;
                    ConditionQueryBean bean = new ConditionQueryBean();

                    // if (StringUtils.isNotBlank(json.getString("table"))) {
                    // bean.setTable(json.getString("table"));
                    // }
                    // if (StringUtils.isNotBlank(json.getString("column"))) {
                    // bean.setColumn(json.getString("column"));
                    // }
                    if (StringUtils.isNotBlank(json.getString("leftSuffix"))) {
                        bean.setLeftSuffix(json.getString("leftSuffix"));
                    }
                    if (StringUtils.isNotBlank(json.getString("joinFlag"))) {
                        bean.setJoinFlag(json.getString("joinFlag"));
                    }
                    if (StringUtils.isNotBlank(json.getString("rightSuffix"))) {
                        bean.setRightSuffix(json.getString("rightSuffix"));
                    }
                    if (StringUtils.isNotBlank(json.getString("path"))) {
                        bean.setPath(json.getString("path"));
                        String fieldStr = StringUtils.substringBefore(bean.getPath(), ",");
                        Class clazz = M2Util.getClass(StringUtils.substringBefore(fieldStr, "$")).get();
                        try {
                            Field field = clazz.getDeclaredField(StringUtils.substringAfter(fieldStr, "$"));
                            bean.setTable(((GenTable) clazz.getAnnotation(GenTable.class)).tableName());
                            bean.setColumn(field.getAnnotation(GenColumn.class).columnName());
                        } catch (NoSuchFieldException | SecurityException e) {
                            throw new BizException(e);
                        }
                    } else if (StringUtils.isNotBlank(json.getString("tableName"))
                               && StringUtils.isNotBlank(json.getString("columnName"))) {
                        bean.setTable(json.getString("tableName"));
                        bean.setColumn(json.getString("columnName"));
                    } else {
                        throw new BizException("查询参数错误");
                    }

                    if (StringUtils.isNotBlank(json.getString("type"))) {
                        bean.setType(json.getString("type"));
                    }
                    if (StringUtils.isNotBlank(json.getString("value"))) {
                        bean.setValue(json.getString("value"));
                    }
                    queryBeans.add(bean);
                }
            });
        }
        return queryBeans;
    }

    private Class<? extends GenExample> getExample(String objId, boolean isJoin) {
        GenTable table = getDO(objId, isJoin).getAnnotation(GenTable.class);
        return table.exampleClass();
    }

    private Class<? extends GenDao> getMapper(String objId, boolean isJoin) {
        GenTable table = getDO(objId, isJoin).getAnnotation(GenTable.class);
        return table.mapperClass();
    }

    private Class<? extends GenDO> getDO(String objId, boolean isJoin) {
        if (isJoin) {
            return (Class<? extends GenDO>) annotationBean.getGenJoinDO().get(objId);
        } else {
            return annotationBean.getGenDO().get(objId);
        }
    }

    /*
     * private Class<? extends GenDO> getDoByGid(String gid,boolean isJoin){ if (isJoin) { return (Class<? extends
     * GenDO>) annotationBean.getGenJoinDO().get(gid); } else { return annotationBean.getGenDO().get(gid); } }
     */

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public JsonResult queryTreeNode(String clazz, String field, String pgid) {

        Class cls = null;
        Field clsField = null;
        try {
            cls = Class.forName(clazz);
            clsField = ReflectionUtils.findField(cls, field);
        } catch (ClassNotFoundException e0) {
            LOGGER.error("找不到类文件%s", clazz);
            throw new BizException(e0);
        }
        GenTable table = (GenTable) cls.getAnnotation(GenTable.class);
        String table_name = table.tableName();
        if (StringUtils.isEmpty(pgid)) {
            if (StringUtils.equalsIgnoreCase(table_name, "hp_ar_materiel_logistics")) {
                pgid = "hp_ar_materiel_gid";
            } else {
                pgid = table_name + "_GID";
            }
        }

        // 拼sql
        StringBuffer sb = new StringBuffer("select leve ");
        if (clsField != null) {
            String column = clsField.getName();
            sb.append(" , " + column + " as name");
            sb.append(" , gid ,");
            sb.append(pgid);
            sb.append(" as pgid ");
        } else {
            sb.append(" , a.* ");
        }
        sb.append(" from ( select level as leve ,t.* from ");
        sb.append(table_name);
        sb.append(" t start with ");
        sb.append(pgid);
        sb.append(" is null ");
        sb.append(" connect by ");
        sb.append(pgid);
        sb.append(" = prior gid order by level ) a");

        String sql = sb.toString();

        List<TreeNodeVO> pos = null;
        try {
            pos = dynSqlMapper.queryList(sql);
        } catch (Exception e) {
            LOGGER.error("对象设计器查询列表时不能执行操作");
            throw new BizException(e);
        }
        if (CollectionUtils.isEmpty(pos)) {
            return null;
        }
        List<TreeDTO> trees = new ArrayList<TreeDTO>();
        for (TreeNodeVO node : pos) {
            if (node != null) {
                TreeDTO treeDto = new TreeDTO();
                treeDto.setId(node.getGid());
                treeDto.setName(node.getName());
                treeDto.setPid(node.getPgid());
                treeDto.setLevel(node.getLeve());
                trees.add(treeDto);
            }
        }
        TreeBuilderUtil treeBuilder = new TreeBuilderUtil(trees);
        Object obj = JSON.toJSON((List<TreeDTO>) treeBuilder.buildListTree());
        JsonResult result = new JsonResult();
        result.setData(obj);
        result.setResultCode(1000);
        return result;
    }

    private void setOrderBye(List<AttributeBean> columns, GenExample example) {
        // orderByClause
        StringBuffer sb = new StringBuffer();
        columns.stream().filter(m -> StringUtils.equalsIgnoreCase(m.getSorted(), "asc")
                                     || StringUtils.equalsIgnoreCase(m.getSorted(), "desc")).forEach(m -> {
                                         sb.append(",").append(m.getColumnName()).append(" ").append(StringUtils.upperCase(m.getSorted()));
                                     });
        if (sb.length() > 0) {
            com.neusoft.core.util.ReflectionUtils.setFieldValue(example, "orderByClause", sb.substring(1));
        }
    }
}
