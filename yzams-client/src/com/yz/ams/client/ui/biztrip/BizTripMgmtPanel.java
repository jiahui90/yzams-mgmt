/*
 * BizTripMgmtPanel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-06 11:01:18
 */
package com.yz.ams.client.ui.biztrip;

import com.nazca.sql.PageResult;
import com.nazca.ui.NWaitingPanel;
import com.nazca.ui.TextHinter;
import com.nazca.ui.UIUtilities;
import com.nazca.ui.laf.border.IconLabelBorder;
import com.nazca.ui.pagination.PaginationListener;
import com.nazca.ui.pagination.TablePageSession;
import com.nazca.ui.util.CardLayoutWrapper;
import com.nazca.usm.model.USMSUser;
import com.nazca.util.NazcaFormater;
import com.nazca.util.StringUtil;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.DeleteOperationPanel;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.agent.DeleteBizTripAgent;
import com.yz.ams.client.agent.QueryBizTripAgent;
import com.yz.ams.client.model.BizTripTableMode;
import com.yz.ams.client.renderer.BizTripTableRenderer;
import com.yz.ams.client.util.ClientUtils;
import com.yz.ams.client.util.DateUtil;
import com.yz.ams.client.util.StaticDataUtil;
import com.yz.ams.model.BizTrip;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * 出差管理面板
 *
 * @author Your Name <Song Haixiang >
 */
public class BizTripMgmtPanel extends javax.swing.JPanel {
    private CardLayoutWrapper leftCard = null;
    private CardLayoutWrapper rightCard = null;
    private IconLabelBorder leftBorder = null;
    private IconLabelBorder rightBorder = null;
    private BizTripTableMode tableModel = null;
    private BizTripTableRenderer tableRenderer = null;
    private QueryBizTripAgent queryBizTripAgent = null;
    private BizTrip curBizTtip = null;
    private DeleteBizTripAgent deleteAgent = null;
             

    /**
     * Creates new form BizTripMgmtPanel
     */
    public BizTripMgmtPanel() {
        initComponents();
        initCommon();
        initModelAndRenderer();
        initUI();
        initAgentAndListener();
        refreshBtnActionPerformed(null);
    }

    private void initCommon() {
        leftCard = new CardLayoutWrapper(cardPane1);
        rightCard = new CardLayoutWrapper(rightPane);
    }

    private void initModelAndRenderer() {
        tableModel = new BizTripTableMode();
        tableComp.setModel(tableModel);
        tableRenderer = new BizTripTableRenderer();
        tableComp.setDefaultRenderer(Object.class, tableRenderer);
        TableRowSorter<TableModel> rightRowSorter = UIUtilities.generateAndSetTriStateRowSorter(tableComp, tableModel);
        tableComp.setRowSorter(rightRowSorter);
    }

    private void initUI() {
        leftBorder = new IconLabelBorder(getClass().getResource(//左右面板标题
                "/com/yz/ams/client/res/vacation-list-32.png"), "出差信息列表");
        leftPane.setBorder(leftBorder);
        rightBorder = new IconLabelBorder(getClass().getResource(
                "/com/yz/ams/client/res/vacation-info-32.png"), "详细信息");
        rightPane.setBorder(rightBorder);
        UIUtilities.attachSearchIcon(searchTxFd);//检索框加搜索标志
        TextHinter.attach("输入内容并回车", searchTxFd);
        UIUtilities.makeItLikeLabel(bizTripNameTxFd);//申请人//面板右侧详情信息的输入框变成无边框
        UIUtilities.makeItLikeLabel(typeTxFd);//类型
        UIUtilities.makeItLikeLabel(locationTxFd);//地点
        UIUtilities.makeItLikeLabel(startDateTxFd);//开始时间
        UIUtilities.makeItLikeLabel(daysTxFd);//天数
        UIUtilities.makeItLikeLabel(startTimeTxFd);//结束时间
        UIUtilities.makeItLikeLabel(auditStateTxFd);//状态
        UIUtilities.makeItLikeLabel(staffIdsTxFd);//出差人员
        jLabel21.setVisible(false);
        typeTxFd.setVisible(false);
//        UIUtilities.makeItLikeLabel(bizTripMemoTxAa);//文本框

    }

