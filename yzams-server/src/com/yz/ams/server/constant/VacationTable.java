/*
 * TestTable.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-03 17:13:50
 */
package com.yz.ams.server.constant;

/**
 *请假表
 * @author Your Name <Song Haixiang >
 */
public enum VacationTable {
    /**
     * 表名
     *//**
     * 表名
     */
    TABLE_NAME("vacation", 0),
    /**
     * 记录UUID
     */
    VACATION_ID("vacation_id", 64),
    /**
     * 请假日期
     */
    VACATION_DATE("vacation_date", 0),
    /**
     * 是否上午
     */
    MORNING("morning", 0),
    /**
     * 请假人ID
     */
    APPLICANT_ID("applicant_id", 64),
    /**
     * 请假人姓名
     */
    APPLICANT_NAME("applicant_name", 64),
    /**
     * 请假天数
     */
    TOTAL_DAYS("total_days", 0),
    /**
     * 请假事由
     */
    MEMO("memo", 64),
    /**
     * 审核状态
     */
    AUDIT_STATE("audit_state", 16),
    /**
     * 是否有病假
     */
    HAS_SICK_TYPE("has_sick_type", 0),
    /**
     * 病假申请状态
     */
    CERTIFICATE_STATE("certificate_state", 16),
    /**
     * 病假图片ID
     */
    CERTIFICATE_PIC_ID("certificate_pic_id",64),
    /**
     * 病假图片上传时间
     */
    CERTIFICATE_UPLOAD_TIME("certificate_upload_time",0),
    /**
     * 一审人员ID
     */
   AUDITOR_ID_1("auditor_id_1",64),
   /**
    * 一审人员姓名
    */
   AUDITOR_NAME_1("auditor_name_1",64),
   /**
    * 一审时间
    */
   AUDIT_TIME_1("audit_time_1",0),    /**
     * 二审人员ID
     */
   AUDITOR_ID_2("auditor_id_2",64),
   /**
    * 二审人员姓名
    */
   AUDITOR_NAME_2("auditor_name_2",64),
   /**
    * 二审时间
    */
   AUDIT_TIME_2("audit_time_2",0),
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

    private VacationTable(String name, int length) {
        this.length = length;
        this.name = name;
    }

    public String getFieldName() {
        return name;
    }
}
