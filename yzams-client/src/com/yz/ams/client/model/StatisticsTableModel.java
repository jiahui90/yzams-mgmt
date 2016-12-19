/*
 * StatisticsTableModel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-22 10:34:55
 */
package com.yz.ams.client.model;

import com.nazca.ui.model.AbstractSimpleObjectTableModel;
import com.nazca.usm.client.util.ComparatorTool;
import com.yz.ams.model.wrap.mgmt.AttendanceMgmtStat;

/**
 * 年假信息的model
 *
 * @author Your Name <zhaohongkun@yzhtech.com>
 */
public class StatisticsTableModel extends AbstractSimpleObjectTableModel<AttendanceMgmtStat> {

    public static final int USERNAME = 0;
    public static final int JOBNUMBER = 1;
    public static final int LEGALATTENDANCE = 2;
    public static final int NORMALATTENDACEDAYS = 3;
    public static final int ANNUALLEAVEDAYS = 4;
    public static final int PERSONALDAYS = 5;
    public static final int SICKDAYS = 6;
    public static final int PAIDLEGADAYS= 7;
    public static final int BUSINESSIDAYSE = 8;
    public static final int RESTDAYS = 9;
    public static final int ABSENTDAYS = 10;
    public static final int LIGHTLATETIME = 11;
    public static final int PUNISHMENTLIGHTLATETIME= 12;
    public static final int LATETIME = 13;
    public static final int SERIOUSLATETIME = 14;
    public static final int EARLYTIME = 15;

    double sumLastYear;
    private static final String[] COLS = new String[]{"姓名", "工号", "法定工作日", "正常出勤", "年假", "事假", "病假",
        "其它假", "出差", "调休","旷工","轻微迟到","处罚轻微迟到次数","迟到","严重迟到","早退"};

    public StatisticsTableModel() {
        super(COLS);

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AttendanceMgmtStat data = dataList.get(rowIndex);
        switch (columnIndex) {
            case USERNAME://姓名
                return data.getUserName();
            case JOBNUMBER://工号
                return data.getJobNumber();
            case LEGALATTENDANCE://法定工作日
                return data.getLegalAttendanceDays();
            case NORMALATTENDACEDAYS://正常出勤
                return data.getNormalAttendanceDays();
            case ANNUALLEAVEDAYS://年假
                return data.getAnnualLeaveDays();
            case PERSONALDAYS://事假
                return data.getPersonalDays();
            case SICKDAYS://病假
                return data.getSickDays();
            case PAIDLEGADAYS://其它假
                return data.getPaidLegaDays();
            case BUSINESSIDAYSE://出差
                return data.getBusinessDays();
            case RESTDAYS://调休
                return data.getRestDays();
            case ABSENTDAYS://旷工
                return data.getAbsentDays();
            case LIGHTLATETIME://轻微迟到
                return data.getLightLateTime();
            case PUNISHMENTLIGHTLATETIME://处罚轻微迟到次数
                return data.getPunishmentLightLateTime();
            case LATETIME://迟到次数
                return data.getLateTime();
            case SERIOUSLATETIME://严重迟到
                return data.getSeriousLateTime();
            case EARLYTIME://早退
                return data.getEarlyTime();
            default:
                return "";
        }
    }

    public int findNext(String keywords, int start) {
        int result = -1;
        if (start >= 0) {
            result = find(keywords, start + 1);
        }
        if (result == -1) {
            result = find(keywords, 0);
        }
        return result;
    }

    private int find(String keywords, int start) {
        for (int i = start; i < dataList.size(); i++) {
            AttendanceMgmtStat wrap = dataList.get(i);
            if (null != wrap) {
                String userName = wrap.getUserName();
                if (ComparatorTool.matchPinYin(userName,keywords)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
}
