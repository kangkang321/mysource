package com.neusoft.hp.runtime.json;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializeBeanInfo;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.service.annotation.GenColumn;
import com.neusoft.core.service.annotation.GenTable;
import com.neusoft.core.service.annotation.SubTable;
import com.neusoft.core.util.M2Util;
import com.neusoft.core.util.ReflectionUtils;
import com.neusoft.hp.runtime.client.QuerySqlByOwner;
import com.neusoft.hp.runtime.dyn.annotation.EnumValue;
import com.neusoft.hp.runtime.dyn.annotation.OriginalValue;
import com.neusoft.hp.runtime.dyn.annotation.RefValue;
import com.neusoft.md.dictionary.client.IDictEnumValueService;
import com.neusoft.md.dictionary.dto.MdDictionaryEnumValueDTO;

/**
 * 类MyFieldInfoWrapper.java的实现描述 在这里处理引用之类的事情
 * 
 * @author Administrator 2017年6月14日 下午5:29:03
 */
public class MyFieldInfoWrapper extends FieldInfo {

    private Annotation        annotation;

    private FieldInfo         wrapper;

    private SerializeConfig   config;

    @SuppressWarnings("unused")
    private SerializeBeanInfo bean;

    public MyFieldInfoWrapper(FieldInfo info, Annotation annotation, SerializeConfig config, SerializeBeanInfo bean){
        super(getName(info, annotation), info.method, info.field, info.fieldClass, info.fieldType,
              (int) ReflectionUtils.getFieldValue(info, "ordinal"), info.serialzeFeatures, info.parserFeatures,
              (JSONField) ReflectionUtils.getFieldValue(info, "fieldAnnotation"),
              (JSONField) ReflectionUtils.getFieldValue(info, "methodAnnotation"), info.label);
        this.annotation = annotation;
        this.wrapper = info;
        this.config = config;
        this.bean = bean;
    }

    private static String getName(FieldInfo info, Annotation annotation) {
        if (annotation instanceof OriginalValue) {
            return ((OriginalValue) annotation).displayName();
        } else if (annotation instanceof RefValue) {
            return ((RefValue) annotation).displayName();
        } else if (annotation instanceof EnumValue) {
            return ((EnumValue) annotation).displayName();
        } else {
            return info.name;
        }
    }

    @Override
    public <T extends Annotation> T getAnnation(Class<T> annotationClass) {
        return wrapper.getAnnation(annotationClass);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Member getMember() {
        return wrapper.getMember();
    }

    @Override
    public int compareTo(FieldInfo o) {
        return super.compareTo(o);
    }

    @Override
    public String getFormat() {
        return wrapper.getFormat();
    }

    @Override
    public Object get(Object javaObject) throws IllegalAccessException, InvocationTargetException {
        Object original = super.get(javaObject);
        if (annotation instanceof OriginalValue) {
        } else if (annotation instanceof RefValue) {
            // FIXME 通过sql拿，后期可能会有问题，如果多个引用对象并不在一个数据库怎么办？
            // 原始的，用过一条sql，一次性查询出引用的结果。
            /*
             * if (config instanceof MySerializeConfigWrapper) { MySerializeConfigWrapper wrapper =
             * (MySerializeConfigWrapper) config; String sql = StringUtils.replace(((RefValue) annotation).sql(),
             * SysConstants.REF, "'" + String.valueOf(original) + "'"); String valBean =
             * wrapper.getContext().getApplicationContext().getBean(DynSqlMapper.class).selectValue(sql); return valBean
             * == null ? "" : valBean; }
             */

            /**
             * @author wuzhou 1：单表多层级的引用。 path:A$a,B$b,C$c
             * PCar$p_shoe_gid,PShoe$p_material_gid,PMaterial$p_source_gid,PSource$p_address_gid,PAddress$address
             * 2：引用了主从表
             * PCar$p_shoe_gid,PShoe$p_material_gid,PMaterial$hp_ar_materiel_gid,（主表）HpArMateriel$hpArMaterielQuality,（从表）HpArMaterielQuality$testMethod
             * 通过多次的单表查询来查引用
             */

            if (config instanceof MySerializeConfigWrapper) {
                MySerializeConfigWrapper wrapper = (MySerializeConfigWrapper) config;

                String result = String.valueOf(original);

                String path = ((RefValue) annotation).path();

                String[] strs = path.split(",");
                List<Field> fields = new ArrayList<>();
                for (String string : strs) {
                    try {
                        // M2Util
                        fields.add(M2Util.getClass(StringUtils.substringBefore(string,
                                                                               "$")).get().getDeclaredField(StringUtils.substringAfter(string,
                                                                                                                                       "$")));
                    } catch (NoSuchFieldException | SecurityException e) {
                        throw new BizException(e);
                    }
                }

                for (int i = 1; i < fields.size(); i++) {
                    // 初始表不用查，A$a a是作为B$b表的gid，引用的都是另一个实体的gid。
                    // 生成sql语句并执行，返回结果到result

                    if (StringUtils.isBlank(result)) {
                        break;
                    }
                    result = recursion(fields.get(i), wrapper, result);

                }

                return result == null ? null : result;
            }
        } else if (annotation instanceof EnumValue) {
            if (config instanceof MySerializeConfigWrapper) {

                if (config instanceof MySerializeConfigWrapper) {
                    MdDictionaryEnumValueDTO enumValueDTO = ((MySerializeConfigWrapper) config).getContext().getApplicationContext().getBean(IDictEnumValueService.class).queryById(String.valueOf(original));
                    if (enumValueDTO != null) {
                        return enumValueDTO.getVal();
                    }
                    return "";
                }
            }
        }
        return original;
    }

    // 查询引用时，根据path中的Field来生成sql,并执行单表的查询
    private String recursion(Field field, MySerializeConfigWrapper wrapper, String result) {
        String res = null;
        GenTable table = field.getDeclaringClass().getAnnotation(GenTable.class);
        String tableName = table.tableName();
        GenColumn column = field.getAnnotation(GenColumn.class);
        String columnName = column.columnName();

        //如果直接查询的是主表的列,没有继续引用子表中的列
        if (!table.isSingle() && null != field.getAnnotation(SubTable.class)) {
        	
            // 如果被引用的是多表的情况，查询主表直接跳过，执行子表的查询。
            return res;
        }
        // 引用对象是多表时，直接用子表的refKey = 上次查询返回的结果->（被引用主表的gid）。
        String sql = null;
        if (StringUtils.isNotBlank(table.refKey())) {
            sql = "select " + tableName + "." + columnName + " from " + tableName + " where " + tableName + "."
                  + table.refKey() + " = " + "'" + result + "'";

        } else {
            sql = "select " + tableName + "." + columnName + " from " + tableName + " where " + tableName + ".Gid = "
                  + "'" + result + "'";
        }
        try {
            // querySql接口：可以进行分库的查询
            res = wrapper.getContext().getApplicationContext().getBean(QuerySqlByOwner.class).querySqlValue(sql,
                                                                                                            table.owner());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void set(Object javaObject, Object value) throws IllegalAccessException, InvocationTargetException {
        wrapper.set(javaObject, value);
    }

    @Override
    public void setAccessible() throws SecurityException {
        wrapper.setAccessible();
    }

    @Override
    public boolean alternateName(String name) {
        return wrapper.alternateName(name);
    }

}
