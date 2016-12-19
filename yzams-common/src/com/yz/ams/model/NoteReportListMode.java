/*
 * NoteReportListMode.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-07-05 17:26:02
 */
package com.yz.ams.model;

import com.yz.ams.model.wrap.mgmt.VacationNote;
import com.yz.ams.model.wrap.mgmt.ReportAttendancesModel;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class NoteReportListMode implements Serializable{
    private static final long serialVersionUID = -2902230379314918761L;
    
    private List<VacationNote> noteLis;

    public List<VacationNote> getNoteLis() {
        return noteLis;
    }

    public void setNoteLis(List<VacationNote> noteLis) {
        this.noteLis = noteLis;
    }
    
}
