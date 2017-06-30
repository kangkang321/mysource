package com.neusoft.hp.mybatis.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import com.neusoft.core.dao.GenDao;
import com.neusoft.core.dao.GenJoinExample;
import com.neusoft.core.service.annotation.AppendWhere;
import com.neusoft.core.service.annotation.SelectItem;
import com.neusoft.core.util.ReflectionUtils;

/**
 * 类CreateFile.java的实现描述：文件生产
 * 
 * @author admin 2017年5月3日 上午10:14:26
 */
public class CreateFile {

    /**
     * xml文件创建
     */

    public List<GeneratedXmlFile> createXmlFile(IntrospectedTable introspectedTable, Context context) {
        List<GeneratedXmlFile> files = new ArrayList<>();
        String subTables = introspectedTable.getTableConfigurationProperty("subTables");
        if (StringUtils.isNotBlank(subTables)) {

            GeneratedXmlFile file = new GeneratedXmlFile(createDocument(introspectedTable,
                                                                        Stream.of(StringUtils.split(subTables,
                                                                                                    ",")).filter(StringUtils::isNotBlank).map(s -> {

                                                                                                        @SuppressWarnings("unchecked")
                                                                                                        List<IntrospectedTable> lists = (List<IntrospectedTable>) ReflectionUtils.getFieldValue(context,
                                                                                                                                                                                                "introspectedTables");
                                                                                                        for (IntrospectedTable table : lists) {
                                                                                                            if (StringUtils.equals(table.getFullyQualifiedTable().getIntrospectedTableName(),
                                                                                                                                   StringUtils.upperCase(s))) {
                                                                                                                return table;
                                                                                                            }
                                                                                                        }
                                                                                                        return null;
                                                                                                    }).collect(Collectors.toList()),
                                                                        context),
                                                         JavaBeansUtil.getCamelCaseString(introspectedTable.getFullyQualifiedTableNameAtRuntime(),
                                                                                          true)
                                                                                  + "$InnerMapper.xml",
                                                         context.getSqlMapGeneratorConfiguration().getTargetPackage(),
                                                         context.getSqlMapGeneratorConfiguration().getTargetProject(),
                                                         false, context.getXmlFormatter());
            files.add(file);
        }
        return files;
    }

    private Document createDocument(IntrospectedTable mainTable, List<IntrospectedTable> tables, Context context) {
        Document document = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
                                         XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
        document.setRootElement(getSqlMapElement(mainTable, tables, context));

        return document;
    }

    protected XmlElement getSqlMapElement(IntrospectedTable mainTable, List<IntrospectedTable> tables,
                                          Context context) {
        XmlElement answer = new XmlElement("mapper"); //$NON-NLS-1$
        String namespace = StringUtils.substring(mainTable.getMyBatis3SqlMapNamespace(), 0,
                                                 mainTable.getMyBatis3SqlMapNamespace().length() - "Mapper".length())
                           + "$InnerMapper";
        answer.addAttribute(new Attribute("namespace", //$NON-NLS-1$
                                          namespace));

        context.getCommentGenerator().addRootComment(answer);
        addResultMapWithBLOBsElement(mainTable, tables, answer);
        addSelectByExampleElement(mainTable, tables, answer);

        return answer;
    }

