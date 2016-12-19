/*
 * MonthPanel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-07 07:39:49
 */
package com.yz.ams.client.ui.holiday;

import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.page.MonthPage;
import com.nazca.ui.UIUtilities;
import com.nazca.ui.model.SimpleObjectListModel;
import com.nazca.ui.util.CardLayoutWrapper;
import com.nazca.util.NazcaFormater;
import com.nazca.util.StringUtil;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.agent.GetDataInfosAgent;
import com.yz.ams.client.agent.QueryHolidaysAgent;
import com.yz.ams.client.comp.MonthChoosePane;
import com.yz.ams.util.DateUtil;
import com.yz.ams.consts.HolidayTypeEnum;
import com.yz.ams.model.Holiday;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javafx.application.Platform;
import javafx.collections.ObservableSet;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 *
 * @author 鏇规収鑻� <caohuiying@yzhtech.com>
 */
public class MonthPanel extends javax.swing.JPanel {
    //model
    private SimpleObjectListModel<String>  holidayComboModel = null;
    //card
    private CardLayoutWrapper wrap = null;
    //agent
    private GetDataInfosAgent agent = null;
    private QueryHolidaysAgent queryHolidaysAgent = null;
    
    private long timeSeq = 0;
    private long queryHolidayTimeSeq = 0;
    private int year;
    private int month;
    private List<Date> selectedDates = null;
    private List<Holiday> holidays = null;
    private List<Holiday> selectedHolidays = null;
    private List<Holiday> holidaysOfMonth = null;
    private List<String> holidayName = null;
    private Map<String,Date> holidayMap = null;
    private String holidayMonth = "";
    /**
     * Creates new form MonthPanel
     */
    public MonthPanel() {
        initComponents();
        holidaysOfMonth = new ArrayList<>();
        holidays = new ArrayList();
        selectedHolidays = new ArrayList();
        holidayName = new ArrayList();
        holidayMap = new HashMap();
        selectedDates=new ArrayList<>();
        initUI();
        initModel();
        initAgentAndListener();
        init();
        initMonthTxFd();
        queryHolidayName();
        initHolidayCombo();
    }
    
    private void initUI(){
        wrap = new CardLayoutWrapper(cardPane);
    }
    private void initAgentAndListener() {
        agent = new GetDataInfosAgent();
        agent.addListener(listener);
        queryHolidaysAgent = new QueryHolidaysAgent();
        queryHolidaysAgent.addListener(queryHolidayNamelistener);
    }
    
    private void initModel(){
        holidayComboModel = new SimpleObjectListModel();
        holidayCombo.setModel(holidayComboModel);
    }
    
    //初始化月份输入框�
    private void initMonthTxFd(){
        monthTxFd.setText(DateUtil.getDateTimeFormat(new Date()).substring(0,7));
    }
    public void getDatas() {
        String date = monthTxFd.getText();
        if(StringUtil.isEmpty(date)){
            date = DateUtil.getDateTimeFormat(new Date());
        }
        year = Integer.valueOf(date.substring(0, 4));
        month = Integer.valueOf(date.substring(5, 7));
        agent.setParam(year, month);
        agent.start();
    }
    private void queryHolidayName(){
        queryHolidaysAgent.start();
    }
    private void initHolidayCombo(){
        holidayName = new ArrayList();
        holidayName.add("选择假期");
        for(Holiday h : holidays){
            if(h.getHolidayType() == HolidayTypeEnum.HOLIDAY){
                String name = h.getHolidayName();
                holidayName.add(name);
                Date date = h.getHolidayDate();
                holidayMap.put(name, date);
            }
        }
        List<String> list2 = new ArrayList<String>();
            for (String s : holidayName){
                if (!list2.contains(s)){
                    list2.add(s);
                }
            }
                
        holidayComboModel.setObjectList(list2);
        holidayCombo.setModel(holidayComboModel);
        holidayCombo.setSelectedItem("选择假期");
    }
    
