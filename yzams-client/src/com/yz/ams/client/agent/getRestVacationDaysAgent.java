/*
 * getRestVacationDaysAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-08-22 14:32:29
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.rpc.app.VacationService;
/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class getRestVacationDaysAgent extends AbstractAgent<Object>{
    private String userId;

    public void setParameters(String userId) {
        this.userId = userId;
    }
    @Override
    protected Object doExecute() throws HttpRPCException {

        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        VacationService service = HttpRPC.getService(VacationService.class, ClientContext.
                getSysServerRPCURL());
        Double innerDays = service.queryPaidInnerDays(userId);
        Double legalDays = service.queryPaidLegalDays(userId);
        String days = String.valueOf(innerDays) + "," + String.valueOf(legalDays);
        return days;
    }
}
