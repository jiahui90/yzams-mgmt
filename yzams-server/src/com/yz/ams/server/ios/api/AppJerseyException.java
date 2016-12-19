/*
 * AppJerseyException.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-12-01 12:26:48
 */
package com.yz.ams.server.ios.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Hu Qin<huqin@yzhtech.com>
 */
public class AppJerseyException extends WebApplicationException{
    public AppJerseyException(String message) {
        super(Response.status(Status.INTERNAL_SERVER_ERROR).
                entity(message).type("text/plain;charset=utf-8").build());
    }
    
    public AppJerseyException(String message, int errorCode) {
        super(Response.status(Status.INTERNAL_SERVER_ERROR).
                entity(message+"("+errorCode+")").type("text/plain;charset=utf-8").build());
    }
    
}
