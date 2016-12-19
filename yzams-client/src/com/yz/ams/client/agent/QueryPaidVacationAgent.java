/*
 * QueryPaidVacationAgent.java
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
import com.yz.ams.model.wrap.mgmt.PaidVacationWrap;
import com.yz.ams.rpc.mgmt.PaidVacationService;
import java.util.List;

/**
 *查询年假信息的监听器
 * @author Your Name <zhaohongkun@yzhtech.com >
 */
public class QueryPaidVacationAgent extends AbstractAgent<List<PaidVacationWrap>> {
    private int year;
    private List<PaidVacationWrap> result = null;
    public void setParameters(int year) {
        this.year = year;
    }

    @Override
    protected List<PaidVacationWrap> doExecute() throws HttpRPCException {
         try { 
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        if (FakeDataFactory.isFake()) {
            result = FakeDataFactory. queryPaidVacation(year);
        } else {
            PaidVacationService service = HttpRPC.getService(PaidVacationService.class, ClientContext.
                    getSysServerRPCURL());
            result = service.queryPaidVacationInfo(year);
        }
        return result;
    }
}
        