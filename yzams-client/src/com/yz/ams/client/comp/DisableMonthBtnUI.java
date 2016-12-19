/*
 * Copyright(c) 2007-2009 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at Oct 29, 2009 11:40:54 AM
 */
package com.yz.ams.client.comp;

import com.nazca.ui.GraphicsTool;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author liuyizhe
 */
public class DisableMonthBtnUI extends BasicButtonUI {

    private Color BgColor = new Color(0xE3EBF2);
    private Color textColor = new Color(0x9C9C9C);

    static {
        GraphicsTool.defaultAnimatorTimingSource();
    }

    public static ComponentUI createUI(JComponent c) {
        return new DisableMonthBtnUI();
    }

    static {
        GraphicsTool.defaultAnimatorTimingSource();
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension d = super.getPreferredSize(c);
        return d;
    }

    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        c.setBorder(null);
        c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void uninstallUI(JComponent c) {
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        JToggleButton b = (JToggleButton) c;
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints org = g2d.getRenderingHints();
        GraphicsTool.setQuanlifiedGraphics(g2d);
        int w = b.getWidth();
        int h = b.getHeight();
        //获得鼠标的状态
        String str = b.getText();
        g2d.setFont(g2d.getFont().deriveFont(12f));
        FontMetrics fm = g2d.getFontMetrics();
        int strHeight = fm.getHeight();
        int strLength = fm.stringWidth(str);
        //背景颜色
        g2d.setColor(BgColor);
        g2d.fillRoundRect((b.getWidth() - strLength - 10) / 2, (b.getHeight() - strHeight - 5) / 2, strLength + 10, strHeight + 5, 5, 5);
        //按钮文字
        g2d.setColor(textColor);
        int th = (int) fm.getAscent() - strHeight / 2;
        g2d.drawString(str, (int) (w - strLength) / 2, (int) h / 2 + th);
        g2d.setRenderingHints(org);
    }

    @Override
    protected void paintIcon(Graphics g, JComponent c, Rectangle iconRect) {
    }

    @Override
    protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
    }

    @Override
    protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
    }

}
