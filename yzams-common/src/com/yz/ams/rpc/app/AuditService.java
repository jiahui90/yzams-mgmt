package com.yz.ams.rpc.app;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.yz.ams.model.wrap.app.ApplyInfo;
import java.util.Date;
import java.util.List;

/**
 * Created by litao on 2016/1/18.
 */
@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING, identifier = "com.yz.ams.server.rpcimpl.app.AuditServiceImpl")
public interface AuditService {

    /**
     * 获取我的申请信息
     *
     * @param userId 申请人ID
     * @param dateTime 时间,传null表示当前时间
     * @param isAfter 是否向后(时间越大越向后)查询
     * @param count 数量
     * @return
     * @throws HttpRPCException
     */
//    @HttpRPCSessionTokenRequired
    public List<ApplyInfo> getMyApplyInfos(String userId, Date dateTime, boolean isAfter, int count) throws HttpRPCException;
    public List<ApplyInfo> getMyAuditInfos(String userId, Date dateTime, boolean isAfter, int count,boolean isPM) throws HttpRPCException;
    /**
     * 请假审核通过
     *
     * @param vacationId
     * @param auditorId
     * @param isPM
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
  public void auditVacationPass(String vacationId, String auditorId,String auditorName,boolean isPM) throws HttpRPCException;

    /**
     * 请假审核不通过
     *
     * @param vacationId
     * @param auditorId
     * @param isPM
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
   public void auditVacationDeny(String vacationId, String auditorId,String auditorName,boolean isPM) throws HttpRPCException;

    /**
     * 出差审核通过
     *
     * @param bizTripId
     * @param auditorId
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
  public  void auditBizTripPass(String bizTripId, String auditorId,String auditorName) throws HttpRPCException;

    /**
     * 出差审核不通过
     *
     * @param bizTripId
     * @param auditorId
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
   public void auditBizTripDeny(String bizTripId, String auditorId,String auditorName) throws HttpRPCException;
    
    /**
     * 调休审核通过
     *
     * @param restId
     * @param auditorId
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
   public void auditRestPass(String restId, String auditorId,String auditorName) throws HttpRPCException;

    /**
     * 调休审核不通过
     *
     * @param restId
     * @param auditorId
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
   public void auditRestDeny(String restId, String auditorId,String auditorName) throws HttpRPCException;
}
