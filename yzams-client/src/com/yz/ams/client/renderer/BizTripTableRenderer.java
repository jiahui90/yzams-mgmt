/*
 * BizTiipTableRenderer.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-15 11:28:08
 */
package com.yz.ams.client.renderer;

import com.nazca.ui.laf.blueocean.NazcaTableDefaultCellRenderer;
import com.yz.ams.client.model.BizTripTableMode;
import java.awt.Component;
import javax.swing.JTable;

/**
 *出差renderer
 * @author Your Name <Song Haixiang >
 */
public class BizTripTableRenderer extends NazcaTableDefaultCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         if(column == BizTripTableMode.FINISHTIME && value == null){
            label.setText("--");
        }
        return this;
    }

}
