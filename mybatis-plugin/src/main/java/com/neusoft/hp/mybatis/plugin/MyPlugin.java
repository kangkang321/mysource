package com.neusoft.hp.mybatis.plugin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import com.neusoft.core.util.ReflectionUtils;

public class MyPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /* 第三顺序级 */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {

        return true;
    }

    /* 第一顺序级 */
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        field.addAnnotation(AnnotationFactory.createGenColumn(field, topLevelClass, introspectedColumn,
                                                              introspectedTable, modelClassType));
        return true;
    }

    /* 第二顺序级 */
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("com.neusoft.core.service.annotation.GenTable");
        topLevelClass.addImportedType("com.neusoft.core.service.annotation.GenColumn");
        topLevelClass.addImportedType("com.neusoft.core.service.annotation.SubTable");
        topLevelClass.addImportedType("com.alibaba.fastjson.annotation.JSONField");
        topLevelClass.addAnnotation(AnnotationFactory.createGenTable(topLevelClass, introspectedTable));
        return true;
    }

    /*
     * 没有它什么事情
     */
    public boolean providerGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.setSuperClass("com.neusoft.core.dao.GenExample");
        return true;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public boolean sqlMapBaseColumnListElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        XmlElement choose = new XmlElement("choose");
        XmlElement test = new XmlElement("when");
        test.addAttribute(new Attribute("test",
                                        "!_parameter.getClass().getName().equals(\'java.lang.String\') and selectItems != null and selectItems != '' and selectItems != 'null'"));
        test.addElement(new TextElement("${selectItems}"));
        choose.addElement(test);
        XmlElement other = new XmlElement("otherwise");
        ReflectionUtils.setFieldValue(other, "elements", element.getElements());
        choose.addElement(other);
        List elements = new ArrayList<>();
        elements.add(choose);
        ReflectionUtils.setFieldValue(element, "elements", elements);
        return true;
    }

    public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        XmlElement where = null;
        for (int i = 0; i < element.getElements().size(); i++) {
            if (element.getElements().get(i) instanceof XmlElement) {
                XmlElement e = (XmlElement) element.getElements().get(i);
                if ("where".equals(e.getName())) {
                    where = e;
                    break;
                }
            }
        }
        XmlElement test = new XmlElement("if");
        String id = element.getAttributes().stream().filter(a -> "id".equals(a.getName())).findFirst().get().getValue();
        if (StringUtils.equals(id, "Example_Where_Clause")) {
            test.addAttribute(new Attribute("test",
                                            "appendWhere != null and appendWhere != '' and appendWhere != 'null'"));
            test.addElement(new TextElement("and ${appendWhere}"));
        } else if (StringUtils.equals(id, "Update_By_Example_Where_Clause")) {
            test.addAttribute(new Attribute("test",
                                            "example.appendWhere != null and example.appendWhere != '' and example.appendWhere != 'null'"));
            test.addElement(new TextElement("and ${example.appendWhere}"));
        }
        where.addElement(0, new TextElement("IS_DELETE = 0"));
        where.addElement(1, test);
        XmlElement el = (XmlElement) where.getElements().get(2);
        XmlElement element2 = (XmlElement) el.getElements().get(0);
        element2.addElement(0, new TextElement("and"));
        return true;
    }

    /*
     * @Override public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable
     * introspectedTable) { List<Element> elements = new ArrayList<>(); TextElement select = new TextElement("select");
     * elements.add(select); XmlElement dis = new XmlElement("if"); dis.addAttribute(new Attribute("test", "distinct"));
     * dis.addElement(new TextElement("distinct")); elements.add(dis); XmlElement bl = new XmlElement("include");
     * bl.addAttribute(new Attribute("refid", "Base_Column_List")); elements.add(bl); String from = " from "+
     * introspectedTable.getFullyQualifiedTableNameAtRuntime() + " where IS_DELETE = 0"; elements.add(new
     * TextElement(from)); XmlElement para = new XmlElement("if"); para.addAttribute(new Attribute("test",
     * "_parameter != null")); XmlElement examplewhereclause = new XmlElement("include");
     * examplewhereclause.addAttribute(new Attribute("refid", "Example_Where_Clause"));
     * para.addElement(examplewhereclause); elements.add(para); XmlElement orderbyclause = new XmlElement("if");
     * orderbyclause.addAttribute(new Attribute("test", "orderByClause != null")); orderbyclause.addElement(new
     * TextElement("order by ${orderByClause}")); elements.add(orderbyclause); ReflectionUtils.setFieldValue(element,
     * "elements", elements); return true; }
     */

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,
                                                                  IntrospectedTable introspectedTable) {
        // TODO Auto-generated method stub
        return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<Element> elements = new ArrayList<>();
        TextElement select = new TextElement("select");
        elements.add(select);
        XmlElement bl = new XmlElement("include");
        bl.addAttribute(new Attribute("refid", "Base_Column_List"));
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        StringBuffer fromAndwhere = new StringBuffer();
        fromAndwhere.append(" from ").append(tableName).append(" where GID = #{gid,jdbcType=CHAR} and IS_DELETE = 0");
        elements.add(bl);
        elements.add(new TextElement(fromAndwhere.toString()));
        ReflectionUtils.setFieldValue(element, "elements", elements);
        return true;
    }

    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
        return new CreateFile().createXmlFile(introspectedTable, context);
    }

    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        return new CreateFile().createJavaFile(introspectedTable, context);
    }

}