    private void initAgentAndListener() {
        queryBizTripAgent = new QueryBizTripAgent();
        queryBizTripAgent.addListener(queryBizTripAgentLis);

        pagePane.addPaginationListener(new PaginationListener() { //分页数据监听
            @Override
            public void onPageChanged(TablePageSession tps) {
                refreshBtnActionPerformed(null);
            }
        });
        
        deleteAgent = new DeleteBizTripAgent();

        tableComp.getSelectionModel().addListSelectionListener(new ListSelectionListener() {//数据监听
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectingBizTrip();
                }

            }
        });
        searchTxFd.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                    refreshBtnActionPerformed(null);
                }
            }
        });
    }

    private void selectingBizTrip() {//表格数据监听
        int idx = tableComp.getSelectedRow();
        if (idx >= 0) {
            idx = tableComp.convertRowIndexToModel(idx);
            curBizTtip = tableModel.getData(idx);
            updateBtn.setEnabled(true);
            deleteBtn.setEnabled(true);
            fillRightpane();
        } else {
            updateBtn.setEnabled(false);
            deleteBtn.setEnabled(false);
        }
    }

    private void fillRightpane() {
        bizTripNameTxFd.setText(curBizTtip.getApplicantName());//申请人
         typeTxFd.setText("--");//出差类型TODD
        locationTxFd.setText(curBizTtip.getLocation());//出差地点
        startDateTxFd.setText(formatStartTime(curBizTtip.getStartDate(), curBizTtip.isMorning())); //开始时间
        daysTxFd.setText(String.valueOf(curBizTtip.getDays()));//出差天数
        startTimeTxFd.setText(
                catEndTime(curBizTtip.getEndDate()));//结束时间
        auditStateTxFd.setText(curBizTtip.getAuditState().toString());//出差状态
        jScrollPane2.getVerticalScrollBar().setValue(0);
        //人员列表
        String staffIds = curBizTtip.getStaffIds();
        Map<String, USMSUser> userMap = StaticDataUtil.getUserMap();
        String peosonName = "";
        if (!StringUtil.isEmpty(staffIds)) {
            String [] ids = staffIds.split(",");
            for (String id : ids) {
                if (userMap != null) {
                    USMSUser user = userMap.get(id);
                    if (user != null) {
                        peosonName += userMap.get(id).getName() +"，";
                    }
                }
            }
        }
        if (peosonName.length() >= 1) {
            staffIdsTxFd.setText(peosonName.substring(0, peosonName.length()-1));
        }else{
            staffIdsTxFd.setText("--");
        }
        bizTripMemoTxAa.setText(curBizTtip.getMemo());//事由
    }

    private String formatStartTime(Date d, boolean m) {
        StringBuilder sb = new StringBuilder(NazcaFormater.getSimpleDateString(d));
        if (m) {
            sb.append(" 上午");
        } else {
            sb.append(" 下午");
        }
        return sb.toString();
    }

     private String catEndTime(Date endDate) {
        int hour = DateUtil.getHourOfDate(endDate);
        StringBuilder sb = new StringBuilder(NazcaFormater.getSimpleDateString(endDate));
        if (hour == 12) {
            sb.append(" 上午");
        } else {
            sb.append(" 下午");
        }
        return sb.toString();
    }
    

    private void setCompsEnabled(boolean b) {//操作时按钮禁用
        refreshBtn.setEnabled(b);
        addBtn.setEnabled(b);
        updateBtn.setEnabled(b);
        deleteBtn.setEnabled(b);
        searchTxFd.setEnabled(b);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane2 = new javax.swing.JSplitPane();
        leftPane = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        refreshBtn = new javax.swing.JButton();
        addBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(2000, 0));
        searchTxFd = new javax.swing.JTextField();
        cardPane1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        pagePane = new com.nazca.ui.pagination.PaginationPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableComp = new javax.swing.JTable();
        waitPane = new com.nazca.ui.NWaitingPanel();
        rightPane = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        bizTripMemoTxAa = new javax.swing.JTextArea();
        bizTripNameTxFd = new javax.swing.JTextField();
        typeTxFd = new javax.swing.JTextField();
        locationTxFd = new javax.swing.JTextField();
        startDateTxFd = new javax.swing.JTextField();
        daysTxFd = new javax.swing.JTextField();
        startTimeTxFd = new javax.swing.JTextField();
        auditStateTxFd = new javax.swing.JTextField();
        staffIdsTxFd = new javax.swing.JTextField();
        waitPane1 = new com.nazca.ui.NWaitingPanel();

        setLayout(new java.awt.BorderLayout());

        jSplitPane2.setResizeWeight(0.65);

        leftPane.setMaximumSize(new java.awt.Dimension(10, 10));
        leftPane.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        refreshBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/refresh_16.png"))); // NOI18N
        refreshBtn.setText("刷新");
        refreshBtn.setFocusable(false);
        refreshBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(refreshBtn);

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/add_16.png"))); // NOI18N
        addBtn.setText("添加");
        addBtn.setFocusable(false);
        addBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(addBtn);

        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/update-16.png"))); // NOI18N
        updateBtn.setText("修改");
        updateBtn.setFocusable(false);
        updateBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(updateBtn);

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/delete_16.png"))); // NOI18N
        deleteBtn.setText("删除");
        deleteBtn.setFocusable(false);
        deleteBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(deleteBtn);
        jToolBar1.add(filler1);

        searchTxFd.setMaximumSize(new java.awt.Dimension(150, 21));
        searchTxFd.setMinimumSize(new java.awt.Dimension(150, 21));
        searchTxFd.setPreferredSize(new java.awt.Dimension(150, 21));
        jToolBar1.add(searchTxFd);

        leftPane.add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        cardPane1.setLayout(new java.awt.CardLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(pagePane, java.awt.BorderLayout.PAGE_END);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

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
        tableComp.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tableComp);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        cardPane1.add(jPanel2, "CONTENT");
        cardPane1.add(waitPane, "WAIT");

        leftPane.add(cardPane1, java.awt.BorderLayout.CENTER);

        jSplitPane2.setLeftComponent(leftPane);

        rightPane.setMaximumSize(new java.awt.Dimension(10, 10));
        rightPane.setLayout(new java.awt.CardLayout());

        jLabel20.setText("　申请人：");

        jLabel21.setText("　　类型：");

        jLabel22.setText("　　地点：");

        jLabel23.setText("开始时间：");

        jLabel24.setText("　　天数：");

        jLabel25.setText("结束时间：");

        jLabel26.setText("　　状态：");

        jLabel27.setText("出差人员：");

        jLabel28.setText("出差事由：");

        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        bizTripMemoTxAa.setEditable(false);
        bizTripMemoTxAa.setLineWrap(true);
        jScrollPane6.setViewportView(bizTripMemoTxAa);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel28)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6)
                            .addComponent(auditStateTxFd)
                            .addComponent(bizTripNameTxFd)
                            .addComponent(typeTxFd)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(locationTxFd))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(daysTxFd)
                            .addComponent(startDateTxFd, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(staffIdsTxFd)
                            .addComponent(startTimeTxFd))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(bizTripNameTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(typeTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(locationTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(startDateTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(daysTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(startTimeTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(auditStateTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(staffIdsTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))
                .addContainerGap())
        );

        rightPane.add(jPanel1, "CONTENT");
        rightPane.add(waitPane1, "WAIT");

        jSplitPane2.setRightComponent(rightPane);

        add(jSplitPane2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
         String keywords = searchTxFd.getText().trim();
        if (keywords.isEmpty()) {
            keywords = null;
        }
        TablePageSession page = pagePane.getPageSession();
        queryBizTripAgent.setParameters(keywords, page.getCurPageNum(), page.getPageSize());
        queryBizTripAgent.start();

    }//GEN-LAST:event_refreshBtnActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        AddOrUpdateBizTripPanel editPane = new AddOrUpdateBizTripPanel();
        BizTrip biz = editPane.showMe(this);
        if (biz != null) {
            refreshBtnActionPerformed(evt);
        }
    }//GEN-LAST:event_addBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        AddOrUpdateBizTripPanel editPane = new AddOrUpdateBizTripPanel();
        editPane.setPaneContent(curBizTtip);
        BizTrip biz = editPane.showMe1(this);
        if (biz != null) {
            refreshBtnActionPerformed(evt);
        }
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        int row = tableComp.getSelectedRow();
        if (row >= 0) {
            row = tableComp.convertRowIndexToModel(row);
            BizTrip data = tableModel.getData(row);
            USMSUser curUser = ClientContext.getUser();
            data.setModifierId(curUser.getId());
            data.setModifierName(curUser.getName());
            deleteAgent.setBiz(data);
            DeleteOperationPanel<BizTrip> deletePane = new DeleteOperationPanel<>(deleteAgent);
            deletePane.configSingleDelete("申请人", data.getApplicantName());
            BizTrip biz = deletePane.showMe(deleteBtn, ClientUtils.buildImageIcon("delete-biztrip.png"), "删除出差信息", 400, 150);
            if (biz != null) {
                refreshBtnActionPerformed(null);
            }
        } else {
            UIUtilities.warningDlg(this, "请先选择要删除的出差信息");
            return;
        }
    }//GEN-LAST:event_deleteBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JTextField auditStateTxFd;
    private javax.swing.JTextArea bizTripMemoTxAa;
    private javax.swing.JTextField bizTripNameTxFd;
    private javax.swing.JPanel cardPane1;
    private javax.swing.JTextField daysTxFd;
    private javax.swing.JButton deleteBtn;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel leftPane;
    private javax.swing.JTextField locationTxFd;
    private com.nazca.ui.pagination.PaginationPanel pagePane;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JPanel rightPane;
    private javax.swing.JTextField searchTxFd;
    private javax.swing.JTextField staffIdsTxFd;
    private javax.swing.JTextField startDateTxFd;
    private javax.swing.JTextField startTimeTxFd;
    private javax.swing.JTable tableComp;
    private javax.swing.JTextField typeTxFd;
    private javax.swing.JButton updateBtn;
    private com.nazca.ui.NWaitingPanel waitPane;
    private com.nazca.ui.NWaitingPanel waitPane1;
    // End of variables declaration//GEN-END:variables

    private AgentListener<PageResult<BizTrip>> queryBizTripAgentLis = new AgentListener<PageResult<BizTrip>>() {//监听
        @Override//监听
        public void onStarted(long seq) {
            setCompsEnabled(false);
            waitPane.setIndeterminate(true);
            waitPane.showMsgMode("数据加载中，请稍后...", 0, NWaitingPanel.MSG_MODE_INFO);
            waitPane.showWaitingMode();
            leftCard.show(CardLayoutWrapper.WAIT);
            waitPane1.setIndeterminate(true);
            waitPane1.showWaitingMode();
            rightCard.show(CardLayoutWrapper.WAIT);

        }

        @Override
        public void onSucceeded(PageResult<BizTrip> result, long seq) {
            if (result != null && result.getTotalCount() > 0) {//判断是否为空
                List<BizTrip> list = result.getPageList();//数据集合
                int totalCount = result.getTotalCount();//当前页
                int pageSize = result.getPageSize();
                tableModel.setDatas(list);
                leftCard.show(CardLayoutWrapper.CONTENT);
                waitPane.setIndeterminate(false);
                rightCard.show(CardLayoutWrapper.CONTENT);//切换面板
                waitPane1.setIndeterminate(false);//关闭等待面板
                tableComp.getSelectionModel().setSelectionInterval(0, 0);//刷新结束默认第一条数据
                pagePane.initPageButKeepSession(totalCount, pageSize);
            } else {
                selectingBizTrip();
                updateBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                waitPane.showMsgMode("暂无出差信息", 0, NWaitingPanel.MSG_MODE_INFO);
                leftCard.show(CardLayoutWrapper.WAIT);
                waitPane1.showMsgMode("请选择出差信息", 0, NWaitingPanel.MSG_MODE_INFO);
                rightCard.show(CardLayoutWrapper.WAIT);
            }
            searchTxFd.setEnabled(true);
            refreshBtn.setEnabled(true);
            addBtn.setEnabled(true);
        }

        @Override
        public void onFailed(String errMsg, int errCode, long seq) {
            refreshBtn.setEnabled(true);
            addBtn.setEnabled(true);
            searchTxFd.setEnabled(true);
            waitPane.showMsgMode(errMsg, errCode, NWaitingPanel.MSG_MODE_ERROR);
            leftCard.show(CardLayoutWrapper.FAIL);
            waitPane.setIndeterminate(false);
            waitPane1.showMsgMode(errMsg, errCode, NWaitingPanel.MSG_MODE_ERROR);
            rightCard.show(CardLayoutWrapper.FAIL);
            waitPane1.setIndeterminate(false);
        }

    };

    public void init() {
        refreshBtnActionPerformed(null);
    }


    
}
