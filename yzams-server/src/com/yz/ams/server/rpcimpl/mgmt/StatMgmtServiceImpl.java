/*
 * StatisticsMgmtServiceImpl.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 17:32:28
 */
package com.yz.ams.server.rpcimpl.mgmt;

import com.j256.ormlite.support.ConnectionSource;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.HttpRPCInjection;
import com.nazca.sql.PageResult;
import com.nazca.usm.common.SessionConst;
import com.nazca.util.StringUtil;
import com.yz.ams.consts.DelayTypeEnum;
import static com.yz.ams.consts.DelayTypeEnum.LIGHT;
import static com.yz.ams.consts.DelayTypeEnum.NORMAL;
import static com.yz.ams.consts.DelayTypeEnum.SERIOUS;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.consts.HolidayTypeEnum;
import com.yz.ams.consts.Permissions;
import com.yz.ams.consts.VacationTypeEnum;
import static com.yz.ams.consts.VacationTypeEnum.BIRTH;
import static com.yz.ams.consts.VacationTypeEnum.FUNERAL;
import static com.yz.ams.consts.VacationTypeEnum.NURSING;
import static com.yz.ams.consts.VacationTypeEnum.PAID_INNER;
import static com.yz.ams.consts.VacationTypeEnum.PAID_LEGAL;
import static com.yz.ams.consts.VacationTypeEnum.PERSONAL;
import static com.yz.ams.consts.VacationTypeEnum.SICK;
import static com.yz.ams.consts.VacationTypeEnum.WEDDING;
import com.yz.ams.model.Attendance;
import com.yz.ams.model.BizTrip;
import com.yz.ams.model.Employee;
import com.yz.ams.model.Holiday;
import com.yz.ams.model.DateMode;
import com.yz.ams.model.Rest;
import com.yz.ams.model.Team;
import com.yz.ams.model.TeamMember;
import com.yz.ams.model.Vacation;
import com.yz.ams.model.VacationDetail;
import com.yz.ams.model.wrap.mgmt.AttendanceMgmtStat;
import com.yz.ams.model.wrap.mgmt.DailyAttendance;
import com.yz.ams.model.wrap.mgmt.MorningNoon;
import com.yz.ams.model.wrap.mgmt.ReportAttendancesModel;
import com.yz.ams.model.wrap.mgmt.StaffAttendancesWrap;
import com.yz.ams.model.wrap.mgmt.StatItem;
import com.yz.ams.model.wrap.mgmt.VacationAndVacationDetailWrap;
import com.yz.ams.rpc.mgmt.StatMgmtService;
import com.yz.ams.server.dao.AttendanceDAO;
import com.yz.ams.server.dao.BizTripDAO;
import com.yz.ams.server.dao.EmployeeDAO;
import com.yz.ams.server.dao.HolidayDAO;
import com.yz.ams.server.dao.RestDAO;
import com.yz.ams.server.dao.TeamDAO;
import com.yz.ams.server.dao.TeamMemberDAO;
import com.yz.ams.server.dao.VacationDAO;
import com.yz.ams.server.dao.VacationDetailDAO;
import com.yz.ams.server.util.ConnectionUtil;
import com.yz.ams.server.util.USMSTool;
import com.yz.ams.util.DateUtil;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 考勤统计
 *
 * @author Your Name <Song Haixiang >
 */
public class StatMgmtServiceImpl implements StatMgmtService {
    @HttpRPCInjection
    private HttpSession session; 
    private static final Log log = LogFactory.getLog(StatMgmtServiceImpl.class);
    private final ConnectionSource connSrc = ConnectionUtil.getConnSrc();

    @Override
    public PageResult<StaffAttendancesWrap> queryStaffAttendanceDetails(
            String week, String month, String keyword, int curPage, int pageSize)
            throws HttpRPCException {
        return null;
        //TODO
    }

    @Override
    public List<ReportAttendancesModel> queryStaffAttendanceReport(Date start, Date end) throws HttpRPCException {
        return null;
        //TODO
    }

    /**
     * 把出勤信息放到报表中
     *
     * @param map
     * @param attendLis
     * @param src
     */
    public void putAttendanceToReport(Map<String, Map<Date, MorningNoon>> map, List<Attendance> attendLis, ConnectionSource src) {
        for (Attendance attendance : attendLis) {
            String userId = attendance.getUserId();
            Map<Date, MorningNoon> personalAttendance = map.get(userId);
            for (String uId : map.keySet()) {
                //查询出user
                if (uId.equals(userId)) {
                    Date date = attendance.getAttendanceDate();
                    date = DateUtil.getZeroTimeOfDay(date); //获取00:00:00，否则如果从下午开始，这天就不统计了
                    Date now = new Date();
                    if(date.getTime() >= now.getTime()) break; //如果大于当前时间，不统计 
                    //如果包含日期的key,则放入该日期的key-value;
                    if (!personalAttendance.containsKey(date)) {
                        continue;
                    } else {
                        MorningNoon morningNoon = personalAttendance.get(date);
                        DelayTypeEnum type = attendance.getDelayType();
                        //上午的出勤
                        if (type != null) {
                            switch (type) {
                                case LIGHT:
                                    morningNoon.setMorning("轻");
                                    break;
                                case NORMAL:
                                    morningNoon.setMorning("迟");
                                    break;
                                case SERIOUS:
                                    morningNoon.setMorning("严");
                                    break;
                                default:
                                    break;
                            }
                        }
                        //下午的出勤
                        if (attendance.isLeaveEarly()) {
                            morningNoon.setNoon("早");
                        }
                        //加入某一天的出勤
                        personalAttendance.put(date, morningNoon);
                    }
                    //加入一个user的出勤
                    map.put(userId, personalAttendance);
                }
            }
        }
    }

