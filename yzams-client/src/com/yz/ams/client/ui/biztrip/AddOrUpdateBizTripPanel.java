/*
 * CreateBizTripPanel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-20 10:22:21
 */
package com.yz.ams.client.ui.biztrip;

import com.nazca.ui.NInternalDiag;
import com.nazca.ui.NLabelMessageTool;
import com.nazca.usm.model.USMSUser;
import com.nazca.util.StringUtil;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.agent.AddOrUpdateBizTripAgent;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.agent.QueryHolidaysAgent;
import com.yz.ams.client.ui.SingerUsmInfoTreePanel;
import com.yz.ams.client.ui.UsmInfoTreePanel;
import com.yz.ams.client.util.ClientUtils;
import com.yz.ams.client.util.StaticDataUtil;
import com.yz.ams.consts.HolidayTypeEnum;
import com.yz.ams.model.BizTrip;
import com.yz.ams.model.Holiday;
import com.yz.ams.util.DateUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;

/**
 *
 * @author Your Name <Song Haixiang >
 */
public class AddOrUpdateBizTripPanel extends javax.swing.JPanel {
    private NInternalDiag<BizTrip, JComponent> diag = null;
    private AddOrUpdateBizTripAgent addOrUpdateBizTripAgent= null;
    private QueryHolidaysAgent queryHolidayAgent = null;
    private BizTrip bizTrip = null;
    private String userIds = null;
    private USMSUser applicantUser = null;
    private List<Holiday> holidayList = null;

    private void initAgentAndListener() {
        addOrUpdateBizTripAgent = new AddOrUpdateBizTripAgent();
        addOrUpdateBizTripAgent.addListener(agentListener);
        queryHolidayAgent = new QueryHolidaysAgent();
        queryHolidayAgent.addListener(queryHolidayLisener);
    }
    
    private void queryHoliday(){
        queryHolidayAgent.start();
    }
    
    /**
     * Creates new form CreateBizTripPanel
     */
    public AddOrUpdateBizTripPanel() {
        initComponents();
        initUI();
//        initPaneConent();
        initAgentAndListener();
        queryHoliday();
    }

    public BizTrip showMe(JComponent parent) {
        diag = new NInternalDiag<BizTrip, JComponent>("添加出差", ClientUtils.buildImageIcon("add-biztrip.png"), this, 750, 550);
        okBtn.setText("保存");
        return diag.showInternalDiag(parent);
    }

    public BizTrip showMe1(JComponent parent) {
        diag = new NInternalDiag<BizTrip, JComponent>("修改出差", ClientUtils.buildImageIcon("modi-biztrip.png"), this, 750, 550);
        okBtn.setText("保存");
        return diag.showInternalDiag(parent);
    }
    
    private void initPaneConent(){
        USMSUser curUser = ClientContext.getUser();
        nameTxFd.setText(curUser.getName());//姓名
    }
     private void initUI(){
        nameTxFd.setEditable(false);
        auditorIdTxFd.setEditable(false);
    }

    public void setPaneContent(BizTrip bizTrip) {
        this.bizTrip = bizTrip;
        nameTxFd.setText(bizTrip.getApplicantName());//姓名
        //出差类型
        if (bizTrip != null) {
            datePick.setSelectedDate(bizTrip.getStartDate());//出差时间
            if (bizTrip.isMorning()) {
                amOrPmCmbBox.setSelectedItem("上午");
            } else {
                amOrPmCmbBox.setSelectedItem("下午");
            }
            locationTxFd.setText(bizTrip.getLocation());//地点
            daysTxFd.setText(String.valueOf(bizTrip.getDays()));//天数
            //人员列表
            String staffIds = bizTrip.getStaffIds();
            Map<String, USMSUser> userMap = StaticDataUtil.getUserMap();
            String peosonName = "";
            if (!StringUtil.isEmpty(staffIds)) {
                String [] ids = staffIds.split(",");
                for (String id : ids) {
                    USMSUser user = userMap.get(id);
                    if (user != null) {
                        peosonName += userMap.get(id).getName() +",";
                    }
                }
            }
            if (peosonName.length() >= 1) {
                auditorIdTxFd.setText(peosonName.substring(0, peosonName.length()-1));
            }
            memoTxAa.setText(bizTrip.getMemo());//事由
        }
    }

