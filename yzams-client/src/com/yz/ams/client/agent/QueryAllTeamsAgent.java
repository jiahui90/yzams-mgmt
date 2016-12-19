/*
 * QueryAllTeamsAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-01 16:09:25
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.Team;
import com.yz.ams.rpc.mgmt.TeamMgmtService;
import java.util.List;

/**
 *
 * @author Qiu Dongyue <qdy@yzhtech.com>
 */
public class QueryAllTeamsAgent extends AbstractAgent<List<Team>>{
     
    @Override
    protected List<Team> doExecute() throws HttpRPCException {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        List<Team> result = null;
         
            TeamMgmtService service = HttpRPC.getService(TeamMgmtService.class, ClientContext.
                    getSysServerRPCURL());
            result = service.queryAllTeams();
        
        return result;
    }
    
}
