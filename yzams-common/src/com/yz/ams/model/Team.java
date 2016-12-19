/*
 * Team.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 10:19:10
 */
package com.yz.ams.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import java.util.Objects;

/**
 * 团队管理
 *
 * @author Your Name <Song Haixiang >
 */
@DatabaseTable(tableName = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = 7686971473558389896L;
    /**
     * 团队ID
     */
    @DatabaseField(id = true, columnName = "team_id", width = 64)
    private String teamId;
    /**
     * 团队名称
     */
    @DatabaseField(columnName = "team_name", width = 64)
    private String teamName;

      /**
     * 团队是否为管理团队
     */
    @DatabaseField(columnName = "is_mgmt", width = 1)
    private boolean isMgmt;

    public boolean isIsMgmt() {
        return isMgmt;
    }

    public void setIsMgmt(boolean isMgmt) {
        this.isMgmt = isMgmt;
    }
    
    
    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.teamId);
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
        final Team other = (Team) obj;
        if (!Objects.equals(this.teamId, other.teamId)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return teamName;
    }

}
