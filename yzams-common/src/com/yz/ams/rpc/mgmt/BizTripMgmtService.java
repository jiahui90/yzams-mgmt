/*
 * BizTripMgmtService.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 14:42:26
 */
package com.yz.ams.rpc.mgmt;

import com.yz.ams.model.BizTrip;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.HttpRPCSessionTokenRequired;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.nazca.sql.PageResult;

/**
 * 出差管理
 *
 * @author Your Name <Song Haixiang >
 */
@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING, identifier
        = "com.yz.ams.server.rpcimpl.mgmt.BizTripMgmtServiceImpl")
public interface BizTripMgmtService {

    /**
     * 分页获取出差信息
     *
     * @param keyword
     * @param curPage
     * @param pageSize
     * @return　出差信息列表,如果未查到,则返回长度为0的列表
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    PageResult<BizTrip> queryBizTrips(String keyword, int curPage, int pageSize) throws HttpRPCException;

    /**
     * 添加出差信息
     *
     * @param bizTrip
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public BizTrip createBizTrip(BizTrip bizTrip) throws HttpRPCException;

    /**
     * 修改出差信息
     *
     * @param bizTrip
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public BizTrip modifyBizTrip(BizTrip bizTrip) throws HttpRPCException;

    /**
     * 删除出差信息
     *
     * @param bizTrip
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public BizTrip deleteBizTrip(BizTrip bizTrip) throws HttpRPCException;

}
