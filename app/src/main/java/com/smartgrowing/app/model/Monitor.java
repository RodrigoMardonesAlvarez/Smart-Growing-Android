package com.smartgrowing.app.model;

public class Monitor {
    String alias, temp, acid;

    public Monitor(){}

    public Monitor(String alias, String placa, String temp, String acid) {
        this.alias = alias;
        this.temp = temp;
        this.acid = acid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getAcid() {
        return acid;
    }

    public void setAcid(String acid) {
        this.acid = acid;
    }
}
