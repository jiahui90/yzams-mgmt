/*
 * HolidayTypeEnum.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 12:00:34
 */
package com.yz.ams.consts;

/**
 *
 * @author Zhang Chun Nan
 */
public enum HolidayTypeEnum {
    HOLIDAY(){
        @Override
        public String toString() {
            return "节假日";
        }
    },
    WORKDAY(){
        @Override
        public String toString() {
            return "工作日";
        }
    }
}
