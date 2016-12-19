/*
 * USMSUserList.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-12-02 09:42:38
 */
package com.yz.ams.server.ios.api.model;

import com.nazca.usm.model.USMSUser;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Hu Qin<huqin@yzhtech.com>
 */
public class USMSUserList implements Serializable{
    private static final long serialVersionUID = -5858603004125708911L;
    private List<USMSUser> data;

    public USMSUserList() {
    }

    public USMSUserList(List<USMSUser> data) {
        this.data = data;
    }

    public List<USMSUser> getData() {
        return data;
    }

    public void setData(List<USMSUser> data) {
        this.data = data;
    }
    
}
