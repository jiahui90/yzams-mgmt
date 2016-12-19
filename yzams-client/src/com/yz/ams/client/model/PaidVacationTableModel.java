/*
 * PaidVacationTableModel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-22 10:34:55
 */
package com.yz.ams.client.model;

import com.nazca.ui.model.AbstractSimpleObjectTableModel;
import com.nazca.usm.client.util.ComparatorTool;
import com.yz.ams.util.DateUtil;
import com.yz.ams.model.wrap.mgmt.PaidVacationWrap;
import java.text.ParseException;
import java.util.Date;

/**
 * 年假信息的model
 *
 * @author Your Name <zhaohongkun@yzhtech.com>
 */
public class PaidVacationTableModel extends AbstractSimpleObjectTableModel<PaidVacationWrap> {

    public static final int USERNAME = 0;
    public static final int WORKNUMBER = 1;
    public static final int TAKINGWORKDATE = 2;
    public static final int TAKINGWORKYEAR = 3;
    public static final int OFFICIALDAYS = 4;
    public static final int INNERDAYS = 5;
    public static final int LASTYEARREMAINDAYS = 6;
    public static final int TOTALVACATIONDAYS = 7;
    public static final int HAVATAKEVACATION = 8;
    public static final int REMAINVACATION = 9;

    double sumLastYear = 0;
    private static final String[] COLS = new String[]{"姓名", "工号", "入职时间", "入职年限", "法定年假", "内部年假", "上年度剩余年假",
        "总年假天数", "已休年假", "剩余年假"};

    public PaidVacationTableModel() {
        super(COLS);

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PaidVacationWrap data = dataList.get(rowIndex);
        Date systemTime = new Date();
        double officeDays = 0;
        double innerDays = 0;
        double lastYearDays =0;
         if(data.getPaidVacationinfo() !=null &&data.getPaidVacationinfo().getOfficialDays() != null){
            officeDays = data.getPaidVacationinfo().getOfficialDays();
        }
          if(data.getPaidVacationinfo() !=null &&data.getPaidVacationinfo().getInnerDays() != null){
            innerDays = data.getPaidVacationinfo().getInnerDays();
        }
          if(data.getPaidVacationinfo() !=null){
            lastYearDays = data.getPaidVacationinfo().getLastYearDays();
        }
        double totalVacationDays = officeDays + innerDays + lastYearDays;
        double haveTakePaidVacationDays = data.getHaveTakePaidVocationYear();
        switch (columnIndex) {
            case USERNAME://姓名
                
                return data.getUserName() != null ? data.getUserName():null;
                
            case WORKNUMBER://工号
                
                return data.getEmployeeNumber() != null ? data.getEmployeeNumber():null;
                
            case TAKINGWORKDATE://入职时间
                
                return data.getEntryTime() != null ? data.getEntryTime():null;
                
            case TAKINGWORKYEAR:
            try {
                //入职年限
                return data.getEntryTime() != null ? DateUtil.getDateDiffence(data.getEntryTime(),systemTime):null;
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        
                 
            case OFFICIALDAYS://法定年假天数
                if(data.getPaidVacationinfo() !=null &&data.getPaidVacationinfo().getOfficialDays() != null){
                    return data.getPaidVacationinfo().getOfficialDays();
                }else{
                    return 0;
                }
            case INNERDAYS://内部年假天数
                if(data.getPaidVacationinfo() !=null &&data.getPaidVacationinfo().getInnerDays() != null){
                    return data.getPaidVacationinfo().getInnerDays();
                }else{
                    return 0;
                }
            case LASTYEARREMAINDAYS://上年剩余年假天数
                if(data.getPaidVacationinfo() !=null ){
                     sumLastYear = data.getPaidVacationinfo().getLastYearDays();
                }
                if (sumLastYear > 3) {
                    return 3;
                } else {
                    return sumLastYear;
                }
            case TOTALVACATIONDAYS://总年假天数
                return totalVacationDays;
            case HAVATAKEVACATION://已修年假天数
                return haveTakePaidVacationDays;
            case REMAINVACATION://剩余年假天数
                return totalVacationDays - haveTakePaidVacationDays;
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
            PaidVacationWrap wrap = dataList.get(i);
            if (null != wrap.getPaidVacationinfo().getUsreId()) {
                String userName = wrap.getUserName();
                if (ComparatorTool.matchPinYin(userName,keywords)) {
                    return i;
                }
            }
        }
        return -1;
    }
}
