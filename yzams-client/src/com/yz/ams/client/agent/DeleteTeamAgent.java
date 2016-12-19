/*
 * DeleteTeamAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-02 11:54:16
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.Team;
import com.yz.ams.rpc.mgmt.TeamMgmtService;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class DeleteTeamAgent extends AbstractAgent<Team>{
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
        TeamMgmtService service = HttpRPC.getService(TeamMgmtService.class, ClientContext.getSysServerRPCURL());
        String teamId = team.getTeamId();
        service.deleteTeam(teamId);
        return team;
    }
}
