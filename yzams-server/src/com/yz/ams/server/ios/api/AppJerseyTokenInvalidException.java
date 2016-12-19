/*
 * AppJerseyTokenInvalidException.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-12-01 12:18:52
 */
package com.yz.ams.server.ios.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Hu Qin<huqin@yzhtech.com>
 */
public class AppJerseyTokenInvalidException extends WebApplicationException{
    public AppJerseyTokenInvalidException(String message) {
        super(Response.status(Status.FORBIDDEN).
                entity(message).type("text/plain;charset=utf-8").build());
    }
}
