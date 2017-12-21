#!/usr/bin/env bash
date=`date +%Y%m%d%H`
java -jar {USER_HOME}/universal-simulator-dtplatform-1.0-SNAPSHOT-jar-with-dependencies.jar {USER_HOME}/simulator/config.json >>{USER_HOME}/simluator/logs/${date}.log