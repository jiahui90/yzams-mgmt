/*
 * MemberTableRenderer.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-12 15:31:03
 */
package com.yz.ams.client.renderer;

import com.nazca.ui.laf.blueocean.NazcaTableDefaultCellRenderer;
import com.yz.ams.client.model.MemberTableModel;
import com.yz.ams.util.DateUtil;
import java.awt.Component;
import java.util.Date;
import javax.swing.JTable;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class MemberTableRenderer extends NazcaTableDefaultCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(column == MemberTableModel.NAME && value != null){
            String name = (String)value;
            label.setText(name);
            label.setHorizontalTextPosition(CENTER);
        }
        if(column == MemberTableModel.NUM && value != null){
            label.setText((String)value);
            label.setHorizontalTextPosition(CENTER);
        }
        if(column == MemberTableModel.ZHIWU && value != null){
            label.setText((String)value);
            label.setHorizontalTextPosition(CENTER);
        }
        if(column == MemberTableModel.MOBILE){
            if(value != null || !value.equals("")){
                label.setText((String)value);
            }else{
                label.setText("--");
            }
            label.setHorizontalTextPosition(CENTER);
        }
        if(column == MemberTableModel.ENTRYTIME ){
            if(value != null){
                Date date = (Date)value;
                String d = DateUtil.getDateTimeFormat(date);
                d = d.substring(0, 10);
                label.setText(d);
                label.setHorizontalTextPosition(CENTER);
            }else{
                label.setText("--");
            }
        }
        if(column == MemberTableModel.ENTRYYEAR ){
            if(value != null){
                int year = (int)value;
                String y = String.valueOf(year);
                label.setText(y + "年");
                label.setHorizontalTextPosition(CENTER);
            }else{
                label.setText("--");
            }
        }
        return this;
    }
}
