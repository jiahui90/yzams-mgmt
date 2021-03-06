/*
 * HolidayMgmtPanel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-07 08:43:24
 */
package com.yz.ams.client.ui.holiday;

import com.nazca.ui.laf.NazcaLAFTool;
import java.awt.BorderLayout;
import javafx.application.Platform;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class HolidayMgmtPanel extends javax.swing.JPanel {
    private MonthPanel pane = null;
    /**
     * Creates new form HolidayMgmtPanel
     */
    public HolidayMgmtPanel() {
        initComponents();
        //calendarFx license必须放在含有calendarfx组件的最顶层面板
        //CalendarFX.setLicenseKey("LIC=CO_24;VEN=ComponentSource;VER=STANDARD;PRO=1;RUN=no;CTR=0;SignCode=3F;Signature=302D02147D42F7F46938EF031E080B9F4AB3DE76FE709213021500913D47CB73AE23411EC840E00FDD2B54B6868DAF");
        pane = new MonthPanel();
        this.add(pane, BorderLayout.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
     public void init() {
        NazcaLAFTool.applyNazcaLAF();
        Platform.setImplicitExit(false);
    }
}
