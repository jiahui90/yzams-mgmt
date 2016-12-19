/*
 * QueryBizTripAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-15 13:42:12
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.sql.PageResult;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.util.FakeDataFactory;
import com.yz.ams.model.BizTrip;
import com.yz.ams.rpc.mgmt.BizTripMgmtService;

/**
 *查询出差Agent类
 * @author Your Name <Song Haixiang >
 */
public class QueryBizTripAgent extends AbstractAgent<PageResult<BizTrip>> {
    private String keyword;
    private int curPage;
    private int pageSize;

    public void setParameters(String keyword, int curPage, int pageSize) {
        this.keyword = keyword;
        this.curPage = curPage;
        this.pageSize = pageSize;
    }

    @Override
    protected PageResult<BizTrip> doExecute() throws HttpRPCException {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        PageResult<BizTrip> result = null;
        if (FakeDataFactory.isFake()) {
            result = FakeDataFactory.queryBizTrip(keyword, curPage, pageSize);
        } else {
            BizTripMgmtService service = HttpRPC.
                    getService(BizTripMgmtService.class, ClientContext.getSysServerRPCURL());
            result = service.queryBizTrips(keyword, curPage, pageSize);
        }

        return result;
    }

}
