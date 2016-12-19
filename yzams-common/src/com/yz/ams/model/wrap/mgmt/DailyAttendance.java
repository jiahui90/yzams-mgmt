/*
 * DailyAttendance.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-17 13:42:40
 */
package com.yz.ams.model.wrap.mgmt;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class DailyAttendance implements Serializable{
    private static final long serialVersionUID = -5905567296228508235L;
    private Date date;
    private MorningNoon morningNoon;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MorningNoon getMorningNoon() {
        return morningNoon;
    }

    public void setMorningNoon(MorningNoon morningNoon) {
        this.morningNoon = morningNoon;
    }
    
    
}
