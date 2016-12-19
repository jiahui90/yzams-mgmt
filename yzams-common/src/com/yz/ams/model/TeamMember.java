/*
 * TeamMember.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 10:19:52
 */
package com.yz.ams.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 团队成员表
 *
 * @author Your Name <Song Haixiang >
 */
@DatabaseTable(tableName = "team_member")
public class TeamMember implements Serializable {

    private static final long serialVersionUID = -4483155404780321233L;
    /**
     * 成员ID
     */
    @DatabaseField(id = true, columnName = "member_id", width = 64)
    private String memberId;
    /**
     * 团队ID
     */
    @DatabaseField(columnName = "team_id", width = 64)
    private String teamId;
    /**
     * USMS用户ID
     */
    @DatabaseField(columnName = "user_id", width = 64)
    private String userID;
    
     /**
     * 入职时间
     */
    @DatabaseField(columnName = "entry_time")
    private Date entryTime;
    
    public String getMemberId() {
        return memberId;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.memberId);
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
        final TeamMember other = (TeamMember) obj;
        if (!Objects.equals(this.memberId, other.memberId)) {
            return false;
        }
        return true;
    }

}
