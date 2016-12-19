package com.yz.ams.server.util;
 
/*
 * UploadUtil.java
 * 
 * Copyright(c) 2007-2013 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2013-11-08 16:11:56
 */

import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.util.PropertyTool;
import com.nazca.util.StringUtil;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.consts.ProjectConst;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

/**
 *
 * @author liuyizhe
 */
public class UploadUtil {

    public static final String UPLOAD_CONFIG_FILE_NAME = "upload-config.xml";

    private static final String PROJ_PIC_PATH_KEY = "projPicPath";
    private static final String PROJ_PIC_PATH = "projPic";

    private static Properties prop = new Properties();

    static {
        try {
            prop = PropertyTool.loadProperty(new File(
                    ProjectConst.PLATFORM_CONFIG_DIR_PATH),
                    ProjectConst.SERVER_PRJ_ID, UPLOAD_CONFIG_FILE_NAME);
        } catch (IOException ex) {
            try {
                prop = new Properties();
                prop.setProperty(PROJ_PIC_PATH_KEY,                              //键
                        ProjectConst.PLATFORM_CONFIG_DIR_PATH
                        + File.separator + "." + ProjectConst.SERVER_PRJ_ID      //路径
                        + File.separator + PROJ_PIC_PATH);
                PropertyTool.saveProperty(prop, new File(ProjectConst.PLATFORM_CONFIG_DIR_PATH),
                        ProjectConst.SERVER_PRJ_ID, UPLOAD_CONFIG_FILE_NAME);
            } catch (IOException e) {
            }
        }
    }

    public static String saveProjPic(InputStream is, String fileName) throws
            HttpRPCException {
        return saveFile(is, fileName, PROJ_PIC_PATH_KEY);
    }

    public static InputStream getProjPic(String id) throws HttpRPCException {
        return getFile(id, PROJ_PIC_PATH_KEY);
    }



    //savePictureFile
    private static String saveFile(InputStream is, String fileName, String pathKey) throws HttpRPCException {
        String picSavePath = prop.getProperty(pathKey);//图片保存位置
        String wholePath = "";//完整图片路径
        String finalFileName = "";//上传后的文件名称
        FileOutputStream fos = null;
        try {
            if (StringUtil.isEmpty(fileName)) {
                throw new HttpRPCException("文件未找到", ErrorCode.FILE_NOT_FOUND);
            } else {
                String UUIDStr = UUID.randomUUID().toString();
                finalFileName = UUIDStr + fileName.substring(fileName.
                        lastIndexOf("."));
                wholePath = picSavePath + File.separator + finalFileName;
                File file = new File(wholePath);
                File path = new File(picSavePath);
                if (!path.exists() && !path.isDirectory()) {
                    path.mkdir();
                }
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        throw new HttpRPCException("文件创建失败",
                                ErrorCode.FILE_NOT_FOUND);
                    }
                }
                fos = new FileOutputStream(wholePath);
                byte[] buf = new byte[1024];
                while ((is.read(buf)) > 0) {
                    fos.write(buf);
                }
            }
            return finalFileName;
        } catch (HttpRPCException | IOException t) {
            throw new HttpRPCException("文件未找到", ErrorCode.FILE_NOT_FOUND);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    throw new HttpRPCException("文件读取失败",
                            ErrorCode.FILE_NOT_FOUND);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    throw new HttpRPCException("文件读取失败",
                            ErrorCode.FILE_NOT_FOUND);
                }
            }
        }
    }

    private static InputStream getFile(String id, String pathKey) throws HttpRPCException {
        String picSavePath = prop.getProperty(pathKey);//图片保存位置
        File file = new File(picSavePath , File.separator + id);
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException t) {      
            throw new HttpRPCException("文件未找到", ErrorCode.FILE_NOT_FOUND);
        }
    }
}
