package com.dtyunxi.dtplatform.utils;

import com.mifmif.common.regex.Generex;
import com.sun.tools.javah.Gen;
import org.bson.Document;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class JsonUtils {

    public static String getJson(String path){
        StringBuffer json = new StringBuffer();
        File file=new File(path);// 打开文件
        BufferedReader reader=null;
        try{
            FileInputStream in = new FileInputStream(file);
            reader=new BufferedReader(new InputStreamReader(in,"UTF-8"));// 读取文件
            String line=null;
            while((line=reader.readLine())!=null){
               json.append(line);
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader!=null){
                try{
                    reader.close();
                }catch(IOException el){
                }
            }
        }
        return json.toString();
    }

   /* *//**
     * 这种情况没有考虑数组的情形
     * @param document
     * @return
     *//*
    public static Document getJsonByRegex(Document document){
        Document result = new Document();
        Set<Map.Entry<String, Object>> entries = document.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> keyValue = iterator.next();
            String key = keyValue.getKey();
            if (document.get(key) instanceof Document){
                result.append(key,getJsonByRegex((Document)document.get(key)));
            }else {
                if (keyValue.getValue() instanceof List){
                    List<String> regexs = new ArrayList<String>();
                    List<String> regexs_tmp =(List<String>)keyValue.getValue();
                    for (String regex_tmp : regexs_tmp) {
                        Generex generex = new Generex(regex_tmp);
                        String value = generex.random();
                        regexs.add(value);
                    }
                    result.append(key,regexs);
                }else{
                    String regex = String.valueOf(keyValue.getValue());
                    Generex generex = new Generex(regex);
                    String value = generex.random();
                    result.append(key,value);
                }
            }
        }
        return result;
    }
*/
    /**
     *考虑到document下又有数组，数组下有多个document的情形-解析接近完成
     * @param document
     * @return
     */
    public static Document getJsonByRegex(Document document){
        Document result = new Document();
        Set<Map.Entry<String, Object>> entries = document.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> keyValue = iterator.next();
            String key = keyValue.getKey();
            if (document.get(key) instanceof Document){
                result.append(key,getJsonByRegex((Document)document.get(key)));
            }else {
                if (keyValue.getValue() instanceof List){
                    List regexs = new ArrayList();
                    List regexs_tmp =(List)keyValue.getValue();
                    for (Object regex_tmp : regexs_tmp) {
                        if (regex_tmp instanceof Document){
                            regexs.add(getJsonByRegex((Document) regex_tmp));
                        }
                        if (regex_tmp instanceof String){
                            Generex generex = new Generex(String.valueOf(regex_tmp));
                            String value = generex.random();
                            regexs.add(value);
                        }
                    }
                    result.append(key,regexs);
                }else{
                    String regex = String.valueOf(keyValue.getValue());
                    Generex generex = new Generex(regex);
                    String value = generex.random();
                    result.append(key,value);
                }
            }
        }
        return result;
    }

 /*   *//**
     * 针对开始多个document的情形
     * @param document
     * @return
     *//*
    public static Document getJsonArrayDocumentByRegex(Document document){
        Document result = new Document();
        Set<Map.Entry<String, Object>> entries = document.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> keyValue = iterator.next();
            String key = keyValue.getKey();
            if (document.get(key) instanceof Document){
                result.append(key,getJsonArrayByRegex((Document)document.get(key)));
            }else {
                if (keyValue.getValue() instanceof List){
                    List regexs = new ArrayList();
                    List regexs_tmp =(List)keyValue.getValue();
                    for (Object regex_tmp : regexs_tmp) {
                        if (regex_tmp instanceof Document){
                            regexs.add(getJsonArrayByRegex((Document) regex_tmp));
                        }

                        if (regex_tmp instanceof String){
                            Generex generex = new Generex(String.valueOf(regex_tmp));
                            String value = generex.random();
                            regexs.add(value);
                        }
                    }
                    result.append(key,regexs);
                }else{
                    String regex = String.valueOf(keyValue.getValue());
                    Generex generex = new Generex(regex);
                    String value = generex.random();
                    result.append(key,value);
                }
            }
        }
        return result;
    }*/

    public static Document getJsonArrayByRegex(Document document){
        Document result = new Document();
        Set<Map.Entry<String, Object>> entries = document.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> keyValue = iterator.next();
            String key = keyValue.getKey();
            if (document.get(key) instanceof Document){
                result.append(key,getJsonArrayByRegex((Document)document.get(key)));
            }else {
                if (keyValue.getValue() instanceof List){
                    List regexs = new ArrayList();
                    List regexs_tmp =(List)keyValue.getValue();
                    for (Object regex_tmp : regexs_tmp) {
                        if (regex_tmp instanceof Document){
                            regexs.add(getJsonArrayByRegex((Document) regex_tmp));
                        }else if (regex_tmp instanceof List){
                            List arrayList = new ArrayList();
                            for (Object o : ((List) regex_tmp)) {
                                Object value = GenerexUtils.getRandom(o);
                                arrayList.add(value);
                            }
                            regexs.add(arrayList);
                        }else {
                            Object value = GenerexUtils.getRandom(regex_tmp);
                            regexs.add(value);
                        }
                    }
                    result.append(key,regexs);
                }else{
                    Object value = GenerexUtils.getRandom(keyValue.getValue());
                    result.append(key,value);
                }
            }
        }
        return result;
    }
}
