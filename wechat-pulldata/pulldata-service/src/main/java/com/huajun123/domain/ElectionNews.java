package com.huajun123.domain;

import java.util.Map;

public class ElectionNews {
    private Map<String,Object> bettingOdds;
    private Map<String,Object> nationalPolls;

    public Map<String, Object> getBettingOdds() {
        return bettingOdds;
    }

    public void setBettingOdds(Map<String, Object> bettingOdds) {
        this.bettingOdds = bettingOdds;
    }

    public Map<String, Object> getNationalPolls() {
        return nationalPolls;
    }

    public void setNationalPolls(Map<String, Object> nationalPolls) {
        this.nationalPolls = nationalPolls;
    }

    @Override
    public String toString() {
        return "ElectionNews{" +
                "bettingOdds=" + bettingOdds +
                ", nationalPolls=" + nationalPolls +
                '}';
    }
}
