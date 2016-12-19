/*
 * RestTableRenderer.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-17 12:15:18
 */
package com.yz.ams.client.renderer;

import com.nazca.ui.laf.blueocean.NazcaTableDefaultCellRenderer;
import com.yz.ams.client.model.RestTableMode;
import java.awt.Component;
import javax.swing.JTable;

/**
 *调休renderer
 * @author Your Name <Song Haixiang >
 */
public class RestTableRenderer extends NazcaTableDefaultCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(column == RestTableMode.ENDDATE && value == null){
            label.setText("--");
        }
        return this;
    }

}
