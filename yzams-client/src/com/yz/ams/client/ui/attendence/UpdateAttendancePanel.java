/*
 * AttendancePanel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-21 10:58:49
 */
package com.yz.ams.client.ui.attendence;

import com.nazca.ui.NInternalDiag;
import com.nazca.ui.NLabelMessageTool;
import com.nazca.util.StringUtil;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.agent.UpdateAttendanceAgent;
import com.yz.ams.client.util.ClientUtils;
import com.yz.ams.consts.AttendanceOutTypeEnum;
import com.yz.ams.consts.DelayTypeEnum;
import com.yz.ams.model.Attendance;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComponent;

/**
 *
 * @author Your Name <Song Haixiang >
 */
public class UpdateAttendancePanel extends javax.swing.JPanel {
    private NInternalDiag<Attendance, JComponent> diag;
    private UpdateAttendanceAgent updateAttendAgent = null;
    private Attendance attendance;
    /**
     * Creates new form AttendancePanel
     */
    public UpdateAttendancePanel() {
        initComponents();
        initAgentAndListener();
    }
    
    private void initAgentAndListener(){
        updateAttendAgent = new UpdateAttendanceAgent();
        updateAttendAgent.addListener(updateAttendListener);
        lateCkBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (lateCkBox.isSelected()) {
                    lightLateCkBox.setSelected(false);
                    seriousCkBox.setSelected(false);
                }
            }
        });
        lightLateCkBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (lightLateCkBox.isSelected()) {
                    lateCkBox.setSelected(false);
                    seriousCkBox.setSelected(false);
                }
            }
        });
        seriousCkBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (seriousCkBox.isSelected()) {
                    lateCkBox.setSelected(false);
                    lightLateCkBox.setSelected(false);
                }
            }
        });
        vacationCkBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (vacationCkBox.isSelected()) {
                    bizTripCkBox.setSelected(false);
                    restCkBox.setSelected(false);
                }
            }
        });
        bizTripCkBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (bizTripCkBox.isSelected()) {
                    vacationCkBox.setSelected(false);
                    restCkBox.setSelected(false);
                }
            }
        });
        restCkBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (restCkBox.isSelected()) {
                    vacationCkBox.setSelected(false);
                    bizTripCkBox.setSelected(false);
                }
            }
        });
    }

    public Attendance showMe(JComponent parent) {
        diag = new NInternalDiag<Attendance, JComponent>("修改出勤", ClientUtils.buildImageIcon("modi-attendence.png"), this, 500, 350);
        return diag.showInternalDiag(parent);
    }
    
    private Attendance getPaneContent(){
        attendance.setAttendanceDate(jDatePicker1.getSelectedDate());
        if (lateCkBox.isSelected()) {
            attendance.setDelayType(DelayTypeEnum.NORMAL);
        }else if(lightLateCkBox.isSelected()){
            attendance.setDelayType(DelayTypeEnum.LIGHT);
        }else if(seriousCkBox.isSelected()){
            attendance.setDelayType(DelayTypeEnum.SERIOUS);
        }else{
            attendance.setDelayType(null);
        }
        if (bizTripCkBox.isSelected()) {
            attendance.setOutType(AttendanceOutTypeEnum.BIZ_TRIP);
        }else if(restCkBox.isSelected()){
            attendance.setOutType(AttendanceOutTypeEnum.REST);
        }else if(vacationCkBox.isSelected()){
            attendance.setOutType(AttendanceOutTypeEnum.VACATION);
        }else{
            attendance.setOutType(null);
        }
        attendance.setLeaveEarly(leaveEarlyCkBox.isSelected());
        if (absentCkBox.isSelected()) {
            String days = absentDaysTxFd.getText();
            attendance.setAbsentDays(Double.valueOf(days));
        }else{
            attendance.setAbsentDays(0);
        }
        return attendance;
    }
    
    public void initPaneContent(Attendance attendance){
        this.attendance = attendance;
        nameLbl.setText(attendance.getUserName());
        jDatePicker1.setSelectedDate(attendance.getAttendanceDate());
        //迟到状态
        DelayTypeEnum delayType = attendance.getDelayType();
        if (delayType != null) {
            if (delayType.equals(delayType.NORMAL)) {
                lateCkBox.setSelected(true);
            }else if(delayType.equals(delayType.LIGHT)){
                lightLateCkBox.setSelected(true);
            }else {
                seriousCkBox.setSelected(true);
            }
        }
        AttendanceOutTypeEnum outType = attendance.getOutType();
        if (outType != null) {
            if (outType.equals(AttendanceOutTypeEnum.BIZ_TRIP)) {
                bizTripCkBox.setSelected(true);
            }else if(outType.equals(AttendanceOutTypeEnum.REST)){
                restCkBox.setSelected(true);
            }else{
                vacationCkBox.setSelected(true);
            }
        }
        double absentDays = attendance.getAbsentDays();
        if (absentDays > 0) {
            absentCkBox.setSelected(true);
            setCompVisible(true);
            absentDaysTxFd.setText(String.valueOf(absentDays));
        }else{
            absentCkBox.setSelected(false);
            setCompVisible(false);
        }
        boolean leaveEarly = attendance.isLeaveEarly();
        leaveEarlyCkBox.setSelected(leaveEarly);
//        memoTxArea.setText("");
    }
    
    private void setCompVisible(boolean flag){
        absentDaysTxFd.setVisible(flag);
        daysLbl.setVisible(flag);
    }
    
    private boolean analysisContents() {
        boolean flag = true;
         if(jDatePicker1.getSelectedDate() == null){
            gotoWarnMessage("出勤日期不能为空");
            flag = false;
        }
        if (absentCkBox.isSelected()) {
            String reg = "^\\d+(\\.\\d+)?$";
            String day = absentDaysTxFd.getText();
            if (StringUtil.isEmpty(day)) {
                gotoWarnMessage("请填写旷工天数");
                flag = false;
            }else if (!day.matches(reg)) {
                gotoWarnMessage("调休天数格式不正确");
                flag = false;
            }
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        lateRaoBtn = new javax.swing.JRadioButton();
        lightLateRaoBtn = new javax.swing.JRadioButton();
        seriousRaoBtn = new javax.swing.JRadioButton();
        vacationRaoBtn = new javax.swing.JRadioButton();
        restRaoBtn = new javax.swing.JRadioButton();
        bizTripRaoBtn = new javax.swing.JRadioButton();
        leaveEarlyRaoBtn = new javax.swing.JRadioButton();
        minersRaoBtn = new javax.swing.JRadioButton();
        buttonGroup2 = new javax.swing.ButtonGroup();
        Jpanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        nameLbl = new javax.swing.JLabel();
        jDatePicker1 = new com.nazca.ui.JDatePicker();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lateCkBox = new javax.swing.JCheckBox();
        lightLateCkBox = new javax.swing.JCheckBox();
        seriousCkBox = new javax.swing.JCheckBox();
        vacationCkBox = new javax.swing.JCheckBox();
        restCkBox = new javax.swing.JCheckBox();
        bizTripCkBox = new javax.swing.JCheckBox();
        leaveEarlyCkBox = new javax.swing.JCheckBox();
        absentCkBox = new javax.swing.JCheckBox();
        absentDaysTxFd = new javax.swing.JTextField();
        daysLbl = new javax.swing.JLabel();
        nActionPane1 = new com.nazca.ui.NActionPane();
        cancleBtn = new javax.swing.JButton();
        okBtn = new javax.swing.JButton();

        lateRaoBtn.setText("迟到");

        lightLateRaoBtn.setText("轻微迟到");

        seriousRaoBtn.setText("严重迟到");

        vacationRaoBtn.setText("请假");

        restRaoBtn.setText("调休");

        bizTripRaoBtn.setText("出差");

        leaveEarlyRaoBtn.setText("早退");

        minersRaoBtn.setText("旷工");

        setLayout(new java.awt.BorderLayout());

        jLabel3.setText("出勤日期：");

        nameLbl.setText("张三");

        jLabel2.setText("状态：");

        jLabel4.setText("姓名：");

        lateCkBox.setText("迟到");
        lateCkBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lateCkBoxActionPerformed(evt);
            }
        });

        lightLateCkBox.setText("轻微迟到");

        seriousCkBox.setText("严重迟到");

        vacationCkBox.setText("请假");

        restCkBox.setText("调休");

        bizTripCkBox.setText("出差");

        leaveEarlyCkBox.setText("早退");

        absentCkBox.setText("旷工");
        absentCkBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                absentCkBoxItemStateChanged(evt);
            }
        });

        daysLbl.setText(" 天");

        javax.swing.GroupLayout Jpanel1Layout = new javax.swing.GroupLayout(Jpanel1);
        Jpanel1.setLayout(Jpanel1Layout);
        Jpanel1Layout.setHorizontalGroup(
            Jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Jpanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Jpanel1Layout.createSequentialGroup()
                        .addGroup(Jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLbl)
                            .addGroup(Jpanel1Layout.createSequentialGroup()
                                .addGroup(Jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Jpanel1Layout.createSequentialGroup()
                                        .addComponent(lateCkBox)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lightLateCkBox))
                                    .addGroup(Jpanel1Layout.createSequentialGroup()
                                        .addComponent(vacationCkBox)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(restCkBox))
                                    .addGroup(Jpanel1Layout.createSequentialGroup()
                                        .addComponent(leaveEarlyCkBox)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(absentCkBox)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(Jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Jpanel1Layout.createSequentialGroup()
                                        .addComponent(absentDaysTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(daysLbl))
                                    .addComponent(bizTripCkBox)
                                    .addComponent(seriousCkBox))))
                        .addGap(0, 11, Short.MAX_VALUE))
                    .addComponent(jDatePicker1, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))
                .addContainerGap())
        );
        Jpanel1Layout.setVerticalGroup(
            Jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Jpanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(nameLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(lateCkBox)
                    .addComponent(lightLateCkBox)
                    .addComponent(seriousCkBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(vacationCkBox)
                    .addComponent(restCkBox)
                    .addComponent(bizTripCkBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(leaveEarlyCkBox)
                    .addComponent(absentCkBox)
                    .addComponent(absentDaysTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(daysLbl))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        add(Jpanel1, java.awt.BorderLayout.CENTER);

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
            Attendance attendance = getPaneContent();
            updateAttendAgent.setAttendance(attendance);
            updateAttendAgent.start();
        }
    }//GEN-LAST:event_okBtnActionPerformed

    private void absentCkBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_absentCkBoxItemStateChanged
        setCompVisible(absentCkBox.isSelected());
    }//GEN-LAST:event_absentCkBoxItemStateChanged

    private void lateCkBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lateCkBoxActionPerformed
//        boolean flag = lateCkBox.isSelected();
//        lateCkBox.setSelected(!flag);
    }//GEN-LAST:event_lateCkBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Jpanel1;
    private javax.swing.JCheckBox absentCkBox;
    private javax.swing.JTextField absentDaysTxFd;
    private javax.swing.JCheckBox bizTripCkBox;
    private javax.swing.JRadioButton bizTripRaoBtn;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton cancleBtn;
    private javax.swing.JLabel daysLbl;
    private com.nazca.ui.JDatePicker jDatePicker1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JCheckBox lateCkBox;
    private javax.swing.JRadioButton lateRaoBtn;
    private javax.swing.JCheckBox leaveEarlyCkBox;
    private javax.swing.JRadioButton leaveEarlyRaoBtn;
    private javax.swing.JCheckBox lightLateCkBox;
    private javax.swing.JRadioButton lightLateRaoBtn;
    private javax.swing.JRadioButton minersRaoBtn;
    private com.nazca.ui.NActionPane nActionPane1;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JButton okBtn;
    private javax.swing.JCheckBox restCkBox;
    private javax.swing.JRadioButton restRaoBtn;
    private javax.swing.JCheckBox seriousCkBox;
    private javax.swing.JRadioButton seriousRaoBtn;
    private javax.swing.JCheckBox vacationCkBox;
    private javax.swing.JRadioButton vacationRaoBtn;
    // End of variables declaration//GEN-END:variables
    
    private AgentListener<Attendance> updateAttendListener = new AgentListener<Attendance>() {
        @Override
        public void onStarted(long seq) {
            cancleBtn.setEnabled(false);
            okBtn.setEnabled(false);
            setEnableComp(false);
            nActionPane1.getWaitingProcess().setVisible(true);
            nActionPane1.getWaitingProcess().setIndeterminate(true);
            nActionPane1.getMsgLabel().setVisible(true);
            NLabelMessageTool.infoMessage(nActionPane1.getMsgLabel(), "正在提交");

        }

        @Override
        public void onSucceeded(Attendance result, long seq) {
            okBtn.setEnabled(true);
            cancleBtn.setEnabled(true);
            setEnableComp(true);
            nActionPane1.getWaitingProcess().setVisible(false);
            nActionPane1.getWaitingProcess().setIndeterminate(false);
            NLabelMessageTool.goodNewsMessage(nActionPane1.getMsgLabel(), "提交成功");
            nActionPane1.getMsgLabel().setVisible(true);
            NInternalDiag.findNInternalDiag(UpdateAttendancePanel.this).hideDiag(result);
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
        jDatePicker1.setEnabled(flag);
        lateCkBox.setEnabled(flag);
        lightLateCkBox.setEnabled(flag);
        seriousCkBox.setEnabled(flag);
        vacationCkBox.setEnabled(flag);
        restCkBox.setEnabled(flag);
        bizTripCkBox.setEnabled(flag);
        leaveEarlyCkBox.setEnabled(flag);
        absentCkBox.setEnabled(flag);
        absentDaysTxFd.setEnabled(flag);
//        memoTxArea.setEnabled(flag);
    }
    
}
