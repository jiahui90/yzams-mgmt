/*
 * VacationDetailsDao.java
 * VacationDetailDAO.java
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * Created at 2016-03-04 16:59:36
 */
package com.yz.ams.server.dao;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.util.StringUtil;
import com.yz.ams.consts.AuditStateEnum;
import com.yz.ams.consts.VacationTypeEnum;
import com.yz.ams.model.Vacation;
import com.yz.ams.model.VacationDetail;
import com.yz.ams.model.wrap.mgmt.VacationAndVacationDetailWrap;
import com.yz.ams.model.wrap.mgmt.VacationNote;
import com.yz.ams.server.util.ConnectionUtil;
import com.yz.ams.util.DateUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 *
 * @author litao <your.name at your.org>
 */
public class VacationDetailDAO extends AbstractORMDAO<VacationDetail> {
    public VacationDetailDAO(ConnectionSource connSrc,
            Class<VacationDetail> claz) throws SQLException {
        super(connSrc, VacationDetail.class);
    }

    public VacationDetailDAO(ConnectionSource connSrc) throws SQLException {
        super(connSrc, VacationDetail.class);
    }

    /**
     * 创建请假详情 litao
     *
     * @param vacationDetail
     * @throws SQLException
     */
    public void creatVacationDetail(VacationDetail vacationDetail) throws
            SQLException {
        dao.create(vacationDetail);
    }

    /**
     * 根据id获取请假详情 litao
     *
     * @param vacationId
     * @return
     * @throws SQLException
     */
    public List<VacationDetail> queryById(String vacationId) throws SQLException {
        return dao.queryForEq("vacation_id", vacationId);
    }

   /**
     * lyc 
     * 根据ID查询各个类型的天数的和
     * @param userId
     * @param start
     * @param end
     * @return
     * @throws java.sql.SQLException
     */
    @SuppressWarnings("empty-statement")
    public Map<String, String> queryAttendanceStat(String userId, Date start,
            Date end) throws SQLException {
        QueryBuilder qb1 = DaoManager.createDao(connSrc, Vacation.class).
                queryBuilder();
        qb1.selectColumns("vacation_id")
                .where()
                .eq("applicant_id", userId)
                .and()
                .eq("deleted", false)
                .and()
                .eq("audit_state",AuditStateEnum.PASS)
                .and()
                .ge("vacation_date", start)
                .and()
                .le("vacation_date", end);
        Map<String, String> days = new HashMap<>();
        QueryBuilder<VacationDetail, String> qb = dao.queryBuilder();
        Where<VacationDetail, String> where = qb.where();
        
        where.in("vacation_id", qb1);
        qb.selectRaw("vacation_type","sum(vacation_days)") //根据类型查询
                .groupBy("vacation_type"); 
        GenericRawResults results = qb.queryRaw();
        List<String[]> rowList = results.getResults();
        for (String[] row : rowList) {
                days.put(row[0], row[1]);
        }
        return days;
    }
    
     /**
     * lyc 
     * 根据ID查询各个类型的天数的和
     * @param userId
     * @param start
     * @param end
     * @return
     * @throws java.sql.SQLException
     */
    @SuppressWarnings("empty-statement")
    public Map<String, String> queryVacationNotDeny(String userId, Date start,
            Date end) throws SQLException {
        QueryBuilder qb1 = DaoManager.createDao(connSrc, Vacation.class).
                queryBuilder();
          Map<String, String> days = new HashMap<>();
          Where<Vacation, String> where = qb1.where();
        qb1.selectColumns("vacation_id")
                .where()
                .eq("applicant_id", userId)
                .and()
                .eq("deleted", false)
                .and()
                .ge("vacation_date", start)
                .and()
                .le("vacation_date", end);
        
                where.or(where.eq("audit_state",AuditStateEnum.PASS), 
                                   where.eq("audit_state",AuditStateEnum.WAIT_FOR_PM),
                                   where.eq("audit_state",AuditStateEnum.WAIT_FOR_CEO));
      
                QueryBuilder<VacationDetail, String> qb = dao.queryBuilder();
                Where<VacationDetail, String> where1 = qb.where();
                where1.in("vacation_id", qb1);
                
        qb.selectRaw("vacation_type","sum(vacation_days)") //根据类型查询
                .groupBy("vacation_type"); 
        GenericRawResults results = qb.queryRaw();
        List<String[]> rowList = results.getResults();
        for (String[] row : rowList) {
            days.put(row[0], row[1]);
        }
        return days;
    }
    /**
     * 添加请假详情 litao
     *
     * @param vacationId
     * @param detailLis
     * @return
     * @throws HttpRPCException
     * @throws SQLException
     */
    public List<VacationDetail> createVacationDetail(String vacationId,
            List<VacationDetail> detailLis) throws HttpRPCException,
            SQLException {
        for (VacationDetail detail : detailLis) {
            detail.setDetailId(UUID.randomUUID().toString());
            detail.setVacationId(vacationId);
            dao.create(detail);
        }
        return detailLis;
    }

