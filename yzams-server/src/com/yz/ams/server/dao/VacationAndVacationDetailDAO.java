/*
 * VacationAndVacationDetailDAO.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-16 10:27:41
 */
package com.yz.ams.server.dao;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.yz.ams.consts.AuditStateEnum;
import com.yz.ams.model.Vacation;
import com.yz.ams.model.VacationDetail;
import com.yz.ams.model.wrap.mgmt.VacationAndVacationDetailWrap;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class VacationAndVacationDetailDAO extends AbstractORMDAO<VacationAndVacationDetailWrap>{
    public VacationAndVacationDetailDAO(ConnectionSource connSrc) throws SQLException {
        super(connSrc, VacationAndVacationDetailWrap.class);
    }
    public List<VacationAndVacationDetailWrap> queryVacationAndDetailLis(String userId, Date start,Date end) throws SQLException {
        QueryBuilder qb1 = DaoManager.createDao(connSrc, Vacation.class).
                queryBuilder();
        QueryBuilder qb2 = DaoManager.createDao(connSrc, VacationDetail.class).
                queryBuilder();
        QueryBuilder builder = dao.queryBuilder();
        qb1.selectColumns("vacation_id")
                .where()
                .eq("applicant_id", userId)
                .and()
                .eq("audit_state",AuditStateEnum.PASS)
                .and()
                .ge("vacation_date", start)
                .and()
                .le("vacation_date", end);
        
        qb2.where()
                .ge("start_time",start)
                .and()
                .le("end_time", end)
                .and()
                .in("vacation_id", qb1);
        
        return builder.query();
    }
}
