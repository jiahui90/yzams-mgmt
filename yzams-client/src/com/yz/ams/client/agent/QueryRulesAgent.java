/*
 * QueryRulesAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-01 10:57:53
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.util.FakeDataFactory;
import com.yz.ams.model.SystemParam;
import com.yz.ams.rpc.mgmt.SystemParamMgmtService;
import java.util.List;

/**
 *查询规则信息的监听器
 * @author 赵洪坤 <zhaohongkun@yzhtech.com>
 */
public class QueryRulesAgent extends AbstractAgent<List<SystemParam>>
{
    private List<SystemParam> result = null;

    @Override
    public void removeAllListeners() {
        super.removeAllListeners(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop() {
        super.stop(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void fireFailed(String msg, int errorCode, long seq) {
        super.fireFailed(msg, errorCode, seq); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void fireSucceeded(List<SystemParam> result, long seq) {
        super.fireSucceeded(result, seq); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void fireStart(long seq) {
        super.fireStart(seq); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AgentListener<List<SystemParam>>[] getListeners() {
        return super.getListeners(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListener(AgentListener<List<SystemParam>> listener) {
        super.removeListener(listener); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addListener(AgentListener<List<SystemParam>> listener) {
        super.addListener(listener); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<SystemParam> doExecute() throws HttpRPCException {
         try { 
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        if (FakeDataFactory.isFake()) {
            result = FakeDataFactory.queryRuless();
        } else {
            SystemParamMgmtService service = HttpRPC.getService(SystemParamMgmtService.class, ClientContext.
                    getSysServerRPCURL());
            result = service.queryAllParams();
        }
        return result;
    }
    }
    
