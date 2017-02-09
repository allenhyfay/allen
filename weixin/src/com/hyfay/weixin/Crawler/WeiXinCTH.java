package com.hyfay.weixin.Crawler;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.util.StringUtils;



import com.hyfay.weixin.utils.PropertiesUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 包: Crawler
 * 源文件:WeiXinCTH.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2017年02月06日
 */
public class WeiXinCTH
{

    private List<Map<String, String>> oldUrlList;

    private static WeiXinCTH weiXinCTH;

    private static String uin = "";

    public WeiXinCTH(List<Map<String, String>> oldUrlList)
    {
        this.oldUrlList = oldUrlList;
    }


    public List<Map<String, String>> startChange()
    {
        List<Map<String, String>> newList = new ArrayList<Map<String, String>>();
        try
        {
            uin = PropertiesUtil.readerPropertis();
            String suff =
                    "&devicetype=Windows-QQBrowser&version=61030004&uin=" +
                            uin + "&ascene=1";
            System.out.println("secondReadCount:"+oldUrlList.size());
            if (oldUrlList != null && oldUrlList.size() > 0)
            {
                Map<String, String> newMap = null;
                for (Map<String, String> oldMap : oldUrlList)
                {
                    newMap = new HashMap<String, String>();
                    String oldURl = oldMap.get("url");
                    if (!StringUtils.isEmpty(oldURl))
                    {
                        newMap.put("oldUrl", oldURl);
                        try
                        {
                            String newURL = httpClientCrawler(oldURl, suff);
                            newMap.put("newUrl",newURL);
                        }catch (IOException e){
                            newMap.put("newUrl","");
                        }
                    }
                    newList.add(newMap);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return newList;
    }

    public String httpClientCrawler(String oldUrl, String suff)
            throws IOException
    {
        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod(oldUrl + suff);
        client.executeMethod(get);
        String newURL = get.getURI().getURI();
        System.out.println(newURL);
        return newURL;
    }


}
