package com.neusoft.designer.service.domain;

import com.neusoft.core.dao.GenDO;
import java.io.Serializable;

public class HpOdAttribute extends GenDO implements Serializable {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.HP_OD_CLASS_GID
     *
     * @mbg.generated
     */
    private String            hpOdClassGid;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column HP_OD_ATTRIBUTE.SEQ
     *
     * @mbg.generated
     */
    private Short             seq;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column HP_OD_ATTRIBUTE.CODE
     *
     * @mbg.generated
     */
    private String            code;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.FIELD_NAME
     *
     * @mbg.generated
     */
    private String            fieldName;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column HP_OD_ATTRIBUTE.NAME
     *
     * @mbg.generated
     */
    private String            name;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.DISPLAY_NAME
     *
     * @mbg.generated
     */
    private String            displayName;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.DESCRIPTION
     *
     * @mbg.generated
     */
    private String            description;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.DATA_TYPE
     *
     * @mbg.generated
     */
    private Short             dataType;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.TYPE_VALUE
     *
     * @mbg.generated
     */
    private String            typeValue;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.IS_FIXEDLENGTH
     *
     * @mbg.generated
     */
    private Boolean           isFixedlength;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.ATTR_LENGTH
     *
     * @mbg.generated
     */
    private Short             attrLength;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.ATTR_PRECISION
     *
     * @mbg.generated
     */
    private Short             attrPrecision;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.MAX_VALUE
     *
     * @mbg.generated
     */
    private String            maxValue;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.MIN_VALUE
     *
     * @mbg.generated
     */
    private String            minValue;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.DEFAULT_VALUE
     *
     * @mbg.generated
     */
    private String            defaultValue;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.IS_REQUIRED
     *
     * @mbg.generated
     */
    private Boolean           isRequired;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.IS_READONLY
     *
     * @mbg.generated
     */
    private Boolean           isReadonly;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.IS_UNIQUE
     *
     * @mbg.generated
     */
    private Boolean           isUnique;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_ATTRIBUTE.IS_DATAFILTER
     *
     * @mbg.generated
     */
    private Boolean           isDatafilter;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table HP_OD_ATTRIBUTE
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.HP_OD_CLASS_GID
     *
     * @return the value of HP_OD_ATTRIBUTE.HP_OD_CLASS_GID
     * @mbg.generated
     */
    public String getHpOdClassGid() {
        return hpOdClassGid;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.HP_OD_CLASS_GID
     *
     * @param hpOdClassGid the value for HP_OD_ATTRIBUTE.HP_OD_CLASS_GID
     * @mbg.generated
     */
    public void setHpOdClassGid(String hpOdClassGid) {
        this.hpOdClassGid = hpOdClassGid == null ? null : hpOdClassGid.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.SEQ
     *
     * @return the value of HP_OD_ATTRIBUTE.SEQ
     * @mbg.generated
     */
    public Short getSeq() {
        return seq;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.SEQ
     *
     * @param seq the value for HP_OD_ATTRIBUTE.SEQ
     * @mbg.generated
     */
    public void setSeq(Short seq) {
        this.seq = seq;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.CODE
     *
     * @return the value of HP_OD_ATTRIBUTE.CODE
     * @mbg.generated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.CODE
     *
     * @param code the value for HP_OD_ATTRIBUTE.CODE
     * @mbg.generated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.FIELD_NAME
     *
     * @return the value of HP_OD_ATTRIBUTE.FIELD_NAME
     * @mbg.generated
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.FIELD_NAME
     *
     * @param fieldName the value for HP_OD_ATTRIBUTE.FIELD_NAME
     * @mbg.generated
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.NAME
     *
     * @return the value of HP_OD_ATTRIBUTE.NAME
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.NAME
     *
     * @param name the value for HP_OD_ATTRIBUTE.NAME
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.DISPLAY_NAME
     *
     * @return the value of HP_OD_ATTRIBUTE.DISPLAY_NAME
     * @mbg.generated
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.DISPLAY_NAME
     *
     * @param displayName the value for HP_OD_ATTRIBUTE.DISPLAY_NAME
     * @mbg.generated
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName == null ? null : displayName.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.DESCRIPTION
     *
     * @return the value of HP_OD_ATTRIBUTE.DESCRIPTION
     * @mbg.generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.DESCRIPTION
     *
     * @param description the value for HP_OD_ATTRIBUTE.DESCRIPTION
     * @mbg.generated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.DATA_TYPE
     *
     * @return the value of HP_OD_ATTRIBUTE.DATA_TYPE
     * @mbg.generated
     */
    public Short getDataType() {
        return dataType;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.DATA_TYPE
     *
     * @param dataType the value for HP_OD_ATTRIBUTE.DATA_TYPE
     * @mbg.generated
     */
    public void setDataType(Short dataType) {
        this.dataType = dataType;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.TYPE_VALUE
     *
     * @return the value of HP_OD_ATTRIBUTE.TYPE_VALUE
     * @mbg.generated
     */
    public String getTypeValue() {
        return typeValue;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.TYPE_VALUE
     *
     * @param typeValue the value for HP_OD_ATTRIBUTE.TYPE_VALUE
     * @mbg.generated
     */
    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue == null ? null : typeValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.IS_FIXEDLENGTH
     *
     * @return the value of HP_OD_ATTRIBUTE.IS_FIXEDLENGTH
     * @mbg.generated
     */
    public Boolean getIsFixedlength() {
        return isFixedlength;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.IS_FIXEDLENGTH
     *
     * @param isFixedlength the value for HP_OD_ATTRIBUTE.IS_FIXEDLENGTH
     * @mbg.generated
     */
    public void setIsFixedlength(Boolean isFixedlength) {
        this.isFixedlength = isFixedlength;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.ATTR_LENGTH
     *
     * @return the value of HP_OD_ATTRIBUTE.ATTR_LENGTH
     * @mbg.generated
     */
    public Short getAttrLength() {
        return attrLength;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.ATTR_LENGTH
     *
     * @param attrLength the value for HP_OD_ATTRIBUTE.ATTR_LENGTH
     * @mbg.generated
     */
    public void setAttrLength(Short attrLength) {
        this.attrLength = attrLength;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.ATTR_PRECISION
     *
     * @return the value of HP_OD_ATTRIBUTE.ATTR_PRECISION
     * @mbg.generated
     */
    public Short getAttrPrecision() {
        return attrPrecision;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.ATTR_PRECISION
     *
     * @param attrPrecision the value for HP_OD_ATTRIBUTE.ATTR_PRECISION
     * @mbg.generated
     */
    public void setAttrPrecision(Short attrPrecision) {
        this.attrPrecision = attrPrecision;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.MAX_VALUE
     *
     * @return the value of HP_OD_ATTRIBUTE.MAX_VALUE
     * @mbg.generated
     */
    public String getMaxValue() {
        return maxValue;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.MAX_VALUE
     *
     * @param maxValue the value for HP_OD_ATTRIBUTE.MAX_VALUE
     * @mbg.generated
     */
    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue == null ? null : maxValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.MIN_VALUE
     *
     * @return the value of HP_OD_ATTRIBUTE.MIN_VALUE
     * @mbg.generated
     */
    public String getMinValue() {
        return minValue;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.MIN_VALUE
     *
     * @param minValue the value for HP_OD_ATTRIBUTE.MIN_VALUE
     * @mbg.generated
     */
    public void setMinValue(String minValue) {
        this.minValue = minValue == null ? null : minValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.DEFAULT_VALUE
     *
     * @return the value of HP_OD_ATTRIBUTE.DEFAULT_VALUE
     * @mbg.generated
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.DEFAULT_VALUE
     *
     * @param defaultValue the value for HP_OD_ATTRIBUTE.DEFAULT_VALUE
     * @mbg.generated
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue == null ? null : defaultValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.IS_REQUIRED
     *
     * @return the value of HP_OD_ATTRIBUTE.IS_REQUIRED
     * @mbg.generated
     */
    public Boolean getIsRequired() {
        return isRequired;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.IS_REQUIRED
     *
     * @param isRequired the value for HP_OD_ATTRIBUTE.IS_REQUIRED
     * @mbg.generated
     */
    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.IS_READONLY
     *
     * @return the value of HP_OD_ATTRIBUTE.IS_READONLY
     * @mbg.generated
     */
    public Boolean getIsReadonly() {
        return isReadonly;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.IS_READONLY
     *
     * @param isReadonly the value for HP_OD_ATTRIBUTE.IS_READONLY
     * @mbg.generated
     */
    public void setIsReadonly(Boolean isReadonly) {
        this.isReadonly = isReadonly;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.IS_UNIQUE
     *
     * @return the value of HP_OD_ATTRIBUTE.IS_UNIQUE
     * @mbg.generated
     */
    public Boolean getIsUnique() {
        return isUnique;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.IS_UNIQUE
     *
     * @param isUnique the value for HP_OD_ATTRIBUTE.IS_UNIQUE
     * @mbg.generated
     */
    public void setIsUnique(Boolean isUnique) {
        this.isUnique = isUnique;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_ATTRIBUTE.IS_DATAFILTER
     *
     * @return the value of HP_OD_ATTRIBUTE.IS_DATAFILTER
     * @mbg.generated
     */
    public Boolean getIsDatafilter() {
        return isDatafilter;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_ATTRIBUTE.IS_DATAFILTER
     *
     * @param isDatafilter the value for HP_OD_ATTRIBUTE.IS_DATAFILTER
     * @mbg.generated
     */
    public void setIsDatafilter(Boolean isDatafilter) {
        this.isDatafilter = isDatafilter;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OD_ATTRIBUTE
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
        HpOdAttribute other = (HpOdAttribute) that;
        return (this.getGid() == null ? other.getGid() == null : this.getGid().equals(other.getGid()))
               && (this.getHpOdClassGid() == null ? other.getHpOdClassGid() == null : this.getHpOdClassGid().equals(other.getHpOdClassGid()))
               && (this.getSeq() == null ? other.getSeq() == null : this.getSeq().equals(other.getSeq()))
               && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
               && (this.getFieldName() == null ? other.getFieldName() == null : this.getFieldName().equals(other.getFieldName()))
               && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
               && (this.getDisplayName() == null ? other.getDisplayName() == null : this.getDisplayName().equals(other.getDisplayName()))
               && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
               && (this.getDataType() == null ? other.getDataType() == null : this.getDataType().equals(other.getDataType()))
               && (this.getTypeValue() == null ? other.getTypeValue() == null : this.getTypeValue().equals(other.getTypeValue()))
               && (this.getIsFixedlength() == null ? other.getIsFixedlength() == null : this.getIsFixedlength().equals(other.getIsFixedlength()))
               && (this.getAttrLength() == null ? other.getAttrLength() == null : this.getAttrLength().equals(other.getAttrLength()))
               && (this.getAttrPrecision() == null ? other.getAttrPrecision() == null : this.getAttrPrecision().equals(other.getAttrPrecision()))
               && (this.getMaxValue() == null ? other.getMaxValue() == null : this.getMaxValue().equals(other.getMaxValue()))
               && (this.getMinValue() == null ? other.getMinValue() == null : this.getMinValue().equals(other.getMinValue()))
               && (this.getDefaultValue() == null ? other.getDefaultValue() == null : this.getDefaultValue().equals(other.getDefaultValue()))
               && (this.getIsRequired() == null ? other.getIsRequired() == null : this.getIsRequired().equals(other.getIsRequired()))
               && (this.getIsReadonly() == null ? other.getIsReadonly() == null : this.getIsReadonly().equals(other.getIsReadonly()))
               && (this.getIsUnique() == null ? other.getIsUnique() == null : this.getIsUnique().equals(other.getIsUnique()))
               && (this.getIsDatafilter() == null ? other.getIsDatafilter() == null : this.getIsDatafilter().equals(other.getIsDatafilter()))
               && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
               && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
               && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
               && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
               && (this.getDelete() == null ? other.getDelete() == null : this.getDelete().equals(other.getDelete()))
               && (this.getActive() == null ? other.getActive() == null : this.getActive().equals(other.getActive()));
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OD_ATTRIBUTE
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGid() == null) ? 0 : getGid().hashCode());
        result = prime * result + ((getHpOdClassGid() == null) ? 0 : getHpOdClassGid().hashCode());
        result = prime * result + ((getSeq() == null) ? 0 : getSeq().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getFieldName() == null) ? 0 : getFieldName().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDisplayName() == null) ? 0 : getDisplayName().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getDataType() == null) ? 0 : getDataType().hashCode());
        result = prime * result + ((getTypeValue() == null) ? 0 : getTypeValue().hashCode());
        result = prime * result + ((getIsFixedlength() == null) ? 0 : getIsFixedlength().hashCode());
        result = prime * result + ((getAttrLength() == null) ? 0 : getAttrLength().hashCode());
        result = prime * result + ((getAttrPrecision() == null) ? 0 : getAttrPrecision().hashCode());
        result = prime * result + ((getMaxValue() == null) ? 0 : getMaxValue().hashCode());
        result = prime * result + ((getMinValue() == null) ? 0 : getMinValue().hashCode());
        result = prime * result + ((getDefaultValue() == null) ? 0 : getDefaultValue().hashCode());
        result = prime * result + ((getIsRequired() == null) ? 0 : getIsRequired().hashCode());
        result = prime * result + ((getIsReadonly() == null) ? 0 : getIsReadonly().hashCode());
        result = prime * result + ((getIsUnique() == null) ? 0 : getIsUnique().hashCode());
        result = prime * result + ((getIsDatafilter() == null) ? 0 : getIsDatafilter().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDelete() == null) ? 0 : getDelete().hashCode());
        result = prime * result + ((getActive() == null) ? 0 : getActive().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OD_ATTRIBUTE
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", hpOdClassGid=").append(hpOdClassGid);
        sb.append(", seq=").append(seq);
        sb.append(", code=").append(code);
        sb.append(", fieldName=").append(fieldName);
        sb.append(", name=").append(name);
        sb.append(", displayName=").append(displayName);
        sb.append(", description=").append(description);
        sb.append(", dataType=").append(dataType);
        sb.append(", typeValue=").append(typeValue);
        sb.append(", isFixedlength=").append(isFixedlength);
        sb.append(", attrLength=").append(attrLength);
        sb.append(", attrPrecision=").append(attrPrecision);
        sb.append(", maxValue=").append(maxValue);
        sb.append(", minValue=").append(minValue);
        sb.append(", defaultValue=").append(defaultValue);
        sb.append(", isRequired=").append(isRequired);
        sb.append(", isReadonly=").append(isReadonly);
        sb.append(", isUnique=").append(isUnique);
        sb.append(", isDatafilter=").append(isDatafilter);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
