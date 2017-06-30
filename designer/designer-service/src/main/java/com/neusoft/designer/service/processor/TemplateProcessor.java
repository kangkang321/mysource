package com.neusoft.designer.service.processor;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusfot.designer.domain.vo.QueryTmpVO;
import com.neusfot.designer.domain.vo.StyleTmpVO;
import com.neusfot.designer.domain.vo.TemplateVO;
import com.neusoft.core.service.BaseProcessor;
import com.neusoft.core.service.annotation.Processor;
import com.neusoft.designer.service.domain.HpOdQueryTemplate;
import com.neusoft.designer.service.domain.HpOdQueryTemplateExample;
import com.neusoft.designer.service.domain.HpOdStyleTemplate;
import com.neusoft.designer.service.domain.HpOdStyleTemplateExample;
import com.neusoft.designer.service.domain.HpOdTemplate;
import com.neusoft.designer.service.domain.HpOdTemplateInstance;
import com.neusoft.designer.service.domain.HpOdTemplateInstanceExample;
import com.neusoft.designer.service.domain.constant.BusinessConstant;
import com.neusoft.designer.service.domain.convert.QueryTmpConvert;
import com.neusoft.designer.service.domain.convert.StyleTmpConvert;
import com.neusoft.designer.service.domain.convert.TemplateConvert;
import com.neusoft.designer.service.mapper.HpOdQueryTemplateMapper;
import com.neusoft.designer.service.mapper.HpOdStyleTemplateMapper;
import com.neusoft.designer.service.mapper.HpOdTemplateInstanceMapper;
import com.neusoft.designer.service.mapper.HpOdTemplateMapper;

@Deprecated
@Component
public class TemplateProcessor extends BaseProcessor {

    @Autowired
    private HpOdStyleTemplateMapper    hpOdStyleTemplateMapper;

    @Autowired
    private HpOdQueryTemplateMapper    hpOdQueryTemplateMapper;

    @Autowired
    private HpOdTemplateInstanceMapper hpOdTemplateInstanceMapper;

    @Autowired
    private HpOdTemplateMapper         hpOdTemplateMapper;

    @Processor(bussinessEnum = BusinessConstant.ADD_STYLE_TEMPLATE)
    public String createStyleTemplate(StyleTmpVO vo) {
        HpOdStyleTemplate temp = StyleTmpConvert.convertToPO(vo);
        if (StringUtils.isEmpty(temp.getDisplayName())) {
            temp.setDisplayName(temp.getName());
        }
        hpOdStyleTemplateMapper.insertSelective(temp);
        if (temp != null && temp.getIsDefault() != null && temp.getIsDefault()) {
            this.resetDf(temp, null);
        }
        return temp.getGid();
    }

    @Processor(bussinessEnum = BusinessConstant.MOD_STYLE_TEMPLATE)
    public Boolean modifyStyleTemplate(StyleTmpVO vo) {
        HpOdStyleTemplate styleTemp = StyleTmpConvert.convertToPO(vo);
        hpOdStyleTemplateMapper.updateByPrimaryKeySelective(styleTemp);
        if (styleTemp != null && styleTemp.getIsDefault() != null && styleTemp.getIsDefault()) {
            this.resetDf(styleTemp, null);
        }
        return true;
    }

    @Processor(bussinessEnum = BusinessConstant.DEL_STYLE_TEMPLATE)
    public Boolean deleteStyleTemplate(List<String> ids) {
        for (String id : ids) {
            hpOdStyleTemplateMapper.deleteByPrimaryKey(id);
        }
        return true;
    }

    @Processor(bussinessEnum = BusinessConstant.ADD_QUERY_TEMPLATE)
    public String createQueryTemplate(QueryTmpVO vo) {
        HpOdQueryTemplate temp = QueryTmpConvert.convertToPO(vo);
        if (StringUtils.isEmpty(temp.getDisplayName())) {
            temp.setDisplayName(temp.getName());
        }
        hpOdQueryTemplateMapper.insertSelective(temp);
        if (temp != null && temp.getIsDefault() != null && temp.getIsDefault()) {
            this.resetDf(null, temp);
        }
        return temp.getGid();
    }

    @Processor(bussinessEnum = BusinessConstant.MOD_QUERY_TEMPLATE)
    public Boolean modifyQueryTemplate(QueryTmpVO vo) {
        HpOdQueryTemplate queryTemp = QueryTmpConvert.convertToPO(vo);
        hpOdQueryTemplateMapper.updateByPrimaryKeySelective(queryTemp);
        if (queryTemp != null && queryTemp.getIsDefault() != null && queryTemp.getIsDefault()) {
            this.resetDf(null, queryTemp);
        }
        return true;
    }

    @Processor(bussinessEnum = BusinessConstant.DEL_QUERY_TEMPLATE)
    public Boolean deleteQueryTemplate(List<String> ids) {
        for (String id : ids) {
            hpOdQueryTemplateMapper.deleteByPrimaryKey(id);
        }
        return true;
    }

