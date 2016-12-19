/*
 * VacationMgmtServiceImpl.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 17:33:47
 */
package com.yz.ams.server.rpcimpl.mgmt;

import com.j256.ormlite.misc.TransactionManager;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.HttpRPCInjection;
import com.nazca.sql.PageResult;
import com.nazca.usm.common.SessionConst;
import com.yz.ams.consts.AuditStateEnum;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.consts.Permissions;
import com.yz.ams.consts.ProjectConst;
import com.yz.ams.model.Vacation;
import com.yz.ams.model.VacationDetail;
import com.yz.ams.model.wrap.mgmt.VacationInfo;
import com.yz.ams.model.wrap.mgmt.VacationNote;
import com.yz.ams.rpc.mgmt.VacationMgmtService;
import com.yz.ams.server.dao.VacationDAO;
import com.yz.ams.server.dao.VacationDetailDAO;
import com.yz.ams.server.util.ConnectionUtil;
import com.yz.ams.server.util.USMSTool;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;
import javax.servlet.http.HttpSession;

/**
 * 请假管理
 *
 * @author Your Name <Song Haixiang >
 */
public class VacationMgmtServiceImpl implements VacationMgmtService {
    @HttpRPCInjection
    private HttpSession session;

    private static final Log log = LogFactory.getLog(VacationMgmtServiceImpl.class);

    @Override
    public PageResult<VacationInfo> queryVacations(String keyword, int curPage, int pageSize) throws HttpRPCException {
        List<VacationInfo> infoList = null;
        PageResult<VacationInfo> result = null;
        try {
            infoList = new ArrayList<>();
            VacationDAO dao = new VacationDAO(ConnectionUtil.getConnSrc());
            int totalCount = dao.queryCount(keyword);
            curPage = PageResult.recalculateCurPage(totalCount, curPage, pageSize);
            int start = PageResult.getFromIndex(curPage, pageSize);
            List<Vacation> list = dao.query(keyword, start, pageSize);
            VacationDetailDAO detailDao = new VacationDetailDAO(ConnectionUtil.getConnSrc());
            for (Vacation vac : list) {
                VacationInfo info = new VacationInfo();
                Date maxEndDate = null;
                info.setVacation(vac);
                List<VacationDetail> vacationDetailList = detailDao.getVacation(vac.getVacationId());
                if (!vacationDetailList.isEmpty()) {
                    if (vacationDetailList.get(0).getEndDate() != null) {
                        maxEndDate = vacationDetailList.get(0).getEndDate();
                        for (int i = 0; i < vacationDetailList.size(); i++) {
                            if (vacationDetailList.get(i).getEndDate().compareTo(maxEndDate) >= 0) {   // 判断最大值
                                maxEndDate = vacationDetailList.get(i).getEndDate();
                            }
                        }
                    }
                }
                info.setEndDate(maxEndDate);
                info.setVacationDetail(vacationDetailList);
                infoList.add(info);
            }
            result = new PageResult<>(totalCount, curPage, pageSize, infoList);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("query vacation failed", ex);
            throw new HttpRPCException("query vacation failed", ErrorCode.DB_ERROR);
        }
        return result;
    }

