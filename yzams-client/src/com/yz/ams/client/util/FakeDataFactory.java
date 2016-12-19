/*
 * FakeDataFactory.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-06 12:04:53
 */
package com.yz.ams.client.util;

import com.nazca.sql.PageResult;
import com.yz.ams.consts.AuditStateEnum;
import com.yz.ams.consts.DelayTypeEnum;
import com.yz.ams.consts.SystemParamKey;
import com.yz.ams.model.Attendance;
import com.yz.ams.model.BizTrip;
import com.yz.ams.model.Employee;
import com.yz.ams.model.PaidVacation;
import com.yz.ams.model.Rest;
import com.yz.ams.model.SystemParam;
import com.yz.ams.model.Team;
import com.yz.ams.model.Vacation;
import com.yz.ams.model.wrap.mgmt.AttendanceMgmtStat;
import com.yz.ams.model.wrap.mgmt.PaidVacationWrap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Your Name <Song Haixiang >
 */
public class FakeDataFactory {
    private static boolean isFakeData = false;
    public static PageResult<PaidVacationWrap> PaidVacation;

    /**
     * 是否采用测试数据
     *
     * @return
     */
    public static boolean isFake() {
        return isFakeData;
    }

    /**
     * 测试数据设置
     *
     * @param flag
     */
    public static void setFake(boolean flag) {
        isFakeData = flag;
    }

    public static PageResult<Vacation> queryVacations(String keyword, int curPage, int pageSize) {
        List<Vacation> list = new ArrayList<>();
        Vacation v = new Vacation();
        v.setApplicantId("01");
        v.setApplicantName("李四");
        v.setVacationDate(new Date());
        v.setMorning(true);
        v.setTotalDays(2.0d);
        v.setAuditorName1("领导");
        v.setAuditState(AuditStateEnum.PASS);
        list.add(v);
        
         Vacation v1 = new Vacation();
        v1.setApplicantId("01");
        v1.setApplicantName("王五");
        v1.setVacationDate(new Date());
        v1.setMorning(true);
        v1.setTotalDays(2.0d);
        v1.setAuditorName1("领导");
        v1.setAuditState(AuditStateEnum.PASS);
        list.add(v1);
        
         Vacation v2 = new Vacation();
        v2.setApplicantId("01");
        v2.setApplicantName("张三");
        v2.setVacationDate(new Date());
        v2.setMorning(true);
        v2.setTotalDays(2.0d);
        v2.setAuditorName1("领导");
        v2.setAuditState(AuditStateEnum.PASS);
        list.add(v2);
        PageResult<Vacation> result = new PageResult<Vacation>(100, 1, 20, list);
        return result;
    }

    public static PageResult<BizTrip> queryBizTrip(String keyword, int curPage, int pageSize) {
        List<BizTrip> list = new ArrayList<>();
        BizTrip b = new BizTrip();
        b.setApplicantId("01");
        b.setApplicantName("张三");
        //b.类型TDOO
        b.setLocation("天津");
        b.setStartDate(new Date());
        b.setMorning(true);
        b.setDays(2.0d);
        //b.结束时间 TDOO
        b.setAuditState(AuditStateEnum.PASS);
        list.add(b);
        BizTrip b1 = new BizTrip();
        b1.setApplicantId("01");
        b1.setApplicantName("王五");
        //b.类型TDOO
        b1.setLocation("天津");
        b1.setStartDate(new Date());
        b1.setMorning(true);
        b1.setDays(2.0d);
        //b.结束时间 TDOO
        b1.setAuditState(AuditStateEnum.PASS);
        list.add(b1);
        BizTrip b2 = new BizTrip();
        b2.setApplicantId("01");
        b2.setApplicantName("李四");
        //b.类型TDOO
        b2.setLocation("天津");
        b2.setStartDate(new Date());
        b2.setMorning(true);
        b2.setDays(2.0d);
        //b.结束时间 TDOO
        b2.setAuditState(AuditStateEnum.PASS);
        list.add(b2);
        BizTrip b3 = new BizTrip();
        b3.setApplicantId("01");
        b3.setApplicantName("张三");
        //b.类型TDOO
        b3.setLocation("北京");
        b3.setStartDate(new Date());
        b3.setMorning(true);
        b3.setDays(2.0d);
        //b.结束时间 TDOO
        b3.setAuditState(AuditStateEnum.PASS);
        list.add(b3);
        BizTrip b4 = new BizTrip();
        b4.setApplicantId("01");
        b4.setApplicantName("张三");
        //b.类型TDOO
        b4.setLocation("上海");
        b4.setStartDate(new Date());
        b4.setMorning(true);
        b4.setDays(2.0d);
        //b.结束时间 TDOO
        b4.setAuditState(AuditStateEnum.PASS);
        list.add(b4);
        PageResult<BizTrip> result = new PageResult<BizTrip>(100, 1, 20, list);

        return result;
    }

