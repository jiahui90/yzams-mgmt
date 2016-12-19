/*
 * OfficialDaysMgmtService.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 17:27:49
 */
package com.yz.ams.rpc.mgmt;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.HttpRPCSessionTokenRequired;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.nazca.sql.PageResult;
import com.yz.ams.model.PaidVacation;
import com.yz.ams.model.wrap.mgmt.PaidVacationWrap;
import java.util.List;

/**
 * 年假管理
 *
 * @author Your Name <Song Haixiang >
 */
@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING, identifier
        = "com.yz.ams.server.rpcimpl.mgmt.PaidVacationServiceImpl")
public interface PaidVacationService {

    /**
     * 分页获取年假信息
     *
     * @param keyword
     * @param year
     * @param curPage
     * @param pageSize
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public PageResult<PaidVacation> queryPaidVacation(String keyword, int year) throws
            HttpRPCException;

    /**
     * 修改年假信息
     *
     * @param paidVacation
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public PaidVacation modifyPaidVacation(PaidVacation paidVacation) throws HttpRPCException;
   
    /**
     * 获取年假信息及员工信息
     * @param year
     * @return
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    public List<PaidVacationWrap> queryPaidVacationInfo(int year) throws
            HttpRPCException;

    /**
     * 修改年假信息
     *
     * @param paidVacation
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public PaidVacationWrap modifyPaidVacation(PaidVacationWrap paidVacation) throws HttpRPCException;
}
