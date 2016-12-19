/*
 * BizTripDAO.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-22 14:53:50
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
import com.yz.ams.model.Attendance;
import com.yz.ams.model.BizTrip;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Your Name <Song Haixiang >
 */
public class BizTripDAO extends AbstractORMDAO<BizTrip> {
    public BizTripDAO(ConnectionSource connSrc) throws SQLException {
        super(connSrc, BizTrip.class);
    }

    /**
     * 获取出差申请列表
     *
     * @param keyword
     * @param start
     * @param count
     * @return
     * @throws SQLException
     */
    public List<BizTrip> query(String keyword, long start, long count) throws
            SQLException {
            QueryBuilder builder = dao.queryBuilder();
            Where<BizTrip, String> where = builder.where();
            if (!StringUtil.isEmpty(keyword)) {
                where.and(where.like("applicant_name", "%" + keyword + "%"),
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
     * 获取出差申请列表
     * @param userId
     * @param dateTime
     * @param isAfter
     * @param count
     * @return
     * @throws SQLException
     */
    public List<BizTrip> queryMyApply(String userId, Date dateTime,
            boolean isAfter,
            long count) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        if (isAfter) {
            builder.where().gt("create_time", dateTime).and().eq(
                    "applicant_id", userId)
                    .and()
                    .eq("deleted", false);
            builder.orderBy("create_time", false);
            builder.limit(count);

        } else {
            builder.where().lt("create_time", dateTime).and().eq(
                    "applicant_id", userId).and()
                 .eq("deleted", false);
            builder.orderBy("create_time", false);
            builder.limit(count);
        }
        return builder.query();
    }

  
    /**
     * 获取出差审核列表
     * @param list
     * @param dateTime
     * @param isAfter
     * @param count
     * @return
     * @throws SQLException
     */
    public List<BizTrip> queryCEOAudit(Date dateTime, boolean isAfter,
            long count) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        Where<BizTrip, String> where = builder.where();
        if (isAfter) {
            builder.where().gt("create_time", dateTime).and().eq("audit_state", AuditStateEnum.WAIT_FOR_CEO).and()
                 .eq("deleted", false);;
        } else {
            builder.where().lt("create_time", dateTime).and().eq("audit_state", AuditStateEnum.WAIT_FOR_CEO).and()
                 .eq("deleted", false);;
        }
        builder.orderBy("create_time", false);
        builder.limit(count);
        return builder.query();

    }

    /**
     * 获取数量
     * @param keyword
     * @return
     * @throws SQLException
     */
    public int queryCount(String keyword) throws SQLException {
            QueryBuilder builder = dao.queryBuilder();
            Where<BizTrip, String> where = builder.where();
            if (!StringUtil.isEmpty(keyword)) {
                where.and(where.like("applicant_name", "%" + keyword + "%"),
                        where.eq("deleted", false));
            } else {
                where.eq("deleted", false);
            }
            return (int) builder.countOf();
    }

    /**
     * 添加一个出差对象 lyc
     * @param biz
     * @throws SQLException
     */
    public void createBizTripId(BizTrip biz) throws SQLException {
        dao.create(biz);
    }

    /**
     * 获取出差记录信息 lyc
     * @param bizTripId
     * @return biztrip对象
     * @throws SQLException
     */
    public BizTrip queryBiztrip(String bizTripId) throws SQLException {
          if (!StringUtil.isEmpty(bizTripId)) {
             QueryBuilder builder = dao.queryBuilder();
             builder.where().eq("biz_trip_id", bizTripId)
                    .and()
                    .eq("deleted", false);
               return (BizTrip)builder.queryForFirst();
        }
        return null;
    }

   /**
    * lyc 更新出差审批状态
    * @param bizTripId
    * @param auditorId
    * @param auditorName
    * @param state
    * @throws SQLException 
    */
    public void updateBizTripState(String bizTripId, String auditorId,
            String auditorName,AuditStateEnum state) throws
            SQLException {
        UpdateBuilder builder = dao.updateBuilder();
        builder.where().idEq(bizTripId);
        builder.updateColumnValue("audit_state", state);
        builder.updateColumnValue("auditor_id", auditorId);
         builder.updateColumnValue("auditor_name", auditorName);
          builder.updateColumnValue("audit_time",new Date());
        builder.update();
    }

    /**
     * 添加
     * @param bizTrip
     * @return
     * @throws SQLException
     */
    public BizTrip createBizTrip(BizTrip bizTrip) throws SQLException {
        dao.create(bizTrip);
        return bizTrip;
    }

    /**
     * 修改
     * @param bizTrip
     * @return
     * @throws SQLException
     */
    public BizTrip modifyBizTrip(BizTrip bizTrip) throws SQLException {
            bizTrip.setModifyTime(new Date());
            dao.update(bizTrip);
            return bizTrip;
    }

    /**
     * 删除
     * @param bizTrip
     * @return
     * @throws SQLException
     */
    public BizTrip deleteBizTrip(BizTrip bizTrip) throws SQLException {
            bizTrip.setModifyTime(new Date());
            bizTrip.setDeleted(true);
            dao.update(bizTrip);
            return bizTrip;
    }
/**
    * wangcb
     * 根据UserId获取出差记录
     * @param user
     * @param today
     * @return
     * @throws SQLException
     */
    public boolean queryBizTripByAttendanceId(USMSUser user,Date today) throws SQLException{
        boolean b=true;
        QueryBuilder<BizTrip, String> bdr = dao.queryBuilder();
        bdr.selectRaw("DATE_ADD(start_date,INTERVAL days DAY)")
            .where()
            .eq("audit_state",AuditStateEnum.PASS )
                .and()
                .le("start_date",today)
               .and()
               .like("staff_ids", "%"+user.getId()+"%")
                .and()
                 .eq("deleted", false);
        
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
     * @param user
     * @param today
     * @return
     * @throws SQLException
     */
    public boolean IfWaitBizTripByUserId(USMSUser user,Date today) throws SQLException{
        boolean b=false;
        QueryBuilder<BizTrip, String> bdr = dao.queryBuilder();
        bdr.selectRaw("DATE_ADD(start_date,INTERVAL days DAY)")
            .where()
            .eq("audit_state",AuditStateEnum.WAIT_FOR_PM )
            .or()
            .eq("audit_state",AuditStateEnum.WAIT_FOR_CEO)
            .and()
            .like("staff_ids", "%"+user.getId()+"%")
               .and()
               .eq("deleted", false);
         bdr.orderBy("start_date", false);
       if(dao.queryRaw(bdr.prepareStatementString(), new DataType[]{DataType.DATE}).getResults().size()>0){
            Date endDate=((Date)dao.queryRaw(bdr.prepareStatementString(), new DataType[]{DataType.DATE}).getFirstResult()[0]);
            if(today.getTime()<endDate.getTime()){
                b=true;
            }
        }
        return b;
    }
    
    /**zhaohongkun
     * 出勤list
     * @param start
     * @param end
     * @return
     * @throws SQLException 
     */
    public List<BizTrip> queryBizTripList(Date start, Date end) throws SQLException {
        QueryBuilder<BizTrip, String> builder = dao.queryBuilder();
        Where<BizTrip, String> where = builder.where();
        where.and(where.eq("deleted", false).and().eq("audit_state", AuditStateEnum.PASS),
                where.or(where.or(where.ge("start_date", start)
                        .and()
                        .le("start_date", end), where.ge("end_date", start)
                        .and()
                        .le("end_date", end)),where.and(where.lt("start_date", start),where.gt("end_date", end))));

        return builder.query();
    }
}
