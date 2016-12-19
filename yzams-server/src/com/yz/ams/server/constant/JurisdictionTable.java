/*
 * JurisdictionTable.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-04 14:52:48
 */
package com.yz.ams.server.constant;

/**
 *
 * @author Your Name <Song Haixiang >
 */
public enum JurisdictionTable {
 
    /**
     * 表名
     */
    TABLE_NAME("jurisdiction", 0),
    /**
     * 超级管理员
     */
   ADMIN("admin", 0),
    /**
     *总经理
     */
    CEO("ceo", 0),
    /**
     *项目主管
     */
    PM("pm", 0),
    /**
     * 人事主管
     */
    HR("hr", 0),
    /**
     * 普通员工
     */
    STAFF("staff", 0);
   
   
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

    private JurisdictionTable(String name, int length) {
        this.length = length;
        this.name = name;
    }

    public String getFieldName() {
        return name;
    }
}

