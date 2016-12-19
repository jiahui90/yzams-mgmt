/*
 * AttendanceTableRenderer.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-18 10:00:46
 */
package com.yz.ams.client.renderer;

import com.nazca.ui.laf.blueocean.NazcaTableDefaultCellRenderer;
import com.yz.ams.client.model.AttendanceTableMode;
import java.awt.Component;
import javax.swing.JTable;

/**
 * 出勤管理
 *
 * @author Your Name <Song Haixiang >
 */
public class AttendanceTableRenderer extends NazcaTableDefaultCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (column == AttendanceTableMode.DELAYTYPE) {
            label.setHorizontalAlignment(CENTER);
        }
        return this;
    }
}
