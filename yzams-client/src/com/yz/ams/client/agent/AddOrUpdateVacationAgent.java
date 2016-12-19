/*
 * AddOrUpdateVacationAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-24 10:27:09
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.util.StringUtil;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.wrap.mgmt.VacationInfo;
import com.yz.ams.rpc.mgmt.VacationMgmtService;


/**
 *添加或修改请假Agent类
 * @author Your Name <Song Haixiang >
 */
public class AddOrUpdateVacationAgent extends AbstractAgent<VacationInfo>{
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
        VacationInfo result=null;
        VacationMgmtService service = HttpRPC.getService(VacationMgmtService.class, ClientContext.getSysServerRPCURL());
        if (StringUtil.isEmpty(vacation.getVacation().getVacationId())) {
            result = service.createVacation(vacation);
        }else{
            result = service.modifyVacation(vacation);
        }
         return result;
    }
    
}
