/*
package com.project3.api.entities.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project3.api.entities.post.Post;
import com.project3.api.entities.user.User;
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

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName="id", updatable = false)
    private User user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName="pid", updatable = false)
    private Post post;

    @Column(
            name = "made_at",
            updatable = false
    )
    private Timestamp madeAt;

    public Comment() {
    }

    public Comment(Long cid, String comment, User user, Post post, Timestamp madeAt) {
        this.cid = cid;
        this.comment = comment;
        this.user = user;
        this.post = post;
        this.madeAt = madeAt;
    }

    public Comment(String comment, User user, Post post, Timestamp madeAt) {
        this.comment = comment;
        this.user = user;
        this.post = post;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
                ", user=" + user.toString() +
                ", post=" + post.toString() +
                ", madeAt=" + madeAt +
                '}';
    }
}
*/
