/*
 * VacationWrap.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-03-03 14:05:27
 */
package com.yz.ams.model.wrap.app;

import com.yz.ams.model.Vacation;
import com.yz.ams.model.VacationDetail;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author litao <your.name at your.org>
 */
public class VacationWrap implements Serializable {
    private static final long serialVersionUID = -9062023501401836497L;
    private Vacation vacation;
    private List<VacationDetail> vacationDetails;

    public VacationWrap() {
    }


    public Vacation getVacation() {
        return vacation;
    }

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }

    public List<VacationDetail> getVacationDetails() {
        return vacationDetails;
    }

    public void setVacationDetails(List<VacationDetail> vacationDetails) {
        this.vacationDetails = vacationDetails;
    }

    public VacationWrap(Vacation vacation, List<VacationDetail> vacationDetails) {
        this.vacation = vacation;
        this.vacationDetails = vacationDetails;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.vacation);
        hash = 47 * hash + Objects.hashCode(this.vacationDetails);
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
        final VacationWrap other = (VacationWrap) obj;
        if (!Objects.equals(this.vacation, other.vacation)) {
            return false;
        }
        if (!Objects.equals(this.vacationDetails, other.vacationDetails)) {
            return false;
        }
        return true;
    }

}
