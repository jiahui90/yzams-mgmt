/*
 * SyncUserOrgAgent.java
 * 
 * Copyright(c) 2007-2015 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2015-12-02 15:28:03
 */
package com.yz.ams.client.agent;


import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.usm.model.USMSOrganization;
import com.nazca.usm.model.USMSUser;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.util.StaticDataUtil;
import com.yz.ams.rpc.LoginAuthService;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author liqin
 */
public class SyncUserOrgAgent extends AbstractAgent<USMSOrganization> {

    private Map<String, USMSUser> userMap = new HashMap<String, USMSUser>();
    private Map<String, USMSOrganization> orgMap = new HashMap<String, USMSOrganization>();

    @Override
    protected USMSOrganization doExecute() throws HttpRPCException {
        LoginAuthService rServ = HttpRPC.getService(LoginAuthService.class, ClientContext.getSysServerRPCURL(), true);
        USMSOrganization org = rServ.syncUserOrg();
        recursiveOrgs(org);
        StaticDataUtil.setUserMap(userMap);
        StaticDataUtil.setOrgMap(orgMap);
        return org;
    }

    private void recursiveOrgs(USMSOrganization org) {
        orgMap.put(org.getId(), org);
        for (USMSUser user : org.getUserList()) {
            userMap.put(user.getId(), user);
        }
        if (org.getChildOrgList().size() > 0) {
            for (USMSOrganization o : org.getChildOrgList()) {
                recursiveOrgs(o);
            }
        }
    }
}
