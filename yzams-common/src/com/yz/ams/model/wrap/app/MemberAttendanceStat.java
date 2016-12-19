/*
 * MemberAttendanceStat.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 11:06:06
 */
package com.yz.ams.model.wrap.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 组员出勤统计
 * @author luoyongchang
 */
public class MemberAttendanceStat implements Serializable{
    private static final long serialVersionUID = -4798953075739178322L;
    
    private double legalAttendanceDays;  //法定出勤日
    /**
     * 组员出勤统计详细
     */
    private List<MemberAttendanceStatDetail> memberStatDetails;

    
    
    public MemberAttendanceStat(double legalAttendanceDays,
            List<MemberAttendanceStatDetail> memberStatDetails) {
        this.legalAttendanceDays = legalAttendanceDays;
        this.memberStatDetails = memberStatDetails;
    }

    public MemberAttendanceStat() {
    }

    
    public double getLegalAttendanceDays() {
        return legalAttendanceDays;
    }

    public void setLegalAttendanceDays(double legalAttendanceDays) {
        this.legalAttendanceDays = legalAttendanceDays;
    }

    public List<MemberAttendanceStatDetail> getMemberStatDetails() {
        return memberStatDetails;
    }

    public void setMemberStatDetails(
            List<MemberAttendanceStatDetail> memberStatDetails) {
        this.memberStatDetails = memberStatDetails;
    }
    
    public void addMemberStatDetails(MemberAttendanceStatDetail memberAttendanceStatDetail){
        if (memberStatDetails==null) {
            memberStatDetails = new ArrayList<MemberAttendanceStatDetail >();
        }
        memberStatDetails.add(memberAttendanceStatDetail);
    }
}
