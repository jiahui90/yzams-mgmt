/*
 * EmployeeTableModel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-05 17:27:59
 */
package com.yz.ams.client.model;

import com.nazca.ui.model.AbstractSimpleObjectTableModel;
import com.yz.ams.model.Employee;

/**
 * 请假管理表格
 *
 * @author Your Name <Song Haixiang >
 */
public class EmployeeTableModel extends AbstractSimpleObjectTableModel<Employee> {

    public static final int USERNAME = 0;
    public static final int EMPLOYEENUMBER = 1;
    public static final int TAKINGWORKTIME = 2;
    private static final String[] COLS = new String[]{"用户名", "工号", "入职时间"};

    public EmployeeTableModel() {
        super(COLS);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee data = dataList.get(rowIndex);
        switch (columnIndex) {
            case USERNAME://用户名
                return data.getUserName();
            case EMPLOYEENUMBER://工号
                return data.getEmployeeNumber();
            case TAKINGWORKTIME://入职时间
                return data.getEntryTime();
            default:
                return "";
        }
    }
}
