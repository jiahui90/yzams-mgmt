/*
 * EmployeeMgmtServiceImpl.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-28 16:11:36
 */
package com.yz.ams.server.rpcimpl.mgmt;

import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.usm.model.USMSUser;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.model.Employee;
import com.yz.ams.rpc.mgmt.EmployeeMgmtService;
import com.yz.ams.server.util.ConnectionUtil;
import com.yz.ams.server.util.USMSProxyTool;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yz.ams.server.dao.EmployeeDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * 用户管理
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class EmployeeMgmtServiceImpl implements EmployeeMgmtService {

    private static final Log log = LogFactory.getLog(EmployeeMgmtServiceImpl.class);

    @Override
    public List<Employee> queryEmployees() throws HttpRPCException {
        List<Employee> list = null;
        EmployeeDAO dao;
        try {
            dao = new EmployeeDAO(ConnectionUtil.getConnSrc());
            list = dao.queryForAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("query attnedance failed", ex);
            throw new HttpRPCException("query attnedance failed",
                    ErrorCode.DB_ERROR);
        }
        return list;
    }


    /**
     * 同步用户表
     *
     * @return
     * @throws HttpRPCException
     */
    @Override
    public List<Employee> updateEmployeeTable() throws HttpRPCException {

        EmployeeDAO dao;
        List<Employee> employeeLis =new ArrayList<>();
        try {
            //获得USMUser中的所有用户信息
            ConnectionSource connSrc = ConnectionUtil.getConnSrc();
            dao = new EmployeeDAO(connSrc);
            TransactionManager transactionManager = new TransactionManager(connSrc);
            Callable<Void> callable = new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    Map<String, USMSUser> map = USMSProxyTool.syncUsers();
                    if(map.size() != 0){
                    //获得employee表中的所有数据放入map中
                    List<Employee> employeeAll = dao.queryForAll();
                    //删除employee表中的所有数据
                    dao.deleteAll(employeeAll);
                    //把USMUser对象封装成employee对象，放到map中
                    for (String key : map.keySet()) {
                        Employee em = new Employee();
                        em.setUserId(map.get(key).getId());
                        em.setUserName(map.get(key).getName());
                        em.setEmployeeNumber(map.get(key).getEmployeeNumber());
                        for (Employee employee : employeeAll) {
                            if (key.equals(employee.getUserId())) {
                                //考虑姓名输错的情况
                                Date emEntryTime = employee.getEntryTime();//employee表中数据的入职时间
                                em.setEntryTime(emEntryTime);
                            }
                        }
                        //把List中值更新到Employee表中
                        dao.create(em);
                        employeeLis.add(em);}
                    }
                    return null;
                }
            };
            transactionManager.callInTransaction(callable);
            return employeeLis;
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("update employee failed", ex);
            throw new HttpRPCException("update employee failed", ErrorCode.DB_ERROR);
        }
    }

    @Override
    public List<Employee> queryAllEmployees(String keyword) throws HttpRPCException {
        List<Employee> result = null;
        try {
            EmployeeDAO dao = new EmployeeDAO(ConnectionUtil.getConnSrc());
            List<Employee> list = dao.query(keyword);
            result = list;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("query employees failed", ex);
            throw new HttpRPCException("query employees failed", ErrorCode.DB_ERROR);
        }
        return result;
    }
}
