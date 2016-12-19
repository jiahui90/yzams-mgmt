/*
 * DalayTypeEnum.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 10:45:50
 */
package com.yz.ams.consts;

/**
 * 迟到类型
 * @author Zhang Chun Nan
 */
public enum AttendanceOutTypeEnum {
    BIZ_TRIP(){
        @Override
        public String toString() {
            return "出差";
        }
    },
    REST(){
        @Override
        public String toString() {
            return "调休";
        }
    },
    VACATION(){
        @Override
        public String toString() {
            return "请假";
        }
    }
}
