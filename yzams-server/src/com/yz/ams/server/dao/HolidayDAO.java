/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yz.ams.server.dao;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.yz.ams.consts.HolidayTypeEnum;
import com.yz.ams.model.Holiday;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
/**
 *
 * @author wangcb
 */
public class HolidayDAO extends AbstractORMDAO<Holiday> {

    public HolidayDAO(ConnectionSource connSrc) throws SQLException {
        super(connSrc, Holiday.class);
    }

    /**
     * wangcb 获取当月休假天数
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws SQLException
     */
    public long queryMonthHoliday(Date startDate, Date endDate) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder
                .where()
                .eq("holiday_type", HolidayTypeEnum.HOLIDAY)
                .and()
                .eq("deleted", false)
                .and()
                .ge("holiday_date", startDate)
                .and()
                .le("holiday_date", endDate);
        return builder.countOf();
    }

    /**
     * wangcb 获取当月补班天数
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws SQLException
     */
    public long queryMonthWORKDAY(Date startDate, Date endDate) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder
                .where()
                .eq("holiday_type", HolidayTypeEnum.WORKDAY)
                .and()
                .eq("deleted", false)
                .and()
                .ge("holiday_date", startDate)
                .and()
                .le("holiday_date", endDate);
        return builder.countOf();
    }

    /**
     * 添加一个假期 caohuiying
     *
     * @param holiday
     * @throws SQLException
     */
    public void createHoliday(Holiday holiday) throws SQLException {
        dao.create(holiday);
    }

    /**
     * 修改一个假期 caohuiying
     *
     * @param holiday
     * @throws SQLException
     */
    public void modifyHoliday(Holiday holiday) throws SQLException {
        dao.update(holiday);
    }

    /**
     * 删除一个假期 caohuiying
     *
     * @param holiday
     * @throws SQLException
     */
    public void deleteHoliday(Holiday holiday) throws SQLException {
        holiday.setModifyTime(new Date());
        holiday.setDeleted(true);
        dao.update(holiday);
    }

    /**
     * caohuiying 根据日期Date查询一个假期
     * @param date
     * @return
     * @throws SQLException 
     */
    public Holiday queryHoliday(Date date) throws SQLException {
        QueryBuilder<Holiday, String> qb = dao.queryBuilder();
        qb.where()
                .eq("holiday_date", date).and().eq("deleted", false);
        return qb.queryForFirst();
    }

    /**
     * zhaohongkun 获的指定时间区间的节假日的list
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws SQLException
     */
    public List<Holiday> queryMonthHolidayList(Date startDate, Date endDate) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder
                .where()
                .eq("holiday_type", HolidayTypeEnum.HOLIDAY)
                .and()
                .eq("deleted",false)
                .and()
                .ge("holiday_date", startDate)
                .and()
                .le("holiday_date", endDate);
        return builder.query();
    }
    
    /**
     * caohuiying查询所有假期
     * @return
     * @throws SQLException
     */
    public List<Holiday> queryAllHolidays() throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder.orderBy("holiday_date", false)
                .where()
                .eq("deleted",false);
        return builder.query();
    }
    
    /**
     * caohuiying查询今年所有假期
     * @return
     * @throws SQLException
     */
    public List<Holiday> queryAllHolidaysOfThisYear(Date d1, Date d2) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder.orderBy("holiday_date", false)
                .where()
                .eq("deleted",false)
                .and()
                .ge("holiday_date", d1)
                .and()
                .le("holiday_date", d2);
        return builder.query();
    }
    
    /**
     * caohuiying根据id查询假期
     * @return
     * @throws SQLException
     */
    public Holiday queryHolidayById(String id) throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        builder.where()
                .eq("holiday_id",id);
        return (Holiday)builder.queryForFirst();
    }
}
