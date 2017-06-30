package com.neusoft.hp.runtime.dyn.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;

import com.neusoft.hp.runtime.client.action.TreeNodeVO;
import com.neusoft.hp.runtime.dyn.dao.DynSqlProvider;

/**
 * 类DynSqlMapper.java的实现描述：查询引用值
 * 
 * @author Administrator 2017年4月21日 下午2:48:13
 */
public interface DynSqlMapper {

    @SelectProvider(type = DynSqlProvider.class, method = "getSql")
    @ResultType(value = String.class)
    public String selectValue(String sql);

    @SelectProvider(type = DynSqlProvider.class, method = "getSql")
    @ResultType(value = String.class)
    public List<String> queryValues(String sql);

    @SuppressWarnings("rawtypes")
    @SelectProvider(type = DynSqlProvider.class, method = "getSql")
    @ResultType(value = Map.class)
    public List<Map> queryListMap(String sql);

    @SelectProvider(type = DynSqlProvider.class, method = "getSql")
    @ResultType(value = TreeNodeVO.class)
    public List<TreeNodeVO> queryList(String sql);
}
