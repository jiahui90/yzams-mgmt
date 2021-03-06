/*
 * SetHoliday.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-07 11:06:32
 */
package com.yz.ams.client.ui.holiday;

import com.nazca.ui.NComponentStyleTool;
import com.nazca.ui.NInternalDiag;
import com.nazca.usm.model.USMSUser;
import com.nazca.util.NazcaFormater;
import com.nazca.util.TimeFairy;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.OKCancelPanelListener;
import com.yz.ams.client.agent.AddHolidayAgent;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.util.ClientUtils;
import com.yz.ams.consts.HolidayTypeEnum;
import com.yz.ams.model.Holiday;
import com.yz.ams.util.DateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class AddHolidayPanel extends javax.swing.JPanel {

    private NInternalDiag<List<Holiday>, JComponent> diag = null;
    private List<Date> dates = null;
    private List<Holiday> holidays = null;
    private AddHolidayAgent addHolidayAgent = null;
    private String holidayDesc = "";
    private String beforeDesc = "";
    private String beforeType = "";
    private HolidayTypeEnum holidayType = null;
    private USMSUser curUser = ClientContext.getUser(); 
    /**
     * Creates new form SetHoliday
     */
    public AddHolidayPanel() {
        initComponents();
        initAgent();
        dates = new ArrayList<>();
    }

    private void initAgent() {
        seletedDates.setEditable(false);
        addHolidayAgent = new AddHolidayAgent();
        addHolidayAgent.addListener(agentListener);
        oKCancelPanel1.addOKCancelListener(new OKCancelPanelListener() {
            @Override
            public void onOKClicked() {
                if (checkHolidayName()) {
                    String desc = holidayNameTxFd.getText().trim();
                    for (Holiday h : holidays) {
                        h.setHolidayType(holidayType);
                        h.setHolidayName(desc);
                        h.setModifierId(curUser.getId());
                        h.setModifierName(curUser.getName());
                        h.setModifyTime(new Date());
                    }
                    addHolidayAgent.setParam(holidays);
                    addHolidayAgent.start();
                }
            }

            @Override
            public void onCancelClicked() {
                diag.hideDiag();
            }
        });
    }

    public List<Holiday> showMe(JComponent parent, List<Holiday> holidays) {
        diag = new NInternalDiag<List<Holiday>, JComponent>("修改假期设置", ClientUtils.buildImageIcon("modi_holiday.png"), this, 500, 250);
        this.holidays = holidays;
        setSelectedDateTxFd();
        return diag.showInternalDiag(parent);
    }
    //所选日期文本框的设置
    private void setSelectedDateTxFd(){
        StringBuffer sb=new StringBuffer();
            if(holidays.get(0).getHolidayType() == null){ //假期类型为空
                if(DateUtil.isWorkingDays(holidays.get(0).getHolidayDate())){
                    workdayBtn.setSelected(true);
                }else{
                    holidayBtn.setSelected(true);
                }
                holidayBtn.setSelected(true);
            }else{ //假期类型不为空
                if(holidays.get(0).getHolidayType() == HolidayTypeEnum.HOLIDAY){
                    holidayBtn.setSelected(true);
                }else{
                    workdayBtn.setSelected(true);
                }
            }
                
        //得到所选日期
        for (Holiday h : holidays) {
            sb.append(NazcaFormater.getSimpleDateString(h.getHolidayDate()));
            sb.append(",");
        }
        
        String datesStr = sb.toString();
        seletedDates.setText(datesStr=datesStr.substring(0, datesStr.length() - 1));
    }
    private boolean checkHolidayName() {
        holidayDesc = holidayNameTxFd.getText();
        if (holidayDesc.trim().length() > 15) {
            oKCancelPanel1.gotoErrorMode("备注最多只能输入15个字符!");
            NComponentStyleTool.errorStyle(holidayNameTxFd);
            return false;
        } else {
            NComponentStyleTool.normalStyle(holidayNameTxFd);
        }
        return true;
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
        jPanel1 = new javax.swing.JPanel();
        holidayBtn = new javax.swing.JRadioButton();
        workdayBtn = new javax.swing.JRadioButton();
        holidayNameTxFd = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        seletedDates = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        oKCancelPanel1 = new com.yz.ams.client.OKCancelPanel();

        setPreferredSize(new java.awt.Dimension(220, 100));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(250, 200));

        buttonGroup1.add(holidayBtn);
        holidayBtn.setText("节假日");
        holidayBtn.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                holidayBtnItemStateChanged(evt);
            }
        });

        buttonGroup1.add(workdayBtn);
        workdayBtn.setText("工作日");
        workdayBtn.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                workdayBtnItemStateChanged(evt);
            }
        });

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        seletedDates.setColumns(20);
        seletedDates.setLineWrap(true);
        seletedDates.setRows(2);
        seletedDates.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        seletedDates.setMargin(new java.awt.Insets(0, 0, 0, 0));
        seletedDates.setMaximumSize(new java.awt.Dimension(100, 250));
        jScrollPane1.setViewportView(seletedDates);

        jLabel1.setText("标记为：");

        jLabel2.setText("备注：");

        jLabel3.setText("所选日期：");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(holidayBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(workdayBtn))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                    .addComponent(holidayNameTxFd))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(holidayBtn)
                    .addComponent(workdayBtn))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(holidayNameTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLabel2.getAccessibleContext().setAccessibleName("备    注：");

        add(jPanel1, java.awt.BorderLayout.CENTER);
        add(oKCancelPanel1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void holidayBtnItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_holidayBtnItemStateChanged
        if (holidayBtn.isSelected()) {
            holidayType = HolidayTypeEnum.HOLIDAY;
        }
    }//GEN-LAST:event_holidayBtnItemStateChanged

    private void workdayBtnItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_workdayBtnItemStateChanged
        if (workdayBtn.isSelected()) {
            holidayType = HolidayTypeEnum.WORKDAY;
        }
    }//GEN-LAST:event_workdayBtnItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton holidayBtn;
    private javax.swing.JTextField holidayNameTxFd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.yz.ams.client.OKCancelPanel oKCancelPanel1;
    private javax.swing.JTextArea seletedDates;
    private javax.swing.JRadioButton workdayBtn;
    // End of variables declaration//GEN-END:variables

    AgentListener<List<Holiday>> agentListener = new AgentListener<List<Holiday>>() {
        @Override
        public void onStarted(long seq) {
            holidayNameTxFd.setEnabled(false);
            holidayBtn.setEnabled(false);
            workdayBtn.setEnabled(false);
            oKCancelPanel1.gotoWaitMode("正在修改假期...");
        }

        @Override
        public void onSucceeded(List<Holiday> result, long seq) {

            oKCancelPanel1.gotoSuccessMode("修改假期成功！");
            new Thread() {
                @Override
                public void run() {
                    new TimeFairy().sleepIfNecessary();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            diag.hideDiag(result);
                        }
                    });
                }
            }.start();
        }

        @Override
        public void onFailed(String errMsg, int errCode, long seq) {
            holidayNameTxFd.setEnabled(true);
            holidayBtn.setEnabled(true);
            workdayBtn.setEnabled(true);
            oKCancelPanel1.gotoErrorMode(errMsg, errCode);
        }

    };
}