    @Override
    public VacationInfo createVacation(VacationInfo vacation) throws HttpRPCException {
        try {
            VacationDAO dao1 = new VacationDAO(ConnectionUtil.getConnSrc());
            VacationDetailDAO dao2 = new VacationDetailDAO(ConnectionUtil.getConnSrc());
            TransactionManager.callInTransaction(ConnectionUtil.getConnSrc(),
                    new Callable<Void>() {
                public Void call() throws Exception {
                    if (USMSTool.hasPermission(Permissions.PM, vacation.getVacation().getApplicantId()) 
                            || USMSTool.hasPermission(Permissions.CEO, vacation.getVacation().getApplicantId())) {
                        vacation.getVacation().setAuditState(AuditStateEnum.WAIT_FOR_CEO);
                    } else {
                        vacation.getVacation().setAuditState(AuditStateEnum.WAIT_FOR_PM);
                    }
                    Vacation vac = dao1.createVacation(vacation.getVacation());
                    List<VacationDetail> detail = dao2.createVacationDetail(vac.getVacationId(), vacation.getVacationDetail());
                    vacation.setVacation(vac);
                    vacation.setVacationDetail(detail);
                    return null;
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("create vacation failed", ex);
            throw new HttpRPCException("create vacation failed", ErrorCode.DB_ERROR);
        }
        return vacation;
    }

    @Override
    public VacationInfo modifyVacation(VacationInfo vacation) throws HttpRPCException {
        try {
            
            
            VacationDAO dao = new VacationDAO(ConnectionUtil.getConnSrc());
            VacationDetailDAO detailDao = new VacationDetailDAO(ConnectionUtil.getConnSrc());
            TransactionManager.callInTransaction(ConnectionUtil.getConnSrc(),
                    new Callable<Void>() {
                public Void call() throws Exception {
                    Vacation vac = dao.modifyVacation(vacation.getVacation());
                    List<VacationDetail> detail = detailDao.modifyVacationDetail(vac.getVacationId(), vacation.getVacationDetail());
                    vacation.setVacation(vac);
                    vacation.setVacationDetail(detail);
                    return null;
                }
            });
            return vacation;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("modify vacation failed", ex);
            throw new HttpRPCException("modify vacation failed", ErrorCode.DB_ERROR);
        }
    }

    @Override
    public VacationInfo deleteVacation(VacationInfo vacation) throws HttpRPCException {
        try {
            String userId = (String)session.getAttribute(SessionConst.KEY_USER_ID);
            String userName = USMSTool.transformUserIdsToUser(userId).getName();
            VacationDetailDAO detailDao = new VacationDetailDAO(ConnectionUtil.getConnSrc());
            VacationDAO dao = new VacationDAO(ConnectionUtil.getConnSrc());
            TransactionManager.callInTransaction(ConnectionUtil.getConnSrc(),
                    new Callable<Void>() {
                public Void call() throws Exception {
                    Vacation vac = vacation.getVacation();
                    String vacationId = vac.getVacationId();
                    List<VacationDetail> detailList = detailDao.deleteVacationDetail(vacationId);
                    vac.setModifierName(userName);
                    vac = dao.deleteVacation(vac);
                    vacation.setVacation(vac);
                    vacation.setVacationDetail(detailList);
                    return null;
                }
            });
            return vacation;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("delete vacation failed", ex);
            throw new HttpRPCException("delete vacation failed", ErrorCode.DB_ERROR);
        }
    }

    @Override
    public OutputStream queryCertificatePic(String certificatePicId) throws
            HttpRPCException {
        return null;
        //TODO
    }

    @Override
    public void auditCertificatePass(String vacationId) throws HttpRPCException {
        try {
            VacationDAO dao = new VacationDAO(ConnectionUtil.getConnSrc());
            dao.auditCertificatePass(vacationId);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("update certificate state pass failed", ex);
            throw new HttpRPCException("update certificate state pass failed", ErrorCode.DB_ERROR);
        }
    }

    @Override
    public void auditCertificateDeny(String vacationId) throws HttpRPCException {
        try {
            VacationDAO dao = new VacationDAO(ConnectionUtil.getConnSrc());
            dao.auditCertificateDeny(vacationId);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("update certificate state deny failed", ex);
            throw new HttpRPCException("update certificate state deny failed", ErrorCode.DB_ERROR);
        }
    }

    @Override
    public InputStream getCertificatePic(String certificatePicId) throws HttpRPCException {
        File f = new File(ProjectConst.PLATFORM_CONFIG_DIR_PATH_TWO + certificatePicId);
        try {
            return new FileInputStream(f);
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error("get certificate picture failed", ex);
            throw new HttpRPCException("get certificate picture failed", ErrorCode.DB_ERROR);
        }
    }

    @Override
    public List<VacationNote> queryVacationNotes(String vacationId) throws HttpRPCException {
        List<VacationNote> noteList = new ArrayList<>();
        String str = "";
        try {
            VacationDetailDAO detailDao = new VacationDetailDAO(ConnectionUtil.getConnSrc());
            if (vacationId.contains(",")) {
                String[] ids = vacationId.split(",");
                for (String id : ids) {
                    str = str + "'" + id + "'" + ",";
                }
                str = str.substring(0, str.length() - 1);
            } else {
                str = "'" + vacationId + "'";
            }
            return detailDao.queryHolidayNotes(str);
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("query vacation notes failed", ex);
            throw new HttpRPCException("query vacation notes failed", ErrorCode.DB_ERROR);
        }
    }
}