    protected void addResultMapWithBLOBsElement(IntrospectedTable mainTable, List<IntrospectedTable> tables,
                                                XmlElement parent) {
        XmlElement answer = new XmlElement("resultMap"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("id", //$NON-NLS-1$
                                          "BaseResultMap"));
        answer.addAttribute(new Attribute("type", mainTable.getBaseRecordType() + "$Inner"));
        mainTable.getAllColumns().forEach(column -> {
            XmlElement c = new XmlElement("result");
            // c.addAttribute(new Attribute("column",
            // mainTable.getFullyQualifiedTableNameAtRuntime() + "$"
            // + column.getActualColumnName()));
            c.addAttribute(new Attribute("column", column.getActualColumnName()));
            c.addAttribute(new Attribute("jdbcType", column.getJdbcTypeName()));
            c.addAttribute(new Attribute("property", column.getJavaProperty()));
            answer.addElement(c);
        });
        // 剔除resultMap中association子标签,如果需要可以打开
        /*XmlElement association1 = new XmlElement("association");
        association1.addAttribute(new Attribute("property",
                                                JavaBeansUtil.getCamelCaseString(mainTable.getFullyQualifiedTableNameAtRuntime(),
                                                                                 false)));
        association1.addAttribute(new Attribute("javaType", mainTable.getBaseRecordType()));
        mainTable.getAllColumns().forEach(column -> {
            XmlElement c = new XmlElement("result");
            // c.addAttribute(new Attribute("column",
            // mainTable.getFullyQualifiedTableNameAtRuntime() + "$"
            // + column.getActualColumnName()));
            c.addAttribute(new Attribute("column", "t" + "_" + column.getActualColumnName()));
            c.addAttribute(new Attribute("jdbcType", column.getJdbcTypeName()));
            c.addAttribute(new Attribute("property", column.getJavaProperty()));
            association1.addElement(c);
        });
        answer.addElement(association1);
        for (int i = 0; i < tables.size(); i++) {
            IntrospectedTable t = tables.get(i);
            XmlElement association = new XmlElement("association");
            association.addAttribute(new Attribute("property",
                                                   JavaBeansUtil.getCamelCaseString(t.getFullyQualifiedTable().getIntrospectedTableName(),
                                                                                    false)));
            association.addAttribute(new Attribute("javaType", t.getBaseRecordType()));
            for (int j = 0; j < t.getAllColumns().size(); j++) {
                XmlElement c = new XmlElement("result");
                c.addAttribute(new Attribute("column", "t" + i + "_" + t.getAllColumns().get(j).getActualColumnName()));
                c.addAttribute(new Attribute("jdbcType", t.getAllColumns().get(j).getJdbcTypeName()));
                c.addAttribute(new Attribute("property", t.getAllColumns().get(j).getJavaProperty()));
                association.addElement(c);
            }
            answer.addElement(association);
        }*/
        parent.addElement(answer);

    }

    
/*    protected void addSelectByExampleElement(IntrospectedTable mainTable, List<IntrospectedTable> tables,
                                             XmlElement parent) {
        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", "selectByExample"));
        select.addAttribute(new Attribute("parameterType", mainTable.getExampleType() + "$Inner"));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
         XmlElement choose = new XmlElement("choose");
         select.addElement(choose);
         XmlElement when = new XmlElement("when");
         choose.addElement(when);
         when.addAttribute(new Attribute("test", "_parameter == null"));
         FIXME 动态显示列的有些问题没有想明白暂时不处理

        for (int i = 0; i < tables.size(); i++) {
            IntrospectedTable table = tables.get(i);
            select.addElement(new TextElement(getSelect(mainTable, tables, table)));
             XmlElement when2 = new XmlElement("when");
             choose.addElement(when2);
             when2.addAttribute(new Attribute("test",
             JavaBeansUtil.getCamelCaseString(mainTable.getFullyQualifiedTable().getIntrospectedTableName()
             + "SelectItems", false)
             + " != null || "
             + JavaBeansUtil.getCamelCaseString(table.getFullyQualifiedTable().getIntrospectedTableName()
             + "SelectItems", false)
             + " != null"));
             when2.addElement(new TextElement(getSelect2(mainTable, tables, table)));
            XmlElement where = new XmlElement("where");
            select.addElement(where);
            String refKey = table.getTableConfigurationProperty("refKey");
            where.addElement(new TextElement(mainTable.getFullyQualifiedTableNameAtRuntime() + ".GID = "
                                             + table.getFullyQualifiedTable().getIntrospectedTableName() + "."
                                             + refKey));
            XmlElement test = new XmlElement("if");
            test.addAttribute(new Attribute("test",
                                            JavaBeansUtil.getCamelCaseString(mainTable.getFullyQualifiedTable().getIntrospectedTableName()
                                                                             + "AppendWhere", false)
                                                    + " != null and "
                                                    + JavaBeansUtil.getCamelCaseString(mainTable.getFullyQualifiedTable().getIntrospectedTableName()
                                                                                       + "AppendWhere", false)
                                                    + " != '' and "
                                                    + JavaBeansUtil.getCamelCaseString(mainTable.getFullyQualifiedTable().getIntrospectedTableName()
                                                                                       + "AppendWhere", false)
                                                    + " != 'null'"));
            test.addElement(new TextElement("and ${"
                                            + JavaBeansUtil.getCamelCaseString(mainTable.getFullyQualifiedTable().getIntrospectedTableName()
                                                                               + "AppendWhere", false)
                                            + "}"));

            where.addElement(test);
            XmlElement test2 = new XmlElement("if");
            test2.addAttribute(new Attribute("test",
                                             JavaBeansUtil.getCamelCaseString(table.getFullyQualifiedTable().getIntrospectedTableName()
                                                                              + "AppendWhere", false)
                                                     + " != null and "
                                                     + JavaBeansUtil.getCamelCaseString(table.getFullyQualifiedTable().getIntrospectedTableName()
                                                                                        + "AppendWhere", false)
                                                     + " != '' and "
                                                     + JavaBeansUtil.getCamelCaseString(table.getFullyQualifiedTable().getIntrospectedTableName()
                                                                                        + "AppendWhere", false)
                                                     + " != 'null'"));
            test2.addElement(new TextElement("and ${"
                                             + JavaBeansUtil.getCamelCaseString(table.getFullyQualifiedTable().getIntrospectedTableName()
                                                                                + "AppendWhere", false)
                                             + "}"));
            where.addElement(test2);
            select.addElement(new TextElement(" UNION ALL "));

        }
        select.getElements().remove(select.getElements().size() - 1);
        parent.addElement(select);
    }*/
    
    
    //addSelectByExampleElement改造sql语句查询主表，返回主表，不返回子表的任何结果（sql语句中加入exists关键字）
    protected void addSelectByExampleElement(IntrospectedTable mainTable, List<IntrospectedTable> tables,
                                             XmlElement parent) {
        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", "selectByExample"));
        select.addAttribute(new Attribute("parameterType", mainTable.getExampleType() + "$Inner"));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
    
        select.addElement(new TextElement(getSelectMainTable(mainTable)));
        XmlElement where = new XmlElement("where");
        select.addElement(where);
        //改造sql ，不需要关联条件
        /* String refKey = table.getTableConfigurationProperty("refKey");
        where.addElement(new TextElement(mainTable.getFullyQualifiedTableNameAtRuntime() + ".GID = "
                                         + table.getFullyQualifiedTable().getIntrospectedTableName() + "."
                                         + refKey));*/
        
		where.addElement(new TextElement(" is_delete = 0 "));
        XmlElement test = new XmlElement("if");
        test.addAttribute(new Attribute("test",
                                        JavaBeansUtil.getCamelCaseString(mainTable.getFullyQualifiedTable().getIntrospectedTableName()
                                                                         + "AppendWhere", false)
                                        + " != null and "
                                        + JavaBeansUtil.getCamelCaseString(mainTable.getFullyQualifiedTable().getIntrospectedTableName()
                                                                           + "AppendWhere", false)
                                        + " != '' and "
                                        + JavaBeansUtil.getCamelCaseString(mainTable.getFullyQualifiedTable().getIntrospectedTableName()
                                                                           + "AppendWhere", false)
                                        + " != 'null'"));
        test.addElement(new TextElement("and ${"
                + JavaBeansUtil.getCamelCaseString(mainTable.getFullyQualifiedTable().getIntrospectedTableName()
                                                   + "AppendWhere", false)
                + "}"));
        where.addElement(test);
        for (int i = 0; i < tables.size(); i++) {
            IntrospectedTable table = tables.get(i);
           
            XmlElement test2 = new XmlElement("if");
            test2.addAttribute(new Attribute("test",
                                             JavaBeansUtil.getCamelCaseString(table.getFullyQualifiedTable().getIntrospectedTableName()
                                                                              + "AppendWhere", false)
                                             + " != null and "
                                             + JavaBeansUtil.getCamelCaseString(table.getFullyQualifiedTable().getIntrospectedTableName()
                                                                                + "AppendWhere", false)
                                             + " != '' and "
                                             + JavaBeansUtil.getCamelCaseString(table.getFullyQualifiedTable().getIntrospectedTableName()
                                                                                + "AppendWhere", false)
                                             + " != 'null'"));
         
            //and exists (select 1 from 子表 where 子表.外键=主表的外键 and appendWhere子表)
            String refKey = table.getTableConfigurationProperty("refKey");
            test2.addElement(new TextElement(" and exists( select 1 from " + table.getFullyQualifiedTable().getIntrospectedTableName() + " where " 
                                             + table.getFullyQualifiedTable().getIntrospectedTableName() + "."
                                             + refKey + " = "
                                             + mainTable.getFullyQualifiedTableNameAtRuntime() + ".GID " 
                                             + " and ${"
                                             + JavaBeansUtil.getCamelCaseString(table.getFullyQualifiedTable().getIntrospectedTableName()
                                                                                + "AppendWhere", false)
                                             + "})"));
            where.addElement(test2);
            
        }
        
        XmlElement order = new XmlElement("if");
        order.addAttribute(new Attribute("test", "orderByClause != null"));
        order.addElement(new TextElement("order by ${orderByClause}"));
        select.addElement(order);
        parent.addElement(select);
    }

