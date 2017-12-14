package com.dtyunxi.dtplatform.utils;

import com.dtyunxi.dtplatform.model.Config;
import com.dtyunxi.dtplatform.simulator.UniversalDataSimulator;
import org.apache.log4j.Logger;
import org.bson.Document;
import sun.rmi.runtime.Log;

public class ConfigUtils {

    public static Logger logger = Logger.getLogger(ConfigUtils.class);
    public static Config getConfig(String configFile){

        String json = JsonUtils.getJson(configFile);

        Document document=null;
        try {
            document=Document.parse(json);
        }catch (Exception e){
            logger.error("config file format is error.");
            System.exit(1);
        }
        Config config = new Config();
        try {
            config.setMessModels((((Document) document.get("messModel")).getString("paths")).split(","));

            Document execute= (Document) document.get("execute");
            String startTime=execute.getString("startTime");
            String endTime=execute.getString("endTime");
            String threadNum=execute.getString("threadNum");
            String totalMess=execute.getString("totalMess");
            config.setStartTime(startTime);
            config.setEndTime(endTime);
            config.setThreadNum(threadNum);
            config.setTotalMess(totalMess);

            Document output = (Document)document.get("output");
            config.setMetadata_broker_list(((Document) output.get("kafka")).getString("metadata.broker.list"));
            config.setProducer_type(((Document) output.get("kafka")).getString("producer.type"));
            config.setSerializer_class(((Document) output.get("kafka")).getString("serializer.class"));
            config.setTopics(((Document) output.get("kafka")).getString("topics").split(","));
            config.setFsPaths(((Document) output.get("hdfs")).getString("paths").split(","));
            config.setUri(((Document) output.get("hdfs")).getString("uri"));
            config.setUser(((Document) output.get("hdfs")).getString("user"));


            config.setLocalPaths(((Document) output.get("localFile")).getString("paths").split(","));
        }catch (Exception e){
            logger.error("config file config error,please check");
        }
        return config;
    }

}
