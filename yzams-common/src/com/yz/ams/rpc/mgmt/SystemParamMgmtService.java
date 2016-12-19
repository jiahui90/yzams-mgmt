/*
 * SystemParamMgmtService.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 16:46:35
 */
package com.yz.ams.rpc.mgmt;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.HttpRPCSessionTokenRequired;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.yz.ams.model.SystemParam;
import java.util.List;

/**
 * 规则设定
 *
 * @author Your Name <Song Haixiang >
 */
@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING, identifier = "com.yz.ams.server.rpcimpl.mgmt.SystemParamMgmtServiceImpl")
public interface SystemParamMgmtService {

    /**
     * 获取规则参数信息
     *
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    public List<SystemParam> queryAllParams() throws HttpRPCException;
    /**
     * 修改规则参数信息
     * @param params
     * @return
     * @throws HttpRPCException 
     */
    @HttpRPCSessionTokenRequired
    public List<SystemParam> modifyRules(List<SystemParam> params) throws HttpRPCException;

}
