/*
 * VacationDetailTable.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-04 09:33:33
 */
package com.yz.ams.server.constant;

/**
 *
 * 请假详情表
 * @author Your Name <Song Haixiang >
 */
public enum VacationDetailTable {
    /**
     * 表名
     */
   TABLE_NAME("vacation_detail",0),
    /**
     * 记录UUID
     */
   DETAIL_ID("detail_id",64), 
   /**
    * 请假记录ID
    */
  VACATION_ID("vacation_id",64),
   /**
    * 类型
    */
  VACATION_TYPE("vacation_type",16), 
   /**
    * 天数
    */
   VACATION_DAYS("vacation_days",0);
 

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

    private VacationDetailTable(String name, int length) {
        this.length = length;
        this.name = name;
    
}
}
