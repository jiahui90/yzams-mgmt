/*
 * StaffAttendance.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 10:54:12
 */
package com.yz.ams.model.wrap.app;

import com.nazca.usm.model.USMSUser;
import com.yz.ams.consts.AttendanceOutTypeEnum;
import com.yz.ams.model.Attendance;
import java.io.Serializable;
import java.util.Objects;

/**
 *员工考勤
 * @author luoyongchang
 */
public class StaffAttendance implements Serializable {

    private static final long serialVersionUID = -8670634748265961307L;

    private USMSUser user;  //用户

    private Attendance attendance;  //出勤记录
    
    private AttendanceOutTypeEnum waitType; //等待审核通过的类型：请假/出差/调休

    public AttendanceOutTypeEnum getWaitType() {
        return waitType;
    }

    public void setWaitType(AttendanceOutTypeEnum waitType) {
        this.waitType = waitType;
    }

    public USMSUser getUser() {
        return user;
    }

    public void setUser(USMSUser user) {
        this.user = user;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.user);
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
        final StaffAttendance other = (StaffAttendance) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }
}
