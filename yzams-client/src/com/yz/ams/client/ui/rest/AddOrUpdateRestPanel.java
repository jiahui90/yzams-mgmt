/*
 * RestPanel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-27 15:27:18
 */
package com.yz.ams.client.ui.rest;

import com.nazca.ui.NInternalDiag;
import com.nazca.ui.NLabelMessageTool;
import com.nazca.usm.model.USMSUser;
import com.nazca.util.StringUtil;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.agent.AddOrUpdateRestAgent;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.agent.QueryHolidaysAgent;
import com.yz.ams.client.ui.SingerUsmInfoTreePanel;
import com.yz.ams.client.ui.UsmInfoTreePanel;
import com.yz.ams.client.util.ClientUtils;
import com.yz.ams.client.util.StaticDataUtil;
import com.yz.ams.consts.HolidayTypeEnum;
import com.yz.ams.model.Holiday;
import com.yz.ams.model.Rest;
import com.yz.ams.util.DateUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;

/**
 *
 * @author Your Name <Song Haixiang >
 */
public class AddOrUpdateRestPanel extends javax.swing.JPanel {
    private NInternalDiag<Rest, JComponent> diag;
    private Rest rest = null;
    private AddOrUpdateRestAgent addOrUpdateRestAgent = null;
    private QueryHolidaysAgent queryHolidayAgent = null;
    private List<Holiday> holidayList = null;
    //申请人
    private USMSUser applicantUser = null;
    //调休人员的所有ID
    private String userIds = null;

    public void setRest(Rest rest) {
        this.rest = rest;
    }

    private void initAgentAndListener() {
        addOrUpdateRestAgent = new AddOrUpdateRestAgent();
        addOrUpdateRestAgent.addListener(agentListener);
        queryHolidayAgent = new QueryHolidaysAgent();
        queryHolidayAgent.addListener(queryHolidayLisener);
    }

    /**
     * Creates new form RestPanel
     */
    public AddOrUpdateRestPanel() {
        initComponents();
        initUI();
//        initPaneContnet();
        initAgentAndListener();
        queryHoliday();
    }
    
    private void queryHoliday(){
        queryHolidayAgent.start();
    }
    
    public Rest showMe(JComponent parent) {
        diag = new NInternalDiag<Rest, JComponent>("添加调休", ClientUtils.buildImageIcon("add-rest.png"), this, 750, 550);
        return diag.showInternalDiag(parent);
    }
    
    public Rest showMe1(JComponent parent) {
        diag = new NInternalDiag<Rest, JComponent>("修改调休", ClientUtils.buildImageIcon("modi-rest.png"), this, 750, 550);
        return diag.showInternalDiag(parent);
    }

    private void initPaneContnet(){
        USMSUser curUser = ClientContext.getUser();
        nameTxFd.setText(curUser.getName());//姓名
    }
    
    private void initUI(){
        nameTxFd.setEditable(false);
        staffIdsTxFd.setEditable(false);
    }

    public void setPaneContent(Rest rest) {
        this.rest = rest;
        nameTxFd.setText(rest.getUserName());//姓名
        if (rest != null) {
//            nameTxFd.setText(rest.getUserName());//申请人姓名
            datePick.setSelectedDate(rest.getStartDate());//开始日期
            if (rest.isMorning()) {
                amOrPamCmbBox.setSelectedItem("上午");
            } else {
                amOrPamCmbBox.setSelectedItem("下午");
            }
            daysTxFd.setText(String.valueOf(rest.getDays()));//天数
            
            //TODO根据staffIds得到用户名
            String staffIds = rest.getStaffIds();
            Map<String, USMSUser> userMap = StaticDataUtil.getUserMap();
            String peosonName = "";
            if (!StringUtil.isEmpty(staffIds)) {
                String [] ids = staffIds.split("，");
                for (String id : ids) {
                    USMSUser user = userMap.get(id);
                    if (user != null) {
                        peosonName += userMap.get(id).getName() +"，";
                    }
                }
            }
            if (peosonName.length() >= 1) {
                staffIdsTxFd.setText(peosonName.substring(0, peosonName.length()-1));
            }
            memoTxAa.setText(rest.getMemo());//事由
        }
    }

