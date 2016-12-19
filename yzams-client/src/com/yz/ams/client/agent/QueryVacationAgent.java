/*
 * QueryVacationsAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-05 17:46:26
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.sql.PageResult;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.util.FakeDataFactory;
import com.yz.ams.model.wrap.mgmt.VacationInfo;
import com.yz.ams.rpc.mgmt.VacationMgmtService;

/**
 * 请假管理
 *
 * @author Your Name <Song Haixiang >
 */
public class QueryVacationAgent extends AbstractAgent<PageResult<VacationInfo>> {
    private String keyword;
    private int curPage;
    private int pageSize;

    public void setParameters(String keyword, int curPage, int pageSize) {
        this.keyword = keyword;
        this.curPage = curPage;
        this.pageSize = pageSize;
    }

    @Override
    protected PageResult<VacationInfo> doExecute() throws HttpRPCException {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        PageResult<VacationInfo> result = null;
        if (FakeDataFactory.isFake()) {
//            result = FakeDataFactory.queryVacations(keyword, curPage, pageSize);
        } else {
            VacationMgmtService service = HttpRPC.getService(VacationMgmtService.class, ClientContext.
                    getSysServerRPCURL());
            result = service.queryVacations(keyword, curPage, pageSize);
        }
        return result;
    }
}
