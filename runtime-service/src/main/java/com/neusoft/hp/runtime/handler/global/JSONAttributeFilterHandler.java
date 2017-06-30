package com.neusoft.hp.runtime.handler.global;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.core.dao.GenDO;
import com.neusoft.core.dao.GenJoinDO;
import com.neusoft.core.service.annotation.GenTable;
import com.neusoft.hp.runtime.dyn.annotation.EnumValue;
import com.neusoft.hp.runtime.dyn.annotation.OriginalValue;
import com.neusoft.hp.runtime.dyn.annotation.RefValue;
import com.neusoft.hp.runtime.handler.EntityOperatorHandlerAdapter;
import com.neusoft.hp.runtime.handler.HandlerContext;

@Deprecated
public class JSONAttributeFilterHandler extends EntityOperatorHandlerAdapter {

    @SuppressWarnings("deprecation")
    public JSONAttributeFilterHandler(){

        this.setSorted(3);
        this.setUsed(true);
    }

    @Override
    public boolean afterQuery(List<GenDO> po, HandlerContext context) {
        if (context.getDomainClass().getSuperclass().isAssignableFrom(GenJoinDO.class)) {
            Map<Field, List<Annotation>> map = context.getStyle().getAnnotations();
            JSONArray array = new JSONArray();
            for (Object object : context.getList()) {
                JSONObject obj = convert((JSONObject) object, context, map);
                array.add(obj);
            }
            context.setList(array);
        }
        return super.afterQuery(po, context);
    }

    private JSONObject convert(JSONObject object, HandlerContext context, Map<Field, List<Annotation>> map) {
        JSONObject json = new JSONObject();
        Field[] field = context.getDomainClass().getDeclaredFields();
        for (Field f : field) {
            GenTable table = f.getType().getAnnotation(GenTable.class);
            JSONObject o = (JSONObject) object.get(f.getName());
            if (o == null) {
                continue;
            }
            if (table.parentClass().isAssignableFrom(GenDO.class)) {
                json.put("gid", o.get("gid"));
                json.put("createBy", o.get("createBy"));
                json.put("createTime", o.get("createTime"));
                json.put("updateBy", o.get("updateBy"));
                json.put("updateTime", o.get("updateTime"));
                json.put("active", o.get("active"));
                json.put("delete", o.get("delete"));
            }
            for (Map.Entry<Field, List<Annotation>> entry : map.entrySet()) {
                List<Annotation> annotation = entry.getValue();
                for (Annotation a : annotation) {
                    if (a instanceof OriginalValue) {
                        if (o.containsKey(((OriginalValue) a).displayName())) {
                            json.put(((OriginalValue) a).displayName(), o.get(((OriginalValue) a).displayName()));
                        }
                    } else if (a instanceof EnumValue) {
                        if (o.containsKey(((EnumValue) a).displayName())) {
                            json.put(((EnumValue) a).displayName(), o.get(((EnumValue) a).displayName()));
                        }
                    } else if (a instanceof RefValue) {
                        if (o.containsKey(((RefValue) a).displayName())) {
                            json.put(((RefValue) a).displayName(), o.get(((RefValue) a).displayName()));
                        }
                    }
                }
            }
        }
        return json;
    }

}
