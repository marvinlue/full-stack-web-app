package com.project3.api.post;

import javax.persistence.*;
import java.sql.Timestamp;
import static javax.persistence.GenerationType.SEQUENCE;

@Table
@Entity(name = "posts")
public class Post {
    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "post_sequence"
    )
    @Column(
            name = "pid",
            updatable = false
    )
    private Long pid;

    @Column(
            name = "post",
            columnDefinition = "TEXT"
    )
    private String post;

    @Column(
            name = "category"
    )
    private String category;

    @Column(
            name = "group_id",
            updatable = false
    )
    private Long groupId;

    @Column(
            name = "user_id",
            updatable = false
    )
    private Long userId;

    @Column(
            name = "site_id",
            updatable = false
    )
    private Long siteId;

    @Column(
            name = "posted_at",
            updatable = false
    )
    private Timestamp postedAt;

    public Post() {
    }

    public Post(Long pid, String post, String category, Long groupId, Long userId, Long siteId, Timestamp postedAt) {
        this.pid = pid;
        this.post = post;
        this.category = category;
        this.groupId = groupId;
        this.userId = userId;
        this.siteId = siteId;
        this.postedAt = postedAt;
    }

    public Post(String post, String category, Long groupId, Long userId, Long siteId, Timestamp postedAt) {
        this.post = post;
        this.category = category;
        this.groupId = groupId;
        this.userId = userId;
        this.siteId = siteId;
        this.postedAt = postedAt;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Timestamp getCreatedAt() {
        return postedAt;
    }

    public void setCreatedAt(Timestamp postedAt) {
        this.postedAt = postedAt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "pid=" + pid +
                ", post='" + post + '\'' +
                ", category='" + category + '\'' +
                ", groupId=" + groupId +
                ", userId=" + userId +
                ", siteId=" + siteId +
                ", postedAt=" + postedAt +
                '}';
    }
}
