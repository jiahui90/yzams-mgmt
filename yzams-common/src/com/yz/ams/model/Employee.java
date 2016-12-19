/*
 * Employee.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-15 15:17:46
 */
package com.yz.ams.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *员工表
 * @author 赵洪坤 <zhaohongkun@yzhtech.com>
 */
@DatabaseTable(tableName = "employee")
public class Employee implements Serializable{
    
    private static final long serialVersionUID = -6083177171796043005L;
    /**
     * 用户UUID
     */
    @DatabaseField(id = true, columnName = "user_id", width = 64)
    private String userId;
    /**
     * 员工入职姓名
     */
    @DatabaseField(columnName = "entry_time", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd")
    private Date entryTime;
    /**
     * 员工姓名
     */
    @DatabaseField(columnName = "user_name", width = 64)
    private String userName;
    /**
     * 员工号
     */
    @DatabaseField(columnName = "employee_number", width = 64)
    private String employeeNumber;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.userId);
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
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  "  " + userName;
    }
    
}
