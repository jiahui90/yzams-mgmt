/*
 * StaffAttendanceList.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-12-02 09:38:24
 */
package com.yz.ams.server.ios.api.model;

import com.yz.ams.model.wrap.app.StaffAttendance;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Hu Qin<huqin@yzhtech.com>
 */
public class StaffAttendanceList implements Serializable{
    private static final long serialVersionUID = 4190812533247788151L;
    private List<StaffAttendance> data;

    public StaffAttendanceList() {
    }

    public StaffAttendanceList(List<StaffAttendance> data) {
        this.data = data;
    }

    public List<StaffAttendance> getData() {
        return data;
    }

    public void setData(List<StaffAttendance> data) {
        this.data = data;
    }
    
}
