/*
 * DeleteBizTripAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-03-16 13:37:02
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.BizTrip;
import com.yz.ams.rpc.mgmt.BizTripMgmtService;

/**
 *删除出差Agent类
 * @author Zhu Mengchao
 */
public class DeleteBizTripAgent extends AbstractAgent<BizTrip>{
    private BizTrip biz;

    public void setBiz(BizTrip biz) {
        this.biz = biz;
    }
    
    @Override
    protected BizTrip doExecute() throws HttpRPCException {
        try { 
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        BizTripMgmtService service = HttpRPC.getService(BizTripMgmtService.class, ClientContext.getSysServerRPCURL());
        return service.deleteBizTrip(biz);
    }
}