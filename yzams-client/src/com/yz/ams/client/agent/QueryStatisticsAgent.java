/*
 * QueryStatisticsAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-19 10:14:56
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.util.FakeDataFactory;
import com.yz.ams.consts.Permissions;
import com.yz.ams.model.wrap.mgmt.AttendanceMgmtStat;
import com.yz.ams.rpc.mgmt.StatMgmtService;
import java.util.Date;
import java.util.List;

/**
 *查询年假信息的监听器
 * @author Your Name <zhaohongkun@yzhtech.com >
 */
public class QueryStatisticsAgent extends AbstractAgent<List<AttendanceMgmtStat>> {
    private Date start;
    private Date end;
    public void setParameters(Date start,Date end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected List<AttendanceMgmtStat> doExecute() throws HttpRPCException {
        List<AttendanceMgmtStat> result = null;
        if (FakeDataFactory.isFake()) {
            result = FakeDataFactory.queryStatisticss(start,end);
        } else {
            StatMgmtService service = HttpRPC.getService(StatMgmtService.class, ClientContext.
                    getSysServerRPCURL());
            result = service.queryAttendanceStat(start,end);
        }
        return result;
    }
}
        