    public List<VacationDetail> modifyVacationDetail(String vacationId,
            List<VacationDetail> detailLis) throws HttpRPCException,
            SQLException {

        TransactionManager.callInTransaction(ConnectionUtil.getConnSrc(),
                new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                QueryBuilder builder = dao.queryBuilder();
                builder.where().eq("vacation_id", vacationId);
                List<VacationDetail> lis = builder.query();
                dao.delete(lis);
                for (VacationDetail detail : detailLis) {
                    detail.setVacationId(vacationId);
                    dao.create(detail);
                }
                return null;
            }
        });
        return detailLis;
    }
    
    public List<VacationDetail> deleteVacationDetail(String vacationId) throws HttpRPCException,
            SQLException {
        
            QueryBuilder builder = dao.queryBuilder();
            builder.where().eq("vacation_id", vacationId);
            List<VacationDetail> lis = builder.query();
            dao.delete(lis);
        return lis;
    }

    public List<VacationDetail> getVacation(String vacationId) throws
            HttpRPCException, SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder.where().eq("vacation_id", vacationId);
        return builder.query();
    }

    /**
     * zhaohongkun 
     * 根据ID查询各个类型的天数的和（电脑端）
     * @param userId
     * @param start
     * @param end
     * @return
     * @throws java.sql.SQLException
     */
    public List<VacationDetail> queryAttendanceStatList(String userId, Date start,
            Date end) throws SQLException {
        QueryBuilder qb1 = DaoManager.createDao(connSrc, Vacation.class).
                queryBuilder();
        qb1.selectColumns("vacation_id")
                .where()
                .eq("applicant_id", userId)
                .and()
                .eq("audit_state",AuditStateEnum.PASS)
                .and()
                .eq("deleted",false);
        QueryBuilder<VacationDetail, String> qb = dao.queryBuilder();
        Where<VacationDetail, String> where = qb.where();
        where.and(where.in("vacation_id", qb1),
                where.or(where.or(where.ge("start_time", start)
                        .and()
                        .le("start_time", end), where.ge("end_time", start)
                        .and()
                        .le("end_time", end)),where.and(where.lt("start_time", start),where.gt("end_time", end))));
        return qb.query();
    }
    
    /**
     * caohuiying
     * vacation，vacation_detail连表查询
     * @param start
     * @param end
     * @return
     * @throws SQLException 
     */
    public List<VacationAndVacationDetailWrap> queryVacationAndDetail(Date start,
            Date end) throws SQLException {
        QueryBuilder qb1 = DaoManager.createDao(connSrc, Vacation.class).
                queryBuilder();
        String startDate = DateUtil.getDateTimeFormat(start);
        String endDate = DateUtil.getDateTimeFormat(end);
        String sql ="select vacation.applicant_id,vacation.morning, vacation_detail.vacation_type,vacation_detail.vacation_days,vacation_detail.start_time,vacation_detail.end_time "
                + "from vacation left join vacation_detail on vacation.vacation_id = vacation_detail.vacation_id where  vacation.audit_state='PASS' and vacation.deleted='false' "
                + "and (vacation_detail.start_time>='" + startDate + "' and vacation_detail.start_time<='" + endDate 
                + "' or vacation_detail.end_time >='" + startDate + "' and vacation_detail.start_time<='" + endDate + "')";
        GenericRawResults<String[]> rawResults = dao.queryRaw(sql);
		//拿出其中的结果
		List<String[]> results = rawResults.getResults();
                //组成封装对象后，存到list
                List<VacationAndVacationDetailWrap> vacationList = new ArrayList<>();
                VacationAndVacationDetailWrap wrap = null;
                for(int k = 0; k < results.size(); k++) {
                    String[] detail = results.get(k);
                    wrap = getWrapObject(detail);
                    vacationList.add(wrap);
		}
       return vacationList;
    }
    
    /**
     * 将字符串转换为枚举假期类型
     * @param str
     * @return 
     */
    private VacationTypeEnum getVacationType(String str){
        VacationTypeEnum type = null;
        switch(str){
                        case "PERSONAL"   : type = VacationTypeEnum.PERSONAL; break;
                        case "SICK"       : type = VacationTypeEnum.SICK ;break;
                        case "PAID_LEGAL" : type = VacationTypeEnum.PAID_LEGAL ;break;
                        case "PAID_INNER" : type = VacationTypeEnum.PAID_INNER ;break;
                        case "WEDDING"    : type = VacationTypeEnum.WEDDING ;break;
                        case "BIRTH"      : type = VacationTypeEnum.BIRTH ;break;
                        case "NURSING"    : type = VacationTypeEnum.NURSING ;break;
                        case "FUNERAL"    : type = VacationTypeEnum.FUNERAL ;break;                        
                        default :break;
                    }
        return type;
    }
    
     /**
      * caohuiying
      * 查询上年度所有员工已经请的法定年假,返回map<empployeeId,days>
      * @param applicantId
      * @return
      * @throws SQLException 
      */
    public Map<String,String> getUsedPaidVacation(Date start, Date end) throws SQLException {
        QueryBuilder qb1 = DaoManager.createDao(connSrc, Vacation.class).
                queryBuilder();
        String sql ="select v.applicant_id, d.vacation_days "
                + "from vacation as v join vacation_detail as d "
                + "on v.vacation_id = d.vacation_id "
                + "where d.vacation_type='PAID_LEGAL' " 
                + "and (d.start_time >= '" + start + "'"
                + " and d.end_time <= '" + end + "' )";
        GenericRawResults<String[]> rawResults = dao.queryRaw(sql);
		//得到字符串查询数组�
		List<String[]> results = rawResults.getResults();
                Map<String,String> vacationDaysOfEmployee = new HashMap<>();
                for(int k = 0; k < results.size(); k++) {
                    String[] detail = results.get(k);
                    if(!vacationDaysOfEmployee.containsKey(detail[0])){
                        vacationDaysOfEmployee.put(detail[0], detail[1]);
                        
                    }else{
                        String days = vacationDaysOfEmployee.get(detail[0]);
                        days = String.valueOf(Integer.valueOf(days) + Integer.valueOf(detail[1])); //同一个员工，累加请假天数
                        vacationDaysOfEmployee.put(detail[0], days);
                    }
		}
       return vacationDaysOfEmployee;
    }
    
    /**
     * 将连表查询的结果（String[]数组）转换为封装对象
     * @param strArr
     * @return 
     */
    private VacationAndVacationDetailWrap getWrapObject(String[] strArr){
        VacationAndVacationDetailWrap wrap = new VacationAndVacationDetailWrap();
        
                    String str0 = strArr[0];
                    if(!StringUtil.isEmpty(str0)){
                        wrap.setAppliantId(strArr[0]);
                    }
                    String str1 = strArr[1];
                    if(!StringUtil.isEmpty(str1)){
                        wrap.setMorning(strArr[1].equals("1"));
                    }
                    String str2 = strArr[2];
                    if(!StringUtil.isEmpty(str2)){
                        wrap.setVacationType(getVacationType(str2));
                    }
                    String str3 = strArr[3];
                    if(!StringUtil.isEmpty(str3)){
                        String daysStr = strArr[3];
                        wrap.setDays(Float.parseFloat(daysStr));
                    }                    
                    String str4 = strArr[4];
                    if(!StringUtil.isEmpty(str4)){
                        wrap.setStartTime(DateUtil.str2Date(strArr[4].substring(0, 19),"yyyy-MM-dd HH:mm:ss"));
                    }
                    String str5 = strArr[5];
                    if(!StringUtil.isEmpty(str5)){
                        wrap.setEndTime(DateUtil.str2Date(strArr[5].substring(0, 19),"yyyy-MM-dd HH:mm:ss"));
                    }
            return wrap;
    }
    
     /**
      * caohuiying
      * vacation连表acation_detail,查询请假单
      * @param applicantId
      * @return
      * @throws SQLException 
      */
    public List<VacationNote> queryHolidayNotes(String vacationId) throws SQLException {
        QueryBuilder qb1 = DaoManager.createDao(connSrc, Vacation.class).
                queryBuilder();
        String sql ="select vacation.applicant_name,vacation.memo, vacation.create_time,vacation_detail.vacation_type,vacation_detail.vacation_days,vacation_detail.start_time,vacation_detail.end_time,vacation.auditor_name1,vacation.auditor_name2 "
                + "from vacation left join vacation_detail on vacation.vacation_id = vacation_detail.vacation_id "
                + "where vacation.audit_state='PASS' and vacation.deleted='false' and vacation.vacation_id in(" + vacationId + ")" ;
        GenericRawResults<String[]> rawResults = dao.queryRaw(sql);
		//得到字符串查询数组�
		List<String[]> results = rawResults.getResults();
                //请假单list
                List<VacationNote> noteList = new ArrayList<>();
                VacationNote note = null;
                for(int k = 0; k < results.size(); k++) {
                    String[] detail = results.get(k);
                    note = getNoteObject(detail);
                    noteList.add(note);
		}
       return noteList;
    }
    
    /**
     * 转换为VacationNote对象
     * @param strArr
     * @return 
     */
    private VacationNote getNoteObject(String[] strArr){
        VacationNote wrap = new VacationNote();
        
                    String str0 = strArr[0];
                    if(!StringUtil.isEmpty(str0)){
                        wrap.setAppliantName(strArr[0]);
                    }
                    String str1 = strArr[1];
                    if(!StringUtil.isEmpty(str1)){
                        String str = strArr[1];
                        while(str.length() < 73){
                            str = str + " ";
                        }
                        wrap.setMemo(str);
                    }
                    String str2 = strArr[2];
                    if(!StringUtil.isEmpty(str2)){
                        wrap.setCreateTime(strArr[2].substring(0, 10));
                    }
                    String str3 = strArr[3];
                    if(!StringUtil.isEmpty(str3)){
                        wrap.setVacationType( "  " + getVacationTypeStr(strArr[3]) + "  ");
                    }                    
                    String str4 = strArr[4];
                    if(!StringUtil.isEmpty(str4)){
                        
                        wrap.setVacationDays(Double.parseDouble(strArr[4]));
                    }
                    String str5 = strArr[5];
                    if(!StringUtil.isEmpty(str5)){
                        wrap.setStartTime(strArr[5].substring(0, 16));
                    }
                    String str6 = strArr[6];
                    if(!StringUtil.isEmpty(str6)){
                        wrap.setEndTime(strArr[6].substring(0, 16));
                    }
                    String str7 = strArr[7];
                    String str8 = strArr[8];
                    if(!StringUtil.isEmpty(str7)){
                        wrap.setAuditorName1("  " + strArr[7]+ "  ");
                    }else{
                        wrap.setAuditorName1("  " + strArr[8]+ "  ");
                    }
                    
                    if(!StringUtil.isEmpty(str7)){
                        if(!StringUtil.isEmpty(str8)){
                            wrap.setAuditorName2(str7 + " , " + strArr[8]);
                        }else{
                            wrap.setAuditorName2(str7);
                        }
                    }else{
                        wrap.setAuditorName2(str8);
                    }
                    
            return wrap;
    }
    
    /**
     * 字符串转为假期枚举类型
     * @param t
     * @return 
     */
    private String getVacationTypeStr(String t){
        String type = "";
        switch(t){
                        case "PERSONAL"   : type = VacationTypeEnum.PERSONAL.toString(); break;
                        case "SICK"       : type = VacationTypeEnum.SICK.toString() ;break;
                        case "PAID_LEGAL" : type = VacationTypeEnum.PAID_LEGAL.toString() ;break;
                        case "PAID_INNER" : type = VacationTypeEnum.PAID_INNER.toString() ;break;
                        case "WEDDING"    : type = VacationTypeEnum.WEDDING.toString();break;
                        case "BIRTH"      : type = VacationTypeEnum.BIRTH.toString() ;break;
                        case "NURSING"    : type = VacationTypeEnum.NURSING.toString() ;break;
                        case "FUNERAL"    : type = VacationTypeEnum.FUNERAL.toString() ;break;                        
                        default :break;
                    }
        return type;
    }
}
