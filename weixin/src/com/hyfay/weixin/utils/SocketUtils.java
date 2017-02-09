package com.hyfay.weixin.utils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hyfay.weixin.Crawler.WeiXinCTH;

/**
 * 包: utils
 * 源文件:SocketUtils.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2017年02月06日
 */
public class SocketUtils implements Runnable
{
    @Override
    public void run()
    {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("服务器已启动...............");
            while (true) {
                Socket socket=null;
                socket = serverSocket.accept();
                System.out.println("客户端已连接IP地址为：" + socket.getInetAddress());

                InputStream inputStream = socket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String readContent;
                StringBuffer bufer=new StringBuffer();
                while ((readContent = bufferedReader.readLine()) != null) {
                    bufer.append(readContent+"\n");
                    if (readContent.length() == 0)
                        break;
                }
                System.out.println(bufer);
                String url=parseHtml(bufer.toString());
                String text=parseURL(url);
                //封装输出流
                OutputStream outputStream = socket.getOutputStream();
                try{
                    PrintStream stream=new PrintStream(outputStream);
                    stream.write(text.getBytes());
                    stream.close();
                }catch(Exception e){
                    e.printStackTrace();
                }

                socket.close();
                System.out.println("客户端连接已释放.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String parseHtml(String param){
        String clientUrl="";
        String [] parms=param.split("\n");
        if(parms!=null&&parms.length>0){
            String methodURL=parms[0];
            String[] params=methodURL.split("\\s");
            if(params!=null&&params.length>0){
                String url=params[1];
                clientUrl=url;
            }
        }
        return clientUrl;
    }

    private String parseURL(String url){
        String urlParam=url.substring(url.indexOf("?")+1);
        String [] params=urlParam.split("&");
        Map<String,String> map=new HashMap<String, String>();
        for (int i=0;i<params.length;i++){
            String[] t=params[i].split("=");
            String t1=params[i];
            String tp=t1.substring(0,t1.indexOf("="));
            String tv=t1.substring(t1.indexOf("=")+1);
            map.put(tp,tv);
        }
        if(map.get("type").equals("sysSet")){
            String uin=map.get("uin");
            try
            {
                if(uin!=null&&!uin.equals(""))
                {
                    PropertiesUtil.writerProperties(uin);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return e.getMessage();
            }
            return "success";
        }else if(map.get("type").equals("URLChange")){
            try
            {
                String filePath = map.get("file");
                String fileName = filePath
                        .substring(filePath.lastIndexOf("\\") + 1,
                                filePath.lastIndexOf("."));
                FileUtils fileUtils = new FileUtils();
                List<Map<String, String>> urlList = fileUtils
                        .readExcel(filePath);
                System.out.println(
                        "SocketUTils-firstRead count:" + urlList.size());
                List<Map<String, String>> newList = new WeiXinCTH(urlList)
                        .startChange();
                String newFilePath = fileUtils
                        .importExcel(fileName + "_new", newList);
                return newFilePath;
            }catch (Exception e){
                return e.getMessage();
            }
        }
        return "";
    }

}
