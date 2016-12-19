/*
 * PaidVacationDAO.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-23 14:54:42
 */
package com.yz.ams.server.dao;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.util.StringUtil;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.model.PaidVacation;
import com.yz.ams.model.wrap.app.AttendanceStat;
import com.yz.ams.server.util.ConnectionUtil;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 *年假管理业务具体的实DAO层
 * @author Your Name <Song Haixiang >
 */
public class PaidVacationDAO extends AbstractORMDAO<PaidVacation> {

    public PaidVacationDAO(ConnectionSource connSrc) throws SQLException {
        super(connSrc, PaidVacation.class);
    }

    public List<PaidVacation> query(int year)
            throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        Where<PaidVacation, String> where = builder.where();
            where.eq("pv_year", year);
        return builder.query();
    }

    /**
     * 查询用户在特定年份的总年假数量 lyc
     *@param userId
     * @param year
     * @return 员工出勤对象
     * @throws SQLException
     */
    public double queryTotalPaidVacationDays(String userId, int year) throws
            SQLException {
        QueryBuilder<PaidVacation, String> bdr = dao.queryBuilder();
        Where<PaidVacation, String> where = bdr.where();
        where.eq("user_id", userId);
        where.and().eq("pv_year", year);
        PaidVacation pv = bdr.queryForFirst();
        if (pv != null) {
            return pv.getOfficialDays() + pv.getInnerDays()+pv.getLastYearDays();
        } else {
            return 0;
        }
    }
    
    /**
     * 查询用户在上一年的总法定年假数量 caohuiying
     *@param userId
     * @param year
     * @return 员工出勤对象
     * @throws SQLException
     */
    public double queryTotalLegalVacationDaysOfLastYear(String userId, int year) throws
            SQLException {
        QueryBuilder<PaidVacation, String> bdr = dao.queryBuilder();
        Where<PaidVacation, String> where = bdr.where();
        where.eq("user_id", userId);
        where.and().eq("pv_year", year);
        PaidVacation pv = bdr.queryForFirst();
        if (pv != null) {
            return pv.getOfficialDays() + pv.getLastYearDays();
        } else {
            return 0;
        }
    }
  
    /**
     * 内部年假获取
     * @param userId
     * @param yearStart
     * @param yearEnd
     * @param yearDays
     * @return
     * @throws com.nazca.io.httprpc.HttpRPCException
     * @throws SQLException 
     */
    @SuppressWarnings("empty-statement")
     public double queryPaidInnerDays(String userId,Date yearStart,Date yearEnd,Map<String,String> yearDays) throws
            HttpRPCException, SQLException {
        System.out.println("DAO break queryPaidInnerDays userId------------------------------------------------"+ userId);
          QueryBuilder bdr = dao.queryBuilder();
        Where where = bdr.where();
        where.eq("user_id", userId);
        PaidVacation pv = (PaidVacation) bdr.queryForFirst();
     
            if(StringUtil.isEmpty(userId)){
                throw new HttpRPCException("参数不能为空", ErrorCode.PARAMETER_ERROR);
            }
         if (pv != null) {
            return  pv.getInnerDays()-toDouble(yearDays.get("PAID_INNER"));
        } else {
            return 0;
        }
    }
     /**
     * 法定年假获取
     * @param userId
     * @param yearStart
     * @param yearEnd
     * @param yearDays
     * @return
     * @throws com.nazca.io.httprpc.HttpRPCException
     * @throws SQLException 
     */
     public double queryPaidLegalDays(String userId,Date yearStart,Date yearEnd,Map<String,String> yearDays) throws
            HttpRPCException, SQLException {         
           QueryBuilder bdr = dao.queryBuilder();
        Where where = bdr.where();
        where.eq("user_id", userId);
        PaidVacation pv = (PaidVacation) bdr.queryForFirst();
            if(StringUtil.isEmpty(userId)){
                throw new HttpRPCException("参数不能为空", ErrorCode.PARAMETER_ERROR);
            }        
           if (pv != null) {
            return  pv.getOfficialDays()+pv.getLastYearDays()-toDouble(yearDays.get("PAID_LEGAL"));
        } else {
            return 0;
        }
    }
     
     /**
     * 修改
     * @param paidVacation
     * @return
     * @throws SQLException
     */
    public PaidVacation modifyPaidVacation(PaidVacation paidVacation) throws SQLException {
            paidVacation.setModifyTime(new Date());
            dao.update(paidVacation);
            return paidVacation;
    }
    
    /**
     * 查询已修年假和剩余年假
     * @param userId
     * @param year
     * @param yearStart
     * @param yearEnd
     * @return
     * @throws HttpRPCException 
     */
    public AttendanceStat queryAttendanceStat(String userId,int year ,Date yearStart,Date yearEnd) throws
            HttpRPCException {
         if(StringUtil.isEmpty(userId)){
            throw new HttpRPCException("参数不能为空", ErrorCode.PARAMETER_ERROR);
        }
          AttendanceStat attendanceStat = new AttendanceStat();
        try {
              ConnectionSource src = ConnectionUtil.getConnSrc();
            VacationDetailDAO v = new VacationDetailDAO(src);
            //根据id查询出所有请假类别
            Map<String, String> yearDays = v.queryAttendanceStat(userId,
                    yearStart, yearEnd);
           // 查询当年总年假
            PaidVacationDAO pvDao = new PaidVacationDAO(src);
            double totalPaidVDays = pvDao.queryTotalPaidVacationDays(userId,
                    year);
            //年假
            double PaidVocationThisYear = (toDouble(yearDays.get("PAID_LEGAL"))
                    + toDouble(yearDays.get("PAID_INNER")));
            attendanceStat.setPaidVocationThisYear(PaidVocationThisYear);

             //总年假减去请的年假
            double availablePaidVacationDays = totalPaidVDays
                    - PaidVocationThisYear;
            //剩余年假
            attendanceStat.setAvailablePaidVacationDays(
                    availablePaidVacationDays);

         
        } catch (SQLException ex) {
            throw new HttpRPCException("查询员工出勤信息失败", ErrorCode.DB_ERROR);
        }
        
        return attendanceStat;
    }
    /**
     * 把字符串类型转换成double类型
     * @param daysString
     * @return 
     */
    private double toDouble(String daysString) {
        double result = 0;
            if (StringUtil.isEmpty(daysString)) {
                 daysString ="0";
            }else{
            result = Double.parseDouble(daysString);
            }
        
        return result;
    }
    /**
     * 添加年假数据
     * @param paidVacation
     * @return
     * @throws SQLException 
     */
    public PaidVacation createPaidVacation(PaidVacation paidVacation) throws SQLException {
        try {
            dao.create(paidVacation);
            return paidVacation;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    /**
     * 在每年1月1日 批量插入每个员工的年假数据
     * @param list 
     */
    public void bacthInsertPaidVacation(List<PaidVacation> list){
        try{
                 dao.callBatchTasks(new Callable<Void>() {  
  
                @Override  
                public Void call() throws Exception {  
                    for (PaidVacation p : list) {  
                        dao.create(p);  
                    }  
                    return null;  
                }  
            });  
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
