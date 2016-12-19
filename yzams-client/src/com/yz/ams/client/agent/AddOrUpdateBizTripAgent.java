/*
 * AddOrUpdateBizTripAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-24 14:31:48
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.util.StringUtil;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.BizTrip;
import com.yz.ams.rpc.mgmt.BizTripMgmtService;

/**
 *添加修改出差Agent
 * @author Your Name <Song Haixiang >
 */
public class AddOrUpdateBizTripAgent extends AbstractAgent<BizTrip> {
    private BizTrip bizTrip;

    public void setBizTrip(BizTrip bizTrip) {
        this.bizTrip = bizTrip;
    }

    @Override
    protected BizTrip doExecute() throws HttpRPCException {

        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        BizTrip result = null;
        BizTripMgmtService service = HttpRPC.getService(BizTripMgmtService.class, ClientContext.getSysServerRPCURL());
        if (StringUtil.isEmpty(bizTrip.getBizTripId())) {
            result = service.createBizTrip(bizTrip);
        } else {
            result = service.modifyBizTrip(bizTrip);
        }
        return result;
    }
}
