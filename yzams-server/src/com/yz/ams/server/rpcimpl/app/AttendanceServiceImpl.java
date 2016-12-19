/*
 * AttendanceServiceImpl.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-26 08:55:08
 */
package com.yz.ams.server.rpcimpl.app;
import com.j256.ormlite.support.ConnectionSource;
import com.nazca.usm.client.connector.USMSRPCService;
import com.nazca.usm.client.connector.USMSRPCServiceException;
import com.nazca.usm.model.USMSUser;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.util.StringUtil;
import com.yz.ams.consts.AttendanceOutTypeEnum;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.model.Attendance;
import com.yz.ams.model.TeamMember;
import com.yz.ams.model.wrap.app.AttendanceStat;
import com.yz.ams.model.wrap.app.MemberAttendanceStat;
import com.yz.ams.model.wrap.app.MemberAttendanceStatDetail;
import com.yz.ams.model.wrap.app.StaffAttendance;
import com.yz.ams.model.wrap.app.TeamAttendanceStat;
import com.yz.ams.model.wrap.app.TeamAttendanceStatDetail;
import com.yz.ams.rpc.app.AttendanceService;
import com.yz.ams.server.consts.ServiceConst;
import com.yz.ams.server.dao.AttendanceDAO;
import com.yz.ams.server.dao.BizTripDAO;
import com.yz.ams.server.dao.HolidayDAO;
import com.yz.ams.server.dao.TeamDAO;
import com.yz.ams.server.dao.PaidVacationDAO;
import com.yz.ams.server.dao.RestDAO;
import com.yz.ams.server.dao.TeamMemberDAO;
import com.yz.ams.server.dao.VacationDAO;
import com.yz.ams.server.dao.VacationDetailDAO;
import com.yz.ams.server.util.ConnectionUtil;
import com.yz.ams.server.util.DateTool;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 考勤记录 Created by luoyongchang on 2016/1/18.
 */
public class AttendanceServiceImpl implements AttendanceService {

    /**
     * lyc 获取所有员工当日的出勤情况
     * @return
     * @returrows com.nazca.io.httprpc.HttpRPCException
     */
    @Override
    public List<StaffAttendance> queryTodayStaffAttendances() throws
            HttpRPCException {
        try {
            ConnectionSource src = ConnectionUtil.getConnSrc();
            AttendanceDAO attdao = new AttendanceDAO(src);
            List<USMSUser> users = USMSRPCService.getInstance(ServiceConst.AMS_MODULE).getAllUsersInPage(0, Integer.MAX_VALUE);
            Date today = DateTool.getTodayDate();
            List<StaffAttendance> staffs = new ArrayList<StaffAttendance>();
            BizTripDAO bizdao =new BizTripDAO(src);
            RestDAO restdao= new RestDAO(src);
            VacationDAO vacationdao= new VacationDAO(src);
            
            for (int i = 0; i < users.size(); i++) {
                USMSUser user = users.get(i);
                Attendance attendance = attdao.queryTodayAttendance(user.getId(), today);
                if(
                        bizdao.queryBizTripByAttendanceId(user,today) &&
                        restdao.queryRestByAttendanceId(user, today) &&
                        vacationdao.queryVacationByAttendanceId(user, today)
                        ){
                
                    StaffAttendance stf = new StaffAttendance();
                    stf.setUser(user);
                    stf.setAttendance(attendance);
                    stf.setWaitType(WaitType(user, today,bizdao,restdao,vacationdao));
                    
                    staffs.add(stf);
                    
                }
                
            }
            return staffs;
        } catch (USMSRPCServiceException ex) {
            throw new HttpRPCException(ex.getMessage(), ex.getErrcode());
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new HttpRPCException("数据查询失败", ErrorCode.DB_ERROR);
        }
    }

