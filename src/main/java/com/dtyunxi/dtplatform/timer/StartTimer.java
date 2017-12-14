package com.dtyunxi.dtplatform.timer;

import com.dtyunxi.dtplatform.model.Config;
import com.dtyunxi.dtplatform.simulator.UniversalDataSimulator;
import com.dtyunxi.dtplatform.utils.HdfsUtils;
import com.dtyunxi.dtplatform.utils.KafkaUtils;
import com.dtyunxi.dtplatform.utils.SimulatorUtils;
import kafka.javaapi.producer.Producer;
import org.apache.hadoop.fs.FileSystem;
import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;
public class StartTimer extends TimerTask{
    private Config config;
    public static Logger logger = Logger.getLogger(StartTimer.class);

    public StartTimer(Config config) {
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
        FileSystem fileSystem=null;

        fileSystem = HdfsUtils.getFileSystem(config);

        config.setProducer(producer);
        config.setFileSystem(fileSystem);
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
