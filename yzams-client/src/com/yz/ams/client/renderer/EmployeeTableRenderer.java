/*
 * EmployeeTableRenderer.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-19 14:10:25
 */
package com.yz.ams.client.renderer;

import com.nazca.ui.laf.blueocean.NazcaTableDefaultCellRenderer;
import com.nazca.util.NazcaFormater;
import com.yz.ams.client.model.EmployeeTableModel;
import java.awt.Component;
import java.util.Date;
import javax.swing.JTable;

/**
 *用户管理界面model
 * @author Your Name <Zhao Hongkun >
 */
public class EmployeeTableRenderer extends NazcaTableDefaultCellRenderer {
    public EmployeeTableRenderer() {
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(column == EmployeeTableModel.USERNAME && value == null){
            label.setText("--");
        }
        if(column == EmployeeTableModel.EMPLOYEENUMBER && value == null){  
            label.setText("--");
        }
        if(column == EmployeeTableModel.TAKINGWORKTIME && value == null){  
            label.setText("--");
        }
        if(column == EmployeeTableModel.TAKINGWORKTIME && value != null){ 
            Date date = (Date)value;
            String d =  NazcaFormater.getSimpleDateString(date);
            label.setText(d);
        }
        return this;
    }

}
