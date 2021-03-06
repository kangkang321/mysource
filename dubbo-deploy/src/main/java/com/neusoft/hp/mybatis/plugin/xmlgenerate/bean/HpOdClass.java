package com.neusoft.hp.mybatis.plugin.xmlgenerate.bean;

import java.io.Serializable;
import java.util.List;

public class HpOdClass extends GenDO implements Serializable {
	
    


	/**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_CLASS.HP_OD_OBJECT_GID
     *
     * @mbg.generated
     */
    private String            hpOdObjectGid;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_CLASS.HP_OD_CLASS_GID
     *
     * @mbg.generated
     */
    private String            hpOdClassGid;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column HP_OD_CLASS.CODE
     *
     * @mbg.generated
     */
    private String            code;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column HP_OD_CLASS.NAME
     *
     * @mbg.generated
     */
    private String            name;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_CLASS.DISPLAY_NAME
     *
     * @mbg.generated
     */
    private String            displayName;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_CLASS.DESCRIPTION
     *
     * @mbg.generated
     */
    private String            description;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_CLASS.TABLE_NAME
     *
     * @mbg.generated
     */
    private String            tableName;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_CLASS.CLASS_NAME
     *
     * @mbg.generated
     */
    private String            className;
    /**
     * owner 指定该对象的实体所属
     */
    private String 			owner;
    
    /**
     * domian中的外键对应属性名
     */
    private String 			refKeyPropertyName;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * HP_OD_CLASS.IS_PRIMARY
     *
     * @mbg.generated
    */ 
    private Boolean           isPrimary;
    
    private List<HpOdAttribute> columns;
    
    private String refKey;
    
    private String parentClass;
    
    private String subTables;
    
    public String getOwner() {
    	return owner;
    }
    
    public void setOwner(String owner) {
    	this.owner = owner;
    }

    public String getSubTables() {
		return subTables;
	}

	public void setSubTables(String subTables) {
		this.subTables = subTables;
	}

	public String getRefKey() {
		return refKey;
	}

	public void setRefKey(String refKey) {
		this.refKey = refKey;
	}

	public String getParentClass() {
		return parentClass;
	}

	public void setParentClass(String parentClass) {
		this.parentClass = parentClass;
	}

	public List<HpOdAttribute> getColumns() {
		return columns;
	}

	public void setColumns(List<HpOdAttribute> columns) {
		this.columns = columns;
	}

