package com.huajun123.domain;

public class NewsSelectpr {
    private String nationalRate;
    private String fRate;
    private String pRate;
    private String wRate;
    private String aRate;
    private String gRate;

    public String getNationalRate() {
        return nationalRate;
    }

    public void setNationalRate(String nationalRate) {
        this.nationalRate = nationalRate;
    }

    public String getfRate() {
        return fRate;
    }

    public void setfRate(String fRate) {
        this.fRate = fRate;
    }

    public String getpRate() {
        return pRate;
    }

    public void setpRate(String pRate) {
        this.pRate = pRate;
    }

    public String getwRate() {
        return wRate;
    }

    public void setwRate(String wRate) {
        this.wRate = wRate;
    }

    public String getaRate() {
        return aRate;
    }

    public void setaRate(String aRate) {
        this.aRate = aRate;
    }

    public String getgRate() {
        return gRate;
    }

    public void setgRate(String gRate) {
        this.gRate = gRate;
    }

    @Override
    public String toString() {
        return "NewsSelectpr{" +
                "nationalRate='" + nationalRate + '\'' +
                ", fRate='" + fRate + '\'' +
                ", pRate='" + pRate + '\'' +
                ", wRate='" + wRate + '\'' +
                ", aRate='" + aRate + '\'' +
                ", gRate='" + gRate + '\'' +
                '}';
    }
}
