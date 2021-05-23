package com.project3.api.comment;

import javax.persistence.*;
import java.sql.Timestamp;
import static javax.persistence.GenerationType.SEQUENCE;

@Table
@Entity(name = "comments")
public class Comment {
    @Id
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "comment_sequence"
    )
    @Column(
            name = "cid",
            updatable = false
    )
    private Long cid;

    @Column(
            name = "comment_text",
            columnDefinition = "TEXT"
    )
    private String comment;

    @Column(
            name = "user_id",
            updatable = false
    )
    private Long userId;

    @Column(
            name = "post_id",
            updatable = false
    )
    private Long postId;

    @Column(
            name = "made_at",
            updatable = false
    )
    private Timestamp madeAt;

    public Comment() {
    }

    public Comment(Long cid, String comment, Long userId, Long postId, Timestamp madeAt) {
        this.cid = cid;
        this.comment = comment;
        this.userId = userId;
        this.postId = postId;
        this.madeAt = madeAt;
    }

    public Comment(String comment, Long userId, Long postId, Timestamp madeAt) {
        this.comment = comment;
        this.userId = userId;
        this.postId = postId;
        this.madeAt = madeAt;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Timestamp getMadeAt() {
        return madeAt;
    }

    public void setMadeAt(Timestamp madeAt) {
        this.madeAt = madeAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "cid=" + cid +
                ", comment='" + comment + '\'' +
                ", userId=" + userId +
                ", postId=" + postId +
                ", madeAt=" + madeAt +
                '}';
    }
}
