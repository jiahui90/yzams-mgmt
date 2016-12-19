
/*
 * StaticDataUtil.java
 * 
 * Copyright(c) 2007-2015 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2015-09-30 12:07:45
 */
package com.yz.ams.server.util;

import com.nazca.usm.client.connector.USMSRPCService;
import com.nazca.usm.client.connector.USMSRPCServiceException;
import com.nazca.usm.model.USMSOrganization;
import com.nazca.usm.model.USMSUser;
import com.nazca.util.NazcaFormater;
import com.yz.ams.consts.ProjectConst;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Wu Jinghua
 */
public class StaticDataUtil {
    private static Map<String, USMSUser> userMap;
    private static Map<String, USMSOrganization> orgMap;
    private static Map<String, USMSUser> leaderMap;
    private static USMSOrganization rootOrg;
    private static String LEADER_ORG_ID = "22122";
    private static long INTERVAL = 30L * 60 * 1000; //30分钟
    private static long lastSyncTime = -1;

    static void sync(){
        try {
            USMSOrganization org = USMSRPCService.getInstance(ProjectConst.USMS_MODULE_ID).getOrganizationTree(true);
            Map<String, USMSUser> userM = new HashMap<>();
            Map<String, USMSOrganization> orgM = new HashMap<>();
            Map<String, USMSUser> leaderM = new HashMap<>();
            recursiveOrgs(org, userM, orgM, leaderM);
            StaticDataUtil.userMap = userM;
            StaticDataUtil.orgMap = orgM;
            StaticDataUtil.leaderMap = leaderM;
            StaticDataUtil.rootOrg = org;
            lastSyncTime = System.currentTimeMillis();
            System.out.println("usm data synced at " + NazcaFormater.getSimpleDateTimeString(new Date()));
        } catch (USMSRPCServiceException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void recursiveOrgs(USMSOrganization org, Map<String, USMSUser> userM,
            Map<String, USMSOrganization> orgM, Map<String, USMSUser> leaderM) {
        orgM.put(org.getId(), org);
        for (USMSUser user : org.getUserList()) {
            userM.put(user.getId(), user);
        }
        if(org.getId().equals(LEADER_ORG_ID)){
            for (USMSUser user : org.getUserList()) {
                USMSUser trunkedUser = new USMSUser();
                trunkedUser.setId(user.getId());
                trunkedUser.setName(user.getName());
                trunkedUser.setLoginName(user.getLoginName());
                trunkedUser.setOrg(user.getOrg());
                trunkedUser.setEmail(user.getEmail());
                trunkedUser.setMobile(user.getMobile());
                trunkedUser.setWorkPhone(user.getWorkPhone());
                trunkedUser.setJobTitle(user.getJobTitle());
                trunkedUser.setIdentityCardNumber(user.getIdentityCardNumber());
                trunkedUser.setStatus(user.getStatus());
                leaderM.put(user.getId(), trunkedUser);
            }
        }
        if (org.getChildOrgList().size() > 0) {
            for (USMSOrganization o : org.getChildOrgList()) {
                recursiveOrgs(o, userM, orgM, leaderM);
            }
        }
    }
    
    public static synchronized Map<String, USMSUser> getUserMap() {
        if(userMap == null){
            sync();
        }
        return userMap;
    }

    public static synchronized Map<String, USMSOrganization> getOrgMap() {
        if(orgMap == null){
            sync();
        }
        return orgMap;
    }

    public static synchronized Map<String, USMSUser> getLeaderMap() {
        if(leaderMap == null){
            sync();
        }
        return leaderMap;
    }

    public static synchronized USMSOrganization getRootOrg() {
        if(rootOrg == null){
            sync();
        }
        return rootOrg;
    }
}
