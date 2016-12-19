/*
 * AuditServiceImpl.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-26 09:06:22
 */
package com.yz.ams.server.rpcimpl.app;

import com.j256.ormlite.support.ConnectionSource;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.util.StringUtil;
import com.yz.ams.consts.ApplyInfoTypeEnum;
import com.yz.ams.consts.AuditStateEnum;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.model.BizTrip;
import com.yz.ams.model.Rest;
import com.yz.ams.model.Vacation;
import com.yz.ams.model.wrap.app.ApplyInfo;
import com.yz.ams.rpc.app.AuditService;
import com.yz.ams.server.dao.BizTripDAO;
import com.yz.ams.server.dao.RestDAO;
import com.yz.ams.server.dao.TeamDAO;
import com.yz.ams.server.dao.TeamMemberDAO;
import com.yz.ams.server.dao.VacationDAO;
import com.yz.ams.server.util.ConnectionUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author litao
 */
public class AuditServiceImpl implements AuditService {
    /**
     * 获取我的申请信息 litao
     *
     * @param userId 申请人ID
     * @param dateTime 时间,传null表示当前时间
     * @param isAfter 是否向后(时间越大越向后)查询
     * @param count 数量
     * @return List<ApplyInfo>
     * @throws HttpRPCException
     */
    @Override
    public List<ApplyInfo> getMyApplyInfos(String userId, Date dateTime,
            boolean isAfter, int count) throws HttpRPCException {

        try {
              ConnectionSource src = ConnectionUtil.getConnSrc();
            List<ApplyInfo> applyList = new ArrayList<>();
            List<ApplyInfo> applyListReturn = new ArrayList<>();

            VacationDAO vacationDAO = new VacationDAO(src);
            BizTripDAO bizTripDAO = new BizTripDAO(src);
            RestDAO restDAO = new RestDAO(src);

            List<Vacation> vacationListApply = vacationDAO.queryMyApply(userId,
                    dateTime,
                    isAfter, count);
            List<Rest> restListApply = restDAO.queryMyApply(userId, dateTime,
                    isAfter,
                    count);
            List<BizTrip> bizTripListApply = bizTripDAO.
                    queryMyApply(userId, dateTime, isAfter, count);

            for (Vacation data : vacationListApply) {
                ApplyInfo applyInfo = new ApplyInfo();
                applyInfo.setApplierName(data.getApplicantName());
                applyInfo.setApplyRecordId(data.getVacationId());
                applyInfo.setApplyTime(data.getCreateTime());
                applyInfo.setAuditState(data.getAuditState());
                applyInfo.setInfoType(ApplyInfoTypeEnum.VACATION);
                applyInfo.setSickCertState(data.getCertificateState());

                applyList.add(applyInfo);
            }
            for (Rest data : restListApply) {
                ApplyInfo applyInfo = new ApplyInfo();
                applyInfo.setApplierName(data.getUserName());
                applyInfo.setApplyRecordId(data.getRestId());
                applyInfo.setApplyTime(data.getCreateTime());
                applyInfo.setAuditState(data.getAuditState());
                applyInfo.setInfoType(ApplyInfoTypeEnum.REST);

                applyList.add(applyInfo);
            }
            for (BizTrip data : bizTripListApply) {
                ApplyInfo applyInfo = new ApplyInfo();
                applyInfo.setApplierName(data.getApplicantName());
                applyInfo.setApplyRecordId(data.getBizTripId());
                applyInfo.setApplyTime(data.getCreateTime());
                applyInfo.setAuditState(data.getAuditState());
                applyInfo.setInfoType(ApplyInfoTypeEnum.BIZ_TRIP);
                applyList.add(applyInfo);
            }

            Collections.sort(applyList, new Comparator<ApplyInfo>() {
                @Override
                public int compare(ApplyInfo o1, ApplyInfo o2) {

                    Date date1 = o1.getApplyTime();
                    Date date2 = o2.getApplyTime();
                    if (date1.before(date2)) {
                        return 1;
                    }
                    return -1;
                }
            });
            //截取10条

            applyListReturn.clear();
            if (applyList.size() >= 10) {
                applyListReturn.addAll(applyList.subList(0, count));
            } else {
                applyListReturn.addAll(applyList.subList(0,
                        applyList.size()));
            }
            return applyListReturn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HttpRPCException("请假信息查询失败",
                    ErrorCode.DB_TRANSACTION_ERROR);
        }
    }

