package com.neusoft.hp.runtime.json;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeBeanInfo;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.neusoft.core.dao.GenDO;
import com.neusoft.core.dao.GenJoinDO;
import com.neusoft.core.service.annotation.SubTable;
import com.neusoft.core.util.ReflectionUtils;
import com.neusoft.hp.runtime.handler.HandlerContext;

/**
 * 类MySerializeConfigWrapper.java的实现描述：为了配合引用所做的修改
 * 
 * @author Administrator 2017年6月14日 下午5:30:56
 */
public class MySerializeConfigWrapper extends SerializeConfig {

    private static final SerializeConfig wrapper = SerializeConfig.globalInstance;

    private HandlerContext               context;

    public SerializeConfig getWrapper() {
        return wrapper;
    }

    public HandlerContext getContext() {
        return context;
    }

    public void setContext(HandlerContext context) {
        this.context = context;
    }

    public MySerializeConfigWrapper(HandlerContext context){
        this.context = context;
    }

    @Override
    public String getTypeKey() {
        return wrapper.getTypeKey();
    }

    @Override
    public void setTypeKey(String typeKey) {
        wrapper.setTypeKey(typeKey);
    }

    @Override
    public ObjectSerializer createJavaBeanSerializer(SerializeBeanInfo beanInfo) {
        FieldInfo[] fields = (FieldInfo[]) ReflectionUtils.getFieldValue(beanInfo, "fields");
        FieldInfo[] sortedFields = (FieldInfo[]) ReflectionUtils.getFieldValue(beanInfo, "sortedFields");
        SerializeBeanInfo newBeanInfo = new SerializeBeanInfo((Class<?>) ReflectionUtils.getFieldValue(beanInfo,
                                                                                                       "beanType"),
                                                              (JSONType) ReflectionUtils.getFieldValue(beanInfo,
                                                                                                       "jsonType"),
                                                              (String) ReflectionUtils.getFieldValue(beanInfo,
                                                                                                     "typeName"),
                                                              (int) ReflectionUtils.getFieldValue(beanInfo, "features"),
                                                              convertFieldInfo(fields, beanInfo),
                                                              convertFieldInfo(sortedFields, beanInfo));
        return super.createJavaBeanSerializer(newBeanInfo);
    }

    private FieldInfo[] convertFieldInfo(FieldInfo[] sources, SerializeBeanInfo beanInfo) {
        return Stream.of(sources).collect(Collectors.toMap(key -> key,
                                                           info -> getInfos(info, context,
                                                                            beanInfo))).values().stream().flatMap(Collection::stream).toArray(FieldInfo[]::new);

    }

    private List<FieldInfo> getInfos(FieldInfo info, HandlerContext context, SerializeBeanInfo beanInfo) {
        List<FieldInfo> infos = new ArrayList<>();
        if (isSkip(info, beanInfo)) {
            List<Annotation> annos = getAnnos(context.getStyle().getAnnotations(), info.field);
            if (!CollectionUtils.isEmpty(annos)) {
                annos.stream().forEach(anno -> infos.add(new MyFieldInfoWrapper(info, anno, this, beanInfo)));
            }
        } else {
            infos.add(info);
        }
        return infos;
    }

    private List<Annotation> getAnnos(Map<Field, List<Annotation>> maps, Field field) {
        for (Field f : maps.keySet()) {
            if (field.getDeclaringClass().getName().equals(f.getDeclaringClass().getName())
                && field.getName().equals(f.getName()) && field.getType().equals(f.getType())) {
                return maps.get(f);
            }
        }
        return null;
    }

    private boolean isSkip(FieldInfo field, SerializeBeanInfo beanInfo) {
        // GenJoinDO中
        if (GenJoinDO.class.isAssignableFrom(field.declaringClass)) {
            return false;
        }

        // 子类集合排除
        if (field.field.getAnnotation(SubTable.class) != null) {
            return false;
        }

        if (GenDO.class.equals(field.declaringClass)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isAsmEnable() {
        return wrapper.isAsmEnable();
    }

    @Override
    public void setAsmEnable(boolean asmEnable) {
        wrapper.setAsmEnable(asmEnable);
    }

    @Override
    public void addFilter(Class<?> clazz, SerializeFilter filter) {
        wrapper.addFilter(clazz, filter);
    }

    @Override
    public void config(Class<?> clazz, SerializerFeature feature, boolean value) {
        wrapper.config(clazz, feature, value);
    }

    @Override
    public ObjectSerializer getObjectWriter(Class<?> clazz) {
        // 因为是根据传递过来的参数序列化的，所以每次都不一样，每次都要重新生成
        if (GenDO.class.isAssignableFrom(clazz)) {
            return createJavaBeanSerializer(TypeUtils.buildBeanInfo(clazz, null, wrapper.propertyNamingStrategy));
        }
        return wrapper.getObjectWriter(clazz);
    }

    @Override
    public boolean put(Object type, Object value) {
        return wrapper.put(type, value);
    }

    @Override
    public boolean put(Type type, ObjectSerializer value) {
        return wrapper.put(type, value);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void configEnumAsJavaBean(Class<? extends Enum>... enumClasses) {
        wrapper.configEnumAsJavaBean(enumClasses);
    }

}
