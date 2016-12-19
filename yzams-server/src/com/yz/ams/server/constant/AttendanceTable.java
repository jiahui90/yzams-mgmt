/*
 * BusDyLineStat5mTable.java
 * 
 * Copyright(c) 2007-2015 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2015-09-09 13:58:35
 */
package com.yz.ams.server.constant;

/**
 *出勤表
 * @author Zhu Mengchao
 */
public enum AttendanceTable {
    /**
     * 表名
     *//**
     * 表名
     */
    TABLE_NAME("attendance", 0),
    /**
     * 记录UUID
     */
    ATTENDANCE_ID("attendance_id", 64),
    /**
     * 记录日期
     */
    ATTENDANCE_DATE("attendance_date", 0),
    /**
     * 用户ID
     */
    USER_ID("user_id", 64),
    /**
     * 用户姓名
     */
    USER_NAME("user_name", 0),
    /**
     * 迟到类型
     */
    DELAY_TPE("delay_type", 16),
    /**
     * 是否早退
     */
    LEAVE_EARLY("leave_early", 0),
    /**
     * 外出类型
     */
    OUT_TYPE("out_type", 16),
    /**
     * 旷工天数
     */
    ABSENT_DAYS("absent_days", 0),
    /**
     * 是否删除
     */
    DELETED("deleted", 0),
    /**
     * 创建者ID
     */
    CREATOR_ID("creator_id", 64),
    /**
     * 创建者姓名
     */
   CREATOR_NAME("creator_name", 64),
   /**
    * 创建时间
    */
   CREATE_TIME("create_time", 0),
   /**
    * 修改者ID
    */
   MODIFIER_ID("modifier_id",64),
   /**
    * 修改者姓名
    */
   MODIFIER_NAME("modifier_name",64),
   /**
    * 修改时间
    */
   MODIFY_TIME("modify_time",0);

   
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

    private AttendanceTable(String name, int length) {
        this.length = length;
        this.name = name;
    }

    public String getFieldName() {
        return name;
    }
}
