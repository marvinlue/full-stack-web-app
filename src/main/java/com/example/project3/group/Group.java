package com.example.project3.group;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.SEQUENCE;

@Table
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
            name = "group_admin"
    )
    private Long groupAdmin;

    @Column(
            name = "created_at",
            updatable = false
    )
    private Timestamp createdAt;

    public Group() {
    }

    public Group(Long gid, String groupName, Long groupAdmin, Timestamp createdAt) {
        this.gid = gid;
        this.groupName = groupName;
        this.groupAdmin = groupAdmin;
        this.createdAt = createdAt;
    }

    public Group(String groupName, Long groupAdmin, Timestamp createdAt) {
        this.groupName = groupName;
        this.groupAdmin = groupAdmin;
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

    public Long getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(Long groupAdmin) {
        this.groupAdmin = groupAdmin;
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
                ", groupAdmin='" + groupAdmin + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
