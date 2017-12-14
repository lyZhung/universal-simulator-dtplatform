package com.dtyunxi.dtplatform.utils;

import com.dtyunxi.dtplatform.model.Config;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Properties;

public class FileUtils {


    public static void writeMess(String log,String localFile){
        File file=null;
        FileWriter fileWriter=null;
        BufferedWriter bufferedWriter=null;
        try {
            file = new File(localFile);
            fileWriter=new FileWriter(file,true);
            bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.write(log);
            bufferedWriter.newLine();//换行
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
