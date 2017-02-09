import java.util.HashMap;

/**
 * 包: PACKAGE_NAME
 * 源文件:TestMap.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2017年02月08日
 */
public class TestMap
{

    public static void main(String[] args)
    {
        HashMap<String,Object> map=new HashMap<>();
        map.put("key",30);
        map.put("key2",30);
        System.out.println(map.hashCode());
        System.out.println(map.hashCode());
        System.out.println(1 & (4-1));

    }
}
