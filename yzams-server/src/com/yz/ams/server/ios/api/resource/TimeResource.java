/*
 * TimeResource.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-12-01 14:23:40
 */
package com.yz.ams.server.ios.api.resource;

import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.model.SystemParam;
import com.yz.ams.server.ios.api.AppJerseyException;
import com.yz.ams.server.rpcimpl.app.TimeServiceImpl;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Hu Qin<huqin@yzhtech.com>
 */
@Path("time")
@Produces(MediaType.APPLICATION_JSON)
public class TimeResource {
    
    /**
     * lyc
     * 获取上班时间
     * @return
     * @throws HttpRPCException 
     */
    @GET
    @Path("queryAMWorkTime")
    public SystemParam queryAMWorkTime(){
        TimeServiceImpl service = new TimeServiceImpl();
        try {
            return service.queryAMWorkTime();
        } catch (HttpRPCException ex) {
            ex.printStackTrace();
            throw new AppJerseyException(ex.getMessage());
        }
    }
}
