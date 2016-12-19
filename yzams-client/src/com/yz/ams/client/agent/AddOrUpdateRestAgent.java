/*
 * AddOrUpdateRestAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-24 14:32:54
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.util.StringUtil;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.Rest;
import com.yz.ams.rpc.mgmt.RestMgmtService;

/**
 *添加修改调休Agent
 * @author Your Name <Song Haixiang >
 */
public class AddOrUpdateRestAgent extends AbstractAgent<Rest> {
    private Rest rest;

    public void setRest(Rest rest) {
        this.rest = rest;
    }

    @Override
    protected Rest doExecute() throws HttpRPCException {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Rest result = null;
        RestMgmtService service = HttpRPC.getService(RestMgmtService.class, ClientContext.getSysServerRPCURL());
        if (StringUtil.isEmpty(rest.getRestId())) {
            result = service.createRest(rest);
        } else {
            result = service.modifyRest(rest);
        }
        return result;
    }
}
