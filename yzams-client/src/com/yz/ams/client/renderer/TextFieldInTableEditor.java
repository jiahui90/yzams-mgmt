/*
 * SettlementItemInTableEditor.java
 * 
 * Copyright(c) 2007-2015 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2015-09-22 17:37:12
 */
package com.yz.ams.client.renderer;

import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Zhu Mengchao
 */
public class TextFieldInTableEditor extends DefaultCellEditor implements TableCellEditor {

    public TextFieldInTableEditor() {
        super(new JTextField());
    }

    @Override
    public Object getCellEditorValue() {
        return super.getCellEditorValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }
    
    @Override
    public boolean stopCellEditing() {
        Object value = this.getCellEditorValue();
        try {
            double cellValue = Double.parseDouble(String.valueOf(value));
            if (cellValue < 0) {
                Toolkit.getDefaultToolkit().beep();
                this.editorComponent.requestFocus();
                JOptionPane.showMessageDialog(null, "请输入0及以上数字", "提示", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
            this.editorComponent.requestFocus();
            JOptionPane.showMessageDialog(null, "请输入数字", "提示", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return super.stopCellEditing();
    }

    public JTextField getTextField() {
        return (JTextField) super.getComponent();
    }
    
}
