/*
 * AddHolidayAgent.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-07 14:19:06
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
public class AddHolidayAgent extends AbstractAgent<List<Holiday>>{
    private List<Holiday> holidays;
    
    public void setParam(List<Holiday> holidays) {
        this.holidays = holidays;
     }

    @Override
    protected List<Holiday> doExecute() throws HttpRPCException {

        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        Holiday h = null;
        HolidayMgmtService service = HttpRPC.getService(HolidayMgmtService.class, ClientContext.
                getSysServerRPCURL());
        return service.modifyHolidays(holidays);
    }
}
