/*
 * ModifyPaidVacationPanel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-02 17:44:25
 */
package com.yz.ams.client.ui.PaidVacationMgmt;

import com.nazca.ui.NComponentStyleTool;
import com.nazca.ui.NInternalDiag;
import com.nazca.ui.NLabelMessageTool;
import com.nazca.ui.UIUtilities;
import com.nazca.usm.model.USMSUser;
import com.nazca.util.StringUtil;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.agent.ModifyPaidVacationAgent;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.util.ClientUtils;
import com.yz.ams.util.DateUtil;
import com.yz.ams.model.PaidVacation;
import com.yz.ams.model.wrap.mgmt.PaidVacationWrap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 修改年假信息的面板
 *
 * @author 赵洪坤 <zhaohongkun@yzhtech.com>
 */
public class ModifyPaidVacationPanel extends javax.swing.JPanel {

    ModifyPaidVacationAgent addOrUpdatePaidVacationAgent = null;
    private NInternalDiag<PaidVacationWrap, JComponent> diag = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date systemTime = new Date();
    //显示年假信息
    private PaidVacationWrap paidVacation = null;

    /**
     * Creates new form ModifyPaidVacationPanel
     */
    public ModifyPaidVacationPanel() {
        initComponents();
        initUI();
        initAgentAndListener();
    }

    private void initUI() {
        UIUtilities.makeItLikeLabel(nameTxFd);
        UIUtilities.makeItLikeLabel(applicantionIdTxFd);
        UIUtilities.makeItLikeLabel(takingWorkDateTxFd);
        UIUtilities.makeItLikeLabel(takingWorkYearsTxFd);
        UIUtilities.makeItLikeLabel(lastYearRemainTxFd);
        UIUtilities.makeItLikeLabel(totalVacationDaysTxFd);
        NComponentStyleTool.goodNewsStyle(updateBtn);

    }

