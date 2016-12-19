/*
 * CreateAttendancePanel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-21 11:08:16
 */
package com.yz.ams.client.ui.attendence;

import com.nazca.ui.NInternalDiag;
import com.nazca.ui.NLabelMessageTool;
import com.nazca.usm.model.USMSUser;
import com.nazca.util.StringUtil;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.agent.AddAttendanceAgent;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.ui.UsmInfoTreePanel;
import com.yz.ams.client.util.ClientUtils;
import com.yz.ams.consts.AttendanceOutTypeEnum;
import com.yz.ams.consts.DelayTypeEnum;
import com.yz.ams.model.Attendance;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JComponent;

/**
 * 出勤管理——添加
 * @author Your Name <Song Haixiang >
 */
public class AddAttendancePanel extends javax.swing.JPanel {
    private NInternalDiag<List<Attendance>, JComponent> diag = null;
    private AddAttendanceAgent addAttendanceAgent = null;
    private List<USMSUser> checkedUserLis = null;
    

    /**
     * Creates new form CreateAttendancePanel
     */
    public AddAttendancePanel() {
        initComponents();
        initUI();
        initAgentAndListener();
    }   
     
    private void initAgentAndListener() {
        addAttendanceAgent = new AddAttendanceAgent();
        addAttendanceAgent.addListener(agentListener);
    }
    
    private void initUI(){
        dayLbl.setVisible(false);
        daysTxFd.setVisible(false);
        attendPeosonTxArea.setEditable(false);
    }

    public List<Attendance> showMe(JComponent parent) {
        diag = new NInternalDiag<List<Attendance>, JComponent>("添加出勤", ClientUtils.buildImageIcon("add-attendence.png"), this, 750, 550);
        return diag.showInternalDiag(parent);
    }
    
    private List<Attendance> getPaneContent(){
        List<Attendance> attenLis = new ArrayList<>();
        if (checkedUserLis == null) {
            checkedUserLis = new ArrayList<>();
        }
//        checkedUserLis = userModel.getAllSelectedNominals();
        Date selectedDate = attenDatePick.getSelectedDate();
        Object selectedItem = statusCmBox.getSelectedItem();
        DelayTypeEnum delayType = null;
        double days = 0;
        boolean isLeaveEarly = false;
        AttendanceOutTypeEnum outType = null; 
        if (selectedItem.equals("轻微迟到")) {
            delayType = DelayTypeEnum.LIGHT;
        }else if(selectedItem.equals("严重迟到")){
            delayType = DelayTypeEnum.SERIOUS;
        }else if(selectedItem.equals("迟到")){
            delayType = DelayTypeEnum.NORMAL;
        }else if(selectedItem.equals("早退")){
            isLeaveEarly = true;
        }else if(selectedItem.equals("旷工")){
            String str = daysTxFd.getText();
            if (!StringUtil.isEmpty(str)) {
                days = Double.valueOf(str);
            }
        }else if(selectedItem.equals("请假")){
            outType = AttendanceOutTypeEnum.VACATION;
        }else if(selectedItem.equals("调休")){
            outType = AttendanceOutTypeEnum.REST;
        }else if(selectedItem.equals("出差")){
            outType = AttendanceOutTypeEnum.BIZ_TRIP;
        }
        USMSUser user = ClientContext.getUser();
        for (USMSUser checkedUser : checkedUserLis) {
            Attendance attendance = new Attendance();
            attendance.setAttendanceDate(selectedDate);
            attendance.setCreatorId(user.getId());
            attendance.setCreatorName(user.getName());
            attendance.setUserId(checkedUser.getId());
            attendance.setUserName(checkedUser.getName());
            attendance.setAbsentDays(days);
            attendance.setDelayType(delayType);
            attendance.setLeaveEarly(isLeaveEarly);
            attendance.setOutType(outType);
            attenLis.add(attendance);
        }
        return attenLis;
    }
    