    private Rest getPaneContent() {
        USMSUser curUser = ClientContext.getUser();
        if (this.rest == null) {
            rest = new Rest();
            //创建者ID、创建者姓名
            rest.setCreatorId(curUser.getId());
            rest.setCreatorName(curUser.getName());
            //申请人ID
            rest.setUserId(applicantUser.getId());
            rest.setUserName(applicantUser.getName());
        } else {
            rest.setModifierId(curUser.getId());
            rest.setModifierName(curUser.getName());
            if (applicantUser != null) {
                rest.setUserId(applicantUser.getId());
                rest.setUserName(applicantUser.getName());
            }
        }
        Date startDate = datePick.getSelectedDate();
        rest.setStartDate(startDate);
        if (amOrPamCmbBox.getSelectedItem().equals("上午")) {
            rest.setMorning(true);
        } else {
            rest.setMorning(false);
            //如果下午，开始时间设置为12:00:00
            rest.setStartDate(DateUtil.getLastHourStartTime(startDate,12));
            startDate = DateUtil.getLastHourStartTime(startDate,12);
        }
        Double days = Double.valueOf(daysTxFd.getText());//天数
        rest.setDays(days);
        
        while(days > 0){
            //如果是休息日，跳过这一天
            if(isWeekendOrHoliday(DateUtil.getZeroTimeOfDay(startDate))){
               startDate =  DateUtil.getLastHourStartTime(startDate,24);
               continue;
            }

            days = days - 0.5;
            startDate = DateUtil.getLastHourStartTime(startDate,12);
        }
        
        //结束时间为在下午，设置为23:59:59
        if(DateUtil.getHourOfDate(startDate) == 0){
            startDate = DateUtil.addSecond2Date(startDate, -1);
        }
            //结束时间为在上午,设置为 00:00:00
            rest.setEndDate(startDate);
       
        if (!StringUtil.isEmpty(userIds)) {
            rest.setStaffIds(userIds);
        }
        rest.setMemo(memoTxAa.getText());
        rest.setCreateTime(new Date());
        return rest;
    }
    
    //是节假日或周末
    private boolean isWeekendOrHoliday(Date d){
        boolean isWorkday = DateUtil.isWorkingDays(d);
        //是工作日
        if(isWorkday){
            for(Holiday h : holidayList){
                Date date = h.getHolidayDate();
                if(d.getTime() == date.getTime()){
                    if(h.getHolidayType().equals(HolidayTypeEnum.HOLIDAY)){
                        return true;         //如果工作日是HOLIDAY，返回true,跳过这个日期
                    }
                }
            }
            return false;
        }else{
            for(Holiday h : holidayList){
                Date date = h.getHolidayDate();
                if(d.getTime() == date.getTime()){
                    if(h.getHolidayType().equals(HolidayTypeEnum.WORKDAY)){
                        return false; 
                    }
                }
            }
            return true;       //如果周末都不是是WORKDAY，返回true,跳过这个日期
        }
    }
    
    private boolean analysisContents() {
        boolean flag = true;
        String memo = memoTxAa.getText();
        if (memo.length() > 64) {
            gotoWarnMessage("调休事由内容过长");
            flag = false;
        }
        if(datePick.getSelectedDate() == null){
            gotoWarnMessage("调休日期不能为空");
            flag = false;
        }
        String bizUsers = staffIdsTxFd.getText();
        if (StringUtil.isEmpty(bizUsers)) {
            gotoWarnMessage("请至少选择一个调休人员");
            flag = false;
        }else if (bizUsers.length() > 1024) {
            gotoWarnMessage("调休人员内容过长");
            flag = false;
        }
        String day = daysTxFd.getText();
        String reg = "^\\d+(\\.\\d+)?$";
        if (day.isEmpty()) {
            gotoWarnMessage("请填写调休天数");
            flag = false;
        } else if (!day.matches(reg)) {
            gotoWarnMessage("调休天数格式不正确");
            flag = false;
        }
        String name = nameTxFd.getText().trim();
        if (name.isEmpty()) {
            gotoWarnMessage("请选择调休申请人");
            flag = false;
        }
        return flag;
    }
    
