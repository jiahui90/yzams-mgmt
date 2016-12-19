/*
 * QueryTeamMembers.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-02 14:58:14
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.wrap.mgmt.TeamMemberWrap;
import com.yz.ams.rpc.mgmt.TeamMgmtService;
import java.util.List;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class QueryTeamMembersAgent extends AbstractAgent<List<TeamMemberWrap>>{
    private String teamId;

    public void setParameters(String teamId) {
        this.teamId = teamId;
    }
     @Override
    protected List<TeamMemberWrap> doExecute() throws HttpRPCException {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        List<TeamMemberWrap> result = null;
        TeamMgmtService service = HttpRPC.getService(TeamMgmtService.class, ClientContext.
                    getSysServerRPCURL());
        
        result = service.queryMembersInTeam(teamId);
    
        return result;
    }
}
