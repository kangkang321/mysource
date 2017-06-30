package com.neusoft.hp.runtime.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.alibaba.druid.util.StringUtils;
import com.neusoft.core.dao.GenDO;
import com.neusoft.core.dao.GenExample;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.service.annotation.GenTable;

/**
 * 类EntityOperatorListener.java的实现描述：表单设计器埋点 FIXME 如果想中断程序执行，就抛出异常
 * 
 * @author Administrator 2017年3月16日 上午10:51:18
 */
public class EntityOperatorHandlerAdapter extends BaseHandler {

    /**
     * 新增之前的埋点，可以考虑搞验证、默认字段等扩展
     * 
     * @param po
     * @param context
     * @return
     */
    public boolean beforeInsert(GenDO po, HandlerContext context) {
        return true;
    }

    /**
     * 新增之后的埋点，暂时未想到应用场景
     * 
     * @param po
     * @param context
     * @return
     */
    public boolean afterInsert(GenDO po, HandlerContext context) {
        return true;
    }

    /**
     * 修改之前的埋点，可以考虑搞验证、默认字段等扩展
     * 
     * @param po
     * @param context
     * @return
     */
    public boolean beforeSave(GenDO po, HandlerContext context) {
        return true;
    }

    /**
     * 修改之后的埋点，暂时未想到应用场景
     * 
     * @param po
     * @param context
     * @return
     */
    public boolean afterSave(GenDO po, HandlerContext context) {
        return true;
    }

    /**
     * 删除之前的埋点，可以考虑删除之前的验证
     * 
     * @param id
     * @param context
     * @return
     */
    public boolean beforeDelete(String id, HandlerContext context) {
        return true;
    }

    /**
     * 删除之后的埋点，可以考虑级联删除
     * 
     * @param id
     * @param context
     * @return
     */
    public boolean afterDelete(String id, HandlerContext context) {
        return true;
    }

    /**
     * 查询之前的埋点，暂时未想到应用场景
     * 
     * @param id
     * @param context
     * @return
     */
    public boolean beforeSelect(GenExample example, HandlerContext context) {
        return true;
    }

    /**
     * 查询之后的埋点，可以考虑图卡模式展示想要的字段
     * 
     * @param po
     * @param context
     * @return
     */
    public boolean afterSelect(GenDO po, HandlerContext context) {
        return true;
    }

    /**
     * 查询之前的埋点，可以考虑查询条件的拼装
     * 
     * @param query
     * @param example
     * @param context
     * @return
     */
    public boolean beforeQuery(GenExample example, HandlerContext context) {
        return true;
    }

    /**
     * 查询之后的埋点，可以考虑列表模式展示想要的字段
     * 
     * @param po
     * @param context
     * @return
     */
    public boolean afterQuery(List<GenDO> po, HandlerContext context) {
        return true;
    }

    /**
     * @param param
     * @param flag 首字母大写传true,首字母小写传false
     * @return
     */
    protected String underlineToCamel(String param, Boolean flag) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == '_') {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                if (i == 0) {
                    if (flag) {
                        sb.append(Character.toUpperCase(param.charAt(i)));
                    } else {
                        sb.append(Character.toLowerCase(param.charAt(i)));
                    }
                } else {
                    sb.append(Character.toLowerCase(param.charAt(i)));
                }
            }
        }
        return sb.toString();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected GenExample getQueryByForeignKey(Class domain, String foreignKey, String value, String gid) {
        GenTable table = (GenTable) domain.getAnnotation(GenTable.class);
        Class clazz = table.exampleClass();
        GenExample example = null;
        try {
            example = (GenExample) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BizException(e);
        }
        // METHOD METHOD = REFLECTIONUTILS.FINDMETHOD(CLAZZ, "CREATECRITERIA");
        // TRY {
        // OBJECT C = REFLECTWRAPPER.INVOKE(METHOD, EXAMPLE);
        // METHOD EQUAL = REFLECTIONUTILS.FINDMETHOD(C.GETCLASS(), "AND" + FOREIGNKEY + "EQUALTO", NULL);
        // REFLECTWRAPPER.INVOKE(EQUAL, C, VALUE);
        // IF (!STRINGUTILS.ISEMPTY(GID)) {
        //
        // METHOD EQUALGID = REFLECTIONUTILS.FINDMETHOD(C.GETCLASS(), "ANDGIDEQUALTO", NULL);
        // REFLECTWRAPPER.INVOKE(EQUALGID, C, GID);
        // }
        // } CATCH (NOSUCHMETHODEXCEPTION | INVOCATIONTARGETEXCEPTION E) {
        // THROW NEW BIZEXCEPTION(E);
        // }
        example.setAppendWhere(foreignKey + "='" + value + "'");
        if (!StringUtils.isEmpty(gid)) {
            example.setAppendWhere("gid = '" + gid + "'");
        }
        return example;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected GenExample getQueryByForeignKey(Class domain, String foreignKey, String value, List<String> gids) {
        GenTable table = (GenTable) domain.getAnnotation(GenTable.class);
        Class clazz = table.exampleClass();
        GenExample example = null;
        try {
            example = (GenExample) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BizException(e);
        }
        // Method method = ReflectionUtils.findMethod(clazz, "createCriteria");
        // try {
        // Object c = ReflectWrapper.invoke(method, example);
        // Method equal = ReflectionUtils.findMethod(c.getClass(), "and" + foreignKey + "EqualTo", null);
        // ReflectWrapper.invoke(equal, c, value);
        // if (!CollectionUtils.isEmpty(gids)) {
        // Method in = ReflectionUtils.findMethod(c.getClass(), "andGidNotIn", null);
        // ReflectWrapper.invoke(in, c, gids);
        // }
        // } catch (NoSuchMethodException | InvocationTargetException e) {
        // throw new BizException(e);
        // }
        example.setAppendWhere(foreignKey + "='" + value + "'");
        if (!CollectionUtils.isEmpty(gids)) {
            String inStr = "(" + gids.stream().collect(StringBuffer::new,
                                                       (m, n) -> m.append(",").append("'").append(n).append("'"),
                                                       StringBuffer::append).substring(1)
                           + ")";
            example.setAppendWhere("gid not in " + inStr);
        }
        return example;
    }

    public static void main(String[] args) {
        List<String> gids = new ArrayList<>();
        gids.add("a");
        gids.add("b");
        System.out.println(gids.stream().collect(StringBuffer::new,
                                                 (m, n) -> m.append(",").append("'").append(n).append("'"),
                                                 StringBuffer::append).substring(1));
    }
}