    private void resetDf(HpOdStyleTemplate styleTemp, HpOdQueryTemplate queryTemp) {
        if (styleTemp != null) {
            HpOdStyleTemplate notDf = new HpOdStyleTemplate();
            notDf.setIsDefault(false);
            HpOdStyleTemplateExample styleExample = new HpOdStyleTemplateExample();
            HpOdStyleTemplateExample.Criteria c = styleExample.createCriteria();
            c.andHpOdObjectGidEqualTo(styleTemp.getHpOdObjectGid());
            HpOdStyleTemplate l = hpOdStyleTemplateMapper.selectByPrimaryKey(styleTemp.getGid());
            c.andTypeEqualTo(l.getType());
            c.andGidNotEqualTo(styleTemp.getGid());
            hpOdStyleTemplateMapper.updateByExampleSelective(notDf, styleExample);
        }
        if (queryTemp != null) {
            HpOdQueryTemplate notDf = new HpOdQueryTemplate();
            notDf.setIsDefault(false);
            HpOdQueryTemplateExample queryExample = new HpOdQueryTemplateExample();
            HpOdQueryTemplateExample.Criteria c = queryExample.createCriteria();
            c.andHpOdObjectGidEqualTo(queryTemp.getHpOdObjectGid());
            c.andGidNotEqualTo(queryTemp.getGid());
            hpOdQueryTemplateMapper.updateByExampleSelective(notDf, queryExample);
        }
    }

    @Processor(bussinessEnum = BusinessConstant.ADD_TEMPLATE)
    public String createTemplate(TemplateVO vo) {
        HpOdTemplateInstance temp = TemplateConvert.convertToPO(vo);
        if (StringUtils.isEmpty(temp.getDisplayName())) {
            temp.setDisplayName(temp.getName());
        }
        hpOdTemplateInstanceMapper.insertSelective(temp);
        if (temp != null && temp.getIsDefault() != null && temp.getIsDefault()) {
            this.newResetDf(temp);
        }
        return temp.getGid();
    }

    @Processor(bussinessEnum = BusinessConstant.MOD_TEMPLATE)
    public Boolean modifyTemplate(TemplateVO vo) {
        HpOdTemplateInstance temp = TemplateConvert.convertToPO(vo);
        hpOdTemplateInstanceMapper.updateByPrimaryKeySelective(temp);
        if (temp.getIsDefault() != null) {
            if (temp != null && temp.getIsDefault() != null && temp.getIsDefault()) {
                this.newResetDf(temp);
            }
        }

        return true;
    }

    @Processor(bussinessEnum = BusinessConstant.DEL_TEMPLATE)
    public Boolean deleteTemplate(List<String> ids) {
        for (String id : ids) {
            hpOdTemplateInstanceMapper.deleteByPrimaryKey(id);
        }
        return true;
    }

    private void newResetDf(HpOdTemplateInstance temp) {
        if (temp != null) {
            HpOdTemplateInstance notDf = new HpOdTemplateInstance();
            notDf.setIsDefault(false);
            HpOdTemplateInstanceExample example = new HpOdTemplateInstanceExample();
            HpOdTemplateInstanceExample.Criteria c = example.createCriteria();
            if (StringUtils.isNoneBlank(temp.getHpOdTemplateGid())) {
                c.andHpOdTemplateGidEqualTo(temp.getHpOdTemplateGid());
            }
            HpOdTemplateInstance l = hpOdTemplateInstanceMapper.selectByPrimaryKey(temp.getGid());
            c.andTemplateTypeEqualTo(l.getTemplateType());
            c.andGidNotEqualTo(temp.getGid());
            hpOdTemplateInstanceMapper.updateByExampleSelective(notDf, example);
        }
    }

    @Processor(bussinessEnum = BusinessConstant.MOD_TEMP)
    public Boolean modifyTemp(TemplateVO vo) {
        HpOdTemplate temp = TemplateConvert.convertToHpOdTemplate(vo);
        hpOdTemplateMapper.updateByPrimaryKeySelective(temp);
        return true;
    }

    @Processor(bussinessEnum = BusinessConstant.ADD_TEMP)
    public String createTemp(TemplateVO vo) {
        HpOdTemplate temp = TemplateConvert.convertToHpOdTemplate(vo);
        if (StringUtils.isEmpty(temp.getDisplayName())) {
            temp.setDisplayName(temp.getName());
        }
        hpOdTemplateMapper.insertSelective(temp);
        return temp.getGid();
    }

    @Processor(bussinessEnum = BusinessConstant.DEL_TEMP)
    public Boolean deleteTemp(List<String> ids) {
        for (String id : ids) {
            hpOdTemplateMapper.deleteByPrimaryKey(id);
        }
        return true;
    }
}
