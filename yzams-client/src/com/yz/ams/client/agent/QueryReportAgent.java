/*
 * QueryReportAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-17 18:18:09
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.consts.Permissions;
import com.yz.ams.model.wrap.mgmt.ReportAttendancesModel;
import com.yz.ams.rpc.mgmt.StatMgmtService;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class QueryReportAgent extends AbstractAgent<List<ReportAttendancesModel>>{
    private Date start;
    private Date end;
    private String keyword;
    public void setParameters(Date start, Date end,String keyword) {
        this.start = start;
        this.end = end;
        this.keyword = keyword;
    }
    @Override
    protected List<ReportAttendancesModel> doExecute() throws HttpRPCException {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        List<ReportAttendancesModel> result = null;
        
            StatMgmtService service = HttpRPC.getService(StatMgmtService.class, ClientContext.
                    getSysServerRPCURL());
            
            result = service.queryStaffAttendanceReport(start, end, keyword);
        return result;
    }
    
}