    /**
     * 获取审核信息
     *
     * @param userId
     * @param dateTime
     * @param isAfter
     * @param count
     * @param isPM
     * @return
     * @throws HttpRPCException
     */
    @Override
    public List<ApplyInfo> getMyAuditInfos(String userId, Date dateTime,
            boolean isAfter, int count, boolean isPM) throws HttpRPCException {

        try {
             ConnectionSource src = ConnectionUtil.getConnSrc();
            List<ApplyInfo> applyListAudit = new ArrayList<>();
            List<ApplyInfo> applyListAuditReturn = new ArrayList<>();
            List<Vacation> vacationListAudit;
            List<Rest> restListAudit;
            List<BizTrip> bizTripListAudit;

            VacationDAO vacationDAO = new VacationDAO(src);
            BizTripDAO bizTripDAO = new BizTripDAO(src);
            RestDAO restDAO = new RestDAO(src);

            
            if (isPM) {
                TeamMemberDAO teamMemberDao = new TeamMemberDAO(src);
                List<String> list = teamMemberDao.queryAllTeamUserIdByUserId(userId,new TeamDAO(src).queryTeamNotTrue());
                vacationListAudit = vacationDAO.queryPMAudit(list, dateTime,
                        isAfter, count);
                for (Vacation data : vacationListAudit) {
                    ApplyInfo applyInfo = new ApplyInfo();
                    applyInfo.setApplierName(data.getApplicantName());
                    applyInfo.setApplyRecordId(data.getVacationId());
                    applyInfo.setApplyTime(data.getCreateTime());
                    applyInfo.setAuditState(data.getAuditState());
                    applyInfo.setInfoType(ApplyInfoTypeEnum.VACATION);
                    applyInfo.setSickCertState(data.getCertificateState());
                    applyListAudit.add(applyInfo);
                }
            } else {
                
                 TeamMemberDAO teamMemberDao = new TeamMemberDAO(src);
                List<String> list = teamMemberDao.queryAllTeamUserIdByUserId(userId,new TeamDAO(src).queryTeamNotTrue());
                vacationListAudit = vacationDAO.queryPMAudit(list, dateTime,
                        isAfter, count);
                for (Vacation data : vacationListAudit) {
                    ApplyInfo applyInfo = new ApplyInfo();
                    applyInfo.setApplierName(data.getApplicantName());
                    applyInfo.setApplyRecordId(data.getVacationId());
                    applyInfo.setApplyTime(data.getCreateTime());
                    applyInfo.setAuditState(data.getAuditState());
                    applyInfo.setInfoType(ApplyInfoTypeEnum.VACATION);
                    applyInfo.setSickCertState(data.getCertificateState());
                    applyListAudit.add(applyInfo);
                }
                
                vacationListAudit = vacationDAO.queryCEOAudit(dateTime,
                        isAfter, count);
                restListAudit = restDAO.queryCEOAudit(dateTime, isAfter,
                                count);
                bizTripListAudit = bizTripDAO.queryCEOAudit(dateTime,
                        isAfter, count);

                for (Vacation data : vacationListAudit) {
                    ApplyInfo applyInfo = new ApplyInfo();
                    applyInfo.setApplierName(data.getApplicantName());
                    applyInfo.setApplyRecordId(data.getVacationId());
                    applyInfo.setApplyTime(data.getCreateTime());
                    applyInfo.setAuditState(data.getAuditState());
                    applyInfo.setInfoType(ApplyInfoTypeEnum.VACATION);
                    applyInfo.setSickCertState(data.getCertificateState());

                    applyListAudit.add(applyInfo);
                }
                for (Rest data : restListAudit) {
                    ApplyInfo applyInfo = new ApplyInfo();
                    applyInfo.setApplierName(data.getUserName());
                    applyInfo.setApplyRecordId(data.getRestId());
                    applyInfo.setApplyTime(data.getCreateTime());
                    applyInfo.setAuditState(data.getAuditState());
                    applyInfo.setInfoType(ApplyInfoTypeEnum.REST);

                    applyListAudit.add(applyInfo);
                }
                for (BizTrip data : bizTripListAudit) {
                    ApplyInfo applyInfo = new ApplyInfo();
                    applyInfo.setApplierName(data.getApplicantName());
                    applyInfo.setApplyRecordId(data.getBizTripId());
                    applyInfo.setApplyTime(data.getCreateTime());
                    applyInfo.setAuditState(data.getAuditState());
                    applyInfo.setInfoType(ApplyInfoTypeEnum.BIZ_TRIP);
                    applyListAudit.add(applyInfo);
                }
            }
            Collections.sort(applyListAudit, new Comparator<ApplyInfo>() {
                @Override
                public int compare(ApplyInfo o1, ApplyInfo o2) {

                    Date date1 = o1.getApplyTime();
                    Date date2 = o2.getApplyTime();
                    if (date1.before(date2)) {
                        return 1;
                    }
                    return -1;
                }
            });
            applyListAuditReturn.clear();
            if (applyListAudit.size() >= 10) {
                applyListAuditReturn.
                        addAll(applyListAudit.subList(0, count));
            } else {
                applyListAuditReturn.addAll(applyListAudit.subList(0,
                        applyListAudit.size()));
            }
            return applyListAuditReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new HttpRPCException("请假信息查询失败",
                    ErrorCode.DB_TRANSACTION_ERROR);
        }
    }