    /**
     * 把出差信息放到报表中
     *
     * @param maps
     * @param tripLis
     * @param src
     * @throws HttpRPCException
     */
    private void putBizTripToReport(Map<String, Map<Date, MorningNoon>> maps, List<BizTrip> tripLis, ConnectionSource src) throws HttpRPCException {
        //装出勤信息的小map(某人一个时间段内的出勤)
        Map<Date, MorningNoon> tripStaffAttend = null;
        for (BizTrip trip : tripLis) {
            String applicantId = trip.getApplicantId();
            Date startDate = trip.getStartDate();
            Date endDate = trip.getEndDate();
            boolean isMorning = trip.isMorning();
            String staffIds = trip.getStaffIds();
            //只有一个申请者
            if (StringUtil.isEmpty(staffIds) || (!StringUtil.isEmpty(staffIds) && !staffIds.contains(","))) {
                for (String userId : maps.keySet()) {
                    if (userId.equals(applicantId)) {
                        tripStaffAttend = maps.get(userId);
                        personalDailyAttendance(tripStaffAttend, startDate, endDate, isMorning, src);
                        maps.put(applicantId, tripStaffAttend);
                    }
                }

                //staffIds中包含多个用户id
            } else {
                String[] userIDs = staffIds.split(",");
                for (int i = 0; i < userIDs.length; i++) {
                    String uid = userIDs[i];
                    for (String userId : maps.keySet()) {
                        //如果maps包含这个用户
                        if (userId.equals(uid)) {
                            tripStaffAttend = maps.get(uid);
                            //将出差信息放到这个userid中
                            personalDailyAttendance(tripStaffAttend, startDate, endDate, isMorning, src);
                            maps.put(applicantId, tripStaffAttend);
                        }
                    }
                }
            }
        }
    }

