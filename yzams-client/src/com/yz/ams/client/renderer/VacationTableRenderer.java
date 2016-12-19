/*
 * VacationTableRenderer.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-05 17:37:35
 */
package com.yz.ams.client.renderer;

import com.nazca.ui.laf.blueocean.NazcaTableDefaultCellRenderer;
import java.awt.Component;
import javax.swing.JTable;

/**
 *请假管理
 * @author Your Name <Song Haixiang >
 */
public class VacationTableRenderer extends NazcaTableDefaultCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        return this;
    }
}
