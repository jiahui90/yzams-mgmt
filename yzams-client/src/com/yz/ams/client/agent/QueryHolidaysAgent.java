/*
 * QueryHolidaysAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-07-05 11:58:20
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import com.yz.ams.model.Holiday;
import com.yz.ams.rpc.mgmt.HolidayMgmtService;
import java.util.List;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class QueryHolidaysAgent extends AbstractAgent<List<Holiday>>{
    
    List<Holiday> list = null;
    @Override
    protected List<Holiday> doExecute() throws HttpRPCException {

        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        HolidayMgmtService service = HttpRPC.getService(HolidayMgmtService.class, ClientContext.
                getSysServerRPCURL());
        list = service.queryHolidays();
       
        return list;
    }
}
