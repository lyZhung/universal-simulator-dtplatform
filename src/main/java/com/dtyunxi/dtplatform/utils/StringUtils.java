package com.dtyunxi.dtplatform.utils;

public class StringUtils {

    public static String getSubstring(String str){
        String substring = str.substring(str.indexOf("["), str.lastIndexOf("]") + 1);
        return substring;
    }

    public static String getType(Object o){ //获取变量类型方法
        String classes = o.getClass().toString();
        String type = classes.substring(classes.lastIndexOf(".")+1);
        return type; //使用int类型的getClass()方法
    }
}
