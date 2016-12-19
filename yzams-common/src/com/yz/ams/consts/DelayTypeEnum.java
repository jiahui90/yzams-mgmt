/*
 * DelayTypeEnum.java
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
public enum DelayTypeEnum {
    LIGHT(){
        @Override
        public String toString() {
            return "轻微";
        }
    },
    NORMAL(){
        @Override
        public String toString() {
            return "一般";
        }
    },
    SERIOUS(){
        @Override
        public String toString() {
            return "严重";
        }
    }
}
