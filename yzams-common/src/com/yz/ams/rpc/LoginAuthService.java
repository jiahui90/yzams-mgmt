/*
 * LoginAuthService.java
 * 
 * Copyright(c) 2007-2015 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2015-03-26 16:36:11
 */
package com.yz.ams.rpc;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.nazca.usm.model.USMSOrganization;

/**
 * 登录验证服务接口
 * @author Qiu Dongyue
 */
@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING,
        identifier = "com.yz.ams.server.rpcimpl.LoginAuthServiceImpl")
public interface LoginAuthService {
    
    void auth(String userId, String token) throws HttpRPCException;

    void logout() throws HttpRPCException;
    
    /**
     * 获取组织机构树
     * @return
     * @throws HttpRPCException 
     */
    USMSOrganization syncUserOrg() throws HttpRPCException;
}
