/*
 * EmployeeListModel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-28 15:47:21
 */
package com.yz.ams.client.model;

import com.nazca.ui.TriStateCellWrapper;
import com.nazca.ui.TristateCheckBox;
import com.nazca.ui.model.SimpleObjectListModel;
import com.yz.ams.model.Employee;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 * 选择团队成员的modelList，里面的对象为employee
 */
public class EmployeeListModel extends SimpleObjectListModel<TriStateCellWrapper<Employee>>{
    public void setNominalList(List<Employee> dList) {
        List<TriStateCellWrapper<Employee>> checkList = new ArrayList<>();
        for (Employee d : dList) {
            checkList.add(new TriStateCellWrapper<Employee>(d));
        }
        super.setObjectList(checkList);
    }

    public List<Employee> getAllSelectedNominals() {
        List<Employee> dList = new ArrayList<Employee>();
        for (TriStateCellWrapper<Employee> tcw : getObjectList()) {
            if (tcw.getState() == TristateCheckBox.State.CHECKED) {
                dList.add(tcw.getObj());
            }
        }
        return dList;
    }
    
    public void setReversSelected() {
        for (TriStateCellWrapper<Employee> wrp : list) {
            if (wrp.getState() == TristateCheckBox.State.CHECKED) {
                wrp.setState(TristateCheckBox.State.UNCHECKED);
            } else {
                wrp.setState(TristateCheckBox.State.CHECKED);
            }
        }
        this.fireContentsChanged(this, 0, list.size() - 1);
    }

    public void setAllSelected() {
        for (TriStateCellWrapper<Employee> wrp : list) {
            wrp.setState(TristateCheckBox.State.CHECKED);
        }
        this.fireContentsChanged(this, 0, list.size() - 1);
    }

    public void setAllUnselected() {
        for (TriStateCellWrapper<Employee> wrp : list) {
            wrp.setState(TristateCheckBox.State.UNCHECKED);
        }
        this.fireContentsChanged(this, 0, list.size() - 1);
    }

    public void setSelected(List<Employee> dList) {
        List<TriStateCellWrapper<Employee>> checkList = new ArrayList<TriStateCellWrapper<Employee>>();
        for (Employee d : dList) {
            checkList.add(new TriStateCellWrapper<Employee>(d));
        }
        for (TriStateCellWrapper<Employee> wrp : list) {
            for (int i = 0; i < checkList.size(); i++) {
                TriStateCellWrapper<Employee> obj = checkList.get(i);
                if (wrp.toString().equals(obj.toString())) {
                    wrp.setState(TristateCheckBox.State.CHECKED);
                }
            }
        }
        this.fireContentsChanged(this, 0, list.size() - 1);
    }
}
