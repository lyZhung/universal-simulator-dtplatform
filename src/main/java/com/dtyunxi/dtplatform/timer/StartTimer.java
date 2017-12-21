package com.dtyunxi.dtplatform.timer;

import com.dtyunxi.dtplatform.model.Config;
import com.dtyunxi.dtplatform.model.Model;
import com.dtyunxi.dtplatform.simulator.UniversalDataSimulator;
import com.dtyunxi.dtplatform.utils.ConfigUtils;
import com.dtyunxi.dtplatform.utils.HdfsUtils;
import com.dtyunxi.dtplatform.utils.KafkaUtils;
import com.dtyunxi.dtplatform.utils.SimulatorUtils;
import kafka.javaapi.producer.Producer;
import org.apache.hadoop.fs.FileSystem;
import org.apache.log4j.Logger;
import scala.Int;

import java.util.List;
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

        List<Model> models = config.getModels();
        for (final Model model : models) {
           new SimulatorUtils().dataSimulator(config, model);
        }
    }
}
