/*
 * SickCertificateStateEnum.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 11:30:46
 */
package com.yz.ams.consts;

/**
 * 病假证明确认状态
 * @author Zhang Chun Nan
 */
public enum SickCertificateStateEnum {
    PENDING(){
        @Override
        public String toString() {
            return "待上传";
        }
    },
    WAIT(){
        @Override
        public String toString() {
            return "待审核";
        }
    },
    PASS(){
        @Override
        public String toString() {
            return "审核通过";
        }
    },
    DENY(){
        @Override
        public String toString() {
            return "审核不通过";
        }
    }
}
