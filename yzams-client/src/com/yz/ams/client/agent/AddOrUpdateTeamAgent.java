/*
 * AddTeamAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-02 10:32:37
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.util.StringUtil;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.Team;
import com.yz.ams.rpc.mgmt.TeamMgmtService;
/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class AddOrUpdateTeamAgent extends AbstractAgent<Team>{
     private Team team;

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    protected Team doExecute() throws HttpRPCException {

        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Team result = null;
        TeamMgmtService service = HttpRPC.getService(TeamMgmtService.class, ClientContext.
                getSysServerRPCURL());
        
        if (StringUtil.isEmpty(team.getTeamId())) {
            result = service.createTeam(team);
         } else {
            result = service.modifyTeam(team);
        }
        return result;
    }
}
