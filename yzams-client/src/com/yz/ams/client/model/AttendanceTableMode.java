/*
 * AttendanceTableMode.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-17 10:42:55
 */
package com.yz.ams.client.model;

import com.nazca.ui.model.AbstractSimpleObjectTableModel;
import com.yz.ams.consts.AttendanceOutTypeEnum;
import com.yz.ams.consts.DelayTypeEnum;
import com.yz.ams.model.Attendance;

/**
 * 出勤表格管理
 *
 * @author Your Name <Song Haixiang >
 */
public class AttendanceTableMode extends AbstractSimpleObjectTableModel<Attendance> {

    public static final int USERNAME = 0;
    public static final int ATTENDANCEDATE = 1;
    public static final int DELAYTYPE = 2;
    public static final int CREATETIME = 3;
    public static final int CREATORNAME = 4;
    private static final String[] COLS = new String[]{"姓名", "考勤日期", "类型", "记录时间", "记录人"};

    public AttendanceTableMode() {
        super(COLS);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Attendance data = dataList.get(rowIndex);
        switch (columnIndex) {
            case USERNAME://姓名
                return data.getUserName();
            case ATTENDANCEDATE://考勤日期
                return data.getAttendanceDate();
            case DELAYTYPE://类型
                String type = "";
                if(data.getDelayType() != null){
                    if(data.getDelayType() == DelayTypeEnum.LIGHT) {
                        type += "轻微迟到";
                    }else if(data.getDelayType() == DelayTypeEnum.NORMAL){
                        type += "迟到";
                    }else if(data.getDelayType() == DelayTypeEnum.SERIOUS){
                        type += "严重迟到";
                    }
                }
                if(data.isLeaveEarly() == true ){
                    if("".equals(type)){
                        type += "早退";
                    }else{
                        type += ";早退";
                    }
                }
                if(data.getOutType() !=null){
                    if(!"".equals(type)){
                        type += ";";
                    }
                    if(data.getOutType() == AttendanceOutTypeEnum.BIZ_TRIP) {
                        type += "出差";
                    }else if(data.getOutType() == AttendanceOutTypeEnum.REST){
                        type += "调休";
                    }else if(data.getOutType() == AttendanceOutTypeEnum.VACATION){
                        type += "请假";
                    }
                }
                if(data.getAbsentDays() > 0){
                    if("".equals(type)){
                        type += "旷工";
                    }else{
                        type += ";旷工";
                    }
                }
                return type;
            case CREATETIME://记录时间
                return data.getCreateTime();
            case CREATORNAME://记录时间
                return data.getCreatorName();
            default:
                return "";
        }

    }

}
