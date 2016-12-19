/*
 * AuditState.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 09:28:27
 */
package com.yz.ams.consts;

/**
 * 审核状态
 *
 * @author Zhang Chun Nan
 */
public enum AuditStateEnum {
    WAIT_FOR_PM() {
        @Override
        public String toString() {
            return "待一审";
        }
    },
    WAIT_FOR_CEO() {
        @Override
        public String toString() {
            return "待二审";
        }
    },
    PASS() {
        @Override
        public String toString() {
            return "通过";
        }
    },
    DENY() {
        @Override
        public String toString() {
            return "不通过";
        }
    }
}
