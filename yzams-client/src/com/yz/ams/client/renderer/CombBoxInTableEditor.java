/*
 * SettlementItemInTableEditor.java
 * 
 * Copyright(c) 2007-2015 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2015-09-22 17:37:12
 */
package com.yz.ams.client.renderer;

import com.yz.ams.consts.VacationTypeEnum;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Zhu Mengchao
 */
public class CombBoxInTableEditor extends DefaultCellEditor implements TableCellEditor {
    
    private JComboBox cmBox;
    public CombBoxInTableEditor(JComboBox cmBox) {
        super(cmBox);
        this.cmBox=cmBox;
    }

    @Override
    public Object getCellEditorValue() {
        VacationTypeEnum type=(VacationTypeEnum) cmBox.getSelectedItem();
        return type.name();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }

    public JComboBox getTextField() {
        return (JComboBox) super.getComponent();
    }
    
}
