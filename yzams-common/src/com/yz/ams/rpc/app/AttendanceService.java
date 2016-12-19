package com.yz.ams.rpc.app;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.nazca.usm.model.USMSUser;
import com.yz.ams.model.Attendance;
import com.yz.ams.model.wrap.app.AttendanceStat;
import com.yz.ams.model.wrap.app.MemberAttendanceStat;
import com.yz.ams.model.wrap.app.StaffAttendance;
import com.yz.ams.model.wrap.app.TeamAttendanceStat;
import java.util.Date;
import java.util.List;

/**
 * 考勤记录 Created by luoyongchang on 2016/1/18.
 */
@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING, identifier = "com.yz.ams.server.rpcimpl.app.AttendanceServiceImpl")
public interface AttendanceService {

    /**
     * 获取所有员工当日的出勤情况
     *
     * @return 返回所有员工对象
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    public List<StaffAttendance> queryTodayStaffAttendances() throws HttpRPCException;

    /**
     * 添加一条考勤记录信息
     *
     * @param attendance 考勤对象
     * @return
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    public Attendance createOrModifyAttendance(Attendance attendance) throws HttpRPCException;

    /**
     * 查询员工的出勤统计信息
     *
     * @param userId 员工ID
     * @return 员工对象
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    public AttendanceStat queryAttendanceStat(String userId) throws HttpRPCException;
    /**
     * 查询员工所在团队出勤统计
     * @param userId
     * @return
     * @throws HttpRPCException 
     */
    public MemberAttendanceStat queryLastMonthMyTeamMemberAttendanceStat(String userId) throws HttpRPCException;
    /**
     * 查询团队出勤统计
     * @return
     * @throws HttpRPCException 
     */
    public TeamAttendanceStat queryLastMonthTeamAttendanceStat() throws HttpRPCException;
  
  
}
