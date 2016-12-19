/*
 * TeamMgmtServiceImpl.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 17:34:25
 */
package com.yz.ams.server.rpcimpl.mgmt;

import com.mysql.jdbc.StringUtils;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.HttpRPCInjection;
import com.nazca.usm.client.connector.USMSRPCServiceException;
import com.nazca.usm.model.USMSUser;
import com.nazca.util.StringUtil;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.model.Employee;
import com.yz.ams.model.Team;
import com.yz.ams.model.TeamMember;
import com.yz.ams.model.wrap.mgmt.TeamMemberWrap;
import com.yz.ams.rpc.mgmt.TeamMgmtService;
import com.yz.ams.server.dao.EmployeeDAO;
import com.yz.ams.server.dao.TeamDAO;
import com.yz.ams.server.dao.TeamMemberDAO;
import com.yz.ams.server.util.ConnectionUtil;
import com.yz.ams.server.util.USMSTool;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *团队管理
 * @author Your Name <Song Haixiang >
 */
public class TeamMgmtServiceImpl implements TeamMgmtService{
    
    private static final Log log = LogFactory.getLog(PaidVacationServiceImpl.class);
     @HttpRPCInjection
    private HttpServletRequest request; 
    List<Team> teamListApply=new ArrayList<>();
    List<String> teamMembersIds = new ArrayList<>();
    