    private BizTrip getPaneContent() {
        USMSUser curUser = ClientContext.getUser();
        if (bizTrip == null) {
            //为空则为添加
            bizTrip = new BizTrip();
            //创建者ID、创建者姓名
            bizTrip.setCreatorId(curUser.getId());
            bizTrip.setCreatorName(curUser.getName());
            //申请人
            bizTrip.setApplicantId(applicantUser.getId());
            bizTrip.setApplicantName(applicantUser.getName());//姓名
        }else{
            //不为空则为修改
            bizTrip.setModifierId(curUser.getId());
            bizTrip.setModifierName(curUser.getName()); 
            //如果applicantUser不为空，那么申请人修改了
            if (applicantUser != null) {
                bizTrip.setApplicantId(applicantUser.getId());
                bizTrip.setApplicantName(applicantUser.getName());//姓名
            }
        }
        bizTrip.setLocation(locationTxFd.getText());//地点
        bizTrip.setStartDate(datePick.getSelectedDate());//出差时间
        Date startDate = datePick.getSelectedDate();
        if (amOrPmCmbBox.getSelectedItem().equals("上午")) {
            bizTrip.setMorning(true);
        } else {
            //如果是下午，时间改为12:00:00
            bizTrip.setStartDate(DateUtil.addHour2Date(startDate, 12));//出差时间
            startDate = DateUtil.addHour2Date(startDate, 12);
            bizTrip.setMorning(false);
        }
        Double days = Double.valueOf(daysTxFd.getText());//天数
        bizTrip.setDays(days);
        
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
            bizTrip.setEndDate(startDate);
                
        if (!StringUtil.isEmpty(userIds)) {
            bizTrip.setStaffIds(userIds);
        }
        bizTrip.setMemo(memoTxAa.getText());//事由
        bizTrip.setCreateTime(new Date());  //创建时间
        return bizTrip;
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
            gotoWarnMessage("出差事由内容过长");
            flag = false;
        }
        if(datePick.getSelectedDate() == null){
            gotoWarnMessage("出差日期不能为空");
            flag = false;
        }
        String bizUsers = auditorIdTxFd.getText();
        if (StringUtil.isEmpty(bizUsers)) {
            gotoWarnMessage("请至少选择一个出差人员");
            flag = false;
        }else if (bizUsers.length() > 1024) {
            gotoWarnMessage("出差人员内容过长");
            flag = false;
        }
        
        String day = daysTxFd.getText();
        String reg = "^\\d+(\\.\\d+)?$";
        if (day.isEmpty()) {
            gotoWarnMessage("请填写出差天数");
            flag = false;
        } else if (!day.matches(reg)) {
            gotoWarnMessage("出差天数格式不正确");
            flag = false;
        }
        
