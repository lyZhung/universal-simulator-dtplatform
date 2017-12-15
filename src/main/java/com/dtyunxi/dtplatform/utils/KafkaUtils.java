package com.dtyunxi.dtplatform.utils;

import com.dtyunxi.dtplatform.model.Config;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.hadoop.hdfs.DFSClient;
import org.bson.Document;

import java.nio.channels.ClosedChannelException;
import java.util.Properties;

public class KafkaUtils {

    public static Producer<String, String> getProducer(Config config){
        Document server = config.getServer();
        Document document = (Document) server.get("kafka");
        Properties properties=new Properties();
        properties.setProperty("metadata.broker.list",document.getString("metadata.broker.list"));
        properties.setProperty("producer.type",document.getString("producer.type"));
        properties.setProperty("serializer.class",document.getString("serializer.class"));
        /**
         * 创建producer对象
         */
        Producer<String, String> producer= new Producer<String, String>(new ProducerConfig(properties));
        return producer;
    }

    public static void sendMess(String topic, String log, Producer producer)throws InterruptedException{
        /**
         * 加载消息
         */
        //定义主题
        //为一个主题加载一个消息
        KeyedMessage<String, String> message = new KeyedMessage<String, String>(topic , log);
        //发送一个消息
        producer.send(message);
    }

}
