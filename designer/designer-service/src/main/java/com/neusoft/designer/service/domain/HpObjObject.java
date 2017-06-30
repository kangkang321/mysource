package com.neusoft.designer.service.domain;

import java.io.Serializable;

import com.neusoft.core.dao.GenDO;

public class HpObjObject extends GenDO implements Serializable {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OBJ_OBJECT.HP_OBJ_ORG_GID
     *
     * @mbg.generated
     */
    private String            hpObjOrgGid;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OBJ_OBJECT.PARENT_OBJ_GID
     *
     * @mbg.generated
     */
    private String            parentObjGid;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column HP_OBJ_OBJECT.NAME
     *
     * @mbg.generated
     */
    private String            name;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column HP_OBJ_OBJECT.LABEL
     *
     * @mbg.generated
     */
    private String            label;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column HP_OBJ_OBJECT.DESCR
     *
     * @mbg.generated
     */
    private String            descr;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OBJ_OBJECT.CATEGORY
     *
     * @mbg.generated
     */
    private String            category;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column HP_OBJ_OBJECT.MODULE
     *
     * @mbg.generated
     */
    private String            module;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OBJ_OBJECT.IS_REFERENCED
     *
     * @mbg.generated
     */
    private Short             isReferenced;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table HP_OBJ_OBJECT
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OBJ_OBJECT.HP_OBJ_ORG_GID
     *
     * @return the value of HP_OBJ_OBJECT.HP_OBJ_ORG_GID
     * @mbg.generated
     */
    public String getHpObjOrgGid() {
        return hpObjOrgGid;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OBJ_OBJECT.HP_OBJ_ORG_GID
     *
     * @param hpObjOrgGid the value for HP_OBJ_OBJECT.HP_OBJ_ORG_GID
     * @mbg.generated
     */
    public void setHpObjOrgGid(String hpObjOrgGid) {
        this.hpObjOrgGid = hpObjOrgGid == null ? null : hpObjOrgGid.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OBJ_OBJECT.PARENT_OBJ_GID
     *
     * @return the value of HP_OBJ_OBJECT.PARENT_OBJ_GID
     * @mbg.generated
     */
    public String getParentObjGid() {
        return parentObjGid;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OBJ_OBJECT.PARENT_OBJ_GID
     *
     * @param parentObjGid the value for HP_OBJ_OBJECT.PARENT_OBJ_GID
     * @mbg.generated
     */
    public void setParentObjGid(String parentObjGid) {
        this.parentObjGid = parentObjGid == null ? null : parentObjGid.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OBJ_OBJECT.NAME
     *
     * @return the value of HP_OBJ_OBJECT.NAME
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OBJ_OBJECT.NAME
     *
     * @param name the value for HP_OBJ_OBJECT.NAME
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OBJ_OBJECT.LABEL
     *
     * @return the value of HP_OBJ_OBJECT.LABEL
     * @mbg.generated
     */
    public String getLabel() {
        return label;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OBJ_OBJECT.LABEL
     *
     * @param label the value for HP_OBJ_OBJECT.LABEL
     * @mbg.generated
     */
    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OBJ_OBJECT.DESCR
     *
     * @return the value of HP_OBJ_OBJECT.DESCR
     * @mbg.generated
     */
    public String getDescr() {
        return descr;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OBJ_OBJECT.DESCR
     *
     * @param descr the value for HP_OBJ_OBJECT.DESCR
     * @mbg.generated
     */
    public void setDescr(String descr) {
        this.descr = descr == null ? null : descr.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OBJ_OBJECT.CATEGORY
     *
     * @return the value of HP_OBJ_OBJECT.CATEGORY
     * @mbg.generated
     */
    public String getCategory() {
        return category;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OBJ_OBJECT.CATEGORY
     *
     * @param category the value for HP_OBJ_OBJECT.CATEGORY
     * @mbg.generated
     */
    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OBJ_OBJECT.MODULE
     *
     * @return the value of HP_OBJ_OBJECT.MODULE
     * @mbg.generated
     */
    public String getModule() {
        return module;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OBJ_OBJECT.MODULE
     *
     * @param module the value for HP_OBJ_OBJECT.MODULE
     * @mbg.generated
     */
    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OBJ_OBJECT.IS_REFERENCED
     *
     * @return the value of HP_OBJ_OBJECT.IS_REFERENCED
     * @mbg.generated
     */
    public Short getIsReferenced() {
        return isReferenced;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OBJ_OBJECT.IS_REFERENCED
     *
     * @param isReferenced the value for HP_OBJ_OBJECT.IS_REFERENCED
     * @mbg.generated
     */
    public void setIsReferenced(Short isReferenced) {
        this.isReferenced = isReferenced;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_OBJECT
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
        HpObjObject other = (HpObjObject) that;
        return (this.getGid() == null ? other.getGid() == null : this.getGid().equals(other.getGid()))
               && (this.getHpObjOrgGid() == null ? other.getHpObjOrgGid() == null : this.getHpObjOrgGid().equals(other.getHpObjOrgGid()))
               && (this.getParentObjGid() == null ? other.getParentObjGid() == null : this.getParentObjGid().equals(other.getParentObjGid()))
               && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
               && (this.getLabel() == null ? other.getLabel() == null : this.getLabel().equals(other.getLabel()))
               && (this.getDescr() == null ? other.getDescr() == null : this.getDescr().equals(other.getDescr()))
               && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
               && (this.getModule() == null ? other.getModule() == null : this.getModule().equals(other.getModule()))
               && (this.getIsReferenced() == null ? other.getIsReferenced() == null : this.getIsReferenced().equals(other.getIsReferenced()))
               && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
               && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
               && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
               && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
               && (this.getDelete() == null ? other.getDelete() == null : this.getDelete().equals(other.getDelete()))
               && (this.getActive() == null ? other.getActive() == null : this.getActive().equals(other.getActive()));
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_OBJECT
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGid() == null) ? 0 : getGid().hashCode());
        result = prime * result + ((getHpObjOrgGid() == null) ? 0 : getHpObjOrgGid().hashCode());
        result = prime * result + ((getParentObjGid() == null) ? 0 : getParentObjGid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getLabel() == null) ? 0 : getLabel().hashCode());
        result = prime * result + ((getDescr() == null) ? 0 : getDescr().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getModule() == null) ? 0 : getModule().hashCode());
        result = prime * result + ((getIsReferenced() == null) ? 0 : getIsReferenced().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDelete() == null) ? 0 : getDelete().hashCode());
        result = prime * result + ((getActive() == null) ? 0 : getActive().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OBJ_OBJECT
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", hpObjOrgGid=").append(hpObjOrgGid);
        sb.append(", parentObjGid=").append(parentObjGid);
        sb.append(", name=").append(name);
        sb.append(", label=").append(label);
        sb.append(", descr=").append(descr);
        sb.append(", category=").append(category);
        sb.append(", module=").append(module);
        sb.append(", isReferenced=").append(isReferenced);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
