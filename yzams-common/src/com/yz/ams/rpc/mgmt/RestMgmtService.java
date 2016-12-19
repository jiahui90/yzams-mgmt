/*
 * RestMgmtService.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 16:14:05
 */
package com.yz.ams.rpc.mgmt;

import com.yz.ams.model.Rest;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.HttpRPCSessionTokenRequired;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.nazca.sql.PageResult;

/**
 * 调休管理
 *
 * @author Your Name <Song Haixiang >
 */
@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING, identifier
        = "com.yz.ams.server.rpcimpl.mgmt.RestMgmtServiceImpl")
public interface RestMgmtService {
    /**
     * 分页获取调休信息
     *
     * @param keyword
     * @param curPage
     * @param pageSize
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    PageResult<Rest> queryRest(String keyword, int curPage, int pageSize) throws HttpRPCException;

    /**
     * 添加调休信息
     *
     * @param rest
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public Rest createRest(Rest rest) throws HttpRPCException;

    /**
     * 修改调休信息
     *
     * @param rest
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public Rest modifyRest(Rest rest) throws HttpRPCException;

    /**
     * 删除调休信息
     *
     * @param rest
     * @return 
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public Rest deleteRest(Rest rest) throws HttpRPCException;

}
