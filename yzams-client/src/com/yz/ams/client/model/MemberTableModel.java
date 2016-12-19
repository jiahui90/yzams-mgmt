/*
 * MemberTableModel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-01 15:54:31
 */
package com.yz.ams.client.model;

import com.nazca.ui.model.AbstractSimpleObjectTableModel;
import com.nazca.usm.model.USMSUser;
import com.yz.ams.model.Employee;
import com.yz.ams.util.DateUtil;
import com.yz.ams.model.wrap.mgmt.TeamMemberWrap;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Qiu Dongyue <qdy@yzhtech.com>
 */
public class MemberTableModel extends AbstractSimpleObjectTableModel<TeamMemberWrap> {
    public static final int NAME = 0;
    public static final int NUM = 1;
    public static final int ZHIWU = 2;
    public static final int MOBILE = 3;
    public static final int ENTRYTIME = 4;
    public static final int ENTRYYEAR = 5;
    
    public static final String[] columns = new String[]{"姓名", "工号", "职务", "联系电话","入职时间","入职年限"};

    public MemberTableModel() {
        super(columns);
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TeamMemberWrap rowData = dataList.get(rowIndex);
            switch (columnIndex) {
                case NAME:
                    return rowData.getUser() != null ? rowData.getUser().getName() : null;
                case NUM:
                    //TODO
                    return rowData.getUser() != null ? rowData.getUser().getEmployeeNumber() : null;
                case ZHIWU:
                    return rowData.getUser() != null ? rowData.getUser().getJobPosition() : null;
                case MOBILE:
                    String mobile = "";
                    if(rowData.getUser() != null){
                        USMSUser u = rowData.getUser(); 
                        return u.getMobile();
                    }
                    return mobile;
                case ENTRYTIME:
                    return rowData.getEmployee() != null ? rowData.getEmployee().getEntryTime() : null;
                case ENTRYYEAR:
                    Employee employee = rowData.getEmployee();
                    if(employee != null){
                        if(employee.getEntryTime() != null){
                            return getEntryYear(new Date(),employee.getEntryTime());
                        }
                    }
                    return null;
                default:
                    return "";
            }
    }
    
    private int getEntryYear(Date nowDate,Date entryDate){
        int year = 0;
        try {
            year = (int)DateUtil.getDateDiffence(entryDate, nowDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return year;
    }
}
