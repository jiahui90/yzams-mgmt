/*
 * RestTableMode.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-17 10:42:18
 */
package com.yz.ams.client.model;

import com.nazca.ui.model.AbstractSimpleObjectTableModel;
import com.nazca.usm.model.USMSUser;
import com.nazca.util.StringUtil;
import com.yz.ams.client.util.StaticDataUtil;
import com.yz.ams.util.DateUtil;
import com.yz.ams.model.Rest;
import java.util.Map;

/**
 *调休model
 * @author Your Name <Song Haixiang >
 */
public class RestTableMode extends AbstractSimpleObjectTableModel<Rest> {
    public static final int USERNAME = 0;
    public static final int STARTDATE = 1;
    public static final int DAYS = 2;
    public static final int ENDDATE = 3;
    public static final int STAFF = 4;
    public static final int MEMO = 5;
    public static final int AUDITSTATE = 6;
    private static final String[] COLS = new String[]{"申请人", "开始时间", "天数", "结束时间","调休人员", "事由", "状态"};

    public RestTableMode() {
        super(COLS);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Rest data = dataList.get(rowIndex);
        Map<String, USMSUser> userMap = StaticDataUtil.getUserMap();
        switch (columnIndex) {
            case USERNAME://申请人
                return data.getUserName();
            case STARTDATE://开始时间
                return DateUtil.catStartTime(data.getStartDate(), data.isMorning());
            case DAYS://天数
                return data.getDays();
            case ENDDATE://结束时间
                return DateUtil.catEndTime(data.getEndDate());
            case STAFF://调休人员
                String ids = data.getStaffIds();
                String names = "";
                if(!StringUtil.isEmpty(ids)){
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
            case MEMO://事由
                return StringUtil.isEmpty(data.getMemo())  ?  "--" : data.getMemo() ;
            case AUDITSTATE://状态
                return data.getAuditState();
            default:
                return "";
        }
    }
}
