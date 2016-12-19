/*
 * AddTeamMemberAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-03 10:42:18
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.TeamMember;
import com.yz.ams.rpc.mgmt.TeamMgmtService;
import java.util.List;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class AddTeamMemberAgent extends AbstractAgent<List<TeamMember>>{
    private List<TeamMember> memberList;
    
    public void setParam(List<TeamMember> memberList) {
        this.memberList = memberList;
     }

    @Override
    protected List<TeamMember> doExecute() throws HttpRPCException {

        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    
        TeamMgmtService service = HttpRPC.getService(TeamMgmtService.class, ClientContext.
                getSysServerRPCURL());
           memberList =  service.addMemberToTeam(memberList);
        
        return memberList;
    }
}
