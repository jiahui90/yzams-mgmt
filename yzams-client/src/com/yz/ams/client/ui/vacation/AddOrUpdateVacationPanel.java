/*
 * CaeateVacationPanel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-20 09:49:17
 */
package com.yz.ams.client.ui.vacation;

import com.nazca.ui.NInternalDiag;
import com.nazca.ui.NLabelMessageTool;
import com.nazca.ui.UIUtilities;
import com.nazca.ui.model.SimpleObjectListModel;
import com.nazca.usm.model.USMSUser;
import com.nazca.util.TimeFairy;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.agent.AddOrUpdateVacationAgent;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.agent.QueryHolidaysAgent;
import com.yz.ams.client.agent.getRestVacationDaysAgent;
import com.yz.ams.client.model.VacationTypeTableModel;
import com.yz.ams.client.renderer.CombBoxInTableEditor;
import com.yz.ams.client.renderer.TextFieldInTableEditor;
import com.yz.ams.client.ui.SingerUsmInfoTreePanel;
import com.yz.ams.client.util.ClientUtils;
import com.yz.ams.consts.HolidayTypeEnum;
import com.yz.ams.consts.VacationTypeEnum;
import com.yz.ams.model.Holiday;
import com.yz.ams.model.Vacation;
import com.yz.ams.model.VacationDetail;
import com.yz.ams.model.wrap.mgmt.VacationInfo;
import com.yz.ams.util.DateUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Your Name <Song Haixiang >
 */
public class AddOrUpdateVacationPanel extends javax.swing.JPanel {
    private NInternalDiag<VacationInfo, JComponent> diag = null;
    private AddOrUpdateVacationAgent addOrUpdateVacationAgent = null;
    SimpleObjectListModel<VacationTypeEnum> typeModel = new SimpleObjectListModel<>();
    private QueryHolidaysAgent queryHolidayAgent = null;
    private getRestVacationDaysAgent getRestDaysAgent = null;
    
    private CombBoxInTableEditor typeEditor = null;
    private VacationTypeTableModel typeTableModel = null;
    private TextFieldInTableEditor doubleEditor = null;
    private List<VacationDetail> delDetailLis = null;
    private List<Holiday> holidayList = null;
    private VacationInfo vacation = null; //TODO包含请假详情的类
    private Map<String, USMSUser> userMap = new HashMap<>();
    private List<String> userIdsInTeam = new ArrayList<>();
    private Double restInnerDays = 0.0;
    private Double restLegalDays = 0.0;
    private Double restDays = 0.0;
    private Double vacationDays = 0.0;
    
    /**
     * Creates new form CaeateVacationPanel
     */
    public AddOrUpdateVacationPanel() {
        initComponents();
        initModel();
        initAgentAndListener();
        queryHoliday();
    }

