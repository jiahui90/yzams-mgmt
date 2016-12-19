/*
 * EmployeeMgmtService.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-28 16:08:55
 */
package com.yz.ams.rpc.mgmt;

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.io.httprpc.HttpRPCSessionTokenRequired;
import com.nazca.io.httprpc.InvokingMethod;
import com.nazca.io.httprpc.ServerInvoking;
import com.yz.ams.model.Employee;
import java.util.List;

/**
 * 请假管理
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
@ServerInvoking(method = InvokingMethod.SERVICE_MAPPING, identifier
        = "com.yz.ams.server.rpcimpl.mgmt.EmployeeMgmtServiceImpl")
public interface EmployeeMgmtService {
     /**
      * 获取员工信息
      * @return
      * @throws HttpRPCException 
      */
    @HttpRPCSessionTokenRequired
    public List<Employee> queryEmployees() throws HttpRPCException;

    /**
     * 分页获取用户信息
     *
     * @param keyword
     * @param curPage
     * @param pageSize
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    List<Employee> queryAllEmployees(String keyword) throws HttpRPCException;

    /**
     * 更新员工表
     *
     * @return
     * @throws HttpRPCException
     */
    @HttpRPCSessionTokenRequired
    List<Employee> updateEmployeeTable() throws HttpRPCException;

}
