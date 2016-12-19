package com.yz.ams.server.dao;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.yz.ams.model.Team;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wangcb
 */
public class TeamDAO extends AbstractORMDAO<Team>{
    
    public TeamDAO(ConnectionSource connSrc, Class<Team> claz) throws SQLException {
        super(connSrc, claz);
    }
    public TeamDAO(ConnectionSource connSrc) throws SQLException {
        super(connSrc, Team.class);
    }
    /**
     * 根据ID查询团队
     *  wang cheng bin
     * @param teamID
     * @return
     * @throws SQLException
     */
public Team queryTeamByID(String teamID) throws SQLException {
        return dao.queryForId(teamID);
    }
     /**
     * 获取团队列表
     *  wang cheng bin
     * @return List<Team>
     * @throws SQLException
     */
    public List<Team> queryTeam() throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder.orderBy("team_id", false);
        
        return builder.query();
    }
    /**
     * 添加团队
     *  wang cheng bin
     * @param team
     * @throws SQLException
     */
    public void createTeam(Team team)throws SQLException{
        dao.create(team);
    }
     /**
     * 修改团队
     *  wang cheng bin
     * @param team
     * @throws SQLException
     */
    public void modifyTeam(Team team)throws SQLException{
        dao.update(team);
    }
     /**
     * 删除团队
     *  wang cheng bin
     * @param teamId
     * @throws SQLException
     */
     public void deleteTeam(String teamId) throws SQLException{
         dao.deleteById(teamId);
     }
  
     /**
      * lyc
      * 根据id查询团队名称
      * @param timeId
      * @return
      * @throws SQLException 
      */
    public String queryTimeNames(String timeId) throws SQLException{
          QueryBuilder<Team,String> builder =dao.queryBuilder();
         builder.selectColumns("team_name")
                 .where()
                 .eq("team_id",timeId);
         String timeName = builder.queryForFirst().getTeamName();
        return timeName;
    }
    
    /**
     * 获取非true的团队
     *  wang cheng bin
     * @return List<Team>
     * @throws SQLException
     */
    public List<String> queryTeamNotTrue() throws SQLException {
        QueryBuilder<Team,String> qb = dao.queryBuilder();
          qb.selectColumns("team_id")
           .where()
           .eq("is_mgmt", false);
             List<String> ids = new ArrayList<>();
        for(String[] strArr : qb.queryRaw().getResults()){
            ids.add(strArr[0]);
        }
        return ids;
    }
    
    /**
     * 根据userId查询所在团队id
     * @param userId
     * @return
     * @throws SQLException 
     */
    public Team queryTeamIdByUserId(String userId) throws SQLException {
       QueryBuilder builder =  dao.queryBuilder();
        builder
                .where()
                .eq("user_id", userId);
        return (Team)builder.queryForFirst();
       
    }
}
