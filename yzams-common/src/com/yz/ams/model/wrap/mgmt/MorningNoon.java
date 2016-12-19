/*
 * MorningNoon.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-15 17:27:22
 */
package com.yz.ams.model.wrap.mgmt;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 * 报表中用到的对象，有两个属性，分别存储上午和下午的出勤情况
 */
public class MorningNoon implements Serializable{
    private static final long serialVersionUID = -5460899074035069990L;
    /**
     * 上午出勤描述
     */
    private String morning;
    /**
     * 下午出勤描述
     */
    private String noon;

    public String getMorning() {
        return morning;
    }

    public String getNoon() {
        return noon;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public void setNoon(String noon) {
        this.noon = noon;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.morning);
        hash = 19 * hash + Objects.hashCode(this.noon);
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
        final MorningNoon other = (MorningNoon) obj;
        if (!Objects.equals(this.morning, other.morning)) {
            return false;
        }
        if (!Objects.equals(this.noon, other.noon)) {
            return false;
        }
        return true;
    }
    
}
