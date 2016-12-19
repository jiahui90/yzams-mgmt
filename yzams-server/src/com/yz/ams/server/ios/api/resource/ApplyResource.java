/*
 * ApplyResource.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-12-01 12:31:51
 */
package com.yz.ams.server.ios.api.resource;

import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.model.BizTrip;
import com.yz.ams.model.Rest;
import com.yz.ams.server.ios.api.AppJerseyException;
import com.yz.ams.server.ios.api.model.USMSUserList;
import com.yz.ams.server.rpcimpl.app.ApplyServiceImpl;
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
@Path("apply")
@Produces(MediaType.APPLICATION_JSON)
public class ApplyResource {
    /**
     * lyc
     * 申请出差
     * @param bizTrip
     * @return
     */
    @POST
    @Path("applyBizTrip")
    @Consumes(MediaType.APPLICATION_JSON)
    public BizTrip applyBizTrip(BizTrip bizTrip){
        ApplyServiceImpl service = new ApplyServiceImpl();
        
        try {
            return service.applyBizTrip(bizTrip);
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }
    
    /**
     * lyc 
     * 获取所有员工
     * @return 返回所有员工对象
     */
    @GET
    @Path("queryAllStaffs")
    public USMSUserList queryAllStaffs(){
        try {
            return new USMSUserList(new ApplyServiceImpl().queryAllStaffs()) ;
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
}
    /**
     * lyc 
     * 审核时获取到要出差的记录信息
     * @param bizTripId
     * @return 出差信息对象
     */
    @GET
    @Path("queryBizTrip")
    public BizTrip queryBizTrip(@QueryParam("bizTripId") String bizTripId) {
        ApplyServiceImpl service = new ApplyServiceImpl();
        try {
            return service.queryBizTrip(bizTripId);
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }
    
    /**
     * lyc 申请调休
     * @param restss 调休信息对象
     * @return
     */
    @POST
    @Path("applyRest")
    @Consumes(MediaType.APPLICATION_JSON)
    public Rest applyRest(Rest restss) {
        ApplyServiceImpl service = new ApplyServiceImpl();
        try {
            return service.applyRest(restss);
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }
    
    /**
     * lyc 
     * 获取调休信息
     * @param restId
     * @return 调休信息对象
     */
    @GET
    @Path("queryRest")
    public Rest queryRest(@QueryParam("restId") String restId){
         ApplyServiceImpl service = new ApplyServiceImpl();
        try {
            return service.queryRest(restId);
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }
    
    
}
