package com.project3.api.entities.post.dto;

public class CommentUpdate {
    private String updatedText;

    public CommentUpdate() {
    }

    public CommentUpdate(String updatedText) {
        this.updatedText = updatedText;
    }

    public String getUpdatedText() {
        return updatedText;
    }

    public void setUpdatedText(String updatedText) {
        this.updatedText = updatedText;
    }

    @Override
    public String toString() {
        return "CommentUpdate{" +
                "updatedText='" + updatedText + '\'' +
                '}';
    }
}
