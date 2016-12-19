/*
 * Launcher.java
 * 
 * Copyright(c) 2007-2015 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2015-03-25 10:33:53
 */
package com.yz.ams.client;

import com.nazca.ui.laf.NazcaLAFTool;
import com.nazca.usm.client.ServiceConfig;
import com.nazca.util.PropertyTool;
import com.yz.ams.consts.ProjectConst;
import java.awt.Dimension;
import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import javax.swing.JFrame;

/**
 * 系统启动器
 * @author Qiu Dongyue
 */
public class Launcher {
    //以下6个file是用于检查更新的文件
    public static File rootPath = null;
    public static File updFile = null;
    public static File oaPath = null;
    public static File updPath = null;
    public static File toolsPath = null;
    public static File toolFile = null;
    private static Properties prop = null;
    private static String usmsServerRPC;
    private static String sysServerRPC;

    public static void main(String args[]) throws Exception {
        rootPath = new File(System.getProperty("user.dir"));
        oaPath = new File(rootPath.getParent() + File.separator + "client");
        toolsPath = new File(rootPath.getParent() + File.separator + "tools");
        toolFile = new File(toolsPath, "updatetools.exe");
        updPath = new File(rootPath.getParent() + File.separator + "update");
        updFile = new File(updPath, "update.exe");

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        System.setProperty("user.timezone", "Asia/Shanghai");
        System.out.println(java.util.Calendar.getInstance().getTimeZone());
        System.out.println(System.getProperty("user.timezone"));
        System.out.println(new Date());
        NazcaLAFTool.applyNazcaLAF();
        try {
            //获取文件配置信息
            prop = PropertyTool.loadProperty(new File(ProjectConst.CONFIG_DIR_PATH), ProjectConst.CLIENT_PRJ_ID, 
                    ProjectConst.SERVER_URL_FILE_NAME);
        } catch (Exception ex) {
            ex.printStackTrace();
            //获取用户目录下的配置信息失败，则通过ClientConfig类中配置的信息创建文件（第一次加载程序时）
            prop = new Properties();
            prop.put(ProjectConst.KEY_USMS_SERVER_RPC_URL, ClientConfig.HTTPRPC_USM_SERVER);
            prop.put(ProjectConst.KEY_SYS_SERVER_RPC_URL, ClientConfig.HTTPRPC_SYS_SERVER);
            prop.put(ProjectConst.KEY_USMS_SERVER_RPC_LIST_URL, ClientConfig.HTTPRPC_USM_SERVER_LIST);
            prop.put(ProjectConst.KEY_SYS_SERVER_RPC_LIST_URL, ClientConfig.HTTPRPC_SYS_SERVER_LIST);
            //创建文件
            PropertyTool.saveProperty(prop, new File(ProjectConst.CONFIG_DIR_PATH), ProjectConst.CLIENT_PRJ_ID,
                    ProjectConst.SERVER_URL_FILE_NAME);
        }
        //通过文件获取当前服务器地址
        usmsServerRPC = prop.getProperty(ProjectConst.KEY_USMS_SERVER_RPC_URL);
        sysServerRPC = prop.getProperty(ProjectConst.KEY_SYS_SERVER_RPC_URL);
        //修改用户/.test/client/server_rpc_config.xml文件上下文为当前服务器地址
        ClientConfig.setUsmsServerRpcURL(usmsServerRPC);
        ClientConfig.setSysServerRpcURL(sysServerRPC);

        System.out.println("usms.rpc = " + usmsServerRPC);
        //url保存usm客户端
        ServiceConfig.setUsmsServerURL(new URL(usmsServerRPC));
        System.out.println("sys.rpc = " + sysServerRPC);
        
        ClientMainFrame f = new ClientMainFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置宽高
        int h = 720;
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int w = h * 16 / 10;
        if (w > screenSize.width) {
            w = screenSize.width;
        }
        f.setSize(w, h);
        //设置在屏幕中央，不写默认左上角
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
