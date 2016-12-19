/*
 * ModifyRulesAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-06 13:37:09
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.SystemParam;
import com.yz.ams.rpc.mgmt.SystemParamMgmtService;
import java.util.List;

/**
 *更改规则信息的监听器
 * @author 赵洪坤 <zhaohongkun@yzhtech.com>
 */
public class ModifyRulesAgent extends AbstractAgent<List<SystemParam>>{

    /**
     * 系统参数试题类的list
     */
    private List<SystemParam> systemParamList;

    public List<SystemParam> getSystemParamList() {
        return systemParamList;
    }

    public void setSystemParamList(List<SystemParam> systemParamList) {
        this.systemParamList = systemParamList;
    }
    
    @Override
    protected List<SystemParam> doExecute() throws HttpRPCException {
         try { 
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        List<SystemParam> result=null;
        SystemParamMgmtService service = HttpRPC.getService(SystemParamMgmtService.class, ClientContext.getSysServerRPCURL());
            result = service.modifyRules(systemParamList);
         return result;
    }
    }

    
    

