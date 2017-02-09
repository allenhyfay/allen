package com.hyfay.swing.frame;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;

import javax.swing.*;

import com.hyfay.weixin.utils.SocketUtils;

import java.awt.*;

/**
 * 包: frame
 * 源文件:MainFrame.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2016年12月22日
 */
public class MainFrame extends JFrame implements Runnable
{

    private static final long serialVersionUID = -5988513125942516733L;
    private static MainFrame mainFrame;

    private static  String mainPath="";

    private Thread thread;

    public static MainFrame instance() {
        if (mainFrame == null)
            mainFrame = new MainFrame();
        return mainFrame;
    }

    public MainFrame(){
        setTitle("微信URL转换工具助手(v1.0)");
        mainFrame = this;
       // mainPath=this.getClass().getResource("staticFile/mainFrame.html").getPath();
        mainPath=System.getProperty("user.dir")+"/staticFile/mainFrame.html";
        setAlwaysOnTop(true);
        int x,y;
        Dimension size=Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        x=(size.width-700)/2;
        y=(size.height-650)/2;
        setSize(700,650);
        setLocation(x,y);
        setMinimumSize(new Dimension(300, 200));
        try{
           UIUtils.setPreferredLookAndFeel();
        }catch(Exception e){e.printStackTrace();}
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
               // mainFrame.add(WebPanel.instance(mainPath.substring(1)),BorderLayout.CENTER);
                mainFrame.add(WebPanel.instance(mainPath),BorderLayout.CENTER);
            }
        });
        NativeInterface.runEventPump();
    }

    public void open() {
        setVisible(true);
        if(LoadFrame.instance().isVisible())
        {
            LoadFrame.instance().dispose();
        }
        thread=new Thread(new SocketUtils());
        thread.start();
    }



    @Override
    public void run()
    {
        instance().open();
    }


}
