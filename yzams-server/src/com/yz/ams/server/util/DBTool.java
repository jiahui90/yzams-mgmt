/*
 * DBTool.java
 * 
 * Copyright(c) 2007-2013 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2013-11-04 17:39:51
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Chen Jianan
 */
public class DBTool {
    private static Log log = LogFactory.getLog(DBTool.class);

    private DBTool() {
    }

    public static void closeConnection(Connection conn, Statement stmt,
            ResultSet rs) {
        closeConnection(rs, stmt);
        closeConnection(conn);
    }

    public static void closeConnection(ResultSet rs, Statement stmt) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                log.warn("Cannot close result set", ex);
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                log.warn("Cannot close statement", ex);
            }
        }
    }

    public static void closeConnection(Statement stmt) {
        closeConnection(null, stmt);
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                log.warn("Cannot close connection", ex);
            }
        }
    }

    public static void rollbackConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
                log.warn("Cannot roll back", ex);
            }
        }
    }
}