    private void gotoWarnMessage(String msg){
        addOrUpdateBtn.setEnabled(true);
        nActionPane1.getWaitingProcess().setVisible(false);
        nActionPane1.getWaitingProcess().setIndeterminate(false);
        NLabelMessageTool.warningMessage(nActionPane1.getMsgLabel(), msg);
        nActionPane1.getMsgLabel().setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        datePick = new com.nazca.ui.JDatePicker();
        amOrPamCmbBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        daysTxFd = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        memoTxAa = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        staffIdsTxFd = new javax.swing.JTextArea();
        nameTxFd = new javax.swing.JTextField();
        jLinkLabel1 = new com.nazca.ui.JLinkLabel();
        jLinkLabel2 = new com.nazca.ui.JLinkLabel();
        nActionPane1 = new com.nazca.ui.NActionPane();
        cancleBtn = new javax.swing.JButton();
        addOrUpdateBtn = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("　申请人：");

        jLabel4.setText("调休时间：");

        amOrPamCmbBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "上午", "下午" }));

        jLabel5.setText("    天数：");

        jLabel6.setText("调休人员：");

        jLabel7.setText("调休事由：");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setFocusCycleRoot(true);

        memoTxAa.setColumns(20);
        memoTxAa.setLineWrap(true);
        jScrollPane1.setViewportView(memoTxAa);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setAlignmentX(0.0F);
        jScrollPane2.setAlignmentY(0.0F);

        staffIdsTxFd.setColumns(20);
        staffIdsTxFd.setLineWrap(true);
        jScrollPane2.setViewportView(staffIdsTxFd);

        jLinkLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/user_16.png"))); // NOI18N
        jLinkLabel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLinkLabel1ActionPerformed(evt);
            }
        });

        jLinkLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/user_16.png"))); // NOI18N
        jLinkLabel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLinkLabel2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(datePick, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(amOrPamCmbBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(daysTxFd)
                            .addComponent(nameTxFd))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLinkLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLinkLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(nameTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLinkLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel4)
                            .addComponent(datePick, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(daysTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(amOrPamCmbBox, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLinkLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);

        cancleBtn.setText("取消");
        cancleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancleBtnActionPerformed(evt);
            }
        });
        nActionPane1.add(cancleBtn);

        addOrUpdateBtn.setText("确定");
        addOrUpdateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOrUpdateBtnActionPerformed(evt);
            }
        });
        nActionPane1.add(addOrUpdateBtn);

        add(nActionPane1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void cancleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancleBtnActionPerformed
        diag.hideDiag(); // TODO add your handling code here:
    }//GEN-LAST:event_cancleBtnActionPerformed

    private void addOrUpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrUpdateBtnActionPerformed
        if (analysisContents()) {
            Rest rest = getPaneContent();
            addOrUpdateRestAgent.setRest(rest);
            addOrUpdateRestAgent.start();
        }
    }//GEN-LAST:event_addOrUpdateBtnActionPerformed

    private void jLinkLabel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLinkLabel1ActionPerformed
        UsmInfoTreePanel usmPane = new UsmInfoTreePanel();
        List<USMSUser> userList = usmPane.showMe(jLinkLabel1);
        
        if (null != userList && userList.size() > 0) {
            userIds = "";
            String peopleNameStr = "";
            for (int i = 0; i < userList.size(); i++) {
                USMSUser user = userList.get(i);
                peopleNameStr += user.getName() + ",";
                userIds += user.getId() + ",";
            }
            staffIdsTxFd.setText(peopleNameStr.substring(0, peopleNameStr.length() - 1));
            userIds = userIds.substring(0, userIds.length() - 1);
        }
    }//GEN-LAST:event_jLinkLabel1ActionPerformed

    private void jLinkLabel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLinkLabel2ActionPerformed
        SingerUsmInfoTreePanel usmPane = new SingerUsmInfoTreePanel();
        applicantUser = usmPane.showMe(jLinkLabel2);
        if (applicantUser != null) {
            nameTxFd.setText(applicantUser.getName());
        }
    }//GEN-LAST:event_jLinkLabel2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addOrUpdateBtn;
    private javax.swing.JComboBox<String> amOrPamCmbBox;
    private javax.swing.JButton cancleBtn;
    private com.nazca.ui.JDatePicker datePick;
    private javax.swing.JTextField daysTxFd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private com.nazca.ui.JLinkLabel jLinkLabel1;
    private com.nazca.ui.JLinkLabel jLinkLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea memoTxAa;
    private com.nazca.ui.NActionPane nActionPane1;
    private javax.swing.JTextField nameTxFd;
    private javax.swing.JTextArea staffIdsTxFd;
    // End of variables declaration//GEN-END:variables

    private AgentListener<Rest> agentListener = new AgentListener<Rest>() {
        @Override
        public void onStarted(long seq) {
            cancleBtn.setEnabled(false);
            addOrUpdateBtn.setEnabled(false);
            setEnableComp(false);
            nActionPane1.getWaitingProcess().setVisible(true);
            nActionPane1.getWaitingProcess().setIndeterminate(true);
            nActionPane1.getMsgLabel().setVisible(true);
            NLabelMessageTool.infoMessage(nActionPane1.getMsgLabel(), "正在提交");

        }

        @Override
        public void onSucceeded(Rest result, long seq) {
            addOrUpdateBtn.setEnabled(true);
            cancleBtn.setEnabled(true);
            setEnableComp(true);
            nActionPane1.getWaitingProcess().setVisible(false);
            nActionPane1.getWaitingProcess().setIndeterminate(false);
            NLabelMessageTool.goodNewsMessage(nActionPane1.getMsgLabel(), "提交成功");
            nActionPane1.getMsgLabel().setVisible(true);
            NInternalDiag.findNInternalDiag(AddOrUpdateRestPanel.this).hideDiag(result);
        }

        @Override
        public void onFailed(String errMsg, int errCode, long seq) {
            addOrUpdateBtn.setEnabled(true);
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
        datePick.setEnabled(flag);
        amOrPamCmbBox.setEnabled(flag);
        daysTxFd.setEditable(flag);
        jLinkLabel2.setEnabled(flag);
        memoTxAa.setEnabled(flag);
    }
    
    AgentListener<List<Holiday>> queryHolidayLisener = new AgentListener<List<Holiday>>() {
        @Override
        public void onStarted(long seq) {
            
        }

        @Override
        public void onSucceeded(List<Holiday> result, long seq) {
            holidayList = result;
        }

        @Override
        public void onFailed(String errMsg, int errCode, long seq) {
            
        }
    };
}
