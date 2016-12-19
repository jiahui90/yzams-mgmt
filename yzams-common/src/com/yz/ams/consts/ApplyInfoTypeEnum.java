/*
 * ApplyInfoTypeEnum.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 11:27:22
 */
package com.yz.ams.consts;

/**
 * 申请记录类型
 * @author Zhang Chun Nan
 */
public enum ApplyInfoTypeEnum {
    VACATION(){
        @Override
        public String toString() {
            return "请假";
        }
    },
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
}