    /**
     * lyc 添加修改一条考勤记录信息
     * @param attendance 考勤对象
     * @return
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    @Override
    public Attendance createOrModifyAttendance(Attendance attendance) throws
            HttpRPCException {
        if(attendance == null){
            throw new HttpRPCException("考勤信息不能为空", ErrorCode.PARAMETER_ERROR);
        }
        try {
              ConnectionSource src = ConnectionUtil.getConnSrc();
            AttendanceDAO dao = new AttendanceDAO(src);
            if (attendance.getAttendanceId()== null) {
                attendance.setAttendanceId(UUID.randomUUID().toString()); //UUID
                attendance.setCreateTime(new Date()); //创建时间
                dao.addAttendance(attendance);
                return attendance;
            } else {
                attendance.setModifyTime(new Date()); //修改时间
                dao.updateAttendance(attendance);
                return attendance;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HttpRPCException("添加考勤信息失败", ErrorCode.DB_ERROR);
        }
    }

    /**
     * lyc
     * 查询员工的出勤统计信息
     * @param userId 员工ID
     * @return 员工对象
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    @Override
    public AttendanceStat queryAttendanceStat(String userId) throws
            HttpRPCException {
         if(StringUtil.isEmpty(userId)){
            throw new HttpRPCException("参数不能为空", ErrorCode.PARAMETER_ERROR);
        }
          AttendanceStat attendanceStat = new AttendanceStat();
        try {
              ConnectionSource src = ConnectionUtil.getConnSrc();
            Calendar cal = Calendar.getInstance();
            // 查询当年总年假
            PaidVacationDAO pvDao = new PaidVacationDAO(src);
            double totalPaidVDays = pvDao.queryTotalPaidVacationDays(userId,
                    cal.get(Calendar.YEAR));
            //查询本月请假总天数和分项
            VacationDAO vacationdao = new VacationDAO(src);
            VacationDetailDAO v = new VacationDetailDAO(src);
            Date monthStart = DateTool.getFirstDateOfThisMonth();
            Date monthEnd = DateTool.getLastDateOfThisMonth();
            //根据id查询出所有请假类别
            Map<String, String> days = v.queryAttendanceStat(userId,
                    monthStart, monthEnd);
            //病假
              attendanceStat.setSickVocationThisMonth(toDouble(days.get("SICK")));
            //事假
              attendanceStat.setPersonalVocationThisMonth(toDouble(days.get("PERSONAL")));
               //年假
            double PaidVocationThisMonth = (toDouble(days.get("PAID_LEGAL"))
                    + toDouble(days.get("PAID_INNER")));
            attendanceStat.setPaidVocationThisMonth(PaidVocationThisMonth);
            //其他
            attendanceStat.setOtherVocationThisMonth(toDouble(days.
                    get("WEDDING")) + toDouble(days.get("BIRTH")) + toDouble(
                    days.get("NURSING")) + toDouble(days.get("FUNERAL")));
       
            //总年假减去请的年假
            double availablePaidVacationDays = totalPaidVDays
                    - PaidVocationThisMonth;
            //剩余年假
            attendanceStat.setAvailablePaidVacationDays(
                    availablePaidVacationDays);

            //查询本周考勤
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            c.add(Calendar.DAY_OF_WEEK, (-1) * c.get(Calendar.DAY_OF_WEEK) + 1);
            c.set(Calendar.DATE, c.get(Calendar.DATE)+1);
            Date weekStart = c.getTime();
            c.set(Calendar.DATE, c.get(Calendar.DATE)+6);
            Date weekEnd = c.getTime();

            AttendanceDAO attdao = new AttendanceDAO(src);
            //迟到
            int delayThisWeek = attdao.queryDelayThisWeek(userId, weekStart, weekEnd);
            attendanceStat.setDelayThisWeek(delayThisWeek);
            //旷工
            int absentThisWeek = attdao.queryAbsentThisWeek(userId, weekStart, weekEnd);
            attendanceStat.setAbsentThisWeek(absentThisWeek);
            //早退
            int leaveEarlyThisWeek = attdao.queryEaveEarlyThisWeek(userId, weekStart, weekEnd);
            attendanceStat.setLeaveEarlyThisWeek(leaveEarlyThisWeek);

            //本年请假天数
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_YEAR, 1);//设置为1号,当前日期既为本年的第一天
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date yearStart = cal.getTime();
            cal.add(Calendar.YEAR, 1);
            cal.add(Calendar.SECOND, -1);
            Date yearEnd = cal.getTime();
            //根据id查询出所有请假类别
            Map<String, String> yearDays = v.queryAttendanceStat(userId,
                    yearStart, yearEnd);
            //病假
            attendanceStat.setSickVocationThisYear(
                    toDouble(yearDays.get("SICK")));
            //事假
            attendanceStat.setPersonalVocationThisYear(toDouble(yearDays.get(
                    "PERSONAL")));
            //年假
            double PaidVocationThisYear = (toDouble(yearDays.get("PAID_LEGAL"))
                    + toDouble(yearDays.get("PAID_INNER")));
            attendanceStat.setPaidVocationThisYear(PaidVocationThisYear);
            //其他
            attendanceStat.setOtherVocationThisYear(toDouble(yearDays.get("WEDDING")) + toDouble(yearDays.get("BIRTH")) + toDouble(
                    yearDays.get("NURSING")) + toDouble(yearDays.get("FUNERAL")));


        } catch (SQLException ex) {
            throw new HttpRPCException("查询员工出勤信息失败", ErrorCode.DB_ERROR);
        }
        return attendanceStat;
    }
   private double toDouble(String daysString) {
        double result = 0;
        try {
            if (StringUtil.isEmpty(daysString)) {
                 daysString ="0";
            }else{
            result = Double.parseDouble(daysString);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    
    /**
     * lyc 
     * 查询员工所在团队出勤统计
     * @param userId
     * @return 本组成员的出勤情况
     * @throws HttpRPCException
     */
    @Override
    public MemberAttendanceStat queryLastMonthMyTeamMemberAttendanceStat(
            String userId) throws HttpRPCException {
          if(StringUtil.isEmpty(userId)){
            throw new HttpRPCException("参数不能为空", ErrorCode.PARAMETER_ERROR);
        }
        MemberAttendanceStat members = new MemberAttendanceStat();
        try {
              ConnectionSource src = ConnectionUtil.getConnSrc();
            TeamMemberDAO teamMemberDao = new TeamMemberDAO(src);
            VacationDAO vacationDAO = new VacationDAO(src);
            RestDAO restDAO = new RestDAO(src);
            AttendanceDAO attdao = new AttendanceDAO(src);
            HolidayDAO holidaydao = new HolidayDAO(src);
            TeamMember team = teamMemberDao.queryTeamId(userId,new TeamDAO(src).queryTeamNotTrue());
            String teamId = team.getTeamId();//团队ID
            List<String> ids = teamMemberDao.queryUserId(teamId);
                Date monthStart = DateTool.getLastMonthDateStart();
                Date monthEnd = DateTool.getLastMonthDateEnd();
            long workDays = dateDiff(monthStart, monthEnd);//获得工作日天数

            workDays-=holidaydao.queryMonthHoliday(monthStart, monthEnd);//工作日天数-法定节假日
            workDays+=holidaydao.queryMonthWORKDAY(monthStart, monthEnd);//工作日天数+补班天数
            List<MemberAttendanceStatDetail> memberAttendanceStatDetails = new ArrayList<>();
            for (String id : ids) {
                USMSUser user = USMSRPCService.getInstance(ServiceConst.AMS_MODULE).getUserByIdAndProperties(id);
                double vacationDays = vacationDAO.queryVacationDaysInMouth(id, monthStart, monthEnd); //请假天数和
                double restDays = restDAO.queryRestDaysInMouth(id, monthStart, monthEnd); //调休天数的和
                //迟到
                int delayThisWeek = attdao.queryDelayThisWeek(id, monthStart, monthEnd);
                //旷工次数
                int absent = attdao.queryAbsentThisWeek(id, monthStart, monthEnd);
                //旷工天数
                 double absentNumber = attdao.queryAbsentThisWeekNumbwer(id, monthStart, monthEnd);
                //早退
                int leaveEarlyThisWeek = attdao.queryEaveEarlyThisWeek(id, monthStart, monthEnd);
                MemberAttendanceStatDetail detail = new MemberAttendanceStatDetail(user.getName(), workDays - vacationDays - restDays - absentNumber, vacationDays, delayThisWeek, absent, leaveEarlyThisWeek);
                memberAttendanceStatDetails.add(detail);
            }
            members.setLegalAttendanceDays(workDays);
            members.setMemberStatDetails(memberAttendanceStatDetails);

        } catch (SQLException e) {
            throw new HttpRPCException("查询员工所在团队出勤信息失败", ErrorCode.DB_ERROR);
        } catch (USMSRPCServiceException ex) {
            ex.printStackTrace();
        }
        return members;
    }

