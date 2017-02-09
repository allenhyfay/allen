package com.hyfay.swing.frame;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;

import javax.swing.*;

import java.awt.*;


/**
 * 包: frame
 * 源文件:SetPanel.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2017年02月04日
 */
public class WebPanel extends JWebBrowser
{

    private static final long serialVersionUID = 1L;
    private static WebPanel setPanel;
    private String url;

    public static WebPanel instance(String url) {
        if (setPanel == null)
            setPanel = new WebPanel(url);
        return setPanel;
    }

    public WebPanel(String url)
    {
        this.url=url;
        final JWebBrowser webBrowser = new JWebBrowser();
        webBrowser.setBarsVisible(false);
        webBrowser.navigate(url);
        webBrowser.setBackground(Color.BLUE);
        webBrowser.addWebBrowserListener(new WebBrowserAdapter()
        {
            @Override
            public void locationChanging(WebBrowserNavigationEvent e)
            {
                super.locationChanging(e);
                System.out.println(e.toString());
            }

            @Override
            public void loadingProgressChanged(WebBrowserEvent e)
            {
                super.loadingProgressChanged(e);
                System.out.println("9090"+e.toString());
            }
        });
        add(webBrowser, BorderLayout.CENTER);
    }


}