package com.project3.api.entities.post.dto;


public class PostUpdate {
    private String text;

    public PostUpdate(String text) {
        this.text = text;
    }

    public PostUpdate() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
