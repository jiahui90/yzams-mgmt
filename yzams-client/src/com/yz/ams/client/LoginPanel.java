package com.yz.ams.client;

/*
 * LoginPanel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-14 11:09:47
 */


import com.nazca.ui.*;
import com.nazca.usm.model.USMSUser;
import com.nazca.util.StringUtil;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.agent.LoginAgent;
import com.yz.ams.client.ui.AboutPanel;
import com.yz.ams.client.util.ClientUtils;
import com.yz.ams.util.VersionHelper;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;
import org.jdesktop.core.animation.timing.interpolators.AccelerationInterpolator;


/**
 *
 * @author Zhu Mengchao
 */
public class LoginPanel extends JPanel implements AgentListener<USMSUser>{

    private static final int waitingSec = 1;
    //透明度
    private float logoAlpha = 0;
    //登录最佳宽高
    private int loginCompPreferWidth;
    private int loginCompPreferHeight;
    //左侧背景图片
    private BufferedImage dbg = null;
    //登录代理
    private LoginAgent agent = null;
    //关于面板
    private AboutPanel aboutPane = null;

    /**
     * Creates new form LoginPanel
     */
    public LoginPanel() {
        initComponents();
        versionLb.setText("版本：" + VersionHelper.getTotalVersion());
        try {
            dbg = GraphicsTool.loadCompatibleImage(getClass().getResource("/com/yz/ams/client/res/bg.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //登录界面初始化时是否可见及透明度
        logincomp.setVisible(true);
        logincomp.setAlpha(0);
        remeberPwdCkBox.setEnabled(false);
        jProgressBar1.setIndeterminate(true);
        jProgressBar1.setVisible(false);
        this.initLoginComp();
        this.setOpaque(false);
//        logincompBg = ClientUtils.buildBufferedImage("Unknown-2.png");
        final Animator logincompAnim = new Animator.Builder()
                .setDuration(800, TimeUnit.MILLISECONDS)
                .setInterpolator(new AccelerationInterpolator(0.9f, 0.1f))
                .build();
        logincompAnim.addTarget(new TimingTargetAdapter() {
            @Override
            public void begin(Animator ani) {
                logincomp.setVisible(true);
            }

            @Override
            public void timingEvent(Animator ani, double x) {
                logincomp.setAlpha((float) x);
            }

            @Override
            public void end(Animator ani) {
                userNameTxFd.requestFocus();
                loginBtn.getRootPane().setDefaultButton(loginBtn);
            }
        });
//        logincompAnim.start();
        //延期waitingSec + 500后,透明度逐渐加强
        final Animator logoAnim = new Animator.Builder()
                .setDuration(800, TimeUnit.MILLISECONDS)
                .setStartDelay(waitingSec + 500, TimeUnit.MILLISECONDS)
                .build();

        logoAnim.addTarget(new TimingTargetAdapter() {
            @Override
            public void end(Animator ani) {
                logincompAnim.start();
            }

            @Override
            public void timingEvent(Animator ani, double x) {
//                logoAlpha = (float) x;
                repaint();
            }
        });
        logoAnim.start();
        loginCompPreferWidth = logincomp.getPreferredSize().width;
        loginCompPreferHeight = logincomp.getPreferredSize().height;
        setLayout(new LayoutManager() {

            @Override
            public void addLayoutComponent(String name, Component comp) {
            }

            @Override
            public void removeLayoutComponent(Component comp) {
            }

            @Override
            public Dimension preferredLayoutSize(Container parent) {
//                return new Dimension(0, 0);
                return null;
            }

            @Override
            public Dimension minimumLayoutSize(Container parent) {
//                return new Dimension(0, 0);
                return null;
            }

            @Override
            public void layoutContainer(Container parent) {
                synchronized (getTreeLock()) {
                    bg.setBounds(0, 0, getWidth() * 10/16, getHeight());
                    jPanel2.setBounds(getWidth() * 10/16, 0, getWidth() * 6/ 16 + 1, getHeight());
                }
            }
        });
        this.agent = new LoginAgent();
        this.agent.addListener(this);
    }

    private void initLoginComp() {
        this.logincomp.setOpaque(false);
    }
    //绘制左侧背景，用在bg面板property的code
    private void paintLeftBG(Graphics g) {
        if (dbg != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            GraphicsTool.setQuanlifiedGraphics(g2d);
            GraphicsTool.paintFillCanvasWithRatio(dbg, g2d, bg.getWidth(), bg.getHeight(), this); 
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

        bg = new JPanel(){
            public void paint(Graphics g){
                //super.paint(g);
                paintLeftBG(g);
                super.paint(g);
            }
        };
        nImagePanel1 = new com.nazca.ui.NImagePanel();
        testMarkLb = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        logincomp = new org.jdesktop.swingx.JXPanel();
        jLabel6 = new javax.swing.JLabel();
        antialiasedLabel1 = new com.nazca.ui.AntialiasedLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        userNameTxFd = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        passwordTxFd = new javax.swing.JPasswordField();
        remeberPwdCkBox = new javax.swing.JCheckBox();
        loginBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        remberUserNameCkBox = new javax.swing.JCheckBox();
        aboutLink = new com.nazca.ui.JLinkLabel();
        versionLb = new javax.swing.JLabel();
        jLinkLabel4 = new com.nazca.ui.JLinkLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setOpaque(false);
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bg.setOpaque(false);

        testMarkLb.setFont(testMarkLb.getFont().deriveFont((float)20));
        testMarkLb.setForeground(java.awt.Color.white);
        testMarkLb.setText("测试版");

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(nImagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(testMarkLb)))
                .addContainerGap(212, Short.MAX_VALUE))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(testMarkLb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nImagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 7, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 15));

        logincomp.setBackground(new java.awt.Color(255, 255, 255));
        logincomp.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 30, 0, 30));
        logincomp.setName("login"); // NOI18N

