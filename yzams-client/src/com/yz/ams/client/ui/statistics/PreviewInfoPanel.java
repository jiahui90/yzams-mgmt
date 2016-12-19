/*
 * PreviewInfoPane.java
 * 
 * Copyright(c) 2007-2015 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2015-11-23 17:32:15
 */
package com.yz.ams.client.ui.statistics;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.swing.JRViewerToolbar;
import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.save.JRDocxSaveContributor;
import net.sf.jasperreports.view.save.JRHtmlSaveContributor;
import net.sf.jasperreports.view.save.JROdtSaveContributor;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;

/**
 *
 * @author Zhu Mengchao
 */
public class PreviewInfoPanel extends javax.swing.JPanel {
    /**
     * Creates new form PreviewInfoPane
     */
    public PreviewInfoPanel() {
        initComponents();
    }
    
    public void previewListInfo(Collection collection, Map<String, Object> map, String formType) {
        try {
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(collection);
            JasperPrint jasperPrint = JasperFillManager.fillReport(getClass().getResourceAsStream(formType), map,
                    source);
            JRViewer viewer = new JRViewer(jasperPrint, null);
            JRViewerToolbar bar = (JRViewerToolbar) viewer.getComponent(0);
            JRSaveContributor[] contributor = new JRSaveContributor[4];
            for (JRSaveContributor jrsc : bar.getSaveContributors()) {
               if (jrsc instanceof JRDocxSaveContributor) {
                    contributor[0] = jrsc;
                } else if (jrsc instanceof JRPdfSaveContributor) {
                    contributor[1] = jrsc;
                } else if (jrsc instanceof JRHtmlSaveContributor) {
                    contributor[2] = jrsc;
                } else if (jrsc instanceof JROdtSaveContributor) {
                    contributor[3] = jrsc;
                }
            }
            bar.setSaveContributors(contributor);

            for (int i = 1; i < bar.getComponentCount(); i++) {
                Component c = bar.getComponent(i);
                switch (i) {
                    case 0:
                        JButton btn0 = (JButton) c;
                        btn0.setMaximumSize(new Dimension(60, 23));
                        btn0.setPreferredSize(new Dimension(60, 23));
                        btn0.setText("保存");
                        btn0.setToolTipText(null);
                        break;
                    case 1:
                        JButton btn1 = (JButton) c;
                        btn1.setMaximumSize(new Dimension(60, 23));
                        btn1.setPreferredSize(new Dimension(60, 23));
                        btn1.setText("打印");
                        btn1.setToolTipText(null);
                        break;
//                    case 2:
//                        JButton btn2 = (JButton) c;
//                        btn2.setMaximumSize(new Dimension(80, 23));
//                        btn2.setPreferredSize(new Dimension(80, 23));
//                        btn2.setText("重新载入");
//                        btn2.setToolTipText(null);
//                        break;
                    case 4:
                        JButton btn4 = (JButton) c;
                        btn4.setToolTipText("首页");
                        break;
                    case 5:
                        JButton btn5 = (JButton) c;
                        btn5.setToolTipText("上一页");
                        break;
                    case 6:
                        JButton btn6 = (JButton) c;
                        btn6.setToolTipText("下一页");
                        break;
                    case 7:
                        JButton btn7 = (JButton) c;
                        btn7.setToolTipText("尾页");
                        break;
                    case 10:
                        JToggleButton btn10 = (JToggleButton) c;
                        btn10.setToolTipText("实际尺寸");
                        break;
                    case 11:
                        JToggleButton btn11 = (JToggleButton) c;
                        btn11.setToolTipText("最适高度尺寸");
                        break;
                    case 12:
                        JToggleButton btn12 = (JToggleButton) c;
                        btn12.setToolTipText("最适宽度尺寸");
                        break;
                    case 14:
                        JButton btn14 = (JButton) c;
                        btn14.setMaximumSize(new Dimension(60, 23));
                        btn14.setPreferredSize(new Dimension(60, 23));
                        btn14.setText("放大");
                        btn14.setToolTipText(null);
                        break;
                    case 15:
                        JButton btn15 = (JButton) c;
                        btn15.setMaximumSize(new Dimension(60, 23));
                        btn15.setPreferredSize(new Dimension(60, 23));
                        btn15.setText("缩小");
                        btn15.setToolTipText(null);
                        break;
                    case 16:
                        JComboBox cbox = (JComboBox) c;
                        cbox.setToolTipText("显示比例");
                        break;
                    default:
                        break;
                }
            }
            this.add(viewer, BorderLayout.CENTER);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
