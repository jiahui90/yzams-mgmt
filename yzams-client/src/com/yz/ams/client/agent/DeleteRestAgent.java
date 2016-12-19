/*
 * DeleteRestAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-03-16 15:09:42
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.Rest;
import com.yz.ams.rpc.mgmt.RestMgmtService;

/**
 *删除调休Agent类
 * @author Zhu Mengchao
 */
public class DeleteRestAgent extends AbstractAgent<Rest> {
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
        RestMgmtService service = HttpRPC.getService(RestMgmtService.class, ClientContext.getSysServerRPCURL());
        return service.deleteRest(rest);
    }
}
