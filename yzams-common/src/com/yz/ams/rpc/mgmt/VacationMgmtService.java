/*
 * VacationMgmtService.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 17:28:16
 */
package com.yz.ams.rpc.mgmt;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.HttpRPCSessionTokenRequired;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.nazca.sql.PageResult;
import com.yz.ams.model.wrap.mgmt.VacationInfo;
import com.yz.ams.model.wrap.mgmt.VacationNote;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 请假管理
 *
 * @author Your Name <Song Haixiang >
 */
@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING, identifier
        = "com.yz.ams.server.rpcimpl.mgmt.VacationMgmtServiceImpl")
public interface VacationMgmtService {

    /**
     * 分页获取请假信息
     *
     * @param keyword
     * @param curPage
     * @param pageSize
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    PageResult<VacationInfo> queryVacations(String keyword, int curPage, int pageSize) throws HttpRPCException;

    /**
     * 添加请假信息
     *
     * @param vacation
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    VacationInfo createVacation(VacationInfo vacation) throws HttpRPCException;

    /**
     * 修改请假信息
     *
     * @param vacation
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    VacationInfo modifyVacation(VacationInfo vacation) throws HttpRPCException;

    /**
     * 删除请假信息
     *
     * @param vacation
     * @return 
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    VacationInfo deleteVacation(VacationInfo vacation) throws HttpRPCException;

    /**
     * 获取证明图片
     *
     * @param certificatePicId
     * @param CertificatepicId
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    OutputStream queryCertificatePic(String certificatePicId) throws HttpRPCException;

    /**
     * 审批通过
     *
     * @param vacationId
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    void auditCertificatePass(String vacationId) throws HttpRPCException;

    /**
     * 审批不通过
     *
     * @param vacationId
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    void auditCertificateDeny(String vacationId) throws HttpRPCException;
    
    /**
     * 获取证明图片
     *
     * @param certificatePicId
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    InputStream getCertificatePic(String certificatePicId) throws HttpRPCException;
    /**
     * 根据applicantId查询假条
     * @param applicantId
     * @return
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    List<VacationNote> queryVacationNotes(String applicantId) throws HttpRPCException;
}
