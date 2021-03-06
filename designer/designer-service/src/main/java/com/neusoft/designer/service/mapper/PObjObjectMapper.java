package com.neusoft.designer.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.neusoft.core.dao.GenDao;
import com.neusoft.designer.service.domain.PObjObject;
import com.neusoft.designer.service.domain.PObjObjectExample;

public interface PObjObjectMapper extends GenDao {

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_OBJECT
     *
     * @mbg.generated
     */
    long countByExample(PObjObjectExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int deleteByExample(PObjObjectExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String gid);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int insert(PObjObject record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int insertSelective(PObjObject record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_OBJECT
     *
     * @mbg.generated
     */
    List<PObjObject> selectByExample(PObjObjectExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_OBJECT
     *
     * @mbg.generated
     */
    PObjObject selectByPrimaryKey(String gid);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PObjObject record, @Param("example") PObjObjectExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PObjObject record, @Param("example") PObjObjectExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PObjObject record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PObjObject record);
}
