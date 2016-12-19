/*
 * BizTripMgmtServiceImpl.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 14:43:48
 */
package com.yz.ams.server.rpcimpl.mgmt;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.sql.PageResult;
import com.yz.ams.consts.AuditStateEnum;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.model.BizTrip;
import com.yz.ams.rpc.mgmt.BizTripMgmtService;
import com.yz.ams.server.dao.BizTripDAO;
import com.yz.ams.server.util.ConnectionUtil;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 出差管理
 * @author Your Name <Song Haixiang >
 */
public class BizTripMgmtServiceImpl implements BizTripMgmtService {

    private static final Log log = LogFactory.getLog(BizTripMgmtServiceImpl.class);

    @Override
    public PageResult<BizTrip> queryBizTrips(String keyword, int curPage, int pageSize) throws HttpRPCException {
        List<BizTrip> list = null;
        PageResult<BizTrip> result = null;
        try {
            BizTripDAO dao = new BizTripDAO(ConnectionUtil.getConnSrc());
            int totalCount = dao.queryCount(keyword);
            curPage = PageResult.recalculateCurPage(totalCount, curPage, pageSize);
            int start = PageResult.getFromIndex(curPage, pageSize);
            list = dao.query(keyword, start, pageSize);
            result = new PageResult<>(totalCount, curPage, pageSize, list);
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("query bizTrip failed", ex);
            throw new HttpRPCException("query bizTrip failed", ErrorCode.DB_ERROR);
        }
        return result;
    }

    @Override
    public BizTrip createBizTrip(BizTrip bizTrip) throws HttpRPCException {
        BizTrip trip = null;
        try {
            BizTripDAO dao = new BizTripDAO(ConnectionUtil.getConnSrc());
            bizTrip.setBizTripId(UUID.randomUUID().toString());
            bizTrip.setAuditState(AuditStateEnum.WAIT_FOR_CEO);
            trip = dao.createBizTrip(bizTrip);

        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("create bizTrip failed", ex);
            throw new HttpRPCException("create bizTrip failed", ErrorCode.DB_ERROR);
        }
        return trip;
    }

    @Override
    public BizTrip modifyBizTrip(BizTrip bizTrip) throws HttpRPCException {
        try {
            BizTripDAO dao = new BizTripDAO(ConnectionUtil.getConnSrc());
            return dao.modifyBizTrip(bizTrip);
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("modify bizTrip failed", ex);
            throw new HttpRPCException("modify bizTrip failed", ErrorCode.DB_ERROR);
        }
    }

    @Override
    public BizTrip deleteBizTrip(BizTrip bizTrip) throws HttpRPCException {
        try {
            BizTripDAO dao = new BizTripDAO(ConnectionUtil.getConnSrc());
            return dao.deleteBizTrip(bizTrip);
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("delete bizTrip failed", ex);
            throw new HttpRPCException("delete bizTrip failed", ErrorCode.DB_ERROR);
        }
    }

}
