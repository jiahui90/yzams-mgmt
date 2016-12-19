/*
 * HolidayMgmtServiceImpl.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 17:30:04
 */
package com.yz.ams.server.rpcimpl.mgmt;

import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.model.Holiday;
import com.yz.ams.rpc.mgmt.HolidayMgmtService;
import com.yz.ams.server.dao.HolidayDAO;
import com.yz.ams.server.util.ConnectionUtil;
import com.yz.ams.server.util.DateTool;
import com.yz.ams.util.DateUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *假期管理
 * @author Your Name <Song Haixiang >
 */
public class HolidayMgmtServiceImpl implements HolidayMgmtService {
    private static final Log log = LogFactory.getLog(EmployeeMgmtServiceImpl.class);
    @Override
    public List<Holiday> queryHolidays(Date month) throws HttpRPCException {
        return null;
        //TODO
    }

    @Override
    public Holiday createHoliday(Holiday holiday) throws HttpRPCException {
        if(holiday == null){
                throw new HttpRPCException("参数不能为空",ErrorCode.PARAMETER_ERROR);
            }
        holiday.setHolidayId(UUID.randomUUID().toString());
        try {
            HolidayDAO holidaydao =new HolidayDAO(ConnectionUtil.
                    getConnSrc());
            holidaydao.createHoliday(holiday);
        } catch (SQLException ex) {
            Logger.getLogger(HolidayMgmtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            log.error("create holiday failed", ex);
            throw new HttpRPCException("create holiday failed", ErrorCode.DB_ERROR);
        }
        return holiday;
    }

    @Override
    public Holiday modifyHoliday(Holiday holiday) throws HttpRPCException {
        try {
            HolidayDAO holidayDao =new HolidayDAO(ConnectionUtil.
                    getConnSrc());
            holidayDao.modifyHoliday(holiday);
        } catch (SQLException ex) {
            Logger.getLogger(HolidayMgmtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            log.error("modify holiday failed", ex);
            throw new HttpRPCException("modify holiday failed", ErrorCode.DB_ERROR);
        }
        return holiday;
    }

    @Override
    public void deleteHoliday(String holidayId) throws HttpRPCException {
        
    }
     
    /**
     * caohuiying 创建多个假期
     * @param holidays
     * @return
     * @throws HttpRPCException 
     */
    @Override
    public List<Holiday> createHolidays(List<Holiday> holidays) throws HttpRPCException {
        Holiday h = null;
        
        if(holidays == null){
                throw new HttpRPCException("参数不能为空",ErrorCode.PARAMETER_ERROR);
            }
        try {
            HolidayDAO holidayDao =new HolidayDAO(ConnectionUtil.
                    getConnSrc());
            for(Holiday holiday : holidays){
                Date date = holiday.getHolidayDate();
                h = holidayDao.queryHoliday(date);
                //如果没有这个日期的假期，则创建一个假期
                if(h == null){
                    //没有这个假期，创建"
                    holiday.setHolidayId(UUID.randomUUID().toString());
                    holidayDao.createHoliday(holiday);
                    //假期类型相同，修改
                }else if(holiday.getHolidayType() == h.getHolidayType()){
                    holidayDao.modifyHoliday(holiday);
                    //如果假期类型不同，则删除假期
                }else if(holiday.getHolidayType() != h.getHolidayType()){
                    holidayDao.deleteHoliday(holiday);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(HolidayMgmtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            log.error("create holidays failed", ex);
            throw new HttpRPCException("create holidays failed", ErrorCode.DB_ERROR);
        }
        return holidays;
    }
    
    /**
     * 查询一个假期
     * @param date
     * @return
     * @throws HttpRPCException 
     */
    @Override
    public Holiday queryHoliday(Date date) throws HttpRPCException {
        Holiday holiday = null;
        try {
            HolidayDAO holidayDao =new HolidayDAO(ConnectionUtil.
                    getConnSrc());
                holiday = holidayDao.queryHoliday(date);
            
        } catch (SQLException ex) {
            Logger.getLogger(HolidayMgmtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            log.error("query holiday failed", ex);
            throw new HttpRPCException("query holiday failed", ErrorCode.DB_ERROR);
        }
        return holiday;
        
    }

    @Override
    public void deleteHoliday(Holiday holiday) throws HttpRPCException {
        if(holiday == null){
                throw new HttpRPCException("参数不能为空",ErrorCode.PARAMETER_ERROR);
            }
        try {
            HolidayDAO holidaydao =new HolidayDAO(ConnectionUtil.
                    getConnSrc());
            holidaydao.deleteHoliday(holiday);
        } catch (SQLException ex) {
            Logger.getLogger(HolidayMgmtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            log.error("delete holiday failed", ex);
            throw new HttpRPCException("delete holiday failed", ErrorCode.DB_ERROR);
        }
    }
  
    @Override
    public List<Holiday> queryHolidays() throws HttpRPCException {
        try {
            Date firstDayOfYear = DateTool.getFirstDateOfThisYear();
            Date lastDayOfYear = DateTool.getLastDateOfThisYear();
            HolidayDAO holidaydao =new HolidayDAO(ConnectionUtil.
                    getConnSrc());
            return holidaydao.queryAllHolidaysOfThisYear(firstDayOfYear, lastDayOfYear);
        } catch (SQLException ex) {
            Logger.getLogger(HolidayMgmtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            log.error("query holidays failed", ex);
            throw new HttpRPCException("query holidays failed", ErrorCode.DB_ERROR);
        }
    }
    
    /**
     * 修改多个假期
     * @param holidays
     * @return
     * @throws HttpRPCException 
     */
    @Override
    public List<Holiday> modifyHolidays(List<Holiday> holidays) throws HttpRPCException {
        try {
            HolidayDAO holidaydao =new HolidayDAO(ConnectionUtil.
                    getConnSrc());
            Holiday h = null;
            for(Holiday holiday : holidays){
                h = holidaydao.queryHolidayById(holiday.getHolidayId());
                //如果不存在，创建假期
                if(h == null){
                    holidaydao.createHoliday(holiday);
                //如果存在，修改假期
                }else{
                    h.setHolidayDate(holiday.getHolidayDate());
                    h.setHolidayName(holiday.getHolidayName());
                    h.setHolidayType(holiday.getHolidayType());
                    holidaydao.modifyHoliday(h);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(HolidayMgmtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            log.error("modify holidays failed", ex);
            throw new HttpRPCException("modify holidays failed", ErrorCode.DB_ERROR);
        }
        
        return holidays;
    }
    /**
     * 查询本月的假期
     * @return
     * @throws HttpRPCException 
     */
    public List<Holiday> queryHolidaysOfThisMonth(int year, int month) throws HttpRPCException{
        Date startDate = DateUtil.getMinDateByYearAndMonth(year, month - 1);
        Date endDate = DateUtil.getMaxDateByYearAndMonth(year, month - 1);
        List<Holiday> list = new ArrayList<>();
        try {
                HolidayDAO holidaydao =new HolidayDAO(ConnectionUtil.
                        getConnSrc());
        int i = 0;
        while (startDate.getTime() <= endDate.getTime()) {
            i++;
            Holiday info = new Holiday();
                //根据日期查询节假日
                info = holidaydao.queryHoliday(startDate);
                if(info != null){
                    list.add(info);
                }
                startDate = DateUtil.addDay2Date(startDate, 1);
            } 
            
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    /**
     * 根据id查询假期
     * @param id
     * @return
     * @throws HttpRPCException 
     */
    public Holiday queryHolidayById(String id) throws HttpRPCException{
        
        return null;
    }
}
