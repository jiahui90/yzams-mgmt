package com.yz.ams.consts;

/*
 * ProjectConst.java
 * 
 * Copyright(c) 2007-2015 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2015-03-25 10:38:29
 */


import java.io.File;

/**
 * 项目配置常量
 * @author Qiu Dongyue
 */
public interface ProjectConst {
    String USMS_MODULE_ID = "ams-module";//用户目录下的.usms-client下的模块配置文件MODULE_ID和此USMS_MODULE_ID值相对应
    String CONFIG_DIR_PATH = System.getProperty("user.home") + File.separator + ".yzams"; //用户目录下生成.test文件
    String CLIENT_PRJ_ID = "client";
    String SERVER_PRJ_ID = "server";
    //用户目录下.test/client下生成该文件，该文件的节点就是系统设置的一些服务器地址
    String SERVER_URL_FILE_NAME = "server_rpc_config.xml";  
    //这4个是server_rpc_config.xml下的节点
    String KEY_USMS_SERVER_RPC_URL = "USMS_SERVER_RPC_URL"; //系统设置所选的usm服务器地址
    String KEY_SYS_SERVER_RPC_URL = "SYS_SERVER_RPC_URL";   //系统设置所选的本地服务器地址
    String KEY_USMS_SERVER_RPC_LIST_URL = "USMS_SERVER_RPC_LIST_URL";
    String KEY_SYS_SERVER_RPC_LIST_URL = "SYS_SERVER_RPC_LIST_URL";
     //考勤系统项目对应的机构编码
    String ORGNUM = "BJYZXD";
    
    String PLATFORM_CONFIG_DIR_PATH = System.getProperty("user.home") + File.separator + ".yzams";
    String PLATFORM_CONFIG_DIR_PATH_TWO = System.getProperty("user.home") + File.separator + ".yzams"+File.separator +".server"+File.separator+"projPic"+File.separator;
    String TEST_CONFIG_DIR_PATH = System.getProperty("user.home") + File.separator + ".yzams" + File.separator + "test";

    
    
}
