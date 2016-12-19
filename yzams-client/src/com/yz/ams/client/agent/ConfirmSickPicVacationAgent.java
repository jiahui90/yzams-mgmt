/*
 * ConfirmSickPicVacationAgent.java
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
import com.yz.ams.model.Vacation;
import com.yz.ams.rpc.mgmt.VacationMgmtService;
import java.io.InputStream;

/**
 *修改病假审核不通过状态
 * @author 赵洪坤 <zhaohongkun@yzhtech.com>
 */
public class ConfirmSickPicVacationAgent extends AbstractAgent<InputStream>{
   
    private Vacation vacation;

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }
    @Override
    protected InputStream doExecute() throws HttpRPCException {
            InputStream is = null;
             VacationMgmtService service = HttpRPC.getService(VacationMgmtService.class, ClientContext.getSysServerRPCURL());
             if(vacation.getCertificatePicId() != null){
                is = service.getCertificatePic(vacation.getCertificatePicId());}
    return is;
    }
    
}
