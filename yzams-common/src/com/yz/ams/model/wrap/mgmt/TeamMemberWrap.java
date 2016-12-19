/*
 * TeamMemberWrap.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 12:23:16
 */
package com.yz.ams.model.wrap.mgmt;

import com.j256.ormlite.table.DatabaseTable;
import com.nazca.usm.model.USMSUser;
import com.yz.ams.model.Employee;
import com.yz.ams.model.TeamMember;
import java.io.Serializable;
import java.util.Objects;

/**
 * 团队成员包装类
 * @author Zhang Chun Nan
 */
@DatabaseTable(tableName = "Team_member_wrap")
public class TeamMemberWrap implements Serializable {
    private static final long serialVersionUID = -5223219538757996354L;
    /**
     * 用户
     */
    private USMSUser user;
    /**
     * 成员信息
     */
    private TeamMember memberInfo;
    /**
     * 员工信息
     */
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    

    public USMSUser getUser() {
        return user;
    }

    public void setUser(USMSUser user) {
        this.user = user;
    }

    public TeamMember getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(TeamMember memberInfo) {
        this.memberInfo = memberInfo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.user);
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
        final TeamMemberWrap other = (TeamMemberWrap) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }



}
