/*
 * StatisticsMgmtPanel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-14 10:44:41
 */
package com.yz.ams.client.ui.statistics;

import com.nazca.ui.NWaitingPanel;
import com.nazca.ui.TextHinter;
import com.nazca.ui.UIUtilities;
import com.nazca.ui.model.SimpleObjectListModel;
import com.nazca.ui.util.CardLayoutWrapper;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.agent.QueryStatisticsAgent;
import com.yz.ams.client.model.StatisticsTableModel;
import com.yz.ams.client.renderer.StatisticsTableRenderer;
import com.yz.ams.client.util.ClientUtils;
import com.yz.ams.consts.Permissions;
import com.yz.ams.consts.StatTypeEnum;
import com.yz.ams.util.DateUtil;
import com.yz.ams.model.wrap.mgmt.AttendanceMgmtStat;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *出勤统计面板
 * @author 赵洪坤 <zhaohongkun@yzhtech.com>
 */
public class StatisticsMgmtPanel extends javax.swing.JPanel {

    /**
     * Creates new form StatisticsMgmtPanel
     */
    private CardLayoutWrapper card = null;
    private StatisticsTableModel statisticsTableModel = null;
    private QueryStatisticsAgent queryStatisticsAgent = null;
    private final SimpleObjectListModel<Integer> yearComboxModel = new SimpleObjectListModel<>();
    private final SimpleObjectListModel<String> weekComboxModel = new SimpleObjectListModel<>();
    private final SimpleObjectListModel<String> monthComboModel = new SimpleObjectListModel<>();
    private final SimpleObjectListModel<String> testComboModel = new SimpleObjectListModel<>();
    private String viewMode = "";
    List<AttendanceMgmtStat> backResult = null;
    private final StatisticsTableRenderer renderer = new StatisticsTableRenderer();
    private String keyword = "";
    private Date start;
    private Date end;

    public StatisticsMgmtPanel() {
        initComponents();
        initUI();
        initAgentAndListener();
        initModelAndRenderer();
        searchBtnActionPerformed(null);
    }

    private void initUI() {
        searchTxFd.setVisible(true);
        UIUtilities.attachSearchIcon(searchTxFd);//给搜索框加搜索图标
        TextHinter.attach("输入姓名并回车", searchTxFd);
        card = new CardLayoutWrapper(dataPanel);
        setVisiable(false);
        String userId = ClientContext.getUserId();
        if(ClientContext.hasPermission(Permissions.PM) && !ClientContext.hasPermission(Permissions.HR)){
            searchTxFd.setVisible(false);
        }else if(ClientContext.hasPermission(Permissions.ADMIN) || ClientContext.hasPermission(Permissions.CEO) || ClientContext.hasPermission(Permissions.HR)){
            searchTxFd.setVisible(true);
        }else if(ClientContext.hasPermission(Permissions.STAFF)){
            searchTxFd.setVisible(false);
        }
    }

    private void initModelAndRenderer() {
        //年下拉列表框
        statisticsTableModel = new StatisticsTableModel();
        tableComp.setModel(statisticsTableModel);
        statementYearCmBox.setModel(yearComboxModel);
        yearComboxModel.setObjectList(ClientUtils.getYears());
        statementYearCmBox.setSelectedIndex(0);
        statementCmBox.setSelectedIndex(1);
        tableComp.setDefaultRenderer(Object.class, renderer);
    }

