package com.neusoft.designer.service.mapper;

import com.neusoft.core.dao.GenDao;
import com.neusoft.designer.service.domain.HpOdObject;
import com.neusoft.designer.service.domain.HpOdObjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HpOdObjectMapper extends GenDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HP_OD_OBJECT
     *
     * @mbg.generated
     */
    long countByExample(HpOdObjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HP_OD_OBJECT
     *
     * @mbg.generated
     */
    int deleteByExample(HpOdObjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HP_OD_OBJECT
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String gid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HP_OD_OBJECT
     *
     * @mbg.generated
     */
    int insert(HpOdObject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HP_OD_OBJECT
     *
     * @mbg.generated
     */
    int insertSelective(HpOdObject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HP_OD_OBJECT
     *
     * @mbg.generated
     */
    List<HpOdObject> selectByExample(HpOdObjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HP_OD_OBJECT
     *
     * @mbg.generated
     */
    HpOdObject selectByPrimaryKey(String gid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HP_OD_OBJECT
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") HpOdObject record, @Param("example") HpOdObjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HP_OD_OBJECT
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") HpOdObject record, @Param("example") HpOdObjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HP_OD_OBJECT
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(HpOdObject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HP_OD_OBJECT
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(HpOdObject record);
}