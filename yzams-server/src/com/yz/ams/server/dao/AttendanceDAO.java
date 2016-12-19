/*
 * AttendanceDAO.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-04 16:47:26
 */
package com.yz.ams.server.dao;

import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.nazca.util.StringUtil;
import com.yz.ams.consts.AuditStateEnum;
import com.yz.ams.model.Attendance;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Your Name <Song Haixiang >
 */
public class AttendanceDAO extends AbstractORMDAO<Attendance> {

    public AttendanceDAO(ConnectionSource connSrc) throws SQLException {
        super(connSrc, Attendance.class);
    }

    /**
     * 获取出勤列表
     * @param keyword
     * @param start
     * @param count
     * @return
     * @throws SQLException
     */
    public List<Attendance> query(String keyword, long start, long count) throws SQLException {
            QueryBuilder builder = dao.queryBuilder();
            Where<Attendance, String> where = builder.where();
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
     * 获取数量
     *
     * @param keyword
     * @return
     * @throws SQLException
     */
    public int queryCount(String keyword) throws SQLException {
            QueryBuilder builder = dao.queryBuilder();
            Where<Attendance, String> where = builder.where();
            if (!StringUtil.isEmpty(keyword)) {
                where.and(where.like("user_name", "%" + keyword + "%"),
                        where.eq("deleted", false));
            } else {
                where.eq("deleted", false);
            }
            return (int) builder.countOf();
    }

    /**
     * *
     * lyc 添加一条考勤记录信息
     * @param attendance
     * @return
     * @throws SQLException
     */
    public Attendance creatAttendance(Attendance attendance) throws SQLException {
        attendance.setAttendanceId(UUID.randomUUID().toString());
        attendance.setAuditState(AuditStateEnum.WAIT_FOR_PM);
        attendance.setDeleted(false);
        attendance.setCreateTime(new Date());
        dao.create(attendance);
        return attendance;
    }

    /**
     * 修改
     *
     * @param attendance
     * @return
     * @throws SQLException
     */
    public Attendance modifyAttendance(Attendance attendance) throws SQLException {
        dao.update(attendance);
        return attendance;
    }

    /**
     * 删除
     * @param attendance
     * @return
     * @throws java.sql.SQLException
     */
    public Attendance deleteAttendance(Attendance attendance) throws SQLException {
        dao.update(attendance);
        return attendance;
    }

    public void addAttendance(Attendance attendance) throws SQLException {
        dao.create(attendance);
    }

    /**
     * *
     * lyc 修改一条考勤记录信息
     * @param attendance
     * @return
     * @throws SQLException
     */
    public Attendance updateAttendance(Attendance attendance) throws SQLException {
        dao.update(attendance);
        return attendance;
    }

    /**
     * lyc 根据id查询一条考勤记录对象
     * @param att
     * @param today
     * @return
     * @throws SQLException 
     */
    public Attendance queryAttendId(Attendance att, Date today) throws SQLException {
        if (!StringUtil.isEmpty(att.getUserId())) {
            QueryBuilder builder = dao.queryBuilder();
            builder.where().eq("deleted", false)
              .and().eq("user_id", att.getUserId()).and().eq("attendance_date", today);
            return (Attendance) builder.queryForFirst();
        }
        return null;
    }

    /**
     * lyc 根据id查询一条当天的考勤记录
     * @param userId
     * @param today
     * @return
     * @throws SQLException
     */
    public Attendance queryTodayAttendance(String userId, Date today) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder.where().eq("user_id", userId)
                .and()
                .eq("attendance_date", today)
                .and()
                .eq("deleted", false);
        
        return (Attendance) builder.queryForFirst();
    }

