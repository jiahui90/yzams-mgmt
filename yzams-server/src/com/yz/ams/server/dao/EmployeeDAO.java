/*
 * EmployeeDAO.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-16 10:48:40
 */
package com.yz.ams.server.dao;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.nazca.util.StringUtil;
import com.yz.ams.model.Employee;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 赵洪坤 <zhaohongkun@yzhtech.com>
 */
public class EmployeeDAO extends AbstractORMDAO<Employee>{
    public EmployeeDAO(ConnectionSource connSrc) throws SQLException {
        super(connSrc, Employee.class);
    }
    /**
     * 查询员工信息
     * @return
     * @throws SQLException 
     */
    public List<Employee> queryEmpoyees() throws SQLException {
       QueryBuilder builder =  dao.queryBuilder();
        builder.selectColumns("user_id","user_name","employee_number");
        return builder.query();
       
    }
    
    /**
     * 通过员工的ID查询员工信息
     * @return
     * @throws SQLException 
     */
    public List<Employee> queryEmpoyeesByIds(List<String> ids) throws SQLException {
       QueryBuilder builder =  dao.queryBuilder();
        builder.selectColumns("user_name","employee_number","entry_time")
                .where()
                .in("user_id", ids);
        return builder.query();
       
    }
    
   /**
    * 根据名字查询员工信息
    * @param keyword
    * @return
    * @throws SQLException 
    */
    public List<Employee> queryEmpoyeesByName(String keyword) throws SQLException {
       QueryBuilder builder =  dao.queryBuilder();
        builder.where().like("user_name", "%" + keyword + "%");
        return builder.query();
       
    }
    /**
     * 查询员工入职时间,工号
     * @param userId
     * @return
     * @throws SQLException 
     */
    public List<Employee> queryEmpoyeesByUserId( String userId) throws SQLException {
       QueryBuilder builder =  dao.queryBuilder();
        builder.selectColumns("user_name","employee_number","entry_time")
                .where()
                .eq("user_id", userId);
        return builder.query();
       
    }
    /**
     * 分页查询用户
     * @param keyword
     * @param start
     * @param count
     * @return
     * @throws SQLException
     */
    public List<Employee> query(String keyword) throws
            SQLException {
        QueryBuilder<Employee, String> builder = dao.queryBuilder();
        if (!StringUtil.isEmpty(keyword)) {
            builder.where().like("user_name", "%" + keyword + "%");   
        }
        builder.orderBy("employee_number", true);
        return builder.query(); 
    }
     /**
     *查询数量
     * @param keyword
     * @return
     * @throws java.sql.SQLException
     */
    public int queryCount(String keyword) throws Exception {

        QueryBuilder builder = dao.queryBuilder();
        if (!StringUtil.isEmpty(keyword)) {
            builder.where().like("user_name", "%" + keyword + "%");   
        }
        builder.orderBy("employee_number", true);
        return (int) builder.countOf();

    }
    /**
     * 删除全部
     * @param emLis
     */
    public void deleteAll(List<Employee> emLis) throws SQLException
    {
        dao.delete(emLis);
    }

    
    public Employee queryEmployeeById(String id) throws SQLException{
        QueryBuilder builder =  dao.queryBuilder();
        builder.where().eq("user_id", id);
        return (Employee)builder.queryForFirst();
    } 
     /**
     *
     * 插入一条请假数据 litao
     *
     * @param employee
     * @throws java.sql.SQLException
     */
    public Employee create(Employee employee) throws SQLException {
        dao.create(employee);
        return employee;
    }
    /**
     * 查询所有用户的信息
     * @return
     * @throws SQLException 
     */
    public List<Employee> queryForAll() throws SQLException {
        return dao.queryForAll();
    }
}