    /**
     * 请假审核通过litao
     *
     * @param vacationId
     * @param auditorId
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    @Override
    public void auditVacationPass(String vacationId, String auditorId,
            String auditorName,boolean isPM) throws
            HttpRPCException {
        try {
            ConnectionSource src = ConnectionUtil.getConnSrc();
            VacationDAO vacationDAO = new VacationDAO(src);
            Vacation vacation = vacationDAO.queryById(vacationId);
            double d = vacation.getTotalDays();
            if (d < 3) {
                vacationDAO.updateVacationState(vacationId,auditorId,auditorName,
                        AuditStateEnum.PASS,isPM);
            } else if (isPM) {
                vacationDAO.updateVacationState(vacationId, auditorId,auditorName,
                        AuditStateEnum.WAIT_FOR_CEO,isPM);
            } else {
                vacationDAO.updateVacationState(vacationId, auditorId,auditorName,
                        AuditStateEnum.PASS,isPM);
            }
        } catch (SQLException ex) {
            throw new HttpRPCException("请假审核通过失败",
                    ErrorCode.DB_TRANSACTION_ERROR);
        }
    }

    /**
     * 请假审核不通过litao
     *
     * @param vacationId
     * @param auditorId
     * @param isPM
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    public void auditVacationDeny(String vacationId, String auditorId,
           String auditorName,boolean isPM) throws
            HttpRPCException {
        VacationDAO dao;
        try {
            dao = new VacationDAO(ConnectionUtil.getConnSrc());
            dao.updateVacationState(vacationId, auditorId,auditorName,AuditStateEnum.DENY,
                    isPM);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new HttpRPCException("请假审核不通过失败",
                    ErrorCode.DB_TRANSACTION_ERROR);
        }

    }

    /**
     * lyc 出差审核通过
     *
     * @param bizTripId
     * @param auditorId
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    @Override
    public void auditBizTripPass(String bizTripId, String auditorId,String auditorName) throws
            HttpRPCException {
        if (StringUtil.isEmpty(bizTripId) || StringUtil.isEmpty(auditorId)) {
            throw new HttpRPCException("参数不能为空", ErrorCode.PARAMETER_ERROR);
        }
        try {
            BizTripDAO dao = new BizTripDAO(ConnectionUtil.getConnSrc());
            dao.updateBizTripState(bizTripId, auditorId,auditorName,AuditStateEnum.PASS);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new HttpRPCException("出差审核通过失败",
                    ErrorCode.DB_TRANSACTION_ERROR);
        }

    }

    /**
     * lyc 出差审核不通过
     *
     * @param bizTripId
     * @param auditorId
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    @Override
    public void auditBizTripDeny(String bizTripId, String auditorId,String auditorName) throws
            HttpRPCException {
        if (StringUtil.isEmpty(bizTripId) || StringUtil.isEmpty(auditorId)) {
            throw new HttpRPCException("参数不能为空", ErrorCode.PARAMETER_ERROR);
        }
        try {
            BizTripDAO dao = new BizTripDAO(ConnectionUtil.getConnSrc());
            dao.updateBizTripState(bizTripId, auditorId,auditorName,AuditStateEnum.DENY);
        } catch (SQLException ex) {
            throw new HttpRPCException("出差审核不通过失败",
                    ErrorCode.DB_TRANSACTION_ERROR);
        }
    }

    /**
     * lyc 调休审核通过
     *
     * @param restId
     * @param auditorId
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    @Override
    public void auditRestPass(String restId, String auditorId,String auditorName) throws
            HttpRPCException {
        if (StringUtil.isEmpty(restId) || StringUtil.isEmpty(auditorId)) {
            throw new HttpRPCException("参数不能为空", ErrorCode.PARAMETER_ERROR);
        }
        try {
            RestDAO dao = new RestDAO(ConnectionUtil.getConnSrc());
            dao.updateRestState(restId, auditorId,auditorName,AuditStateEnum.PASS);
        } catch (SQLException ex) {
            throw new HttpRPCException("调休审核通过失败",
                    ErrorCode.DB_TRANSACTION_ERROR);
        }
    }

    /**
     * lyc 调休审核不通过
     *
     * @param restId
     * @param auditorId
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    @Override
    public void auditRestDeny(String restId, String auditorId,String auditorName) throws
            HttpRPCException {
        if (StringUtil.isEmpty(restId) || StringUtil.isEmpty(auditorId)) {
            throw new HttpRPCException("参数不能为空", ErrorCode.PARAMETER_ERROR);
        }
        try {
            RestDAO dao = new RestDAO(ConnectionUtil.getConnSrc());
            dao.updateRestState(restId, auditorId,auditorName,AuditStateEnum.DENY);
        } catch (SQLException ex) {
            throw new HttpRPCException("调休审核否决失败",
                    ErrorCode.DB_TRANSACTION_ERROR);
        }
    }

}
