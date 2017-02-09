package com.hyfay.weixin.utils;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 包: utils
 * 源文件:FileUtils.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2016年12月21日
 */
public class FileUtils
{

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private final static String database = "/src/main/database/data.json";

    private final static String database_keys = "/src/main/database/keys.json";

    private String checkFiles()
    {
        String relativelyPath = System.getProperty("user.dir");
        File file = new File(
                relativelyPath + "/data/" + format.format(new Date()));
        if (!file.exists())
        {
            file.mkdirs();
        }
        return file.getPath();
    }

    public File createFile(String fileName)
    {
        try
        {
            String path = checkFiles();
            File file = new File(path + "/" + fileName + ".xls");
            if (!file.exists())
            {
                file.createNewFile();
            }
            return file;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 添加数据到excel
     */
    public synchronized String importExcel(String name,
            List<Map<String,String>> pos)
    {

        try
        {
            File excelFile = createFile(name);
            WritableWorkbook workbook = Workbook.createWorkbook(
                    excelFile);
            WritableSheet sheet = null;
            try
            {
                sheet = workbook.getSheet(0);
            }
            catch (Exception e)
            {
                sheet = workbook.createSheet(name, 0);
            }
            int col = 0;
            int row = sheet.getRows();
            System.out.println(col);
            if (row == 0)
            {
                sheet.addCell(createCell(col++, 0, "原路径"));
                sheet.addCell(createCell(col++, 0, "永久路径"));
            }
            col = 0;
            row += 1;
            if (pos != null && pos.size() > 0)
            {
                for (Map<String,String> proc : pos)
                {
                    sheet.addCell(createCell(col++, row, proc.get("oldUrl")));
                    sheet.addCell(createCell(col++, row, proc.get("newUrl")));
                    row++;
                    col = 0;
                }
            }
            workbook.write();
            workbook.close();
            System.out.println(excelFile.getAbsolutePath());
            return "success<br/>Processed file path：<br/>&nbsp;&nbsp;&nbsp;&nbsp;"+excelFile.getAbsolutePath();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "error:"+e.getMessage();
        }
    }
    private static Label createCell(int c, int r, String content)
    {
        Label l = new Label(c, r, content);

        try
        {
            l.setCellFormat(getWritableCellFormat());
        }
        catch (Exception e)
        {
        }

        return l;
    }

    private static WritableCellFormat getWritableCellFormat() throws Exception
    {
        WritableCellFormat wcfFC = new WritableCellFormat();
        wcfFC.setAlignment(Alignment.CENTRE);
        wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN);// 设置边框的颜色和样式
        WritableFont font1 = new WritableFont(WritableFont.createFont("宋体"), 11,
                WritableFont.NO_BOLD);
        wcfFC.setFont(font1);
        return wcfFC;
    }

    /**
     * 读取数据表中所有的值
     *
     * @param flag keys 导出到keys数据 ，否则是data
     */
    public String readJson(String flag)
    {
        Scanner scanner = null;
        try
        {
            String basePath = System.getProperty("user.dir") + database;
            if (flag.equalsIgnoreCase("keys"))
            {
                basePath = System.getProperty("user.dir") + database_keys;
            }
            File file = new File(basePath);

            StringBuilder buffer = new StringBuilder();
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine())
            {
                buffer.append(scanner.nextLine());
            }
            return buffer.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (scanner != null)
            {
                scanner.close();
            }
        }
    }

 /*   *//**
     * @param flag keys
     * @return
     *//*
    public JSONArray readByJSONArray(String flag)
    {
        String text = readJson(flag);
        JSONArray array = null;
        try
        {
            array = JSONArray.fromObject("[" + text + "]");
            return array;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return array;
    }*/

   /* *//**
 * 保存到data
 * @param list
 * @return
 *//*
    public int saveJson(List<NewProcPO> list){
        FileWriter writer=null;
        Long dateTime=new Date().getTime();
        try{
            String basePath = System.getProperty("user.dir") + database;
            File file = new File(basePath);
            if(!file.exists()){
                file.createNewFile();
            }
            if(list!=null&&list.size()>0){
                writer=new FileWriter(file,true);
                for (NewProcPO po:list){
                    po.setCreateDate(dateTime+"");
                    writer.write(po.toString()+",");
                }
                writer.flush();
            }
            return list.size();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }finally
        {
            if(writer!=null){
                try
                {
                    writer.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }*/

    /**
     * 保存到keys
     *
     * @param list
     * @return
     */
    public int saveKeys(List<Map<String, Object>> list, boolean append)
    {
        FileWriter writer = null;
        Long dateTime = new Date().getTime();
        try
        {
            String basePath = System.getProperty("user.dir") + database_keys;
            File file = new File(basePath);
            if (!file.exists())
            {
                file.createNewFile();
            }
            if (list != null && list.size() > 0)
            {
                writer = new FileWriter(file, append);
                for (Map keys : list)
                {
                    Iterator<String> iterator = keys.keySet().iterator();
                    String s = "{";
                    while (iterator.hasNext())
                    {
                        String key = iterator.next();
                        s += "\"" + key + "\":\"" + keys.get(key) + "\",";
                    }
                    s = s.substring(0, s.length() - 1);
                    s += "},";
                    writer.write(s);
                }
                writer.flush();
            }
            return list.size();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
        finally
        {
            if (writer != null)
            {
                try
                {
                    writer.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取excel文件
     *
     * @param filePath
     */
    public List<Map<String, String>> readExcel(String filePath)
    {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try
        {
            File file = new File(filePath);
            if (file.exists())
            {
                Workbook workbook = Workbook
                        .getWorkbook(new FileInputStream(file));
                Sheet sheet = workbook.getSheet(0);
                if (sheet != null)
                {
                    int rows = sheet.getRows();
                    int col = 0;
                    for (int i = 1; i < rows; i++)
                    {
                        String text = sheet.getCell(col, i).getContents();
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("url", text);
                        list.add(map);
                    }
                }
                workbook.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

}
