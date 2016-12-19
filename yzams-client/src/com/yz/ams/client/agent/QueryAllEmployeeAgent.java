/*
 * QueryEmployeeAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-28 16:06:31
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.Employee;
import com.yz.ams.rpc.mgmt.EmployeeMgmtService;
import java.util.List;

/**
 * 请假管理
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class QueryAllEmployeeAgent extends AbstractAgent<List<Employee>>{
    private String keyword;
    private int curPage;
    private int pageSize;

    public void setParameters(String keyword, int curPage, int pageSize) {
        this.keyword = keyword;
        this.curPage = curPage;
        this.pageSize = pageSize;
    }
    @Override
    protected List<Employee> doExecute() throws HttpRPCException {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        List<Employee> result = null;
         
            EmployeeMgmtService service = HttpRPC.getService(EmployeeMgmtService.class, ClientContext.
                    getSysServerRPCURL());
            
            result = service.queryEmployees();
        return result;
    }
}
