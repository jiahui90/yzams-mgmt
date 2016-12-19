/*
 * RestDAO.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-22 15:11:59
 */
package com.yz.ams.server.dao;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.nazca.usm.model.USMSUser;
import com.nazca.util.StringUtil;
import com.yz.ams.consts.AuditStateEnum;
import com.yz.ams.model.BizTrip;
import com.yz.ams.model.Rest;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Your Name <Song Haixiang >
 */
public class RestDAO extends AbstractORMDAO<Rest> {
    public RestDAO(ConnectionSource connSrc) throws SQLException {
        super(connSrc, Rest.class);
    }

    /**
     * 获取调休列表auditor_id
     *
     * @param keyword
     * @param start
     * @param count
     * @return
     * @throws SQLException
     */
    public List<Rest> query(String keyword, long start, long count) throws
            SQLException {
            QueryBuilder builder = dao.queryBuilder();
            Where<Rest, String> where = builder.where();
            if (!StringUtil.isEmpty(keyword)) {
                where.and(where.like("user_name", "%" + keyword + "%"),
                        where.eq("deleted", false));
            } else {
                where.eq("deleted", false);
            }
            builder.orderBy("create_time", true);
            builder.offset(start);
            builder.limit(count);
            return builder.query();
    }

    /**
     * 获取调休申请列表
     * @param userId
     * @param dateTime
     * @param isAfter
     * @param count
     * @return
     * @throws SQLException 
     */
    public List<Rest> queryMyApply(String userId, Date dateTime, boolean isAfter,
            long count) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        if (isAfter) {
            builder.where().gt("create_time", dateTime)
              .and()
              .eq("deleted", false)
              .and()
              .eq( "user_id", userId);
            builder.orderBy("create_time", false);
            builder.limit(count);

        } else {
            builder.where().lt("create_time", dateTime)
              .and()
             .eq("deleted", false)
             .and()
             .eq( "user_id", userId);
            builder.orderBy("create_time", false);
            builder.limit(count);
        }
        return builder.query();
    }

    /**
     * 获取调休审核列表（移动端）
     * @param dateTime
     * @param isAfter
     * @param count
     * @return
     * @throws SQLException
     */
    public List<Rest> queryCEOAudit(Date dateTime, boolean isAfter,
            long count) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        if (isAfter) {
            builder.where().gt("create_time", dateTime)
                    .and()
                    .eq("audit_state", AuditStateEnum.WAIT_FOR_CEO)
                    .and()
                    .eq("deleted", false);
        } else {
            builder.where().lt("create_time", dateTime)
                    .and().eq("audit_state", AuditStateEnum.WAIT_FOR_CEO)
                    .and()
                    .eq("deleted", false);
        }
        builder.orderBy("create_time", false);
        builder.limit(count);
        return builder.query();
    }

    /**
     * 获取数量
     *
     * @param keyword
     * @return
     * @throws SQLException
     */
    public int queryCount(String keyword) throws SQLException {
            QueryBuilder builder = dao.queryBuilder();
            Where<Rest, String> where = builder.where();
            if (!StringUtil.isEmpty(keyword)) {
                where.and(where.like("user_name", "%" + keyword + "%"),
                        where.eq("deleted", false));
            } else {
                where.eq("deleted", false);
            }
            return (int) builder.countOf();
    }

    /**
     * lyc 添加一个调休对象
     * @param rest
     * @throws SQLException
     */
    public void createRestId(Rest rest) throws SQLException {
        dao.create(rest);
    }

    /**
     * lyc 获取调休记录信息
     * @param userid
     * @return
     * @throws SQLException 
     */
    public Rest queryRest(String userid) throws SQLException {
       if (!StringUtil.isEmpty(userid)) {
             QueryBuilder builder = dao.queryBuilder();
             builder.where().eq("rest_id", userid)
                    .and()
                    .eq("deleted", false);
               return (Rest)builder.queryForFirst();
        }
        return null;
    }

    /**
     * lyc 更新调休审批状态
     * @param restId
     * @param auditorId
     * @param auditorName
     * @param state
     * @throws SQLException 
     */
    public void updateRestState(String restId, String auditorId,
           String auditorName,AuditStateEnum state) throws
            SQLException {
        UpdateBuilder builder = dao.updateBuilder();
        builder.where().idEq(restId);
        builder.updateColumnValue("audit_state", state);
        builder.updateColumnValue("auditor_id", auditorId);
        builder.updateColumnValue("auditor_name",auditorName);
        builder.updateColumnValue("audit_time",new Date());
        builder.update();
    }

    /**
     * 添加
     * @param rest
     * @return
     * @throws SQLException
     */
    public Rest createRest(Rest rest) throws SQLException {
        dao.create(rest);
        return rest;
    }

    /**
     * 修改
     * @param rest
     * @return
     * @throws SQLException
     */
    public Rest modifyRest(Rest rest) throws SQLException {
            rest.setModifyTime(new Date());
            dao.update(rest);
            return rest;
    }

    /**
     * 删除
     * @param rest
     * @return
     * @throws SQLException
     */
    public Rest deleteRest(Rest rest) throws SQLException {
            rest.setModifyTime(new Date());
            rest.setDeleted(true);
            dao.update(rest);
            return rest;
    }
    
     /**
    * lyc
    * @param userId
    * @param start
    * @param end
    * @return 上个月员工调休天数和
    * @throws SQLException 
    */
   public double queryRestDaysInMouth(String userId,Date start,Date end) throws SQLException{
       QueryBuilder<Rest, String> bdr = dao.queryBuilder();
        bdr.selectRaw("sum(days)")
        .where()
        .like("staff_ids", "%"+ userId + "%")
        .and()
        .eq("audit_state",AuditStateEnum.PASS )
        .and()
         .eq("deleted", false)
         .and()
         .le("start_date", end)
         .and()
         .ge("start_date", start);
       double days = (Double)dao.queryRaw(bdr.prepareStatementString(), new DataType[]{DataType.DOUBLE}).getFirstResult()[0];
       return days;
   }
   /**
    * wangcb
     * 根据UserId获取调休记录
     *
     * @param user
     * @param today
     * @return
     * @throws SQLException
     */
    public boolean queryRestByAttendanceId(USMSUser user,Date today) throws SQLException{
        boolean b=true;
        QueryBuilder<Rest, String> bdr = dao.queryBuilder();
        bdr.selectRaw("DATE_ADD(start_date,INTERVAL days DAY)")
            .where()
            .eq("audit_state",AuditStateEnum.PASS)
            .and()
             .eq("deleted", false)
             .and()
             .le("start_date",today)
            .and()
            .like("staff_ids", "%"+user.getId()+"%");
        bdr.orderBy("start_date", false);
       if(dao.queryRaw(bdr.prepareStatementString(), new DataType[]{DataType.DATE}).getResults().size()>0){
            Date endDate=((Date)dao.queryRaw(bdr.prepareStatementString(), new DataType[]{DataType.DATE}).getFirstResult()[0]);
            if(today.getTime()<endDate.getTime()){
                b=false;
            }
        }
        return b;
    }

        /**
     * wangcb
     * 根据UserId获取请假记录是否为待审核状态
     *
     * @param user
     * @param today
     * @return
     * @throws SQLException
     */
    public boolean IfWaitRestByUserId(USMSUser user,Date today) throws SQLException{
        boolean b=false;
        QueryBuilder<Rest, String> bdr = dao.queryBuilder();
        bdr.selectRaw("DATE_ADD(start_date,INTERVAL days DAY)")
            .where()
             .eq("deleted", false)
              .and()
            .eq("audit_state",AuditStateEnum.WAIT_FOR_PM )
            .or()
            .eq("audit_state",AuditStateEnum.WAIT_FOR_CEO)
            .and()
            .like("staff_ids", "%"+user.getId()+"%");
         bdr.orderBy("start_date", false);
       if(dao.queryRaw(bdr.prepareStatementString(), new DataType[]{DataType.DATE}).getResults().size()>0){
            Date endDate=((Date)dao.queryRaw(bdr.prepareStatementString(), new DataType[]{DataType.DATE}).getFirstResult()[0]);
            if(today.getTime()<endDate.getTime()){
                b=true;
            }
        }
        return b;
    }

    /**zhaohongkun修改
     * caohuiying 报表中用到的出勤list
     * @param start
     * @param end
     * @return
     * @throws SQLException
     */
    public List<Rest> queryRestList(Date start, Date end) throws SQLException {
        QueryBuilder<Rest, String> builder = dao.queryBuilder();
        Where<Rest, String> where = builder.where();
        where.and(where.eq("deleted", false).and().eq("audit_state", AuditStateEnum.PASS),
                 where.or(where.or(where.ge("start_date", start)
                        .and()
                        .le("start_date", end), where.ge("end_date", start)
                        .and()
                        .le("end_date", end)),where.and(where.lt("start_date", start),where.gt("end_date", end))));
        return builder.query();
    }
}