    /**
     * lyc 本周迟到次数
     * @param userId
     * @param start
     * @param end
     * @return
     * @throws SQLException
     */
    public int queryDelayThisWeek(String userId, Date start, Date end) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder
                .where()
                .eq("user_id", userId)
                .and()
                .eq("deleted", false)
                .and()
                .ge("attendance_date", start)
                .and()
                .le("attendance_date", end)
                .and()
                .isNotNull("delay_type");
        return (int) builder.countOf();
    }

    /**
     * lyc 
     * 本周早退次数
     * @param userId
     * @param start
     * @param end
     * @return
     * @throws SQLException
     */
    public int queryEaveEarlyThisWeek(String userId, Date start, Date end) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder
                .where().eq("user_id", userId)
                .and()
                .eq("leave_early", true)
                .and()
                .eq("deleted", false)
                .and()
                .ge("attendance_date",start)
                .and()
                .le("attendance_date",end);
        return (int) builder.countOf();
    }

    /**
     * lyc 本周旷工次数
     * @param userId
     * @param start
     * @param end
     * @return
     * @throws SQLException
     */
    public int queryAbsentThisWeek(String userId, Date start, Date end) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder.selectRaw("count(absent_days)")
                .where().eq("user_id", userId)
                .and()
                .ge("attendance_date", start)
                .and()
                .le("attendance_date", end)
                .and()
                  .eq("deleted", false)
                .and()
                .gt("absent_days",0);
        return (int) dao.queryRawValue(builder.prepareStatementString());
    }
    
    /**
     * lyc
     * @param userId
     * @param start
     * @param end
     * @return 返回旷工的天数
     * @throws SQLException 
     */
    public double queryAbsentThisWeekNumbwer(String userId, Date start, Date end) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder.selectRaw("sum(absent_days)")
                .where().eq("user_id", userId)
                 .and()
                 .eq("deleted", false)
                .and()
                .ge("attendance_date", start)
                .and()
                .le("attendance_date", end)
                .and()
                .gt("absent_days", 0);
        GenericRawResults<Object[]> r = dao.queryRaw(builder.prepareStatementString(), new DataType[]{DataType.DOUBLE});
       return (Double)r.getFirstResult()[0];
    }
    
 
    /**
     * lyc 上个月团队迟到次数
     * @param userIds
     * @param start
     * @param end
     * @return
     * @throws SQLException 
     */
    public int queryDelayLastMonth(List<String> userIds, Date start, Date end) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder
                .where()
                 .eq("deleted", false)
                 .and()
                .in("user_id", userIds)
                .and()
                .ge("attendance_date", start)
                .and()
                .le("attendance_date", end);
        return (int) builder.countOf();
    }

    /**
     * lyc 上个月团队早退次数
     * @param userIds
     * @param start
     * @param end
     * @return
     * @throws SQLException 
     */
    public int queryEaveEarlyLastMonth(List<String> userIds, Date start, Date end) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        
        builder
                .where().in("user_id", userIds)
                 .and()
                 .eq("deleted", false)
                .and()
                .eq("leave_early", true)
                .and()
                .ge("attendance_date", start)
                .and()
                .le("attendance_date", end);
        return (int) builder.countOf();
    }

    /**
     * lyc
     * 上个月团队旷工次数
     * @param userIds
     * @param start
     * @param end
     * @return
     * @throws SQLException
     */
    public int queryAbsentLastMonth(List<String> userIds, Date start, Date end) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder.selectRaw("count(case  when absent_days>0 then 1 else 0 END)")
                .where().in("user_id", userIds)
                 .and()
                 .eq("deleted", false)
                .and()
                .ge("attendance_date", start)
                .and()
                .le("attendance_date", end)
                .and()
                .gt("absent_days", 0);
        return (int) dao.queryRawValue(builder.prepareStatementString());
    }
    /**
     * wangcb 上个月团队旷工天数
     * @param userIds
     * @param start
     * @param end
     * @return
     * @throws SQLException
     */
    public int queryAbsentDaysLastMonth(List<String> userIds, Date start, Date end) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder.selectRaw("sum(absent_days)")
                .where()
                .in("user_id", userIds)
                 .and()
                 .eq("deleted", false)
                .and()
                .ge("attendance_date", start)
                .and()
                .le("attendance_date", end)
                 .and()
                .gt("absent_days", 0);
        return (int) dao.queryRawValue(builder.prepareStatementString());
    }
    
    /**
     * caohuiying 报表中用到的出勤list
     * @param start
     * @param end
     * @return
     * @throws SQLException
     */
    public List<Attendance> queryAttendanceList(Date start, Date end) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder.where()
                .ge("attendance_date", start)
                .and()
                .le("attendance_date", end)
                 .and()
                .eq("deleted", false);
        return  builder.query();
    }
    /**
     * caohuiying 报表中用到的出勤list
     * @param start
     * @param end
     * @return
     * @throws SQLException
     */
    public List<Attendance> queryAttendanceLeaveEarlyList(Date start, Date end) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder.where()
                .ge("attendance_date", start)
                .and()
                .le("attendance_date", end)
                 .and()
                .eq("leave_early", true)
                .and()
                .eq("deleted", false);
        return  builder.query();
    }
}
