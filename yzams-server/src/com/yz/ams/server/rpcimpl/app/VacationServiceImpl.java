/*
 * VacationService.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-26 09:09:50
 */
package com.yz.ams.server.rpcimpl.app;

import com.j256.ormlite.support.ConnectionSource;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.consts.HolidayTypeEnum;
import com.yz.ams.consts.SickCertificateStateEnum;
import com.yz.ams.consts.VacationTypeEnum;
import com.yz.ams.model.Holiday;
import com.yz.ams.model.Vacation;
import com.yz.ams.model.VacationDetail;
import com.yz.ams.model.wrap.app.VacationWrap;
import com.yz.ams.rpc.app.VacationService;
import com.yz.ams.server.dao.HolidayDAO;
import com.yz.ams.server.dao.PaidVacationDAO;
import com.yz.ams.server.dao.VacationDAO;
import com.yz.ams.server.dao.VacationDetailDAO;
import com.yz.ams.server.util.ConnectionUtil;
import com.yz.ams.server.util.DateTool;
import com.yz.ams.server.util.UploadUtil;
import com.yz.ams.util.DateUtil;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 请假服务 Created by litao on 2016/1/18.
 */
public class VacationServiceImpl implements VacationService {
    private List<Holiday> holidayList = null;

    /**
     * 根据id查询一条信息 litao
     *
     * @param vacationId 请假信息ID
     * @return
     * @throws com.nazca.io.httprpc.HttpRPCException
     * @return一条请假信息的对象
     */
    @Override
    public VacationWrap queryVacation(String vacationId) throws HttpRPCException {
        VacationWrap vacationWrap = new VacationWrap();
        Vacation vacation;
        try {
            ConnectionSource src = ConnectionUtil.getConnSrc();
            VacationDetailDAO vacationDetailDao = new VacationDetailDAO(
                    src);
            VacationDAO vacationDAO = new VacationDAO(src);
            vacation = vacationDAO.queryById(vacationId);

            List<VacationDetail> vacationDetailList = vacationDetailDao.
                    queryById(vacationId);

            vacationWrap = new VacationWrap();
            vacationWrap.setVacation(vacation);
            vacationWrap.setVacationDetails(vacationDetailList);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new HttpRPCException("查询请假信息失败", ErrorCode.DB_ERROR);
        }
        return vacationWrap;
    }

