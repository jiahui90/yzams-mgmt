package com.yz.ams.rpc.app;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.nazca.usm.model.USMSUser;
import com.yz.ams.model.BizTrip;
import com.yz.ams.model.Rest;
import java.util.List;

/**
 * 出差调修
 * Created by luoyongchang on 2016/1/18.
 */
@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING, identifier = "com.yz.ams.server.rpcimpl.app.ApplyServiceImpl")
public interface ApplyService {

    /**
     * 申请出差
     * @param biz 出差信息对象
     * @return 
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    public BizTrip applyBizTrip(BizTrip biz) throws HttpRPCException;

    /**
     *获取所有员工
     * @return 返回所有员工对象
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    public List<USMSUser> queryAllStaffs() throws HttpRPCException;

    /**
     * 审核时获取到要出差的记录信息
     * @param bizTripId
     * @return 出差信息对象
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    public BizTrip queryBizTrip(String bizTripId) throws HttpRPCException;
    
    /**
     * 申请调休
     * @param rest 调休信息对象
     * @return 
     * @throws com.nazca.io.httprpc.HttpRPCException 
     */
    public Rest applyRest(Rest rest) throws HttpRPCException;

    /**
     * 获取调休信息
     * @param restId
     * @return 调休信息对象
     * @throws com.nazca.io.httprpc.HttpRPCException
     */
    public Rest queryRest(String restId) throws HttpRPCException;
}
