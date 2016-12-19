/*
 * TeamMemberTable.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-04 10:44:40
 */
package com.yz.ams.server.constant;

/**
 *
 * @author Your Name <Song Haixiang >
 */
public enum TeamMemberTable {
   /**
     * 表名
     */
    TABLE_NAME("team_member", 0),
    /**
     * 成员ID
     */
    MEMBER_ID("member_id",64),
  /**
   * 团队ID
   */
    TAEM_ID("tean_id",64),
    /**
     * USMS用户ID
     */
    USE_ID("user_id",64);

   /**
     * 长度
     */
    private int length;
    /**
     * 字段的名称
     */
    private String name;

    public int getLength() {
        return length;
    }

    private TeamMemberTable(String name, int length) {
        this.length = length;
        this.name = name;
    }

    public String getFieldName() {
        return name;
    }
}
 

