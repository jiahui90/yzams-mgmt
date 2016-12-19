/*
 * VacationResource.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-12-01 14:47:22
 */
package com.yz.ams.server.ios.api.resource;

import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.model.Vacation;
import com.yz.ams.model.wrap.app.VacationWrap;
import com.yz.ams.server.ios.api.AppJerseyException;
import com.yz.ams.server.ios.api.model.DoubleResult;
import com.yz.ams.server.ios.api.model.applyVacationParams;
import com.yz.ams.server.ios.api.model.uploadSickCertificateParams;
import com.yz.ams.server.rpcimpl.app.VacationServiceImpl;
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
@Path("vacation")
@Produces(MediaType.APPLICATION_JSON)
public class VacationResource{

    @GET
    @Path("queryVacation")
    public VacationWrap queryVacation(@QueryParam("vacationId") String vacationId){
        VacationServiceImpl service = new VacationServiceImpl();
        try {
            return service.queryVacation(vacationId);
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }

    @POST
    @Path("applyVacation")
    @Consumes(MediaType.APPLICATION_JSON)
    public VacationWrap applyVacation(applyVacationParams applyVacationParams) {
        VacationServiceImpl service = new VacationServiceImpl();
        try {
            return service.applyVacation(applyVacationParams.getVacationInfo(), applyVacationParams.getVacationDetails());
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }

    @POST
    @Path("uploadSickCertificate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Vacation uploadSickCertificate(uploadSickCertificateParams uploadSickCertificateParams){
        VacationServiceImpl service = new VacationServiceImpl();
        try {
            return service.uploadSickCertificate(uploadSickCertificateParams.getVacationId(), uploadSickCertificateParams.getImgFile(), uploadSickCertificateParams.getFilename());
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }

    @GET
    @Path("queryPaidInnerDays")
    public DoubleResult queryPaidInnerDays(@QueryParam("userID")String userID) {
        VacationServiceImpl service = new VacationServiceImpl();
        try {
            return new DoubleResult(service.queryPaidInnerDays(userID));
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }

    @GET
    @Path("queryPaidLegalDays")
    public DoubleResult queryPaidLegalDays(@QueryParam("userID")String userID){
        VacationServiceImpl service = new VacationServiceImpl();
        try {
            return new DoubleResult(service.queryPaidLegalDays(userID));
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }

    @GET
    @Path("queryPaidDays")
    public DoubleResult queryPaidDays(@QueryParam("userID") String userID){
        try {
            return new DoubleResult(new VacationServiceImpl().queryPaidDays(userID));
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }
    
}
