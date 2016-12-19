/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yz.ams.client.comp;

import com.nazca.ui.NInternalDiag;
import com.nazca.ui.laf.NazcaLAFTool;
import com.yz.ams.client.util.ClientUtils;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

/**
 *
 * @author liuyizhe
 */
public class MonthChoosePane extends javax.swing.JPanel {
    
    private NInternalDiag<Date, JComponent> diag = null;

    String months[] = new String[]{"1月", "2月", "3月", "4月", "5月", "6月",
        "7月", "8月", "9月", "10月", "11月", "12月"};
    Map<JToggleButton, Integer> monthsButton1 = new HashMap<JToggleButton, Integer>();
    Map<Integer, JToggleButton> monthsButton2 = new HashMap<Integer, JToggleButton>();
    private Calendar selectDate;

    /**
     * Creates new form MonthChoosePane
     */
    public MonthChoosePane() {
        initComponents();
        jToolBar1.setUI(new StateToolbarUI());
        jPanel1.setLayout(new GridLayout(3, 4));
        for (int i = 0; i < months.length; i++) {
            JToggleButton numberButton = new JToggleButton();
            numberButton.setBorder(null);
            numberButton.setHorizontalAlignment(SwingConstants.RIGHT);
            numberButton.setText(months[i]);
            numberButton.setUI(new MonthBtnUI());
            buttonGroup1.add(numberButton);
            monthsButton1.put(numberButton, i + 1);
            monthsButton2.put(i + 1, numberButton);
            numberButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() instanceof JToggleButton) {
                        JToggleButton btn = (JToggleButton) e.getSource();
                        if (monthsButton1.containsKey(btn)) {
                            int month = monthsButton1.get(btn);
                            selectDate.set(Calendar.MONTH, month - 1);
                            selectDate.set(Calendar.DAY_OF_MONTH, 1);
                        }
                    }
                }
            });
            jPanel1.add(numberButton);
        }

        oKCancelPanel1.addOKCancelListener(new OKCancelPanelListener() {

            @Override
            public void onOKClicked() {
                NInternalDiag diag = NInternalDiag.findNInternalDiag(MonthChoosePane.this);
                if (diag != null) {
                    
                    diag.hideDiag(selectDate.getTime());
                }
            }

            @Override
            public void onCancelClicked() {
                NInternalDiag diag = NInternalDiag.findNInternalDiag(MonthChoosePane.this);
                if (diag != null) {
                    diag.hideDiag(null);
                }
            }
        });
    }

    public void setSelectDate(Date curDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        selectDate = cal;
        int year = selectDate.get(Calendar.YEAR);
        yearJL.setText(String.valueOf(year));
        int month = selectDate.get(Calendar.MONTH) + 1;
        for (Map.Entry<JToggleButton, Integer> entrySet : monthsButton1.entrySet()) {
            JToggleButton key = entrySet.getKey();
            Integer value = entrySet.getValue();
            if (value == month) {
                key.setUI(new CurMonthBtnUI());
                break;
            }
        }
//        setDisableBtn(curDate);
    }

    public void setDisableBtn(Date curDate) {
        Date nowDate = new Date();
        Calendar curCal = Calendar.getInstance();
        curCal.setTime(curDate);
        int culYear = curCal.get(Calendar.YEAR);

        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(nowDate);
        int nowYear = nowCal.get(Calendar.YEAR);

        int nowMonth = nowCal.get(Calendar.MONTH) + 1;
        for (int i = 1; i <= months.length; i++) {
            JToggleButton btn = monthsButton2.get(i);
            if (culYear == nowYear) {
                if (i > nowMonth) {
                    btn.setEnabled(false);
                    btn.setUI(new DisableMonthBtnUI());
                }
            } else {
                btn.setEnabled(true);
                btn.setUI(new MonthBtnUI());
            }
        }

//        if (culYear + 1 > nowYear) {
//            jButton2.setEnabled(false);
//        } else {
//            jButton2.setEnabled(true);
//        }
    }
    
    public Date showMe(JComponent parent,String teamId) {
        diag = new NInternalDiag<Date, JComponent>("选择月份", ClientUtils.buildImageIcon("select_month.png"), this,400, 250);
        return diag.showInternalDiag(parent);
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
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        yearJL = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        oKCancelPanel1 = new com.yz.ams.client.comp.OKCancelPanel();

        setMaximumSize(new java.awt.Dimension(230, 230));
        setMinimumSize(new java.awt.Dimension(230, 230));
        setPreferredSize(new java.awt.Dimension(230, 230));
        setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setMaximumSize(new java.awt.Dimension(2147483647, 30));
        jToolBar1.setMinimumSize(new java.awt.Dimension(30, 30));
        jToolBar1.setPreferredSize(new java.awt.Dimension(30, 30));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/pre.png"))); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButton1.setMaximumSize(new java.awt.Dimension(30, 30));
        jButton1.setMinimumSize(new java.awt.Dimension(30, 30));
        jButton1.setPreferredSize(new java.awt.Dimension(30, 30));
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        yearJL.setFont(yearJL.getFont().deriveFont(yearJL.getFont().getSize()+2f));
        yearJL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yearJL.setText("2015");
        yearJL.setMaximumSize(new java.awt.Dimension(300, 15));
        jToolBar1.add(yearJL);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/next.png"))); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButton2.setMaximumSize(new java.awt.Dimension(30, 30));
        jButton2.setMinimumSize(new java.awt.Dimension(30, 30));
        jButton2.setPreferredSize(new java.awt.Dimension(30, 30));
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        add(jToolBar1, java.awt.BorderLayout.NORTH);

        jPanel1.setBackground(new java.awt.Color(227, 235, 242));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel1.setLayout(new java.awt.GridBagLayout());
        add(jPanel1, java.awt.BorderLayout.CENTER);
        add(oKCancelPanel1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int year = Integer.parseInt(yearJL.getText());
        yearJL.setText(year - 1 + "");
//        jButton2.setEnabled(true);
        selectDate.add(Calendar.YEAR, -1);
//        setDisableBtn(selectDate.getTime());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int year = Integer.parseInt(yearJL.getText());
        Date nowDate = new Date();
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(nowDate);
        int nowYear = nowCal.get(Calendar.YEAR);
//        if (year + 1 > nowYear) {
//            jButton2.setEnabled(false);
//        } else {
        yearJL.setText(year + 1 + "");
//            jButton2.setEnabled(true);
        selectDate.add(Calendar.YEAR, 1);
//        }
//        setDisableBtn(selectDate.getTime());
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private com.yz.ams.client.comp.OKCancelPanel oKCancelPanel1;
    private javax.swing.JLabel yearJL;
    // End of variables declaration//GEN-END:variables

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        NazcaLAFTool.applyNazcaLAF();
        JFrame mainFrame = new JFrame("测试");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(230, 230);
        mainFrame.setLayout(new java.awt.BorderLayout());
        MonthChoosePane choosePane = new MonthChoosePane();
        choosePane.setSelectDate(new Date());
        mainFrame.add(choosePane, java.awt.BorderLayout.CENTER);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int w = mainFrame.getWidth();
        int h = mainFrame.getHeight();
        mainFrame.setLocation((width - w) / 2, (height - h) / 2);
        mainFrame.setVisible(true);
    }
}
