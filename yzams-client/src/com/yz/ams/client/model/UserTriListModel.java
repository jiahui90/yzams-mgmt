/*
 * DistrictPublishStatusTriListModel.java
 * 
 * Copyright(c) 2007-2012 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2012-03-27 18:57:03
 */
package com.yz.ams.client.model;

import com.nazca.ui.TriStateCellWrapper;
import com.nazca.ui.TristateCheckBox;
import com.nazca.ui.model.SimpleObjectListModel;
import com.nazca.usm.model.USMSUser;
import java.util.ArrayList;
import java.util.List;

/**
 * 可勾选会议资源列表模板
 *
 * @author Qiu Dongyue
 */
public class UserTriListModel extends SimpleObjectListModel<TriStateCellWrapper<USMSUser>> {

    public void setNominalList(List<USMSUser> dList) {
        List<TriStateCellWrapper<USMSUser>> checkList = new ArrayList<TriStateCellWrapper<USMSUser>>();
        for (USMSUser d : dList) {
            checkList.add(new TriStateCellWrapper<USMSUser>(d));
        }
        super.setObjectList(checkList);
    }

    public List<USMSUser> getAllSelectedNominals() {
        List<USMSUser> dList = new ArrayList<USMSUser>();
        for (TriStateCellWrapper<USMSUser> tcw : getObjectList()) {
            if (tcw.getState() == TristateCheckBox.State.CHECKED) {
                dList.add(tcw.getObj());
            }
        }
        return dList;
    }

    public void setReversSelected() {
        for (TriStateCellWrapper<USMSUser> wrp : list) {
            if (wrp.getState() == TristateCheckBox.State.CHECKED) {
                wrp.setState(TristateCheckBox.State.UNCHECKED);
            } else {
                wrp.setState(TristateCheckBox.State.CHECKED);
            }
        }
        this.fireContentsChanged(this, 0, list.size() - 1);
    }

    public void setAllSelected() {
        for (TriStateCellWrapper<USMSUser> wrp : list) {
            wrp.setState(TristateCheckBox.State.CHECKED);
        }
        this.fireContentsChanged(this, 0, list.size() - 1);
    }

    public void setAllUnselected() {
        for (TriStateCellWrapper<USMSUser> wrp : list) {
            wrp.setState(TristateCheckBox.State.UNCHECKED);
        }
        this.fireContentsChanged(this, 0, list.size() - 1);
    }

    public void setSelected(List<USMSUser> dList) {
        List<TriStateCellWrapper<USMSUser>> checkList = new ArrayList<TriStateCellWrapper<USMSUser>>();
        for (USMSUser d : dList) {
            checkList.add(new TriStateCellWrapper<USMSUser>(d));
        }
        for (TriStateCellWrapper<USMSUser> wrp : list) {
            for (int i = 0; i < checkList.size(); i++) {
                TriStateCellWrapper<USMSUser> obj = checkList.get(i);
                if (wrp.toString().equals(obj.toString())) {
                    wrp.setState(TristateCheckBox.State.CHECKED);
                }
            }
        }
        this.fireContentsChanged(this, 0, list.size() - 1);
    }
}
