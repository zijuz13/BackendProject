package com.huajun123.domain;

public class Project {
    private Integer id;
    private String name;
    private String video;
    private String description;
    private String salepoint;
    private String imageUrl;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalepoint() {
        return salepoint;
    }

    public void setSalepoint(String salepoint) {
        this.salepoint = salepoint;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", video='" + video + '\'' +
                ", description='" + description + '\'' +
                ", salepoint='" + salepoint + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
