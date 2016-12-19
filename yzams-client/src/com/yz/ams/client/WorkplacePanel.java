/*
 * WorkplacePanel.java
 * 
 * Copyright(c) 2007-2014 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2014-03-21 12:31:25
 */
package com.yz.ams.client;

import com.nazca.ui.JLinkLabel;
import com.nazca.ui.NSlideMenuContainedPanel;
import com.nazca.ui.wpf.NClassicWorkplace;
import com.nazca.usm.model.USMSUser;
import com.yz.ams.client.agent.SyncUserOrgAgent;
import com.yz.ams.client.ui.AboutPanel;
import com.yz.ams.client.ui.statistics.AttendanceStatTabPanel;
import com.yz.ams.client.ui.AttendanceInfoMgmtTabPanel;
import com.yz.ams.client.ui.SystemMgmtTabPanel;
import com.yz.ams.client.util.ClientUtils;
import com.yz.ams.consts.Permissions;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author Qiu Dongyue
 */
public class WorkplacePanel extends JPanel {

    public static final int TITLE_HEIGHT = 66;
    public static final int HELP_PANEL_WIDTH = 200;
    private NClassicWorkplace workPlace = null;
    private BufferedImage bgImg = null;
    private ImageIcon titleImg = null;
    private JLinkLabel about = null;
    private JLinkLabel help = null;
    private JLinkLabel logout = null;
    private JLabel userinfo = new JLabel();
    private static final Color BG_G1 = Color.decode("#d1d1d1");
    private static final Color BG_G2 = Color.decode("#efefef");
    private SyncUserOrgAgent syncAgent = null;
    //关于面板
    private AboutPanel aboutPane = null;

    public WorkplacePanel() {
        syncAgent = new SyncUserOrgAgent();
        syncAgent.start();
        try {
            bgImg = ClientUtils.buildBufferedImage("title-bg.png");
            titleImg = ClientUtils.buildImageIcon("logo_small.png");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        setOpaque(false);
        workPlace = new NClassicWorkplace() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(BG_G2);
                g2.fillRect(0, 0, getWidth(), 70);

                GradientPaint gp = new GradientPaint(0, 0, BG_G1, 0, 33, BG_G2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), 70);
                TexturePaint tp = new TexturePaint(bgImg, new Rectangle(bgImg.getWidth(), bgImg.getHeight()));
                g2.setPaint(tp);
                g2.fillRect(0, 0, getWidth(), 70);
                Image img = titleImg.getImage();
                g.drawImage(img, 10, 10, titleImg.getIconWidth(), titleImg.getIconHeight(), titleImg.getImageObserver());
            }
            
        };
        userinfo.setForeground(Color.DARK_GRAY);
        USMSUser curUser = ClientContext.getUser();
        userinfo.setText(curUser.getName() + "，您好！");
        this.setLayout(new BorderLayout());
        this.add(workPlace, BorderLayout.CENTER);
        initLinks();
        initTabs();
        
    }

    private void initLinks() {
        final JLinkLabel setPwd = new JLinkLabel("修改密码");
        setPwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                new ChgUserInfoPanel().showMe(setPwd);
            }
        });
        setPwd.setIcon(ClientUtils.buildImageIcon("user-info-16.png"));
        JLinkLabel lock = new JLinkLabel("锁定");
        lock.setIcon(ClientUtils.buildImageIcon("lock-16.png"));
        lock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 PasswordCheckPanel pane = new PasswordCheckPanel();
                if (pane.showMeForLock(getRootPane())) {
                    Panel panel = new Panel();
                    panel.setLayout(new BorderLayout());
                    NSlideMenuContainedPanel p = new NSlideMenuContainedPanel();
                    panel.add(p, BorderLayout.CENTER);
                    validate();
                    repaint();
                }
//                PasswordCheckPanel pane = new PasswordCheckPanel();
//                NSlideMenuContainedPanel p = new NSlideMenuContainedPanel();
//                WorkplacePanel.this.removeAll();
//                WorkplacePanel.this.add(p, BorderLayout.CENTER);
//                validate();
//                repaint();
//                pane.showMeForLock(getRootPane());
//                WorkplacePanel.this.removeAll();
//                WorkplacePanel.this.add(titlePane, WorkplaceLayout.NORTH);
//                WorkplacePanel.this.add(mainContainer, WorkplaceLayout.CENTER);
            }
        });
        logout = new JLinkLabel("注销");
        logout.setIcon(ClientUtils.buildImageIcon("logout-16.png"));
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientContext.getMainFrame().gotoLogout();
            }
        });
//        help = new JLinkLabel();
//        help.setText("帮助");
//        help.setIcon(CommonResourceUtil.readIcon("help-16.png"));
//        help.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    OpenBrowser.openBrowser(WhtipsClientConfig.HELP_URL);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });
//        this.linkContainer.addLink(help);
        about = new JLinkLabel();
        about.setText("关于");
        about.setIcon(ClientUtils.buildImageIcon("about-16.png"));
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (aboutPane == null) {
                aboutPane = new AboutPanel();
                }
                aboutPane.showMe(about);
            }
        });
        workPlace.getLinkContainer().addLink(lock);
        workPlace.getLinkContainer().addLink(logout);
        workPlace.getLinkContainer().addLink(about);
    }

    private void initTabs() {
        if(ClientContext.hasPermission(Permissions.HR)){
            AttendanceStatTabPanel statPane = new AttendanceStatTabPanel();
            SystemMgmtTabPanel rePanel = new SystemMgmtTabPanel();
            AttendanceInfoMgmtTabPanel attendancePane = new AttendanceInfoMgmtTabPanel();
            workPlace.getTabContainer().addTabPanel(rePanel, false);
            workPlace.getTabContainer().addTabPanel(statPane, false);
            workPlace.getTabContainer().addTabPanel(attendancePane, true);
        }else{
            AttendanceStatTabPanel statPane = new AttendanceStatTabPanel();
            workPlace.getTabContainer().addTabPanel(statPane, false);
            workPlace.getTabContainer().activeTabPanelByIndex(0);
        }
    }
}

