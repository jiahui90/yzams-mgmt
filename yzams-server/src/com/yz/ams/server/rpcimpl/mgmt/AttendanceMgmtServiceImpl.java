/*
 * AttendanceMgmtServiceImpl.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-15 17:39:26
 */
package com.yz.ams.server.rpcimpl.mgmt;

import com.j256.ormlite.misc.TransactionManager;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.sql.PageResult;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.model.Attendance;
import com.yz.ams.rpc.mgmt.AttendanceMgmtService;
import com.yz.ams.server.dao.AttendanceDAO;
import com.yz.ams.server.util.ConnectionUtil;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 出勤管理
 * @author Zhang Chun Nan
 */
public class AttendanceMgmtServiceImpl implements AttendanceMgmtService {

    private static Log log = LogFactory.getLog(AttendanceMgmtServiceImpl.class);

    @Override
    public PageResult<Attendance> queryAttnedance(String keyword, int curPage,
            int pageSize) throws HttpRPCException {
        List<Attendance> list = null;
        try {
            AttendanceDAO dao = new AttendanceDAO(ConnectionUtil.getConnSrc());
            int totalCount = dao.queryCount(keyword);
            curPage = PageResult.recalculateCurPage(totalCount, curPage,
                    pageSize);
            int start = PageResult.getFromIndex(curPage, pageSize);
            list = dao.query(keyword, start, pageSize);
            return new PageResult<>(totalCount, curPage, pageSize, list);
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("query attnedance failed", ex);
            throw new HttpRPCException("query attnedance failed",
                    ErrorCode.DB_ERROR);
        }
    }

    @Override
    public List<Attendance> createAttendance(List<Attendance> attendanceLis)
            throws HttpRPCException {
        try {
            AttendanceDAO dao = new AttendanceDAO(ConnectionUtil.getConnSrc());
            TransactionManager.callInTransaction(ConnectionUtil.getConnSrc(),
                    new Callable<Void>() {
                public Void call() throws Exception {
                    for (Attendance attendance : attendanceLis) {
                        dao.creatAttendance(attendance);
                    }
                    return null;
                }
            });
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("create attnedance failed", ex);
            throw new HttpRPCException("create attnedance failed",
                    ErrorCode.DB_ERROR);
        }
        return attendanceLis;
    }

    @Override
    public Attendance modifyAttendance(Attendance attendance) throws
            HttpRPCException {
        try {
            AttendanceDAO dao = new AttendanceDAO(ConnectionUtil.getConnSrc());
            attendance.setModifyTime(new Date());
            return dao.modifyAttendance(attendance);
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("modify attnedance failed", ex);
            throw new HttpRPCException("modify attnedance failed",
                    ErrorCode.DB_ERROR);
        }
    }

    @Override
    public Attendance deleteAttendance(Attendance attendance) throws
            HttpRPCException {
        try {
            AttendanceDAO dao = new AttendanceDAO(ConnectionUtil.getConnSrc());
            attendance.setModifyTime(new Date());
            attendance.setDeleted(true);
            return dao.deleteAttendance(attendance);
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("delete attnedance failed", ex);
            throw new HttpRPCException("delete attnedance failed",
                    ErrorCode.DB_ERROR);
        }
    }
}
