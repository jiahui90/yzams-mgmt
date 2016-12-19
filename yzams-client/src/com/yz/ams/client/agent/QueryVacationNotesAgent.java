/*
 * QueryVacationNotesAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-07-05 17:38:22
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.wrap.mgmt.VacationNote;
import com.yz.ams.rpc.mgmt.VacationMgmtService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class QueryVacationNotesAgent extends AbstractAgent<List<VacationNote>>{
    private String vacationId;
    
    public void setParameters(String vacationId) {
        this.vacationId = vacationId;
    }
    @Override
    protected List<VacationNote> doExecute() throws HttpRPCException {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        List<VacationNote> result = null;
         
            VacationMgmtService service = HttpRPC.getService(VacationMgmtService.class, ClientContext.
                    getSysServerRPCURL());
                result = service.queryVacationNotes(vacationId);
        
        return result;
    }
    
}
