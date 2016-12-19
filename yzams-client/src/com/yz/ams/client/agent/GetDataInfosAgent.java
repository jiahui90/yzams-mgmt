/*
 * GetDataInfosAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-07 07:52:49
 */
package com.yz.ams.client.agent;

import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.yz.ams.client.ClientContext;
import java.util.List;
import com.yz.ams.model.Holiday;
import com.yz.ams.rpc.mgmt.HolidayMgmtService;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class GetDataInfosAgent extends AbstractAgent<List<Holiday>> {
    
    private int year;
    private int month;
    
    public void setParam(int year, int month) {
        this.year = year;
        this.month = month;
    }
    
    @Override
    protected List<Holiday> doExecute() throws HttpRPCException {
        List<Holiday> list = null;
               
        HolidayMgmtService service = HttpRPC.getService(HolidayMgmtService.class, ClientContext.
                getSysServerRPCURL());
        list = service.queryHolidaysOfThisMonth(year,month);
        
        return list;
    }
}
