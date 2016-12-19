/*
 * SystemParam.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 12:09:19
 */
package com.yz.ams.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.yz.ams.consts.SystemParamKey;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 系统参数
 *
 * @author Zhang Chun Nan
 */
public class SystemParam implements Serializable {
    private static final long serialVersionUID = -8668144049919961684L;
    /**
     * 参数KEY
     */
    @DatabaseField(id = true, columnName = "param_key", width = 32, dataType = DataType.ENUM_STRING)
    private SystemParamKey paramKey;
    /**
     * 参数值
     */
    @DatabaseField(columnName = "param_value", width = 64)
    private String paramValue;
    /**
     * 修改者ID
     */
    @DatabaseField(columnName = "modifier_id", width = 64)
    private String modifierId;
    /**
     * 修改者姓名
     */
    @DatabaseField(columnName = "modifier_name", width = 64)
    private String modifierName;
    /**
     * 修改时间
     */
    @DatabaseField(columnName = "modify_time", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    public SystemParamKey getParamKey() {
        return paramKey;
    }

    public void setParamKey(SystemParamKey paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.paramKey);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SystemParam other = (SystemParam) obj;
        if (this.paramKey != other.paramKey) {
            return false;
        }
        return true;
    }

}
