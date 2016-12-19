/*
 * BizTripTable.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-04 09:53:26
 */
package com.yz.ams.server.constant;

/**
 *出差表
 * @author Your Name <Song Haixiang >
 */
public enum BizTripTable {
  /**
     * 表名
     *//**
     * 表名
     */
    TABLE_NAME("biz_trip", 0),
    /**
     * ID
     */
    BIZ_TRIP_ID("biz_trip_id",64),
    /**
     * 申请人ID
     */
    APPLICANT_ID("applicant_id",64),
    /**
     * 申请人姓名
     */
    APPLICANT_NAME("applicant_name",64),
    /**
     * 出差地点
     */
    LOCATION("location",256),
    /**
     * 出差开始时间
     */
    START_DATE ("start_date ",0),
    /**
     * 是否上午
     */
    MORNING ("morning ",0),
    /**
     * 天数
     */
    DAYS("days",0),
    /**
     * 人员列表，
     */
    STAFF_IDS("staff_ids",1024),
    /**
     * 审核状态
     */
    AUDIT_STATE("audit_state",16),
    /**
     * 事由
     */
    MEMO("memo",64),
    /**
     * 审核人员ID
     */
    AUDITOR_ID("auditor_id",64),
    /**
     * 审核人团姓名
     */
    AUDITOR_NAME("auditor_name",64),
    /**
     * 审核时间
     */
    AUDIT_TIME("audit_time",0),
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

    private BizTripTable(String name, int length) {
        this.length = length;
        this.name = name;
    }

    public String getFieldName() {
        return name;
    }
}
