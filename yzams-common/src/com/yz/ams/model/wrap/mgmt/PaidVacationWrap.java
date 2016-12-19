/*
 * PaidVacationWrap.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-02 10:02:07
 */
package com.yz.ams.model.wrap.mgmt;

import com.yz.ams.model.PaidVacation;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *年假实体类，上一件剩余年假信息，今年剩余年假信息，总年假天数的封装类
 * @author 赵洪坤 <zhaohongkun@yzhtech.com>
 */
public class PaidVacationWrap implements Serializable{
   private static final long serialVersionUID = -5905567296228508234L;
    /**
     * 年假信息
     */
    private PaidVacation paidVacationinfo;
    
    private  double haveTakePaidVocationYear;
    
    private double remainPaidVacationYear;
    
    private Date entryTime;
    
    private String userName;
    
    private String employeeNumber;

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

    public double getRemainPaidVacationYear() {
        return remainPaidVacationYear;
    }

    public void setRemainPaidVacationYear(double remainPaidVacationYear) {
        this.remainPaidVacationYear = remainPaidVacationYear;
    }

    public double getHaveTakePaidVocationYear() {
        return haveTakePaidVocationYear;
    }

    public void setHaveTakePaidVocationYear(double haveTakePaidVocationYear) {
        this.haveTakePaidVocationYear = haveTakePaidVocationYear;
    }
    
    public PaidVacation getPaidVacationinfo() {
        return paidVacationinfo;
    }

    public void setPaidVacationinfo(PaidVacation paidVacationinfo) {
        this.paidVacationinfo = paidVacationinfo;
    }
    
     @Override
    public int hashCode() {
        int hash = 7;
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
        final PaidVacationWrap other = (PaidVacationWrap) obj;
        if (!Objects.equals(this.paidVacationinfo, other.paidVacationinfo)) {
            return false;
        }
        return true;
    }
}
