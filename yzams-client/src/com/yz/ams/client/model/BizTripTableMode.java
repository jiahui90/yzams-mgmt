/*
 * BizTripTableMode.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-15 11:47:52
 */
package com.yz.ams.client.model;

import com.nazca.ui.model.AbstractSimpleObjectTableModel;
import com.nazca.usm.model.USMSUser;
import com.yz.ams.client.util.StaticDataUtil;
import com.yz.ams.util.DateUtil;
import com.yz.ams.model.BizTrip;
import java.util.Map;


/**
 * 出差表格管理
 *
 * @author Your Name <Song Haixiang >
 */
public class BizTripTableMode extends AbstractSimpleObjectTableModel<BizTrip> {
    public static final int APPLICANTNAME = 0;
    public static final int LOCATION = 1;
    public static final int STARDATE = 2;
    public static final int DAYS = 3;
    public static final int FINISHTIME = 4;
    public static final int STAFF = 5;
    public static final int AUDITSTATE = 6;
    private static final String[] COLS = new String[]{"申请人", "地点", "开始时间", "天数", "结束时间", "出差人员" ,"状态",};

    public BizTripTableMode() {
        super(COLS);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BizTrip data = dataList.get(rowIndex);
        Map<String, USMSUser> userMap = StaticDataUtil.getUserMap();
        switch (columnIndex) {
            case APPLICANTNAME://申请人
                return data.getApplicantName();
            case LOCATION://地点
                return data.getLocation();
            case STARDATE://开始时间
                return DateUtil.catStartTime(data.getStartDate(), data.isMorning());
            case DAYS://天数
                return data.getDays();
            case FINISHTIME://结束时间
                return DateUtil.catEndTime(data.getEndDate());
            case AUDITSTATE://状态
                return data.getAuditState();
            case STAFF://出差人员
                String ids = data.getStaffIds();
                String names = "";
                if(ids != null){
                    if(ids.contains(",")){
                    String[] idStr = ids.split(",");
                    for(int i=0; i<idStr.length; i++){
                        if(userMap.get(idStr[i]) != null){
                            names += userMap.get(idStr[i]).getName();
                            names += ",";
                        }
                    }
                        names = names.substring(0,names.length() - 1);
                    }else{
                        names = userMap.get(ids)!= null ? userMap.get(ids).getName() : "";
                    }
                }
                return names;
            default:
                return "";
        }
    }
}
