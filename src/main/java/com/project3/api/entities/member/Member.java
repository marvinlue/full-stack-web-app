package com.project3.api.entities.member;

import com.project3.api.entities.group.Group;
import com.project3.api.entities.user.User;
import javax.persistence.*;
import java.sql.Timestamp;
import static javax.persistence.GenerationType.SEQUENCE;

@Table(name = "members_info")
@Entity(name = "members_info")
public class Member {
    @Id
    @SequenceGenerator(
            name = "member_sequence",
            sequenceName = "member_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "member_sequence"
    )
    @Column(
            name = "member_id",
            updatable = false
    )
    private Long memberId;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName="gid", updatable = false)
    private Group group;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName="id", updatable = false)
    private User user;

    @Column(
            name = "admin_rights"
    )
    private Boolean adminRights;

    @Column(
            name = "joined_at",
            updatable = false
    )
    private Timestamp joinedAt;

    public Member() {
    }

    public Member(Long memberId, Group group, User user, Boolean adminRights, Timestamp joinedAt) {
        this.memberId = memberId;
        this.group = group;
        this.user = user;
        this.adminRights = adminRights;
        this.joinedAt = joinedAt;
    }

    public Member(Group group, User user, Boolean adminRights, Timestamp joinedAt) {
        this.group = group;
        this.user = user;
        this.adminRights = adminRights;
        this.joinedAt = joinedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Boolean getAdminRights() { return adminRights; }

    public void setAdminRights(Boolean adminRights) { this.adminRights = adminRights; }

    public Timestamp getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Timestamp joinedAt) {
        this.joinedAt = joinedAt;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", adminRights=" + adminRights +
                ", joinedAt=" + joinedAt +
                ", user=" + user +
                ", group=" + group +
                '}';
    }
}