    private String getSelectMainTable(IntrospectedTable mainTable){
        StringBuffer sb = new StringBuffer();
        mainTable.getAllColumns().forEach(c -> {
            sb.append(",");
            sb.append(mainTable.getFullyQualifiedTableNameAtRuntime());
            sb.append(".");
            sb.append(c.getActualColumnName());
        /*  sb.append(" AS ");
            sb.append("t");
            sb.append("_");
            sb.append(c.getActualColumnName());*/
        });
        
        return "SELECT " + sb.substring(1) + " FROM " + mainTable.getFullyQualifiedTableNameAtRuntime() /*+ "," + curr.getFullyQualifiedTable().getIntrospectedTableName()*/;//只查主表
    }
    
    private String getSelect(IntrospectedTable mainTable, List<IntrospectedTable> tables, IntrospectedTable curr) {
        StringBuffer sb = new StringBuffer();
        mainTable.getAllColumns().forEach(c -> {
            sb.append(",");
            sb.append(mainTable.getFullyQualifiedTableNameAtRuntime());
            sb.append(".");
            sb.append(c.getActualColumnName());
            sb.append(" AS ");
            sb.append("t");
            sb.append("_");
            sb.append(c.getActualColumnName());
        });
        
       
        for (int i = 0; i < tables.size(); i++) {
            IntrospectedTable t = tables.get(i);
            String s = "t" + i;
            if (t == curr) {

                t.getAllColumns().forEach(c -> {
                    sb.append(",");
                    sb.append(t.getFullyQualifiedTable().getIntrospectedTableName());
                    sb.append(".");
                    sb.append(c.getActualColumnName());
                    sb.append(" AS ");
                    sb.append(s);
                    sb.append("_");
                    sb.append(c.getActualColumnName());
                });
            } else {
                t.getAllColumns().forEach(c -> {
                    sb.append(",NULL AS ");
                    sb.append(s);
                    sb.append("_");
                    sb.append(c.getActualColumnName());
                });
            }
        }
        return "SELECT " + sb.substring(1) + " FROM " + mainTable.getFullyQualifiedTableNameAtRuntime() + "," + curr.getFullyQualifiedTable().getIntrospectedTableName();
    }

