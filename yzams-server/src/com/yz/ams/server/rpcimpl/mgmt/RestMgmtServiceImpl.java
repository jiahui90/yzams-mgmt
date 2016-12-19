/*
 * RestMgmtServiceImpl.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 16:18:21
 */
package com.yz.ams.server.rpcimpl.mgmt;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.sql.PageResult;
import com.yz.ams.consts.AuditStateEnum;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.model.Rest;
import com.yz.ams.rpc.mgmt.RestMgmtService;
import com.yz.ams.server.dao.RestDAO;
import com.yz.ams.server.util.ConnectionUtil;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 调休管理
 * @author Your Name <Song Haixiang >
 */
public class RestMgmtServiceImpl implements RestMgmtService {
    private static final Log log = LogFactory.getLog(AttendanceMgmtServiceImpl.class);

    @Override
    public PageResult<Rest> queryRest(String keyword, int curPage, int pageSize) throws HttpRPCException {
        List<Rest> list = null;
        PageResult<Rest> result = null;
        try {
            RestDAO dao = new RestDAO(ConnectionUtil.getConnSrc());
            int totalCount = dao.queryCount(keyword);
            curPage = PageResult.recalculateCurPage(totalCount, curPage, pageSize);
            int start = PageResult.getFromIndex(curPage, pageSize);
            list = dao.query(keyword, start, pageSize);
            result = new PageResult<>(totalCount, curPage, pageSize, list);
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("query rest failed", ex);
            throw new HttpRPCException("query rest failed", ErrorCode.DB_ERROR);
        }
        return result;
    }

    @Override
    public Rest createRest(Rest rest) throws HttpRPCException {
        try {
            RestDAO dao = new RestDAO(ConnectionUtil.getConnSrc());
            rest.setRestId(UUID.randomUUID().toString());
            rest.setAuditState(AuditStateEnum.WAIT_FOR_CEO);
            return dao.createRest(rest);
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("creat rest failed", ex);
            throw new HttpRPCException("creat rest failed", ErrorCode.DB_ERROR);
        }

    }

    @Override
    public Rest modifyRest(Rest rest) throws HttpRPCException {
        try {
            RestDAO dao = new RestDAO(ConnectionUtil.getConnSrc());
            return dao.modifyRest(rest);
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("modify rest failed", ex);
            throw new HttpRPCException("modify rest failed", ErrorCode.DB_ERROR);
        }
    }

    @Override
    public Rest deleteRest(Rest rest) throws HttpRPCException {
        try {
            RestDAO dao = new RestDAO(ConnectionUtil.getConnSrc());
            return dao.deleteRest(rest);
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("delete rest failed", ex);
            throw new HttpRPCException("delete rest failed", ErrorCode.DB_ERROR);
        }
    }
   
}