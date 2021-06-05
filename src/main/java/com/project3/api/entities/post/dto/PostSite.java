package com.project3.api.entities.post.dto;

public class PostSite {
    private String sitename;
    private Integer radius;

    public PostSite() {
    }

    public PostSite(String sitename, Integer radius) {
        this.sitename = sitename;
        this.radius = radius;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "PostSite{" +
                "sitename='" + sitename + '\'' +
                ", radius=" + radius +
                '}';
    }
}