    /**
     * 判断选择的多个假期类型是否相同�
     * @return 
     */
    private boolean isSameType(List<Holiday> holidayLis){
        HolidayTypeEnum type = null;
        
            if(holidayLis.get(0).getHolidayType() != null){ //得到第一个日期的假期类型�
                type = holidayLis.get(0).getHolidayType();
            
                if(type == HolidayTypeEnum.HOLIDAY){ //如果是holiday类型
                    for(Holiday h : holidayLis){
                        if(h.getHolidayType() != null){
                            if(h.getHolidayType() == HolidayTypeEnum.WORKDAY){ //假期类型不相同
                                return false;
                            }
                        }else if(DateUtil.isWorkingDays(h.getHolidayDate())){ //是工作日，则不是同类型
                            return false;
                        }
                    }
                }else if(type == HolidayTypeEnum.WORKDAY){//如果是workday类型
                    for(Holiday h : holidayLis){
                        if(h.getHolidayType() != null){
                            if(h.getHolidayType() == HolidayTypeEnum.HOLIDAY){ //假期类型不相同
                                return false;
                            }
                        }else if(!DateUtil.isWorkingDays(h.getHolidayDate())){ //是工作日，则不是同类型
                            return false;
                        }
                    }
                }
            }else{
                if(DateUtil.isWorkingDays(holidayLis.get(0).getHolidayDate())){  //工作日 
                    for(Holiday h : holidayLis){
                        if(h.getHolidayType() != null){
                            if(h.getHolidayType() == HolidayTypeEnum.HOLIDAY){ //是假期，不相同
                                return false;
                            }
                        }
//                        else if(!DateUtil.isWorkingDays(h.getHolidayDate())){ //不是工作日，则不是同类型
//                            return false;
//                        }
                    }
                }else{//周末
                    for(Holiday h : holidayLis){
                        if(h.getHolidayType() != null){
                            if(h.getHolidayType() == HolidayTypeEnum.WORKDAY){ //是假期，不相同
                                return false;
                            }
                        }
//                        else if(DateUtil.isWorkingDays(h.getHolidayDate())){ //不是工作日，则不是同类型
//                            return false;
//                        }
                    }
                }
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

        jToolBar1 = new javax.swing.JToolBar();
        refreshBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        selectMonthBtn = new javax.swing.JButton();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 32767));
        monthTxFd = new javax.swing.JTextField();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 32767));
        holidayCombo = new javax.swing.JComboBox<>();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(790, 0), new java.awt.Dimension(790, 0), new java.awt.Dimension(790, 32767));
        cardPane = new javax.swing.JPanel();
        centerPane = new javax.swing.JPanel();
        nWaitingPanel1 = new com.nazca.ui.NWaitingPanel();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        refreshBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/refresh_16.png"))); // NOI18N
        refreshBtn.setText("刷新");
        refreshBtn.setFocusable(false);
        refreshBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        refreshBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(refreshBtn);

        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/update-16.png"))); // NOI18N
        updateBtn.setText("修改");
        updateBtn.setFocusable(false);
        updateBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        updateBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(updateBtn);
        jToolBar1.add(filler5);

        selectMonthBtn.setText("选择月份");
        selectMonthBtn.setFocusable(false);
        selectMonthBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectMonthBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        selectMonthBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectMonthBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectMonthBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(selectMonthBtn);
        jToolBar1.add(filler6);

        monthTxFd.setMaximumSize(new java.awt.Dimension(100, 100));
        jToolBar1.add(monthTxFd);
        jToolBar1.add(filler2);

        holidayCombo.setMaximumRowCount(10);
        holidayCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "选择假期" }));
        holidayCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                holidayComboItemStateChanged(evt);
            }
        });
        jToolBar1.add(holidayCombo);
        jToolBar1.add(filler1);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        cardPane.setLayout(new java.awt.CardLayout());

        centerPane.setLayout(new java.awt.BorderLayout());
        cardPane.add(centerPane, "CONTENT");
        cardPane.add(nWaitingPanel1, "WAIT");

        add(cardPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        
        if(selectedDates.isEmpty()){
            UIUtilities.warningDlg(cardPane, "请选择一个日期!");
        }else{
            toHoliday(selectedDates); //选择的日期转换为holiday
            
            if(selectedHolidays.size() >= 2 && !isSameType(selectedHolidays)){ //选择的假期类型不相同，提示�
                UIUtilities.warningDlg(cardPane, "请选择假期类型相同的日期!");
            }else{
                    AddHolidayPanel setHolidayPanel = new AddHolidayPanel();
                    List<Holiday> holidayList = setHolidayPanel.showMe(this, selectedHolidays);

                    if (holidayList != null) {
                        refreshBtnActionPerformed(null);
                    }
            }
        }
    }//GEN-LAST:event_updateBtnActionPerformed

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        selectedDates.clear();
        getDatas();
        queryHolidayName();
    }//GEN-LAST:event_refreshBtnActionPerformed

    private void selectMonthBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectMonthBtnActionPerformed
        MonthChoosePane monthChoosePane = new MonthChoosePane();
        monthChoosePane.setSelectDate(new Date());
        Date date = monthChoosePane.showMe(cardPane, TOOL_TIP_TEXT_KEY);
        if(date != null){
            String monthStr = DateUtil.getDateTimeFormat(date).substring(0,7);
            monthTxFd.setText(monthStr);
            //选择的月份不等于所选假期的月份，假期选择下拉框初始化
            if(!monthStr.equals(holidayMonth)){
                holidayCombo.setSelectedItem("选择假期");
            }
            refreshBtnActionPerformed(null);
        }
    }//GEN-LAST:event_selectMonthBtnActionPerformed

    private void holidayComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_holidayComboItemStateChanged
        String selectedHoliday  = (String)holidayCombo.getSelectedItem();
        if(selectedHoliday != null){
            if(!selectedHoliday.equals("选择假期") ){
                Date date = holidayMap.get(selectedHoliday);
                int year = DateUtil.getYearByDate(date);
                int month = DateUtil.getMonthByDate(date) + 1;
                if(month >=10){
                    holidayMonth = year + "-" + month;
                }else{
                    holidayMonth = year + "-0" + month;
                }
                monthTxFd.setText(holidayMonth);
                getDatas();
            }
        }
    }//GEN-LAST:event_holidayComboItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cardPane;
    private javax.swing.JPanel centerPane;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.JComboBox<String> holidayCombo;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField monthTxFd;
    private com.nazca.ui.NWaitingPanel nWaitingPanel1;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JButton selectMonthBtn;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables

    private void setPaneEnabled(boolean b) {
        refreshBtn.setEnabled(b);
        updateBtn.setEnabled(b);
    }
    
    private AgentListener<List<Holiday>> listener = new AgentListener<List<Holiday>>() {
        @Override
        public void onStarted(long seq) {
            timeSeq = seq;
            nWaitingPanel1.setIndeterminate(true);
            wrap.show(CardLayoutWrapper.WAIT);
            setPaneEnabled(false);
            nWaitingPanel1.showWaitingMode("数据加载中，请稍后...");
        }

        @Override
        public void onSucceeded(List<Holiday> result, long seq) {
            if (timeSeq != seq) {
                return;
            }
            holidaysOfMonth = result;
            setPaneEnabled(true);
            
            //创建月视图 
            buildMonthCalendar(result);
            wrap.show(CardLayoutWrapper.CONTENT);
            
            nWaitingPanel1.setIndeterminate(false);
        }

        @Override
        public void onFailed(String errMsg, int errCode, long seq) {
            if (timeSeq != seq) {
                return;
            }
            setPaneEnabled(true);
            nWaitingPanel1.setIndeterminate(false);
        }
    };
    
    private AgentListener<List<Holiday>> queryHolidayNamelistener = new AgentListener<List<Holiday>>() {
        @Override
        public void onStarted(long seq) {
            queryHolidayTimeSeq = seq;
        }

        @Override
        public void onSucceeded(List<Holiday> result, long seq) {
            if (queryHolidayTimeSeq != seq) {
                return;
            }
            holidays = result;
            initHolidayCombo(); //初始化假期选择下拉框
        }

        @Override
        public void onFailed(String errMsg, int errCode, long seq) {
            
        }
    };
     
     private void buildMonthCalendar(final List<Holiday> result) {
        final JFXPanel jfxPane = new JFXPanel();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                BorderPane borderPane = new BorderPane();
                Scene scence = new Scene(borderPane);
                jfxPane.setScene(scence);
                scence.getStylesheets().add("com/yz/ams/client/ui/css/calendar.css");
                borderPane.setCenter(initMonthView(result));
            }
        });
        centerPane.removeAll();
        centerPane.add(jfxPane);
        centerPane.repaint();
    }

    private MonthPage initMonthView(List<Holiday> result) {
        final MonthPage monthPage = new MonthPage();
        CalendarSource calSource = new CalendarSource();
        LocalDate localDate = LocalDate.of(year, month, 1);
        monthPage.setDate(localDate);
        monthPage.getMonthView().setDate(localDate);
        for (Holiday info : result) {
            MonthCalendar cal = new MonthCalendar(monthPage.getMonthView().getYearMonth(), info);
            //caohuiying 如果将休息日修改为工作日，显示style2,将工作日修改为休息日，显示style5,其他的不显示style
            if(info.isDeleted()){
                continue;
            }else if (info.getHolidayType() == HolidayTypeEnum.WORKDAY ) {
                cal.setStyle(com.calendarfx.model.Calendar.Style.STYLE2);
            } else if(info.getHolidayType() == HolidayTypeEnum.HOLIDAY  ){
                cal.setStyle(com.calendarfx.model.Calendar.Style.STYLE5);
            }else{
                continue;
            }
            calSource.getCalendars().add(cal);
        }
        monthPage.getCalendarSources().add(calSource);
        monthPage.setNodeOrientation(NodeOrientation.INHERIT);
        monthPage.setShowDate(false);
        monthPage.showNavigationProperty().setValue(false);
        monthPage.getMonthView().showWeekendsProperty().setValue(true);
        monthPage.getMonthView().showWeekNumbersProperty().setValue(false);
        monthPage.getMonthView().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                    selectedDates.clear();
                    selectedHolidays.clear();
                    ObservableSet set = monthPage.getMonthView().getSelectedDates();
                    LocalDate date = monthPage.getMonthView().getDate();
                    String year = String.valueOf(date.getYear());
                    String month = String.valueOf(date.getMonthValue());
                    String oldStr = monthTxFd.getText();
                    if(month.length() == 1){
                        monthTxFd.setText(year +"-0" + month);
                    }else{
                        monthTxFd.setText(year +"-" + month);
                    }
                    if (!oldStr.equals(monthTxFd.getText())) {
                        getDatas();
                    }
                    String selectedDateStr = set.toString();
                    if (selectedDateStr.length() > 2) {
                        String datesStr = selectedDateStr.substring(1, selectedDateStr.length() - 1);
                        String[] dateStrArray = datesStr.split(",");
                        for (int i = 0; i < dateStrArray.length; i++) {
                            String dateStr = dateStrArray[i];
                            Date d = NazcaFormater.parseSimpleDate(dateStr);
                            selectedDates.add(d);
                        }
                    }
            }
            
        });
        return monthPage;
    }
    
    /**
     * 选择的日期转换为Holiday
     * @param dates 
     */
    private void toHoliday(List<Date> dates){
        boolean isBreak = false;
        Holiday holiday = null;
        for(Date d : dates){
            if(holidaysOfMonth != null){
                for(Holiday h : holidaysOfMonth){
                    if(h.getHolidayDate().getTime() == d.getTime()){
                        selectedHolidays.add(h);
                        isBreak = true;
                        break;
                    }
                }
                if(!isBreak){
                    holiday = new Holiday();
                    holiday.setHolidayId(UUID.randomUUID().toString());
                    holiday.setHolidayDate(d);
                    selectedHolidays.add(holiday);
                }
            }else{
                holiday = new Holiday();
                holiday.setHolidayId(UUID.randomUUID().toString());
                holiday.setHolidayDate(d);
                selectedHolidays.add(holiday);
            }
        }
        
    }
    public void init(){
        monthTxFd.setEditable(false);
        refreshBtnActionPerformed(null);
    }
}
