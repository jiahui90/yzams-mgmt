/*
 * AuditResource.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-12-01 14:26:38
 */
package com.yz.ams.server.ios.api.resource;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.util.NazcaFormater;
import com.yz.ams.server.ios.api.AppJerseyException;
import com.yz.ams.server.ios.api.model.ApplyInfoList;
import com.yz.ams.server.ios.api.model.VoidResult;
import com.yz.ams.server.rpcimpl.app.AuditServiceImpl;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Hu Qin<huqin@yzhtech.com>
 */
@Path("audit")
@Produces(MediaType.APPLICATION_JSON)
public class AuditResource {

    @GET
    @Path("getMyApplyInfos")
    public ApplyInfoList getMyApplyInfos(@QueryParam("userId") String userId,
            @QueryParam("dateTime") String dateTime,
            @QueryParam("isAfter") boolean isAfter,
            @QueryParam("count") int count){
        AuditServiceImpl service = new AuditServiceImpl();
        try {
            return new ApplyInfoList(service.getMyApplyInfos(userId, NazcaFormater.
                    parseMilitaryDateTime(dateTime), isAfter, count));
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }

    }

    @GET
    @Path("getMyAuditInfos")
    public ApplyInfoList getMyAuditInfos(@QueryParam("userId") String userId,
            @QueryParam("dateTime") String dateTime,
            @QueryParam("isAfter") boolean isAfter,
            @QueryParam("count") int count, @QueryParam("isPM") boolean isPM)
            {
        try {
            return new ApplyInfoList(new AuditServiceImpl().getMyAuditInfos(userId, NazcaFormater.
                    parseMilitaryDateTime(dateTime), isAfter, count, isPM));
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }

    @GET
    @Path("auditVacationPass")
    public VoidResult auditVacationPass(@QueryParam("vacationId") String vacationId,
            @QueryParam("auditorId") String auditorId,
            @QueryParam("auditorName") String auditorName,
            @QueryParam("isPM") boolean isPM){
        try {
            new AuditServiceImpl().auditVacationPass(vacationId, auditorId, auditorName,
                    isPM);
            return new VoidResult();
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
        
    }

    @GET
    @Path("auditVacationDeny")
    public VoidResult auditVacationDeny(@QueryParam("vacationId") String vacationId,
            @QueryParam("auditorId") String auditorId,
            @QueryParam("auditorName") String auditorName,
            @QueryParam("isPM") boolean isPM) {
        AuditServiceImpl service = new AuditServiceImpl();
        try {
            service.auditVacationDeny(vacationId, auditorId, auditorName, isPM);
            return new VoidResult();
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
        
    }

    @GET
    @Path("auditBizTripPass")
    public VoidResult auditBizTripPass(@QueryParam("bizTripId") String bizTripId,
            @QueryParam("auditorId") String auditorId,
            @QueryParam("auditorName") String auditorName){
        AuditServiceImpl service = new AuditServiceImpl();
        try {
            service.auditBizTripPass(bizTripId, auditorId, auditorName);
            return new VoidResult();
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }

    @GET
    @Path("auditBizTripDeny")
    public VoidResult auditBizTripDeny(@QueryParam("bizTripId") String bizTripId,
            @QueryParam("auditorId") String auditorId,
            @QueryParam("auditorName") String auditorName){
        AuditServiceImpl service = new AuditServiceImpl();
        try {
            service.auditBizTripDeny(bizTripId, auditorId, auditorName);
            return new VoidResult();
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }

    @GET
    @Path("auditRestPass")
    public VoidResult auditRestPass(@QueryParam("restId") String restId, @QueryParam(
            "auditorId") String auditorId,
            @QueryParam("auditorName") String auditorName){
        AuditServiceImpl service = new AuditServiceImpl();
        try {
            service.auditRestPass(restId, auditorId, auditorName);
            return new VoidResult();
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }

    @GET
    @Path("auditRestDeny")
    public VoidResult auditRestDeny(@QueryParam("restId") String restId, @QueryParam(
            "auditorId") String auditorId,
            @QueryParam("auditorName") String auditorName){
        AuditServiceImpl service = new AuditServiceImpl();
        try {
            service.auditRestDeny(restId, auditorId, auditorName);
            return new VoidResult();
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }

}
