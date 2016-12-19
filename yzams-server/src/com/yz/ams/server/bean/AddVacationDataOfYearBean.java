/*
 * AddVacationDataOfYearBean.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-08-19 09:54:38
 */
package com.yz.ams.server.bean;

import com.yz.ams.model.Employee;
import com.yz.ams.model.PaidVacation;
import com.yz.ams.server.dao.EmployeeDAO;
import com.yz.ams.server.dao.PaidVacationDAO;
import com.yz.ams.server.dao.RulesDAO;
import com.yz.ams.server.dao.VacationDetailDAO;
import com.yz.ams.server.util.ConnectionUtil;
import com.yz.ams.server.util.DateTool;
import com.yz.ams.util.DateUtil;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Stateless;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
@Stateless
public class AddVacationDataOfYearBean {
    private static boolean SYNC_DONE = true;
    private int curYear = DateTool.getYearByDate(new Date());
    private double PAID_VACATION_INNER = 5.0;
    List<Employee> employeeList = new ArrayList<>();
    private Map<String,String> usedVacationMap = new HashMap<>();
    private List<PaidVacation> vacationList = new ArrayList<>();
    EmployeeDAO employeeDAO = null;
    RulesDAO rulesDAO = null;
    PaidVacationDAO vacationDAO = null;
    
    @Schedules({
        @Schedule(hour = "5", dayOfMonth = "1", month = "1")})
    public void AddVacationDataOfYearBean() {
        System.out.println("更新年假数据！");
        try {
            if (SYNC_DONE) {
                SYNC_DONE = false;
                rulesDAO = new RulesDAO(ConnectionUtil.getConnSrc());
                usedVacationMap = getUsedPaidVacation();
                employeeDAO = new EmployeeDAO(ConnectionUtil.getConnSrc());
                vacationDAO = new PaidVacationDAO(ConnectionUtil.getConnSrc());
                employeeList = employeeDAO.queryForAll();
                for ( Employee employee : employeeList) {
                    PaidVacation paidVacation = new PaidVacation();
                    Date entryTime = employee.getEntryTime();
                    String userId = employee.getUserId();
                    Double legalVacationDaysOfLastYear = 0.0;
                    int usedVacationDays = 0;
                    int lastYear = curYear - 1;
                    
                    legalVacationDaysOfLastYear = vacationDAO.queryTotalLegalVacationDaysOfLastYear(userId,lastYear);
                        
                    for (String key : usedVacationMap.keySet()) {
                        if(key.equals(userId)){
                            usedVacationDays = Integer.valueOf(usedVacationMap.get(userId));
                        }
                    }
                    Double lostVacationDays = legalVacationDaysOfLastYear - usedVacationDays;
                    
                    paidVacation.setPvId(UUID.randomUUID().toString());
                    paidVacation.setPvYear(curYear);
                    paidVacation.setUserId(userId);
                    paidVacation.setOfficialDays(Double.valueOf(getPaidVacationByEntryTime(entryTime)));
                    paidVacation.setInnerDays(PAID_VACATION_INNER);
                    paidVacation.setModifierId("sys");
                    paidVacation.setModifierName("sys");
                    paidVacation.setModifyTime(new Date());
                    paidVacation.setLastYearDays(( lostVacationDays > 3 ) ? 3.0 : lostVacationDays); //剩余年假
                    vacationList.add(paidVacation);
                }
                vacationDAO.bacthInsertPaidVacation(vacationList);
            }
            System.out.println("更新年假数据成功！");
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("同步年假数据失败！");
        } finally {
            SYNC_DONE = true;
        }
    }
    
    /**
     * 根据入职年限计算出法定年假天数
     * @param entryYear
     * @return 
     */
    private int getPaidVacationByEntryTime(Date entryTime){
        int paidVacation = 0;
        int entryYear = 0;
        try {
            if(entryTime != null ){
                entryYear = (int)DateUtil.getDateDiffence(entryTime, new Date());
            }
            
            Map<String, String> map = new HashMap<>();
            map = rulesDAO.queryRules();
            if(entryYear > 0 && entryYear < 10) {
                paidVacation = Integer.valueOf(map.get("PAID_VACATION_ONE_YEAR"));
            }else if(entryYear >= 10 && entryYear < 20){
                paidVacation = Integer.valueOf(map.get("PAID_VACATION_TEN_YEAR"));
            }else if(entryYear >= 20){
                paidVacation = Integer.valueOf(map.get("PAID_VACATION_TWENTY_YEAR"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }catch (ParseException ex) {
            ex.printStackTrace();
        }
        return paidVacation;
    }
    
    /**
     * 得到每个员工的总请假天数
     * @return 
     */
    public Map<String,String> getUsedPaidVacation(){
        VacationDetailDAO detailDAO = null;
        Map<String,String> daysMap = null;
        try {
            detailDAO = new VacationDetailDAO(ConnectionUtil.getConnSrc());
        
            Date startDate = DateUtil.getMonthByYearAndMonth(curYear - 1, 1);
            startDate =  DateUtil.getZeroTimeOfDay(startDate);
            Date endDate = DateUtil.addSecond2Date(startDate, -1);
            daysMap = detailDAO.getUsedPaidVacation(startDate, endDate);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return daysMap;
    }
    
}