    private String getSelect2(IntrospectedTable mainTable, List<IntrospectedTable> tables, IntrospectedTable curr) {
        StringBuffer sb = new StringBuffer();
        sb.append("${");
        sb.append(JavaBeansUtil.getCamelCaseString(mainTable.getFullyQualifiedTable().getIntrospectedTableName()
                                                   + "SelectItems", false)
                  + "}");
        sb.append(" ${");
        sb.append(JavaBeansUtil.getCamelCaseString(curr.getFullyQualifiedTable().getIntrospectedTableName()
                                                   + "SelectItems", false)
                  + "}");

        return "SELECT " + sb.toString() + " FROM " + mainTable.getFullyQualifiedTableNameAtRuntime() + ","
               + curr.getFullyQualifiedTable().getIntrospectedTableName();
    }

    /**
     * java文件创建
     */

    public List<GeneratedJavaFile> createJavaFile(IntrospectedTable introspectedTable, Context context) {
        List<GeneratedJavaFile> files = new ArrayList<>();
        String subTables = introspectedTable.getTableConfigurationProperty("subTables");
        if (StringUtils.isNotBlank(subTables)) {
            @SuppressWarnings("unchecked")
            List<IntrospectedTable> lists = (List<IntrospectedTable>) ReflectionUtils.getFieldValue(context,
                                                                                                    "introspectedTables");
            List<IntrospectedTable> tables = lists.stream().filter(l -> {
                for (String s : StringUtils.split(subTables, ",")) {
                    if (StringUtils.isNotBlank(s)
                        && StringUtils.equalsIgnoreCase(s, l.getFullyQualifiedTable().getIntrospectedTableName())) {
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
            files.add(new GeneratedJavaFile(createDao(introspectedTable, tables),
                                            context.getJavaClientGeneratorConfiguration().getTargetProject(),
                                            context.getJavaFormatter()));
            //  不生成$inner domain类 ,可以注释方法。
            TopLevelClass levelClass = createDO(introspectedTable, tables);
            files.add(new GeneratedJavaFile(levelClass,
                                            context.getJavaModelGeneratorConfiguration().getTargetProject(),
                                            context.getJavaFormatter()));
            files.add(new GeneratedJavaFile(createExample(introspectedTable, tables),
                                            context.getJavaModelGeneratorConfiguration().getTargetProject(),
                                            context.getJavaFormatter()));

        }
        return files;
    }

    private Interface createDao(IntrospectedTable mainTable, List<IntrospectedTable> tables) {
        Interface interfaze = new Interface(StringUtils.substring(mainTable.getMyBatis3SqlMapNamespace(), 0,
                                                                  mainTable.getMyBatis3SqlMapNamespace().length()
                                                                                                             - "Mapper".length())
                                            + "$InnerMapper");
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        interfaze.addSuperInterface(new FullyQualifiedJavaType(GenDao.class.getName()));
        interfaze.addImportedType(new FullyQualifiedJavaType(GenDao.class.getName()));
        interfaze.addImportedType(new FullyQualifiedJavaType(mainTable.getExampleType() + "$Inner"));
        interfaze.addImportedType(new FullyQualifiedJavaType(mainTable.getBaseRecordType()/* + "$Inner"*/));

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(mainTable.getBaseRecordType() /*+ "$Inner"*/); //  $inner domain类 已被剔除，修改$InnerMapper接口中selectByExample方法的返回值为主表类型。
        returnType.addTypeArgument(listType);
        method.setReturnType(returnType);
        method.setName("selectByExample");
        method.addParameter(new Parameter(new FullyQualifiedJavaType(mainTable.getExampleType() + "$Inner"),
                                          "example"));
        interfaze.addMethod(method);
        return interfaze;
    }

   /* public TopLevelClass createDO(IntrospectedTable mainTable, List<IntrospectedTable> tables) {
        TopLevelClass clazz = new TopLevelClass(new FullyQualifiedJavaType(mainTable.getBaseRecordType() + "$Inner"));
        clazz.setVisibility(JavaVisibility.PUBLIC);
        clazz.addImportedType(mainTable.getBaseRecordType());
        StringBuffer sb = new StringBuffer();
        sb.append("@GenTable(");
        sb.append("objId=\"");
        sb.append(mainTable.getTableConfigurationProperty("objId"));
        sb.append("\",mapperClass=");
        sb.append(StringUtils.substring(mainTable.getMyBatis3SqlMapNamespace(), 0,
                                        mainTable.getMyBatis3SqlMapNamespace().length() - "Mapper".length())
                  + "$InnerMapper");
        sb.append(".class");
        sb.append(",gid=\"");
        sb.append("\",exampleClass=");
        sb.append(mainTable.getExampleType());
        sb.append("$Inner.class");
        sb.append(",tableName=\"");
        sb.append("\")");
        clazz.addAnnotation(sb.toString());
        Field f = new Field();
        f.setName(JavaBeansUtil.getCamelCaseString(mainTable.getFullyQualifiedTableNameAtRuntime(), false));
        f.setType(new FullyQualifiedJavaType(mainTable.getBaseRecordType()));
        f.setVisibility(JavaVisibility.PUBLIC);
        clazz.addField(f);
        tables.forEach(t -> {
            clazz.addImportedType(t.getBaseRecordType());
            Field f1 = new Field();
            f1.setName(JavaBeansUtil.getCamelCaseString(t.getFullyQualifiedTable().getIntrospectedTableName(), false));
            f1.setType(new FullyQualifiedJavaType(t.getBaseRecordType()));
            f1.setVisibility(JavaVisibility.PUBLIC);
            clazz.addField(f1);
        });
        clazz.setSuperClass(GenJoinDO.class.getName());
        return clazz;
    }*/
    /**
     * $Inner domain类修改成
     * 与主表的domain字段一致,直接extends 主表，implements GenJoinDO
     * @param mainTable
     * @param tables
     * @return
     */
    public TopLevelClass createDO(IntrospectedTable mainTable,List<IntrospectedTable> tables) {
        TopLevelClass clazz = new TopLevelClass(new FullyQualifiedJavaType(mainTable.getBaseRecordType() + "$Inner"));
        clazz.setVisibility(JavaVisibility.PUBLIC);
        clazz.addImportedType(mainTable.getBaseRecordType());
        clazz.addImportedType("com.neusoft.core.service.annotation.GenTable");
//        clazz.addImportedType("com.neusoft.core.service.annotation.SubTable");
        StringBuffer sb = new StringBuffer();
        sb.append("@GenTable(");
        sb.append("objId=\"");
        sb.append(mainTable.getTableConfigurationProperty("objId"));
        sb.append("\",mapperClass=");
        sb.append(StringUtils.substring(mainTable.getMyBatis3SqlMapNamespace(), 0,
                                        mainTable.getMyBatis3SqlMapNamespace().length() - "Mapper".length())
                  + "$InnerMapper");
        sb.append(".class");
        sb.append(",gid=\"");
        sb.append("\",exampleClass=");
        sb.append(mainTable.getExampleType());
        sb.append("$Inner.class");
        sb.append(",tableName=\"");
        sb.append("\")");
        clazz.addAnnotation(sb.toString());
        
       
/*        mainTable.getBaseColumns().forEach(c -> {
        	clazz.addImportedType(c.getFullyQualifiedJavaType());
            Field f1 = new Field();
            f1.setName(JavaBeansUtil.getCamelCaseString(c.getActualColumnName(), false));
            f1.setType(c.getFullyQualifiedJavaType());
            f1.setVisibility(JavaVisibility.PRIVATE);
            clazz.addField(f1);
            Method method = JavaBeansUtil.getJavaBeansGetter(AnnotationFactory.createColumn(mainTable,f1),mainTable.getContext(),mainTable);
        	Method method2 = JavaBeansUtil.getJavaBeansSetter(AnnotationFactory.createColumn(mainTable,f1),mainTable.getContext(),mainTable);
        	clazz.addMethod(method);
        	clazz.addMethod(method2);
        });
        
        tables.forEach(t -> {
        	// @SubTable(subClass=com.neusoft.designer.runtime.domain.HpArMaterielLogistics.class)
        	clazz.addImportedType(t.getIbatis2SqlMapNamespace());
        	StringBuffer sub = new StringBuffer();
        	sub.append("@SubTable(");
        	sub.append("subClass=");
        	sub.append(StringUtils.substring(t.getMyBatis3SqlMapNamespace(), 0,
                                            t.getMyBatis3SqlMapNamespace().length() - "Mapper".length())
);
        	sub.append(".class");
        	sub.append(")");
            clazz.addImportedType(t.getBaseRecordType());
            
            Field f1 = new Field();
            f1.setName(JavaBeansUtil.getCamelCaseString(t.getFullyQualifiedTable().getIntrospectedTableName(), false));
            
            FullyQualifiedJavaType fieldType = new FullyQualifiedJavaType(List.class.getName());
            fieldType.addTypeArgument(new FullyQualifiedJavaType(t.getBaseRecordType()));
            Field field = new Field(JavaBeansUtil.getCamelCaseString(t.getBaseRecordType(),
                                                                     false),
                                    fieldType);
            f1.setType(fieldType);
            f1.setVisibility(JavaVisibility.PRIVATE);
            f1.addAnnotation(sub.toString());
            clazz.addField(f1);
            
            Method method = JavaBeansUtil.getJavaBeansGetter(AnnotationFactory.createColumn(t,f1),t.getContext(),t);
			Method method2 = JavaBeansUtil.getJavaBeansSetter(AnnotationFactory.createColumn(t,f1),t.getContext(),t);
			clazz.addMethod(method);
			clazz.addMethod(method2);
        });*/
        
        clazz.setSuperClass(mainTable.getBaseRecordType());
        clazz.addSuperInterface(new FullyQualifiedJavaType("com.neusoft.core.dao.GenJoinDO"));
        return clazz;
    }

    public TopLevelClass createExample(IntrospectedTable mainTable, List<IntrospectedTable> tables) {
        TopLevelClass clazz = new TopLevelClass(new FullyQualifiedJavaType(mainTable.getExampleType() + "$Inner"));
        clazz.addImportedType(new FullyQualifiedJavaType(SelectItem.class.getName()));
        clazz.addImportedType(new FullyQualifiedJavaType(AppendWhere.class.getName()));
        clazz.setVisibility(JavaVisibility.PUBLIC);
        Field fs = new Field();
        Field fw = new Field();
        fs.setName(JavaBeansUtil.getCamelCaseString(mainTable.getFullyQualifiedTableNameAtRuntime() + "SelectItems",
                                                    false));
        fs.setType(new FullyQualifiedJavaType(String.class.getName()));
        fs.setVisibility(JavaVisibility.PUBLIC);
        fs.addAnnotation("@SelectItem(tableName=\"" + mainTable.getFullyQualifiedTableNameAtRuntime()
                         + "\",placeHolder=true)");
        fw.setName(JavaBeansUtil.getCamelCaseString(mainTable.getFullyQualifiedTableNameAtRuntime() + "AppendWhere",
                                                    false));
        fw.setType(new FullyQualifiedJavaType(String.class.getName()));
        fw.setVisibility(JavaVisibility.PUBLIC);
        fw.addAnnotation("@AppendWhere(tableName=\"" + mainTable.getFullyQualifiedTableNameAtRuntime() + "\")");
        clazz.addField(fs);
        clazz.addField(fw);
        tables.forEach(t -> {
            Field fs1 = new Field();
            Field fw1 = new Field();
            fs1.setName(JavaBeansUtil.getCamelCaseString(t.getFullyQualifiedTable().getIntrospectedTableName()
                                                         + "SelectItems", false));
            fs1.setType(new FullyQualifiedJavaType(String.class.getName()));
            fs1.setVisibility(JavaVisibility.PUBLIC);
            fs1.addAnnotation("@SelectItem(tableName=\"" + t.getFullyQualifiedTable().getIntrospectedTableName()
                              + "\",placeHolder=true)");
            fw1.setName(JavaBeansUtil.getCamelCaseString(t.getFullyQualifiedTable().getIntrospectedTableName()
                                                         + "AppendWhere", false));
            fw1.setType(new FullyQualifiedJavaType(String.class.getName()));
            fw1.setVisibility(JavaVisibility.PUBLIC);
            fw1.addAnnotation("@AppendWhere(tableName=\"" + t.getFullyQualifiedTable().getIntrospectedTableName()
                              + "\")");
            clazz.addField(fs1);
            clazz.addField(fw1);

            Field fs2 = new Field();
            fs2.setName(JavaBeansUtil.getCamelCaseString(t.getFullyQualifiedTable().getIntrospectedTableName()
                                                         + "Holder", false));
            fs2.setType(new FullyQualifiedJavaType(String.class.getName()));
            fs2.setVisibility(JavaVisibility.PUBLIC);
            fs2.addAnnotation("@SelectItem(tableName=\"" + t.getFullyQualifiedTable().getIntrospectedTableName()
                              + "\",placeHolder=false)");
            clazz.addField(fs2);
        });
        
        Field orderByClauseField = new Field();
        orderByClauseField.setName("orderByClause");
        orderByClauseField.setVisibility(JavaVisibility.PROTECTED);
        orderByClauseField.setType(new FullyQualifiedJavaType(String.class.getName()));
        clazz.addField(orderByClauseField);
        clazz.setSuperClass(GenJoinExample.class.getName());
        return clazz;
    }

}
