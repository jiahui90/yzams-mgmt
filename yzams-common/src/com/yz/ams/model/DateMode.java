/*
 * DateModel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-28 11:55:50
 */
package com.yz.ams.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 * 考勤子报表用的list类
 */
public class DateMode implements Serializable{
    private static final long serialVersionUID = -5460899074035069999L;
    /**
     * 考勤的报表的表头，日期行
     */
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.date);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DateMode other = (DateMode) obj;
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }
    
    
}
