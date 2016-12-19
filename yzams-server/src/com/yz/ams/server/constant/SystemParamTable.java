/*
 * SystemParamTable.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-04 14:44:19
 */
package com.yz.ams.server.constant;

/**
 *系统参数表
 * @author Your Name <Song Haixiang >
 */
public enum SystemParamTable {
 /**
     * 表名
     *//**
     * 表名
     */
    TABLE_NAME("system_param", 0),
    /**
     * 参数KEY
     */
   PARAM_KEY("param_key", 32),
    /**
     *参数值
     */
    PARAM_VALUE("param_value", 64),
    /**
     * 创建者ID
     */
    CREATOR_ID("creator_id", 64),
    /**
     * 创建者姓名
     */
   CREATE_NAME("create_name", 64),
   /**
    * 创建时间
    */
   CREATOR_TIME("create_time", 0),
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

    private SystemParamTable(String name, int length) {
        this.length = length;
        this.name = name;
    }

    public String getFieldName() {
        return name;
    }
}
