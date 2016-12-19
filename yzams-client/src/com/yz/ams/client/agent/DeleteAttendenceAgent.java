/*
 * DeleteAttendenceAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-03-16 17:55:46
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.Attendance;
import com.yz.ams.rpc.mgmt.AttendanceMgmtService;

/**
 *删除出勤Agent类
 * @author Zhu Mengchao
 */
public class DeleteAttendenceAgent extends AbstractAgent<Attendance>{
    private Attendance attendance;

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    @Override
    protected Attendance doExecute() throws HttpRPCException {
        try { 
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        AttendanceMgmtService service = HttpRPC.getService(AttendanceMgmtService.class, ClientContext.getSysServerRPCURL());
        return service.deleteAttendance(attendance);
    }
}
