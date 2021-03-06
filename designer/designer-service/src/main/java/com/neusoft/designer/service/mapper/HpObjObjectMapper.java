package com.neusoft.designer.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.neusoft.core.dao.GenDao;
import com.neusoft.designer.service.domain.HpObjObject;
import com.neusoft.designer.service.domain.HpObjObjectExample;

public interface HpObjObjectMapper extends GenDao {

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_OBJECT
     *
     * @mbg.generated
     */
    long countByExample(HpObjObjectExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int deleteByExample(HpObjObjectExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String gid);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int insert(HpObjObject record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int insertSelective(HpObjObject record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_OBJECT
     *
     * @mbg.generated
     */
    List<HpObjObject> selectByExample(HpObjObjectExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_OBJECT
     *
     * @mbg.generated
     */
    HpObjObject selectByPrimaryKey(String gid);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") HpObjObject record, @Param("example") HpObjObjectExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") HpObjObject record, @Param("example") HpObjObjectExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(HpObjObject record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_OBJECT
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(HpObjObject record);
}
