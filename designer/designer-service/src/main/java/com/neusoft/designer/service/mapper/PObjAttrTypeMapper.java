package com.neusoft.designer.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.neusoft.core.dao.GenDao;
import com.neusoft.designer.service.domain.PObjAttrType;
import com.neusoft.designer.service.domain.PObjAttrTypeExample;

public interface PObjAttrTypeMapper extends GenDao {

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_ATTR_TYPE
     *
     * @mbg.generated
     */
    long countByExample(PObjAttrTypeExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_ATTR_TYPE
     *
     * @mbg.generated
     */
    int deleteByExample(PObjAttrTypeExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_ATTR_TYPE
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String gid);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_ATTR_TYPE
     *
     * @mbg.generated
     */
    int insert(PObjAttrType record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_ATTR_TYPE
     *
     * @mbg.generated
     */
    int insertSelective(PObjAttrType record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_ATTR_TYPE
     *
     * @mbg.generated
     */
    List<PObjAttrType> selectByExample(PObjAttrTypeExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_ATTR_TYPE
     *
     * @mbg.generated
     */
    PObjAttrType selectByPrimaryKey(String gid);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_ATTR_TYPE
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PObjAttrType record, @Param("example") PObjAttrTypeExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_ATTR_TYPE
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PObjAttrType record, @Param("example") PObjAttrTypeExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_ATTR_TYPE
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PObjAttrType record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_ATTR_TYPE
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PObjAttrType record);
}
