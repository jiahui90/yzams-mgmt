package com.yz.ams.rpc.app;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.yz.ams.model.Vacation;
import com.yz.ams.model.VacationDetail;
import com.yz.ams.model.wrap.app.VacationWrap;

import java.io.InputStream;
import java.util.List;

/**
 * 请假服务 
 * Created by litao on 2016/1/18.
 */
@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING, identifier = "com.yz.ams.server.rpcimpl.app.VacationServiceImpl")
public interface VacationService {

    /**
     * 查询一条请假信息
     * @param vacationId 请假信息ID
     * @return 
     * @return一条请假信息的对象
     */
    VacationWrap queryVacation(String vacationId) throws HttpRPCException;

    /**
     * 上传请假信息
     * @param vacationInfo
     * @param vacationDetails
     * @return
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    VacationWrap applyVacation(Vacation vacationInfo, List<VacationDetail> vacationDetails) throws HttpRPCException;

    /**
     * 上传病假证明
     * @param vacationID
     * @param imgFile
     * @param filename
     * @return
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
   public Vacation uploadSickCertificate(String vacationID, InputStream imgFile,String filename)throws HttpRPCException;


    /**
     * 获取病假证明图片
     *
     * @param certificatePicID
     * @return
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    InputStream downloadSickCertificate(String certificatePicID) throws HttpRPCException;
    /**
     * 获取内部年假
     * @param userID
     * @return
     * @throws HttpRPCException 
     */
    double queryPaidInnerDays(String userID) throws HttpRPCException;
    /**
     * 获取法定年假
     * @param userID
     * @return
     * @throws HttpRPCException 
     */
    double queryPaidLegalDays(String userID) throws HttpRPCException;
    
    
     /**
     * 获取总年假
     * @param userID
     * @return
     * @throws HttpRPCException 
     */
    double queryPaidDays(String userID) throws HttpRPCException;
    
}
