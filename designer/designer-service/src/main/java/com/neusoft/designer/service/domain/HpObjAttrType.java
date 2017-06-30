package com.neusoft.designer.service.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.neusoft.core.dao.GenDO;

public class HpObjAttrType extends GenDO implements Serializable {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column HP_OBJ_ATTR_TYPE.KEY
     *
     * @mbg.generated
     */
    private BigDecimal        key;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OBJ_ATTR_TYPE.LABEL
     *
     * @mbg.generated
     */
    private String            label;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OBJ_ATTR_TYPE.DESCR
     *
     * @mbg.generated
     */
    private String            descr;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OBJ_ATTR_TYPE.REG_EXP
     *
     * @mbg.generated
     */
    private String            regExp;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table HP_OBJ_ATTR_TYPE
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OBJ_ATTR_TYPE.KEY
     *
     * @return the value of HP_OBJ_ATTR_TYPE.KEY
     * @mbg.generated
     */
    public BigDecimal getKey() {
        return key;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OBJ_ATTR_TYPE.KEY
     *
     * @param key the value for HP_OBJ_ATTR_TYPE.KEY
     * @mbg.generated
     */
    public void setKey(BigDecimal key) {
        this.key = key;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OBJ_ATTR_TYPE.LABEL
     *
     * @return the value of HP_OBJ_ATTR_TYPE.LABEL
     * @mbg.generated
     */
    public String getLabel() {
        return label;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OBJ_ATTR_TYPE.LABEL
     *
     * @param label the value for HP_OBJ_ATTR_TYPE.LABEL
     * @mbg.generated
     */
    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OBJ_ATTR_TYPE.DESCR
     *
     * @return the value of HP_OBJ_ATTR_TYPE.DESCR
     * @mbg.generated
     */
    public String getDescr() {
        return descr;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OBJ_ATTR_TYPE.DESCR
     *
     * @param descr the value for HP_OBJ_ATTR_TYPE.DESCR
     * @mbg.generated
     */
    public void setDescr(String descr) {
        this.descr = descr == null ? null : descr.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OBJ_ATTR_TYPE.REG_EXP
     *
     * @return the value of HP_OBJ_ATTR_TYPE.REG_EXP
     * @mbg.generated
     */
    public String getRegExp() {
        return regExp;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OBJ_ATTR_TYPE.REG_EXP
     *
     * @param regExp the value for HP_OBJ_ATTR_TYPE.REG_EXP
     * @mbg.generated
     */
    public void setRegExp(String regExp) {
        this.regExp = regExp == null ? null : regExp.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_ATTR_TYPE
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        HpObjAttrType other = (HpObjAttrType) that;
        return (this.getGid() == null ? other.getGid() == null : this.getGid().equals(other.getGid()))
               && (this.getKey() == null ? other.getKey() == null : this.getKey().equals(other.getKey()))
               && (this.getLabel() == null ? other.getLabel() == null : this.getLabel().equals(other.getLabel()))
               && (this.getDescr() == null ? other.getDescr() == null : this.getDescr().equals(other.getDescr()))
               && (this.getRegExp() == null ? other.getRegExp() == null : this.getRegExp().equals(other.getRegExp()))
               && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
               && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
               && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
               && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
               && (this.getDelete() == null ? other.getDelete() == null : this.getDelete().equals(other.getDelete()))
               && (this.getActive() == null ? other.getActive() == null : this.getActive().equals(other.getActive()));
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_ATTR_TYPE
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGid() == null) ? 0 : getGid().hashCode());
        result = prime * result + ((getKey() == null) ? 0 : getKey().hashCode());
        result = prime * result + ((getLabel() == null) ? 0 : getLabel().hashCode());
        result = prime * result + ((getDescr() == null) ? 0 : getDescr().hashCode());
        result = prime * result + ((getRegExp() == null) ? 0 : getRegExp().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDelete() == null) ? 0 : getDelete().hashCode());
        result = prime * result + ((getActive() == null) ? 0 : getActive().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_ATTR_TYPE
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", key=").append(key);
        sb.append(", label=").append(label);
        sb.append(", descr=").append(descr);
        sb.append(", regExp=").append(regExp);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
