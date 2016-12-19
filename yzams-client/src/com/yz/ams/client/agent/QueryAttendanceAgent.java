/*
 * QueryAttendanceAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-18 10:25:28
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.sql.PageResult;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.util.FakeDataFactory;
import com.yz.ams.model.Attendance;
import com.yz.ams.rpc.mgmt.AttendanceMgmtService;

/**
 *查询出勤Agent类
 * @author Your Name <Song Haixiang >
 */
public class QueryAttendanceAgent extends AbstractAgent<PageResult<Attendance>> {
      private String keyword;
    private int curPage;
    private int pageSize;

    public void setParameters(String keyword, int curPage, int pageSize) {
        this.keyword = keyword;
        this.curPage = curPage;
        this.pageSize = pageSize;
    }
    
    
    
    @Override
    protected PageResult<Attendance> doExecute() throws HttpRPCException {
        
         try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        PageResult<Attendance> result = null;
        if (FakeDataFactory.isFake()) {
            result = FakeDataFactory.queryAttnedance(keyword, curPage, pageSize);
        } else {
           AttendanceMgmtService service = HttpRPC.getService(AttendanceMgmtService.class, ClientContext.getSysServerRPCURL());
            result = service.queryAttnedance(keyword, curPage, pageSize);
        }
        return result;
    }
    
}
