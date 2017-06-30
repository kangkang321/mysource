<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
  PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
  "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
	<#list list as context>
    <context id="${context.id}" targetRuntime="MyBatis3" defaultModelType="flat">
        <plugin type="org.mybatis.generator.plugins.EqualsHashCodePlugin"></plugin>  
        <plugin type="org.mybatis.generator.plugins.SerializablePlugin"></plugin> 
        <plugin type="org.mybatis.generator.plugins.ToStringPlugin"></plugin> 
        <plugin type="com.neusoft.hp.mybatis.plugin.MyPlugin"></plugin>
        <commentGenerator>
            <property name="suppressDate" value="true" />
            <property name="suppressAllComments" value="false" />
        </commentGenerator>
        <jdbcConnection driverClass="${driverClass}"
            connectionURL="${connectionURL}" userId="${userId}" password="${password}">
            </jdbcConnection>
        <javaTypeResolver>
            <property name="forceBigDecimals" value="false" />
        </javaTypeResolver>
        <javaModelGenerator targetPackage="${context.domainPackage}"
            targetProject="src/main/java">
            <property name="enableSubPackages" value="true" />
            <property name="rootClass" value="com.neusoft.core.dao.GenDO" />
            <property name="trimStrings" value="true" />
        </javaModelGenerator>
        <sqlMapGenerator targetPackage="mapper"
            targetProject="src/main/resources">
            <property name="enableSubPackages" value="true" />
        </sqlMapGenerator>
        <javaClientGenerator type="XMLMAPPER"
            targetPackage="${context.mapperPackage}" targetProject="src/main/java">
            <property name="enableSubPackages" value="true" />
            <property name="rootInterface" value="com.neusoft.core.dao.GenDao" />
        </javaClientGenerator>
        
        
 
		
		<#list context.list as table>
			
		
        <table tableName="${table.tableName}" domainObjectName="${table.className}">
        
				<#if (table.hpOdObjectGid) ?? >
					<property name="objId" value="${table.hpOdObjectGid}"/>
				</#if>
				<#if (table.gid) ?? >
					<property name="gid" value="${table.gid}"/>
				</#if>
				<#if (table.refKey) ??>
	        		<property name="refKey" value="${table.refKey}"/>
	        	</#if>
	        	<#if (table.parentClass) ??>
	        		<property name="parentClass" value="${table.parentClass}"/>
	        	</#if>
            <#if (table.owner) ??>
              <property name="owner" value="${table.owner}"/>
            </#if>
	        	<#if (table.subTables) ??>
	        		<property name="subTables" value="${table.subTables}"/>
	        	</#if>
            
          	
          	<generatedKey column="GID" sqlStatement="select SYS_GUID() from dual"/>
          	
          		<!-- 和主表的关联字段如何表示 -->
          	
          	<#list table.columns  as column>
          		<#if column.typeValue == "Boolean">
          		 	<columnOverride column="${column.code}" property="${column.fieldName}" javaType="${column.javaType}" typeHandler="com.neusoft.core.dao.wrapper.BooleanTypeHandler">
          		 		<property name="gid" value="${column.gid}"/> 
            			<property name="type" value="${column.dataType}"/>
            			<#if column.dataType != 0>
            				<property name="value" value="${column.typeValue}" />
            			</#if>
          		 	</columnOverride>
          		 <#else >
          		 	<columnOverride column="${column.code}" property="${column.fieldName}" javaType="${column.javaType}" >
          		 		<property name="gid" value="${column.gid}"/> 
            			<property name="type" value="${column.dataType}"/>
            			<#if column.dataType != 0>
            				<property name="value" value="${column.typeValue}" />
            			</#if>
          		 	</columnOverride>
          		</#if> 
          	</#list>	
          	
          	<columnOverride column="IS_ACTIVE" property="active" javaType="java.lang.Boolean" typeHandler="com.neusoft.core.dao.wrapper.BooleanTypeHandler"></columnOverride>
        	<columnOverride column="IS_DELETE" property="delete" javaType="java.lang.Boolean" typeHandler="com.neusoft.core.dao.wrapper.BooleanTypeHandler"></columnOverride>
          	
           
        </table>
        
        </#list>
        
        
    </context>
	</#list>
</generatorConfiguration>