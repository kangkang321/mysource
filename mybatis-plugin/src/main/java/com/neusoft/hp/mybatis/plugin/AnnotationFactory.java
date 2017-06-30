package com.neusoft.hp.mybatis.plugin;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin.ModelClassType;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import com.neusoft.core.util.ReflectionUtils;

public class AnnotationFactory {

    @SuppressWarnings("unchecked")
    public static String createGenTable(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        StringBuffer sb = new StringBuffer();
        sb.append("@GenTable(");
        sb.append("objId=\"");
        sb.append(introspectedTable.getTableConfigurationProperty("objId"));
        sb.append("\",mapperClass=");
        sb.append(introspectedTable.getMyBatis3JavaMapperType());
        sb.append(".class");
        sb.append(",tableName=\"");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        sb.append("\",gid=\"");
        sb.append(introspectedTable.getTableConfigurationProperty("gid"));
        sb.append("\",refKey=\"");
        String refKey = introspectedTable.getTableConfigurationProperty("refKey");
        if (StringUtils.isNoneBlank(refKey)) {
            sb.append(refKey);
        }
        sb.append("\",exampleClass=");
        sb.append(introspectedTable.getExampleType());
        sb.append(".class");
        if (introspectedTable.getTableConfigurationProperty("parentClass") != null) {
            sb.append(",parentClass=");
            sb.append(introspectedTable.getTableConfigurationProperty("parentClass"));
            sb.append(".class");
        }

        // object表新增owner列，@GenTable注解新增owner属性
        sb.append(",owner=\"");
        String owner = introspectedTable.getTableConfigurationProperty("owner");
        if (StringUtils.isNotBlank(owner)) {
            sb.append(owner);
        }
        sb.append("\"");

        String subTables = introspectedTable.getTableConfigurationProperty("subTables");
        if (StringUtils.isNotBlank(subTables)) {
            sb.append(",isSingle=false");
            String[] tables = StringUtils.split(subTables, ",");
            for (String t : tables) {
                if (StringUtils.isNotBlank(t)) {
                    List<IntrospectedTable> lists = (List<IntrospectedTable>) ReflectionUtils.getFieldValue(introspectedTable.getContext(),
                                                                                                            "introspectedTables");
                    lists.stream().filter(l -> StringUtils.equals(l.getFullyQualifiedTable().getIntrospectedTableName(),
                                                                  StringUtils.upperCase(t))).forEach(f -> {
                                                                      FullyQualifiedJavaType fieldType = new FullyQualifiedJavaType(List.class.getName());
                                                                      fieldType.addTypeArgument(new FullyQualifiedJavaType(f.getBaseRecordType()));
                                                                      Field field = new Field(JavaBeansUtil.getCamelCaseString(t,
                                                                                                                               false)
                                                                                              + "s", fieldType);
                                                                      field.setVisibility(JavaVisibility.PRIVATE);// FIXME
                                                                      field.addAnnotation(createSubTable(f));
                                                                      field.addAnnotation("@JSONField(name=\""
                                                                                          + f.getBaseRecordType()
                                                                                          + "\")");
                                                                      topLevelClass.addField(field);
                                                                      Method method = JavaBeansUtil.getJavaBeansGetter(createColumn(introspectedTable,
                                                                                                                                    field),
                                                                                                                       introspectedTable.getContext(),
                                                                                                                       introspectedTable);
                                                                      Method method2 = JavaBeansUtil.getJavaBeansSetter(createColumn(introspectedTable,
                                                                                                                                     field),
                                                                                                                        introspectedTable.getContext(),
                                                                                                                        introspectedTable);
                                                                      topLevelClass.addMethod(method);
                                                                      topLevelClass.addMethod(method2);
                                                                  });
                }
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static IntrospectedColumn createColumn(IntrospectedTable introspectedTable, Field field) {
        IntrospectedColumn introspectedColumn = new IntrospectedColumn();
        introspectedColumn.setFullyQualifiedJavaType(field.getType());
        introspectedColumn.setIntrospectedTable(introspectedTable);
        introspectedColumn.setJavaProperty(field.getName());
        return introspectedColumn;
    }

    public static String createGenColumn(Field field, TopLevelClass topLevelClass,
                                         IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
                                         ModelClassType modelClassType) {
        StringBuffer sb = new StringBuffer();
        // FIXME 后期考虑写主键生成策略
        if (StringUtils.isNotBlank(introspectedColumn.getProperties().getProperty("gid"))) {
            sb.append("@GenColumn(");
            sb.append("gid=\"");
            sb.append(introspectedColumn.getProperties().getProperty("gid"));
            sb.append("\",columnName=\"");
            sb.append(introspectedColumn.getActualColumnName());
            sb.append("\"");
            if (StringUtils.isNotBlank(introspectedColumn.getProperties().getProperty("type"))) {
                String type = introspectedColumn.getProperties().getProperty("type");
                sb.append(",type=\"");
                sb.append(type);
                sb.append("\"");
                if (StringUtils.equals(type, "1")) {
                    sb.append(",enumId=\"");
                    sb.append(introspectedColumn.getProperties().getProperty("value"));
                    sb.append("\"");
                } else if (StringUtils.equals(type, "2")) {
                    sb.append(",refObjectId=\"");
                    sb.append(introspectedColumn.getProperties().getProperty("value"));
                    sb.append("\"");
                }

            } else {
                sb.append(",type=\"0\"");
            }
            sb.append(")");
        }
        return sb.toString();
    }

    public static String createSubTable(IntrospectedTable introspectedTable) {
        StringBuffer sb = new StringBuffer();
        sb.append("@SubTable(");
        sb.append("subClass=");
        sb.append(introspectedTable.getBaseRecordType());
        sb.append(".class)");
        return sb.toString();
    }
}
