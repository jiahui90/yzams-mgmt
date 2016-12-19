/*
 * uploadSickCertificateParams.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-12-02 14:33:56
 */
package com.yz.ams.server.ios.api.model;

import java.io.InputStream;
import java.io.Serializable;

/**
 *
 * @author Hu Qin<huqin@yzhtech.com>
 */
public class uploadSickCertificateParams implements Serializable{
    private static final long serialVersionUID = -8415641849899367599L;
    private String vacationId;
    private InputStream imgFile;
    private String filename;

    public String getVacationId() {
        return vacationId;
    }

    public void setVacationId(String vacationId) {
        this.vacationId = vacationId;
    }

    public InputStream getImgFile() {
        return imgFile;
    }

    public void setImgFile(InputStream imgFile) {
        this.imgFile = imgFile;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
}
