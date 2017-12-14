package com.dtyunxi.dtplatform.utils;

import com.mifmif.common.regex.Generex;
import org.bson.Document;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class GenerexUtils {

    public static Object getRandom(Object object){
        /*String regexType = String.valueOf(object);
        String type = regexType.substring(regexType.lastIndexOf("_") + 1);
        String regex = regexType.substring(0, regexType.lastIndexOf("_"));
        String value=null;
        if (regex.equals("timestamp_s")){
            Long timestamp = new Date().getTime() / 1000;
            return timestamp.intValue();
        }else if (regex.equals("timestamp_ms")){
            Long timestamp = new Date().getTime();
            return timestamp.intValue();
        }else {
            Generex generex = new Generex(regex);
            value = generex.random();
        }

        if (type.equals("l")){
            Long longValue = Long.parseLong(value);
            return longValue.intValue();
        }else if (type.equals("d")){
            return Double.valueOf(value);
        }else {
            return String.valueOf(value);
        }*/


        String regexType = String.valueOf(object);
        Document document = Document.parse(regexType);
        String type = document.getString("type");
        String regex = document.getString("regex");

        String value=null;
        if (regex.equals("timestamp")){
            String unit = document.getString("unit");
            if (unit.equals("ms")){
                if (type.equals("long")){
                    return new Date().getTime() / 1000;
                }
                if (type.equals("string")){
                    return String.valueOf(new Date().getTime() / 1000);
                }
            }else {
                if (type.equals("long")){
                    return new Date().getTime();
                }
                if (type.equals("string")){
                    return String.valueOf(new Date().getTime());
                }
            }
        }else if (regex.equals("date")){
            String format = document.getString("format");
            String date = DateUtils.getDate(new Date(), format);
            return date;
        }else {
            Generex generex = new Generex(regex);
            value = generex.random();
        }
        if (type.equals("long")){
            Long longValue = Long.parseLong(value);
            return longValue.intValue();
        }else if (type.equals("double")){
            return Double.valueOf(value);
        }else {
            return String.valueOf(value);
        }
    }
}
