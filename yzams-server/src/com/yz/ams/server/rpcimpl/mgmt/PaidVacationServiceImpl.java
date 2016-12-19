/*
 * OfficialDaysMgmtServiceImpl.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 17:33:13
 */
package com.yz.ams.server.rpcimpl.mgmt;

import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.sql.PageResult;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.model.Employee;
import com.yz.ams.model.PaidVacation;
import com.yz.ams.model.wrap.app.AttendanceStat;
import com.yz.ams.model.wrap.mgmt.PaidVacationWrap;
import com.yz.ams.server.dao.PaidVacationDAO;
import com.yz.ams.server.util.ConnectionUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yz.ams.rpc.mgmt.PaidVacationService;
import com.yz.ams.server.dao.EmployeeDAO;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 年假管理
 * @author Your Name <zhaohongkun@yzhtech.com >
 */
public class PaidVacationServiceImpl implements PaidVacationService {

    private static final Log log = LogFactory.getLog(PaidVacationServiceImpl.class);
    private final ConnectionSource connSrc = ConnectionUtil.getConnSrc();

    @Override
    public PaidVacationWrap modifyPaidVacation(PaidVacationWrap paidVacation) throws HttpRPCException {
        try {
            
            PaidVacationDAO dao = new PaidVacationDAO(connSrc);
            TransactionManager.callInTransaction(connSrc,
                    new Callable<Void>() {
                public Void call() throws Exception {
                    PaidVacation vac = dao.modifyPaidVacation(paidVacation.getPaidVacationinfo());
                    paidVacation.setPaidVacationinfo(vac);
                    return null;
                }
            });
            return paidVacation;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("modify vacation failed", ex);
            throw new HttpRPCException("modify vacation failed", ErrorCode.DB_ERROR);
        }

    }

    @Override
    public PageResult<PaidVacation> queryPaidVacation(String keyword, int year)
            throws HttpRPCException {
        return null;
        //TODO
    }

    @Override
    public List<PaidVacationWrap> queryPaidVacationInfo(int year) throws HttpRPCException {

        List<PaidVacationWrap> infoList = null;
        List<PaidVacation> list = null;
        List<PaidVacationWrap> result = null;
        PaidVacationDAO dao = null;
        EmployeeDAO employeeDao = null;
        
        //本年第一天
        Date startYear = getTime(year);
        
        //本年最后一天 
        Date endYear = getTime2(year);
        try {
            dao = new PaidVacationDAO(connSrc);
            employeeDao = new EmployeeDAO(connSrc);
            list = dao.query(year);
            infoList = new ArrayList<>();
            List<Employee> EmployeeList = employeeDao.queryForAll();
            for (Employee employee : EmployeeList) {
                PaidVacationWrap wrap = new PaidVacationWrap();
                wrap.setUserName(employee.getUserName());
                wrap.setEmployeeNumber(employee.getEmployeeNumber());
                wrap.setEntryTime(employee.getEntryTime());
                AttendanceStat attendanceStat = dao.queryAttendanceStat(employee.getUserId(), year, startYear, endYear);
                wrap.setHaveTakePaidVocationYear(attendanceStat.getPaidVocationThisYear());
                wrap.setRemainPaidVacationYear(attendanceStat.getAvailablePaidVacationDays());
                for (PaidVacation paidVacation : list) {
                    if(paidVacation.getUserId().equals(employee.getUserId())){
                        wrap.setPaidVacationinfo(paidVacation);
                    }
                }
                if(wrap.getPaidVacationinfo() == null){
                    PaidVacation pv = new PaidVacation();
                    pv.setPvId(UUID.randomUUID().toString());
                    pv.setUserId(employee.getUserId());
                    pv.setPvYear(Calendar.getInstance().get(Calendar.YEAR));
                    pv.setOfficialDays(0.0);
                    pv.setInnerDays(5.0);
                    pv.setLastYearDays(0.0);
                    pv.setModifierId("sys");
                    pv.setModifierName("sys");
                    pv.setModifyTime(new Date());
                    PaidVacation pv2 = dao.createPaidVacation(pv);
                    wrap.setPaidVacationinfo(pv2);
                }
                 infoList.add(wrap);
            }
            result = infoList;
        } catch (SQLException | HttpRPCException ex) {
            ex.printStackTrace();
            log.error("query paidvacationInfo failed", ex);
            throw new HttpRPCException("query paidvacationInfo failed", ErrorCode.DB_ERROR);
        }
        return result;
    }

    @Override
    public PaidVacation modifyPaidVacation(PaidVacation paidVacation) throws
            HttpRPCException {
        return null;
        //TODO
    }
    
    /**
     * 获取指定的时间
     *
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    private Date getTime(int year) {
       Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.DAY_OF_YEAR, 1);     
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
    /**
     * 获取指定的时间2
     *
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    private Date getTime2(int year) {
         Calendar  c = Calendar.getInstance();
        c.set(Calendar.YEAR, year + 1);
        c.set(Calendar.DAY_OF_YEAR, 1);       
        c.add(Calendar.DAY_OF_YEAR, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }
}
