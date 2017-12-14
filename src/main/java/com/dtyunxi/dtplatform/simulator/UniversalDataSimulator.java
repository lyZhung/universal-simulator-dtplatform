package com.dtyunxi.dtplatform.simulator;

import com.dtyunxi.dtplatform.model.Config;
import com.dtyunxi.dtplatform.timer.EndTimer;
import com.dtyunxi.dtplatform.timer.StartTimer;
import com.dtyunxi.dtplatform.utils.*;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Timer;

/**
 * 入口类
 */
public class UniversalDataSimulator{

    public static Logger logger = Logger.getLogger(UniversalDataSimulator.class);

    public static void main(String[] args) {
        if (args.length<1){
            logger.error("Usage:config file");
            System.exit(1);
        }
        String configFile = args[0];
        Config config = ConfigUtils.getConfig(configFile);
        config.setProducer(KafkaUtils.getProducer(config));
        config.setFileSystem(HdfsUtils.getFileSystem(config));
        Date startTime = DateUtils.getDate(config.getStartTime());
        Date endTime = DateUtils.getDate(config.getEndTime());
        Timer timer = new Timer();
        timer.schedule(new StartTimer(config),startTime);
        timer.schedule(new EndTimer(),endTime);
    }
}
