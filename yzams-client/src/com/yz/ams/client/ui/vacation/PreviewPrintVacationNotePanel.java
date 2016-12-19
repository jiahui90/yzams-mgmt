/*
 * PreviewPrintVacationNotePanel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-07-07 12:14:45
 */
package com.yz.ams.client.ui.vacation;

import com.yz.ams.client.ui.statistics.PreviewInfoPanel;
import com.nazca.ui.NInternalDiag;
import com.nazca.ui.NLabelMessageTool;
import com.nazca.ui.util.CardLayoutWrapper;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.agent.QueryVacationNotesAgent;
import com.yz.ams.client.util.ClientUtils;
import com.yz.ams.model.NoteReportListMode;
import com.yz.ams.model.wrap.mgmt.VacationNote;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class PreviewPrintVacationNotePanel extends javax.swing.JPanel {
    private String vacationId;
    private CardLayoutWrapper card2 = null;
    private NInternalDiag<Object, JComponent> diag = null;
    private QueryVacationNotesAgent queryVacationNotesAgent = null;
    private List<VacationNote> noteList = null;
    List<NoteReportListMode> lisReport = null;
    
    private void initUI(){
        card2 = new CardLayoutWrapper(contentPane);
    }
     private void initAgent(){
        queryVacationNotesAgent = new QueryVacationNotesAgent();
        queryVacationNotesAgent.addListener(agentListener);
     }
    /**
     * Creates new form PreviewPrintVacationNotePanel
     */
    public PreviewPrintVacationNotePanel() {
        initComponents();
        initUI();
        initAgent();
    }
    
    public void showMe(JComponent Parent,String vacationId) {
        this.vacationId = vacationId;
        queryNotes();
        diag = new NInternalDiag<Object, JComponent>("请假单打印预览",
                ClientUtils.buildImageIcon("export.png"), this, 877, 600);
        diag.showInternalDiag(Parent);
        
    }
    private void queryNotes(){
        queryVacationNotesAgent.setParameters(vacationId);
        queryVacationNotesAgent.start();
    }
    private void initReport(){
        PreviewInfoPanel pane = new PreviewInfoPanel();
       lisReport = new ArrayList<>();
        NoteReportListMode mode = new NoteReportListMode();
        mode.setNoteLis(noteList);
        lisReport.add(mode);
        pane.previewListInfo(lisReport, null, "/com/yz/ams/client/ui/report/VacationNoteInfoReport.jasper");
        peportPane.add(pane, BorderLayout.CENTER);
        
    }
    
    //打印报表
    private void printVacationNotes(List<NoteReportListMode> listModel){
        try {
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(
                    listModel);
            Map<String, Object> map = new HashMap<String, Object>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(PreviewPrintVacationNotePanel.class.getResourceAsStream("/com/yz/ams/client/ui/report/VacationNoteInfoReport.jasper"), map, source);
            JasperPrintManager.printReport(jasperPrint, true);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPane = new javax.swing.JPanel();
        peportPane = new javax.swing.JPanel();
        nWaitingPanel1 = new com.nazca.ui.NWaitingPanel();
        nActionPane1 = new com.nazca.ui.NActionPane();
        cancelBtn = new javax.swing.JButton();
        printBtn = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        contentPane.setLayout(new java.awt.CardLayout());

        peportPane.setLayout(new java.awt.BorderLayout());
        contentPane.add(peportPane, "CONTENT");
        contentPane.add(nWaitingPanel1, "WAIT");

        add(contentPane, java.awt.BorderLayout.CENTER);

        cancelBtn.setText("取消");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        nActionPane1.add(cancelBtn);

        printBtn.setText("打印");
        printBtn.setIconTextGap(2);
        printBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBtnActionPerformed(evt);
            }
        });
        nActionPane1.add(printBtn);

        add(nActionPane1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void printBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBtnActionPerformed
        try {
            printBtn.setEnabled(false);
            cancelBtn.setEnabled(false);
            NLabelMessageTool.plainMessage(nActionPane1.getMsgLabel(), "正在打印...");
                printVacationNotes(lisReport);
            NLabelMessageTool.goodNewsMessage(nActionPane1.getMsgLabel(), "打印成功...");
            diag.hideDiag();
        } catch (Exception e) {
            e.printStackTrace();
            printBtn.setEnabled(false);
            cancelBtn.setEnabled(true);
            NLabelMessageTool.errorMessage(nActionPane1.getMsgLabel(), "打印失败...");
        }
    }//GEN-LAST:event_printBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        diag.hideDiag();
    }//GEN-LAST:event_cancelBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JPanel contentPane;
    private com.nazca.ui.NActionPane nActionPane1;
    private com.nazca.ui.NWaitingPanel nWaitingPanel1;
    private javax.swing.JPanel peportPane;
    private javax.swing.JButton printBtn;
    // End of variables declaration//GEN-END:variables
    AgentListener<List<VacationNote>> agentListener = new AgentListener<List<VacationNote>>() {
        @Override
        public void onStarted(long seq) {
            card2.show(CardLayoutWrapper.WAIT);
        }

        @Override
        public void onSucceeded(List<VacationNote> result, long seq) {
            noteList = result;
            card2.show(CardLayoutWrapper.CONTENT);
            initReport();
            
        }

        @Override
        public void onFailed(String errMsg, int errCode, long seq) {
            
        }
    };
}
