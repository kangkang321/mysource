package com.neusoft.designer.service.domain;

import java.io.Serializable;

import com.neusoft.core.dao.GenDO;

public class PObjAttribute extends GenDO implements Serializable {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * P_OBJ_ATTRIBUTE.P_OBJ_OBJECT_GID
     *
     * @mbg.generated
     */
    private String            pObjObjectGid;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * P_OBJ_ATTRIBUTE.P_OBJ_ENUM_GID
     *
     * @mbg.generated
     */
    private String            pObjEnumGid;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * P_OBJ_ATTRIBUTE.LABEL
     *
     * @mbg.generated
     */
    private String            label;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * P_OBJ_ATTRIBUTE.DESCR
     *
     * @mbg.generated
     */
    private String            descr;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column P_OBJ_ATTRIBUTE.NAME
     *
     * @mbg.generated
     */
    private String            name;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * P_OBJ_ATTRIBUTE.REG_EXP
     *
     * @mbg.generated
     */
    private String            regExp;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * P_OBJ_ATTRIBUTE.IS_UNIQUE
     *
     * @mbg.generated
     */
    private Boolean           unique;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * P_OBJ_ATTRIBUTE.IS_REQUIRED
     *
     * @mbg.generated
     */
    private Boolean           required;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table P_OBJ_ATTRIBUTE
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * P_OBJ_ATTRIBUTE.P_OBJ_OBJECT_GID
     *
     * @return the value of P_OBJ_ATTRIBUTE.P_OBJ_OBJECT_GID
     * @mbg.generated
     */
    public String getpObjObjectGid() {
        return pObjObjectGid;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * P_OBJ_ATTRIBUTE.P_OBJ_OBJECT_GID
     *
     * @param pObjObjectGid the value for P_OBJ_ATTRIBUTE.P_OBJ_OBJECT_GID
     * @mbg.generated
     */
    public void setpObjObjectGid(String pObjObjectGid) {
        this.pObjObjectGid = pObjObjectGid == null ? null : pObjObjectGid.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * P_OBJ_ATTRIBUTE.P_OBJ_ENUM_GID
     *
     * @return the value of P_OBJ_ATTRIBUTE.P_OBJ_ENUM_GID
     * @mbg.generated
     */
    public String getpObjEnumGid() {
        return pObjEnumGid;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * P_OBJ_ATTRIBUTE.P_OBJ_ENUM_GID
     *
     * @param pObjEnumGid the value for P_OBJ_ATTRIBUTE.P_OBJ_ENUM_GID
     * @mbg.generated
     */
    public void setpObjEnumGid(String pObjEnumGid) {
        this.pObjEnumGid = pObjEnumGid == null ? null : pObjEnumGid.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * P_OBJ_ATTRIBUTE.LABEL
     *
     * @return the value of P_OBJ_ATTRIBUTE.LABEL
     * @mbg.generated
     */
    public String getLabel() {
        return label;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * P_OBJ_ATTRIBUTE.LABEL
     *
     * @param label the value for P_OBJ_ATTRIBUTE.LABEL
     * @mbg.generated
     */
    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * P_OBJ_ATTRIBUTE.DESCR
     *
     * @return the value of P_OBJ_ATTRIBUTE.DESCR
     * @mbg.generated
     */
    public String getDescr() {
        return descr;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * P_OBJ_ATTRIBUTE.DESCR
     *
     * @param descr the value for P_OBJ_ATTRIBUTE.DESCR
     * @mbg.generated
     */
    public void setDescr(String descr) {
        this.descr = descr == null ? null : descr.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * P_OBJ_ATTRIBUTE.NAME
     *
     * @return the value of P_OBJ_ATTRIBUTE.NAME
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * P_OBJ_ATTRIBUTE.NAME
     *
     * @param name the value for P_OBJ_ATTRIBUTE.NAME
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * P_OBJ_ATTRIBUTE.REG_EXP
     *
     * @return the value of P_OBJ_ATTRIBUTE.REG_EXP
     * @mbg.generated
     */
    public String getRegExp() {
        return regExp;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * P_OBJ_ATTRIBUTE.REG_EXP
     *
     * @param regExp the value for P_OBJ_ATTRIBUTE.REG_EXP
     * @mbg.generated
     */
    public void setRegExp(String regExp) {
        this.regExp = regExp == null ? null : regExp.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * P_OBJ_ATTRIBUTE.IS_UNIQUE
     *
     * @return the value of P_OBJ_ATTRIBUTE.IS_UNIQUE
     * @mbg.generated
     */
    public Boolean getUnique() {
        return unique;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * P_OBJ_ATTRIBUTE.IS_UNIQUE
     *
     * @param unique the value for P_OBJ_ATTRIBUTE.IS_UNIQUE
     * @mbg.generated
     */
    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * P_OBJ_ATTRIBUTE.IS_REQUIRED
     *
     * @return the value of P_OBJ_ATTRIBUTE.IS_REQUIRED
     * @mbg.generated
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * P_OBJ_ATTRIBUTE.IS_REQUIRED
     *
     * @param required the value for P_OBJ_ATTRIBUTE.IS_REQUIRED
     * @mbg.generated
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_ATTRIBUTE
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
        PObjAttribute other = (PObjAttribute) that;
        return (this.getGid() == null ? other.getGid() == null : this.getGid().equals(other.getGid()))
               && (this.getpObjObjectGid() == null ? other.getpObjObjectGid() == null : this.getpObjObjectGid().equals(other.getpObjObjectGid()))
               && (this.getpObjEnumGid() == null ? other.getpObjEnumGid() == null : this.getpObjEnumGid().equals(other.getpObjEnumGid()))
               && (this.getLabel() == null ? other.getLabel() == null : this.getLabel().equals(other.getLabel()))
               && (this.getDescr() == null ? other.getDescr() == null : this.getDescr().equals(other.getDescr()))
               && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
               && (this.getRegExp() == null ? other.getRegExp() == null : this.getRegExp().equals(other.getRegExp()))
               && (this.getUnique() == null ? other.getUnique() == null : this.getUnique().equals(other.getUnique()))
               && (this.getRequired() == null ? other.getRequired() == null : this.getRequired().equals(other.getRequired()))
               && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
               && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
               && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
               && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
               && (this.getActive() == null ? other.getActive() == null : this.getActive().equals(other.getActive()))
               && (this.getDelete() == null ? other.getDelete() == null : this.getDelete().equals(other.getDelete()));
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_ATTRIBUTE
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGid() == null) ? 0 : getGid().hashCode());
        result = prime * result + ((getpObjObjectGid() == null) ? 0 : getpObjObjectGid().hashCode());
        result = prime * result + ((getpObjEnumGid() == null) ? 0 : getpObjEnumGid().hashCode());
        result = prime * result + ((getLabel() == null) ? 0 : getLabel().hashCode());
        result = prime * result + ((getDescr() == null) ? 0 : getDescr().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getRegExp() == null) ? 0 : getRegExp().hashCode());
        result = prime * result + ((getUnique() == null) ? 0 : getUnique().hashCode());
        result = prime * result + ((getRequired() == null) ? 0 : getRequired().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getActive() == null) ? 0 : getActive().hashCode());
        result = prime * result + ((getDelete() == null) ? 0 : getDelete().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table P_OBJ_ATTRIBUTE
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", pObjObjectGid=").append(pObjObjectGid);
        sb.append(", pObjEnumGid=").append(pObjEnumGid);
        sb.append(", label=").append(label);
        sb.append(", descr=").append(descr);
        sb.append(", name=").append(name);
        sb.append(", regExp=").append(regExp);
        sb.append(", unique=").append(unique);
        sb.append(", required=").append(required);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
