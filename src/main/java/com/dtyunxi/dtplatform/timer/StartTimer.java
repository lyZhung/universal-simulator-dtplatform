package com.dtyunxi.dtplatform.utils;

import com.dtyunxi.dtplatform.model.Config;
import com.dtyunxi.dtplatform.simulator.UniversalDataSimulator;
import kafka.javaapi.producer.Producer;
import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class TimerUtils extends TimerTask{
    private Config config;
    public static Logger logger = Logger.getLogger(TimerTask.class);


    public TimerUtils(Config config) {
        this.config = config;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public void run() {
        startSimulator();
    }

    public void startSimulator(){

        final UniversalDataSimulator universalDataSimulator = new UniversalDataSimulator();
        final String[] messModels = config.getMessModels();

        if (messModels.length<1||(messModels.length==1&&messModels[0].equals(""))){
            logger.error("message's model can't is empty,program is exit");
            System.exit(1);
        }

        Producer<String, String> producer = KafkaUtils.getProducer(config);
        config.setProducer(producer);
        Integer threadNum = Integer.parseInt(config.getThreadNum().equals("")?"5":config.getThreadNum());


        for (int i = 0; i < threadNum; i++) {
            new Thread(new Runnable() {
                public void run() {
                    SimulatorUtils.dataSimulator(config,universalDataSimulator);
                }
            }).start();
        }
    }
}
