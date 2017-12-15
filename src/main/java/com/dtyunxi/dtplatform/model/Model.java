package com.dtyunxi.dtplatform.model;

import java.util.List;
import java.util.Map;

public class Model {

    private String model;
    private String threads;
    private String total;
    public  Long totaling=1L;
    private List<Map<String,String>> exports;

    public Model() {

    }
    public Model(String model, String threads, String total, List<Map<String, String>> exports) {
        this.model = model;
        this.threads = threads;
        this.total = total;
        this.exports = exports;
    }

    public Long getTotaling() {
        return totaling;
    }

    public void setTotaling(Long totaling) {
        this.totaling = totaling;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getThreads() {
        return threads;
    }

    public void setThreads(String threads) {
        this.threads = threads;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Map<String, String>> getExports() {
        return exports;
    }

    public void setExports(List<Map<String, String>> exports) {
        this.exports = exports;
    }
}
