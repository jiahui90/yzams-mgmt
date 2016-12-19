/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yz.ams.client.model;

import com.nazca.ui.model.AbstractSimpleObjectTableModel;
import com.yz.ams.consts.VacationTypeEnum;
import com.yz.ams.model.VacationDetail;

/**
 *
 * @author Administrator
 */
public class VacationTypeTableModel extends AbstractSimpleObjectTableModel<VacationDetail> {
    public static final int VACATIONTYPE = 0;
    public static final int DAYS = 1;
    private static final String[] COLS = new String[]{"请假类型", "天数"};
    private boolean editable = true;

    public VacationTypeTableModel() {
        super(COLS);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        VacationDetail data = dataList.get(rowIndex);
        switch (columnIndex) {
            case VACATIONTYPE:
                return data.getVacationType().toString();
            case DAYS:
                return data.getVacationDays();
            default:
                return "";
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (dataList.size() > rowIndex) {
            VacationDetail item = dataList.get(rowIndex);
            switch (columnIndex) {
                case VACATIONTYPE:
                    item.setVacationType(VacationTypeEnum.valueOf(String.valueOf(aValue)));
                    break;
                case DAYS:
                    item.setVacationDays(Double.parseDouble(String.valueOf(aValue)));
                    break;
                default:
                    break;
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
    public void setEditable(boolean editable) {
        this.editable = editable;
        fireTableDataChanged();
    }
}
