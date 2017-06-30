package com.neusoft.hp.runtime.dyn.visitor;

import com.neusoft.hp.runtime.dyn.visitor.element.EnumDynField;
import com.neusoft.hp.runtime.dyn.visitor.element.OriginalDynField;
import com.neusoft.hp.runtime.dyn.visitor.element.RefDynField;
import com.neusoft.hp.runtime.dyn.visitor.element.SubTableDynField;

/**
 * 类DynFieldVisitor.java的实现描述：访问者
 * 
 * @author Administrator 2017年4月21日 下午2:48:35
 */
public interface DynFieldVisitor {

    public void visitor(EnumDynField element);

    public void visitor(OriginalDynField element);

    public void visitor(RefDynField element);

    public void visitor(SubTableDynField element);

}