    /**
     * 上传一条请假申请 litao
     *
     * @param vacation
     * @param vacationDetailsList
     * @return
     */
    @Override
    public VacationWrap applyVacation(Vacation vacation,
            List<VacationDetail> vacationDetailsList) throws HttpRPCException {
        try {
            ConnectionSource src = ConnectionUtil.getConnSrc();
            VacationWrap vacationWrap;
            VacationDAO vacationDAO = new VacationDAO(src);
            VacationDetailDAO vacationDetailDao = new VacationDetailDAO(
                    src, VacationDetail.class);

            //根据id查询出所有请假类别
            Map<String, String> yearDays = vacationDetailDao.
                    queryAttendanceStat(vacation.getApplicantId(),
                            DateTool.getFirstDateOfThisYear(), DateTool.
                            getLastDateOfThisYear());
            //插入Vacation
            vacation.setVacationId(UUID.randomUUID().toString());
            if (vacation.isHasSickType()) {
                vacation.setCertificateState(SickCertificateStateEnum.PENDING);
            }
            Date now = new Date();
            vacation.setCreateTime(now);
            vacationDAO.create(vacation);
            Date startDate = vacation.getVacationDate();
            //如果下午，开始时间设为12:00:00
            Boolean isMorning = vacation.isMorning();
            if (!isMorning) {
                startDate = DateUtil.setHour2Date(startDate, 12);
            }
            //caohuiying========================
            //得到假期list
            this.holidayList = this.getHolidayList();
            boolean isFirstDay = true;
            //caohuiying========================
            //插入VacationDetails
            VacationDetail d = null;
            //开始日期
//            Date startDate = vacation.getVacationDate();
            Double restPaidInnerDays = new PaidVacationDAO(src).
                    queryPaidInnerDays(vacation.getApplicantId(), DateTool.
                            getFirstDateOfThisYear(), DateTool.
                            getLastDateOfThisYear(), yearDays);
            for (VacationDetail data : vacationDetailsList) {
                VacationDetail vacationDetail = new VacationDetail();

                if (data.getVacationType().equals(VacationTypeEnum.ANNUAL_LEAVE)) {

                    if (restPaidInnerDays > 0) {
                        if (data.getVacationDays() <= restPaidInnerDays) {
                            vacationDetail.setVacationType(
                                    VacationTypeEnum.PAID_INNER);
                            vacationDetail.setVacationDays(data.
                                    getVacationDays());
                            startDate = setDetailObject(isFirstDay, startDate,
                                    vacationDetail);
                        } else {
                            vacationDetail.setVacationType(
                                    VacationTypeEnum.PAID_INNER);
                            vacationDetail.setVacationDays(restPaidInnerDays);
                            startDate = setDetailObject(isFirstDay, startDate,
                                    vacationDetail);
                            if (DateUtil.getHourOfDate(startDate) != 12) { //如果时间不等于12点
                                startDate = DateUtil.
                                        addSecond2Date(startDate, 1); //如果不是第一天且时间为(23:59:59)，开始时间设置为00:00:00
                            }
                            d = new VacationDetail();
                            d.setDetailId(UUID.randomUUID().toString());
                            d.setVacationId(vacation.getVacationId());
                            d.setVacationType(VacationTypeEnum.PAID_LEGAL);
                            d.setVacationDays(vacation.getTotalDays()
                                    - restPaidInnerDays);
                            d.setStartDate(startDate);
                            d.setEndDate(setDetailObject(isFirstDay, startDate,
                                    d));
                        }
                    } else {
                        vacationDetail.setVacationType(
                                VacationTypeEnum.PAID_LEGAL);
                        vacationDetail.setVacationDays(data.getVacationDays());
                        startDate = setDetailObject(isFirstDay, startDate,
                                vacationDetail);
                    }

                } else {
                    vacationDetail.setVacationType(data.getVacationType());
                    vacationDetail.setVacationDays(data.getVacationDays());
                    startDate = setDetailObject(isFirstDay, startDate,
                            vacationDetail);
                }
                vacationDetail.setDetailId(UUID.randomUUID().toString());
                vacationDetail.setVacationId(vacation.getVacationId());
                isFirstDay = false;
                vacationDetailDao.creatVacationDetail(vacationDetail);
                if (d != null) {
                    vacationDetailDao.creatVacationDetail(d);
                }
            }
            vacationWrap = new VacationWrap();
            vacationWrap.setVacation(vacation);
            vacationWrap.setVacationDetails(vacationDetailsList);
            return vacationWrap;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new HttpRPCException("请假信息保存失败", ErrorCode.DB_ERROR);
        }
    }

    private Date setDetailObject(boolean isFirstDay, Date startDate,
            VacationDetail detail) {
        if (!isFirstDay) {
            if (DateUtil.getHourOfDate(startDate) != 12) { //如果时间不等于12点
                startDate = DateUtil.addSecond2Date(startDate, 1); //如果不是第一天且时间为(23:59:59)，开始时间设置为00:00:00
            }
        }
        while (isWeekendOrHoliday(startDate)) { //开始时间如果是周末或节假，跳过这一天
            startDate = DateUtil.getLastHourStartTime(startDate, 24);
        }
        detail.setStartDate(startDate); //设置va开始时间
        double days = detail.getVacationDays();
        while (days > 0) {
            if (isWeekendOrHoliday(DateUtil.getZeroTimeOfDay(startDate))) { //如果是休息日，跳过这一天
                startDate = DateUtil.getLastHourStartTime(startDate, 24);
                isFirstDay = false;
                continue;
            }
            days = days - 0.5;
            startDate = DateUtil.getLastHourStartTime(startDate, 12);
        }
        if (DateUtil.getHourOfDate(startDate) == 0) { //结束时间为在下午，设置为23:59:59
            startDate = DateUtil.addSecond2Date(startDate, -1);
        }
        detail.setEndDate(startDate); //结束时间为在上午,设置为 00:00:00
        return startDate;
    }

