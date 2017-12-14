#!/usr/bin/env bash
date=`date +%Y%m%d%H`
java -jar /home/admin/zhangguohao/simulator/apps/universal-simulator-dtplatform-1.0-SNAPSHOT-jar-with-dependencies.jar /home/admin/zhangguohao/simulator/config.json >>/home/admin/zhangguohao/simluator/logs/${date}.log