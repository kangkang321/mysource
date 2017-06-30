package com.neusoft.hp.runtime.dyn;

import java.util.List;

import com.neusoft.core.dao.GenDO;
import com.neusoft.hp.runtime.dyn.bean.DynQueryBean;
import com.neusoft.hp.runtime.dyn.bean.DynStyleBean;

/**
 * 类DynBeanFactory.java的实现描述：模板保存的时候动态生成类 这个为什么放在这里，是因为对象设计器的classpath是没有GenDO的；放在这里也就决定了不能直接操作对象设计器的表
 * 
 * @author Administrator 2017年4月14日 上午11:39:42
 */
public interface DynBeanFactory {

    /**
     * @param selectedAttributes 模板属性
     * @param type 查询or列表
     * @param tmpId 模板id
     * @param domain 主对象
     */
    public DynStyleBean create(List<AttributeBean> selectedAttributes, String type, Class<? extends GenDO> domain);

    /**
     * @param selectedAttributes 模板属性
     * @param tmpId 模板id
     * @param clazz 多表关联查询时的接口
     */
    public DynQueryBean create(List<AttributeBean> selectedAttributes, Class<? extends GenDO> domain);

}
