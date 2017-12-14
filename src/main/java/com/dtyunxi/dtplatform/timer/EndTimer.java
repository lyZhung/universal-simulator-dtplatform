package com.dtyunxi.dtplatform.timer;

import com.dtyunxi.dtplatform.utils.SimulatorUtils;
import org.apache.log4j.Logger;

import java.util.TimerTask;

public class EndTimer extends TimerTask{
    public static Logger logger = Logger.getLogger(EndTimer.class);

    public void run() {
        endSimulator();
    }

    public void endSimulator(){
        logger.warn("Simulator produce data time out,pieces of "+" data is produced.");
        System.exit(0);
    }


}
