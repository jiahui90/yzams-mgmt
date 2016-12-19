/*
 * UserInfoTableModel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-03-16 16:04:26
 */
package com.yz.ams.client.model;

import com.nazca.ui.model.AbstractSimpleObjectTableModel;
import com.nazca.usm.model.USMSUser;
import com.yz.ams.model.wrap.mgmt.CheckableItem;

/**
 *用户管理model
 * @author Zhu Mengchao
 */
public class UserInfoTableModel extends AbstractSimpleObjectTableModel<CheckableItem> {
    public static final int CHECK = 0;
    public static final int USERNAME = 1;
    private static final String[] COLS = new String[]{"","姓名"};

    public UserInfoTableModel() {
        super(COLS);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CheckableItem data = dataList.get(rowIndex);
        switch (columnIndex) {
            case CHECK:
                return data.isChecked();
            case USERNAME:
                return ((USMSUser)data.getItem()).getName();
            default:
                return "";
        }

    }

}