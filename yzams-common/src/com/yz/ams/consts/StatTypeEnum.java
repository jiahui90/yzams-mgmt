/*
 * StatTypeEnum.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 12:32:23
 */
package com.yz.ams.consts;

/**
 *
 * @author Zhang Chun Nan
 */
public enum StatTypeEnum {
    WEEK(){
        @Override
        public String toString() {
            return "周统计";
        }
    },
    MONTH(){
        @Override
        public String toString() {
            return "月统计";
        }
    },
    YEAR(){
        @Override
        public String toString() {
            return "年统计";
        }
    }
}
