package com.project3.api.entities.post.dto;

public class PostComment {
    private String commentText;
    private Long userId;

    public PostComment() {
    }

    public PostComment(String commentText, Long userId, Long groupId) {
        this.commentText = commentText;
        this.userId = userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PostComment{" +
                "commentText='" + commentText + '\'' +
                ", userId=" + userId +
                '}';
    }
}
