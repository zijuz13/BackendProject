package com.huajun123.domain;

public class Menu {
    private Integer id;
    private String name;
    private String icon;
    private Integer status;
    private String url;
    private Boolean isTabbar;

    public Boolean getIsTabbar() {
        return isTabbar;
    }

    public void setIsTabbar(Boolean tabbar) {
        isTabbar = tabbar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", status=" + status +
                '}';
    }
}