    /**
     * 将时间段内的出差放到list中
     *
     * @param tripStaffAttend
     * @param startDate
     * @param endDate
     * @param isMorning
     * @param src
     * @throws HttpRPCException
     */
    private void personalDailyAttendance(Map<Date, MorningNoon> tripStaffAttend, Date startDate, Date endDate, Boolean isMorning, ConnectionSource conn) throws HttpRPCException {
        try {
            for (Date date = startDate; date.getTime() <= endDate.getTime(); date = DateUtil.getLastHourStartTime(date, 24)) {
                if(date.getTime() > (new Date().getTime())){  //如果大于当前时间就不统计了
                    break;
                }
                date = DateUtil.getZeroTimeOfDay(date);
                //如果不在这个时间段内,continue
                if (!tripStaffAttend.containsKey(date)) {
                    continue;
                } else {
                    HolidayDAO holidayDao = new HolidayDAO(conn);
                    Holiday holiday = null;
                    holiday = holidayDao.queryHoliday(date);
                    if (DateUtil.isWorkingDays(date)) { //如果是工作日
                        if (holiday != null && holiday.getHolidayType().equals(HolidayTypeEnum.HOLIDAY)) {  //工作日是假日
                            continue;
                        }
                        MorningNoon morningNoon = tripStaffAttend.get(date);
                        morningNoon.setMorning("差");
                        morningNoon.setNoon("差");
                        //如果循环时间等于请假开始时间，且morning为false，则这天的上午为空
                        if (date.getTime() == startDate.getTime()) {
                            if (!isMorning) {
                                morningNoon.setMorning("");
                            }
                        }
                        //如果循环时间等于请假结束时间，且为12点，则这天的下午为空
                        if (DateUtil.getMiddleTimeOfDay(date).getTime() == endDate.getTime()) {
                            morningNoon.setNoon("");
                        }
                        tripStaffAttend.put(date, morningNoon);
                    } else if (holiday != null && holiday.getHolidayType().equals(HolidayTypeEnum.WORKDAY)) {  //周六日是工作日
                        MorningNoon morningNoon = tripStaffAttend.get(date);
                        morningNoon.setMorning("差");
                        morningNoon.setNoon("差");
                        //如果循环时间等于请假开始时间，且morning为false，则这天的上午为空
                        if (date.getTime() == startDate.getTime()) {
                            if (!isMorning) {
                                morningNoon.setMorning("");
                            }
                            //如果循环时间等于请假结束时间，且为12点，则这天的下午为空
                            if (DateUtil.getMiddleTimeOfDay(date).getTime() == endDate.getTime()) {
                                morningNoon.setNoon("");
                            }
                            tripStaffAttend.put(date, morningNoon);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            log.error("add biztrip to report failed", ex);
            throw new HttpRPCException("add biztrip to report failed", ErrorCode.DB_ERROR);
        }
    }

    /**
     * 将时间段内的调休放到list中
     *
     * @param restStaffAttend
     * @param startDate
     * @param endDate
     * @param isMorning
     * @param src
     * @throws HttpRPCException
     */
    private void personalRestOfTimes(Map<Date, MorningNoon> restStaffAttend, Date startDate, Date endDate, Boolean isMorning, ConnectionSource src) throws HttpRPCException {
        try {
            for (Date date = startDate; date.getTime() <= endDate.getTime(); date = DateUtil.addDay2Date(date, 1)) {
                if(date.getTime() > (new Date().getTime())){  //如果大于当前时间就不统计了
                    break;
                }
                date = DateUtil.getZeroTimeOfDay(date); //获取00:00:00，否则如果从下午开始，这天就不统计了
                //如果不在这个时间段内,continue
                if (!restStaffAttend.containsKey(date)) {
                    continue;
                } else {
                    HolidayDAO holidayDao = new HolidayDAO(src);
                    Holiday holiday = null;
                    holiday = holidayDao.queryHoliday(date);
                    if (DateUtil.isWorkingDays(date)) { //如果是工作日
                        if (holiday != null && holiday.getHolidayType().equals(HolidayTypeEnum.HOLIDAY)) {  //工作日是假日
                            continue;
                        }
                        MorningNoon morningNoon = restStaffAttend.get(date);
                        morningNoon.setMorning("休");
                        morningNoon.setNoon("休");
                        //如果循环时间等于请假开始时间，且morning为false，则这天的上午为空
                        if (date.getTime() == startDate.getTime()) {
                            if (!isMorning) {
                                morningNoon.setMorning("");
                            }
                        }
                        //如果循环时间等于请假结束时间，且为12点，则这天的下午为空
                        if (DateUtil.getMiddleTimeOfDay(date).getTime() == endDate.getTime()) {
                            morningNoon.setNoon("");
                        }
                        restStaffAttend.put(date, morningNoon);
                    } else if (holiday != null && holiday.getHolidayType().equals(HolidayTypeEnum.WORKDAY)) {  //周六日是工作日
                        MorningNoon morningNoon = restStaffAttend.get(date);
                        morningNoon.setMorning("休");
                        morningNoon.setNoon("休");
                        //如果循环时间等于请假开始时间，且morning为false，则这天的上午为空
                        if (date == startDate) {
                            if (!isMorning) {
                                morningNoon.setMorning("");
                            }
                        }
                        //如果循环时间等于请假结束时间，且为12点，则这天的下午为空
                        if (DateUtil.getMiddleTimeOfDay(date).getTime() == endDate.getTime()) {
                            morningNoon.setNoon("");
                        }
                        restStaffAttend.put(date, morningNoon);
                    }
                }
            }
        } catch (SQLException ex) {
            log.error("add biztrip to report failed", ex);
            throw new HttpRPCException("add biztrip to report failed", ErrorCode.DB_ERROR);
        }
    }

    /**
     * 把调休信息放到报表中
     *
     * @param maps
     * @param restLis
     * @param src
     * @throws HttpRPCException
     */
    private void putRestToReport(Map<String, Map<Date, MorningNoon>> maps, List<Rest> restLis, ConnectionSource src) throws HttpRPCException {
        Map<Date, MorningNoon> dailyRestList = null;
        for (Rest rest : restLis) {
            String applicantId = rest.getUserId();
            Date startDate = rest.getStartDate();
            Date endDate = rest.getEndDate();
            boolean isMorning = rest.isMorning();
            String staffIds = rest.getStaffIds();
            //只有一个申请者
            if (StringUtil.isEmpty(staffIds) || (!StringUtil.isEmpty(staffIds) && !staffIds.contains(","))) {
                for (String userId : maps.keySet()) {
                    if (userId.equals(applicantId)) {
                        dailyRestList = maps.get(userId);
                        personalRestOfTimes(dailyRestList, startDate, endDate, isMorning, src);
                        maps.put(applicantId, dailyRestList);
                    }
                }

                //staffIds中包含多个用户id
            } else {
                String[] userIDs = staffIds.split(",");
                for (int i = 0; i < userIDs.length; i++) {
                    String uid = userIDs[i];
                    for (String userId : maps.keySet()) {
                        //如果maps包含这个用户
                        if (userId.equals(uid)) {
                            dailyRestList = maps.get(uid);
                            //将调休信息放到这个userid中
                            personalRestOfTimes(dailyRestList, startDate, endDate, isMorning, src);
                            maps.put(applicantId, dailyRestList);
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<AttendanceMgmtStat> queryAttendanceStat(Date start, Date end) throws HttpRPCException {

        //处理当前年，当前月，当前周时取系统时间，如果系统时间大于中午十二点则小于23：59：59秒，则取中午十二点
        //如果小于中午十二点则取00:00:00
        end = getEndTime(end);

        //查询emplyee表，将结果放入userMap和statMap
        Map<Integer, Map> map = getuserMapAndstatMap();

        //获得用户usermap
        Map<String, Employee> userMap = (Map<String, Employee>) map.get(0);//key: userId

        //获得statmap
        Map<String, StatItem> statMap = (Map<String, StatItem>) map.get(1);//key: userId

        //按时间查询attendance表，将满足条件所有内容查出放入statMap
        statMap = queryAttendence(statMap, start, end);

        //按时间查询detail表，将满足条件所有内容查出放入statMap
        statMap = queryVacationDetail(statMap, userMap, start, end);

        //按时间查询biz表，将满足条件所有内容查出放入statMap
        statMap = queryBizTrip(statMap, userMap, start, end);

        //按时间查询rest表，将满足条件所有内容查出放入statMap
        statMap = queryRest(statMap, start, end);

        //遍历statMap，根据key到userMap拿到Employee，与statItem组合封装AttendanceMgmtStat，完成到result的转换
        List<AttendanceMgmtStat> result = getResult(statMap, userMap);

        return result;
    }

    /**
     * 获得早退，旷工，轻微迟到，正常迟到，严重迟到的数据
     *
     * @param daysString
     * @return
     */
    private Map<String, StatItem> queryAttendence(Map<String, StatItem> statMap, Date start, Date end) throws HttpRPCException {
        AttendanceDAO attendanceDao = null;
        //按时间查询attendance表，将满足条件所有内容查出放入statMap
        //把时间区间截成周，用每周的开始时间和终了时间来查询返回一个map
        Map<Date, Date> map = getStartDateAndEndDate(start, end);
        try {
            attendanceDao = new AttendanceDAO(connSrc);

            for (Date key : map.keySet()) {

                Date value = map.get(key);

                Date weekStart = key;
                Date weekEnd = value;
                int lightLateCont = 0;
                List<Attendance> attList = attendanceDao.queryAttendanceList(weekStart, weekEnd);
                
                for (Attendance att : attList) {
                    String userId = att.getUserId();
                    DelayTypeEnum delayType = att.getDelayType();
                    double absentDays = att.getAbsentDays();
                    boolean leaveEarly = att.isLeaveEarly();
                    StatItem item = statMap.get(userId);
                    if (item != null) {
                        if (leaveEarly) {
                            item.setEarlyTime(item.getEarlyTime() + 1);
                        }
                        item.setAbsentDays(item.getAbsentDays() + absentDays);
                        if (delayType != null) {
                            switch (delayType) {
                                case LIGHT:
                                    lightLateCont++;
                                    item.setLightLateTime(item.getLightLateTime()+1);
                                     //惩罚迟到次数
                                    if (lightLateCont == 3) {
                                        item.setPunishmentLightLateTime(item.getPunishmentLightLateTime() + 1);
                                    }
                                    break;
                                case NORMAL:
                                    item.setLateTime(item.getLateTime() + 1);
                                    break;
                                case SERIOUS:
                                    item.setSeriousLateTime(item.getSeriousLateTime() + 1);
                            }
                        }
                    }
                     statMap.put(userId, item);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("query attnedance stat failed", ex);
            throw new HttpRPCException("query attnedance stat failed", ErrorCode.DB_ERROR);
        }
        return statMap;
    }

    /**
     * 按时间查询detail表，将满足条件所有内容查出放入statMap
     * 得到法定工作日数，出勤日数，各种请假的天数
     * @param daysString
     * @return
     */
    private Map<String, StatItem> queryVacationDetail(Map<String, StatItem> statMap, Map<String, Employee> userMap, Date start, Date end) throws HttpRPCException {
        VacationDetailDAO vacationDetailDao = null;
        try {
            vacationDetailDao = new VacationDetailDAO(connSrc);
            //按时间查询detail表，将满足条件所有内容查出放入statMap
            for (String key : userMap.keySet()) {
                //按时间查询detail表，将满足条件所有内容查出放入statMap
                String userId = key;

                //法定年假天数，内部年假天数
                double paidLeagalDay = 0, paidInnerDay = 0;

                //婚假天数，产假天数，护理假天数，吊唁假天数
                double weddingDay = 0, birthDay = 0, nursingDay = 0, funeralDay = 0;

                //病假天数，事假天数
                double sickDays = 0, personalDays = 0;

                StatItem item = statMap.get(userId);
                //查询各种假
                List<VacationDetail> vacationDetailList = vacationDetailDao.queryAttendanceStatList(key, start, end);

                for (VacationDetail vacationDetail : vacationDetailList) {
                    double days = calculateDays(start, end, vacationDetail.getStartDate(), vacationDetail.getEndDate(), vacationDetail.getVacationDays());
                    VacationTypeEnum vacationType = vacationDetail.getVacationType();
                    if (vacationType != null) {
                        switch (vacationType) {
                            case PERSONAL:
                                personalDays += days;

                                break;
                            case SICK:
                                sickDays += days;

                                break;
                            case PAID_LEGAL:
                                paidLeagalDay += days;
                                break;
                            case PAID_INNER:
                                paidInnerDay += days;

                                break;
                            case WEDDING:
                                weddingDay += days;

                                break;
                            case BIRTH:
                                birthDay += days;

                                break;
                            case NURSING:
                                nursingDay += days;

                                break;
                            case FUNERAL:
                                funeralDay += days;

                                break;
                        }
                    }
                }
                //年假
                double annualLeaveDays = paidLeagalDay + paidInnerDay;

                //其它假
                double otherDays = weddingDay + birthDay + nursingDay + funeralDay;

                //获得法定工作日数
                long workDays = getLegalDays(start, end);

                double absentDays = 0;
                double restDays = 0;

                if (item != null) {
                    absentDays = item.getAbsentDays();
                    restDays = item.getRestDays();
                }
                //获得正常出勤日
                double normalAttendanceDays = workDays - (annualLeaveDays + personalDays + sickDays + otherDays + absentDays + restDays);
                if (item != null) {
                    item.setLegalAttendanceDays(workDays);
                    item.setAnnualLeaveDays(item.getAnnualLeaveDays() + annualLeaveDays);
                    item.setPersonalDays(item.getPersonalDays() + personalDays);
                    item.setSickDays(item.getSickDays() + sickDays);
                    item.setOtherDays(item.getOtherDays() + otherDays);
                    item.setNormalAttendanceDays(normalAttendanceDays);
                }
                statMap.put(userId, item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("query Vacation Detail failed", ex);
            throw new HttpRPCException("query Vacation Detail failed", ErrorCode.DB_ERROR);
        }
        return statMap;
    }

    /**
     * 按按时间查询biz表，将满足条件所有内容查出放入statMap
     * 得到出差天数
     * @param daysString
     * @return
     */
    private Map<String, StatItem> queryBizTrip(Map<String, StatItem> statMap, Map<String, Employee> userMap, Date start, Date end) throws HttpRPCException {
        BizTripDAO bizTripDao = null;
        try {
            bizTripDao = new BizTripDAO(connSrc);
            List<BizTrip> bizList = bizTripDao.queryBizTripList(start, end);
            for (BizTrip bizTrip : bizList) {
                String[] userIds = bizTrip.getStaffIds().split(",");
                for (int i = 0; i < userIds.length; i++) {
                    String userIdNode = userIds[i];
                    StatItem item = statMap.get(userIdNode);
                    double businessDays = calculateDays(start, end, bizTrip.getStartDate(), bizTrip.getEndDate(), bizTrip.getDays());
                    if (item != null) {
                        item.setBusinessDays(item.getBusinessDays() + businessDays);
                    }
                    statMap.put(userIdNode, item);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("query BizTrip failed", ex);
            throw new HttpRPCException("query BizTrip failed", ErrorCode.DB_ERROR);
        }
        return statMap;
    }

    /**
     * 按时间查询rest表，将满足条件所有内容查出放入statMap
     * 得到调休天数
     * @param daysString
     * @return
     */
    private Map<String, StatItem> queryRest(Map<String, StatItem> statMap, Date start, Date end) throws HttpRPCException {
        RestDAO restDAO = null;
        try {
            restDAO = new RestDAO(connSrc);
            List<Rest> resList = restDAO.queryRestList(start, end);
            for (Rest res : resList) {
                if (!StringUtil.isEmpty(res.getStaffIds())) {
                    String[] userIds = res.getStaffIds().split(",");
                    for (int i = 0; i < userIds.length; i++) {
                        String userIdNode = userIds[i];
                        StatItem item = statMap.get(userIdNode);
                        double restDays = 0;
                        restDays = calculateDays(start, end, res.getStartDate(), res.getEndDate(), res.getDays());
                        item.setRestDays(item.getRestDays() + restDays);
                        statMap.put(userIdNode, item);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("query Rest failed", ex);
            throw new HttpRPCException("query Rest failed", ErrorCode.DB_ERROR);
        }
        return statMap;
    }

    /**
     * 遍历statMap，根据key到userMap拿到Employee，与statItem组合封装AttendanceMgmtStat，完成到result的转换
     *
     * @param daysString
     * @return
     */
    private List<AttendanceMgmtStat> getResult(Map<String, StatItem> statMap, Map<String, Employee> userMap) throws HttpRPCException {
        List<AttendanceMgmtStat> result = new ArrayList<>();
        for (String key : statMap.keySet()) {
            AttendanceMgmtStat attendanceMgmtStat = new AttendanceMgmtStat();
            if (userMap.get(key) != null) {
                attendanceMgmtStat.setUserName(userMap.get(key).getUserName());
                attendanceMgmtStat.setJobNumber(userMap.get(key).getEmployeeNumber());
            }
            if (statMap.get(key) != null) {
                attendanceMgmtStat.setLegalAttendanceDays(statMap.get(key).getLegalAttendanceDays());
                attendanceMgmtStat.setNormalAttendanceDays(statMap.get(key).getNormalAttendanceDays());
                attendanceMgmtStat.setAnnualLeaveDays(statMap.get(key).getAnnualLeaveDays());
                attendanceMgmtStat.setPersonalDays(statMap.get(key).getPersonalDays());
                attendanceMgmtStat.setSickDays(statMap.get(key).getSickDays());
                attendanceMgmtStat.setPaidLegaDays(statMap.get(key).getOtherDays());
                attendanceMgmtStat.setBusinessDays(statMap.get(key).getBusinessDays());
                attendanceMgmtStat.setRestDays(statMap.get(key).getRestDays());
                attendanceMgmtStat.setAbsentDays(statMap.get(key).getAbsentDays());
                attendanceMgmtStat.setLightLateTime(statMap.get(key).getLightLateTime());
                attendanceMgmtStat.setEarlyTime(statMap.get(key).getEarlyTime());
                //惩罚轻微迟到次数
                attendanceMgmtStat.setPunishmentLightLateTime(statMap.get(key).getPunishmentLightLateTime());
                attendanceMgmtStat.setLateTime(statMap.get(key).getLateTime());
                attendanceMgmtStat.setSeriousLateTime(statMap.get(key).getSeriousLateTime());
            }
            result.add(attendanceMgmtStat);
        }
        return result;
    }

    /**
     * 对传入的终了时间进行处理
     *
     * @param daysString
     * @return
     */
    private Date getEndTime(Date end) throws HttpRPCException {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date sysDateTime = new Date();
        String sysDate = dateFormater.format(sysDateTime);
        String endTemp = dateFormater.format(end);
        if (sysDate.equals(endTemp)) {
            //获得中午12:00:00
            Date noonDateTime = getTime(sysDateTime, 12, 0, 0);

            //获得晚上日期时间23:59:59
            Date nightDateTime = getTime(sysDateTime, 23, 59, 59);

            //获得零点
            Date morningDateTime = getTime(sysDateTime, 0, 0, 0);

            //如果系统时间大于中午十二点则小于23：59：59秒，则取中午十二点
            if (sysDateTime.compareTo(noonDateTime) < 0 && sysDateTime.compareTo(morningDateTime) >= 0) {
                end = morningDateTime;
            } else if (sysDateTime.compareTo(noonDateTime) >= 0 && sysDateTime.compareTo(nightDateTime) < 0) {
                end = noonDateTime;
            } else {
                end = nightDateTime;
            }
        }
        return end;
    }

    /**
     * 查询emplyee表，将结果放入userMap和statMap
     *
     * @param daysString
     * @return
     */
    private Map<Integer, Map> getuserMapAndstatMap() throws HttpRPCException {
        Map<String, Employee> userMap = new HashMap<>(); //key: userId
        Map<String, StatItem> statMap = new HashMap<>(); //key: userId
        Map<Integer, Map> map = new HashMap<>();
        EmployeeDAO employeeDao = null;
        try {
            employeeDao = new EmployeeDAO(connSrc);
            List<Employee> employees = this.queryEmployees("");
            
            for (Employee e : employees) {
                StatItem items = new StatItem();
                userMap.put(e.getUserId(), e);
                statMap.put(e.getUserId(), items);
            }
            map.put(0, userMap);
            map.put(1, statMap);
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("query employee failed", ex);
            throw new HttpRPCException("query employee failed", ErrorCode.DB_ERROR);
        }
        return map;
    }

    /**
     * 得到请假的类型
     *
     * @param type
     * @return
     */
    private String getVacationType(VacationTypeEnum type) {
        String t = "";
        switch (type) {
            case BIRTH:
                t = "产";
                break;
            case WEDDING:
                t = "婚";
                break;
            case PAID_LEGAL:
                t = "法";
                break;
            case SICK:
                t = "病";
                break;
            case PERSONAL:
                t = "事";
                break;
            case PAID_INNER:
                t = "内";
                break;
            case NURSING:
                t = "护";
                break;
            case FUNERAL:
                t = "吊";
                break;
            default:
                break;
        }
        return t;
    }

    /**
     * 把请假信息到到报表中
     *
     * @param maps
     * @param start
     * @param end
     * @param vacationLis
     * @param src
     * @throws HttpRPCException
     */
    private void putVacationToReport(Map<String, Map<Date, MorningNoon>> maps, Date start, Date end, ConnectionSource src) throws HttpRPCException {
        VacationDetailDAO detailDAO = null;
        List<VacationAndVacationDetailWrap> wrapList = null;
        try {
            detailDAO = new VacationDetailDAO(src);
            //得到所有请假的list
            wrapList = detailDAO.queryVacationAndDetail(start, end);
            //外层循环，循环请假list
            for (int i = 0; i < wrapList.size(); i++) {
                VacationAndVacationDetailWrap wrap = wrapList.get(i);
                String userId = wrap.getAppliantId();
                Boolean isMorning = wrap.isMorning();
                //内层循环，循环大map
                for (String uId : maps.keySet()) {
                    //得到某一个人的所有出勤格子
                    Map<Date, MorningNoon> dailyVacationList = maps.get(uId);
                    if (uId.equals(userId)) {
                        Date startDate = wrap.getStartTime();
                        Date endDate = wrap.getEndTime();
                        //将请假类型转换为描述：病，事，产
                        VacationTypeEnum type = wrap.getVacationType();
                        String vacationType = getVacationType(type);
                        //从开始时间到结束时间循环
                        for (Date date = startDate; date.getTime() <= endDate.getTime(); date = DateUtil.addDay2Date(date, 1)) {
                            if(date.getTime() > (new Date().getTime())){  //如果大于当前时间就不统计了
                                break;
                            }
                            date = DateUtil.getZeroTimeOfDay(date); //设置为00:00:00
                            startDate = DateUtil.getZeroTimeOfDay(startDate);
                            //如果不在这个时间段内,continue
                            if (!dailyVacationList.containsKey(date)) {
                                continue;
                            } else {
                                HolidayDAO holidayDao = new HolidayDAO(src);
                                Holiday holiday = null;
                                holiday = holidayDao.queryHoliday(date);
                                if (DateUtil.isWorkingDays(date)) {
                                    if (holiday != null && holiday.getHolidayType().equals(HolidayTypeEnum.HOLIDAY)) {
                                        continue;
                                    }
                                    MorningNoon morningNoon = dailyVacationList.get(date);
                                    morningNoon.setMorning(vacationType);
                                    morningNoon.setNoon(vacationType);
                                    //如果循环时间等于请假开始时间，且morning为false，则这天的上午为空
                                    if (date.getTime() == startDate.getTime()) {
                                        if (!isMorning) {
                                            morningNoon.setMorning("");
                                        }
//                                        dailyVacationList.put(date, morningNoon);
                                        //如果循环时间等于请假结束时间，且为12点，则这天的下午为空
                                    } else if (DateUtil.getMiddleTimeOfDay(date).getTime() == endDate.getTime()) {
                                        morningNoon.setNoon("");
                                    }
                                    dailyVacationList.put(date, morningNoon);

                                } else if (holiday != null && holiday.getHolidayType().equals(HolidayTypeEnum.WORKDAY)) {
                                    MorningNoon morningNoon = dailyVacationList.get(date);
                                    morningNoon.setMorning(vacationType);
                                    morningNoon.setNoon(vacationType);
                                    //如果循环时间等于请假开始时间，且morning为false，则这天的上午为空
                                    if (date.getTime() == startDate.getTime()) {
                                        if (!isMorning) {
                                            morningNoon.setMorning("");
                                        }
//                                        dailyVacationList.put(date, morningNoon);
                                        //如果循环时间等于请假结束时间，且为12点，则这天的下午为空
                                    } else if (DateUtil.getMiddleTimeOfDay(date) == endDate) {
                                        morningNoon.setNoon("");
                                    }
                                    dailyVacationList.put(date, morningNoon);
                                }
                            }
                        }
                        
                        //加入某个人的出勤
                        maps.put(uId, dailyVacationList);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("add vacation to report failed", ex);
            throw new HttpRPCException("add vacation to report failed", ErrorCode.DB_ERROR);
        }
    }

    /**
     * 得到开始日期到结束日期的list
     *
     * @param start
     * @param end
     * @param src
     * @return
     */
    private List<Date> getDateList(Date start, Date end) {
        List<Date> dateList = new ArrayList<>();
        Date date = start;
        while (date.getTime() <= end.getTime()) {
            dateList.add(date);
            date = DateUtil.getLastHourStartTime(date, 24);
        }
        return dateList;
    }
    
    private List<Employee> queryEmployees(String keyword){
        List<Employee> employees = new ArrayList<>();
        try {
            EmployeeDAO employeeDAO = new EmployeeDAO(connSrc);
            TeamDAO teamDAO = new TeamDAO(connSrc);
            TeamMemberDAO memberDAO = new TeamMemberDAO(connSrc);
            
            String userId = (String)session.getAttribute(SessionConst.KEY_USER_ID);
            if(USMSTool.hasPermission(Permissions.ADMIN, userId) || USMSTool.hasPermission(Permissions.CEO, userId) || USMSTool.hasPermission(Permissions.HR, userId)){
                //有查询的时候加逻辑
                if (StringUtil.isEmpty(keyword)) {
                    employees = employeeDAO.queryEmpoyees();  
                } else {
                    employees = employeeDAO.queryEmpoyeesByName(keyword);
                }
            }else if(USMSTool.hasPermission(Permissions.PM, userId)){
                    List<TeamMember> memberLis = memberDAO.queryTeamMembersUserId(userId); //teamId
                    for(TeamMember m :  memberLis){
                        Team team = teamDAO.queryTeamByID(m.getTeamId());
                        if(!team.isIsMgmt()){
                            List<String> userIds = memberDAO.queryAllTeamByUsers(team.getTeamId());   //团队所有人ID
                            employees = employeeDAO.queryEmpoyeesByIds(userIds);
                        }
                    }
            }else if(USMSTool.hasPermission(Permissions.STAFF, userId)){
                employees = employeeDAO.queryEmpoyeesByUserId(userId);
            }else{
                employees = employeeDAO.queryEmpoyees();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employees;
    }
    /**
     * caohuiying 将查询出来的数据放到报表中
     *
     * @param start
     * @param end
     * @return
     * @throws HttpRPCException
     */
    @Override
    public List<ReportAttendancesModel> queryStaffAttendanceReport(Date start, Date end, String keyword) throws HttpRPCException {
        ConnectionSource conn = ConnectionUtil.getConnSrc();
        List<Date> dateList = getDateList(start, end);
        List<ReportAttendancesModel> attendanceList = null;

        BizTripDAO tripDAO = null;
        AttendanceDAO attendDAO = null;
        RestDAO restDAO = null;
        EmployeeDAO employeeDAO = null;
        VacationDAO vacationDAO = null;
        try {
            tripDAO = new BizTripDAO(conn);
            attendDAO = new AttendanceDAO(conn);
            restDAO = new RestDAO(conn);
            employeeDAO = new EmployeeDAO(conn);
            vacationDAO = new VacationDAO(conn);
            //个人出勤包装类
            Map<String, Map<Date, MorningNoon>> maps = new HashMap();
            Map<String, String> employMap = new HashMap<>();
            
            List<Employee> employees = queryEmployees(keyword);
            
            //初始化employeeMap
            for (Employee employee : employees) {
                employMap.put(employee.getUserId(), employee.getUserName());
            }

            //初始化大map和小map,把整个盒子填满
            for (String key : employMap.keySet()) {
                Map<Date, MorningNoon> dateMap = new LinkedHashMap();
                for (int i = 0; i < dateList.size(); i++) {
                    MorningNoon morningNoon = new MorningNoon();
                    morningNoon.setMorning("");
                    morningNoon.setNoon("");
                    dateMap.put(dateList.get(i), morningNoon);
                }
                maps.put(key, dateMap);
            }
            //分别将出勤，出差，调休，和请假的list查询出来
            List<Attendance> attendList = attendDAO.queryAttendanceList(start, end);
            List<BizTrip> bizTripList = tripDAO.queryBizTripList(start, end);
            List<Rest> restList = restDAO.queryRestList(start, end);
            List<Vacation> vacationList = vacationDAO.queryVacationList(start, end);
            //将所有出勤对象放到map中
            putAttendanceToReport(maps, attendList, conn);
            //将所有出差对象放到map中
            putBizTripToReport(maps, bizTripList, conn);
            //将所有调休对象放到map中
            putRestToReport(maps, restList, conn);
            //将请假对象放到map中
            putVacationToReport(maps, start, end, conn);

            attendanceList = new ArrayList<>();

            for (String key : employMap.keySet()) {
                ReportAttendancesModel reportAttendancesModel = new ReportAttendancesModel();
                //设置userName
                String userName = employeeDAO.queryEmployeeById(key).getUserName();
                reportAttendancesModel.setUserName(userName);

                //得到某个员工一天的出勤list
                Map<Date, MorningNoon> attendance = new LinkedHashMap<>();

                //得到某一个员工的所有出勤,出差，调休，请假
                Map<Date, MorningNoon> attend = new HashMap<>();
                attend = maps.get(key);
                //存到有序的map中
                if (attend != null) {
                    for (Date d : attend.keySet()) {
                        attendance.put(d, attend.get(d));
                    }
                }
                //将一个员工的时间段内的出勤转换为list,往daylyAttendance这个list中放数据
                List<DailyAttendance> dailyAttendanceList = new ArrayList<>();
                for (Date d : attendance.keySet()) {
                    MorningNoon morninNoon = attendance.get(d);
                    DailyAttendance dailyAttendance = new DailyAttendance();
                    dailyAttendance.setDate(d);
                    dailyAttendance.setMorningNoon(morninNoon);
                    dailyAttendanceList.add(dailyAttendance);
                }

                List<DateMode> days2 = new ArrayList<>();
                for (Date d = start; d.getTime() <= end.getTime(); d = DateUtil.getLastHourStartTime(d, 24)) {
                    Integer day = DateUtil.getDayOfMonthByDate(d);
                    DateMode dateModel = new DateMode();
                    dateModel.setDate(day.toString());
                    days2.add(dateModel);
                }
                //设置某个员工一天的出勤list
                reportAttendancesModel.setAttendance(dailyAttendanceList);
                reportAttendancesModel.setDateList(days2);
                attendanceList.add(reportAttendancesModel);
            }
        } catch (SQLException ex) {
            log.error("query report failed", ex);
            throw new HttpRPCException("query report failed", ErrorCode.DB_ERROR);
        }
        return attendanceList;
    }

    /**
     * zhaohongkun 得到某年某周的最后一天
     *
     * @param year
     * @param week
     * @return
     */
    private Date getLastWeekDateByYearAndWeek(int year, int week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.WEEK_OF_YEAR, week);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday   
        return c.getTime();
    }

    /**
     * 获取两个日期之间的每周的开始时间和终了时间
     *
     * @param start
     * @param end
     * @return
     */
    private Map<Date, Date> getStartDateAndEndDate(Date start, Date end) {
        Map<Date, Date> map = new HashMap<>();
        int weekCountStart = DateUtil.getWeekByDate(start);
        Date weekMinDate = DateUtil.getFistWeekDateByYearAndWeek(getYearByDate(start), weekCountStart);
        Date weekMaxDate = getLastWeekDateByYearAndWeek(getYearByDate(start), weekCountStart);
        map.put(weekMinDate, weekMaxDate);
        while (weekMaxDate.compareTo(end) <= 0) {
            weekCountStart++;
            weekMinDate = DateUtil.getFistWeekDateByYearAndWeek(getYearByDate(start), weekCountStart);
            weekMaxDate = getLastWeekDateByYearAndWeek(getYearByDate(start), weekCountStart);
            map.put(weekMinDate, weekMaxDate);
        }
        return map;
    }

    /**
     * 获得除去周六日的法定节假日
     *
     * @param start
     * @param end
     * @return
     * @throws HttpRPCException
     */
    private int getLegalDaysCount(Date start, Date end) throws HttpRPCException {
        HolidayDAO holidaydao = null;
        try {
            holidaydao = new HolidayDAO(connSrc);
            //获得法定节假日
            List<Holiday> holidayList = holidaydao.queryMonthHolidayList(start, end);
            int legalDaysCount = 0;
            for (Holiday holiday : holidayList) {
                int weekDay = DateUtil.getDayOfWeed(holiday.getHolidayDate());
                if (weekDay != 7 && weekDay != 1) {
                    legalDaysCount++;
                }
            }
            return legalDaysCount;
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("get legalDaysCount failed", ex);
            throw new HttpRPCException("get legalDaysCount failed", ErrorCode.DB_ERROR);
        }
    }

    /**
     * 获得补班天数
     *
     * @param start
     * @param end
     * @return
     * @throws HttpRPCException
     */
    private long getFillWorkDays(Date start, Date end) throws HttpRPCException {
        HolidayDAO holidaydao = null;
        try {
            holidaydao = new HolidayDAO(connSrc);
            //获得补班日数
            long fillWorkDays = holidaydao.queryMonthWORKDAY(start, end);
            return fillWorkDays;
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("get fillWorkDays failed", ex);
            throw new HttpRPCException("get fillWorkDays failed", ErrorCode.DB_ERROR);
        }
    }

    /**
     * 获得法定工作日数
     *
     * @param start
     * @param end
     * @return
     * @throws HttpRPCException
     */
    private long getLegalDays(Date start, Date end) throws HttpRPCException {
        //除去周六日的工作日数-除去周六的法定节假日数 + 补班天数
        long workDays = dateDiff(start, end) - getLegalDaysCount(start, end) + getFillWorkDays(start, end);
        return workDays;

    }

    /**
     * 计算天数 如果区间开始时间的月和终了时间的月相等，则截取区间开始时间和终了时间的时间差
     * 如果区间终了时间的月和开始时间的月相等，则截取区间终了时间和开始时间的时间差 以上两种情况以外则截取开始时间和终了时间的时间差
     *
     * @param start
     * @param end
     * @param startDate
     * @param endDate
     * @param tdays
     * @return
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    private double calculateDays(Date start, Date end, Date startDate, Date endDate, double tdays) throws HttpRPCException {
        double days = 0;
            if (endDate.compareTo(start) >= 0 && endDate.compareTo(end) <= 0 && startDate.compareTo(start) <= 0) {//start落在startDate和endDate之间的情况
                days = getDaysByStartDateAndEndDate(start, endDate) - getLegalDaysCount(start, endDate) + getFillWorkDays(start, endDate) - getSaSunCount(start, endDate);
            } else if (startDate.compareTo(start) >= 0 && startDate.compareTo(end) <= 0 && endDate.compareTo(end) >= 0) {//end落在startDate和endDate之间的情况
                days = getDaysByStartDateAndEndDate(startDate, end) - getLegalDaysCount(startDate, end) + getFillWorkDays(startDate, end) - getSaSunCount(startDate, end);
            } else if (start.compareTo(startDate) <= 0 && end.compareTo(endDate) >= 0) {//startDate和endDate都落在stat和end的区间中
                days = tdays;
            } else if (startDate.compareTo(start) <= 0 && endDate.compareTo(end) >= 0) {//start和end落在startDate和endDate区间内
                days = getDaysByStartDateAndEndDate(start, end) - getLegalDaysCount(start, end) + getFillWorkDays(start, end) - getSaSunCount(start, end);
            } else {
                days = tdays;
            }
        return days;
    }

    /**
     * 获取2个日期之间周六，周日的天数
     *
     * @param start
     * @param stop
     * @return
     */
    private int getSaSunCount(Date start, Date stop) {
        List<Date> yearMonthDayList = new ArrayList();
        if (start.after(stop)) {
            Date tmp = start;
            start = stop;
            stop = tmp;
        }
        //将起止时间中的所有时间加到List中
        Calendar calendarTemp = Calendar.getInstance();
        calendarTemp.setTime(start);
        while (calendarTemp.getTime().getTime() <= stop.getTime()) {
            yearMonthDayList.add(calendarTemp.getTime());
            calendarTemp.add(Calendar.DAY_OF_YEAR, 1);
        }
        Collections.sort(yearMonthDayList);
        int num = 0;//周六，周日的总天数
        int size = yearMonthDayList.size();
        int week = 0;
        for (int i = 0; i < size; i++) {
            Date day = yearMonthDayList.get(i);
            System.out.println(day);
            week = DateUtil.getDayOfWeed(day);
            if (week == 1 || week == 7) {//周六，周日
                num++;
            }
        }
        return num;
    }

    /**
     * 获取指定的时间
     *
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    private Date getTime(Date date, int hour, int minute, int second) {
        //获得中午12:00:00
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        Date getDateTime = calendar.getTime();
        return getDateTime;
    }

    /**
     * 获取开始日期到终了日期之间的除去周六日的工作天数
     *
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

    /**
     * 获取月
     *
     * @param d
     * @return
     */
    private int getMonthByDate(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int month = cal.get(Calendar.MONTH);
        return month + 1;
    }

    /**
     * 获取年
     *
     * @param d
     * @return
     */
    private int getYearByDate(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    /**
     * 获得两个时间差多少天
     *
     * @param begin
     * @param end
     * @return
     */
    private float getDaysByStartDateAndEndDate(Date begin, Date end) {
        long between = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour == 23) {
            int year = getYearByDate(end);
            int months = getMonthByDate(end);
            int days = DateUtil.getDayOfMonthByDate(end);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, months - 1);
            calendar.set(Calendar.DAY_OF_MONTH, days + 1);
            calendar.set(Calendar.HOUR_OF_DAY, 00);
            calendar.set(Calendar.MINUTE, 00);
            calendar.set(Calendar.SECOND, 00);
            end = calendar.getTime();
        }
        between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
        long day = between / (24 * 60 * 60 * 1000);
        long hour2 = (between / (60 * 60 * 1000) - day * 24);
        if (hour2 != 0) {
            hour2 = hour2 + 1;
        }
        if (hour2 == 13) {
            hour2 = hour2 - 1;
        }
        float size = (float) hour2 / 24;
        return (day + size);
    }

}