        jLabel6.setFont(jLabel6.getFont().deriveFont(jLabel6.getFont().getSize()+15f));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/logo_big.png"))); // NOI18N

        antialiasedLabel1.setForeground(new java.awt.Color(51, 51, 51));
        antialiasedLabel1.setText("系统用户登录");
        antialiasedLabel1.setFont(antialiasedLabel1.getFont().deriveFont(antialiasedLabel1.getFont().getStyle() | java.awt.Font.BOLD, antialiasedLabel1.getFont().getSize()+3));
        antialiasedLabel1.setMinimumSize(new java.awt.Dimension(0, 0));

        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("<html>请使用“考勤管理系统”的用户名和密码进行登录，有任何问题请及时与管理员联系。</html>");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(433, 136));
        jPanel1.setOpaque(false);

        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setText("用户名");

        userNameTxFd.setFont(userNameTxFd.getFont().deriveFont(userNameTxFd.getFont().getSize()+3f));

        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("密码");

        passwordTxFd.setFont(passwordTxFd.getFont().deriveFont(passwordTxFd.getFont().getSize()+3f));

        remeberPwdCkBox.setBackground(new java.awt.Color(255, 255, 255));
        remeberPwdCkBox.setForeground(new java.awt.Color(153, 153, 153));
        remeberPwdCkBox.setText("记住密码");
        remeberPwdCkBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remeberPwdCkBoxActionPerformed(evt);
            }
        });

        loginBtn.setText("登录");
        loginBtn.setActionCommand("登  录");
        loginBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 10, 3, 10));
        loginBtn.setPreferredSize(new java.awt.Dimension(35, 21));
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SimHei", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setToolTipText("");

        jProgressBar1.setPreferredSize(new java.awt.Dimension(146, 10));

        remberUserNameCkBox.setBackground(new java.awt.Color(255, 255, 255));
        remberUserNameCkBox.setForeground(new java.awt.Color(153, 153, 153));
        remberUserNameCkBox.setText("记住用户名");
        remberUserNameCkBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remberUserNameCkBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(userNameTxFd)
            .addComponent(passwordTxFd)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(remberUserNameCkBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(remeberPwdCkBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(userNameTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(remeberPwdCkBox)
                    .addComponent(remberUserNameCkBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {passwordTxFd, userNameTxFd});

        javax.swing.GroupLayout logincompLayout = new javax.swing.GroupLayout(logincomp);
        logincomp.setLayout(logincompLayout);
        logincompLayout.setHorizontalGroup(
            logincompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(antialiasedLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(logincompLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );
        logincompLayout.setVerticalGroup(
            logincompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logincompLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(antialiasedLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jLabel3))
        );

        aboutLink.setForeground(new java.awt.Color(255, 68, 0));
        aboutLink.setText("关于");
        aboutLink.setActiveColor(new java.awt.Color(255, 68, 0));
        aboutLink.setClickColor(new java.awt.Color(255, 68, 0));
        aboutLink.setName("about"); // NOI18N
        aboutLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutLinkActionPerformed(evt);
            }
        });

        versionLb.setForeground(new java.awt.Color(102, 102, 102));
        versionLb.setText("版本：");
        versionLb.setName("support"); // NOI18N

        jLinkLabel4.setText("系统设置");
        jLinkLabel4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLinkLabel4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logincomp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLinkLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(versionLb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(aboutLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logincomp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 162, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(versionLb)
                    .addComponent(aboutLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLinkLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 700));
    }// </editor-fold>//GEN-END:initComponents

    public String getPsd(char[] psd) {
        String strPsd = new String(psd);
        return strPsd;
    }

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        if (isCorrected()) {
            jLabel5.setText("");
            loginStart();
        }
    }//GEN-LAST:event_loginBtnActionPerformed

    private void aboutLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutLinkActionPerformed
        if (aboutPane == null) {
            aboutPane = new AboutPanel();
        }
        aboutPane.showMe(this);
    }//GEN-LAST:event_aboutLinkActionPerformed

    private void remberUserNameCkBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remberUserNameCkBoxActionPerformed
        if (remberUserNameCkBox.isSelected()) {
            remeberPwdCkBox.setEnabled(true);
        } else {
            remeberPwdCkBox.setSelected(false);
            remeberPwdCkBox.setEnabled(false);
        }
    }//GEN-LAST:event_remberUserNameCkBoxActionPerformed

    private void jLinkLabel4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLinkLabel4ActionPerformed
        ServerSelectionPanel serverSelectionPane = new ServerSelectionPanel();
        NInternalDiag nd = new NInternalDiag("系统设置",ClientUtils.buildImageIcon("sys-setting.png"), serverSelectionPane);
        nd.setResizable(true);
        nd.setCloseButtonVisible(true);
        nd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        nd.showInternalDiag(this);
    }//GEN-LAST:event_jLinkLabel4ActionPerformed

    private void remeberPwdCkBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remeberPwdCkBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_remeberPwdCkBoxActionPerformed

    private void loginStart() {
        String psd = getPsd(passwordTxFd.getPassword());
        String userName = userNameTxFd.getText().trim();
        logincomp.setPreferredSize(new Dimension(loginCompPreferWidth, loginCompPreferHeight));
        logincomp.repaint();
        LoginPanel.this.repaint();
        loginBtn.setEnabled(false);
//        resetBtn.setEnabled(false);
        setCompEnable(false);
        agent.setLoginName(userName);
        agent.setPassword(psd);
        agent.start();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.nazca.ui.JLinkLabel aboutLink;
    private com.nazca.ui.AntialiasedLabel antialiasedLabel1;
    private javax.swing.JPanel bg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private com.nazca.ui.JLinkLabel jLinkLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JButton loginBtn;
    private org.jdesktop.swingx.JXPanel logincomp;
    private com.nazca.ui.NImagePanel nImagePanel1;
    private javax.swing.JPasswordField passwordTxFd;
    private javax.swing.JCheckBox remberUserNameCkBox;
    private javax.swing.JCheckBox remeberPwdCkBox;
    private javax.swing.JLabel testMarkLb;
    private javax.swing.JTextField userNameTxFd;
    private javax.swing.JLabel versionLb;
    // End of variables declaration//GEN-END:variables

    private void setCompEnable(boolean flag) {
        userNameTxFd.setEnabled(flag);
        passwordTxFd.setEnabled(flag);
        remberUserNameCkBox.setEnabled(flag);
        remeberPwdCkBox.setEnabled(flag);
    }

    public boolean isCorrected() {
        boolean flag = true;
        String psw = getPsd(passwordTxFd.getPassword());
        if (psw == null || psw.isEmpty()) {
            NLabelMessageTool.errorMessage(jLabel5, "密码不能为空");
            jLabel5.setVisible(true);
            flag = false;
        }
        String userName = userNameTxFd.getText().trim();
        if (userName == null || userName.isEmpty()) {
            NLabelMessageTool.errorMessage(jLabel5, "用户名不能为空");
            jLabel5.setVisible(true);
            flag = false;
        }
        return flag;
    }

    @Override
    public void onStarted(long seq) {
        jProgressBar1.setIndeterminate(true);
        this.jProgressBar1.setVisible(true);
        loginBtn.setEnabled(false);
        NLabelMessageTool.infoMessage(jLabel5, "登录中...");
    }

    @Override
    public void onSucceeded(final USMSUser user, long seq) {
        saveUserAndPwd();
        new Thread() {
            @Override
            public void run() {
                try {
                    ClientContext.getMainFrame().gotoWorkplace();
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                jProgressBar1.setVisible(false);
                jProgressBar1.setIndeterminate(false);
                jLabel5.setText("");
                loginBtn.setEnabled(true);
//                resetBtn.setEnabled(true);
                setCompEnable(true);
                loginBtn.setEnabled(true);
                NLabelMessageTool.emptyMessage(jLabel5);
            }
        }.start();
    }

    @Override
    public void onFailed(String msg, int code, long seq) {
        loginBtn.setEnabled(true);
        jProgressBar1.setVisible(false);
        jProgressBar1.setIndeterminate(false);
        NLabelMessageTool.errorMessage(jLabel5, code, msg);
        this.userNameTxFd.setEnabled(true);
        this.passwordTxFd.setEnabled(true);
        this.remberUserNameCkBox.setEnabled(true);
        this.remeberPwdCkBox.setEnabled(true);
        this.checkAutoLoginBtn();
//        this.resetBtn.setEnabled(true);
        this.loginBtn.setEnabled(true);
    }

     private void checkAutoLoginBtn() {
        if (this.remberUserNameCkBox.isSelected()) {
            this.remeberPwdCkBox.setEnabled(true);
        } else {
            this.remeberPwdCkBox.setEnabled(false);
        }
    }
     
     public void setUserAndPwd() {
        if (this.remberUserNameCkBox.isSelected()) {
            String name = ClientConfig.getUserId();
            this.userNameTxFd.setText(name);
        } else {
            this.userNameTxFd.setText("");
        }
        if (this.remeberPwdCkBox.isSelected()) {
            String pwd = ClientConfig.getPassword();
            this.passwordTxFd.setText(pwd);
        } else {
            this.passwordTxFd.setText("");
        }
    }

    public void loadUserAndPwd() {
        String name = ClientConfig.getUserId();
        String pwd = ClientConfig.getPassword();
        if (!StringUtil.isEmpty(name)) {
            this.userNameTxFd.setText(name);
            this.remberUserNameCkBox.setSelected(true);
        }
        if (!StringUtil.isEmpty(pwd)) {
            this.passwordTxFd.setText(pwd);
            this.remeberPwdCkBox.setSelected(true);
        }
        checkAutoLoginBtn();
    }
    
    private void saveUserAndPwd() {
        String userId = userNameTxFd.getText().trim();
        String password = new String(passwordTxFd.getPassword());
        if (remeberPwdCkBox.isSelected()) {
            ClientConfig.saveUserIdAndPassword(userId, password);
        } else if (remberUserNameCkBox.isSelected()) {
            ClientConfig.saveUserId(userId);
        } else {
            ClientConfig.removeUserIdAndPassword();
        }
        ClientContext.setPassword(password);
    }

    public void setEmptyMsgLb() {
        NLabelMessageTool.emptyMessage(jLabel5);
    }
    
}