    public static PageResult<Rest> queryRest(String keyword, int curPage, int pageSize) {

        List<Rest> list = new ArrayList<>();
         Rest r = new Rest();
        r.setUserId("01");
        r.setUserName("王五");
        r.setStartDate(new Date());
        r.setMorning(true);
        r.setDays(2.0d);
        //r.结束时间 TDOO"
        r.setMemo("家中有事");
        r.setAuditState(AuditStateEnum.PASS);
        list.add(r);
        
        Rest r1 = new Rest();
        r1.setUserId("01");
        r1.setUserName("李四");
        r1.setStartDate(new Date());
        r1.setMorning(true);
        r1.setDays(2.0d);
        //r.结束时间 TDOO"
        r1.setMemo("家中有事");
        r1.setAuditState(AuditStateEnum.PASS);
        list.add(r1);
        
        Rest r2 = new Rest();
        r2.setUserId("01");
        r2.setUserName("驱蚊");
        r2.setStartDate(new Date());
        r2.setMorning(true);
        r2.setDays(2.0d);
        //r.结束时间 TDOO"
        r2.setMemo("生病");
        r2.setAuditState(AuditStateEnum.PASS);
        list.add(r2);
        PageResult<Rest> result = new PageResult<Rest>(100, 1, 20, list);

        return result;
    }

    public static PageResult<Attendance> queryAttnedance(String keyword, int curPage, int pageSize) {
        List<Attendance> list = new ArrayList<>();
        Attendance a = new Attendance();
        a.setUserId("01");
        a.setUserName("张三");
        a.setAttendanceDate(new Date());
        a.setDelayType(DelayTypeEnum.LIGHT);
        a.setCreateTime(new Date());
        a.setCreatorName("人事部");
        list.add(a);
        
        Attendance a1 = new Attendance();
        a1.setUserId("01");
        a1.setUserName("李四");
        a1.setAttendanceDate(new Date());
        a1.setDelayType(DelayTypeEnum.LIGHT);
        a1.setCreateTime(new Date());
        a1.setCreatorName("人事部");
        list.add(a1);
     
        PageResult<Attendance> result = new PageResult<Attendance>(100, 1, 20, list);

        return result;
    }

    /**
     * 年假信息的测试数据
     * @param keyword
     * @param curPage
     * @param pageSize
     * @param year
     * @return 
     */
    public static List<PaidVacationWrap> queryPaidVacation(int year) {
        List<PaidVacationWrap> list = new ArrayList<>();
        PaidVacation pc = new PaidVacation();
        PaidVacationWrap pw = new PaidVacationWrap();
        pc.setInnerDays(2.0);
        pc.setModifierId("1");
        pc.setModifierName("zhaohongkun");
        pc.setModifyTime(new Date());
        pc.setOfficialDays(3.0);
        pc.setPvId("1");
        pc.setPvYear(2);
        pc.setUsreId("1");
        PaidVacationWrap p = new PaidVacationWrap();
        p.setPaidVacationinfo(pc);
        list.add(p);
        return list;
    } 
    
