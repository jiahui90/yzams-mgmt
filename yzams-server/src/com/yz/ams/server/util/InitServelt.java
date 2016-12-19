/*
 * InitServelt.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-05 10:20:56
 */
package com.yz.ams.server.util;

import com.j256.ormlite.table.TableUtils;
import com.yz.ams.model.Attendance;
import com.yz.ams.model.BizTrip;
import com.yz.ams.model.Holiday;
import com.yz.ams.model.PaidVacation;
import com.yz.ams.model.Rest;
import com.yz.ams.model.SystemParam;
import com.yz.ams.model.Team;
import com.yz.ams.model.TeamMember;
import com.yz.ams.model.Vacation;
import com.yz.ams.model.VacationDetail;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author Zhang Chun Nan
 */
public class InitServelt extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        Class[] clazes = {Attendance.class, BizTrip.class, Holiday.class,
             PaidVacation.class,Rest.class, SystemParam.class, Team.class, 
             TeamMember.class, Vacation.class, VacationDetail.class};
        for(Class c : clazes){
            try {
                TableUtils.createTableIfNotExists(ConnectionUtil.getConnSrc(), c);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
