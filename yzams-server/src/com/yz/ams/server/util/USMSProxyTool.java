/*
 * Copyright(c) 2007-2011 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2011-06-15 16:23:27
 */
package com.yz.ams.server.util;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.usm.client.connector.USMSRPCService;
import com.nazca.usm.client.connector.USMSRPCServiceException;
import com.nazca.usm.common.AuthException;
import com.nazca.usm.model.USMSOrganization;
import com.nazca.usm.model.USMSUser;
import com.nazca.usm.module.api.rpc.USMSOrgRPCService;
import com.yz.ams.consts.ProjectConst;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询USMUser中的所有用户
 * @author zhaohongkun<zhaohongkun@yzhtech.com>
 */
public class USMSProxyTool {

    public synchronized static Map<String, USMSUser> syncUsers() {
        // 获取全部数量，分页获取用户，放到map中
        Map<String, USMSUser> map = new HashMap<>();
        USMSOrgRPCService service;
        List<USMSOrganization> orgList;
        try {
            service = HttpRPC.getService(USMSOrgRPCService.class, new URL(USMSRPCService.getInstance(ProjectConst.USMS_MODULE_ID).getConfig().getUsmsServerAddr()));
            orgList = service.getOrganizationList(0, Integer.MAX_VALUE);  
            for (USMSOrganization org : orgList) {
                List<USMSUser> userList = USMSRPCService.getInstance(ProjectConst.USMS_MODULE_ID).getUserByOrg(org.getId());
                for (USMSUser user : userList) {
                    if(!user.getId().equals("sys")){
                    USMSUser trunkedUser = new USMSUser();
                    trunkedUser.setId(user.getId());
                    trunkedUser.setName(user.getName());
                    trunkedUser.setEmployeeNumber(user.getEmployeeNumber());
                    map.put(user.getId(), trunkedUser); 
                    }
                }
            }
        } catch (USMSRPCServiceException | MalformedURLException | HttpRPCException | AuthException ex) {
            ex.printStackTrace();
        }
       return map;
    }
}
