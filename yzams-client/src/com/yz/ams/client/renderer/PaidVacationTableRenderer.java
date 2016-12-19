/*
 * PaidVacationTableRenderer.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-19 14:10:25
 */
package com.yz.ams.client.renderer;

import com.nazca.ui.laf.blueocean.NazcaTableDefaultCellRenderer;
import com.nazca.usm.client.util.ComparatorTool;
import com.nazca.util.NazcaFormater;
import com.yz.ams.client.model.PaidVacationTableModel;
import com.yz.ams.model.wrap.mgmt.PaidVacationWrap;
import java.awt.Component;
import java.util.Date;
import javax.swing.JTable;

/**
 *年假管理界面model
 * @author Your Name <Song Haixiang >
 */
public class PaidVacationTableRenderer extends NazcaTableDefaultCellRenderer {
    private String keywords;

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(column == PaidVacationTableModel.USERNAME && value == null){
            label.setText("--");
        }
        if(column == PaidVacationTableModel.WORKNUMBER && value == null){  
            label.setText("--");
        }
        if(column == PaidVacationTableModel.TAKINGWORKDATE && value == null){  
            label.setText("--");
        }
        if(column == PaidVacationTableModel.TAKINGWORKDATE && value != null){ 
            Date date = (Date)value;
            String d =  NazcaFormater.getSimpleDateString(date);
            label.setText(d);
        }
        if(column == PaidVacationTableModel.TAKINGWORKYEAR && value == null){ 
            label.setText("--");
        }
        PaidVacationTableModel model = (PaidVacationTableModel) table.getModel();
        int idx = table.convertRowIndexToModel(row);
        PaidVacationWrap paidVacation = model.getData(idx);
        matched = false;
        if (null != paidVacation) {
            String userName = paidVacation.getUserName();
            if (keywords != null && ComparatorTool.matchPinYin(userName, keywords)) {
                matched = true;
            } else {
                matched = false;
            }
        }
        return this;
    }

}
