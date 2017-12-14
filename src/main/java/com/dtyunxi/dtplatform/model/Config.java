package com.dtyunxi.dtplatform.model;

import kafka.javaapi.producer.Producer;
import org.apache.hadoop.fs.FileSystem;
import org.bson.Document;
import java.io.Serializable;
import java.util.List;

public class Config implements Serializable {
    private String startTime;
    private String endTime;
    private List<Model> models;
    private Document server;

    private Producer<String, String> producer;
    private FileSystem fileSystem;

    public Config() {
    }

    public Document getServer() {
        return server;
    }

    public void setServer(Document server) {
        this.server = server;
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

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public Producer<String, String> getProducer() {
        return producer;
    }

    public void setProducer(Producer<String, String> producer) {
        this.producer = producer;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }
}
