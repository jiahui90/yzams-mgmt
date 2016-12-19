/*
 * PaidVacation.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-04 10:57:24
 */
package com.yz.ams.server.constant;

/**
 *年假管理
 * @author Your Name <Song Haixiang >
 */
public enum PaidVacationTable {
    /**
     * 表名
     *//**
     * 表名
     */
    TABLE_NAME("paid_vacation", 0),
    /**
     * 记录ID
     */
   PV_ID("pv_id", 64),
    /**
     *年度
     */
    PV_YEAR("pv_year", 0),
    /**
     * 用户ID
     */
    USER_ID("user_id", 64),
    /**
     * 法定年假天数
     */
    OFFICIAL_DAYS("official_days", 0),
    /**
     * 内部年假天数
     */
    INNER_DAYS("inner_days", 0),
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

    private PaidVacationTable(String name, int length) {
        this.length = length;
        this.name = name;
    }

    public String getFieldName() {
        return name;
    }
}
