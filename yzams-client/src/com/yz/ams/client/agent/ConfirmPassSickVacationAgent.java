/*
 * ModifyPaidVacationAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-07-05 16:02:35
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.rpc.mgmt.VacationMgmtService;

/**
 *修改病假审核通过状态
 * @author 赵洪坤 <zhaohongkun@yzhtech.com>
 */
public class ConfirmPassSickVacationAgent extends AbstractAgent<Object>{
   
    private String vacationId;

    public String getVacationId() {
        return vacationId;
    }

    public void setVacationId(String vacationId) {
        this.vacationId = vacationId;
    }
    

    @Override
    protected Object doExecute() throws HttpRPCException {
         try { 
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        VacationMgmtService service = HttpRPC.getService(VacationMgmtService.class, ClientContext.getSysServerRPCURL());
             service.auditCertificatePass(vacationId);
             return null;
    }
    
}
