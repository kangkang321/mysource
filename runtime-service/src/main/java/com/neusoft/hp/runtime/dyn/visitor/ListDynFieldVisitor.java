package com.neusoft.hp.runtime.dyn.visitor;

import com.neusoft.hp.runtime.dyn.visitor.element.EnumDynField;
import com.neusoft.hp.runtime.dyn.visitor.element.OriginalDynField;
import com.neusoft.hp.runtime.dyn.visitor.element.RefDynField;
import com.neusoft.hp.runtime.dyn.visitor.element.SubTableDynField;

/**
 * 类ListDynFieldVisitor.java的实现描述：列表模板用到
 * 
 * @author Administrator 2017年4月21日 下午2:49:12
 */
public class ListDynFieldVisitor implements DynFieldVisitor {

    @Override
    public void visitor(EnumDynField element) {
        element.makeForList();
    }

    @Override
    public void visitor(OriginalDynField element) {
        element.makeForList();
    }

    @Override
    public void visitor(RefDynField element) {
        element.makeForList();
    }

    @Override
    public void visitor(SubTableDynField element) {
        element.makeForList();
    }

}
