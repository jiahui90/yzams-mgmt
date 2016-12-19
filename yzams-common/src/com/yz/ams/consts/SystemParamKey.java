/*
 * SystemParamKey.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 12:09:46
 */
package com.yz.ams.consts;

/**
 * 系统参数值
 * @author Zhang Chun Nan
 */
public enum SystemParamKey {
    WORK_START_TIME_AM(){
        @Override
        public String toString() {
            return "上午工作开始时间";
        }
    },
    WORK_END_TIME_AM(){
        @Override
        public String toString() {
            return "上午工作结束时间";
        }
    },
    WORK_START_TIME_PM(){
        @Override
        public String toString() {
            return "下午工作开始时间";
        }
    },
    WORK_END_TIME_PM(){
        @Override
        public String toString() {
            return "下午工作结束时间";
        }
    },
    LIGHT_DELAY_MINUTES(){
        @Override
        public String toString() {
            return "轻微迟到分钟数";
        }
    },
    NORMAL_DELAY_MINUTES(){
        @Override
        public String toString() {
            return "一般迟到分钟数";
        }
    },
    SERIOUS_DELAY_MINUTES(){
        @Override
        public String toString() {
            return "严重迟到分钟数";
        }
    },
    PAID_VACATION_ONE_YEAR(){
        @Override
        public String toString() {
            return "1年以上年假天数";
        }
    },
    PAID_VACATION_TEN_YEAR(){
        @Override
        public String toString() {
            return "10年以上年假天数";
        }
    },
    PAID_VACATION_TWENTY_YEAR(){
        @Override
        public String toString() {
            return "20年以上年假天数";
        }
    },
    PAID_VACATION_INNER(){
        @Override
        public String toString() {
            return "公司内部年假天数";
        }
    }
}
