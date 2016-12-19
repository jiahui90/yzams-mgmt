
/*
 * StaticDataUtil.java
 * 
 * Copyright(c) 2007-2015 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2015-09-30 12:07:45
 */
package com.yz.ams.client.util;

import com.nazca.usm.model.USMSOrganization;
import com.nazca.usm.model.USMSUser;
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

    public static synchronized Map<String, USMSUser> getUserMap() {
        return userMap;
    }

    public static synchronized Map<String, USMSOrganization> getOrgMap() {
        return orgMap;
    }

    public static synchronized Map<String, USMSUser> getLeaderMap() {
        return leaderMap;
    }

    public static synchronized USMSOrganization getRootOrg() {
        return rootOrg;
    }

    public static void setLeaderMap(Map<String, USMSUser> leaderMap) {
        StaticDataUtil.leaderMap = leaderMap;
    }

    public static void setOrgMap(Map<String, USMSOrganization> orgMap) {
        StaticDataUtil.orgMap = orgMap;
    }

    public static void setRootOrg(USMSOrganization rootOrg) {
        StaticDataUtil.rootOrg = rootOrg;
    }

    public static void setUserMap(Map<String, USMSUser> userMap) {
        StaticDataUtil.userMap = userMap;
    }
}
