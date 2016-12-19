/*
 * ModifyPaidVacationAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-03 18:02:35
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.wrap.mgmt.PaidVacationWrap;
import com.yz.ams.rpc.mgmt.PaidVacationService;

/**
 *查询年假信息的监听器
 * @author 赵洪坤 <zhaohongkun@yzhtech.com>
 */
public class ModifyPaidVacationAgent extends AbstractAgent<PaidVacationWrap>{
    /**
     * 包换年假信息、上年剩余年假、这一年剩余年假信息的包装对象
     */
    private PaidVacationWrap paidVacation;

    public PaidVacationWrap getPaidVacation() {
        return paidVacation;
    }

    public void setPaidVacation(PaidVacationWrap paidVacation) {
        this.paidVacation = paidVacation;
    }

    @Override
    protected PaidVacationWrap doExecute() throws HttpRPCException {
         try { 
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        PaidVacationWrap result=null;
        PaidVacationService service = HttpRPC.getService(PaidVacationService.class, ClientContext.getSysServerRPCURL());
            result = service.modifyPaidVacation(paidVacation);
         return result;
    }
}
