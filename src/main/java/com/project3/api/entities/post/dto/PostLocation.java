package com.project3.api.entities.post.dto;

public class PostLocation {
    private Double longitude;
    private Double latitude;
    private Integer radius;

    public PostLocation() {
    }

    public PostLocation(Double longitude, Double latitude, Integer radius) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius*1000;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }


}
