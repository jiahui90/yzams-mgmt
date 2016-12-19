/*
 * VacationMgmtPanel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-05 16:32:33
 */
package com.yz.ams.client.ui.vacation;

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
import com.nazca.util.TimeFairy;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.DeleteOperationPanel;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.agent.ConfirmSickPicVacationAgent;
import com.yz.ams.client.agent.DeleteVacationAgent;
import com.yz.ams.client.agent.QueryVacationAgent;
import com.yz.ams.client.model.VacationTableModel;
import com.yz.ams.client.renderer.VacationTableRenderer;
import com.yz.ams.client.util.ClientUtils;
import com.yz.ams.consts.AuditStateEnum;
import com.yz.ams.consts.Permissions;
import com.yz.ams.model.Vacation;
import com.yz.ams.model.VacationDetail;
import com.yz.ams.model.wrap.mgmt.VacationInfo;
import com.yz.ams.util.DateUtil;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * 请假管理面板
 *
 * @author Your Name <Song Haixiang >
 */
public class VacationMgmtPanel extends javax.swing.JPanel {

    private CardLayoutWrapper leftCard = null;
    private CardLayoutWrapper rightCard = null;
    private CardLayoutWrapper picCard = null;
    private IconLabelBorder leftBorder = null;
    private IconLabelBorder rightBorder = null;
    private VacationTableModel tableModel = null;
    private VacationTableRenderer tableRenderer = null;
    private QueryVacationAgent queryVacationAgent = null;
    private VacationInfo curVacation = null;
    private DeleteVacationAgent deleteVacationAgent = null;
    private ConfirmSickPicVacationAgent confirmSickPicVacationAgent = null;
    private ConfirmeSickVacationPanel editPane = new ConfirmeSickVacationPanel();
    private ShowSickVacationPanel showEditPane = new ShowSickVacationPanel();
    private BufferedImage backCurImage = null;
    private Map<String, BufferedImage> picMap = new HashMap<>();
    private String vacationIds = "";
    private boolean isPass = true;

    /**
     * Creates new form VacationMgmtPanel
     */
    public VacationMgmtPanel() {
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
        picCard = new CardLayoutWrapper(certificateProvePane);
    }

    private void initModelAndRenderer() {
        tableModel = new VacationTableModel();
        tableComp.setModel(tableModel);
        tableRenderer = new VacationTableRenderer();
        tableComp.setDefaultRenderer(Object.class, tableRenderer);
        TableRowSorter<TableModel> rightRowSorter = UIUtilities.generateAndSetTriStateRowSorter(tableComp, tableModel);
        tableComp.setRowSorter(rightRowSorter);
    }

    private void initUI() {
        leftBorder = new IconLabelBorder(getClass().getResource(//左右面板标题
                "/com/yz/ams/client/res/vacation-list-32.png"), "请假信息列表");
        leftPane.setBorder(leftBorder);
        rightBorder = new IconLabelBorder(getClass().getResource(
                "/com/yz/ams/client/res/vacation-info-32.png"), "详细信息");
        rightPane.setBorder(rightBorder);
        UIUtilities.attachSearchIcon(searchTxFd);
        TextHinter.attach("输入内容并回车", searchTxFd);
        UIUtilities.makeItLikeLabel(vacationNameTxFd);
        UIUtilities.makeItLikeLabel(auditorNameTxFd);
        UIUtilities.makeItLikeLabel(startTimeTxFd);
        UIUtilities.makeItLikeLabel(vacationDaysTxFd);
        UIUtilities.makeItLikeLabel(finishTimeTxFd);
        UIUtilities.makeItLikeLabel(auditStateTxFd);
        
        if(!ClientContext.hasPermission(Permissions.HR)){
            confirmBtn.setVisible(false);
        } 
    }

