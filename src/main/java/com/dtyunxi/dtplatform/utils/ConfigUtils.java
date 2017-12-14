package com.dtyunxi.dtplatform.utils;

import com.dtyunxi.dtplatform.model.Config;
import com.dtyunxi.dtplatform.model.Model;
import com.dtyunxi.dtplatform.simulator.UniversalDataSimulator;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.log4j.Logger;
import org.bson.Document;
import sun.rmi.runtime.Log;

import javax.jws.WebParam;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigUtils {

    public static Logger logger = Logger.getLogger(ConfigUtils.class);
    public static Config getConfig(String configFile){
        String json = JsonUtils.getJson(configFile);
        Document document=null;
        try {
            document=Document.parse(json);
        }catch (Exception e){
            logger.error("config file format is error.please check your config file!");
            System.exit(1);
        }
        Config config = new Config();
        String startTime = document.getString("startTime");
        String endTime = document.getString("endTime");
        config.setServer((Document)document.get("server"));

        List<Document> docModels = (List<Document>)document.get("models");
        List<Model> models = getModels(docModels);
        config.setStartTime(startTime);
        config.setEndTime(endTime);
        config.setModels(models);
        return config;
    }

    public static List<Model> getModels(List<Document> docModels){
        List<Model> models = new ArrayList<Model>();
        for (Document docModel : docModels) {
            Model model = new Model();
            String vmodel = docModel.getString("model");
            String threads = docModel.getString("threads");
            String total = docModel.getString("total");
            List<Document> DocExports = (List<Document>)docModel.get("exports");
            List<Map<String, String>> exports= new ArrayList<Map<String, String>>();
            for (Document docExport : DocExports) {
                Map<String, String> map = new HashMap<String, String>();
                String type = docExport.getString("type");
                if (type.equals("kafka")){
                    String topic = docExport.getString("topic");
                    map.put("type",type);
                    map.put("topic",topic);
                }

                if (type.equals("hdfs")){
                    String path = docExport.getString("path");
                    map.put("type",type);
                    map.put("path",path);
                }
                if (type.equals("local")){
                    String path = docExport.getString("path");
                    map.put("type",type);
                    map.put("path",path);
                }
                exports.add(map);
            }
            model.setExports(exports);
            model.setModel(vmodel);
            model.setThreads(threads);
            model.setTotal(total);
            models.add(model);
        }
        return models;
    }

}
