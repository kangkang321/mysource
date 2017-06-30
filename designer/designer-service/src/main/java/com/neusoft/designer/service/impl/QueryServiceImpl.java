package com.neusoft.designer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.neusfot.designer.client.IQueryService;
import com.neusfot.designer.domain.dto.EntityDTO;
import com.neusfot.designer.domain.dto.ObjectDTO;
import com.neusfot.designer.domain.dto.ObjectEntityAttrDTO;
import com.neusfot.designer.domain.dto.ObjectEntityDTO;
import com.neusoft.core.domain.dubbo.BizResult;
import com.neusoft.core.domain.page.Pager;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.service.AbstractQuery;
import com.neusoft.designer.service.domain.HpObjAttribute;
import com.neusoft.designer.service.domain.HpObjAttributeExample;
import com.neusoft.designer.service.domain.HpObjLayout;
import com.neusoft.designer.service.domain.HpObjLayoutExample;
import com.neusoft.designer.service.domain.HpObjObject;
import com.neusoft.designer.service.domain.HpObjObjectExample;
import com.neusoft.designer.service.domain.HpOdAttribute;
import com.neusoft.designer.service.domain.HpOdAttributeExample;
import com.neusoft.designer.service.domain.HpOdClass;
import com.neusoft.designer.service.domain.HpOdClassExample;
import com.neusoft.designer.service.domain.HpOdEnumValue;
import com.neusoft.designer.service.domain.HpOdEnumValueExample;
import com.neusoft.designer.service.domain.HpOdObject;
import com.neusoft.designer.service.domain.HpOdObjectExample;
import com.neusoft.designer.service.domain.HpOdQueryTemplate;
import com.neusoft.designer.service.domain.HpOdQueryTemplateExample;
import com.neusoft.designer.service.domain.HpOdStyleTemplate;
import com.neusoft.designer.service.domain.HpOdStyleTemplateExample;
import com.neusoft.designer.service.domain.HpOdTemplate;
import com.neusoft.designer.service.domain.HpOdTemplateExample;
import com.neusoft.designer.service.domain.HpOdTemplateInstance;
import com.neusoft.designer.service.domain.HpOdTemplateInstanceExample;
import com.neusoft.designer.service.domain.convert.ColumnConvert;
import com.neusoft.designer.service.domain.convert.EntityConvert;
import com.neusoft.designer.service.domain.convert.LayoutConvert;
import com.neusoft.designer.service.domain.convert.ObjectConvert;
import com.neusoft.designer.service.domain.convert.ObjectEntityAttrConvert;
import com.neusoft.designer.service.domain.convert.ObjectEntityConvert;
import com.neusoft.designer.service.domain.convert.QueryTmpConvert;
import com.neusoft.designer.service.domain.convert.SelectOptionConvert;
import com.neusoft.designer.service.domain.convert.StyleTmpConvert;
import com.neusoft.designer.service.domain.convert.TemplateConvert;
import com.neusoft.designer.service.mapper.HpObjAttributeMapper;
import com.neusoft.designer.service.mapper.HpObjLayoutMapper;
import com.neusoft.designer.service.mapper.HpObjObjectMapper;
import com.neusoft.designer.service.mapper.HpOdAttributeMapper;
import com.neusoft.designer.service.mapper.HpOdClassMapper;
import com.neusoft.designer.service.mapper.HpOdEnumValueMapper;
import com.neusoft.designer.service.mapper.HpOdObjectMapper;
import com.neusoft.designer.service.mapper.HpOdQueryTemplateMapper;
import com.neusoft.designer.service.mapper.HpOdStyleTemplateMapper;
import com.neusoft.designer.service.mapper.HpOdTemplateInstanceMapper;
import com.neusoft.designer.service.mapper.HpOdTemplateMapper;

/**
 * 类QueryServiceImpl.java的实现描述：TODO 类实现描述
 * 
 * @author Administrator 2017年3月16日 上午11:54:44
 */
@Component("queryService")
public class QueryServiceImpl extends AbstractQuery implements IQueryService {

    @Autowired
    private HpObjObjectMapper          hpObjObjectMapper;

    @Autowired
    private HpObjAttributeMapper       hpObjAttributeMapper;

    @Autowired
    private HpObjLayoutMapper          hpObjLayoutMapper;

