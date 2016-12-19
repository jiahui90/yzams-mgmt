package com.yz.ams.client;

/*
 * ServerSelectionPanel.java
 * 
 * Copyright(c) 2007-2015 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2015-03-25 10:47:34
 */


import com.nazca.ui.NInternalDiag;
import com.nazca.ui.model.SimpleObjectListModel;
import com.nazca.usm.client.ServiceConfig;
import com.nazca.util.PropertyTool;
import com.yz.ams.consts.ProjectConst;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;



/**
 * 服务器选择面板
 * @author Qiu Dongyue
 */
public class ServerSelectionPanel extends javax.swing.JPanel {
    private SimpleObjectListModel<String> usmModel = null;
    private SimpleObjectListModel<String> sysModel = null;

    /**
     * Creates new form ServerSelectionPanel
     */
    public ServerSelectionPanel() {
        initComponents();
        sysModel = new SimpleObjectListModel<>();
        sysComBox.setModel(sysModel);
        usmModel = new SimpleObjectListModel<>();
        usmComBox.setModel(usmModel);
        initData();
        oKCancelPanel1.addOKCancelListener(new OKCancelPanelListener() {

            @Override
            public void onOKClicked() {
                String curUsmServer = usmModel.getSelectedItem();
                String curSysServer = sysModel.getSelectedItem();
                Properties p;
                try {
                    p = PropertyTool.loadProperty(new File(
                            ProjectConst.CONFIG_DIR_PATH),
                            ProjectConst.CLIENT_PRJ_ID,
                            ProjectConst.SERVER_URL_FILE_NAME);
                    p.put(ProjectConst.KEY_USMS_SERVER_RPC_URL, curUsmServer);
                    p.put(ProjectConst.KEY_SYS_SERVER_RPC_URL, curSysServer);
                    int usmCount = usmModel.getSize();
                    StringBuilder sb1 = new StringBuilder();
                    for (int i = 0; i < usmCount; i++) {
                        String s = usmModel.get(i);
                        sb1.append(s).append(";");
                    }
                    if (!usmModel.getObjectList().contains(curUsmServer)) {
                        sb1.append(curUsmServer);
                    }
                    p.put(ProjectConst.KEY_USMS_SERVER_RPC_LIST_URL, sb1.toString());
                    int tmapCount = sysModel.getSize();
                    StringBuilder sb2 = new StringBuilder();
                    for (int i = 0; i < tmapCount; i++) {
                        String s = sysModel.get(i);
                        sb2.append(s).append(";");
                    }
                    if (!sysModel.getObjectList().contains(curSysServer)) {
                        sb2.append(curSysServer);
                    }
                    p.put(ProjectConst.KEY_SYS_SERVER_RPC_LIST_URL, sb2.toString());
                    PropertyTool.saveProperty(p, new File(
                            ProjectConst.CONFIG_DIR_PATH),
                            ProjectConst.CLIENT_PRJ_ID,
                            ProjectConst.SERVER_URL_FILE_NAME);
                    //根据系统设置所选的地址 修改用户/.test/client/server_rpc_config.xml文件上下文
                    ClientConfig.setUsmsServerRpcURL(curUsmServer);
                    ClientConfig.setSysServerRpcURL(curSysServer);
                    //url保存usm客户端
                    ServiceConfig.setUsmsServerURL(new URL(curUsmServer));
                    //关闭当前窗口
                    NInternalDiag diag = NInternalDiag.findNInternalDiag(ServerSelectionPanel.this);
                    if (diag != null) {
                        diag.hideDiag();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onCancelClicked() {
                NInternalDiag diag = NInternalDiag.findNInternalDiag(ServerSelectionPanel.this);
                if (diag != null) {
                    diag.hideDiag();
                }
            }
        });
        
    }

    private void initData() {
        try {
            Properties p = PropertyTool.loadProperty(new File(
                    ProjectConst.CONFIG_DIR_PATH),
                    ProjectConst.CLIENT_PRJ_ID,
                    ProjectConst.SERVER_URL_FILE_NAME);
            String usmStr = p.getProperty(ProjectConst.KEY_USMS_SERVER_RPC_LIST_URL);
            String[] usmsList = usmStr.split(";");
            for (int i = 0; i < usmsList.length; i++) {
                String string = usmsList[i];
                usmModel.add(string);
            }
            usmModel.setSelectedItem(ClientConfig.getUsmsServerRpcURL());
            String tmapStr = p.getProperty(ProjectConst.KEY_SYS_SERVER_RPC_LIST_URL);
            String[] tmapList = tmapStr.split(";");
            for (int i = 0; i < tmapList.length; i++) {
                String string = tmapList[i];
                sysModel.add(string);
            }
            sysModel.setSelectedItem(ClientConfig.getSysServerRpcURL());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        oKCancelPanel1 = new  com.yz.ams.client.OKCancelPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sysComBox = new javax.swing.JComboBox();
        usmComBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());
        add(oKCancelPanel1, java.awt.BorderLayout.SOUTH);

        jLabel1.setText("　　应用服务器地址：");

        sysComBox.setEditable(true);
        sysComBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        usmComBox.setEditable(true);
        usmComBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("用户认证服务器地址：");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sysComBox, 0, 382, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usmComBox, 0, 342, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(sysComBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(usmComBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private  com.yz.ams.client.OKCancelPanel oKCancelPanel1;
    private javax.swing.JComboBox sysComBox;
    private javax.swing.JComboBox usmComBox;
    // End of variables declaration//GEN-END:variables
}