    private void initAgentAndListener() {
        addOrUpdatePaidVacationAgent = new ModifyPaidVacationAgent();
        addOrUpdatePaidVacationAgent.addListener(agentListener);
        officialDaysTxFd.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                change();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                change();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                change();
            }
        });
        innerDaysTxFd.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                change();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                change();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                change();
            }
        });
    }

    private void change() {
        double officalDays = 0;
        double innerDays = 0;
        double lastYearRemainDays = 0;
        String offiDays;
        if (!StringUtil.isEmpty(innerDaysTxFd.getText().trim())) {
            innerDays = Double.valueOf(innerDaysTxFd.getText());
        }
        if (!StringUtil.isEmpty(lastYearRemainTxFd.getText().trim())) {
            lastYearRemainDays = Double.valueOf(lastYearRemainTxFd.getText());
        }
        if(officialDaysTxFd.getText() != null){
            offiDays = officialDaysTxFd.getText().trim();
        }else{
            offiDays = officialDaysTxFd.getText();
        }
        if (!StringUtil.isEmpty(offiDays)) {
            officalDays = Double.valueOf(offiDays);
        }
        totalVacationDaysTxFd.setText(String.valueOf(officalDays + innerDays + lastYearRemainDays));
    }

    public PaidVacationWrap showMe(JComponent parent) {
        diag = new NInternalDiag<>("修改年假", ClientUtils.buildImageIcon("modi-annual.png"), this, 470, 190);
        updateBtn.setText("确定");
        return diag.showInternalDiag(parent);
    }

    /**
     * 验证是不是数字
     *
     * @param orginal
     * @return
     */
    private boolean isMatch(String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Pattern pattern2 = Pattern.compile("\\+{0,1}[0]\\.[0-9]*|\\+{0,1}[0-9]\\d*\\.\\d*");
        Matcher isNum = pattern.matcher(orginal);
        Matcher isNum2 = pattern2.matcher(orginal);
        return isNum.matches()||isNum2.matches();
    }

    private boolean analysisContents() {
        boolean flag = true;
        //上午开始时间
        String officialDays = officialDaysTxFd.getText();
        String innerDays = innerDaysTxFd.getText();

        //法定年假天数check
        if (officialDays == null || officialDays.length() <= 0) {
            gotoWarnMessage("法定年假天数不能为空");
            flag = false;
        } else if (!isMatch(officialDays)) {
            gotoWarnMessage("法定年假天数必须为正小数");
            flag = false;
        }

        //内部年假天数check
        if (innerDays == null || innerDays.length() <= 0) {
            gotoWarnMessage("内部年假天数不能为空");
            flag = false;
        } else if (!isMatch(innerDays)) {
            gotoWarnMessage("内部年假天数必须为正小数");
            flag = false;
        }

        return flag;
    }

    private void gotoWarnMessage(String msg) {
        updateBtn.setEnabled(true);
        nActionPane1.getWaitingProcess().setVisible(false);
        nActionPane1.getWaitingProcess().setIndeterminate(false);
        NLabelMessageTool.warningMessage(nActionPane1.getMsgLabel(), msg);
        nActionPane1.getMsgLabel().setVisible(true);
    }

    public void initPaneContent(PaidVacationWrap paidVacation) {
        this.paidVacation = paidVacation;
        PaidVacation vac = paidVacation.getPaidVacationinfo();
        if (vac != null) {
            //姓名
            if (paidVacation.getUserName() == null) {
                nameTxFd.setText("--");
            } else {
                nameTxFd.setText(paidVacation.getUserName());
            }

            //工号
            if (paidVacation.getEmployeeNumber() == null) {
                applicantionIdTxFd.setText("--");
            } else {
                applicantionIdTxFd.setText(paidVacation.getEmployeeNumber());
            }

            //入职时间
            if (paidVacation.getEntryTime() == null) {
                takingWorkDateTxFd.setText("--");
            } else {
                takingWorkDateTxFd.setText(sdf.format(paidVacation.getEntryTime()));
            }

            //入职年限
            Date d = (Date) paidVacation.getEntryTime();
            if (d != null) {
                try {
                    takingWorkYearsTxFd.setText(String.valueOf(DateUtil.getDateDiffence(d,systemTime)));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            } else {
                takingWorkYearsTxFd.setText("--");
            }

            //法定年假天数
            officialDaysTxFd.setText(String.valueOf(vac.getOfficialDays()));

            //内部年天数
            innerDaysTxFd.setText(String.valueOf(vac.getInnerDays()));

            //剩余年假天数
            lastYearRemainTxFd.setText(String.valueOf(vac.getLastYearDays()));

            String totalVacationDays = String.valueOf(Double.parseDouble(officialDaysTxFd.getText()) + Double.parseDouble(innerDaysTxFd.getText()) + Double.parseDouble(lastYearRemainTxFd.getText()));
            //总共年假天数
            totalVacationDaysTxFd.setText(totalVacationDays);

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        nameLb = new javax.swing.JLabel();
        nameTxFd = new javax.swing.JTextField();
        lastYearRemainLb = new javax.swing.JLabel();
        takingWorkDateTxFd = new javax.swing.JTextField();
        officialDaysLb = new javax.swing.JLabel();
        officialDaysTxFd = new javax.swing.JTextField();
        takingWorkLb = new javax.swing.JLabel();
        lastYearRemainTxFd = new javax.swing.JTextField();
        takingWorkYearsLb = new javax.swing.JLabel();
        innerDaysTxFd = new javax.swing.JTextField();
        totalVacationDaysLb = new javax.swing.JLabel();
        takingWorkYearsTxFd = new javax.swing.JTextField();
        applicantionIdLb = new javax.swing.JLabel();
        totalVacationDaysTxFd = new javax.swing.JTextField();
        innerDaysLb = new javax.swing.JLabel();
        applicantionIdTxFd = new javax.swing.JTextField();
        legalDaysLb = new javax.swing.JLabel();
        innerDayLb = new javax.swing.JLabel();
        nActionPane1 = new com.nazca.ui.NActionPane();
        cancleBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWidths = new int[] {3};
        jPanel2.setLayout(jPanel2Layout);

        nameLb.setText("姓名：");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel2.add(nameLb, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 35);
        jPanel2.add(nameTxFd, gridBagConstraints);

        lastYearRemainLb.setText("上年剩余法定年假天数：");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 10, 0);
        jPanel2.add(lastYearRemainLb, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 35);
        jPanel2.add(takingWorkDateTxFd, gridBagConstraints);

        officialDaysLb.setText("法定年假天数：");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel2.add(officialDaysLb, gridBagConstraints);

        officialDaysTxFd.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                officialDaysTxFdInputMethodTextChanged(evt);
            }
        });
        officialDaysTxFd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                officialDaysTxFdActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel2.add(officialDaysTxFd, gridBagConstraints);

        takingWorkLb.setText("入职时间：");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel2.add(takingWorkLb, gridBagConstraints);

        lastYearRemainTxFd.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                lastYearRemainTxFdInputMethodTextChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 35);
        jPanel2.add(lastYearRemainTxFd, gridBagConstraints);

        takingWorkYearsLb.setText("入职年限：");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel2.add(takingWorkYearsLb, gridBagConstraints);

        innerDaysTxFd.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                innerDaysTxFdInputMethodTextChanged(evt);
            }
        });
        innerDaysTxFd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                innerDaysTxFdActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel2.add(innerDaysTxFd, gridBagConstraints);

        totalVacationDaysLb.setText("总年假天数：");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanel2.add(totalVacationDaysLb, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 35);
        jPanel2.add(takingWorkYearsTxFd, gridBagConstraints);

        applicantionIdLb.setText("工号：");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel2.add(applicantionIdLb, gridBagConstraints);

        totalVacationDaysTxFd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalVacationDaysTxFdActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 35);
        jPanel2.add(totalVacationDaysTxFd, gridBagConstraints);

        innerDaysLb.setText("内部年假天数：");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel2.add(innerDaysLb, gridBagConstraints);

        applicantionIdTxFd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applicantionIdTxFdActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 35);
        jPanel2.add(applicantionIdTxFd, gridBagConstraints);

        legalDaysLb.setText("天");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 20);
        jPanel2.add(legalDaysLb, gridBagConstraints);

        innerDayLb.setText("天");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 20);
        jPanel2.add(innerDayLb, gridBagConstraints);

        add(jPanel2);

        cancleBtn.setText("取消");
        cancleBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cancleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancleBtnActionPerformed(evt);
            }
        });
        nActionPane1.add(cancleBtn);

        updateBtn.setText("确定");
        updateBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });
        nActionPane1.add(updateBtn);

        add(nActionPane1);
    }// </editor-fold>//GEN-END:initComponents
    /**
     * 获取面板中的信息，用于修改年假信息
     *
     * @return
     */
    private PaidVacation getPaneContent() {
        USMSUser curUser = ClientContext.getUser();
        PaidVacation vac = null;
        vac = paidVacation.getPaidVacationinfo();
        vac.setModifierName(curUser.getName());
        vac.setModifierId(curUser.getId());
        vac.setModifyTime(new Date());
        vac.setOfficialDays(Double.parseDouble(officialDaysTxFd.getText()));
        vac.setInnerDays(Double.valueOf(innerDaysTxFd.getText()));
        return vac;
    }

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        if (analysisContents()) {
            PaidVacation vac = getPaneContent();
            paidVacation.setPaidVacationinfo(vac);
            addOrUpdatePaidVacationAgent.setPaidVacation(paidVacation);
            addOrUpdatePaidVacationAgent.start();
        }
    }//GEN-LAST:event_updateBtnActionPerformed

    private void cancleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancleBtnActionPerformed
        diag.hideDiag();
    }//GEN-LAST:event_cancleBtnActionPerformed

    private void officialDaysTxFdInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_officialDaysTxFdInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_officialDaysTxFdInputMethodTextChanged

    private void officialDaysTxFdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_officialDaysTxFdActionPerformed
        updateBtnActionPerformed(null);
    }//GEN-LAST:event_officialDaysTxFdActionPerformed

    private void lastYearRemainTxFdInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_lastYearRemainTxFdInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_lastYearRemainTxFdInputMethodTextChanged

    private void innerDaysTxFdInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_innerDaysTxFdInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_innerDaysTxFdInputMethodTextChanged

    private void totalVacationDaysTxFdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalVacationDaysTxFdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalVacationDaysTxFdActionPerformed

    private void applicantionIdTxFdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applicantionIdTxFdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_applicantionIdTxFdActionPerformed

    private void innerDaysTxFdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_innerDaysTxFdActionPerformed
        updateBtnActionPerformed(null);
    }//GEN-LAST:event_innerDaysTxFdActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel applicantionIdLb;
    private javax.swing.JTextField applicantionIdTxFd;
    private javax.swing.JButton cancleBtn;
    private javax.swing.JLabel innerDayLb;
    private javax.swing.JLabel innerDaysLb;
    private javax.swing.JTextField innerDaysTxFd;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lastYearRemainLb;
    private javax.swing.JTextField lastYearRemainTxFd;
    private javax.swing.JLabel legalDaysLb;
    private com.nazca.ui.NActionPane nActionPane1;
    private javax.swing.JLabel nameLb;
    private javax.swing.JTextField nameTxFd;
    private javax.swing.JLabel officialDaysLb;
    private javax.swing.JTextField officialDaysTxFd;
    private javax.swing.JTextField takingWorkDateTxFd;
    private javax.swing.JLabel takingWorkLb;
    private javax.swing.JLabel takingWorkYearsLb;
    private javax.swing.JTextField takingWorkYearsTxFd;
    private javax.swing.JLabel totalVacationDaysLb;
    private javax.swing.JTextField totalVacationDaysTxFd;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables

    AgentListener<PaidVacationWrap> agentListener = new AgentListener<PaidVacationWrap>() {
        @Override
        public void onStarted(long seq) {
            updateBtn.setEnabled(false);
            cancleBtn.setEnabled(false);
            nActionPane1.getWaitingProcess().setVisible(true);
            nActionPane1.getWaitingProcess().setIndeterminate(true);
            nActionPane1.getMsgLabel().setVisible(true);
            NLabelMessageTool.infoMessage(nActionPane1.getMsgLabel(), "正在提交");
        }

        @Override
        public void onSucceeded(PaidVacationWrap result, long seq) {
            updateBtn.setEnabled(true);
            cancleBtn.setEnabled(true);
            nActionPane1.getWaitingProcess().setVisible(false);
            nActionPane1.getWaitingProcess().setIndeterminate(false);
            NLabelMessageTool.goodNewsMessage(nActionPane1.getMsgLabel(), "提交成功");
            nActionPane1.getMsgLabel().setVisible(true);
            NInternalDiag.findNInternalDiag(ModifyPaidVacationPanel.this).hideDiag(result);
        }

        @Override
        public void onFailed(String errMsg, int errCode, long seq) {
            updateBtn.setEnabled(true);
            cancleBtn.setEnabled(true);
            nActionPane1.getWaitingProcess().setVisible(false);
            nActionPane1.getWaitingProcess().setIndeterminate(false);
            NLabelMessageTool.errorMessage(nActionPane1.getMsgLabel(), errCode, errMsg);
            nActionPane1.getMsgLabel().setVisible(true);
        }
    };
}
