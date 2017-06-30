package com.neusoft.hp.runtime.dyn.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.hp.runtime.dyn.ConditionQueryBean;
import com.neusoft.hp.runtime.handler.HandlerContext;
import com.neusoft.hp.runtime.sql.AbstractSqlBuilder;
import com.neusoft.hp.runtime.sql.OriginalSqlBuilder;
import com.neusoft.hp.runtime.sql.RefSqlBuilder;
import com.neusoft.hp.runtime.sql.TypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类DynQueryBean.java的实现描述：查询模板对应的动态类
 * 
 * @author Administrator 2017年4月18日 下午3:15:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DynQueryBean extends DynBean {

    private HandlerContext           context;

    private List<ConditionQueryBean> conditionQueryBeans = new ArrayList<>();

    /**
     * 根据表名拼接出动态sql
     * 
     * @param tableName
     * @return
     */
    public String getWhere(String tableName) {
        StringBuffer conQueryStr = new StringBuffer();

        conditionQueryBeans.stream().filter(f -> StringUtils.endsWithIgnoreCase(tableName, f.getTable())).forEach(a -> {
            handler(conQueryStr, a);
        });
        String query = StringUtils.trim(conQueryStr.toString());
        if (StringUtils.endsWithIgnoreCase(query, "and")) {
            query = StringUtils.substring(query, 0, StringUtils.length(query) - 3);
        }
        if (StringUtils.endsWithIgnoreCase(query, "or")) {
            query = StringUtils.substring(query, 0, StringUtils.length(query) - 2);
        }
        return query;
    }

    private void handler(StringBuffer sb, ConditionQueryBean bean) {
        TypeEnum type = Enum.valueOf(TypeEnum.class, StringUtils.upperCase(bean.getType()));
        if (StringUtils.isEmpty(bean.getValue()) && type != TypeEnum.NULL && type != TypeEnum.NONULL) {
            return;
        }
        sb.append(" ");
        sb.append(bean.getLeftSuffix() == null ? "" : bean.getLeftSuffix());
        AbstractSqlBuilder sqlBuilder = null;
        if (StringUtils.contains(bean.getPath(), ",")) {
            // 引用
            sqlBuilder = new RefSqlBuilder(context);
        } else {
            // 非引用
            sqlBuilder = new OriginalSqlBuilder();
        }
        if (StringUtils.isBlank(bean.getPath())) {
            sb.append(sqlBuilder.toSql(bean.getPath(), type, bean.getValue(), bean.getTable(), bean.getColumn()));
        } else {
            sb.append(sqlBuilder.toSql(bean.getPath(), type, bean.getValue()));
        }
        sb.append(bean.getRightSuffix() == null ? "" : bean.getRightSuffix());
        sb.append(" ");
        sb.append(bean.getJoinFlag() == null ? "" : bean.getJoinFlag());
    }

}
