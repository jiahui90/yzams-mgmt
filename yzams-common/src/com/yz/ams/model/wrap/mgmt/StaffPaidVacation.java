/*
 * StaffPaidVacation.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 12:04:40
 */
package com.yz.ams.model.wrap.mgmt;

import com.j256.ormlite.table.DatabaseTable;
import com.nazca.usm.model.USMSUser;
import com.yz.ams.model.PaidVacation;
import java.io.Serializable;
import java.util.Objects;

/**
 *员工带薪假期
 * @author Zhang Chun Nan
 */@DatabaseTable(tableName = "staff_paid_vacation")
public class StaffPaidVacation implements Serializable{
    private static final long serialVersionUID = 3297214259232063647L;
    /**
     * 用户
     */
    private USMSUser user;
    /**
     * 带薪假期
     */
    private PaidVacation paidVacation;

    public USMSUser getUser() {
        return user;
    }

    public void setUser(USMSUser user) {
        this.user = user;
    }

    public PaidVacation getPaidVacation() {
        return paidVacation;
    }

    public void setPaidVacation(PaidVacation paidVacation) {
        this.paidVacation = paidVacation;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.user);
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
        final StaffPaidVacation other = (StaffPaidVacation) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }

 
 
 }
