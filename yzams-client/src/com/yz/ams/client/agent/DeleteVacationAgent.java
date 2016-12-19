/*
 * DeleteVacationAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-03-16 13:20:56
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.Vacation;
import com.yz.ams.model.wrap.mgmt.VacationInfo;
import com.yz.ams.rpc.mgmt.VacationMgmtService;

/**
 *删除请假Agent类
 * @author Zhu Mengchao
 */
public class DeleteVacationAgent extends AbstractAgent<VacationInfo>{
    private VacationInfo vacation;

    public void setVacation(VacationInfo vacation) {
        this.vacation = vacation;
    }

    @Override
    protected VacationInfo doExecute() throws HttpRPCException {
        try { 
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        VacationMgmtService service = HttpRPC.getService(VacationMgmtService.class, ClientContext.getSysServerRPCURL());
        return service.deleteVacation(vacation);
    }
}
