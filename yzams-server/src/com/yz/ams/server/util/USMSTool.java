/*
 * USMSTool.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-03-31 10:59:10
 */
package com.yz.ams.server.util;

import com.nazca.usm.client.connector.USMSRPCService;
import com.nazca.usm.client.connector.USMSRPCServiceException;
import com.nazca.usm.model.USMSUser;
import com.yz.ams.consts.Permissions;
import com.yz.ams.consts.ProjectConst;
import com.yz.ams.server.consts.ServiceConst;

/**
 * 跟USMS相关的工具类
 * @author fred
 */
public final class USMSTool {
    private USMSTool(){}
    
    public static String transformUserIdsToNames(String staffIds) throws USMSRPCServiceException {
        String[] arr = staffIds.split(",");
        StringBuilder buf = new StringBuilder();
        for (String s : arr) {
            String name = USMSRPCService.
                    getInstance(ServiceConst.AMS_MODULE).
                    getUserByIdAndProperties(s).getName();
            buf.append(name).append(' ');
        }
        return buf.toString();
    }
    
    //曹慧英修改，通过userid获取user
    public static USMSUser transformUserIdsToUser(String userId) throws USMSRPCServiceException {

        USMSUser u = USMSRPCService.getInstance(ServiceConst.AMS_MODULE).getUserByIdAndProperties(userId);

        return u;
    }
    
    /**
      * 获得用户的权限
      * @param per
      * @param userId
      * @return 
      */
     public static boolean hasPermission(Permissions per, String userId) {
        try {
            USMSUser usmUser = USMSRPCService.getInstance(ProjectConst.USMS_MODULE_ID).getUserByIdAndProperties(userId, USMSUser.PropertyType.ROLES_AND_PERMISSIONS);
            return usmUser.hasPermission(ProjectConst.USMS_MODULE_ID, per.name());
        } catch (USMSRPCServiceException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
}
