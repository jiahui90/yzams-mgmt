/*
 * VacationTableModel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-05 17:27:59
 */
package com.yz.ams.client.model;

import com.nazca.ui.model.AbstractSimpleObjectTableModel;
import com.nazca.util.StringUtil;
import com.yz.ams.util.DateUtil;
import com.yz.ams.model.Vacation;
import com.yz.ams.model.wrap.mgmt.VacationInfo;

/**
 * 请假管理表格
 *
 * @author Your Name <Song Haixiang >
 */
public class VacationTableModel extends AbstractSimpleObjectTableModel<VacationInfo> {

    public static final int VACATIONNAME = 0;
    public static final int AUDITORNAME = 1;
    public static final int STARTTIME = 2;
    public static final int VACATIONDAYS = 3;
    public static final int FINISHTIME = 4;
    public static final int AUDITSTATE = 5;
    private static final String[] COLS = new String[]{"申请人", "审批人", "开始时间", "天数", "结束时间", "状态"};

    public VacationTableModel() {
        super(COLS);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        VacationInfo data = dataList.get(rowIndex);
        Vacation vac = data.getVacation();
        switch (columnIndex) {
            case VACATIONNAME://申请人
                return vac.getApplicantName();
            case AUDITORNAME://审批人
                String auditorName;
                String auditorName1 = vac.getAuditorName1();
                String auditorName2 = vac.getAuditorName2();
                if(StringUtil.isEmpty(auditorName1)){
                    if(StringUtil.isEmpty(auditorName2)){
                        auditorName = null;
                    }else{
                        auditorName = auditorName2;
                    }
                }else{
                    if(StringUtil.isEmpty(auditorName2)){
                        auditorName = auditorName1;
                    }else{
                        auditorName = auditorName2;
                    }
                }
                return auditorName;
                
            case STARTTIME://开始时间
                return DateUtil.catStartTime(vac.getVacationDate(), vac.isMorning());
            case VACATIONDAYS://天数
                return vac.getTotalDays();
            case FINISHTIME://结束时间
                if (data.getEndDate() == null) {
                    return "-";
                }else{
                    return DateUtil.catEndTime(data.getEndDate());
                }
            case AUDITSTATE://状态
                return vac.getAuditState();
            default:
                return "";
        }
    }
}
