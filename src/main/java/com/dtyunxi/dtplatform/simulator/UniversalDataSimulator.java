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
        //开启监控线程，每分钟判断所有数据是否生产完毕，如果生产完毕则退出程序
        new Thread(new Runnable() {
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                        /*
                        ProducerSendThread-
                        Timer-0
                        Thread-0
                        DestroyJavaVM
                        metrics-meter-tick-thread-2
                        metrics-meter-tick-thread-1
                        Attach Listener
                         */
                        if (Thread.activeCount()==6){
                            logger.warn("All data ia put already,program is exit!");
                            System.exit(0);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        //输入配置文件
        if (args.length<1){
            logger.error("Usage:config file");
            System.exit(1);
        }
        String configFile = args[0];
        Config config = ConfigUtils.getConfig(configFile);
        Date startTime = DateUtils.getDate(config.getStartTime());
        Date endTime = DateUtils.getDate(config.getEndTime());
        Timer timer = new Timer();
        timer.schedule(new StartTimer(config),startTime);
        timer.schedule(new EndTimer(config),endTime);
    }
}