        String address = locationTxFd.getText();
        if (address.length() > 265) {
            gotoWarnMessage("出差地点内容过长");
            flag = false;
        }
        if (StringUtil.isEmpty(address)) {
            gotoWarnMessage("请输入出差地点");
            flag = false;
        }
        String name = nameTxFd.getText().trim();
        if (name.isEmpty()) {
            gotoWarnMessage("请选择出差申请人");
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
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        daysTxFd = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        memoTxAa = new javax.swing.JTextArea();
        locationTxFd = new javax.swing.JTextField();
        amOrPmCmbBox = new javax.swing.JComboBox<>();
        datePick = new com.nazca.ui.JDatePicker();
        jScrollPane2 = new javax.swing.JScrollPane();
        auditorIdTxFd = new javax.swing.JTextArea();
        nameTxFd = new javax.swing.JTextField();
        jLinkLabel1 = new com.nazca.ui.JLinkLabel();
        jLabel8 = new javax.swing.JLabel();
        jLinkLabel2 = new com.nazca.ui.JLinkLabel();
        nActionPane1 = new com.nazca.ui.NActionPane();
        cancleBtn = new javax.swing.JButton();
        okBtn = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jLabel3.setText("出差地点：");

        jLabel4.setText("出差时间：");

        jLabel5.setText("出差天数：");

        jLabel6.setText("出差人员：");

        jLabel7.setText("出差事由：");

        memoTxAa.setColumns(20);
        memoTxAa.setLineWrap(true);
        jScrollPane1.setViewportView(memoTxAa);

        amOrPmCmbBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "上午", "下午" }));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        auditorIdTxFd.setColumns(20);
        auditorIdTxFd.setLineWrap(true);
        jScrollPane2.setViewportView(auditorIdTxFd);

        jLinkLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/user_16.png"))); // NOI18N
        jLinkLabel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLinkLabel1ActionPerformed(evt);
            }
        });

        jLabel8.setText("申请人：");

        jLinkLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/user_16.png"))); // NOI18N
        jLinkLabel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLinkLabel2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameTxFd, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(daysTxFd)
                            .addComponent(locationTxFd)
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addComponent(datePick, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(amOrPmCmbBox, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLinkLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLinkLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLinkLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nameTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(locationTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(datePick, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(amOrPmCmbBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(daysTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLinkLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        add(jPanel, java.awt.BorderLayout.CENTER);

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

    private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
        if (analysisContents()) {
            BizTrip biz = getPaneContent();
            addOrUpdateBizTripAgent.setBizTrip(biz);
            addOrUpdateBizTripAgent.start();
        }
    }//GEN-LAST:event_okBtnActionPerformed

    private void cancleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancleBtnActionPerformed
        diag.hideDiag();
    }//GEN-LAST:event_cancleBtnActionPerformed

    private void jLinkLabel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLinkLabel1ActionPerformed
        UsmInfoTreePanel usmPane = new UsmInfoTreePanel();
        List<USMSUser> userList = usmPane.showMe(jLinkLabel1);
        String peopleNameStr = "";
        if (null != userList && userList.size() > 0) {
            userIds = "";
            for (int i = 0; i < userList.size(); i++) {
                USMSUser user = userList.get(i);
                peopleNameStr += user.getName() + "，";
                userIds += user.getId() + ",";
            }
            auditorIdTxFd.setText(peopleNameStr.substring(0, peopleNameStr.length() - 1));
            userIds = userIds.substring(0, userIds.length() - 1);
//            userIds = peopleNameStr;
        }
//        UIUtilities.infoDlg(this, "提示信息", userIds + " : " + peopleNameStr);
    }//GEN-LAST:event_jLinkLabel1ActionPerformed

    private void jLinkLabel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLinkLabel2ActionPerformed
        SingerUsmInfoTreePanel usmPane = new SingerUsmInfoTreePanel();
        applicantUser = usmPane.showMe(jLinkLabel2);
        if (applicantUser != null) {
            nameTxFd.setText(applicantUser.getName());
        }
    }//GEN-LAST:event_jLinkLabel2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> amOrPmCmbBox;
    private javax.swing.JTextArea auditorIdTxFd;
    private javax.swing.JButton cancleBtn;
    private com.nazca.ui.JDatePicker datePick;
    private javax.swing.JTextField daysTxFd;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private com.nazca.ui.JLinkLabel jLinkLabel1;
    private com.nazca.ui.JLinkLabel jLinkLabel2;
    private javax.swing.JPanel jPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField locationTxFd;
    private javax.swing.JTextArea memoTxAa;
    private com.nazca.ui.NActionPane nActionPane1;
    private javax.swing.JTextField nameTxFd;
    private javax.swing.JButton okBtn;
    // End of variables declaration//GEN-END:variables

    AgentListener<BizTrip> agentListener = new AgentListener<BizTrip>() {
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
        public void onSucceeded(BizTrip result, long seq) {
            okBtn.setEnabled(true);
            cancleBtn.setEnabled(true);
            setEnableComp(true);
            nActionPane1.getWaitingProcess().setVisible(false);
            nActionPane1.getWaitingProcess().setIndeterminate(false);
            NLabelMessageTool.goodNewsMessage(nActionPane1.getMsgLabel(), "提交成功");
            nActionPane1.getMsgLabel().setVisible(true);
            NInternalDiag.findNInternalDiag(AddOrUpdateBizTripPanel.this).hideDiag(result);
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
        locationTxFd.setEnabled(flag);
        datePick.setEnabled(flag);
        amOrPmCmbBox.setEnabled(flag);
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
