package com.project3.api.entities.site;

import com.project3.api.entities.post.Post;
import org.springframework.data.geo.Point;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.GenerationType.SEQUENCE;

@Table
@Entity(name = "sites")
public class Site {
    @Id
    @SequenceGenerator(
            name = "site_sequence",
            sequenceName = "site_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "site_sequence"
    )
    @Column(
            name = "sid",
            updatable = false
    )
    private Long sid;

    @Column(
            name = "site_name",
            updatable = false
    )
    private String siteName;

    @Column(
            name = "location",
            updatable = false
    )
    private Point location;

    @OneToMany(mappedBy = "site", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Post> posts = new ArrayList<>();

    public Site() {
    }

    public Site(Long sid, String siteName, Point location) {
        this.sid = sid;
        this.siteName = siteName;
        this.location = location;
    }

    public Site(String siteName, Point location) {
        this.siteName = siteName;
        this.location = location;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Site{" +
                "sid=" + sid +
                ", siteName='" + siteName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
