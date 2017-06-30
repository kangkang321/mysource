package com.neusoft.core.dao.wrapper;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * 类BooleanTypeHandler.java的实现描述：oracle没有布尔类型，做个1到true，0到false的转换
 * 
 * @author Administrator 2017年3月16日 上午11:24:31
 */
public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter,
                                    JdbcType jdbcType) throws SQLException {
        if (parameter) {
            ps.setShort(i, (short) 1);
        } else {
            ps.setShort(i, (short) 0);
        }
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
        short s = rs.getShort(columnName);
        if (s == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        short s = rs.getShort(columnIndex);
        if (s == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        short s = cs.getShort(columnIndex);
        if (s == 1) {
            return true;
        } else {
            return false;
        }
    }

}
