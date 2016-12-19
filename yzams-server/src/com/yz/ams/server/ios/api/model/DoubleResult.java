/*
 * DoubleResult.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-12-02 09:34:52
 */
package com.yz.ams.server.ios.api.model;

import java.io.Serializable;

/**
 *
 * @author Hu Qin<huqin@yzhtech.com>
 */
public class DoubleResult implements Serializable{
    private static final long serialVersionUID = -5006518351953060553L;
    private double data;

    public DoubleResult() {
    }

    public DoubleResult(double data) {
        this.data = data;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }
    
}
