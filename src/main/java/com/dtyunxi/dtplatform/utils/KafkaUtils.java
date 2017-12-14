package com.dtyunxi.dtplatform.utils;

import com.dtyunxi.dtplatform.model.Config;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class KafkaUtils {

    public static Producer<String, String> getProducer(Config config){
        Properties properties=new Properties();
        properties.setProperty("metadata.broker.list",config.getMetadata_broker_list());
        properties.setProperty("producer.type",config.getProducer_type());
        properties.setProperty("serializer.class",config.getSerializer_class());
        /**
         * 创建producer对象
         */
        Producer<String, String> producer= new Producer<String, String>(new ProducerConfig(properties));
        return producer;
    }

    public static void sendMess(String topic,String log,Producer producer){
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
