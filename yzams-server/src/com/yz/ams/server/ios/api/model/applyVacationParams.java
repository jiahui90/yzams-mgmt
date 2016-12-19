/*
 * applyVacationParams.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-12-02 14:23:50
 */
package com.yz.ams.server.ios.api.model;

import com.yz.ams.model.Vacation;
import com.yz.ams.model.VacationDetail;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Hu Qin<huqin@yzhtech.com>
 */
public class applyVacationParams implements Serializable{
    private static final long serialVersionUID = -5964465025815166305L;
    private Vacation vacationInfo;
    private  List<VacationDetail> vacationDetails;

    public applyVacationParams() {
    }

    public applyVacationParams(Vacation vacationInfo,
            List<VacationDetail> vacationDetails) {
        this.vacationInfo = vacationInfo;
        this.vacationDetails = vacationDetails;
    }

    public Vacation getVacationInfo() {
        return vacationInfo;
    }

    public void setVacationInfo(Vacation vacationInfo) {
        this.vacationInfo = vacationInfo;
    }

    public List<VacationDetail> getVacationDetails() {
        return vacationDetails;
    }

    public void setVacationDetails(List<VacationDetail> vacationDetails) {
        this.vacationDetails = vacationDetails;
    }
    
}
