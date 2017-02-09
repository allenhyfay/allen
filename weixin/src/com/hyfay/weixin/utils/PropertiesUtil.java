package com.hyfay.weixin.utils;


import java.io.*;
import java.util.Properties;
/**
 * 包: utils
 * 源文件:PropertiesUtil.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2017年02月06日
 */
public class PropertiesUtil
{

    private static final String configFile = "/staticFile/config.properties";

    private static Properties properties = null;

    public static String readerPropertis() throws IOException
    {
        String path=System.getProperty("user.dir")+configFile;
       // String path= PropertiesUtil.class.getResource("config.properties").getPath();
        properties = new Properties();
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        properties.load(inputStream);
        inputStream.close();
        return properties.getProperty("uin");
    }

    public static void writerProperties(String uin) throws IOException{
        String path=System.getProperty("user.dir")+configFile;
       // String path= PropertiesUtil.class.getResource("config.properties").getPath();
        properties = new Properties();
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        FileOutputStream outputStream = new FileOutputStream(file);
        properties.setProperty("uin",uin);
        properties.store(outputStream,"uin更新");
        outputStream.close();
        inputStream.close();
    }


    public static void main(String[] args)
    {
        PropertiesUtil util=new PropertiesUtil();
        try{
            //util.writerProperties("Mjk4MjU4MTgxNg%3D%3D");
            System.out.println(util.readerPropertis());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
