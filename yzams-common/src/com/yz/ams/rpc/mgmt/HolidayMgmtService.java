/*
 * HolidayMgmtService.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 17:15:50
 */
package com.yz.ams.rpc.mgmt;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.HttpRPCSessionTokenRequired;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.yz.ams.model.Holiday;
import java.util.Date;
import java.util.List;

/**
 * 假期管理
 *
 * @author Your Name <Song Haixiang >
 */
@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING, identifier = "com.yz.ams.server.rpcimpl.mgmt.HolidayMgmtServiceImpl")
public interface HolidayMgmtService {
    /**
     * 获取特定月份的假期列表
     * @param month
     * @return 
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    List<Holiday> queryHolidays(Date month) throws HttpRPCException;
    /**
     * 添加假期
     * @param holiday
     * @return
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    Holiday createHoliday(Holiday holiday) throws HttpRPCException;
    /**
     * 修改假期
     * @param holiday
     * @return
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    Holiday modifyHoliday(Holiday holiday) throws HttpRPCException;
    /**
     * 删除假期
     * @param holidayId
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    void deleteHoliday(String holidayId) throws HttpRPCException;
    
    /**
     * caohuiying 
     * 同时创建多个假期
     * @param holidays
     * @return
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    List<Holiday> createHolidays(List<Holiday> holidays) throws HttpRPCException;
    
    /**
     * caohuiying
     * 查询一个假期
     * 
     */
    Holiday queryHoliday(Date date) throws HttpRPCException;
    
     /**
      * caohuiying
     * 删除假期
     * @param holiday
     * @throws HttpRPCException 
     */
    void deleteHoliday(Holiday holiday) throws HttpRPCException;
    
    /**
     * 查询所有假期
     * @return
     * @throws HttpRPCException 
     */
    public List<Holiday> queryHolidays() throws HttpRPCException;
    
    /**
     * caohuiying
     * 修改多个假期
     * @return
     * @throws HttpRPCException 
     */
    public List<Holiday> modifyHolidays(List<Holiday> holidays) throws HttpRPCException;
    
    /**
     * 查询本月所有假期
     * @return
     * @throws HttpRPCException 
     */
    public List<Holiday> queryHolidaysOfThisMonth(int year, int month) throws HttpRPCException;
    
}
