/*
 * DeleteTeamMember.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-03 14:52:31
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.TeamMember;
import com.yz.ams.model.wrap.mgmt.TeamMemberWrap;
import com.yz.ams.rpc.mgmt.TeamMgmtService;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class DeleteTeamMemberAgent extends AbstractAgent<TeamMemberWrap>{
    private TeamMemberWrap teamMemberWrap;

    public void setTeamMemberWrap(TeamMemberWrap teamMemberWrap) {
        this.teamMemberWrap = teamMemberWrap;
    }

    @Override
    protected TeamMemberWrap doExecute() throws HttpRPCException {
        try { 
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        TeamMember teamMember = teamMemberWrap.getMemberInfo();
        TeamMgmtService service = HttpRPC.getService(TeamMgmtService.class, ClientContext.getSysServerRPCURL());
        service.deleteTeamMember(teamMember);
        return teamMemberWrap;
    }
}
