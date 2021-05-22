package com.example.project3.member;

import javax.persistence.*;
import java.sql.Timestamp;
import static javax.persistence.GenerationType.SEQUENCE;

@Table
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

    public Member(Long memberId, Long groupId, Long userId, Boolean adminRights, Timestamp joinedAt) {
        this.memberId = memberId;
        this.groupId = groupId;
        this.userId = userId;
        this.adminRights = adminRights;
        this.joinedAt = joinedAt;
    }

    public Member(Long groupId, Long userId, Boolean adminRights, Timestamp joinedAt) {
        this.groupId = groupId;
        this.userId = userId;
        this.adminRights = adminRights;
        this.joinedAt = joinedAt;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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
                ", groupId=" + groupId +
                ", userId=" + userId +
                ", adminRights=" + adminRights +
                ", joinedAt=" + joinedAt +
                '}';
    }
}
