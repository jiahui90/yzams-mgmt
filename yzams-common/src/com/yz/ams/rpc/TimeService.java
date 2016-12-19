/*
 * TimeService.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-03-03 14:16:01
 */
package com.yz.ams.rpc;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.yz.ams.model.SystemParam;
import java.util.Calendar;

/**
 * 时间接口
 * @author Lyc
 */

@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING, identifier = "com.yz.ams.server.rpcimpl.app.TimeServiceImpl")
public interface TimeService {
    public Calendar getCurServerTime() throws HttpRPCException;
    
     public SystemParam queryAMWorkTime()throws HttpRPCException;
    
}
