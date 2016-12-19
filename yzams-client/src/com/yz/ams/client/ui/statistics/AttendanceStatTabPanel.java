package com.yz.ams.client.ui.statistics;

import com.nazca.ui.NTabContentPanel;
import com.nazca.ui.NWaitingPanel;
import com.nazca.ui.TextHinter;
import com.nazca.ui.UIUtilities;
import com.nazca.ui.model.SimpleObjectListModel;
import com.nazca.ui.util.CardLayoutWrapper;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.agent.QueryReportAgent;
import com.yz.ams.consts.Permissions;
import com.yz.ams.consts.StatTypeEnum;
import com.yz.ams.util.DateUtil;
import com.yz.ams.model.reportListMode;
import com.yz.ams.model.wrap.mgmt.DailyAttendance;
import com.yz.ams.model.wrap.mgmt.ReportAttendancesModel;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/*
 * AttendStatis.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-11 10:35:31
 */

/**
 *
 * @author Your Name <Song Haixiang >
 */
public class AttendanceStatTabPanel extends NTabContentPanel {
    private SimpleObjectListModel<String>  weekOrMonthComboModel = new SimpleObjectListModel<String>(); 
    private SimpleObjectListModel<String>  yearComboModel = new SimpleObjectListModel<String>();
    private StatisticsMgmtPanel statisticsMgmtPanel = null;
    private QueryReportAgent queryReportAgent = null;
    private CardLayoutWrapper card1 = null;
    private List<ReportAttendancesModel> attendanceList = null;
    private List<DailyAttendance> dailyAttendanceList = new ArrayList<>();
    private String reportTitle = "";
    /**
     * Creates new form Attendstatis
     */
    public AttendanceStatTabPanel() {
        initComponents();
        setTabText("考勤统计");
        initUI();
        initModel();
        initAgentAndListener();
        initCombo();
        ReportTgBtn.doClick();
        
    }
    private void initModel(){
        statisticsMgmtPanel = new StatisticsMgmtPanel();
        weekOrMonthComboModel = new SimpleObjectListModel<>();
        yearComboModel = new SimpleObjectListModel<>();
    }   
    private void initAgentAndListener(){
        queryReportAgent = new QueryReportAgent();
        queryReportAgent.addListener(queryReportAgentLis);
        ReportSearchTxFd.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                }
            }
        });
    }
    
    private void initUI(){
        ReportSearchTxFd.setVisible(true);
        UIUtilities.attachSearchIcon(ReportSearchTxFd);//给搜索框加搜索图标
        TextHinter.attach("输入姓名并回车", ReportSearchTxFd);
        card1 = new CardLayoutWrapper(jPanel3);
        if(ClientContext.hasPermission(Permissions.PM) && !ClientContext.hasPermission(Permissions.HR)){
            ReportSearchTxFd.setVisible(false);
        }else if(ClientContext.hasPermission(Permissions.ADMIN) || ClientContext.hasPermission(Permissions.CEO) || ClientContext.hasPermission(Permissions.HR)){
            ReportSearchTxFd.setVisible(true);
        }else if(ClientContext.hasPermission(Permissions.STAFF)){
            ReportSearchTxFd.setVisible(false);
        }
    }
    private void initCombo(){
        initYearCombo();
        initMonthComboByYear();
    }
    
    //初始化年
    private void initYearCombo(){
        yearComboModel.setObjectList(this.fillYearCombo());
        YearCombo.setModel(yearComboModel);
        YearCombo.setSelectedItem(String.valueOf(DateUtil.getYearByDate(new Date())));
    }
    private void initMonthComboByYear(){
        weekOrMonthComboModel.setObjectList(fillMonthCombo());
        weekOrMonthCombo.setModel(weekOrMonthComboModel);
        String curMonth = String.valueOf(DateUtil.getMonthByDate(new Date()));
        weekOrMonthCombo.setSelectedItem(curMonth + "月");
    }
    private void initWeekComboByYear(){
        String curWeek = String.valueOf(DateUtil.getWeekByDate(new Date()) - 1);
        String curYear = String.valueOf(DateUtil.getYearByDate(new Date()));
        weekOrMonthComboModel.setObjectList(fillWeekCombo(curYear));
        weekOrMonthCombo.setModel(weekOrMonthComboModel);
        weekOrMonthCombo.setSelectedItem(curWeek + "周");
    }
    private List<String> fillYearCombo(){
        int y = DateUtil.getYearByDate(new Date());
        List<String> years = new ArrayList<String>();
        for(int i=2007; i<=y; i++){
            years.add(String.valueOf(i));
        }
        return years;
    }
    
    private List<String> fillMonthCombo(){
        List<String> months = new ArrayList<String>();
        for(int i=1; i<=12; i++){
            String s = String.valueOf(i) + "月";
            months.add(s);
        }
        return months;
    }
    
    private List<String> fillWeekCombo(String year){
        int weekOrMonth = DateUtil.getWeekNumByYear(Integer.valueOf(year));
        List<String> weeks = new ArrayList<String>();
        for(int i=0; i<weekOrMonth; i++){
            String s = String.valueOf(i+1) + "周";
            weeks.add(s);
        }
        return weeks;
    }
    
    private void initReport(){
        //刷新界面，重新显示结果
        jPanel6.removeAll();
        PreviewInfoPanel pane = new PreviewInfoPanel();
        List<reportListMode> lisReport = new ArrayList<>();
        reportListMode mode = new reportListMode();
        mode.setAttendancelis(attendanceList);
        mode.setTitle(reportTitle);  //设置报表标题
        lisReport.add(mode);
        if(viewModeCombo.getSelectedItem().equals(StatTypeEnum.WEEK.toString())){
            pane.previewListInfo(lisReport, null, "/com/yz/ams/client/ui/report/kaoqin_report.jasper");
            jPanel6.add(pane, BorderLayout.CENTER);
        }else{
            pane.previewListInfo(lisReport, null, "/com/yz/ams/client/ui/report/kaoqin_month_report.jasper");
            jPanel6.add(pane, BorderLayout.CENTER);
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

        jPanel1 = new javax.swing.JPanel();
        jToolBar4 = new javax.swing.JToolBar();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0));
        jLabel7 = new javax.swing.JLabel();
        viewModeCombo = new javax.swing.JComboBox<>();
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        jLabel8 = new javax.swing.JLabel();
        YearCombo = new javax.swing.JComboBox<>();
        filler13 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        filler12 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        weekOrMonthLabel = new javax.swing.JLabel();
        weekOrMonthCombo = new javax.swing.JComboBox<>();
        weekSpan = new javax.swing.JLabel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        searchBtn = new javax.swing.JButton();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(7, 0), new java.awt.Dimension(7, 0), new java.awt.Dimension(7, 32767));
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0));
        ReportSearchTxFd = new javax.swing.JTextField();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0));
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        waitingPanel1 = new com.nazca.ui.WaitingPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        nNavToolBar1 = new com.nazca.ui.NNavToolBar();
        ReportTgBtn = new javax.swing.JToggleButton();
        StatisticsTgBtn = new javax.swing.JToggleButton();
        jPanel5 = new javax.swing.JPanel();

        jPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar4.setFloatable(false);
        jToolBar4.setRollover(true);
        jToolBar4.setMaximumSize(new java.awt.Dimension(420, 2147483647));
        jToolBar4.setMinimumSize(new java.awt.Dimension(420, 27));
        jToolBar4.setPreferredSize(new java.awt.Dimension(420, 27));
        jToolBar4.add(filler6);

        jLabel7.setText("查看方式：");
        jToolBar4.add(jLabel7);

        viewModeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "月统计", "周统计" }));
        viewModeCombo.setMaximumSize(new java.awt.Dimension(70, 32767));
        viewModeCombo.setMinimumSize(new java.awt.Dimension(70, 21));
        viewModeCombo.setPreferredSize(new java.awt.Dimension(80, 21));
        viewModeCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                viewModeComboItemStateChanged(evt);
            }
        });
        jToolBar4.add(viewModeCombo);
        jToolBar4.add(filler11);
        jToolBar4.add(filler10);

        jLabel8.setText("选择年份：");
        jToolBar4.add(jLabel8);

        YearCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020" }));
        YearCombo.setMaximumSize(new java.awt.Dimension(70, 32767));
        YearCombo.setMinimumSize(new java.awt.Dimension(70, 21));
        YearCombo.setPreferredSize(new java.awt.Dimension(70, 21));
        YearCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                YearComboItemStateChanged(evt);
            }
        });
        jToolBar4.add(YearCombo);
        jToolBar4.add(filler13);
        jToolBar4.add(filler12);

        weekOrMonthLabel.setText("选择月：");
        jToolBar4.add(weekOrMonthLabel);

        weekOrMonthCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        weekOrMonthCombo.setMaximumSize(new java.awt.Dimension(70, 21));
        weekOrMonthCombo.setMinimumSize(new java.awt.Dimension(70, 21));
        weekOrMonthCombo.setPreferredSize(new java.awt.Dimension(60, 21));
        weekOrMonthCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                weekOrMonthComboItemStateChanged(evt);
            }
        });
        jToolBar4.add(weekOrMonthCombo);
        jToolBar4.add(weekSpan);
        jToolBar4.add(filler4);

        searchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/search-16.png"))); // NOI18N
        searchBtn.setText("查询");
        searchBtn.setFocusable(false);
        searchBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });
        jToolBar4.add(searchBtn);
        jToolBar4.add(filler9);
        jToolBar4.add(filler3);
        jToolBar4.add(filler1);

        ReportSearchTxFd.setMaximumSize(new java.awt.Dimension(150, 21));
        ReportSearchTxFd.setMinimumSize(new java.awt.Dimension(150, 21));
        ReportSearchTxFd.setPreferredSize(new java.awt.Dimension(150, 21));
        ReportSearchTxFd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReportSearchTxFdActionPerformed(evt);
            }
        });
        jToolBar4.add(ReportSearchTxFd);
        jToolBar4.add(filler2);

        jPanel1.add(jToolBar4, java.awt.BorderLayout.PAGE_START);

        jPanel3.setLayout(new java.awt.CardLayout());

        jPanel6.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jPanel6, "CONTENT");
        jPanel3.add(waitingPanel1, "WAIT");

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        nNavToolBar1.setRollover(true);

        buttonGroup1.add(ReportTgBtn);
        ReportTgBtn.setText("考勤报表");
        ReportTgBtn.setFocusable(false);
        ReportTgBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ReportTgBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ReportTgBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReportTgBtnActionPerformed(evt);
            }
        });
        nNavToolBar1.add(ReportTgBtn);

        buttonGroup1.add(StatisticsTgBtn);
        StatisticsTgBtn.setText("考勤统计");
        StatisticsTgBtn.setFocusable(false);
        StatisticsTgBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        StatisticsTgBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        StatisticsTgBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatisticsTgBtnActionPerformed(evt);
            }
        });
        nNavToolBar1.add(StatisticsTgBtn);

        add(nNavToolBar1, java.awt.BorderLayout.NORTH);
        add(jPanel5, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void ReportTgBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReportTgBtnActionPerformed
        jPanel5.removeAll();
        jPanel5.setLayout(new BorderLayout());
        jPanel5.add(jPanel1,BorderLayout.CENTER);
        revalidate();
        repaint();
    }//GEN-LAST:event_ReportTgBtnActionPerformed

    private void StatisticsTgBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StatisticsTgBtnActionPerformed
        jPanel5.removeAll();
        jPanel5.setLayout(new BorderLayout());
        jPanel5.add(statisticsMgmtPanel,BorderLayout.CENTER);
        revalidate();
        repaint();
    }//GEN-LAST:event_StatisticsTgBtnActionPerformed

    private void viewModeComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_viewModeComboItemStateChanged
        String viewMode = (String) viewModeCombo.getSelectedItem();
        if(viewMode.equals( StatTypeEnum.WEEK.toString())){
            weekOrMonthLabel.setText("选择周：");
            initWeekComboByYear();
        }else if(viewMode.equals(StatTypeEnum.MONTH.toString())){
            initMonthComboByYear();
            weekSpan.setText("");
        }
    }//GEN-LAST:event_viewModeComboItemStateChanged
    
    private void weekOrMonthComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_weekOrMonthComboItemStateChanged
        String keyword = ReportSearchTxFd.getText().trim(); 
        attendanceList = new ArrayList<>();
        Date startDate = null;
        Date endDate = null;
        String selectedYear = yearComboModel.getSelectedItem();
        String selectedWeek = weekOrMonthComboModel.getSelectedItem();
        if(!StringUtils.isEmpty(selectedWeek)){
            selectedWeek = selectedWeek.substring(0, selectedWeek.length() - 1);
            int year = Integer.valueOf(selectedYear);
            int week = Integer.valueOf(selectedWeek);

            if(viewModeCombo.getSelectedItem().equals(StatTypeEnum.WEEK.toString())){ //周统计
                 startDate = DateUtil.getWeekDateByYearAndWeek(year, week);
                 endDate = DateUtil.addDay2Date(startDate,6);
                 reportTitle = getReportTitleOfWeek(selectedYear, selectedWeek, startDate, endDate);
            }else{ //月统计
                 startDate = DateUtil.getMonthByYearAndMonth(year, week);
                 int days  = DateUtil.getMonthDayByYearAndMonth(year, week);
                 endDate = DateUtil.addDay2Date(startDate,days-1);
                 reportTitle = selectedYear + "年" + selectedWeek + "月份";
            }
            startDate = DateUtil.getZeroTimeOfDay(startDate);
            endDate = DateUtil.getZeroTimeOfDay(endDate);
            queryReportAgent.setParameters(startDate, endDate, keyword);
            queryReportAgent.start();
        }
    }//GEN-LAST:event_weekOrMonthComboItemStateChanged
    
    private String getReportTitleOfWeek(String selectedYear, String selectedWeek, Date start, Date end){ //设置周报表标题
        String title = selectedYear + "年第" + selectedWeek + "周";
        String timeSpan = "(" + (DateUtil.getMonthByDate(start) + 1) + "月" + DateUtil.getDayOfMonthByDate(start) + "日—"
                    + (DateUtil.getMonthByDate(end) + 1) + "月" + DateUtil.getDayOfMonthByDate(end) + "日" + ")";
        weekSpan.setText(timeSpan);
        return title += timeSpan;
    }
    
    private void YearComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_YearComboItemStateChanged
        String keyword = ReportSearchTxFd.getText().trim();  //搜索的内容    
        String selectedYear = yearComboModel.getSelectedItem();
        String selectedWeek = weekOrMonthComboModel.getSelectedItem();
        Date startDate = null;
        Date endDate = null;
        if(!StringUtils.isEmpty(selectedWeek)){
            selectedWeek = selectedWeek.substring(0, selectedWeek.length() - 1);
            int year = Integer.valueOf(selectedYear);
            int week = Integer.valueOf(selectedWeek);
            if(viewModeCombo.getSelectedItem().equals(StatTypeEnum.WEEK.toString())){ //周统计
                setWeekCombo(selectedYear);
                startDate = DateUtil.getWeekDateByYearAndWeek(year, week);
                endDate = DateUtil.addDay2Date(startDate,6);
                reportTitle = getReportTitleOfWeek(selectedYear, selectedWeek, startDate, endDate);
            }else{ //月统计
                setMonthCombo();
                startDate = DateUtil.getMonthByYearAndMonth(year, week);
                int days  = DateUtil.getMonthDayByYearAndMonth(year, week);
                endDate = DateUtil.addDay2Date(startDate,days-1);
                reportTitle = selectedYear + "年" + selectedWeek + "月份";
            }
            startDate = DateUtil.getZeroTimeOfDay(startDate);
            endDate = DateUtil.getZeroTimeOfDay(endDate);
            queryReportAgent.setParameters(startDate, endDate, keyword);
            queryReportAgent.start();
        }
    }//GEN-LAST:event_YearComboItemStateChanged
    
    private void setWeekCombo(String y){
        weekOrMonthComboModel.setObjectList(fillWeekCombo(y));
        weekOrMonthCombo.setModel(weekOrMonthComboModel);
        weekOrMonthCombo.setSelectedIndex(0);
    }
    private void setMonthCombo(){
        weekOrMonthComboModel.setObjectList(this.fillMonthCombo());
        weekOrMonthCombo.setModel(weekOrMonthComboModel);
        weekOrMonthCombo.setSelectedIndex(0);
    }
    private void ReportSearchTxFdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReportSearchTxFdActionPerformed
        weekOrMonthComboItemStateChanged(null);
    }//GEN-LAST:event_ReportSearchTxFdActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        weekOrMonthComboItemStateChanged(null);
    }//GEN-LAST:event_searchBtnActionPerformed
    
    private void setEnable(boolean b) {
        viewModeCombo.setEnabled(b);
        YearCombo.setEnabled(b);
        weekOrMonthCombo.setEnabled(b);
        searchBtn.setEnabled(b);
        ReportSearchTxFd.setEnabled(b);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ReportSearchTxFd;
    private javax.swing.JToggleButton ReportTgBtn;
    private javax.swing.JToggleButton StatisticsTgBtn;
    private javax.swing.JComboBox<String> YearCombo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler12;
    private javax.swing.Box.Filler filler13;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JToolBar jToolBar4;
    private com.nazca.ui.NNavToolBar nNavToolBar1;
    private javax.swing.JButton searchBtn;
    private javax.swing.JComboBox<String> viewModeCombo;
    private com.nazca.ui.WaitingPanel waitingPanel1;
    private javax.swing.JComboBox<String> weekOrMonthCombo;
    private javax.swing.JLabel weekOrMonthLabel;
    private javax.swing.JLabel weekSpan;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onInit() {
    }

    @Override
    public String getComponentUUID() {
        return AttendanceStatTabPanel.class.getName();
    }
    
    private AgentListener<List<ReportAttendancesModel>> queryReportAgentLis = new AgentListener<List<ReportAttendancesModel>>() {
        @Override
        public void onStarted(long seq) {
            setEnable(false);
            waitingPanel1.setIndeterminate(true);
            waitingPanel1.showMsgMode("数据加载中，请稍后...", 0, NWaitingPanel.MSG_MODE_INFO);
            waitingPanel1.showWaitingMode();
            card1.show(CardLayoutWrapper.WAIT);
        }

        @Override
        public void onSucceeded(List<ReportAttendancesModel> result, long seq) {
            attendanceList = result;
            //查询成功后显示报表
            initReport();
            card1.show(CardLayoutWrapper.CONTENT);
            setEnable(true);
            waitingPanel1.setIndeterminate(false);
        }

        @Override
        public void onFailed(String errMsg, int errCode, long seq) {
            setEnable(true);
            waitingPanel1.showMsgMode(errMsg, errCode, NWaitingPanel.MSG_MODE_ERROR);
            card1.show(CardLayoutWrapper.FAIL);
            waitingPanel1.setIndeterminate(false);
        }
    };

    
    
    @Override
    public void onDestroy() {
    }
    
}
