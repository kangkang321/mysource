package com.neusoft.hp.runtime.dyn.visitor;

import com.neusoft.hp.runtime.dyn.visitor.element.EnumDynField;
import com.neusoft.hp.runtime.dyn.visitor.element.OriginalDynField;
import com.neusoft.hp.runtime.dyn.visitor.element.RefDynField;
import com.neusoft.hp.runtime.dyn.visitor.element.SubTableDynField;

/**
 * 类QueryDynFieldVisitor.java的实现描述：查询模板用到
 * 
 * @author Administrator 2017年4月21日 下午2:49:25
 */
public class QueryDynFieldVisitor implements DynFieldVisitor {

    @Override
    public void visitor(EnumDynField element) {
        element.makeForQuery();
    }

    @Override
    public void visitor(OriginalDynField element) {
        element.makeForQuery();
    }

    @Override
    public void visitor(RefDynField element) {
        element.makeForQuery();
    }

    @Override
    public void visitor(SubTableDynField element) {
        element.makeForQuery();
    }

}