    private boolean analysisContents() {
        boolean flag = true;
        Object selectedItem = statusCmBox.getSelectedItem();
        if(attenDatePick.getSelectedDate() == null){
            gotoWarnMessage("日期不能为空");
            flag = false;
        }
        if (selectedItem.equals("旷工")) {
            String reg = "^\\d+(\\.\\d+)?$";
            String day = daysTxFd.getText();
            if (StringUtil.isEmpty(day)) {
                gotoWarnMessage("请填写旷工天数");
            }else if (!day.matches(reg)) {
                gotoWarnMessage("调休天数格式不正确");
                flag = false;
            }
        }
        String bizUsers = attendPeosonTxArea.getText();
        if (StringUtil.isEmpty(bizUsers)) {
            gotoWarnMessage("请至少选择一个出勤人员");
            flag = false;
        }
        return flag;
    }
    private void gotoWarnMessage(String msg){
        okBtn.setEnabled(true);
        nActionPane1.getWaitingProcess().setVisible(false);
        nActionPane1.getWaitingProcess().setIndeterminate(false);
        NLabelMessageTool.warningMessage(nActionPane1.getMsgLabel(), msg);
        nActionPane1.getMsgLabel().setVisible(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        添加出勤 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0));
        jLabel2 = new javax.swing.JLabel();
        attenDatePick = new com.nazca.ui.JDatePicker();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jToolBar2 = new javax.swing.JToolBar();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jLabel1 = new javax.swing.JLabel();
        statusCmBox = new javax.swing.JComboBox<>();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        daysTxFd = new javax.swing.JTextField();
        dayLbl = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        attendPeosonTxArea = new javax.swing.JTextArea();
        jLinkLabel1 = new com.nazca.ui.JLinkLabel();
        nActionPane1 = new com.nazca.ui.NActionPane();
        cancleBtn = new javax.swing.JButton();
        okBtn = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(270, 350));
        setPreferredSize(new java.awt.Dimension(270, 350));
        setLayout(new java.awt.BorderLayout());

        添加出勤.setMinimumSize(new java.awt.Dimension(40, 80));
        添加出勤.setPreferredSize(new java.awt.Dimension(10, 60));
        添加出勤.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.add(filler7);

        jLabel2.setText("日期：");
        jToolBar1.add(jLabel2);

        attenDatePick.setMaximumSize(new java.awt.Dimension(120, 32767));
        attenDatePick.setMinimumSize(new java.awt.Dimension(120, 22));
        attenDatePick.setPreferredSize(new java.awt.Dimension(120, 16));
        jToolBar1.add(attenDatePick);
        jToolBar1.add(filler2);

        添加出勤.add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.add(filler5);

        jLabel1.setText("标记为：");
        jToolBar2.add(jLabel1);

        statusCmBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "轻微迟到", "迟到", "严重迟到", "早退", "旷工", "请假", "调休", "出差" }));
        statusCmBox.setMaximumSize(new java.awt.Dimension(100, 20));
        statusCmBox.setMinimumSize(new java.awt.Dimension(100, 20));
        statusCmBox.setPreferredSize(new java.awt.Dimension(100, 20));
        statusCmBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusCmBoxActionPerformed(evt);
            }
        });
        jToolBar2.add(statusCmBox);
        jToolBar2.add(filler3);

        daysTxFd.setMaximumSize(new java.awt.Dimension(100, 21));
        daysTxFd.setMinimumSize(new java.awt.Dimension(100, 21));
        daysTxFd.setPreferredSize(new java.awt.Dimension(100, 21));
        jToolBar2.add(daysTxFd);

        dayLbl.setText(" 天");
        jToolBar2.add(dayLbl);

        添加出勤.add(jToolBar2, java.awt.BorderLayout.PAGE_END);

        jLabel4.setText("员工姓名：");

        attendPeosonTxArea.setColumns(20);
        attendPeosonTxArea.setLineWrap(true);
        jScrollPane1.setViewportView(attendPeosonTxArea);

        jLinkLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/user_16.png"))); // NOI18N
        jLinkLabel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLinkLabel1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLinkLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLinkLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        添加出勤.add(jPanel2, java.awt.BorderLayout.CENTER);

        add(添加出勤, java.awt.BorderLayout.CENTER);

        cancleBtn.setText("取消");
        cancleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancleBtnActionPerformed(evt);
            }
        });
        nActionPane1.add(cancleBtn);

        okBtn.setText("确定");
        okBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okBtnActionPerformed(evt);
            }
        });
        nActionPane1.add(okBtn);

        add(nActionPane1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void cancleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancleBtnActionPerformed
        diag.hideDiag();
    }//GEN-LAST:event_cancleBtnActionPerformed

    private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
        if (analysisContents()) {
            List<Attendance> lis = getPaneContent();
            addAttendanceAgent.setAttenLis(lis);
            addAttendanceAgent.start();
        }
        //test
