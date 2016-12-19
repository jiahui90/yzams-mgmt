/*
 * StatMgmtService.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 17:27:19
 */
package com.yz.ams.rpc.mgmt;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.HttpRPCSessionTokenRequired;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.nazca.sql.PageResult;
import com.yz.ams.model.wrap.mgmt.AttendanceMgmtStat;
import com.yz.ams.model.wrap.mgmt.ReportAttendancesModel;
import com.yz.ams.model.wrap.mgmt.StaffAttendancesWrap;
import java.util.Date;
import java.util.List;

/**
 * 考勤统计
 *
 * @author Your Name <Song Haixiang >
 */
@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING, identifier = "com.yz.ams.server.rpcimpl.mgmt.StatMgmtServiceImpl")
public interface StatMgmtService {
/**
 * 分页获取考勤统计
 * @param statType
 * @param date
 * @param keyword
 * @param curPage
 * @param pageSize
 * @return
 * @throws HttpRPCException 
 */
//    PageResult<AttendanceMgmtStat> queryAttendanceStat(StatTypeEnum statType, Date date, 
//            String keyword,  int curPage, int pageSize) throws HttpRPCException;
/**
 * 分页过去考勤报表
 * @param week
 * @param month
 * @param keyword
 * @param curPage
 * @param pageSize
 * @return
 * @throws HttpRPCException 
 */
    @HttpRPCSessionTokenRequired
    PageResult<StaffAttendancesWrap> queryStaffAttendanceDetails(String week, String month, String keyword, int curPage, int pageSize) throws HttpRPCException;
    
    /**
     * caohuiying 
     * 获取考勤报表
     * @param start
     * @param end
     * @param keyword
     * @return
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    public List<ReportAttendancesModel> queryStaffAttendanceReport(Date start, Date end, String keyword) throws HttpRPCException;
    
    @HttpRPCSessionTokenRequired
    public List<ReportAttendancesModel> queryStaffAttendanceReport(Date start, Date end) throws HttpRPCException;
    
    /**zhaohongkun
     * 考勤统计
     * @param start
     * @param end
     * @return
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    public List<AttendanceMgmtStat> queryAttendanceStat(Date start, Date end) throws HttpRPCException; 
}
