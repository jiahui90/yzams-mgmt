/*
 * ConnectionUtil.java
 * 
 * Copyright(c) 2007-2014 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2014-10-04 23:47:37
 */
package com.yz.ams.server.util;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.nazca.util.PropertyTool;
import com.yz.ams.server.consts.ServiceConst;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties; 

/**
 *
 * @author zcn
 */
public class ConnectionUtil {
    private static JdbcPooledConnectionSource connsrc;
    private static final String KEY_CONNECTION_URL = "CONNECTION_URL";
    private static final String KEY_DB_NAME = "DB_NAME";
    private static final String KEY_DB_PWD = "DB_PWD";
    private static final String DEFAULT_CONNECTION_URL = "jdbc:mysql://172.16.100.26:3306/yzams?useUnicode=true&characterEncoding=utf-8&autoReconnect=true";
    private static final String DEFAULT_DB_NAME = "user1";
    private static final String DEFAULT_DB_PWD = "user1234";
    private static String connectionURL = null;
    private static String dbName = null;
    private static String dbPwd = null;

    static {
        connectionURL = DEFAULT_CONNECTION_URL;
        dbName = DEFAULT_DB_NAME;
        dbPwd = DEFAULT_DB_PWD;
        try {
            Properties p = PropertyTool.loadProperty(new File(ServiceConst.CONF_HOME_PATH),
                    ServiceConst.SERVER_NAME, ServiceConst.DB_CONFIG_FILE_NAME);
            connectionURL = p.getProperty(KEY_CONNECTION_URL, DEFAULT_CONNECTION_URL);
            dbName = p.getProperty(KEY_DB_NAME, DEFAULT_DB_NAME);
            dbPwd = p.getProperty(KEY_DB_PWD, DEFAULT_DB_PWD);
        } catch (IOException ex) {
            System.out.println("load pic server config failed");
            Properties p = new Properties();
            p.setProperty(KEY_CONNECTION_URL, DEFAULT_CONNECTION_URL);
            p.setProperty(KEY_DB_NAME, DEFAULT_DB_NAME);
            p.setProperty(KEY_DB_PWD, DEFAULT_DB_PWD);
            try {
                PropertyTool.saveProperty(p, new File(ServiceConst.CONF_HOME_PATH),
                        ServiceConst.SERVER_NAME, ServiceConst.DB_CONFIG_FILE_NAME);
            } catch (IOException ex1) {
                System.out.println("save car server config failed");
            }
        }
    }

    private ConnectionUtil() {
    }

    public synchronized static ConnectionSource getConnSrc() {
        if (connsrc == null) {
            try {
                connsrc = new JdbcPooledConnectionSource(connectionURL, dbName, dbPwd);
                connsrc.setMaxConnectionAgeMillis(15 * 60 * 1000);
                connsrc.setMaxConnectionsFree(32);
                connsrc.initialize();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("connect to car db failed");
            }
        }
        return connsrc;
    }

}