    private void initAgentAndListener() {
        addOrUpdateVacationAgent = new AddOrUpdateVacationAgent();
        addOrUpdateVacationAgent.addListener(agentListener);
        queryHolidayAgent = new QueryHolidaysAgent();
        queryHolidayAgent.addListener(queryHolidayLisener);
        getRestDaysAgent = new getRestVacationDaysAgent();
        getRestDaysAgent.addListener(queryLostDaysAgentLisener);
        typeTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column == 1) {
                    updateTotalDays();
                }
            }
        });
    }

    private void initModel() {
        JComboBox cmBox = new JComboBox();
        typeModel.setObjectList(getVacationType());
        cmBox.setModel(typeModel);
        cmBox.setSelectedIndex(0);
        typeEditor = new CombBoxInTableEditor(cmBox);
        doubleEditor = new TextFieldInTableEditor();
        typeTableModel = new VacationTypeTableModel();
        jTable2.setModel(typeTableModel);
        jTable2.getColumnModel().getColumn(0).setCellEditor(typeEditor);
        jTable2.getColumnModel().getColumn(1).setCellEditor(doubleEditor);
    }

    private void queryHoliday() {
        queryHolidayAgent.start();
    }
    
    private List<VacationTypeEnum>  getVacationType(){
        List<VacationTypeEnum> list = Arrays.asList(VacationTypeEnum.values());
        List arrayList = new ArrayList(list);
        for (Iterator it = arrayList.iterator(); it.hasNext();) {
            VacationTypeEnum e = (VacationTypeEnum)it.next();
            if (e.equals(VacationTypeEnum.PAID_INNER) || e.equals(VacationTypeEnum.PAID_LEGAL)){
                it.remove();
            }
        }
        return arrayList;
    }
    public VacationInfo showMe(JComponent parent) {
        diag = new NInternalDiag<VacationInfo, JComponent>("添加请假", ClientUtils.buildImageIcon("add-vacation.png"), this, 750, 550);
        addOrUpdateBtn.setText("保存");
        return diag.showInternalDiag(parent);
    }

    public VacationInfo showMe1(JComponent parent) {
        diag = new NInternalDiag<VacationInfo, JComponent>("修改请假", ClientUtils.buildImageIcon("modi-vacation.png"), this, 750, 550);
        addOrUpdateBtn.setText("保存");
        return diag.showInternalDiag(parent);
    }

    public void initPaneContent(VacationInfo vacation) {
        personLinkLbl.setEnabled(false);
        this.vacation = vacation;
        getRestDaysAgent.setParameters(vacation.getVacation().getApplicantId());
        getRestDaysAgent.start();
        Vacation vac = vacation.getVacation();
        if (vac != null) {
            userIdHiddenTxfd.setText(vac.getApplicantId());
            userNameTxFd.setText(vac.getApplicantName());
            datePick.setSelectedDate(vac.getVacationDate());
            if (vac.isMorning()) {
                amOrpmCombBox.setSelectedItem("上午");
            } else {
                amOrpmCombBox.setSelectedItem("下午");
            }
            daysLbl.setText(vac.getTotalDays().toString());
            memoTxAa.setText(vac.getMemo());
        }
        List<VacationDetail> detailsLis = vacation.getVacationDetail();
        VacationDetail detail = reArrangeVacation(detailsLis);
        detailsLis.add(detail);
        typeTableModel.setDatas(detailsLis);
    }

    private VacationDetail reArrangeVacation(List<VacationDetail> detailsLis) {
        boolean isAnnualLeave = false;
        VacationDetail detail = null;
        Date startTime = null;
        Date endTime = null;
        Double days = 0.0;
        for (Iterator it = detailsLis.iterator(); it.hasNext();) {
            boolean isFirst = true;
            
            VacationDetail d = (VacationDetail)it.next();
            VacationTypeEnum e = d.getVacationType();
            if (e.equals(VacationTypeEnum.PAID_INNER) || e.equals(VacationTypeEnum.PAID_LEGAL)){
                if (isFirst) {
                    startTime = d.getStartDate();
                }
                days += d.getVacationDays();
                if (e.equals(VacationTypeEnum.PAID_INNER)) {
                    this.restInnerDays = d.getVacationDays();
                }else {
                    this.restLegalDays = d.getVacationDays();
                }
                endTime = d.getEndDate();
                isAnnualLeave = true;
                it.remove();
            }
            isFirst = false;
        }
        if (isAnnualLeave) {
            detail = new VacationDetail();
            detail.setDetailId(UUID.randomUUID().toString());
            detail.setVacationType(VacationTypeEnum.ANNUAL_LEAVE);
            detail.setStartDate(startTime);
            detail.setEndDate(endTime);
            detail.setVacationDays(days);
        }
        return detail;
    }

    /**
     * 获取面板中的信息，用于添加和修改请假信息
     *
     * @return
     */
    private Vacation getPaneContent() {
        USMSUser curUser = ClientContext.getUser();
        Vacation vac = null;
        if (vacation == null) {
            vacation = new VacationInfo();
            vac = new Vacation();
            vac.setCreatorId(curUser.getId());
            vac.setCreatorName(curUser.getName());
        } else {
            vac = vacation.getVacation();
            vac.setModifierId(curUser.getId());
            vac.setModifierName(curUser.getName());
        }
        vac.setApplicantId(userIdHiddenTxfd.getText());
        
        vac.setApplicantName(userNameTxFd.getText());
        vac.setVacationDate(datePick.getSelectedDate());
        if (amOrpmCombBox.getSelectedItem().equals("上午")) {
            vac.setMorning(true);

        } else {
            vac.setMorning(false);
            //不是早晨，从12:00开始
            vac.setVacationDate(DateUtil.getLastHourStartTime(datePick.getSelectedDate(), 12));
        }
        vac.setTotalDays(Double.valueOf(daysLbl.getText()));
        vac.setMemo(memoTxAa.getText());
        return vac;
    }

    private boolean analysisContents() {
        boolean flag = true;
        String memo = memoTxAa.getText();
        if (memo.length() > 64) {
            gotoWarnMessage("请假事由内容过长");
            flag = false;
        }
        if(datePick.getSelectedDate() == null){
            gotoWarnMessage("请假日期不能为空");
            flag = false;
        }
        String day = daysLbl.getText();
        if (Double.valueOf(day) <= 0) {
            gotoWarnMessage("请假总天数不能小于0");
            flag = false;
        }
        List<VacationDetail> details = typeTableModel.getDatas();
        if (details == null || details.size() <= 0) {
            gotoWarnMessage("请至少添加一条请假类型");
            flag = false;
        } else {
            for (VacationDetail d : details) {
                if (d.getVacationDays() == 0 ) {
                    gotoWarnMessage("请假天数不能为0");
                    flag = false;
                }
                if (d.getVacationType().equals(VacationTypeEnum.ANNUAL_LEAVE)) {
                    if(d.getVacationDays() > restDays){
                        gotoWarnMessage("请年假天数不能大于可用年假天数，年假剩余" + String.valueOf(restDays) + "天");
                        flag = false;
                    }
                }
            }
        }
        String name = userNameTxFd.getText().trim();
        if (name.isEmpty()) {
            gotoWarnMessage("请选择请假用户");
            flag = false;
        }
        return flag;
    }

    private void gotoWarnMessage(String msg) {
        addOrUpdateBtn.setEnabled(true);
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

        jLabel1 = new javax.swing.JLabel();
        userIdHiddenTxfd = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        memoTxAa = new javax.swing.JTextArea();
        userNameTxFd = new javax.swing.JTextField();
        amOrpmCombBox = new javax.swing.JComboBox<>();
        datePick = new com.nazca.ui.JDatePicker();
        personLinkLbl = new com.nazca.ui.JLinkLabel();
        jToolBar2 = new javax.swing.JToolBar();
        addTypeBtu = new javax.swing.JButton();
        deleteTypeBtu = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jLabel4 = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jLabel3 = new javax.swing.JLabel();
        daysLbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        nActionPane1 = new com.nazca.ui.NActionPane();
        cancleBtn = new javax.swing.JButton();
        addOrUpdateBtn = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        userIdHiddenTxfd.setText("jTextField1");

        setLayout(new java.awt.BorderLayout());

        jPanel1.setMaximumSize(new java.awt.Dimension(10, 10));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(10, 10));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(10, 10));

        memoTxAa.setLineWrap(true);
        jScrollPane1.setViewportView(memoTxAa);

        userNameTxFd.setEditable(false);

        amOrpmCombBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "上午", "下午" }));

        personLinkLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/user_16.png"))); // NOI18N
        personLinkLbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                personLinkLblActionPerformed(evt);
            }
        });

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        addTypeBtu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/add_16.png"))); // NOI18N
        addTypeBtu.setFocusable(false);
        addTypeBtu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addTypeBtu.setMargin(new java.awt.Insets(2, 5, 2, 5));
        addTypeBtu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTypeBtuActionPerformed(evt);
            }
        });
        jToolBar2.add(addTypeBtu);

        deleteTypeBtu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/delete_16.png"))); // NOI18N
        deleteTypeBtu.setFocusable(false);
        deleteTypeBtu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteTypeBtu.setMargin(new java.awt.Insets(2, 5, 2, 5));
        deleteTypeBtu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTypeBtuActionPerformed(evt);
            }
        });
        jToolBar2.add(deleteTypeBtu);
        jToolBar2.add(filler1);

        jLabel4.setForeground(new java.awt.Color(196, 127, 45));
        jLabel4.setText("说明：鼠标双击编辑天数后请回车");
        jToolBar2.add(jLabel4);
        jToolBar2.add(filler2);

        jLabel3.setText("请假天数总计：");
        jToolBar2.add(jLabel3);

        daysLbl.setText("0");
        jToolBar2.add(daysLbl);

        jLabel2.setText(" 天");
        jToolBar2.add(jLabel2);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "类型", "天数"
            }
        ));
        jTable2.setMinimumSize(new java.awt.Dimension(10, 10));
        jScrollPane3.setViewportView(jTable2);

        jLabel5.setText("姓名：");

        jLabel6.setText("请假日期：");

        jLabel7.setText("请假事由：");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userNameTxFd)
                            .addComponent(datePick, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(personLinkLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(amOrpmCombBox, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(userNameTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(personLinkLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(datePick, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(amOrpmCombBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 102, Short.MAX_VALUE)))
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
        jPanel1.getAccessibleContext().setAccessibleDescription("");

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
        diag.hideDiag();
    }//GEN-LAST:event_cancleBtnActionPerformed

    private void addOrUpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrUpdateBtnActionPerformed
        if (analysisContents()) {
            Vacation vac = getPaneContent();
            
            boolean hasSick = false;
            List<VacationDetail> details = typeTableModel.getDatas();
            VacationDetail d = null;
            //开始日期
            Date startDate = vac.getVacationDate();
            boolean isFirstDay = true;
            Iterator it = details.iterator();
            boolean isAddNewDetail = false;
            while (it.hasNext()) {
                VacationDetail detail = (VacationDetail) it.next();
                if (detail.getVacationType().equals(VacationTypeEnum.ANNUAL_LEAVE)){ //年假
                    Double days = detail.getVacationDays();
                    if (restInnerDays > 0) { //内部年假>0
                        if (days <= restInnerDays) {
                            detail.setVacationType(VacationTypeEnum.PAID_INNER);
                            startDate = setDetailObject(isFirstDay, startDate, detail);
                        } else {
                            isAddNewDetail = true;
                            detail.setVacationType(VacationTypeEnum.PAID_INNER);
                            detail.setVacationDays(restInnerDays);
                            startDate = setDetailObject(isFirstDay, startDate, detail);
                            if (DateUtil.getHourOfDate(startDate) != 12) { //如果时间不等于12点
                                startDate = DateUtil.addSecond2Date(startDate, 1); //如果不是第一天且时间为(23:59:59)，开始时间设置为00:00:00
                            }
                            d = new VacationDetail();
                            d.setDetailId(UUID.randomUUID().toString());
                            d.setVacationId(vac.getVacationId());
                            d.setVacationType(VacationTypeEnum.PAID_LEGAL);
                            d.setVacationDays(days - restInnerDays);
                            d.setStartDate(startDate);
                            startDate = setDetailObject(isFirstDay, startDate, d);
                            d.setEndDate(startDate);
                        }
                    } else { //内部年假=0
                        detail.setVacationType(VacationTypeEnum.PAID_LEGAL);
                        startDate = setDetailObject(isFirstDay, startDate, detail);
                    }
                } else {
                    startDate = setDetailObject(isFirstDay, startDate, detail);
                }
                isFirstDay = false;
            }
            if(isAddNewDetail){
                details.add(d);
            }
            vac.setHasSickType(hasSick);
            vacation.setVacation(vac);
            vacation.setVacationDetail(details);

            addOrUpdateVacationAgent.setVacation(vacation);
            addOrUpdateVacationAgent.start();
        }
    }//GEN-LAST:event_addOrUpdateBtnActionPerformed

    private Date setDetailObject(boolean isFirstDay, Date startDate, VacationDetail detail) {
        if (!isFirstDay) {
            if (DateUtil.getHourOfDate(startDate) != 12) { 
                startDate = DateUtil.addSecond2Date(startDate, 1); //如果不是第一天且时间为(23:59:59)，开始时间设置为00:00:00
            }
        }
        while (isWeekendOrHoliday(startDate)) { 
            startDate = DateUtil.getLastHourStartTime(startDate, 24);
        }
        detail.setStartDate(startDate); 
        double days = detail.getVacationDays();
        while (days > 0) {
            if (isWeekendOrHoliday(DateUtil.getZeroTimeOfDay(startDate))) { //如果是休息日，跳过这一天
                startDate = DateUtil.getLastHourStartTime(startDate, 24);
                isFirstDay = false;
                continue;
            }
            days = days - 0.5;
            startDate = DateUtil.getLastHourStartTime(startDate, 12);
        }
        if (DateUtil.getHourOfDate(startDate) == 0) { //结束时间在下午，设置为23:59:59
            startDate = DateUtil.addSecond2Date(startDate, -1);
        }
        detail.setEndDate(startDate); 
        return startDate;
    }
    
    //是节假日或周末
    private boolean isWeekendOrHoliday(Date d) {
        boolean isWorkday = DateUtil.isWorkingDays(d);
        //是工作日
        if (isWorkday) {
            for (Holiday h : holidayList) {
                Date date = h.getHolidayDate();
                if (d.getTime() == date.getTime()) {
                    if (h.getHolidayType().equals(HolidayTypeEnum.HOLIDAY)) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            for (Holiday h : holidayList) {
                Date date = h.getHolidayDate();
                if (d.getTime() == date.getTime()) {
                    if (h.getHolidayType().equals(HolidayTypeEnum.WORKDAY)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
    private void addTypeBtuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTypeBtuActionPerformed
        VacationDetail item = new VacationDetail();
        item.setDetailId(UUID.randomUUID().toString());
        item.setVacationDays(0.0);
        item.setVacationType(VacationTypeEnum.PERSONAL);
        typeTableModel.addData(item);
        updateTotalDays();
    }//GEN-LAST:event_addTypeBtuActionPerformed

    private void personLinkLblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_personLinkLblActionPerformed
        SingerUsmInfoTreePanel usmPane = new SingerUsmInfoTreePanel();
        USMSUser user = usmPane.showMe(personLinkLbl);
        if (user != null) {
            userNameTxFd.setText(user.getName());
            userIdHiddenTxfd.setText(user.getId());
            this.getRestDaysAgent.setParameters(user.getId());
            getRestDaysAgent.start();
        }
    }//GEN-LAST:event_personLinkLblActionPerformed

    private void deleteTypeBtuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteTypeBtuActionPerformed
        int idx = jTable2.getSelectedRow();
        if (idx >= 0) {
            idx = jTable2.convertRowIndexToModel(idx);
            VacationDetail data = typeTableModel.getData(idx);
            boolean b = UIUtilities.okCancelDlg(this, "删除确认信息", "您确定删除该请假详情？删除后不可恢复。");
            if (b) {
                if (delDetailLis == null) {
                    delDetailLis = new ArrayList<>();
                }
                delDetailLis.add(data);
                typeTableModel.deleteData(data);
                updateTotalDays();
            }
        } else {
            UIUtilities.infoDlg(this, "提示信息", "请选择要删除的请假详情");
        }
    }//GEN-LAST:event_deleteTypeBtuActionPerformed

    private void updateTotalDays() {
        double totalDays = 0;
        List<VacationDetail> details = typeTableModel.getDatas();
        for (VacationDetail item : details) {
            totalDays = totalDays + item.getVacationDays();
        }
        daysLbl.setText(String.valueOf(totalDays));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addOrUpdateBtn;
    private javax.swing.JButton addTypeBtu;
    private javax.swing.JComboBox<String> amOrpmCombBox;
    private javax.swing.JButton cancleBtn;
    private com.nazca.ui.JDatePicker datePick;
    private javax.swing.JLabel daysLbl;
    private javax.swing.JButton deleteTypeBtu;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTextArea memoTxAa;
    private com.nazca.ui.NActionPane nActionPane1;
    private com.nazca.ui.JLinkLabel personLinkLbl;
    private javax.swing.JTextField userIdHiddenTxfd;
    private javax.swing.JTextField userNameTxFd;
    // End of variables declaration//GEN-END:variables
    AgentListener<VacationInfo> agentListener = new AgentListener<VacationInfo>() {
        @Override
        public void onStarted(long seq) {
            addOrUpdateBtn.setEnabled(false);
            cancleBtn.setEnabled(false);
            setEnableComp(false);
            nActionPane1.getWaitingProcess().setVisible(true);
            nActionPane1.getWaitingProcess().setIndeterminate(true);
            nActionPane1.getMsgLabel().setVisible(true);
            NLabelMessageTool.infoMessage(nActionPane1.getMsgLabel(), "正在提交");
        }

        @Override
        public void onSucceeded(VacationInfo result, long seq) {
            addOrUpdateBtn.setEnabled(true);
            cancleBtn.setEnabled(true);
            setEnableComp(true);
            nActionPane1.getWaitingProcess().setVisible(false);
            nActionPane1.getWaitingProcess().setIndeterminate(false);
            NLabelMessageTool.goodNewsMessage(nActionPane1.getMsgLabel(), "提交成功");
            nActionPane1.getMsgLabel().setVisible(true);
            new Thread() {
                public void run() {
                    new TimeFairy().sleepIfNecessary();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            NInternalDiag.findNInternalDiag(AddOrUpdateVacationPanel.this).hideDiag(result);
                        }
                    });
                }
            }.start();
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

    private void setEnableComp(boolean flag) {
        personLinkLbl.setEnabled(flag);
        datePick.setEnabled(flag);
        amOrpmCombBox.setEnabled(flag);
        addTypeBtu.setEnabled(flag);
        deleteTypeBtu.setEnabled(flag);
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
    
    AgentListener<List<String>> queryUsersInTeamLis = new AgentListener<List<String>>() {
        @Override
        public void onStarted(long seq) {

        }

        @Override
        public void onSucceeded(List<String> result, long seq) {
            userIdsInTeam = result;
        }

        @Override
        public void onFailed(String errMsg, int errCode, long seq) {

        }
    };
    
    AgentListener<Object> queryLostDaysAgentLisener = new AgentListener<Object>() {
        @Override
        public void onStarted(long seq) {
        }
        

        @Override
        public void onSucceeded(Object result, long seq) {
            String str = (String)result;
            String[] strArr = str.split(",");
            restInnerDays += Double.parseDouble(strArr[0]);
            restLegalDays += Double.parseDouble(strArr[1]);
            restDays = Double.valueOf(restInnerDays + restLegalDays);
        }

        @Override
        public void onFailed(String errMsg, int errCode, long seq) {
        }
        
        
    };
}