    /**
     * 规则设定的测试数据
     * @return 
     */
    public static List<SystemParam> queryRuless() {
        List<SystemParam> list = new ArrayList<>();
        SystemParam p = new SystemParam();
        p.setModifierId("2");
        p.setModifierName("zhao");
        p.setModifyTime(new Date());
        p.setParamKey(SystemParamKey.WORK_START_TIME_AM);
        p.setParamValue("12:00");
        list.add(p);
        p.setModifierId("2");
        p.setModifierName("zhao");
        p.setModifyTime(new Date());
        p.setParamKey(SystemParamKey.WORK_END_TIME_AM);
        p.setParamValue("12:00");
        list.add(p);
        p.setModifierId("2");
        p.setModifierName("zhao");
        p.setModifyTime(new Date());
        p.setParamKey(SystemParamKey.WORK_START_TIME_PM);
        p.setParamValue("12:00");
        list.add(p);
        p.setModifierId("2");
        p.setModifierName("zhao");
        p.setModifyTime(new Date());
        p.setParamKey(SystemParamKey.WORK_END_TIME_PM);
        p.setParamValue("12:00");
        list.add(p);
        p.setModifierId("2");
        p.setModifierName("zhao");
        p.setModifyTime(new Date());
        p.setParamKey(SystemParamKey.LIGHT_DELAY_MINUTES);
        p.setParamValue("12:00");
        list.add(p);
        p.setModifierId("2");
        p.setModifierName("zhao");
        p.setModifyTime(new Date());
        p.setParamKey(SystemParamKey.NORMAL_DELAY_MINUTES);
        p.setParamValue("12:00");
        list.add(p);
        p.setModifierId("2");
        p.setModifierName("zhao");
        p.setModifyTime(new Date());
        p.setParamKey(SystemParamKey.SERIOUS_DELAY_MINUTES);
        p.setParamValue("12:00");
        list.add(p);
        p.setModifierId("2");
        p.setModifierName("zhao");
        p.setModifyTime(new Date());
        p.setParamKey(SystemParamKey.PAID_VACATION_ONE_YEAR);
        p.setParamValue("12:00");
        list.add(p);
        p.setModifierId("2");
        p.setModifierName("zhao");
        p.setModifyTime(new Date());
        p.setParamKey(SystemParamKey.PAID_VACATION_TEN_YEAR);
        p.setParamValue("12:00");
        list.add(p);
        p.setModifierId("2");
        p.setModifierName("zhao");
        p.setModifyTime(new Date());
        p.setParamKey(SystemParamKey.PAID_VACATION_TWENTY_YEAR);
        p.setParamValue("12:00");
        list.add(p);
        p.setModifierId("2");
        p.setModifierName("zhao");
        p.setModifyTime(new Date());
        p.setParamKey(SystemParamKey.PAID_VACATION_INNER);
        p.setParamValue("12:00");
        list.add(p);
        return list; 
    }
    public static List<Team> queryAllTeams() {
        return null;
    }
    /**
     * 考勤统计模块测试数据
     * @param start
     * @param end
     * @return 
     */
    public static List<AttendanceMgmtStat> queryStatisticss(Date start,Date end) {
      List<AttendanceMgmtStat> list = new ArrayList<>();
        AttendanceMgmtStat p = new AttendanceMgmtStat();
        p.setUserName("张三");
        p.setJobNumber("123");
        p.setLegalAttendanceDays(23);
        p.setNormalAttendanceDays(1);
        p.setAnnualLeaveDays(1);
        p.setPersonalDays(1);
        p.setSickDays(1);
        p.setPaidLegaDays(1);
        p.setBusinessDays(1);
        p.setRestDays(1);
        p.setAbsentDays(1);
        p.setLightLateTime(1);
        p.setPunishmentLightLateTime(1);
        p.setLateTime(1);
        p.setSeriousLateTime(1);
        p.setEarlyTime(1);
        list.add(p);
        return list; 
          
    }
    /**
     * 查询所有用户
     * @param keyword
     * @param curPage
     * @param pageSize
     * @return 
     */
    public static List<Employee > queryAllEmployees(String keyword) {
      List<Employee> list = new ArrayList<>();
        Employee p = new Employee();
        p.setUserName("张三");
        p.setEmployeeNumber("201");
        p.setEntryTime(new Date());
        p.setUserId("1");
        list.add(p);
        return list; 
          
    }
}
