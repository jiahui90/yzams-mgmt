 
/*
 * AbstractORMDAO.java
 * 
 * Copyright(c) 2007-2014 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2014-10-05 15:17:19
 */

package com.yz.ams.server.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;

/**
 * 
 * @author Zhang Chun Nan
 */
public class AbstractORMDAO<T> {
    protected Dao<T, String> dao;
    protected ConnectionSource connSrc = null;

    public AbstractORMDAO(ConnectionSource connSrc, Class<T> claz) throws SQLException {
        this.connSrc = connSrc;
        dao = DaoManager.createDao(connSrc, claz);
    }
}