    @Override
    public List<Team> queryAllTeams() throws HttpRPCException {
        try {
            TeamDAO teamdao =new TeamDAO(ConnectionUtil.
                    getConnSrc());
            teamListApply=teamdao.queryTeam();
        } catch (SQLException ex) {
            Logger.getLogger(TeamMgmtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            log.error("query all teams failed", ex);
            throw new HttpRPCException("query all teams failed", ErrorCode.DB_ERROR);
        }
        return teamListApply;
    }

    @Override
    public Team createTeam(Team team) throws HttpRPCException {
        if(team == null){
                throw new HttpRPCException("参数不能为空",ErrorCode.PARAMETER_ERROR);
            }
        team.setTeamId(UUID.randomUUID().toString());
        try {
            TeamDAO teamdao =new TeamDAO(ConnectionUtil.
                    getConnSrc());
            teamdao.createTeam(team);
        } catch (SQLException ex) {
            Logger.getLogger(TeamMgmtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            log.error("create team failed", ex);
            throw new HttpRPCException("create team failed", ErrorCode.DB_ERROR);
        }
        return team;
    }

    @Override
    public Team modifyTeam(Team team) throws HttpRPCException {
        if(team == null){
                throw new HttpRPCException("参数不能为空",ErrorCode.PARAMETER_ERROR);
            }
        try {
            TeamDAO teamdao =new TeamDAO(ConnectionUtil.
                    getConnSrc());
            teamdao.modifyTeam(team);
        } catch (SQLException ex) {
            Logger.getLogger(TeamMgmtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            log.error("modify team failed", ex);
            throw new HttpRPCException("modify team failed", ErrorCode.DB_ERROR);
        }
        return team;
    }

    @Override
    public void deleteTeam(String teamId) throws HttpRPCException {
        if(StringUtils.isNullOrEmpty(teamId)){
            throw new HttpRPCException("参数不能为空",ErrorCode.PARAMETER_ERROR);
        }
        try {
            TeamDAO teamdao =new TeamDAO(ConnectionUtil.
                    getConnSrc());
            teamdao.deleteTeam(teamId);
        } catch (SQLException ex) {
            Logger.getLogger(TeamMgmtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            log.error("delete team failed", ex);
            throw new HttpRPCException("delete team failed", ErrorCode.DB_ERROR);
        }
    }

    /**
     * caohy添加了方法的实现,通过teamId得到包装类 TeamMemberWrap
     * @param teamId
     * @return
     * @throws HttpRPCException 
     */
    @Override
    public List<TeamMemberWrap> queryMembersInTeam(String teamId) throws
            HttpRPCException {
         List<TeamMemberWrap>  wraps = new ArrayList<>();
        try {
            TeamMemberDAO teamdao =new TeamMemberDAO(ConnectionUtil.
                    getConnSrc());
            EmployeeDAO employeeDao = new EmployeeDAO(ConnectionUtil.
                    getConnSrc());
            List<TeamMember> members = teamdao.queryAllMemberByTeamId(teamId);
           
            
            for(TeamMember mem: members){
                String userId = mem.getUserID();
                try {
                    USMSUser user = USMSTool.transformUserIdsToUser(userId);
                    Employee employee = employeeDao.queryEmployeeById(userId);
                    if(user != null){
                        TeamMemberWrap wrap = new TeamMemberWrap();
                        wrap.setMemberInfo(mem);
                        wrap.setUser(user);
                        wrap.setEmployee(employee);
                        wraps.add(wrap);
                    }
                    
                } catch (USMSRPCServiceException ex) {
                    ex.printStackTrace();
                     log.error("query employee failed", ex);
                    throw new HttpRPCException("query employee failed", ErrorCode.DB_ERROR);
                }
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(TeamMgmtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            log.error("query members in team failed", ex);
            throw new HttpRPCException("query members in team failed", ErrorCode.DB_ERROR);
        }

        return wraps;
    }

    /**
     * caohuiying,修改,增加团队成员
     * @param userId
     * @param teamId
     * @return
     * @throws HttpRPCException 
     */
    @Override
    public TeamMemberWrap createMemberToTeam(String userId, String teamId)
            throws HttpRPCException {
        if(StringUtil.isEmpty(userId) || StringUtil.isEmpty(teamId)){
            throw new HttpRPCException("参数不能为空",ErrorCode.PARAMETER_ERROR);
        }
        try {
            TeamMember teamMember = new TeamMember();
            teamMember.setTeamId(teamId);
            teamMember.setUserID(userId);
            TeamMemberDAO teamMemberdao =new TeamMemberDAO(ConnectionUtil.
                    getConnSrc());
            teamMemberdao.addTeamMember(teamMember);
            
        } catch (SQLException ex) {
            Logger.getLogger(TeamMgmtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            log.error("create member to team failed", ex);
            throw new HttpRPCException("create member to team failed", ErrorCode.DB_ERROR);
        } 
        return null;
    }
    
    

    @Override
    public void deleteMemberFromTeam(String userId, String teamId) throws
            HttpRPCException {
        
    }
    /**
     * caohuiying 增加团队成员
     * @param memberList
     * @return
     * @throws HttpRPCException 
     */
    @Override
    public List<TeamMember> addMemberToTeam(List<TeamMember> memberList) throws HttpRPCException {
         try {
             TeamMemberDAO teamMemberdao =new TeamMemberDAO(ConnectionUtil.
                     getConnSrc());
             for(TeamMember member : memberList ){
                 String teamId = member.getTeamId();
                 String userId = member.getUserID();
                 List<TeamMember> members = teamMemberdao.queryAllMemberByTeamId(teamId);
                 
                 boolean isAdded = false;
                 //如果此团队中有这个成员，就不加了
                 for(TeamMember m : members){
                     if(m.getUserID().equals(userId)){
                         isAdded = true;
                     }
                 }
                 if(isAdded){
                     continue;
                 }
                 teamMemberdao.addTeamMember(member);
             }
             
         } catch (SQLException ex) {
             ex.printStackTrace();
             log.error("add member to team failed", ex);
            throw new HttpRPCException("add member to team failed", ErrorCode.DB_ERROR);
         }
         return memberList;
    }
    
    /**
     * caohuiying 删除团队成员
     * @param teamMember
     * @return
     * @throws HttpRPCException 
     */
    @Override
    public TeamMember deleteTeamMember(TeamMember teamMember) throws HttpRPCException {
        try {
             TeamMemberDAO teamMemberdao =new TeamMemberDAO(ConnectionUtil.
                     getConnSrc());
             teamMemberdao.deleteTeamMember(teamMember);
         } catch (SQLException ex) {
             ex.printStackTrace();
              log.error("delete team member failed", ex);
            throw new HttpRPCException("delete team member failed", ErrorCode.DB_ERROR);
         }
         return teamMember;
    }
    
    /**
     * caohuiying 通过userId查询所在团队的所有用户id
     * @param teamMember
     * @return
     * @throws HttpRPCException 
     */
    @Override
    public List<String> getAllUserIdInTeamByUserId(String userId) throws HttpRPCException {
         List<String> userIdsInTeam = null;
        try {
             TeamDAO teamDao =new TeamDAO(ConnectionUtil.
                     getConnSrc());
             Team team = teamDao.queryTeamIdByUserId(userId);
             TeamMemberDAO memberDao = new TeamMemberDAO(ConnectionUtil.
                     getConnSrc()); 
            
             userIdsInTeam = memberDao.queryAllTeamByUsers(team.getTeamId());
             
         } catch (SQLException ex) {
             ex.printStackTrace();
              log.error("delete team member failed", ex);
            throw new HttpRPCException("delete team member failed", ErrorCode.DB_ERROR);
         }
         return userIdsInTeam;
    }
}
