/*
 * QueryEmployeeAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-05 17:46:26
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.util.FakeDataFactory;
import com.yz.ams.model.Employee;
import com.yz.ams.rpc.mgmt.EmployeeMgmtService;
import java.util.List;

/**
 * 
 *
 * @author Your Name <Zhao Hongkun >
 */
public class QueryEmployeeAgent extends AbstractAgent<List<Employee>> {
    private String keyword;

    public void setParameters(String keyword) {
        this.keyword = keyword;
    }

    @Override
    protected List<Employee> doExecute() throws HttpRPCException {
         try { 
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        List<Employee> result = null;
        if (FakeDataFactory.isFake()) {
            result = FakeDataFactory.queryAllEmployees(keyword);
        } else {
            EmployeeMgmtService service = HttpRPC.getService(EmployeeMgmtService.class, ClientContext.
                    getSysServerRPCURL());
            result = service.queryAllEmployees(keyword);
        }
        return result;
    }
}