    private void initAgentAndListener() {//监听
        queryVacationAgent = new QueryVacationAgent();
        confirmSickPicVacationAgent = new ConfirmSickPicVacationAgent();
        confirmSickPicVacationAgent.addListener(picAgentListener);
        queryVacationAgent.addListener(queryVacationAgentLis);
        pagePane.addPaginationListener(new PaginationListener() {//分页数据监听
            @Override
            public void onPageChanged(TablePageSession page) {
                refreshBtnActionPerformed(null);
            }
        });
        deleteVacationAgent = new DeleteVacationAgent();
        deleteVacationAgent.addListener(null);
        tableComp.getSelectionModel().addListSelectionListener(new ListSelectionListener() {//分页监听
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectingVacation();
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

    private void selectingVacation() {//表格监听
        isPass = true;
        int[] ids = tableComp.getSelectedRows();
        
            if (ids.length == 1) {  //选择一条
                int index = tableComp.convertRowIndexToModel(ids[0]);
                curVacation = tableModel.getData(index);
                vacationIds = curVacation.getVacation().getVacationId();
                updateBtn.setEnabled(true);
                deleteBtn.setEnabled(true);
                printBtn.setEnabled(true);
                confirmBtn.setEnabled(true);
                rightCard.show(CardLayoutWrapper.CONTENT);
                if(curVacation.getVacation().getCertificatePicId() != null){
                if(picMap.get(curVacation.getVacation().getCertificatePicId()) == null){
                confirmSickPicVacationAgent.setVacation(curVacation.getVacation());
                confirmSickPicVacationAgent.start();}}
                fillRightPane();
                if (curVacation.getVacation().getAuditState() != AuditStateEnum.PASS) {
                    isPass = false;
                }
                 boolean sickType = curVacation.getVacation().isHasSickType();
                    if (sickType == true  && curVacation.getVacation().getAuditState() == AuditStateEnum.PASS) {
                    sickLb.setVisible(true);
                    certificateProvePane.setVisible(true);
                } else {
                    sickLb.setVisible(false);
                    certificateProvePane.setVisible(false);
                    confirmBtn.setEnabled(false);
                }
            } else if(ids.length > 1){ //选择多条
                updateBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                confirmBtn.setEnabled(false);
                rightCard.show(CardLayoutWrapper.WAIT);
                rightWaitPane.showMsgMode("请选择一条请假显示请假详细信息", 0, NWaitingPanel.MSG_MODE_INFO);
                vacationIds = ""; 
                VacationInfo vacationInfo = null;
                for(int id : ids){
                   int index = tableComp.convertRowIndexToModel(id);
                    vacationInfo = tableModel.getData(index);
                    if(vacationInfo.getVacation().getAuditState() != AuditStateEnum.PASS){
                        isPass = false;
                    }
                    
                    String applicantId = vacationInfo.getVacation().getVacationId();
                    vacationIds = vacationIds + applicantId + ",";
                }
                vacationIds = vacationIds.substring(0, vacationIds.length() - 1);
            }
    }

    private void fillRightPane() {
        Vacation vac = curVacation.getVacation();
        vacationNameTxFd.setText(vac.getApplicantName());//请假人
        //审批人
        if (!StringUtil.isEmpty(vac.getAuditorName1())) {
            auditorNameTxFd.setText(vac.getAuditorName1());
        } else {
            auditorNameTxFd.setText("--");
        }
        List<VacationDetail> lis = curVacation.getVacationDetail();
        String vacType = "";
        if (lis != null && lis.size() > 0) {
            for (VacationDetail detail1 : lis) {
                vacType += detail1.getVacationType().toString() + "" + detail1.getVacationDays() + "天，";
            }
            vacationTypeTxAa.setText(vacType.substring(0, vacType.length() - 1));
        } else {
            vacationTypeTxAa.setText("");
        }
        auditStateTxFd.setText(vac.getAuditState().toString());
        startTimeTxFd.setText(formatStartTime(vac.getVacationDate(), vac.isMorning()));//开始时间
        vacationDaysTxFd.setText(String.valueOf(vac.getTotalDays()));//请假天数
        finishTimeTxFd.setText(DateUtil.catEndTime(curVacation.getEndDate()));//结束时间
        vacationMemoTxAa.setText(vac.getMemo());//请假事由
        if(vac.getCertificatePicId() != null){
            BufferedImage bd = picMap.get(vac.getCertificatePicId());
            backCurImage = bd;
            if(bd != null){
                BufferedImage newImage = new BufferedImage(900, 400, bd.getType());
                Graphics g = newImage.getGraphics();
                g.drawImage(bd, 0, 0, 900, 400, null);
                g.dispose();
                bd = newImage;
                picCard.show(CardLayoutWrapper.CONTENT);
                frontImagePanel.setImage(bd);
                   } }else if(vac.getAuditState() == AuditStateEnum.PASS) {

            confirmBtn.setEnabled(false);
            frontImagePanel.removeAll();
            picCard.show(CardLayoutWrapper.FAIL);
            failureInfoPanel1.setFailedInfo("请上传病假证明");
        }
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

    private void setCompsEnabled(boolean enabled) {//刷新时禁用所有按钮
        refreshBtn.setEnabled(enabled);
        addBtn.setEnabled(enabled);
        updateBtn.setEnabled(enabled);
        deleteBtn.setEnabled(enabled);
        printBtn.setEnabled(enabled);
        confirmBtn.setEnabled(enabled);
        searchTxFd.setEnabled(enabled);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        leftPane = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        refreshBtn = new javax.swing.JButton();
        addBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        printBtn = new javax.swing.JButton();
        confirmBtn = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(2000, 0));
        searchTxFd = new javax.swing.JTextField();
        cardPane1 = new javax.swing.JPanel();
        contentPane = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableComp = new javax.swing.JTable();
        pagePane = new com.nazca.ui.pagination.PaginationPanel();
        leftWaitPanel = new com.nazca.ui.NWaitingPanel();
        rightPane = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        vacationNameTxFd = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        auditorNameTxFd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        vacationTypeTxAa = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        startTimeTxFd = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        vacationDaysTxFd = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        finishTimeTxFd = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        auditStateTxFd = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        vacationMemoTxAa = new javax.swing.JTextArea();
        certificateProvePane = new javax.swing.JPanel();
        frontImagePanel = new com.nazca.ui.NImagePanel();
        waitingPanel1 = new com.nazca.ui.WaitingPanel();
        picWaitPane1 = new com.nazca.ui.NWaitingPanel();
        failureInfoPanel1 = new com.nazca.ui.FailureInfoPanel();
        sickLb = new javax.swing.JLabel();
        rightWaitPane = new com.nazca.ui.NWaitingPanel();

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setResizeWeight(0.65);

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

        printBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/print-16.png"))); // NOI18N
        printBtn.setText("打印请假条");
        printBtn.setFocusable(false);
        printBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        printBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(printBtn);

        confirmBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/confirm-16.png"))); // NOI18N
        confirmBtn.setText("确认病假证明");
        confirmBtn.setFocusable(false);
        confirmBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        confirmBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(confirmBtn);
        jToolBar1.add(filler1);

        searchTxFd.setMaximumSize(new java.awt.Dimension(150, 21));
        searchTxFd.setMinimumSize(new java.awt.Dimension(150, 21));
        searchTxFd.setPreferredSize(new java.awt.Dimension(150, 21));
        searchTxFd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTxFdActionPerformed(evt);
            }
        });
        jToolBar1.add(searchTxFd);

        leftPane.add(jToolBar1, java.awt.BorderLayout.NORTH);

        cardPane1.setMaximumSize(new java.awt.Dimension(10, 10));
        cardPane1.setLayout(new java.awt.CardLayout());

        contentPane.setMaximumSize(new java.awt.Dimension(10, 10));
        contentPane.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(10, 10));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 64));

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
        tableComp.setMaximumSize(new java.awt.Dimension(10, 10));
        tableComp.setMinimumSize(new java.awt.Dimension(10, 10));
        jScrollPane1.setViewportView(tableComp);

        contentPane.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        contentPane.add(pagePane, java.awt.BorderLayout.SOUTH);

        cardPane1.add(contentPane, "CONTENT");
        cardPane1.add(leftWaitPanel, "WAIT");

        leftPane.add(cardPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(leftPane);

        rightPane.setMaximumSize(new java.awt.Dimension(10, 10));
        rightPane.setLayout(new java.awt.CardLayout());

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jLabel1.setText("　请假人：");

        vacationNameTxFd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vacationNameTxFdActionPerformed(evt);
            }
        });

        jLabel2.setText("　审批人：");

        jLabel3.setText("请假类型：");

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setDoubleBuffered(true);

        vacationTypeTxAa.setEditable(false);
        vacationTypeTxAa.setColumns(20);
        vacationTypeTxAa.setLineWrap(true);
        jScrollPane3.setViewportView(vacationTypeTxAa);

        jLabel4.setText("开始时间：");

        jLabel5.setText("请假天数：");

        jLabel6.setText("结束时间：");

        jLabel7.setText("　　状态：");

        jLabel8.setText("请假事由：");

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        vacationMemoTxAa.setEditable(false);
        vacationMemoTxAa.setColumns(20);
        vacationMemoTxAa.setLineWrap(true);
        jScrollPane4.setViewportView(vacationMemoTxAa);

        certificateProvePane.setLayout(new java.awt.CardLayout());

        frontImagePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                frontImagePanelMouseClicked(evt);
            }
        });
        frontImagePanel.add(waitingPanel1);

        certificateProvePane.add(frontImagePanel, "CONTENT");
        certificateProvePane.add(picWaitPane1, "WAIT");
        certificateProvePane.add(failureInfoPanel1, "FAIL");

        sickLb.setText("病假证明：");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(sickLb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(certificateProvePane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(vacationNameTxFd))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(auditorNameTxFd))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                            .addComponent(startTimeTxFd)
                            .addComponent(vacationDaysTxFd)
                            .addComponent(finishTimeTxFd)
                            .addComponent(auditStateTxFd)
                            .addComponent(jScrollPane4))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(vacationNameTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(auditorNameTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startTimeTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vacationDaysTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(finishTimeTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(auditStateTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sickLb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(certificateProvePane, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jScrollPane2.setViewportView(jPanel1);

        rightPane.add(jScrollPane2, "CONTENT");
        rightPane.add(rightWaitPane, "WAIT");

        jSplitPane1.setRightComponent(rightPane);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        String keywords = searchTxFd.getText().trim();
        if (keywords.isEmpty()) {
            keywords = null;
        }
        TablePageSession page = pagePane.getPageSession();
        queryVacationAgent.setParameters(keywords, page.getCurPageNum(), page.getPageSize());
        queryVacationAgent.start();
    }//GEN-LAST:event_refreshBtnActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        AddOrUpdateVacationPanel editPane = new AddOrUpdateVacationPanel();
        VacationInfo vacations = editPane.showMe(this);
        if (vacations != null) {
            refreshBtnActionPerformed(evt);
        }
    }//GEN-LAST:event_addBtnActionPerformed

    private void searchTxFdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTxFdActionPerformed
        refreshBtnActionPerformed(null);
    }//GEN-LAST:event_searchTxFdActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        AddOrUpdateVacationPanel editPane = new AddOrUpdateVacationPanel();
        editPane.initPaneContent(curVacation);
        VacationInfo vacations = editPane.showMe1(this);
        if (vacations != null) {
            refreshBtnActionPerformed(evt);
        }
    }//GEN-LAST:event_updateBtnActionPerformed

    private void vacationNameTxFdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vacationNameTxFdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vacationNameTxFdActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        int row = tableComp.getSelectedRow();
        if (row >= 0) {
            row = tableComp.convertRowIndexToModel(row);
            VacationInfo data = tableModel.getData(row);
            USMSUser curUser = ClientContext.getUser();
            VacationInfo info = new VacationInfo();
            Vacation vac = data.getVacation();
            info.setVacation(vac);
            vac.setModifierId(curUser.getId());
            vac.setModifierName(curUser.getName());
            if(vac.getAuditState() == AuditStateEnum.WAIT_FOR_PM){
                deleteVacationAgent.setVacation(info);
                DeleteOperationPanel<VacationInfo> deletePane = new DeleteOperationPanel<>(deleteVacationAgent);
                deletePane.configSingleDelete("请假人", vac.getApplicantName());
                VacationInfo vac1 = deletePane.showMe(deleteBtn, ClientUtils.buildImageIcon("delete-vacation-info.png"), "删除请假信息", 400, 150);
                if (vac1 != null) {
                    refreshBtnActionPerformed(null);
                }
            }else{
                UIUtilities.warningDlg(this, "已经审核的信息不能删除");
            }
            
        } else {
            UIUtilities.warningDlg(this, "请先选择要删除的请假信息");
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void confirmBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmBtnActionPerformed
        editPane.setPicResult(backCurImage);
        editPane.setCurVacation(curVacation.getVacation());
        editPane.showMe(this);
    }//GEN-LAST:event_confirmBtnActionPerformed

    private void frontImagePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_frontImagePanelMouseClicked
        showEditPane.setPicResult(backCurImage);
        showEditPane.showMe(this);
    }//GEN-LAST:event_frontImagePanelMouseClicked

    private void printBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBtnActionPerformed
        if(!isPass){
            UIUtilities.warningDlg(contentPane, "请选择审核通过的打印!");
        }else{
            PreviewPrintVacationNotePanel printPanel = new PreviewPrintVacationNotePanel();
            printPanel.showMe(jPanel1, this.vacationIds);
        }
    }//GEN-LAST:event_printBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JTextField auditStateTxFd;
    private javax.swing.JTextField auditorNameTxFd;
    private javax.swing.JPanel cardPane1;
    private javax.swing.JPanel certificateProvePane;
    private javax.swing.JButton confirmBtn;
    private javax.swing.JPanel contentPane;
    private javax.swing.JButton deleteBtn;
    private com.nazca.ui.FailureInfoPanel failureInfoPanel1;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JTextField finishTimeTxFd;
    private com.nazca.ui.NImagePanel frontImagePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel leftPane;
    private com.nazca.ui.NWaitingPanel leftWaitPanel;
    private com.nazca.ui.pagination.PaginationPanel pagePane;
    private com.nazca.ui.NWaitingPanel picWaitPane1;
    private javax.swing.JButton printBtn;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JPanel rightPane;
    private com.nazca.ui.NWaitingPanel rightWaitPane;
    private javax.swing.JTextField searchTxFd;
    private javax.swing.JLabel sickLb;
    private javax.swing.JTextField startTimeTxFd;
    private javax.swing.JTable tableComp;
    private javax.swing.JButton updateBtn;
    private javax.swing.JTextField vacationDaysTxFd;
    private javax.swing.JTextArea vacationMemoTxAa;
    private javax.swing.JTextField vacationNameTxFd;
    private javax.swing.JTextArea vacationTypeTxAa;
    private com.nazca.ui.WaitingPanel waitingPanel1;
    // End of variables declaration//GEN-END:variables

    private AgentListener<PageResult<VacationInfo>> queryVacationAgentLis = new AgentListener<PageResult<VacationInfo>>() {//监听
        @Override//监听
        public void onStarted(long seq) {
            setCompsEnabled(false);
            leftWaitPanel.setIndeterminate(true);
            leftWaitPanel.showMsgMode("数据加载中，请稍后...", 0, NWaitingPanel.MSG_MODE_INFO);
            leftWaitPanel.showWaitingMode();
            leftCard.show(CardLayoutWrapper.WAIT);
            rightWaitPane.setIndeterminate(true);
            rightWaitPane.showWaitingMode();
            rightCard.show(CardLayoutWrapper.WAIT);
        }

        @Override//成功面板
        public void onSucceeded(PageResult<VacationInfo> result, long seq) {
            if (result != null && result.getTotalCount() > 0) {//判断是否为空
                List<VacationInfo> list = result.getPageList();//数据集合
                int totalCount = result.getTotalCount();//当前页
                int pageSize = result.getPageSize();
                tableModel.setDatas(list);
                leftCard.show(CardLayoutWrapper.CONTENT);
                leftWaitPanel.setIndeterminate(false);
                rightCard.show(CardLayoutWrapper.CONTENT);//切换面板
                rightWaitPane.setIndeterminate(false);//关闭等待面板
                tableComp.getSelectionModel().setSelectionInterval(0, 0);//刷星结束默认第一条数据
                pagePane.initPageButKeepSession(totalCount, pageSize);

            } else {
                selectingVacation();
                updateBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                printBtn.setEnabled(false);
                leftWaitPanel.showMsgMode("暂无请假信息", 0, NWaitingPanel.MSG_MODE_INFO);
                leftCard.show(CardLayoutWrapper.WAIT);
                rightWaitPane.showMsgMode("请选择请假信息", 0, NWaitingPanel.MSG_MODE_INFO);
                rightCard.show(CardLayoutWrapper.WAIT);
            }
            refreshBtn.setEnabled(true);
            addBtn.setEnabled(true);
            searchTxFd.setEnabled(true);
        }

        @Override//失败面板
        public void onFailed(String errMsg, int errCode, long seq) {
            refreshBtn.setEnabled(true);
            addBtn.setEnabled(true);
            searchTxFd.setEnabled(true);
            leftWaitPanel.showMsgMode(errMsg, errCode, NWaitingPanel.MSG_MODE_ERROR);
            leftCard.show(CardLayoutWrapper.FAIL);
            leftWaitPanel.setIndeterminate(false);
            rightWaitPane.showMsgMode(errMsg, errCode, NWaitingPanel.MSG_MODE_ERROR);
            rightCard.show(CardLayoutWrapper.FAIL);
            rightWaitPane.setIndeterminate(false);
        }

    };

    AgentListener<InputStream> picAgentListener = new AgentListener<InputStream>() {
        @Override
        public void onStarted(long seq) {
            picWaitPane1.setIndeterminate(true);
            picWaitPane1.showWaitingMode();
            picCard.show(CardLayoutWrapper.WAIT);
        }

        @Override
        public void onSucceeded(InputStream result, long seq) {
            confirmBtn.setEnabled(true);
            picWaitPane1.setIndeterminate(false);
            try {
                if (result != null) {
                    BufferedImage curImage = ImageIO.read(result);
                    backCurImage = curImage;
                    //把查到的圖片信息備份到map中
                    picMap.put(curVacation.getVacation().getCertificatePicId(), backCurImage);
                    BufferedImage newImage = new BufferedImage(900, 400, curImage.getType());
                    Graphics g = newImage.getGraphics();
                    g.drawImage(curImage, 0, 0, 900, 400, null);
                    g.dispose();
                    curImage = newImage;
                    frontImagePanel.setImage(curImage);
                    new Thread() {
                        public void run() {
                            new TimeFairy().sleepIfNecessary();
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    picCard.show(CardLayoutWrapper.CONTENT);

                                }
                            });
                        }
                    }.start();
                } else {
                    confirmBtn.setEnabled(false);
                    picCard.show(CardLayoutWrapper.FAIL);
                    failureInfoPanel1.setFailedInfo("请上传病假证明");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        @Override

        public void onFailed(String errMsg, int errCode, long seq) {
            waitingPanel1.showMsgMode(errMsg, errCode, NWaitingPanel.MSG_MODE_ERROR);
            picCard.show(CardLayoutWrapper.FAIL);
            waitingPanel1.setIndeterminate(false);
        }
    };

    public void init() {
        refreshBtnActionPerformed(null);
    }

}