//        checkedUserLis = userModel.getAllSelectedNominals();
//        UIUtilities.infoDlg(this, "提示信息", checkedUserLis.get(0).getName()+checkedUserLis.get(1).getName());
    }//GEN-LAST:event_okBtnActionPerformed

    private void statusCmBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusCmBoxActionPerformed
        Object selectedItem = statusCmBox.getSelectedItem();
        if (selectedItem.equals("旷工")) {
            daysTxFd.setVisible(true);
            dayLbl.setVisible(true);
        }else{
            daysTxFd.setVisible(false);
            dayLbl.setVisible(false);
        }
    }//GEN-LAST:event_statusCmBoxActionPerformed

    private void jLinkLabel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLinkLabel1ActionPerformed
        UsmInfoTreePanel usmPane = new UsmInfoTreePanel();
        checkedUserLis = usmPane.showMe(jLinkLabel1);
        String peopleNameStr = "";
        if (null != checkedUserLis && checkedUserLis.size() > 0) {
            for (int i = 0; i < checkedUserLis.size(); i++) {
                USMSUser user = checkedUserLis.get(i);
                peopleNameStr += user.getName() + "，";
            }
            attendPeosonTxArea.setText(peopleNameStr.substring(0, peopleNameStr.length() - 1));
        }
    }//GEN-LAST:event_jLinkLabel1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.nazca.ui.JDatePicker attenDatePick;
    private javax.swing.JTextArea attendPeosonTxArea;
    private javax.swing.JButton cancleBtn;
    private javax.swing.JLabel dayLbl;
    private javax.swing.JTextField daysTxFd;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private com.nazca.ui.JLinkLabel jLinkLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private com.nazca.ui.NActionPane nActionPane1;
    private javax.swing.JButton okBtn;
    private javax.swing.JComboBox<String> statusCmBox;
    private javax.swing.JPanel 添加出勤;
    // End of variables declaration//GEN-END:variables
 AgentListener<List<Attendance>> agentListener = new AgentListener<List<Attendance>>() {
        @Override
        public void onStarted(long seq) {
            okBtn.setEnabled(false);
            cancleBtn.setEnabled(false);
            setEnableComp(false);
            nActionPane1.getWaitingProcess().setVisible(true);
            nActionPane1.getWaitingProcess().setIndeterminate(true);
            nActionPane1.getMsgLabel().setVisible(true);
            NLabelMessageTool.infoMessage(nActionPane1.getMsgLabel(), "正在提交");
        }

        @Override
        public void onSucceeded(List<Attendance> result, long seq) {
            okBtn.setEnabled(true);
            cancleBtn.setEnabled(true);
            setEnableComp(true);
            nActionPane1.getWaitingProcess().setVisible(false);
            nActionPane1.getWaitingProcess().setIndeterminate(false);
            NLabelMessageTool.goodNewsMessage(nActionPane1.getMsgLabel(), "提交成功");
            nActionPane1.getMsgLabel().setVisible(true);
            NInternalDiag.findNInternalDiag(AddAttendancePanel.this).hideDiag(result);
        }

        @Override
        public void onFailed(String errMsg, int errCode, long seq) {
            okBtn.setEnabled(true);
            cancleBtn.setEnabled(true);
            setEnableComp(true);
            nActionPane1.getWaitingProcess().setVisible(false);
            nActionPane1.getWaitingProcess().setIndeterminate(false);
            NLabelMessageTool.errorMessage(nActionPane1.getMsgLabel(), errCode, errMsg);
            nActionPane1.getMsgLabel().setVisible(true);
        }

    };

    private void setEnableComp(boolean flag){
        jLinkLabel1.setEnabled(flag);
        attenDatePick.setEnabled(flag);
        daysTxFd.setEditable(flag);
        statusCmBox.setEnabled(flag);
    }
 
}