    private void initAgentAndListener() {
        queryStatisticsAgent = new QueryStatisticsAgent();
        queryStatisticsAgent.addListener(queryStatisticsAgentLis);
        tableComp.getSelectionModel().addListSelectionListener(new ListSelectionListener() {//分页监听
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectingPaidVacation();
                }
            }
        });
    }

    private void docUpdated() {
        String keywords = searchTxFd.getText().trim();
        renderer.setKeywords(keywords);
        repaint();
        if (!keywords.isEmpty()) {
            int idx = tableComp.getSelectedRow();
            if (idx >= 0) {
                idx = tableComp.convertRowIndexToModel(idx);
            }
            int findRow = statisticsTableModel.findNext(keywords, idx);
            if (findRow < 0) {
                tableComp.clearSelection();
            } else {
                findRow = tableComp.convertRowIndexToView(findRow);
                tableComp.getSelectionModel().setSelectionInterval(findRow, findRow);
                Rectangle rect = tableComp.getCellRect(findRow, 0, true);
                tableComp.scrollRectToVisible(rect);
            }
        } else {
            tableComp.setRowSelectionInterval(0, 0);
        }
    }

    //周下拉列表框
    private void weekCmBoxModel() {
        weekOrMonthCmBox.setModel(weekComboxModel);
        int year = Integer.valueOf(statementYearCmBox.getSelectedItem().toString());
        weekComboxModel.setObjectList(ClientUtils.getWeeks(year));
        Date d = new Date();
        int weekCount = DateUtil.getWeekByDate(d);
        weekOrMonthCmBox.setSelectedIndex(weekCount - 2);
    }

    //月下拉列表框
    private void monthCmBoxModel() {
        weekOrMonthCmBox.setModel(monthComboModel);
        monthComboModel.setObjectList(fillMonthCombo());
        int monthCount = DateUtil.getMonthByDate(new Date());
        weekOrMonthCmBox.setSelectedIndex(monthCount-1);
    }

    //test
    private void yearCmBoxModel() {
        weekOrMonthCmBox.setModel(testComboModel);
        testComboModel.setObjectList(fillTestCombo());
        weekOrMonthCmBox.setSelectedIndex(0);
    }

    private void selectingPaidVacation() {//表格监听
        int idx = tableComp.getSelectedRow();
        if (idx >= 0) {
            idx = tableComp.convertRowIndexToModel(idx);
            statisticsTableModel.getData(idx);
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

        jToolBar3 = new javax.swing.JToolBar();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0));
        jLabel5 = new javax.swing.JLabel();
        statementCmBox = new javax.swing.JComboBox<>();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        jLabel6 = new javax.swing.JLabel();
        statementYearCmBox = new javax.swing.JComboBox<>();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        weekOrMonthLabel = new javax.swing.JLabel();
        weekOrMonthCmBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        searchBtn = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0));
        filler17 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        searchTxFd = new javax.swing.JTextField();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        deriveStatBtn = new javax.swing.JButton();
        dataPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableComp = new javax.swing.JTable();
        dataWaitPanel = new com.nazca.ui.NWaitingPanel();
        dataFailPane = new com.nazca.ui.FailedInfoPanel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setLayout(new java.awt.BorderLayout());

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);
        jToolBar3.setMinimumSize(new java.awt.Dimension(439, 27));
        jToolBar3.setPreferredSize(new java.awt.Dimension(439, 27));
        jToolBar3.add(filler3);

        jLabel5.setText("查看方式：");
        jToolBar3.add(jLabel5);

        statementCmBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "年统计", "月统计", "周统计" }));
        statementCmBox.setMaximumSize(new java.awt.Dimension(80, 32767));
        statementCmBox.setMinimumSize(new java.awt.Dimension(80, 21));
        statementCmBox.setPreferredSize(new java.awt.Dimension(80, 21));
        statementCmBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                statementCmBoxItemStateChanged(evt);
            }
        });
        jToolBar3.add(statementCmBox);
        jToolBar3.add(filler8);

        jLabel6.setText("选择年份：");
        jToolBar3.add(jLabel6);

        statementYearCmBox.setMaximumSize(new java.awt.Dimension(70, 32767));
        statementYearCmBox.setMinimumSize(new java.awt.Dimension(70, 21));
        statementYearCmBox.setPreferredSize(new java.awt.Dimension(70, 21));
        statementYearCmBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                statementYearCmBoxItemStateChanged(evt);
            }
        });
        jToolBar3.add(statementYearCmBox);
        jToolBar3.add(filler5);

        weekOrMonthLabel.setText("选择周：");
        jToolBar3.add(weekOrMonthLabel);

        weekOrMonthCmBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        weekOrMonthCmBox.setMaximumSize(new java.awt.Dimension(60, 21));
        weekOrMonthCmBox.setMinimumSize(new java.awt.Dimension(60, 21));
        weekOrMonthCmBox.setName(""); // NOI18N
        weekOrMonthCmBox.setPreferredSize(new java.awt.Dimension(60, 21));
        weekOrMonthCmBox.setRequestFocusEnabled(false);
        weekOrMonthCmBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                weekOrMonthCmBoxItemStateChanged(evt);
            }
        });
        jToolBar3.add(weekOrMonthCmBox);
        jToolBar3.add(jLabel1);
        jToolBar3.add(filler2);

        searchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/search-16.png"))); // NOI18N
        searchBtn.setText("查询");
        searchBtn.setFocusable(false);
        searchBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        searchBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });
        jToolBar3.add(searchBtn);
        jToolBar3.add(filler1);
        jToolBar3.add(filler4);
        jToolBar3.add(filler17);

        searchTxFd.setMaximumSize(new java.awt.Dimension(150, 21));
        searchTxFd.setMinimumSize(new java.awt.Dimension(150, 21));
        searchTxFd.setPreferredSize(new java.awt.Dimension(150, 21));
        searchTxFd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTxFdActionPerformed(evt);
            }
        });
        jToolBar3.add(searchTxFd);
        jToolBar3.add(filler6);

        deriveStatBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/export.png"))); // NOI18N
        deriveStatBtn.setText("导出");
        deriveStatBtn.setFocusable(false);
        deriveStatBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        deriveStatBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deriveStatBtnActionPerformed(evt);
            }
        });
        jToolBar3.add(deriveStatBtn);

        add(jToolBar3, java.awt.BorderLayout.NORTH);

        dataPanel.setLayout(new java.awt.CardLayout());

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tableComp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tableComp);

        dataPanel.add(jScrollPane2, "CONTENT");
        dataPanel.add(dataWaitPanel, "WAIT");
        dataPanel.add(dataFailPane, "FAIL");

        add(dataPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void statementYearCmBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_statementYearCmBoxItemStateChanged
        int index = statementYearCmBox.getSelectedIndex();
        viewMode = (String) statementCmBox.getSelectedItem();
        if (index >= 0 && viewMode == StatTypeEnum.YEAR.toString()) {
            yearComboxModel.get(index);
            testComboModel.setObjectList(fillTestCombo());
            yearCmBoxModel();
            setStartAndEndDate();
            searchBtnActionPerformed(null);
        } else if (index >= 0 && viewMode == StatTypeEnum.WEEK.toString()) {
            yearComboxModel.get(index);
            weekCmBoxModel();
            setStartAndEndDate();
            searchBtnActionPerformed(null);
        } else {
            yearComboxModel.get(index);
            monthCmBoxModel();
            setStartAndEndDate();
            searchBtnActionPerformed(null);
        }
    }//GEN-LAST:event_statementYearCmBoxItemStateChanged

    private void statementCmBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_statementCmBoxItemStateChanged
        // TODO add your handling code here:
        viewMode = (String) statementCmBox.getSelectedItem();
        if (viewMode == StatTypeEnum.WEEK.toString()) {
            setVisiable(true);
            weekOrMonthLabel.setText("选择周：");
            weekCmBoxModel();
            setStartAndEndDate();
            searchBtnActionPerformed(null);
        } else if (viewMode == StatTypeEnum.MONTH.toString()) {
            setVisiable(true);
            weekOrMonthLabel.setText("选择月：");
            monthCmBoxModel();
            setStartAndEndDate();
            searchBtnActionPerformed(null);
        } else if (viewMode == StatTypeEnum.YEAR.toString()) {
            setVisiable(false);
            yearCmBoxModel();
            setStartAndEndDate();
            searchBtnActionPerformed(null);
        }
    }//GEN-LAST:event_statementCmBoxItemStateChanged

    private void searchTxFdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTxFdActionPerformed
        // TODO add your handling code here:
        docUpdated();
    }//GEN-LAST:event_searchTxFdActionPerformed

    private void weekOrMonthCmBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_weekOrMonthCmBoxItemStateChanged
        // TODO add your handling code here:
        int index = weekOrMonthCmBox.getSelectedIndex();
        if (index >= 0) {
            weekComboxModel.get(index);
            setStartAndEndDate();
            searchBtnActionPerformed(null);
        } 
    }//GEN-LAST:event_weekOrMonthCmBoxItemStateChanged

    private void deriveStatBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deriveStatBtnActionPerformed
        ExportPanel exportPane = new ExportPanel();
        exportPane.setStaLis(backResult);
        exportPane.showMe(deriveStatBtn);
    }//GEN-LAST:event_deriveStatBtnActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        queryStatisticsAgent.setParameters(start, end);
        queryStatisticsAgent.start();
    }//GEN-LAST:event_searchBtnActionPerformed
    
    private void setStartAndEndDate(){
        keyword = searchTxFd.getText().trim();
        int year = Integer.valueOf(statementYearCmBox.getSelectedItem().toString());
        String monthOrWeek = weekOrMonthCmBox.getSelectedItem().toString();
        int monthWeek = 0;
        monthWeek = Integer.valueOf(monthOrWeek.substring(0, monthOrWeek.length() - 1));
        String month = monthOrWeek.substring(monthOrWeek.length() - 1, monthOrWeek.length());
        Date systemDate = new Date();
        int nowYear = DateUtil.getYearByDate(systemDate);
        int nowMonth = DateUtil.getMonthByDate(systemDate) + 1;
        int nowWeek = DateUtil.getWeekByDate(systemDate);
        if (null != monthOrWeek) {
            if (month.equals("月")) {
                start = DateUtil.getFirstDayOfMonth(year, monthWeek);
                if (year == nowYear && monthWeek == nowMonth) {
                    end = systemDate;
                } else {
                    end = DateUtil.getLastDayOfMonth(year, monthWeek);
                }
                jLabel1.setText("");
            } else if (month.equals("周")) {
                start = DateUtil.getFistWeekDateByYearAndWeek(year, monthWeek);
                if (year == nowYear && monthWeek == nowWeek) {
                    end = systemDate;
                } else {
                    end = DateUtil.getLastWeekDateByYearAndWeek(year, monthWeek);
                }
                
                jLabel1.setText(getTimeSpan());
            } else if (month.equals("年")) {
                start = DateUtil.getFirstDayOfMonth(year, 1);
                if (year == nowYear) {
                    end = systemDate;
                } else {
                    end = DateUtil.getLastDayOfMonth(year, 12);
                }
                jLabel1.setText("");
            }
        }
    }
    private String getTimeSpan(){
        String timeSpan = "(" + (DateUtil.getMonthByDate(start) + 1) + "月" + DateUtil.getDayOfMonthByDate(start) + "日—"
                    + (DateUtil.getMonthByDate(end) + 1) + "月" + DateUtil.getDayOfMonthByDate(end) + "日" + ")";
        return timeSpan;
    }
    private void setVisiable(Boolean flag) {
        weekOrMonthLabel.setVisible(flag);
        weekOrMonthCmBox.setVisible(flag);
    }

    private List<String> fillMonthCombo() {
        List<String> months = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            String s = String.valueOf(i);
            months.add(s + "月");
        }
        return months;
    }

    private List<String> fillTestCombo() {
        List<String> months = new ArrayList<String>();
        months.add("1年");
        return months;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.nazca.ui.FailedInfoPanel dataFailPane;
    private javax.swing.JPanel dataPanel;
    private com.nazca.ui.NWaitingPanel dataWaitPanel;
    private javax.swing.JButton deriveStatBtn;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler17;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchTxFd;
    private javax.swing.JComboBox<String> statementCmBox;
    private javax.swing.JComboBox<String> statementYearCmBox;
    private javax.swing.JTable tableComp;
    private javax.swing.JComboBox<String> weekOrMonthCmBox;
    private javax.swing.JLabel weekOrMonthLabel;
    // End of variables declaration//GEN-END:variables
private AgentListener<List<AttendanceMgmtStat>> queryStatisticsAgentLis = new AgentListener<List<AttendanceMgmtStat>>() {
        @Override
        public void onStarted(long seq) {
            searchBtn.setEnabled(false);
            statementCmBox.setEnabled(false);
            statementYearCmBox.setEnabled(false);
            weekOrMonthCmBox.setEnabled(false);
            searchTxFd.setEnabled(false);
            deriveStatBtn.setEnabled(false);
            dataWaitPanel.setIndeterminate(true);
            dataWaitPanel.showWaitingMode();
            card.show(CardLayoutWrapper.WAIT);
        }

        @Override
        public void onSucceeded(List<AttendanceMgmtStat> result, long seq) {
            searchBtn.setEnabled(true);
             statementCmBox.setEnabled(true);
            statementYearCmBox.setEnabled(true);
            weekOrMonthCmBox.setEnabled(true);
            deriveStatBtn.setEnabled(true);
            backResult = result;
            if (result != null && result.size() > 0) {//判断是否为空
                searchTxFd.setEnabled(true);
                statisticsTableModel.setDatas(result);
                card.show(CardLayoutWrapper.CONTENT);
                dataWaitPanel.setIndeterminate(false);
                tableComp.getSelectionModel().setSelectionInterval(0, 0);//刷星结束默认第一条数据
            } else {
                selectingPaidVacation();
                dataWaitPanel.showMsgMode("暂无考勤统计信息", 0, NWaitingPanel.MSG_MODE_INFO);
                card.show(CardLayoutWrapper.WAIT);
                searchTxFd.setEnabled(false);
            }
        }

        @Override
        public void onFailed(String errMsg, int errCode, long seq) {
            searchBtn.setEnabled(true);
            searchTxFd.setEnabled(false);
            deriveStatBtn.setEnabled(false);
            statementCmBox.setEnabled(true);
            statementYearCmBox.setEnabled(true);
            weekOrMonthCmBox.setEnabled(true);
            dataWaitPanel.showMsgMode(errMsg, errCode, NWaitingPanel.MSG_MODE_ERROR);
            card.show(CardLayoutWrapper.FAIL);
            dataWaitPanel.setIndeterminate(false);

        }

    };
}