    @Autowired
    private HpOdClassMapper            hpOdClassMapper;

    @Autowired
    private HpOdObjectMapper           hpOdObjectMapper;

    @Autowired
    private HpOdAttributeMapper        hpOdAttributeMapper;

    @Autowired
    private HpOdStyleTemplateMapper    hpOdStyleTemplateMapper;

    @Autowired
    private HpOdQueryTemplateMapper    hpOdQueryTemplateMapper;

    @Autowired
    private HpOdEnumValueMapper        hpOdEnumValueMapper;

    @Autowired
    private HpOdTemplateMapper         hpOdTemplateMapper;

    @Autowired
    private HpOdTemplateInstanceMapper hpOdTemplateInstanceMapper;

    @Override
    public BizResult queryAllEntity(Pager page) {
        return doQuery(() -> {
            HpObjObjectExample example = new HpObjObjectExample();
            example.setUsedPage(true);
            example.setPage(page);
            List<HpObjObject> pos = hpObjObjectMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(pos)) {
                List<EntityDTO> dtos = pos.stream().map(EntityConvert::convertToDTO).collect(Collectors.toList());
                for (EntityDTO dto : dtos) {
                    dto.setDfLayouts(getDfLayouts(dto.getId()));
                }
                return dtos;
            }
            return null;
        }, m -> {
            m.setPage(page);
        });
    }

    @Deprecated
    private List<String> getDfLayouts(String objId) {
        HpObjLayoutExample example = new HpObjLayoutExample();
        HpObjLayoutExample.Criteria c = example.createCriteria();
        c.andHpObjObjectGidEqualTo(objId);
        c.andDfEqualTo(true);
        List<HpObjLayout> pos = hpObjLayoutMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(pos)) {
            pos.sort((a, b) -> a.getType() - b.getType());
            return pos.stream().map(HpObjLayout::getGid).collect(Collectors.toList());
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public BizResult findColumnsById(String id) {
        return doQuery(() -> {
            Map<String, List> rs = new HashMap<>();
            HpObjAttributeExample example = new HpObjAttributeExample();
            HpObjAttributeExample.Criteria c = example.createCriteria();
            c.andHpObjObjectGidEqualTo(id);
            List<HpObjAttribute> pos = hpObjAttributeMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(pos)) {
                rs.put("columns", pos.stream().map(ColumnConvert::convertToDTO).collect(Collectors.toList()));
            }
            List<String> layouts = getDfLayouts(id);
            if (!CollectionUtils.isEmpty(layouts)) {
                rs.put("layouts", layouts);
            }
            return rs;
        }, null);
    }

    @Deprecated
    @Override
    public BizResult queryAllLayout(String type, String objId, Pager page) {
        return doQuery(() -> {
            HpObjLayoutExample example = new HpObjLayoutExample();
            example.setUsedPage(true);
            example.setPage(page);
            example.setOrderByClause("GID DESC");
            HpObjLayoutExample.Criteria c = example.createCriteria();
            c.andHpObjObjectGidEqualTo(objId);
            if (StringUtils.isNotEmpty(type) && !"0".equals(type)) {
                c.andTypeEqualTo(Short.valueOf(type));
            }
            List<HpObjLayout> pos = hpObjLayoutMapper.selectByExample(example);

            if (!CollectionUtils.isEmpty(pos)) {
                return pos.stream().map(LayoutConvert::convertToDTO).collect(Collectors.toList());
            }
            return null;
        }, m -> {
            m.setPage(page);
        });
    }

    @Deprecated
    @Override
    public BizResult findLayoutById(String id) {
        return doQuery(() -> {
            HpObjLayout po = hpObjLayoutMapper.selectByPrimaryKey(id);
            Map<String, Object> result = new HashMap<String, Object>();
            if (null != po) {
                result.put("type", String.valueOf(po.getType()));
                if (StringUtils.isNotEmpty(po.getJsondata())) {
                    try {
                        if (po.getType() == 1 || po.getType() == 3) {
                            result.put("columns", JSON.parse(po.getJsondata(), List.class));
                        } else if (po.getType() == 2) {
                            result.put("columns", JSON.parse(po.getJsondata(), Map.class));
                        } else {
                            throw new BizException("invalid layoutType");
                        }
                    } catch (ParseException e) {
                        throw new BizException(e);
                    }
                }
            }
            return result;
        }, null);
    }

    @Override
    public BizResult queryAllObj(Pager page) {
        return doQuery(() -> {
            HpOdObjectExample example = new HpOdObjectExample();
            example.setUsedPage(true);
            example.setPage(page);
            List<HpOdObject> pos = hpOdObjectMapper.selectByExample(example);
            /*
             * for (HpOdObject hpOdObject : pos) { HpOdTemplateExample example2 = new HpOdTemplateExample();
             * HpOdTemplateExample.Criteria c = example2.createCriteria();
             * c.andHpOdObjectGidEqualTo(hpOdObject.getGid()); List<HpOdTemplate> hpOdTemplate =
             * hpOdTemplateMapper.selectByExample(example2); if (!CollectionUtils.isEmpty(hpOdTemplate)) {
             * hpOdObject.setTempId(hpOdTemplate.get(0).getGid());
             * hpOdObject.setTempName(hpOdTemplate.get(0).getDisplayName()); } }
             */
            return pos.stream().map(ObjectConvert::convertToDTO).collect(Collectors.toList());
        }, m -> {
            m.setPage(page);
        });
    }

    @Override
    public BizResult findObjectEntityAttr(String objId) {
        return doQuery(() -> {
            HpOdObject obj = hpOdObjectMapper.selectByPrimaryKey(objId);
            if (obj == null) {
                throw new BizException("根据objId：" + objId + "未查询到对象");
            }

            /*
             * HpOdTemplateExample example2 = new HpOdTemplateExample(); HpOdTemplateExample.Criteria c =
             * example2.createCriteria(); c.andHpOdObjectGidEqualTo(objId); List<HpOdTemplate> hpOdTemplate =
             * hpOdTemplateMapper.selectByExample(example2); if (!CollectionUtils.isEmpty(hpOdTemplate)) {
             * obj.setTempId(hpOdTemplate.get(0).getGid()); }
             */

            ObjectDTO objDTO = ObjectConvert.convertToDTO(obj);

            HpOdClassExample example = new HpOdClassExample();
            example.createCriteria().andHpOdObjectGidEqualTo(objId);

            List<HpOdClass> clazzs = hpOdClassMapper.selectByExample(example);

            List<ObjectEntityDTO> entitys = new ArrayList<ObjectEntityDTO>();

            for (HpOdClass hpOdClass : clazzs) {
                String parentName = "";
                if (!StringUtils.isEmpty(hpOdClass.getHpOdClassGid())) {
                    for (HpOdClass self : clazzs) {
                        if (hpOdClass.getHpOdClassGid().equals(self.getGid())) {
                            parentName = self.getClassName();
                            break;
                        }
                    }
                }
                ObjectEntityDTO entityDTO = ObjectEntityConvert.convertToDTO(hpOdClass, parentName);
                HpOdAttributeExample attrExample = new HpOdAttributeExample();
                attrExample.createCriteria().andHpOdClassGidEqualTo(hpOdClass.getGid());

                List<HpOdAttribute> attrs = hpOdAttributeMapper.selectByExample(attrExample);
                List<ObjectEntityAttrDTO> attrDTO = attrs.stream().map((m) -> {
                    return ObjectEntityAttrConvert.convertToDTO(m, hpOdClass.getClassName());
                }).collect(Collectors.toList());
                entityDTO.setAttributes(attrDTO);

                entitys.add(entityDTO);

            }
            objDTO.setEntitys(entitys);

            return objDTO;
        }, null);
    }

    @Deprecated
    @Override
    public BizResult findStyleTemplateByObjId(Pager page, String objId, Boolean df, Integer type) {
        return doQuery(() -> {
            HpOdStyleTemplateExample example = new HpOdStyleTemplateExample();
            example.setUsedPage(true);
            example.setPage(page);
            // example.setOrderByClause("HP_OD_OBJECT_GID DESC");
            HpOdStyleTemplateExample.Criteria c = example.createCriteria();
            c.andHpOdObjectGidEqualTo(objId);
            if (df != null) c.andIsDefaultEqualTo(df);
            if (type != null) c.andTypeEqualTo(type.shortValue());
            List<HpOdStyleTemplate> pos = hpOdStyleTemplateMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(pos)) {
                return pos.stream().map(StyleTmpConvert::convertToDTO).collect(Collectors.toList());
            }
            return null;
        }, m -> {
            m.setPage(page);
        });
    }

    @Deprecated
    @Override
    public BizResult findQueryTemplateByObjId(Pager page, String objId, Boolean df) {
        return doQuery(() -> {
            HpOdQueryTemplateExample example = new HpOdQueryTemplateExample();
            example.setUsedPage(true);
            example.setPage(page);
            // example.setOrderByClause("HP_OD_OBJECT_GID DESC");
            HpOdQueryTemplateExample.Criteria c = example.createCriteria();
            c.andHpOdObjectGidEqualTo(objId);
            if (df != null) c.andIsDefaultEqualTo(df);
            List<HpOdQueryTemplate> pos = hpOdQueryTemplateMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(pos)) {
                return pos.stream().map(QueryTmpConvert::convertToDTO).collect(Collectors.toList());
            }
            return null;
        }, m -> {
            m.setPage(page);
        });
    }

    @Deprecated
    @Override
    public BizResult findCssTmpAttrByTmpId(String tmpId) {
        return doQuery(() -> {
            HpOdStyleTemplate po = hpOdStyleTemplateMapper.selectByPrimaryKey(tmpId);
            Map<String, Object> result = new HashMap<String, Object>();
            if (null != po) {
                result.put("type", String.valueOf(po.getType()));
                if (!StringUtils.isEmpty(po.getTemplateInfo())) {
                    try {
                        result.put("columns", JSON.parse(po.getTemplateInfo(), Object.class));
                    } catch (ParseException e) {
                        throw new BizException(e);
                    }
                }
            }
            return result;
        }, null);
    }

    @Deprecated
    @Override
    public BizResult findQueryTmpAttrByTmpId(String tmpId) {
        return doQuery(() -> {
            HpOdQueryTemplate po = hpOdQueryTemplateMapper.selectByPrimaryKey(tmpId);

            Map<String, Object> result = new HashMap<String, Object>();
            if (null != po) {
                if (!StringUtils.isEmpty(po.getTemplateInfo())) {
                    try {
                        result.put("columns", JSON.parse(po.getTemplateInfo(), Object.class));
                    } catch (ParseException e) {
                        throw new BizException(e);
                    }
                }
            }
            return result;
        }, null);
    }

    @Deprecated
    @Override
    public BizResult findEnumValue(String typeId, String key) {
        return doQuery(() -> {
            HpOdEnumValueExample example = new HpOdEnumValueExample();
            HpOdEnumValueExample.Criteria c = example.createCriteria();
            c.andHpOdEnumGidEqualTo(typeId);
            if (!StringUtils.isEmpty(key)) {
                c.andGidEqualTo(key);
            }
            List<HpOdEnumValue> pos = hpOdEnumValueMapper.selectByExample(example);
            return pos == null
                   || pos.isEmpty() ? null : pos.stream().map(SelectOptionConvert::convertMap).collect(Collectors.toList());
        }, null);
    }

    @Deprecated
    @Override
    public BizResult findTemplateByObjId(Pager page, String objId, String tempId, Boolean df, Integer type) {
        return doQuery(() -> {

            HpOdTemplateInstanceExample example2 = new HpOdTemplateInstanceExample();
            example2.setUsedPage(true);
            example2.setPage(page);
            HpOdTemplateInstanceExample.Criteria c2 = example2.createCriteria();
            if (!StringUtils.isEmpty(tempId)) {
                // 有tempId直接根据tempId查

                c2.andHpOdTemplateGidEqualTo(tempId);
                if (objId != null) c2.andHpOdObjectGidEqualTo(objId);
                if (df != null) c2.andIsDefaultEqualTo(df);
                if (type != null) c2.andTemplateTypeEqualTo(type.shortValue());
                List<HpOdTemplateInstance> pos = hpOdTemplateInstanceMapper.selectByExample(example2);
                if (!CollectionUtils.isEmpty(pos)) {
                    return pos.stream().map(TemplateConvert::convertToDTO).collect(Collectors.toList());
                }

            } else {
                if (!StringUtils.isEmpty(objId)) {
                    // 没有tempId根据objId查
                    // Fix me 模板表和对象关系不确定 后期可能会继续改造
                    c2.andHpOdObjectGidEqualTo(objId);
                    if (df != null) c2.andIsDefaultEqualTo(df);
                    if (type != null) c2.andTemplateTypeEqualTo(type.shortValue());
                    List<HpOdTemplateInstance> pos = hpOdTemplateInstanceMapper.selectByExample(example2);
                    if (!CollectionUtils.isEmpty(pos)) {
                        return pos.stream().map(TemplateConvert::convertToDTO).collect(Collectors.toList());
                    }

                } else {
                    // 没有tempId也没有objId 根据 type和df查询

                    if (tempId != null) c2.andHpOdTemplateGidEqualTo(tempId);
                    if (df != null) c2.andIsDefaultEqualTo(df);
                    if (type != null) c2.andTemplateTypeEqualTo(type.shortValue());
                    List<HpOdTemplateInstance> pos = hpOdTemplateInstanceMapper.selectByExample(example2);
                    if (!CollectionUtils.isEmpty(pos)) {
                        return pos.stream().map(TemplateConvert::convertToDTO).collect(Collectors.toList());
                    }
                }
            }
            page.setTotalCount(0);
            return null;
        }, m -> {
            m.setPage(page);
        });
    }

    @Deprecated
    @Override
    public BizResult findTmpAttrByTmpId(String tmpInstanceId) {
        return doQuery(() -> {
            HpOdTemplateInstance po = hpOdTemplateInstanceMapper.selectByPrimaryKey(tmpInstanceId);
            Map<String, Object> result = new HashMap<String, Object>();
            if (null != po) {
                String tempId = po.getHpOdTemplateGid();
                result.put("tempId", tempId);
                result.put("objId", po.getHpOdObjectGid());
                result.put("type", String.valueOf(po.getTemplateType()));
                if (!StringUtils.isEmpty(po.getTemplateInfo())) {
                    try {
                        result.put("columns", JSON.parse(po.getTemplateInfo(), Object.class));
                    } catch (ParseException e) {
                        throw new BizException(e);
                    }
                }
            }
            return result;
        }, null);
    }

    @Deprecated
    @Override
    public BizResult queryTemp(Pager page, String tempId, String code, String name) {
        return doQuery(() -> {

            HpOdTemplateExample example = new HpOdTemplateExample();
            example.setUsedPage(true);
            example.setPage(page);
            HpOdTemplateExample.Criteria c = example.createCriteria();
            if (tempId != null) c.andGidEqualTo(tempId);
            if (code != null) c.andCodeEqualTo(code);
            if (name != null) c.andNameEqualTo(name);
            List<HpOdTemplate> pos = hpOdTemplateMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(pos)) {
                return pos.stream().map(TemplateConvert::convertToDTO).collect(Collectors.toList());
            }
            page.setTotalCount(0);
            return null;
        }, m -> {
            m.setPage(page);
        });
    }

    @Override
    public BizResult queryHpOdAttributes(String objId, String classGid, Pager page) {
        return doQuery(() -> {
            HpOdAttributeExample example = new HpOdAttributeExample();
            example.setUsedPage(true);
            example.setPage(page);
            HpOdAttributeExample.Criteria c = example.createCriteria();
            List<HpOdAttribute> pos = new ArrayList<>();
            if (StringUtils.isNotBlank(classGid)) {
                c.andHpOdClassGidEqualTo(classGid);
                pos = hpOdAttributeMapper.selectByExample(example);
            } else if (StringUtils.isNotBlank(objId)) {
                HpOdClassExample classExample = new HpOdClassExample();
                HpOdClassExample.Criteria criteria = classExample.createCriteria();
                criteria.andHpOdObjectGidEqualTo(objId);
                List<HpOdClass> hpOdClasses = hpOdClassMapper.selectByExample(classExample);
                if (!CollectionUtils.isEmpty(hpOdClasses)) {
                    List<String> classGids = new ArrayList<>();
                    for (HpOdClass hpOdClass : hpOdClasses) {
                        classGids.add(hpOdClass.getGid());
                    }
                    c.andHpOdClassGidIn(classGids);
                    pos = hpOdAttributeMapper.selectByExample(example);
                }

            }
            page.setTotalCount(pos.size());
            BizResult result = new BizResult();
            result.setPage(page);
            result.setSuccess(true);
            result.setValue(pos);
            return result;
        }, m -> {
            m.setPage(page);
        });
    }

}
