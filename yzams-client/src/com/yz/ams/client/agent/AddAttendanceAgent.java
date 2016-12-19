/*
 * AddAttendanceAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-24 14:36:54
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.Attendance;
import com.yz.ams.rpc.mgmt.AttendanceMgmtService;
import java.util.List;

/**
 *添加出勤Agent
 * @author Your Name <Song Haixiang >
 */
public class AddAttendanceAgent extends AbstractAgent<List<Attendance>> {
    private List<Attendance> attenLis;

    public void setAttenLis(List<Attendance> attenLis) {
        this.attenLis = attenLis;
    }

    @Override
    protected List<Attendance> doExecute() throws HttpRPCException {

        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        AttendanceMgmtService service = HttpRPC.getService(AttendanceMgmtService.class, ClientContext.
                getSysServerRPCURL());
        return service.createAttendance(attenLis);
    }

}
