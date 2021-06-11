package com.project3.api.entities.post.dto;

public class PostComment {
    private String commentText;

    public PostComment() {
    }

    public PostComment(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public String toString() {
        return "PostComment{" +
                "commentText='" + commentText + '\'' +
                '}';
    }
}
