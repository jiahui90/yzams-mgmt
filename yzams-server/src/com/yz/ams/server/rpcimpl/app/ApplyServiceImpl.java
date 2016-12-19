/*
 * ApplyServiceImpl.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 18:42:57
 */
package com.yz.ams.server.rpcimpl.app;

import com.j256.ormlite.support.ConnectionSource;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.usm.client.connector.USMSRPCService;
import com.nazca.usm.client.connector.USMSRPCServiceException;
import com.nazca.usm.model.USMSUser;
import com.nazca.util.StringUtil;
import com.yz.ams.consts.AuditStateEnum;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.consts.HolidayTypeEnum;
import com.yz.ams.model.BizTrip;
import com.yz.ams.model.Holiday;
import com.yz.ams.model.Rest;
import com.yz.ams.rpc.app.ApplyService;
import com.yz.ams.server.consts.ServiceConst;
import com.yz.ams.server.dao.BizTripDAO;
import com.yz.ams.server.dao.HolidayDAO;
import com.yz.ams.server.dao.RestDAO;
import com.yz.ams.server.util.ConnectionUtil;
import com.yz.ams.server.util.USMSTool;
import com.yz.ams.util.DateUtil;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 出差调休
 *
 * @author luoyongchang
 */
public class ApplyServiceImpl implements ApplyService {
    private List<Holiday> holidayList = null;
    /**
     * lyc
     * 申请出差
     * @param bizTrip
     * @return
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    @Override
    public BizTrip applyBizTrip(BizTrip bizTrip) throws HttpRPCException {
          if(bizTrip == null){
            throw new HttpRPCException("参数不能为空", ErrorCode.PARAMETER_ERROR);
        }
        try {
              ConnectionSource src = ConnectionUtil.getConnSrc();
            boolean i = bizTrip.isMorning();
            BizTripDAO dao = new BizTripDAO(src);
             Date startDate = bizTrip.getStartDate();
            //如果下午，开始时间设为12:00:00
            Boolean isMorning = bizTrip.isMorning();
            if(!isMorning){
                startDate = DateUtil.setHour2Date(startDate,12);
            }
            //得到假期list
            holidayList = this.getHolidayList();
            boolean isFirstDay = true;

 		if(!isFirstDay){
                    //如果时间不等于12点（如12:00:00）
                    if(DateUtil.getHourOfDate(startDate) != 12){
                        //如果不是第一天且时间为(23:59:59)，开始时间设置为00:00:00
                        startDate = DateUtil.addSecond2Date(startDate, 1);
                    }
                }
                //开始时间如果是周末或节假，跳过这一天
                while(isWeekendOrHoliday(startDate)){
                    startDate =  DateUtil.getLastHourStartTime(startDate,24);
                }
                //设置vacation detail的开始时间
                bizTrip.setStartDate(startDate);
                double days = bizTrip.getDays();
                while(days > 0){
                    //如果是休息日，跳过这一天
                    if(isWeekendOrHoliday(DateUtil.getZeroTimeOfDay(startDate))){
                       startDate =  DateUtil.getLastHourStartTime(startDate,24);
                       isFirstDay = false;
                       continue;
                    }
                    
                    days = days - 0.5;
                    startDate = DateUtil.getLastHourStartTime(startDate,12);
                }
                
                //结束时间为在下午，设置为23:59:59
                if(DateUtil.getHourOfDate(startDate) == 0){
                    startDate = DateUtil.addSecond2Date(startDate, -1);
                }
                //结束时间为在上午,设置为 00:00:00
                bizTrip.setEndDate(startDate);
                //还原开关
                isFirstDay = false;
            bizTrip.setBizTripId(UUID.randomUUID().toString());//uuid
            bizTrip.setCreateTime(new Date()); //创建申请时间
            bizTrip.setAuditState(AuditStateEnum.WAIT_FOR_CEO);//审核状态，默认二审
            dao.createBizTrip(bizTrip);
            return bizTrip;
        } catch (SQLException ex) {
            throw new HttpRPCException("申请出差失败", ErrorCode.DB_ERROR);
        }
    }
    
  //得到假期list
   private List<Holiday> getHolidayList(){
        HolidayDAO holidaydao = null;
        List<Holiday> holidayList = null;
        try {
            holidaydao = new HolidayDAO(ConnectionUtil.
                    getConnSrc());
            holidayList = holidaydao.queryAllHolidays();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return holidayList;
    }
    
   //是节假日或周末
    private boolean isWeekendOrHoliday(Date d){
        boolean isWorkday = DateUtil.isWorkingDays(d);
        //是工作日
        if(isWorkday){
            for(Holiday h : holidayList){
                Date date = h.getHolidayDate();
                if(d.getTime() == date.getTime()){
                    if(h.getHolidayType().equals(HolidayTypeEnum.HOLIDAY)){
                        return true;
                    }
                }
            }
            return false;
        }else{
            for(Holiday h : holidayList){
                Date date = h.getHolidayDate();
                if(d.getTime() == date.getTime()){
                    if(h.getHolidayType().equals(HolidayTypeEnum.WORKDAY)){
                        return false;
                    }
                }
            }
            return true;
        }
    }
    
    /**
     * lyc 
     * 获取所有员工
     * @return 返回所有员工对象
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    @Override
    public List<USMSUser> queryAllStaffs() throws HttpRPCException {
        try {
            return USMSRPCService.getInstance(ServiceConst.AMS_MODULE).
                    getAllUsersInPage(0, Integer.MAX_VALUE);
        } catch (USMSRPCServiceException ex) {
            throw new HttpRPCException(ex.getMessage(), ex.getErrcode());
        }
    }

    /**
     * lyc 
     * 审核时获取到要出差的记录信息
     * @param bizTripId
     * @return 出差信息对象
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    @Override
    public BizTrip queryBizTrip(String bizTripId) throws HttpRPCException {
         if(bizTripId == null){
            throw new HttpRPCException("参数不能为空", ErrorCode.PARAMETER_ERROR);
        }
        try {
              ConnectionSource src = ConnectionUtil.getConnSrc();
            BizTripDAO bizdao = new BizTripDAO(src);
            BizTrip bizTrip = bizdao.queryBiztrip(bizTripId);
            if(bizTrip == null){
                throw new HttpRPCException("未查询到对应的出差信息", ErrorCode.OBJECT_NOT_EXIST);
            }
            String staffIds = bizTrip.getStaffIds();
            String names = USMSTool.transformUserIdsToNames(staffIds);
            bizTrip.setStaffNames(names);
            return bizTrip;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HttpRPCException("获取出差信息失败", ErrorCode.DB_ERROR);
        } catch (USMSRPCServiceException e){
            e.printStackTrace();
            throw new HttpRPCException("获取用户信息失败", e.getErrcode());
        }
    }

    

    /**
     * lyc 申请调休
     * @param rest 调休信息对象
     * @return
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    @Override
    public Rest applyRest(Rest restss) throws HttpRPCException {
         if(restss == null){
            throw new HttpRPCException("参数不能为空", ErrorCode.PARAMETER_ERROR);
        }
        try {
              ConnectionSource src = ConnectionUtil.getConnSrc();
            RestDAO dao = new RestDAO(src);
              Date startDate = restss.getStartDate();
            //如果下午，开始时间设为12:00:00
            Boolean isMorning = restss.isMorning();
            if(!isMorning){
                startDate = DateUtil.setHour2Date(startDate,12);
            }
            //得到假期list
            holidayList = this.getHolidayList();
            boolean isFirstDay = true;

 		if(!isFirstDay){
                    //如果时间不等于12点（如12:00:00）
                    if(DateUtil.getHourOfDate(startDate) != 12){
                        //如果不是第一天且时间为(23:59:59)，开始时间设置为00:00:00
                        startDate = DateUtil.addSecond2Date(startDate, 1);
                    }
                }
                //开始时间如果是周末或节假，跳过这一天
                while(isWeekendOrHoliday(startDate)){
                    startDate =  DateUtil.getLastHourStartTime(startDate,24);
                }
                //设置vacation detail的开始时间
                restss.setStartDate(startDate);
                double days = restss.getDays();
                while(days > 0){
                    //如果是休息日，跳过这一天
                    if(isWeekendOrHoliday(DateUtil.getZeroTimeOfDay(startDate))){
                       startDate =  DateUtil.getLastHourStartTime(startDate,24);
                       isFirstDay = false;
                       continue;
                    }
                    
                    days = days - 0.5;
                    startDate = DateUtil.getLastHourStartTime(startDate,12);
                }
                
                //结束时间为在下午，设置为23:59:59
                if(DateUtil.getHourOfDate(startDate) == 0){
                    startDate = DateUtil.addSecond2Date(startDate, -1);
                }
                //结束时间为在上午,设置为 00:00:00
                restss.setEndDate(startDate);
                //还原开关
                isFirstDay = false;
            restss.setRestId(UUID.randomUUID().toString()); //UUID
            restss.setAuditState(AuditStateEnum.WAIT_FOR_CEO);//默认待二审
            restss.setCreateTime(new Date()); //创建记录时间
            dao.createRest(restss);
            return restss;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new HttpRPCException("申请调休失败", ErrorCode.DB_ERROR);
        }
    }

    /**
     * lyc 
     * 获取调休信息
     * @param restId
     * @return 调休信息对象
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    @Override
    public Rest queryRest(String restId) throws HttpRPCException {
        if(StringUtil.isEmpty(restId)){
            throw new HttpRPCException("参数不能为空", ErrorCode.PARAMETER_ERROR);
        }
        try {
              ConnectionSource src = ConnectionUtil.getConnSrc();
            RestDAO restdao = new RestDAO(src);
            Rest restss = restdao.queryRest(restId);
            if(restss == null){
                throw new HttpRPCException("未查询到对应的调休信息", ErrorCode.OBJECT_NOT_EXIST);
            }
            String staff = restss.getStaffIds();
            String names = USMSTool.transformUserIdsToNames(staff);
            restss.setStaffNames(names);
            return restss;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HttpRPCException("获取调休信息失败", ErrorCode.DB_ERROR);
        } catch (USMSRPCServiceException e){
            e.printStackTrace();
            throw new HttpRPCException("获取用户信息失败", e.getErrcode());
        }
    }
}
