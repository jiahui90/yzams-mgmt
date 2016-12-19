/*
 * AttendanceMgmtService.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-15 17:36:30
 */
package com.yz.ams.rpc.mgmt;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.HttpRPCSessionTokenRequired;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.nazca.sql.PageResult;
import com.yz.ams.model.Attendance;
import java.util.List;

/**
 * 出勤管理服务
 *
 * @author Zhang Chun Nan
 */
@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING, identifier
        = "com.yz.ams.server.rpcimpl.mgmt.AttendanceMgmtServiceImpl")
public interface AttendanceMgmtService {

    /**
     * 添加出勤信息
     *
     * @param attendanceLis
     * @return 
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public List<Attendance> createAttendance(List<Attendance> attendanceLis) throws HttpRPCException;

    /**
     * 获取出勤信息
     * @param keyword
     * @param curPage
     * @param pageSize
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired 
    public PageResult<Attendance> queryAttnedance(String keyword, int curPage, int pageSize) throws HttpRPCException;

    /**
     * 修改出勤信息
     *
     * @param attendance
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public Attendance modifyAttendance(Attendance attendance) throws HttpRPCException;

    /**
     * 删除出勤信息
     *
     * @param attendance
     * @return 
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public Attendance deleteAttendance(Attendance attendance) throws HttpRPCException;

}
