/*
 * VacationTypeEnum.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 10:34:04
 */
package com.yz.ams.consts;

/**
 * 请假类型
 * @author Zhang Chun Nan
 */
public enum VacationTypeEnum {
    PERSONAL(){
        @Override
        public String toString() {
            return "事假";
        }
    },
    SICK(){
        @Override
        public String toString() {
            return "病假";
        }
    },
    PAID_LEGAL(){
        @Override
        public String toString() {
            return "法定年假";
        }
    },
    PAID_INNER(){
        @Override
        public String toString() {
            return "内部年假";
        }
    },
    ANNUAL_LEAVE(){
        @Override
        public String toString() {
            return "年假";
        }
    },
    WEDDING(){
        @Override
        public String toString() {
            return "婚假";
        }
    },
    BIRTH(){
        @Override
        public String toString() {
            return "产假";
        }
    },
    NURSING(){
        @Override
        public String toString() {
            return "护理假";
        }
    },
    FUNERAL(){
        @Override
        public String toString() {
            return "吊唁假";
        }
    }
}