    //caohuiying========================
    //是节假日或周末
    private boolean isWeekendOrHoliday(Date d) {
        boolean isWorkday = DateUtil.isWorkingDays(d);
        //是工作日
        if (isWorkday) {
            for (Holiday h : holidayList) {
                Date date = h.getHolidayDate();
                if (d.getTime() == date.getTime()) {
                    if (h.getHolidayType().equals(HolidayTypeEnum.HOLIDAY)) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            for (Holiday h : holidayList) {
                Date date = h.getHolidayDate();
                if (d.getTime() == date.getTime()) {
                    if (h.getHolidayType().equals(HolidayTypeEnum.WORKDAY)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private List<Holiday> getHolidayList() {
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

    //caohuiying========================
    /**
     * 上传病假证明 litao
     *
     * @param vacationID
     * @param imgFile
     * @param fileName
     * @return
     */
    @Override
    public Vacation uploadSickCertificate(String vacationID, InputStream imgFile,
            String fileName) throws HttpRPCException {
        Vacation vacation;
        Calendar cal = Calendar.getInstance();
        try {
            VacationDAO vacationDao = new VacationDAO(ConnectionUtil.
                    getConnSrc());
            vacation = vacationDao.queryById(vacationID);

            if (imgFile != null) {//&& !StringUtil.isEmpty(fileName)
                //上传文件
                String fileId = UploadUtil.saveProjPic(imgFile, fileName);
                vacation.setCertificatePicId(fileId);
                vacation.setCertificateState(SickCertificateStateEnum.WAIT);
                vacation.setCertificateUploadTime(cal.getTime());
                vacationDao.update(vacation);
            }
        } catch (SQLException ex) {
            throw new HttpRPCException("上传病假失败", ErrorCode.DB_ERROR);
        }
        return vacation;
    }

    /**
     * 获取病假证明图片 litao
     *
     * @param certificatePicID
     * @return
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    @Override
    public InputStream downloadSickCertificate(String certificatePicID) throws
            HttpRPCException {
        InputStream is = UploadUtil.getProjPic(certificatePicID);
        return is;
    }

    /**
     * 查询所有剩余内部年假
     *
     * @param userID
     * @return
     * @throws HttpRPCException
     */
    @Override
    public double queryPaidInnerDays(String userID) throws HttpRPCException {
        try {

            PaidVacationDAO paidVacationDAO = new PaidVacationDAO(
                    ConnectionUtil.getConnSrc());
            ConnectionSource src = ConnectionUtil.getConnSrc();
            VacationDetailDAO v = new VacationDetailDAO(src);
            //根据id查询出所有请假类别
            Map<String, String> yearDays = v.queryVacationNotDeny(userID,
                    DateTool.getFirstDateOfThisYear(), DateTool.
                    getLastDateOfThisYear());

            return paidVacationDAO.queryPaidInnerDays(userID, DateTool.
                    getFirstDateOfThisYear(), DateTool.getLastDateOfThisYear(),
                    yearDays);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new HttpRPCException("获取内部年假失败",
                    ErrorCode.DB_TRANSACTION_ERROR);
        }
    }

    /**
     * 查询所有剩余法定年假
     *
     * @param userID
     * @return
     * @throws HttpRPCException
     */
    @Override
    public double queryPaidLegalDays(String userID) throws HttpRPCException {
        try {
            PaidVacationDAO paidVacationDAO = new PaidVacationDAO(
                    ConnectionUtil.getConnSrc());
            ConnectionSource src = ConnectionUtil.getConnSrc();
            VacationDetailDAO v = new VacationDetailDAO(src);
            //根据id查询出所有请假类别
            Map<String, String> yearDays = v.queryVacationNotDeny(userID,
                    DateTool.getFirstDateOfThisYear(), DateTool.
                    getLastDateOfThisYear());
            return paidVacationDAO.queryPaidLegalDays(userID, DateTool.
                    getFirstDateOfThisYear(), DateTool.getLastDateOfThisYear(),
                    yearDays);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new HttpRPCException("获取法定年假失败",
                    ErrorCode.DB_TRANSACTION_ERROR);
        }
    }

    /**
     * 查询所有剩余年假
     *
     * @param userID
     * @return
     * @throws HttpRPCException
     */
    @Override
    public double queryPaidDays(String userID) throws HttpRPCException {
        return (this.queryPaidLegalDays(userID) + this.
                queryPaidInnerDays(userID));
    }
}
