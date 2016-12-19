/*
 * TeamMemberDAO.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-03-26 19:37:56
 */
package com.yz.ams.server.dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.yz.ams.model.Team;
import com.yz.ams.model.TeamMember;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lyc
 */
public class TeamMemberDAO extends AbstractORMDAO<TeamMember>{
    public TeamMemberDAO(ConnectionSource connSrc)
            throws SQLException {
        super(connSrc, TeamMember.class);
    }
    /**
     * 添加团队成员
     *  caohuiying
     * @param teamMember
     * @throws SQLException
     */
    public void addTeamMember(TeamMember teamMember)throws SQLException{
        dao.create(teamMember);
    }
    
    /**
     * 删除团队成员
     *  caohuiying
     * @param teamMember
     * @throws SQLException
     */
    public void deleteTeamMember(TeamMember teamMember)throws SQLException{
        dao.delete(teamMember);
    }

    /**
     *  员工所在的团队的所有人员ID(除了自己)
     * @param userId
     * @param teamids
     * @return
     * @throws SQLException 
     */
    public List<String> queryAllTeamUserIdByUserId(String userId,List<String> teamids) throws SQLException{
        QueryBuilder<TeamMember,String> qb = dao.queryBuilder();
        qb.selectColumns("user_id")
                .where()
                .in("team_id",dao.queryBuilder()
                        .selectColumns("team_id")
                        .where()
                        .eq("user_id", userId).and().in("team_id", teamids).queryForFirst().getTeamId()).and().ne("user_id",userId);
        List<String> ids = new ArrayList<>();
        for(String[] strArr : qb.queryRaw().getResults()){
            ids.add(strArr[0]);
        }
        return ids;
    }

    /**
     * 根据ID查询团队内成员
     *  wang cheng bin
     * @param MemberId
     * @return
     * @throws SQLException
     */
    public TeamMember queryTeamMemberByID(String MemberId) throws SQLException {
        return dao.queryForId(MemberId);
    }
    
    /**
     * 根据员工iD查询出所有团队对象
     * @param userId
     * @param ids
     * @return
     * @throws SQLException 
     */
    public TeamMember queryTeamId(String userId,List<String>ids)throws SQLException{
          QueryBuilder<TeamMember,String> qb = dao.queryBuilder();
          qb.selectColumns("team_id")
           .where()
           .eq("user_id", userId)
                  .and()
                  .in("team_id", ids);
        return qb.queryForFirst();
    }
    
      /**
       * 根据团队iD查询出所有员工id
       * @param teamId
       * @return
       * @throws SQLException 
       */
    public List<String> queryUserId(String teamId)throws SQLException{
          QueryBuilder<TeamMember,String> qb = dao.queryBuilder();
          qb.selectColumns("user_id")
           .where()
           .eq("team_id", teamId);
             List<String> ids = new ArrayList<>();
        for(String[] strArr : qb.queryRaw().getResults()){
            ids.add(strArr[0]);
        }
         return ids;
    }
   /**
     * lyc
     * @param teamId
     * @return 根据团队id查询出团队所有人
     * @throws SQLException 
     */
    public List<String> queryAllTeamByUsers(String teamId) throws SQLException{
        QueryBuilder<TeamMember,String> qb = dao.queryBuilder();
        qb.selectColumns("user_id")
                          .where()
                        .eq("team_id", teamId);
        List<String> ids = new ArrayList<>();
        for(String[] strArr : qb.queryRaw().getResults()){
            ids.add(strArr[0]);
        }
        return ids;
    }
    
     /**
     * lyc
     * @param teamId
     * @return 通过teamId得到所有member对象
     * @throws SQLException 
     */
    public List<TeamMember> queryAllMemberByTeamId(String teamId) throws SQLException{
        QueryBuilder<TeamMember,String> qb = dao.queryBuilder();
        qb.where()
                        .eq("team_id", teamId);
        List<TeamMember> members = new ArrayList<>();
        for(TeamMember member : qb.query()){
            members.add(member);
        }
        return members;
    }
   
    /**
     * lyc
     * @return 团队的id和所在团队的人数
     * @throws SQLException 
     */
    public Map<String,String> queryMemberOfTeam() throws SQLException{
        GenericRawResults<String[]> results= dao.queryBuilder()
                .selectRaw("count(1)","team_id")
                .groupBy("team_id")
                .queryRaw();
        Map<String,String> map = new HashMap<>();
        for (String[] strArr : results.getResults()) {
            map.put(strArr[1],strArr[0]);
        }
        return map;
    }
    /**
     * 查询入职时间
     * @param userId
     * @return
     * @throws SQLException 
     */
    public List<TeamMember> queryTeamMenberForUserID(String userId) throws SQLException {
       QueryBuilder builder =  dao.queryBuilder();
        builder.selectColumns("user_id","entry_time")
                .where()
                .eq("user_id", userId);
        return builder.query();
       
    }
    
     /**
     * 根据userId查询所在团队id
     * @param userId
     * @return
     * @throws SQLException 
     */
    public List<TeamMember> queryTeamMembersUserId(String userId) throws SQLException {
       QueryBuilder<TeamMember,String> builder =dao.queryBuilder();
         builder
                 .where()
                 .eq("user_id",userId);
        return builder.query();
    }
}
