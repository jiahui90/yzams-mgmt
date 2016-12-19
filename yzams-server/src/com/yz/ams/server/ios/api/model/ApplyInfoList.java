/*
 * ApplyInfoList.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-12-02 09:28:44
 */
package com.yz.ams.server.ios.api.model;

import com.yz.ams.model.wrap.app.ApplyInfo;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Hu Qin<huqin@yzhtech.com>
 */
public class ApplyInfoList implements Serializable{
    private static final long serialVersionUID = -8528286310743843050L;
    
    private List<ApplyInfo> data;

    public ApplyInfoList() {
    }

    public ApplyInfoList(List<ApplyInfo> data) {
        this.data = data;
    }

    public List<ApplyInfo> getData() {
        return data;
    }

    public void setData(List<ApplyInfo> data) {
        this.data = data;
    }
    
    
    
}
