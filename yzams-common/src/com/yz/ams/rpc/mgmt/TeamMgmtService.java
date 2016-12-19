/*
 * TeamMgmtService.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 17:28:52
 */
package com.yz.ams.rpc.mgmt;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.HttpRPCSessionTokenRequired;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.yz.ams.model.Team;
import com.yz.ams.model.TeamMember;
import com.yz.ams.model.wrap.mgmt.TeamMemberWrap;
import java.util.List;

/**
 * 团队管理
 *
 * @author Your Name <Song Haixiang >
 */
@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING, identifier = "com.yz.ams.server.rpcimpl.mgmt.TeamMgmtServiceImpl")
public interface TeamMgmtService {

    /**
     * 获取团队信息
     *
     * @param keyword
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public List<Team> queryAllTeams() throws HttpRPCException;

    /**
     * 添加团队信息
     *
     * @param team
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public Team createTeam(Team team) throws HttpRPCException;

    /**
     * 修改团队信息
     *
     * @param team
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public Team modifyTeam(Team team) throws HttpRPCException;

    /**
     * 删除团队信息
     *
     * @param teamId
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public void deleteTeam(String teamId) throws HttpRPCException;
    /**
     * 获取团队成员
     * @param teamId
     * @return
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    public List<TeamMemberWrap> queryMembersInTeam(String teamId) throws HttpRPCException;
    /**
     * 添加团队成员
     * @param userId
     * @param teamId
     * @return
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    public TeamMemberWrap createMemberToTeam(String userId, String teamId) throws HttpRPCException;
    
    /**
     * caohuiying 增加团队成员
     * @param teamMember
     * @return
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    public List<TeamMember> addMemberToTeam(List<TeamMember> memberList) throws HttpRPCException;
    
    /**
     * caohuiying 删除团队成员
     * @param teamMember
     * @return
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    public TeamMember deleteTeamMember(TeamMember teamMember) throws HttpRPCException;
    /**
     * 删除团队成员
     * @param userId
     * @param teamId
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    public void deleteMemberFromTeam(String userId, String teamId) throws HttpRPCException;
    
    /**
     * 通过userId得到团队内所有成员的id
     * @param teamMember
     * @return
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    public List<String> getAllUserIdInTeamByUserId(String userId) throws HttpRPCException;
}
