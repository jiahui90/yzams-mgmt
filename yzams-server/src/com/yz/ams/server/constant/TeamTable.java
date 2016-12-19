/*
 * TeamTable.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-04 10:37:29
 */
package com.yz.ams.server.constant;

/**
 *团队管理
 * @author Your Name <Song Haixiang >
 */
public enum TeamTable {
    
  /**
     * 表名
     */
    TABLE_NAME("team", 0),
  /**
   * 团队ID
   */
    TAEM_ID("tean_id",64),
    /**
     * 团队名称
     */
    TEAM_NAME("team_name",64);

   
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

    private TeamTable(String name, int length) {
        this.length = length;
        this.name = name;
    }

    public String getFieldName() {
        return name;
    }
}
