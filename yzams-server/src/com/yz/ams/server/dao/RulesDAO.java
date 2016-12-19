/*
 * RulesDAO.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-03-16 11:10:59
 */
package com.yz.ams.server.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.yz.ams.consts.SystemParamKey;
import com.yz.ams.model.SystemParam;
import com.yz.ams.model.VacationDetail;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lyc
 */
public class RulesDAO {
    protected Dao<SystemParam, SystemParamKey> dao;
    protected ConnectionSource connSrc = null;

    public RulesDAO(ConnectionSource connSrc) throws SQLException {
        dao = DaoManager.createDao(connSrc, SystemParam.class);
    }

    
    /**
     * 获取上班时间
     * @param key
     * @return
     * @throws SQLException
     */
    public SystemParam queryForId(SystemParamKey key) throws SQLException {
        return dao.queryForId(key);
    }
    
    public Map<String,String> queryRules() throws SQLException{
        QueryBuilder builder = dao.queryBuilder();
                builder.selectRaw("param_key","param_value");
        GenericRawResults results = builder.queryRaw();
        List<String[]> rowList = results.getResults();
        Map<String,String> map = new HashMap<>();
        for (String[] strArr : rowList) {
            map.put(strArr[0],strArr[1]);
        }
        return map;
    }
    /**
     * 获得查询的规则设定数据的条数
     * @return
     * @throws SQLException 
     */
    public int queryCount() throws SQLException {
        QueryBuilder builder = dao.queryBuilder();
        return (int) builder.countOf();
    }
    /**
     * 获得查询的规则设定数据的条数
     * @return
     * @throws SQLException 
     */
    @SuppressWarnings("empty-statement")
    public List<SystemParam> queryForAll() throws SQLException {
        return dao.queryForAll();
    }
    
    /**
     * 修改
     * @param systemParam
     * @return
     * @throws SQLException 
     */
    public List<SystemParam> modifyRules(List<SystemParam> systemParam) throws SQLException {
            for (SystemParam systPa : systemParam) {
                dao.update(systPa);
            }            
            return systemParam;
    }
}
