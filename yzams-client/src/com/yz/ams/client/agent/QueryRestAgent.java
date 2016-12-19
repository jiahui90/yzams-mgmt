/*
 * QueryRestAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-17 11:41:21
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.sql.PageResult;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.util.FakeDataFactory;
import com.yz.ams.model.Rest;
import com.yz.ams.rpc.mgmt.RestMgmtService;

/**
 *查询调休Agent类
 * @author Your Name <Song Haixiang >
 */
public class QueryRestAgent extends AbstractAgent<PageResult<Rest>> {
    private String keyword;
    private int curPage;
    private int pageSize;

    public void setParameters(String keyword, int curPage, int pageSize) {
        this.keyword = keyword;
        this.curPage = curPage;
        this.pageSize = pageSize;
    }

    @Override
    protected PageResult<Rest> doExecute() throws HttpRPCException {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        PageResult<Rest> result = null;
        if (FakeDataFactory.isFake()) {
            result = FakeDataFactory.queryRest(keyword, curPage, pageSize);
        } else {
            RestMgmtService service = HttpRPC.getService(RestMgmtService.class, ClientContext.getSysServerRPCURL());
            result = service.queryRest(keyword, curPage, pageSize);
        }
        return result;
    }
}
