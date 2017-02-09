package com.hyfay.swing.frame;


import javax.swing.*;

import java.awt.*;
import java.util.Enumeration;
/**
 * 包: Crawler
 * 源文件:Main.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2016年12月22日
 */
public class Main
{

    public static void main(String[] args)
    {
        Thread thread=new Thread(new LoadFrame());
        thread.start();
        // 设置默认字体
        Font font =new Font("微软雅黑",Font.PLAIN, 14);
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, font);
        }

        try {
            Thread.sleep(600);
            UIManager
                    .setLookAndFeel(
                    "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
        }
        new Thread(MainFrame.instance()).start();
    }
}
