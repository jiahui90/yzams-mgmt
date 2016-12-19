/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yz.ams.client.comp;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicToolBarUI;

/**
 *
 * @author liumeng
 */
public class StateToolbarUI extends BasicToolBarUI {

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g;
        int w = c.getWidth();
        int h = c.getHeight();
        g2.setPaint(new GradientPaint(0, 0, new Color(215, 224, 231), 0, h, new Color(197, 208, 217)));
        g.fillRect(0, 0, w, h);
        super.paint(g, c);
    }

}
