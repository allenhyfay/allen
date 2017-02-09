package com.hyfay.swing.frame;
import com.sun.awt.AWTUtilities;

import javax.swing.*;

import java.awt.*;


/**
 * 包: frame
 * 源文件:LoadFrame.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2016年12月22日
 */
public class LoadFrame extends JFrame implements Runnable
{

    private static final long serialVersionUID = -8236850107130802140L;
    private static LoadFrame loadingFrame;

    public static LoadFrame instance() {
        if (loadingFrame == null)
            loadingFrame = new LoadFrame();
        return loadingFrame;
    }

    public LoadFrame() {
        super("淘宝数据助手");
        setSize(260, 70);
        loadingFrame = this;
        setUndecorated(true);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        AWTUtilities.setWindowOpaque(this, false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println(this.getClass().getResource("imgs/hyfay.png"));
        setIconImage(new ImageIcon(this.getClass().getResource(
                "imgs/hyfay.png")).getImage());
        
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ImageIcon bg = new ImageIcon(this.getClass().getResource(
                "imgs/hyfay.png"));
        g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
    @Override
    public void run() {
        instance();
    }
 

 
}
