/*
 * TitlePanelUI.java
 * 
 * Copyright(c) 2007-2013 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2013-05-08 15:58:02
 */
package com.yz.ams.client;

import com.nazca.ui.GraphicsTool;
import com.yz.ams.client.util.ClientUtils;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.plaf.PanelUI;
import org.jdesktop.core.animation.timing.Animator;

/**
 *
 * @author Qiu Dongyue
 */
public class TitlePanelUI extends PanelUI {

    private static final int HEADER_BG_HEIGHT = 26;
    //private static final int HEADER_BG_HEIGHT = 13;
    private static final int LOGO_TOP_MARGIN = 5;
    private static final int TITLE_BG_HEIGHT = 42;
//    private static final int TITLE_BG_HEIGHT = 21;
    private static final Font TITLE_FONT = new Font("黑体", Font.BOLD, 17);
    private static final Color titleColor = Color.white;//decode("#eaeaea");
    private static final Color titleShadowColor = Color.decode("#4f5b67");
    private static BufferedImage HEADER_BG;
    private static BufferedImage SUB_HEADER_BG;
    private static final int BG_IMG_SUB_INSETS_RIGHT = 650;
    private static int SHOW_MAIN_HEIGHT = 0;
    private static int bgShowHeight = 0;
    private float lineAlpha = 0;
    private static final int TITLE_OFFSET = 20;
    private static final int LOGO_OFFSET = 20;
    private int curTitleOffset = TITLE_OFFSET;
    private int curLogoOffset = 0;
    private float logo_alpha = 0;
    private float title_alpha = 0;
    private static BufferedImage logo = null;
    private static BufferedImage btTiao = null;

    private static BufferedImage bgSpotImg = null;

    static {

        try {
            HEADER_BG = ClientUtils.buildBufferedImage("title-bg.png");
            btTiao = ClientUtils.buildBufferedImage("bt-side.png");
            logo = ClientUtils.buildBufferedImage("logo_small.png");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SUB_HEADER_BG = HEADER_BG.getSubimage(0, 0, BG_IMG_SUB_INSETS_RIGHT, HEADER_BG.getHeight());
        HEADER_BG = HEADER_BG.getSubimage(BG_IMG_SUB_INSETS_RIGHT, 0, HEADER_BG.getWidth() - BG_IMG_SUB_INSETS_RIGHT, HEADER_BG.
                getHeight());
        SHOW_MAIN_HEIGHT = HEADER_BG.getHeight();
        bgShowHeight = 0 - SHOW_MAIN_HEIGHT;
//        System.out.println(SHOW_MAIN_HEIGHT);
    }
    private String title = "";
    private Animator ani = null;
    private JComponent comp = null;
    private float offset = 0f;
    private float alpha = 0f;
    //private AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);

    public TitlePanelUI(String title) {
        this.title = title;
//        if (logo == null) {
//            try {
//                logo = ImageIO.read(getClass().getResourceAsStream("/com/nazca/inside/resources/main_logo.png"));
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
        //gotoMain();
    }

    public void setProjectName(String name) {
        //this.alpha = 0f;
        this.title = name;
        //ani.start();
    }

    @Override
    public void installUI(JComponent comp) {
        super.installUI(comp);
        this.comp = comp;
        comp.repaint();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints orgs = g2.getRenderingHints();
        GraphicsTool.setQuanlifiedGraphics(g2);
        Insets insets = c.getInsets();
        int width = c.getWidth() - insets.left - insets.right;
        int height = c.getHeight();
        g2.translate(insets.left, insets.top);
        g2.drawImage(HEADER_BG, 0, 0, width, HEADER_BG.getHeight(), c);
        drawSpotImage(g2, width, height);
        g2.drawImage(SUB_HEADER_BG, 0, 0, c);
        drawSpotImage(g2, width, height);
        //绘制下部条；
        g2.drawImage(btTiao, 0, height - btTiao.getHeight(), width, btTiao.getHeight(), c);
        //g2.drawImage(logo, 0, 0, c);
        //paintText(g2, c);
        g2.setColor(titleShadowColor);
        g2.drawLine(0, height - 1, width, height - 1);
        g2.translate(-insets.left, -insets.top);
        g2.setRenderingHints(orgs);
        g2.drawImage(logo, 14, 3, c);
        super.paint(g, c);
    }

    private void paintText(Graphics2D g2, JComponent c) {
        float orgAlpha = 1;
        if (g2.getComposite() instanceof AlphaComposite) {
            orgAlpha = ((AlphaComposite) g2.getComposite()).getAlpha();
        }
        Insets insets = c.getInsets();
        int width = c.getWidth() - insets.left - insets.right;
        g2.translate(insets.left, insets.top);
        g2.setColor(Color.LIGHT_GRAY);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, orgAlpha * lineAlpha));
        g2.drawLine(width - logo.getWidth(), LOGO_TOP_MARGIN + 3, width - logo.getWidth(), LOGO_TOP_MARGIN + 3 + 20);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, orgAlpha * logo_alpha));
        g2.drawImage(logo, width - logo.getWidth() + curLogoOffset - LOGO_OFFSET, LOGO_TOP_MARGIN, c);

//        
//        Paint oldPainter = g2.getPaint();
//        g2.setPaint(oldPainter);
        g2.setColor(titleColor);
        g2.setFont(TITLE_FONT);
        FontMetrics fm = g2.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        //g2.setColor(titleShadowColor);
//        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        //g2.drawString(title, width - titleWidth - TITLE_RIGHT_MARGIN -logo.getWidth(), LOGO_TOP_MARGIN +fm.getHeight()+ 2);
        g2.setColor(titleColor);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, orgAlpha * title_alpha));
        g2.drawString(title, width - titleWidth - logo.getWidth() - 7 - curTitleOffset + TITLE_OFFSET, LOGO_TOP_MARGIN + fm.
                getHeight());
    }

    private void drawSpotImage(Graphics2D g, int width, int height) {
        if (bgSpotImg == null || bgSpotImg.getWidth() != width || bgSpotImg.getHeight() != height) {
            bgSpotImg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics bgG = bgSpotImg.getGraphics();
            bgG.setColor(new Color(174, 182, 190));
            for (int x = 0; x < width; x += 4) {
                for (int y = 0; y < height; y += 4) {
                    bgG.drawLine(x, y, x, y);
                }
            }
        }
        g.drawImage(bgSpotImg, 0, 0, null);
    }
}
