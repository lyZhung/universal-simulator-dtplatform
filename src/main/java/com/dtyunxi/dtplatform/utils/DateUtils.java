package com.dtyunxi.dtplatform.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

   public static SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   public static Date getDate(String time){
        Date date=null;
       try {
           date = sdf.parse(time);
       } catch (ParseException e) {
           e.printStackTrace();
       }
       return date;
   }
}
