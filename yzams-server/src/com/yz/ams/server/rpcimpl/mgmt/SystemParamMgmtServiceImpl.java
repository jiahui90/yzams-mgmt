/*
 * SystemParamMgmtServiceImpl.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-05 12:24:24
 */
package com.yz.ams.server.rpcimpl.mgmt;

import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.model.SystemParam;
import com.yz.ams.rpc.mgmt.SystemParamMgmtService;
import com.yz.ams.server.dao.RulesDAO;
import com.yz.ams.server.util.ConnectionUtil;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *规则设定
 * @author Your Name <Song Haixiang >
 */
public class SystemParamMgmtServiceImpl implements SystemParamMgmtService {

    private static final Log log = LogFactory.getLog(PaidVacationServiceImpl.class);
    private ConnectionSource connSrc = ConnectionUtil.getConnSrc();
    /**
     * 查询所有的规则信息
     * @return
     * @throws HttpRPCException 
     */
    @Override
    public List<SystemParam> queryAllParams() throws HttpRPCException {
        List<SystemParam> list =null;
        try {
            RulesDAO rulesDAO = new RulesDAO(connSrc);
            list = rulesDAO.queryForAll();     
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("query all params failed", ex);
            throw new HttpRPCException("query all params failed", ErrorCode.DB_ERROR);
        }
        return list;
    }
    
    /**
     * 修改规则信息
     * @param params
     * @return
     * @throws HttpRPCException 
     */
    @Override
    public List<SystemParam> modifyRules(List<SystemParam> params) throws
            HttpRPCException {
         try {
            ConnectionSource connSrc = ConnectionUtil.getConnSrc();
            RulesDAO dao = new RulesDAO(connSrc);
            TransactionManager.callInTransaction(connSrc,
                    new Callable<Void>() {
                        public Void call() throws Exception {
                            dao.modifyRules(params);
                            return null;
                        }
                    });
            return params;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("modify rules failed", ex);
            throw new HttpRPCException("modify rules failed", ErrorCode.DB_ERROR);
        }
    }   
}
