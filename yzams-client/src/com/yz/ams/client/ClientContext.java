/*
 * ClientContext.java
 * 
 * Copyright(c) 2007-2015 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2015-03-25 10:45:05
 */
package com.yz.ams.client;

import com.nazca.usm.model.USMSUser;
import com.yz.ams.consts.Permissions;
import com.yz.ams.consts.ProjectConst;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 客户端上下文
 * @author Qiu Dongyue
 */
public class ClientContext {
    private static USMSUser user = null;
    private static String password = "";
    private static ClientMainFrame mainFrame = null;
    private static ClientContext context = null;
    private static String usmsToken = null;

    public static synchronized ClientContext getContext() {
        if (context == null) {
            context = new ClientContext();
        }
        return context;
    }

    private ClientContext() {
    }

    /**
     * 设置主窗口引用
     * @param mainFrame
     */
    public static void setMainFrame(ClientMainFrame mainFrame) {
        ClientContext.mainFrame = mainFrame;
    }

    /**
     * 获取主窗口引用
     * @return
     */
    public static ClientMainFrame getMainFrame() {
        return mainFrame;
    }

    public static URL getSysServerRPCURL() {
        try {
            return new URL(ClientConfig.getSysServerRpcURL());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static URL getUsmsServerRPCURL() {
        try {
            return new URL(ClientConfig.getUsmsServerRpcURL());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void setUser(USMSUser user) {
        ClientContext.user = user;
    }

    public static USMSUser getUser() {
        return user;
    }

    public static String getUserId() {
        return user != null ? user.getId() : null;
    }


    public static void setPassword(String password) {
        ClientContext.password = password;
    }

    public static boolean isPasswordCorrect(String password) {
        return ClientContext.password.equals(password);
    }
    
    public static String getUsmsToken() {
        return usmsToken;
    }

    public static void setUsmsToken(String usmsToken) {
        ClientContext.usmsToken = usmsToken;
    }
    
    /**
      * 判断用户是否具备指定权限
      * @param per
      * @return 
      */
     public static boolean hasPermission(Permissions per){
        if (user != null && user.getPermissionSet() != null) {
            return user.hasPermission(ProjectConst.USMS_MODULE_ID, per.name());
        } else {
            return false;
        }
     }
    
}
