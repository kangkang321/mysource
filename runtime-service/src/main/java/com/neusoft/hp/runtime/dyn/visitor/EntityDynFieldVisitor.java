package com.neusoft.hp.runtime.dyn.visitor;

import com.neusoft.hp.runtime.dyn.visitor.element.EnumDynField;
import com.neusoft.hp.runtime.dyn.visitor.element.OriginalDynField;
import com.neusoft.hp.runtime.dyn.visitor.element.RefDynField;
import com.neusoft.hp.runtime.dyn.visitor.element.SubTableDynField;

/**
 * 类EntityDynFieldVisitor.java的实现描述：表单模板用到
 * 
 * @author Administrator 2017年4月21日 下午2:48:50
 */
public class EntityDynFieldVisitor implements DynFieldVisitor {

    @Override
    public void visitor(EnumDynField element) {
        element.makeForEntity();
    }

    @Override
    public void visitor(OriginalDynField element) {
        element.makeForEntity();
    }

    @Override
    public void visitor(RefDynField element) {
        element.makeForEntity();
    }

    @Override
    public void visitor(SubTableDynField element) {
        element.makeForEntity();
    }

}
