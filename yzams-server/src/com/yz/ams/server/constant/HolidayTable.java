/*
 * HolidayTable.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-04 10:50:46
 */
package com.yz.ams.server.constant;

/**
 *
 * @author Your Name <Song Haixiang >
 */
public enum HolidayTable {
    /**
     * 表名
     *//**
     * 表名
     */
    TABLE_NAME("holiday", 0),
    /**
     * 成员ID
     */
    HOLIDAY_ID("holiday_id",64),
  /**
   * 日期
   */
    HOLIDAY_DATE("holiday_date",0),
    /**
     * 类型
     */
    HOLIDAY_TYPE("holiday_type",16),
    /**
     * 名称
     */
    HOLIDAY_NAME("holiday_name",8),
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

    private HolidayTable(String name, int length) {
        this.length = length;
        this.name = name;
    }

    public String getFieldName() {
        return name;
    }
}
 