	/**
     * This field was generated by MyBatis Generator. This field corresponds to the database table HP_OD_CLASS
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
    
    
    public String getRefKeyPropertyName() {
		return refKeyPropertyName;
	}

	public void setRefKeyPropertyName(String refKeyPropertyName) {
		this.refKeyPropertyName = refKeyPropertyName;
	}

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_CLASS.HP_OD_OBJECT_GID
     *
     * @return the value of HP_OD_CLASS.HP_OD_OBJECT_GID
     * @mbg.generated
     */
    public String getHpOdObjectGid() {
        return hpOdObjectGid;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_CLASS.HP_OD_OBJECT_GID
     *
     * @param hpOdObjectGid the value for HP_OD_CLASS.HP_OD_OBJECT_GID
     * @mbg.generated
     */
    public void setHpOdObjectGid(String hpOdObjectGid) {
        this.hpOdObjectGid = hpOdObjectGid == null ? null : hpOdObjectGid.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_CLASS.HP_OD_CLASS_GID
     *
     * @return the value of HP_OD_CLASS.HP_OD_CLASS_GID
     * @mbg.generated
     */
    public String getHpOdClassGid() {
        return hpOdClassGid;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_CLASS.HP_OD_CLASS_GID
     *
     * @param hpOdClassGid the value for HP_OD_CLASS.HP_OD_CLASS_GID
     * @mbg.generated
     */
    public void setHpOdClassGid(String hpOdClassGid) {
        this.hpOdClassGid = hpOdClassGid == null ? null : hpOdClassGid.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_CLASS.CODE
     *
     * @return the value of HP_OD_CLASS.CODE
     * @mbg.generated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_CLASS.CODE
     *
     * @param code the value for HP_OD_CLASS.CODE
     * @mbg.generated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_CLASS.NAME
     *
     * @return the value of HP_OD_CLASS.NAME
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_CLASS.NAME
     *
     * @param name the value for HP_OD_CLASS.NAME
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_CLASS.DISPLAY_NAME
     *
     * @return the value of HP_OD_CLASS.DISPLAY_NAME
     * @mbg.generated
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_CLASS.DISPLAY_NAME
     *
     * @param displayName the value for HP_OD_CLASS.DISPLAY_NAME
     * @mbg.generated
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName == null ? null : displayName.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_CLASS.DESCRIPTION
     *
     * @return the value of HP_OD_CLASS.DESCRIPTION
     * @mbg.generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_CLASS.DESCRIPTION
     *
     * @param description the value for HP_OD_CLASS.DESCRIPTION
     * @mbg.generated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_CLASS.TABLE_NAME
     *
     * @return the value of HP_OD_CLASS.TABLE_NAME
     * @mbg.generated
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_CLASS.TABLE_NAME
     *
     * @param tableName the value for HP_OD_CLASS.TABLE_NAME
     * @mbg.generated
     */
    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_CLASS.CLASS_NAME
     *
     * @return the value of HP_OD_CLASS.CLASS_NAME
     * @mbg.generated
     */
    public String getClassName() {
        return className;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_CLASS.CLASS_NAME
     *
     * @param className the value for HP_OD_CLASS.CLASS_NAME
     * @mbg.generated
     */
    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * HP_OD_CLASS.IS_PRIMARY
     *
     * @return the value of HP_OD_CLASS.IS_PRIMARY
     * @mbg.generated
     */
    public Boolean getIsPrimary() {
        return isPrimary;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * HP_OD_CLASS.IS_PRIMARY
     *
     * @param isPrimary the value for HP_OD_CLASS.IS_PRIMARY
     * @mbg.generated
     */
    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OD_CLASS
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
        HpOdClass other = (HpOdClass) that;
        return (this.getGid() == null ? other.getGid() == null : this.getGid().equals(other.getGid()))
               && (this.getHpOdObjectGid() == null ? other.getHpOdObjectGid() == null : this.getHpOdObjectGid().equals(other.getHpOdObjectGid()))
               && (this.getHpOdClassGid() == null ? other.getHpOdClassGid() == null : this.getHpOdClassGid().equals(other.getHpOdClassGid()))
               && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
               && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
               && (this.getDisplayName() == null ? other.getDisplayName() == null : this.getDisplayName().equals(other.getDisplayName()))
               && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
               && (this.getTableName() == null ? other.getTableName() == null : this.getTableName().equals(other.getTableName()))
               && (this.getClassName() == null ? other.getClassName() == null : this.getClassName().equals(other.getClassName()))
               && (this.getIsPrimary() == null ? other.getIsPrimary() == null : this.getIsPrimary().equals(other.getIsPrimary()))
               && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
               && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
               && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
               && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
               && (this.getDelete() == null ? other.getDelete() == null : this.getDelete().equals(other.getDelete()))
               && (this.getActive() == null ? other.getActive() == null : this.getActive().equals(other.getActive()));
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OD_CLASS
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGid() == null) ? 0 : getGid().hashCode());
        result = prime * result + ((getHpOdObjectGid() == null) ? 0 : getHpOdObjectGid().hashCode());
        result = prime * result + ((getHpOdClassGid() == null) ? 0 : getHpOdClassGid().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDisplayName() == null) ? 0 : getDisplayName().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getTableName() == null) ? 0 : getTableName().hashCode());
        result = prime * result + ((getClassName() == null) ? 0 : getClassName().hashCode());
        result = prime * result + ((getIsPrimary() == null) ? 0 : getIsPrimary().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDelete() == null) ? 0 : getDelete().hashCode());
        result = prime * result + ((getActive() == null) ? 0 : getActive().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HP_OD_CLASS
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", hpOdObjectGid=").append(hpOdObjectGid);
        sb.append(", hpOdClassGid=").append(hpOdClassGid);
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", displayName=").append(displayName);
        sb.append(", description=").append(description);
        sb.append(", tableName=").append(tableName);
        sb.append(", className=").append(className);
        sb.append(", isPrimary=").append(isPrimary);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
