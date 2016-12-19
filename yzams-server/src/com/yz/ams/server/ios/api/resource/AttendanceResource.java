/*
 * AttendanceResource.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-12-01 14:16:58
 */
package com.yz.ams.server.ios.api.resource;

import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.model.Attendance;
import com.yz.ams.model.wrap.app.AttendanceStat;
import com.yz.ams.model.wrap.app.MemberAttendanceStat;
import com.yz.ams.model.wrap.app.TeamAttendanceStat;
import com.yz.ams.server.ios.api.AppJerseyException;
import com.yz.ams.server.ios.api.model.StaffAttendanceList;
import com.yz.ams.server.rpcimpl.app.AttendanceServiceImpl;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Hu Qin<huqin@yzhtech.com>
 */
@Path("attendance")
@Produces(MediaType.APPLICATION_JSON)
public class AttendanceResource {

    @GET
    @Path("queryTodayStaffAttendances")
    public StaffAttendanceList queryTodayStaffAttendances() {
        try {
            return new StaffAttendanceList(new AttendanceServiceImpl().queryTodayStaffAttendances());
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }

    @POST
    @Path("createOrModifyAttendance")
    @Consumes(MediaType.APPLICATION_JSON)
    public Attendance createOrModifyAttendance(Attendance attendance) {
        AttendanceServiceImpl service = new AttendanceServiceImpl();
        try {
            return service.createOrModifyAttendance(attendance);
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }

    @GET
    @Path("queryAttendanceStat")
    public AttendanceStat queryAttendanceStat(@QueryParam("userId") String userId) {
        AttendanceServiceImpl service = new AttendanceServiceImpl();
        try {
            return service.queryAttendanceStat(userId);
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }

    @GET
    @Path("queryLastMonthMyTeamMemberAttendanceStat")
    public MemberAttendanceStat queryLastMonthMyTeamMemberAttendanceStat(
           @QueryParam("userId") String userId){
        AttendanceServiceImpl service = new AttendanceServiceImpl();
        try {
            return service.queryLastMonthMyTeamMemberAttendanceStat(userId);
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }

    @GET
    @Path("queryLastMonthTeamAttendanceStat")
    public TeamAttendanceStat queryLastMonthTeamAttendanceStat() {
        AttendanceServiceImpl service = new AttendanceServiceImpl();
        try {
            return service.queryLastMonthTeamAttendanceStat();
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }
    
}
