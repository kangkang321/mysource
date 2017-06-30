package com.neusoft.hp.runtime.json;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.util.TypeUtils;

/**
 * 类MyJSON.java的实现描述：为了配合引用所做的修改
 * 
 * @author Administrator 2017年6月14日 下午5:30:38
 */
public class MyJSON extends JSON {

    @SuppressWarnings({ "unchecked", "deprecation" })
    public static Object toJSON(Object javaObject, SerializeConfig config) {
        if (javaObject == null) {
            return null;
        }

        if (javaObject instanceof JSON) {
            return javaObject;
        }

        if (javaObject instanceof Map) {
            Map<Object, Object> map = (Map<Object, Object>) javaObject;

            JSONObject json = new JSONObject(map.size());

            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Object key = entry.getKey();
                String jsonKey = TypeUtils.castToString(key);
                Object jsonValue = MyJSON.toJSON(entry.getValue(), config);
                json.put(jsonKey, jsonValue);
            }

            return json;
        }

        if (javaObject instanceof Collection) {
            Collection<Object> collection = (Collection<Object>) javaObject;
            JSONArray array = new JSONArray(collection.size());
            for (Object item : collection) {
                Object jsonValue = MyJSON.toJSON(item, config);
                array.add(jsonValue);
            }
            return array;
        }

        Class<?> clazz = javaObject.getClass();

        if (clazz.isEnum()) {
            return ((Enum<?>) javaObject).name();
        }

        if (clazz.isArray()) {
            int len = Array.getLength(javaObject);

            JSONArray array = new JSONArray(len);

            for (int i = 0; i < len; ++i) {
                Object item = Array.get(javaObject, i);
                Object jsonValue = MyJSON.toJSON(item, config);
                array.add(jsonValue);
            }

            return array;
        }

        if (ParserConfig.isPrimitive2(clazz)) {
            return javaObject;
        }
        // FIXME 如果是实现了GenDO的则用自己的序列化方式
        ObjectSerializer serializer = config.getObjectWriter(clazz);
        if (serializer instanceof JavaBeanSerializer) {
            JavaBeanSerializer javaBeanSerializer = (JavaBeanSerializer) serializer;

            JSONObject json = new JSONObject();
            try {
                Map<String, Object> values = javaBeanSerializer.getFieldValuesMap(javaObject);

                for (Map.Entry<String, Object> entry : values.entrySet()) {
                    json.put(entry.getKey(), MyJSON.toJSON(entry.getValue(), config));
                }
            } catch (Exception e) {
                throw new JSONException("toJSON error", e);
            }
            return json;
        }

        String text = JSON.toJSONString(javaObject);
        return JSON.parse(text);
    }
}
