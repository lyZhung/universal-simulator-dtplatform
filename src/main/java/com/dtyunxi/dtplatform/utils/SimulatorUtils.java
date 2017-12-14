package com.dtyunxi.dtplatform.utils;

import com.dtyunxi.dtplatform.model.Config;
import com.dtyunxi.dtplatform.simulator.UniversalDataSimulator;
import kafka.javaapi.producer.Producer;
import org.apache.hadoop.fs.FileSystem;
import org.apache.log4j.Logger;
import org.bson.*;

public class SimulatorUtils {

    public static Long totalMessing=1L;
    public static Logger logger = Logger.getLogger(SimulatorUtils.class);

    public static void dataSimulator(Config config, UniversalDataSimulator universalDataSimulator){
        String[] messModels = config.getMessModels();
        String[] topics = config.getTopics();
        String[] localPaths = config.getLocalPaths();
        String[] fsPaths = config.getFsPaths();
        FileSystem fileSystem = config.getFileSystem();
        Producer<String, String> producer = config.getProducer();
        Long totalMess = Long.parseLong(config.getTotalMess().equals("")?String.valueOf(Long.MAX_VALUE):config.getTotalMess());
        while (true){
            for (int j = 0; j < messModels.length; j++) {
                String message=null;
                try{
                    Document messModelDoc = Document.parse(JsonUtils.getJson(messModels[j]));
                    Document result = JsonUtils.getJsonArrayByRegex(messModelDoc);
                    message=result.toJson();
                }catch (BsonInvalidOperationException e){
                    BsonArray result = new BsonArray();
                    BsonArray bsonArray= BsonArray.parse(JsonUtils.getJson(messModels[j]));
                    for (BsonValue bsonValue : bsonArray) {
                        String json = bsonValue.asDocument().toJson();
                        Document documentByRegex = JsonUtils.getJsonArrayByRegex(Document.parse(json));
                        result.add(BsonDocument.parse(documentByRegex.toJson()));
                    }
                    message=StringUtils.getSubstring(result.toString());
                }
                    if (!(topics.length==1&&topics[0].equals(""))){
                        /**
                         * 发送消息到kafka
                         */
                        try{
                            KafkaUtils.sendMess(topics[j],message,producer);
                        }catch (ArrayIndexOutOfBoundsException e){

                        }
                    }
                    /**
                     * 写数据到本地文件
                     */
                    if (!(localPaths.length==1&&localPaths[0].equals(""))){
                        try {
                            FileUtils.writeMess(message,localPaths[j]);
                        }catch (ArrayIndexOutOfBoundsException e){

                        }
                    }
                    /**
                     * 写数据到HDFS
                     */
                if (!(fsPaths.length==1&&fsPaths[0].equals(""))){
                    try {
                        HdfsUtils.writeMess(fileSystem,message,fsPaths[j]);
                    }catch (ArrayIndexOutOfBoundsException e){

                    }
                }
            }
            synchronized (universalDataSimulator){
                totalMessing++;
                if (totalMessing>totalMess){
                    logger.warn("data is put already,program is exit.pieces of "+ (SimulatorUtils.totalMessing-1)+" data is produced.");
                    System.exit(0);
                }
            }
        }
    }
}
