/*
 * VacationDAO.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-05 09:53:15
 */
package com.yz.ams.server.dao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.usm.model.USMSUser;
import com.nazca.util.StringUtil;
import com.yz.ams.consts.AuditStateEnum;
import com.yz.ams.consts.SickCertificateStateEnum;
import com.yz.ams.model.BizTrip;
import com.yz.ams.model.Vacation;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 请假管理
 *
 * @author Zhang Chun Nan
 */
public class VacationDAO extends AbstractORMDAO<Vacation> {

    public VacationDAO(ConnectionSource connSrc) throws SQLException {
        super(connSrc, Vacation.class);
    }

    public VacationDAO(ConnectionSource connSrc, Class<Vacation> aClass) throws
            SQLException {
        super(connSrc, aClass);
    }

    /**
     * 获取请假列表 litao
     *
     * @param userId
     * @param dateTime
     * @param isAfter
     * @param count
     * @return
     * @throws SQLException
     */
    public List<Vacation> queryMyApply(String userId, Date dateTime,
            boolean isAfter,
            long count) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        if (isAfter) {
            builder.where().gt("create_time", dateTime).and().eq("applicant_id",
                    userId).and()
                 .eq("deleted", false);
        } else {
            builder.where().lt("create_time", dateTime).and().eq("applicant_id",
                    userId).and()
                 .eq("deleted", false);
        }
        builder.orderBy("create_time", false);
        builder.limit(count);
        return builder.query();
    }

    /**
     * 获取请假列表
     * @param keyword
     * @param start
     * @param count
     * @return
     * @throws SQLException
     */
    public List<Vacation> query(String keyword, long start, long count) throws
            SQLException {
        QueryBuilder<Vacation, String> builder = dao.queryBuilder();
        Where<Vacation, String> where = builder.where();
        if (!StringUtil.isEmpty(keyword)) {
            where.and(where.like("applicant_name", "%" + keyword + "%"), where.
                    eq("deleted", false));
        } else {
            where.eq("deleted", false);
        }
        builder.orderBy("vacation_date", false);
        builder.offset(start);
        builder.limit(count);
        return builder.query();
    }

    /**
     * 获取审核列表 litao
     *
     * @param list
     * @param dateTime
     * @param isAfter
     * @param count
     * @return
     * @throws SQLException
     */
    public List<Vacation> queryPMAudit(List<String> list, Date dateTime,
            boolean isAfter, long count) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        Where<Vacation, String> where = builder.where();

        if (isAfter) {
            int index = 0;
            for (String data : list) {
                where.eq("applicant_id", data).and()
                .eq("deleted", false);
                index++;
                if (index != list.size()) {
                    where.or();
                }
            }
            where.and();
            where.gt("create_time", dateTime);
            where.and();
            where.eq("audit_state", AuditStateEnum.WAIT_FOR_PM);
        } else {
            int index = 0;
            for (String data : list) {
                where.eq("applicant_id", data).and()
              .eq("deleted", false);
                index++;
                if (index != list.size()) {
                    where.or();
                }
            }
            where.and();
            where.lt("create_time", dateTime);
            where.and();
            where.eq("audit_state", AuditStateEnum.WAIT_FOR_PM);
        }

        builder.orderBy("create_time", false);
        builder.limit(count);
        return builder.query();
    }

    /**
     * 获取CEO审核信息
     * @param dateTime
     * @param isAfter
     * @param count
     * @return
     * @throws SQLException
     */
    public List<Vacation> queryCEOAudit(Date dateTime,
            boolean isAfter, int count) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        Where<Vacation, String> where = builder.where();

        if (isAfter) {
            where.gt("create_time", dateTime);
        } else {
            where.lt("create_time", dateTime);
        }
        where.and();
        where.eq("audit_state", AuditStateEnum.WAIT_FOR_CEO).and()
              .eq("deleted", false);
        builder.orderBy("create_time", false);
        builder.limit(count);
        return builder.query();

    }

    /**
     *
     * @param keyword
     * @return
     * @throws java.sql.SQLException
     */
    public int queryCount(String keyword) throws Exception {
        QueryBuilder builder = dao.queryBuilder();
        Where<Vacation, String> where = builder.where();
        if (!StringUtil.isEmpty(keyword)) {
            where.and(where.like("applicant_name", "%" + keyword + "%"),
                    where.eq("deleted", false));
        } else {
            where.eq("deleted", false);
        }
        builder.orderBy("create_time", true);
        return (int) builder.countOf();
    }

    /**
     * 根据请假id查询一条请假信息 litao
     *
     * @param vacationId
     * @return
     * @throws SQLException
     */
    public Vacation queryById(String vacationId) throws SQLException {
        if (!StringUtil.isEmpty(vacationId)) {
             QueryBuilder builder = dao.queryBuilder();
             builder.where().eq("vacation_id", vacationId)
                    .and()
                    .eq("deleted", false);
               return (Vacation)builder.queryForFirst();
        }
        return null;
    }

    /**
     *
     * 插入一条请假数据 litao
     *
     * @param vacation
     * @throws java.sql.SQLException
     */
    public void create(Vacation vacation) throws SQLException {
        dao.create(vacation);
    }

    /**
     * litao 上传病假证明更新 vacation
     *
     * @param vacation
     * @throws java.sql.SQLException
     */
    public void update(Vacation vacation) throws SQLException {
        dao.update(vacation);
        
    }

    /**
     * 更新请假审批状态(通过/不通过)
     * @param vacationId
     * @param auditorId
     * @param auditorName
     * @param state
     * @param isPM
     * @throws SQLException 
     */
    public void updateVacationState(String vacationId, String auditorId,
          String auditorName,AuditStateEnum state,boolean isPM) throws
            SQLException {
        UpdateBuilder builder = dao.updateBuilder();
        builder.where().idEq(vacationId);
        builder.updateColumnValue("audit_state", state);
        if (isPM) {
            builder.updateColumnValue("auditor_id1", auditorId);
            builder.updateColumnValue("auditor_name1",auditorName);
            builder.updateColumnValue("audit_time1",new Date());
        } else {
            builder.updateColumnValue("auditor_id2", auditorId);
            builder.updateColumnValue("auditor_name2", auditorName);
             builder.updateColumnValue("audit_time2",new Date());
        }
        builder.update();
    }

    /**
     * 添加
     *
     * @param vacation
     * @return
     * @throws SQLException
     */
    public Vacation createVacation(Vacation vacation) throws SQLException {
        try {
            vacation.setCreateTime(new Date());
            //审核状态、删除状态、uuid、创建时间、
            vacation.setVacationId(UUID.randomUUID().toString());
            vacation.setDeleted(false);
            vacation.setCreateTime(new Date());
            dao.create(vacation);
            return vacation;
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * 修改
     *
     * @param vacation
     * @return
     * @throws SQLException
     */
    public Vacation modifyVacation(Vacation vacation) throws SQLException {
            vacation.setModifyTime(new Date());
            dao.update(vacation);
            return vacation;
    }

    public Vacation deleteVacation(Vacation vac) throws SQLException {
            vac.setModifyTime(new Date());
            vac.setDeleted(true);
            dao.update(vac);
            return vac;
    }

    /**
     * 获取证明图片
     *
     * @param certificatePicId
     * @return
     */
    public OutputStream queryCertificatePic(String certificatePicId) throws
            SQLException {
        return null;
        //TDOO
    }

    /**
     * 审批通过
     * @param vacationId
     * @throws SQLException 
     */
    public void auditCertificatePass(String vacationId) throws SQLException {
         Vacation vaction = dao.queryForId(vacationId);
         vaction.setCertificateState(SickCertificateStateEnum.PASS);
         dao.update(vaction);
    }

    /**
     *
     * 审批不通过
     *
     * @param vacationId
     * @throws java.sql.SQLException
     */
    public void auditCertificateDeny(String vacationId) throws SQLException {
        Vacation vaction = dao.queryForId(vacationId);
         vaction.setCertificateState(SickCertificateStateEnum.DENY);
         dao.update(vaction);
    }

    /**
     * lyc
     * @param userId
     * @param start
     * @param end
     * @return 上个月的个人请假天数和
     * @throws SQLException
     */
    public double queryVacationDaysInMouth(String userId, Date start, Date end)
            throws SQLException {
        QueryBuilder<Vacation, String> qb = dao.queryBuilder();
        qb.selectRaw("sum(total_days)")
                .where()
                .eq("applicant_id", userId)
                .and()
                .eq("audit_state",AuditStateEnum.PASS)
                .and()
                .ge("vacation_date", start)
                .and()
                .le("vacation_date", end)
                .and()
                 .eq("deleted", false);

        String days = qb.queryRawFirst()[0];
        if (!StringUtil.isEmpty(days)) {
            return Double.parseDouble(days);
        }
        return 0;
    }

    /**
     * lyc
     * @param userIds
     * @param start
     * @param end
     * @return 上个月的团队请假天数和
     * @throws SQLException
     */
    public double queryVacationDaysInMouth(List<String> userIds, Date start,
            Date end) throws SQLException {
        QueryBuilder<Vacation, String> qb = dao.queryBuilder();
        qb.selectRaw("sum(total_days)")
                .where()
                .in("applicant_id", userIds)
                .and()
                .eq("audit_state",AuditStateEnum.PASS)
                .and()
                .le("vacation_date", end)
                .and()
                .ge("vacation_date", start)
                .and()
                 .eq("deleted", false);

        String days = qb
                .queryRawFirst()[0];
        if (!StringUtil.isEmpty(days)) {
            return Double.parseDouble(days);
        }
        return 0;
    }
    /**
     * wangcb
     * 根据UserId获取请假记录
     *
     * @param user
     * @param today
     * @return
     * @throws SQLException
     */
    public boolean queryVacationByAttendanceId(USMSUser user,Date today) throws SQLException{
        boolean b=true;
        QueryBuilder<Vacation, String> bdr = dao.queryBuilder();
        bdr.selectRaw("DATE_ADD(vacation_date,INTERVAL total_days DAY)")
            .where()
            .eq("audit_state",AuditStateEnum.PASS)
               .and()
              .eq("deleted", false)
              .and()
              .le("vacation_date",today)
            .and()
            .like("applicant_id", "%"+user.getId()+"%");
         bdr.orderBy("vacation_date", false);
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
    public boolean IfWaitVacationByUserId(USMSUser user,Date today) throws SQLException{
        boolean b=false;
        QueryBuilder<Vacation, String> bdr = dao.queryBuilder();
        bdr.selectRaw("DATE_ADD(vacation_date,INTERVAL total_days DAY)")
            .where()
            .eq("audit_state",AuditStateEnum.WAIT_FOR_PM )
             .and()
             .eq("deleted", false)
             .or()
            .eq("audit_state",AuditStateEnum.WAIT_FOR_CEO)
            .and()
            .like("applicant_id", "%"+user.getId()+"%");
         bdr.orderBy("vacation_date", false);
       if(dao.queryRaw(bdr.prepareStatementString(), new DataType[]{DataType.DATE}).getResults().size()>0){
            Date endDate=((Date)dao.queryRaw(bdr.prepareStatementString(), new DataType[]{DataType.DATE}).getFirstResult()[0]);
            if(today.getTime()<endDate.getTime()){
                b=true;
            }
        }
        return b;
    }
    
    /**
     * caohuiying 报表统计中用到的请假list
     * @param start
     * @param end
     * @return
     * @throws SQLException
     */
    public List<Vacation> queryVacationList(Date start, Date end) throws SQLException {
         QueryBuilder builder = dao.queryBuilder();
         builder.where()
            .ge("vacation_date", start)
            .and()
            .le("vacation_date", end)
            .and()
            .eq("audit_state", AuditStateEnum.PASS)
            .and()
             .eq("deleted", false);
            return  builder.query();
    }
   
}
