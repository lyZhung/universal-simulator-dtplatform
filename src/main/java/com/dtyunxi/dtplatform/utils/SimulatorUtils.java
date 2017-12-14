package com.dtyunxi.dtplatform.utils;

import com.dtyunxi.dtplatform.model.Config;
import com.dtyunxi.dtplatform.model.Model;
import com.dtyunxi.dtplatform.simulator.UniversalDataSimulator;
import kafka.javaapi.producer.Producer;
import org.apache.hadoop.fs.FileSystem;
import org.apache.log4j.Logger;
import org.bson.*;
import scala.Int;

import java.util.List;
import java.util.Map;

public class SimulatorUtils {

    public  Long totalMessing=1L;
    public static Logger logger = Logger.getLogger(SimulatorUtils.class);

    public  void dataSimulator(final Config config, final Model model, final UniversalDataSimulator universalDataSimulator){
        final String vmodel = model.getModel();
        final Long total = Long.parseLong(model.getTotal().equals("")?String.valueOf(Long.MAX_VALUE):model.getTotal());
        final List<Map<String, String>> exports = model.getExports();
        final FileSystem fileSystem = config.getFileSystem();
        final Producer<String, String> producer = config.getProducer();
        Integer threads = Integer.valueOf(model.getThreads());
        ThreadGroup threadGroup = new ThreadGroup(model.getModel());

        for (int i = 0; i < threads; i++) {
           new Thread(threadGroup,new Runnable() {

                public void run() {

                    while (!Thread.interrupted()) {
                        String message = null;
                        try {
                            Document messModelDoc = Document.parse(JsonUtils.getJson(vmodel));
                            Document result = JsonUtils.getJsonArrayByRegex(messModelDoc);
                            message = result.toJson();
                        } catch (BsonInvalidOperationException e) {
                            BsonArray result = new BsonArray();
                            BsonArray bsonArray = BsonArray.parse(JsonUtils.getJson(vmodel));
                            for (BsonValue bsonValue : bsonArray) {
                                String json = bsonValue.asDocument().toJson();
                                Document documentByRegex = JsonUtils.getJsonArrayByRegex(Document.parse(json));
                                result.add(BsonDocument.parse(documentByRegex.toJson()));
                            }
                            message = StringUtils.getSubstring(result.toString());
                        }

                        for (Map<String, String> export : exports) {
                            String type = export.get("type");
                            if (type.equals("kafka")) {
                                String topic = export.get("topic");
                                if (!topic.equals("")) {
                                    KafkaUtils.sendMess(topic, message, producer);
                                }
                            }
                            if (type.equals("hdfs")) {
                                String path = export.get("path");
                                if (!path.equals("")) {
                                    HdfsUtils.writeMess(fileSystem, message, path);
                                }
                            }
                            if (type.equals("local")) {
                                String path = export.get("path");
                                if (!path.equals("")) {
                                    FileUtils.writeMess(message, path);
                                }
                            }
                        }
                        synchronized (universalDataSimulator) {
                            totalMessing++;
                            if (totalMessing > total) {
                                logger.warn("The model of ["+model.getModel().substring(model.getModel().lastIndexOf("\\")+1) + "],data is put already,program is exit.pieces of " + (totalMessing - 1) + " data is produced.");
                                Thread.currentThread().getThreadGroup().stop();
                                System.out.println("Thread.activeCount():"+Thread.activeCount());
                                if (Thread.activeCount()==1){
                                    logger.warn("All thread is killed");
                                    System.exit(0);
                                }
                            }
                        }
                    }

                }
            }).start();
        }
    }
}
