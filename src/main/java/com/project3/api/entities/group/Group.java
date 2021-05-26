package com.project3.api.entities.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project3.api.entities.member.Member;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.GenerationType.SEQUENCE;

@Table(name = "_groups")
@Entity(name = "_groups")
public class Group {
    @Id
    @SequenceGenerator(
            name = "group_sequence",
            sequenceName = "group_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "group_sequence"
    )
    @Column(
            name = "gid",
            updatable = false
    )
    private Long gid;

    @Column(
            name = "group_name",
            columnDefinition = "TEXT"
    )
    private String groupName;

    @Column(
            name = "created_at",
            updatable = false
    )
    private Timestamp createdAt;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "group", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Member> members = new ArrayList<>();

    public Group() {
    }

    public Group(Long gid, String groupName, Timestamp createdAt) {
        this.gid = gid;
        this.groupName = groupName;
        this.createdAt = createdAt;
    }

    public Group(String groupName, Timestamp createdAt) {
        this.groupName = groupName;
        this.createdAt = createdAt;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Group{" +
                "gid=" + gid +
                ", groupName='" + groupName + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
