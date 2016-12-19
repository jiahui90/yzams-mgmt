/*
 * VacationInfo.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-03-17 11:20:30
 */
package com.yz.ams.model.wrap.mgmt;

import com.yz.ams.model.Vacation;
import com.yz.ams.model.VacationDetail;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Zhu Mengchao
 */
public class VacationInfo implements Serializable{
    private static final long serialVersionUID = -6177178199377916647L;
    /**
     * 请假信息
     */
    private Vacation vacation;
    /**
     * 请假详情
     */
    private List<VacationDetail> vacationDetail;

    /**
     * 结束时间
     */
    private Date endDate;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public Vacation getVacation() {
        return vacation;
    }

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }

    public List<VacationDetail> getVacationDetail() {
        return vacationDetail;
    }

    public void setVacationDetail(List<VacationDetail> vacationDetail) {
        this.vacationDetail = vacationDetail;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.vacation);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VacationInfo other = (VacationInfo) obj;
        if (!Objects.equals(this.vacation, other.vacation)) {
            return false;
        }
        return true;
    }

    
}