    /**
     * lyc 
     * 查询所有团队出勤统计
     * @return 所有团队的出勤情况
     * @throws HttpRPCException
     */
    @Override
    public TeamAttendanceStat queryLastMonthTeamAttendanceStat() throws
            HttpRPCException {
        TeamAttendanceStat teamsstat = new TeamAttendanceStat();
        try {  
            ConnectionSource src = ConnectionUtil.getConnSrc();
            TeamDAO dao = new TeamDAO(src);
            TeamMemberDAO teamMemberDao = new TeamMemberDAO(src);
            AttendanceDAO attendanceDAO = new AttendanceDAO(src);
            VacationDAO vacationDAO = new VacationDAO(src);
            HolidayDAO holidaydao = new HolidayDAO(src);
            RestDAO restDAO = new RestDAO(src);
            Map<String, String> map = teamMemberDao.queryMemberOfTeam(); //团队的id和所在团队的人数
            List<TeamAttendanceStatDetail> teamAttendanceStatDetails = new ArrayList<>();
                Date monthStart = DateTool.getLastMonthDateStart();
                Date monthEnd = DateTool.getLastMonthDateEnd();
            long workDays = dateDiff(monthStart, monthEnd)-holidaydao.queryMonthHoliday(monthStart, monthEnd);//获得工作日天数-法定节假日
            workDays+=holidaydao.queryMonthWORKDAY(monthStart, monthEnd);//获得工作日天数+补班天数
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String teamName = dao.queryTimeNames(entry.getKey()); //获取团队的名称
                String count = entry.getValue(); //获取所在团队的人数
                List<String> teamUserIds = teamMemberDao.queryAllTeamByUsers(entry.getKey()); //根据团队id查询出所在团队的员工id集合
                //迟到
                int delayThisWeek = attendanceDAO.queryAbsentLastMonth(teamUserIds, monthStart, monthEnd);
                //旷工
                int absentThisWeek = attendanceDAO.queryAbsentLastMonth(teamUserIds, monthStart, monthEnd);
                //早退
                int leaveEarlyThisWeek = attendanceDAO.queryEaveEarlyLastMonth(teamUserIds, monthStart, monthEnd);
                //上月团队调休天数的和
                double restDays = RestDaysLastMonth(teamUserIds, monthStart, monthEnd);
                //上月团队旷工天数的和
                double absentDays=attendanceDAO.queryAbsentDaysLastMonth(teamUserIds, monthStart, monthEnd);
                //请假总天数
                double vacationDays = vacationDAO.queryVacationDaysInMouth(teamUserIds, monthStart, monthEnd);
                //平均出勤天数
                double number = (workDays*Integer.parseInt(count)-(vacationDays + absentDays + restDays))/Integer.parseInt(count);
                TeamAttendanceStatDetail detail = new TeamAttendanceStatDetail(teamName, count, number, vacationDays, delayThisWeek, leaveEarlyThisWeek, absentThisWeek);
                teamAttendanceStatDetails.add(detail);
            }
            teamsstat.setLegalAttendanceDays(workDays);
            teamsstat.setTeamStatDetails(teamAttendanceStatDetails);
        } catch (SQLException e) {
            throw new HttpRPCException("查询团队出勤信息失败", ErrorCode.DB_ERROR);
        }
        return teamsstat;
    }

  /**
     * 获取开始日期到终了日期之间的除去周六日的工作天数
     * @param startDate
     * @param endDate
     * @return
     */
    private long dateDiff(Date startDate, Date endDate) {
        GregorianCalendar endGC = new GregorianCalendar();
        long times, days1 = 0l;
        times = endDate.getTime() - startDate.getTime();
        long days = times / (1000 * 24 * 60 * 60);// 间隔天数
        days1 = (days / 7) * 5;// 整周天数
        long days2 = days % 7;// 除去整周，余下天数
        endGC.setTime(endDate);
        int endweekDay = endGC.get(Calendar.DAY_OF_WEEK);// 结束在周几
         if (endweekDay == 1) {// 结束在周日
            days1 += days2 > 1 ? days2 - 1 : 0;
        } else if (endweekDay == 7) {// 结束在周六
            days1 += days2 == 6 ? 5 : days2;
        } else if (days2 == 0) {// 一个工作日
            days1 += 1;
        } else if (endweekDay - days2 == 1) {// 开始在周日
            days1 += days2;
        } else if (endweekDay - days2 <= 0) {// 开始在周六及以上，即包含周六日，需减一天
            days1 += days2 - 1;
        } else {// 其他时候，因为days为间隔时间，需加一天
            days1 += days2 + 1;
        }
        return days1;
    }  

    
    public double RestDaysLastMonth(List<String> userIds,Date start,Date end) throws SQLException{
        RestDAO restDAO = new RestDAO(ConnectionUtil.getConnSrc());
        double days=0;
        for(String id : userIds){
            days+=restDAO.queryRestDaysInMouth(id,start,end);
        }
        return days;
    }
    public AttendanceOutTypeEnum WaitType(USMSUser user,Date today,BizTripDAO bizdao,RestDAO restdao,VacationDAO vacationdao) throws SQLException{
        if(bizdao.IfWaitBizTripByUserId(user, today)){
            return AttendanceOutTypeEnum.BIZ_TRIP;
        }else if(restdao.IfWaitRestByUserId(user, today)){
            return AttendanceOutTypeEnum.REST;
        }else if(vacationdao.IfWaitVacationByUserId(user, today)){
            return AttendanceOutTypeEnum.VACATION;
        }
        return null;
    }
    
      /**
     * 获取上月的第一天
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    private Date getTime(int year) {
       Calendar c = Calendar.getInstance();
        // c.set(Calendar.YEAR, year);
        c.set(Calendar.DAY_OF_YEAR, 1);     
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
    /**
     *  获取上月的最后1天
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    private Date getTime2(int year) {
         Calendar  c = Calendar.getInstance();
        // c.set(Calendar.YEAR, year + 1);
        c.set(Calendar.DAY_OF_YEAR, 1);       
        c.add(Calendar.DAY_OF_YEAR, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }
}
