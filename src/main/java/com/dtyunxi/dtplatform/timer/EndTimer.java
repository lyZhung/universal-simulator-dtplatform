package com.dtyunxi.dtplatform.timer;

import com.dtyunxi.dtplatform.model.Config;
import com.dtyunxi.dtplatform.model.Model;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.TimerTask;

public class EndTimer extends TimerTask{
    public static Logger logger = Logger.getLogger(EndTimer.class);
    private Config config;

    public EndTimer(Config config) {
        this.config = config;
    }
    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public void run() {
        endSimulator();
    }

    public void endSimulator(){
        List<Model> models = config.getModels();
        for (Model model : models) {
            logger.warn("Simulator produce data time out,the model of "+model.getModel()+" pieces of "+(model.totaling-1)+" data is produced.");
        }
        System.exit(0);
    }
}
