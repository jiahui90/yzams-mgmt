/*
 * StatisticsTableRenderer.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-19 14:10:25
 */
package com.yz.ams.client.renderer;

import com.nazca.ui.laf.blueocean.NazcaTableDefaultCellRenderer;
import com.nazca.usm.client.util.ComparatorTool;
import com.yz.ams.client.model.StatisticsTableModel;
import com.yz.ams.model.wrap.mgmt.AttendanceMgmtStat;
import java.awt.Component;
import javax.swing.JTable;

/**
 *出勤统计renderer
 * @author Your Name <Song Haixiang >
 */
public class StatisticsTableRenderer extends NazcaTableDefaultCellRenderer {

    private String keywords;

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        StatisticsTableModel model = (StatisticsTableModel) table.getModel();
        int idx = table.convertRowIndexToModel(row);
        AttendanceMgmtStat stat = model.getData(idx);
        matched = false;
        if (null != stat) {
            String userName = stat.getUserName();
            if (keywords != null && ComparatorTool.matchPinYin(userName, keywords)) {
                matched = true;
            } else {
                matched = false;
            }
        }
        return this;
    }
}
