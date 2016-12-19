/*
 * StaffAttendancesWrap.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 12:40:31
 */
package com.yz.ams.model.wrap.mgmt;

import com.nazca.usm.model.USMSUser;
import com.yz.ams.model.Attendance;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *员工出勤包装类
 * @author Zhang Chun Nan
 */
public class StaffAttendancesWrap implements Serializable {
    private static final long serialVersionUID = -1846939391147946754L;
    /**
     * 用户
     */
    private USMSUser user;
  /**
   * 员工出勤表
   */
    private List<Attendance> attendances;

    public USMSUser getUser() {
        return user;
    }

    public void setUser(USMSUser user) {
        this.user = user;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.user);
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
        final StaffAttendancesWrap other = (StaffAttendancesWrap) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }


}
