package com.dtyunxi.dtplatform.model;

import kafka.javaapi.producer.Producer;
import org.apache.hadoop.fs.FileSystem;

import java.io.Serializable;

public class Config implements Serializable {

    private String messModels[];
    private String startTime;
    private String endTime;
    private String threadNum;
    private String totalMess;
    private String topics[];
    private String fsPaths[];
    private String localPaths[];
    private String metadata_broker_list;
    private String producer_type;
    private String serializer_class;
    private String uri;
    private String user;
    private Producer<String, String> producer;
    private FileSystem fileSystem;

    public Config() {
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Producer<String, String> getProducer() {
        return producer;
    }

    public void setProducer(Producer<String, String> producer) {
        this.producer = producer;
    }

    public String getMetadata_broker_list() {
        return metadata_broker_list;
    }

    public void setMetadata_broker_list(String metadata_broker_list) {
        this.metadata_broker_list = metadata_broker_list;
    }

    public String getProducer_type() {
        return producer_type;
    }

    public void setProducer_type(String producer_type) {
        this.producer_type = producer_type;
    }

    public String getSerializer_class() {
        return serializer_class;
    }

    public void setSerializer_class(String serializer_class) {
        this.serializer_class = serializer_class;
    }

    public String[] getMessModels() {
        return messModels;
    }

    public void setMessModels(String[] messModels) {
        this.messModels = messModels;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(String threadNum) {
        this.threadNum = threadNum;
    }

    public String getTotalMess() {
        return totalMess;
    }

    public void setTotalMess(String totalMess) {
        this.totalMess = totalMess;
    }

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    public String[] getFsPaths() {
        return fsPaths;
    }

    public void setFsPaths(String[] fsPaths) {
        this.fsPaths = fsPaths;
    }

    public String[] getLocalPaths() {
        return localPaths;
    }

    public void setLocalPaths(String[] localPaths) {
        this.localPaths = localPaths;
    }
}
