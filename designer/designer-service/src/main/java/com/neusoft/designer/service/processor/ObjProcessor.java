package com.neusoft.designer.service.processor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusfot.designer.domain.vo.LayoutVO;
import com.neusoft.core.service.BaseProcessor;
import com.neusoft.core.service.annotation.Processor;
import com.neusoft.designer.service.domain.HpObjLayout;
import com.neusoft.designer.service.domain.HpObjLayoutExample;
import com.neusoft.designer.service.domain.constant.BusinessConstant;
import com.neusoft.designer.service.domain.convert.LayoutConvert;
import com.neusoft.designer.service.mapper.HpObjLayoutMapper;

// FIXME 重要节点发消息
/**
 * 类ObjProcessor.java的实现描述：对象设计器实体操作实现
 * 
 * @author Administrator 2017年3月16日 上午11:27:29
 */

@Deprecated
@Component
public class ObjProcessor extends BaseProcessor {

    // @SuppressWarnings("rawtypes")
    // @Resource(name = "stringRedisTemplate")
    // private RedisTemplate stringRedisTemplate;

    // @SuppressWarnings("rawtypes")
    // @Resource(name = "redisTemplate")
    // private RedisTemplate redisTemplate;

    @Autowired
    private HpObjLayoutMapper hpObjLayoutMapper;

    @Processor(bussinessEnum = BusinessConstant.ADD_LAYOUT)
    public String create(LayoutVO layout) throws IOException {
        HpObjLayout record = LayoutConvert.convertFromVO(layout);
        hpObjLayoutMapper.insert(record);
        // stringRedisTemplate.opsForHash().put(DesignerRedisEnum.DESIGNER_EXPORT_REDIS_OBJECT.getName(),
        // "obj:layoutid:obj:" + record.getGid(), record.getHpObjObjectGid());
        return record.getGid();
    }

    @Processor(bussinessEnum = BusinessConstant.MOD_LAYOUT)
    public Boolean modify(LayoutVO layout) throws IOException {
        HpObjLayout record = LayoutConvert.convertFromVO(layout);
        // FIXME 同一种类型只能有一个是默认模板
        if (null != record.getDf() && record.getDf()) {
            resetDf(record.getGid());
        }
        hpObjLayoutMapper.updateByPrimaryKeySelective(record);
        return true;
    }

    private void resetDf(String gid) {
        HpObjLayout notDf = new HpObjLayout();
        notDf.setDf(false);
        HpObjLayoutExample example = new HpObjLayoutExample();
        HpObjLayoutExample.Criteria c = example.createCriteria();
        HpObjLayout l = hpObjLayoutMapper.selectByPrimaryKey(gid);// 这里因为前台传过来的type可能不准确，所以去数据库捞一遍
        c.andTypeEqualTo(l.getType());
        hpObjLayoutMapper.updateByExampleSelective(notDf, example);
    }

    @Processor(bussinessEnum = BusinessConstant.DEL_LAYOUT)
    public Boolean delete(List<String> ids) {
        for (String id : ids) {
            hpObjLayoutMapper.deleteByPrimaryKey(id);
            // stringRedisTemplate.opsForHash().delete(DesignerRedisEnum.DESIGNER_EXPORT_REDIS_OBJECT.getName(),
            // "obj:layoutid:obj:" + id);
        }
        return true;
    }

    @Processor(bussinessEnum = BusinessConstant.SAVE_LAYOUT)
    public Boolean save(LayoutVO layout) throws IOException {
        HpObjLayout record = LayoutConvert.convertFromVO(layout);
        hpObjLayoutMapper.updateByPrimaryKeySelective(record);
        // stringRedisTemplate.opsForHash().put(DesignerRedisEnum.DESIGNER_EXPORT_REDIS_COLUMNS.getName(),
        // "obj:layoutid:column:" + layout.getId(), record.getFields());
        Map<String, Object> layouts = new HashMap<>();
        layouts.put("type", layout.getType());
        layouts.put("columns", layout.getColumns());
        // redisTemplate.opsForHash().put(DesignerRedisEnum.DESIGNER_EXPORT_REDIS_LAYOUT.getName(),
        // "obj:layoutid:layout:" + layout.getId(), layouts);
        return true;
    }
}
