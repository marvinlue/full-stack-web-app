package com.project3.api.entities.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project3.api.entities.comment.Comment;
import com.project3.api.entities.group.Group;
import com.project3.api.entities.site.Site;
import com.project3.api.entities.user.User;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName="gid", updatable = false)
    private Group group;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName="id", updatable = false)
    private User user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "site_id", referencedColumnName="sid", updatable = false)
    private Site site;

    @Column(
            name = "posted_at",
            updatable = false
    )
    private Timestamp postedAt;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    public Post() {
    }

    public Post(Long pid, String post, String category, Group group, User user, Site site, Timestamp postedAt) {
        this.pid = pid;
        this.post = post;
        this.category = category;
        this.group = group;
        this.user = user;
        this.site = site;
        this.postedAt = postedAt;
    }

    public Post(String post, String category, Group group, User user, Site site, Timestamp postedAt) {
        this.post = post;
        this.category = category;
        this.group = group;
        this.user = user;
        this.site = site;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
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
                ", group=" + group.toString() +
                ", user=" + user.toString() +
                ", site=" + site.toString() +
                ", postedAt=" + postedAt +
                '}';
    }
}
