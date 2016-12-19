/*
 * TimeServiceImpl.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-03-11 13:47:24
 */
package com.yz.ams.server.rpcimpl.app;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.consts.SystemParamKey;
import com.yz.ams.model.SystemParam;
import com.yz.ams.rpc.TimeService;
import com.yz.ams.server.dao.RulesDAO;
import com.yz.ams.server.util.ConnectionUtil;
import java.sql.SQLException;
import java.util.Calendar;

/**
 *获取系统时间
 * @author Lyc
 */
public class TimeServiceImpl implements TimeService{
    
    @Override
    public Calendar getCurServerTime() throws HttpRPCException {
       return Calendar.getInstance();
    }
    
    /**
     * lyc
     * 获取上班时间
     * @return
     * @throws HttpRPCException 
     */
    public SystemParam queryAMWorkTime()throws HttpRPCException{
         try {
             RulesDAO dao = new RulesDAO(ConnectionUtil.getConnSrc());
            return dao.queryForId(SystemParamKey.WORK_START_TIME_AM);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new HttpRPCException("数据查询失败", ErrorCode.DB_ERROR);
        }
    }
}
