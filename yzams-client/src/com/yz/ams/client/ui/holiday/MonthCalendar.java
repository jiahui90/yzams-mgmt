/*
 * MonthCalendar.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-07 09:09:03
 */
package com.yz.ams.client.ui.holiday;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.yz.ams.util.DateUtil;
import com.yz.ams.model.Holiday;
import java.time.LocalDate;
import java.time.YearMonth;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class MonthCalendar extends Calendar{
    public MonthCalendar(YearMonth month, Holiday info) {
        createEntires(month, info);
    }

    private void createEntires(YearMonth month, Holiday info) {
        int dayOfMonth = DateUtil.getDayOfMonthByDate(info.getHolidayDate());
        LocalDate date = month.atDay(dayOfMonth);
        Entry<?> entry = new Entry<>();
        entry.setStartDate(date);
        entry.setEndDate(date);
        entry.setTitle(info.getHolidayName());
        entry.setFullDay(true);
        addEntry(entry);
    }
}
