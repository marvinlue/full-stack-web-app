package com.project3.api.entities.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project3.api.entities.comment.Comment;
import com.project3.api.entities.group.Group;
import com.project3.api.entities.user.User;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Table(name = "posts")
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

    // TODO: support multiple tags
    @Column(
            name = "category"
    )
    private String category;

    @JsonIgnoreProperties({"createdAt"})
    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName="gid", updatable = false)
    private Group group;

    @JsonIgnoreProperties({"password","registeredAt"})
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName="id", updatable = false)
    private User user;

    @Column(
            name = "location",
            updatable = false,
            columnDefinition = "POINT"
    )
    private Point location;

    @Column(
            name = "posted_at",
            updatable = false
    )
    private Timestamp postedAt;

    @JsonIgnoreProperties({"post"})
    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    public Post() {
    }

    public Post(String post, String category, Group group, User user, Point location, Timestamp postedAt) {
        this.post = post;
        this.category = category;
        this.group = group;
        this.user = user;
        this.location = location;
        this.postedAt = postedAt;
    }

    public Post(Long pid, String post, String category, Group group, User user, Point location, Timestamp postedAt) {
        this.pid = pid;
        this.post = post;
        this.category = category;
        this.group = group;
        this.user = user;
        this.location = location;
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

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Timestamp getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Timestamp postedAt) {
        this.postedAt = postedAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "pid=" + pid +
                ", post='" + post + '\'' +
                ", category='" + category + '\'' +
                ", group=" + group +
                ", user=" + user +
                ", location=" + location +
                ", postedAt=" + postedAt +
                ", comments=" + comments +
                '}';
    }
}
