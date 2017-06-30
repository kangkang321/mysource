package com.neusoft.designer.service.processor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusfot.designer.domain.vo.ObjectEntityAttrVO;
import com.neusfot.designer.domain.vo.ObjectEntityVO;
import com.neusfot.designer.domain.vo.ObjectVO;
import com.neusoft.core.service.BaseProcessor;
import com.neusoft.core.service.annotation.Processor;
import com.neusoft.designer.service.domain.HpOdAttribute;
import com.neusoft.designer.service.domain.HpOdAttributeExample;
import com.neusoft.designer.service.domain.HpOdClass;
import com.neusoft.designer.service.domain.HpOdClassExample;
import com.neusoft.designer.service.domain.HpOdObject;
import com.neusoft.designer.service.domain.constant.BusinessConstant;
import com.neusoft.designer.service.domain.convert.ObjectConvert;
import com.neusoft.designer.service.domain.convert.ObjectEntityAttrConvert;
import com.neusoft.designer.service.domain.convert.ObjectEntityConvert;
import com.neusoft.designer.service.listener.AttributeChangeListener;
import com.neusoft.designer.service.mapper.HpOdAttributeMapper;
import com.neusoft.designer.service.mapper.HpOdClassMapper;
import com.neusoft.designer.service.mapper.HpOdObjectMapper;

@Component
public class ObjectProcessor extends BaseProcessor {

    @Autowired
    private HpOdObjectMapper        hpOdObjectMapper;

    @Autowired
    private HpOdClassMapper         hpOdClassMapper;

    @Autowired
    private HpOdAttributeMapper     HpOdAttributeMapper;

    @Autowired
    private AttributeChangeListener AttributeChangeListener;

    private static Logger           LOGGER = LoggerFactory.getLogger(ObjectProcessor.class);

    @Processor(bussinessEnum = BusinessConstant.MOD_OBJ)
    public Boolean modifyObj(ObjectVO obj) {
        HpOdObject object = ObjectConvert.convertToPO(obj);
        hpOdObjectMapper.updateByPrimaryKeySelective(object);

        return true;
    }

    @Processor(bussinessEnum = BusinessConstant.MOD_ENTITY)
    public Boolean modifyEntity(ObjectEntityVO obj) {
        HpOdClass object = ObjectEntityConvert.convertToPO(obj);
        hpOdClassMapper.updateByPrimaryKeySelective(object);

        return true;
    }

    @Processor(bussinessEnum = BusinessConstant.MOD_ATTR)
    public Boolean modifyAttribute(ObjectEntityAttrVO obj) {
        HpOdAttribute object = ObjectEntityAttrConvert.convertToPO(obj);
        HpOdAttribute before = HpOdAttributeMapper.selectByPrimaryKey(object.getGid());
        HpOdAttributeMapper.updateByPrimaryKeySelective(object);
        HpOdAttribute after = HpOdAttributeMapper.selectByPrimaryKey(object.getGid());
        if (!AttributeChangeListener.changed(before, after)) {
            LOGGER.error("属性更新失败,gid=%s,before(%s,%s),after(%s,%s)", object.getGid(), before.getDataType(),
                         before.getTypeValue(), after.getDataType(), after.getTypeValue());
        }
        return true;
    }

    @Processor(bussinessEnum = BusinessConstant.DEL_OBJ)
    public Boolean deleteObj(List<String> ids) {
        for (String id : ids) {
            hpOdObjectMapper.deleteByPrimaryKey(id);// 删除对象
            HpOdClassExample example = new HpOdClassExample();
            example.createCriteria().andHpOdObjectGidEqualTo(id);
            List<HpOdClass> clazzs = hpOdClassMapper.selectByExample(example);// 根据对象查询对应的实体
            for (HpOdClass odClass : clazzs) {
                hpOdClassMapper.deleteByPrimaryKey(odClass.getGid());// 删除实体
                HpOdAttributeExample attrExample = new HpOdAttributeExample();
                attrExample.createCriteria().andHpOdClassGidEqualTo(odClass.getGid());
                HpOdAttributeMapper.deleteByExample(attrExample);// 根据实体id删除属性
            }
        }
        return true;
    }

}
